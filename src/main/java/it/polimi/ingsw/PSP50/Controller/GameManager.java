package it.polimi.ingsw.PSP50.Controller;

import it.polimi.ingsw.PSP50.Model.*;
import it.polimi.ingsw.PSP50.Model.GodsList.Athena;
import it.polimi.ingsw.PSP50.Model.GodsList.Atlas;
import it.polimi.ingsw.PSP50.Model.GodsList.God;
import it.polimi.ingsw.PSP50.Utils.Observable;
import it.polimi.ingsw.PSP50.Utils.Observer;
import it.polimi.ingsw.PSP50.View.VirtualView;
import it.polimi.ingsw.PSP50.network.messages.Message;
import it.polimi.ingsw.PSP50.network.messages.ToClient.*;
import it.polimi.ingsw.PSP50.network.messages.ToClientMessage;
import it.polimi.ingsw.PSP50.network.messages.ToServerMessage;

import java.util.*;
import java.util.concurrent.ConcurrentHashMap;

public class GameManager implements Runnable, Observer {

    private final Game game;
    private  ConcurrentHashMap<String,VirtualView> virtualViews;
    private Object receiver=null;




    public GameManager(ConcurrentHashMap<String, VirtualView> lobby){
        virtualViews = lobby;
        game = new Game();
        List<String> nicknames = new ArrayList<>(lobby.keySet());
        ArrayList<Color> colors = new ArrayList<>(Arrays.asList(Color.values()));
        for (int i = 0; i < nicknames.size(); i++) {
            String nickname = nicknames.get(i);
            Player player = new Player(nickname);
            int randomIndex = new Random().nextInt(colors.size());
            player.setColor(colors.get(randomIndex));
            colors.remove(randomIndex);
            game.setPlayer(player);
            /*
            Register every virtual view as Observers of the Model
            */
            virtualViews.get(nickname).setObservable(game);
        }
    }


    public Game getGame() {
        return game;
    }

    @Override
    public void run(){
        startGame();
        setUpGame();
        runGame();
        endGame();
    }


