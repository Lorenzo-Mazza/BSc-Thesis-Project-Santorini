package it.polimi.ingsw.PSP50.Controller;

import it.polimi.ingsw.PSP50.Model.*;
import it.polimi.ingsw.PSP50.Model.GodsList.God;
import it.polimi.ingsw.PSP50.view.VirtualView;
import it.polimi.ingsw.PSP50.network.messages.ToClientMessage;

import java.util.*;
import java.util.concurrent.ConcurrentHashMap;

public class GameManager implements Runnable, Cloneable {

    private final Game game;
    private  ConcurrentHashMap<String,VirtualView> virtualViews;
    private final List<Player> players;




    public GameManager(ConcurrentHashMap<String, VirtualView> lobby){
        virtualViews = lobby;
        game = new Game();
        ArrayList <VirtualView> list = new ArrayList<>(lobby.values());
        game.setViews(list);
        List<String> nicknames = new ArrayList<>(lobby.keySet());
        for (int i = 0; i < nicknames.size(); i++) {
            String nickname = nicknames.get(i);
            Player player = new Player(nickname);
            game.setPlayer(player);
        }
        players = game.getAllPlayers();
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
    public void startGame()  //
    {
        game.setOpponents();
        Board gameBoard = new Board();
        game.setBoard(gameBoard);
        if (players.size() == 2) { game.setType(GameType.TWOPLAYERS);}
        else if (players.size() == 3) {game.setType(GameType.THREEPLAYERS);}

        Deck gameDeck = new Deck();
        game.setDeck(gameDeck);
        dealCards();
    }

    /**
     * Each player picks a God card, the order is random.
     * @author ALBI MIRAKA
     */
    private void dealCards()
    {
        Deck deck = game.getDeck();
        ArrayList<God> cardsLeft = deck.getChosenCards(game.getType());
        ArrayList<Player> playersCopy = new ArrayList<>(game.getAllPlayers());
        while (cardsLeft.size()>0)
        {
            int randomIndex = (new Random().nextInt(playersCopy.size())) ;
            Player player = playersCopy.get(randomIndex);
            // send cardsLeft to the player view and get a choice back (an int)
            int choice= 0;// random assignment just for testing
            player.setGod(cardsLeft.get(choice));
            cardsLeft.remove(choice);
            playersCopy.remove(randomIndex);
        }
    }

    /**
     * Complete set-up of the game.
     * Players choose the initial position of their Workers and an order of Play is established.
     */
    public void setUpGame()
    {
        ArrayList<Player> playersLeft = new ArrayList<Player>(game.getAllPlayers());
        ArrayList<Player> orderOfPlay = new ArrayList<>();
        while (playersLeft.size()>0)
        {
            int randomIndex = (new Random().nextInt(playersLeft.size()));
            Player player = playersLeft.get(randomIndex);
            orderOfPlay.add(player);

            // player chooses where to place his workers on the board
            playersLeft.remove(randomIndex);
        }
        // set the correct order of play in the players array of game
        game.setPlayers(orderOfPlay);
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
            TurnManager turn= new TurnManager(game,player,game.getView(player));
            turnList.add(turn);
        }

        // Play all the turns
        int currentPlayer=0;
        while ( game.getWinner()==null)
        {
            Player player =turnList.get(currentPlayer).getPlayer();
            turnList.get(currentPlayer).run();
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
                    Worker[] workers= player.getWorkers();
                    workers[0].getPosition().setWorker(null);
                    workers[1].getPosition().setWorker(null);
                    game.getAllPlayers().remove(player);
                    turnList.remove(currentPlayer);

                    //if only a player is left, he wins automatically
                    if (turnList.size()==1)
                    {
                        game.setWinner(turnList.get(0).getPlayer());
                    }
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
        this.endGame();
    }



    /**
     * end the game
     */
    private void endGame()
    {
        // tells to every client the name of the winner.
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




}