    /**
     * Start a new game
     */
    public void startGame()
    {
        game.setOpponents();
        Board gameBoard = new Board();
        game.setBoard(gameBoard);
        if (game.getAllPlayers().size() == 2) { game.setType(GameType.TWOPLAYERS);}
        else if (game.getAllPlayers().size() == 3) {game.setType(GameType.THREEPLAYERS);}

        for (Player player: game.getAllPlayers())
        {
            VirtualView view= virtualViews.get(player.getName());
            view.sendToClient(new WelcomeMessage(player));
        }
        // Give a short pause to read the Welcome Message (5 seconds)
        try {
            Thread.sleep(5000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        Deck gameDeck = new Deck();
        game.setDeck(gameDeck);
        dealCards();
        game.setModelCopy();
    }

    /**
     * Each player picks a God card, the order is random.
     */
    private void dealCards()
    {
        Deck deck = game.getDeck();
        ArrayList<God> cardsLeft = deck.getChosenCards(game.getType());
        ArrayList<Player> playersCopy = new ArrayList<>(game.getAllPlayers());
        ArrayList<Player> orderOfPlay = new ArrayList<>();
        while (cardsLeft.size()>0)
        {
            int randomIndex = (new Random().nextInt(playersCopy.size())) ;
            Player player = playersCopy.get(randomIndex);
            orderOfPlay.add(player);
            // send cardsLeft to the player view and get a choice back (an int)
            int choice= chooseGod(player,cardsLeft);
            // random assignment if player doesn't answer correctly
            player.setGod(cardsLeft.get(choice));
            cardsLeft.remove(choice);
            playersCopy.remove(randomIndex);
            receiver=null;
        }
        // set the correct order of play in the players array of game
        game.setPlayers(orderOfPlay);
    }





    /**
     * Complete set-up of the game.
     * Players choose the initial position of their Workers.
     */
    public void setUpGame()
    {
        // Game is starting!
        for (Player player: game.getAllPlayers()) {
            VirtualView view = virtualViews.get(player.getName());
            view.sendToClient(new GameStarting(player));
            // Waits for player's OK signal (If not the GUI is too slow to respond correctly
            while(receiver == null) {
                Thread.yield();
            }
            receiver=null;
        }
        ArrayList<Player> playersLeft = new ArrayList<>(game.getAllPlayers());
        while (playersLeft.size()>0)
        {
            Player player = playersLeft.get(0);
            receiver=null;
            // player chooses where to place his workers on the board
            VirtualView view = virtualViews.get(player.getName());
            view.sendToClient(new InitializeWorkers(this.game.getBoard()));
            while (receiver==null)
            {
                Thread.yield();
            }
            // get the Spaces that the user has selected
            player.getWorkers()[0].setPosition(((ArrayList<Space>)receiver).get(0));
            player.getWorkers()[1].setPosition(((ArrayList<Space>)receiver).get(1));
            game.notifyChange();
            receiver=null;
            view.unregister(this);
            playersLeft.remove(0);
        }
    }

    /**
     * Run a full game
     */
    public void runGame()
    {
        ArrayList<TurnManager> turnList= new ArrayList<>();
        //create Turn Managers
        for (int currentPlayer=0; currentPlayer<game.getType().getSize();currentPlayer++)
        {
            Player player= game.getPlayer(currentPlayer);
            TurnManager turn= new TurnManager(game,player,virtualViews.get(player.getName()));
            turnList.add(turn);
        }

        // Play all the turns
        int currentPlayer=0;
        while ( game.getWinner()==null)
        {
            Player player =turnList.get(currentPlayer).getPlayer();
            boolean result=turnList.get(currentPlayer).playTurn();
            // if a player won in his turn
            if (result)
            {
                game.setWinner(player);
            }
            else
            {
                // if a player lost in his turn
                if (player.getHasLost())
                {
                    //eliminate player from the game
                    // set parameters correctly, remove his workers from the board, remove the turn from TurnList
                    // player is not eliminated from the game Observers so he keeps receiving updates of the game
                    Worker[] workers= player.getWorkers();
                    workers[0].getPosition().setWorker(null);
                    workers[1].getPosition().setWorker(null);
                    game.getAllPlayers().remove(player);
                    // tell the client he lost
                    this.virtualViews.get(player.getName()).sendToClient(new YouLostMessage(null));
                    for (Player otherPlayer: game.getAllPlayers())
                        otherPlayer.getOpponents().remove(player);

                    turnList.remove(currentPlayer);

                    //if only a player is left, he wins automatically
                    if (turnList.size()==1)
                    {
                        game.setWinner(turnList.get(0).getPlayer());
                    }
                    else game.notifyChange();
                }

                // if a player neither won or lost, do nothing
            }

            currentPlayer++;
            if (currentPlayer==game.getAllPlayers().size())
            {
                currentPlayer=0;
            }
        }
        // after a winner is declared, end the game.
    }



    /**
     * end the game
     */
    private void endGame()
    {
        // Give a short pause to see the last move of the game (5 seconds)
        try {
            Thread.sleep(5000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        // tells to every client the name of the winner.
        notifyAll(new WinnerMessage(game.getWinner().getName()));
    }


    /**
     * Notify every player (method used in pre-game phases, ex lobby)
     * @param msg Message sent
     */
    void notifyAll(ToClientMessage msg) {
        for (String user : virtualViews.keySet()) {
            VirtualView view =virtualViews.get(user);
            view.sendToClient(msg);
        }
    }


    private int chooseGod(Player player, ArrayList<God> cardsLeft){
        int choice=0;
        VirtualView view= virtualViews.get(player.getName());
        if (view!=null) {
            this.setObservable(view);
            view.sendToClient(new ChooseGodCard(cardsLeft));
        }
        while (receiver==null)
        {
            Thread.yield();
        }
        // get the God that the user has selected
        if (((int) receiver >= 0 ) && (cardsLeft.get((int)receiver)!=null)) {
                choice=(int) receiver;
        }
        return choice;
    }

    @Override
    public synchronized void update(Message message) {
        receiver = ((ToServerMessage)message).castChoice();
    }

    @Override
    public void setObservable(Observable observable) {
        observable.register(this);
    }

    public void stopThread(){
        Thread.currentThread().interrupt();
    }
}
