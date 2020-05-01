package it.polimi.ingsw.PSP50.Controller;

import it.polimi.ingsw.PSP50.Model.*;
import it.polimi.ingsw.PSP50.Model.GodsList.Apollo;
import it.polimi.ingsw.PSP50.Model.GodsList.God;

import java.util.ArrayList;
import java.util.Random;

public class GameManager {

    /**
     * Start a new game
     * @param playersList is taken from the View.
     * @return game The game that's being started
     */
    public Game startGame(ArrayList<Player> playersList)  //
    { Game newGame= new Game();
      newGame.setPlayers(playersList);
      newGame.setOpponents();
      Board gameBoard= new Board();
      newGame.setBoard(gameBoard);
      if (playersList.size()==2) { newGame.setType(GameType.TWOPLAYERS);}
      else if (playersList.size()==3) {newGame.setType(GameType.THREEPLAYERS);}

      Deck gameDeck= new Deck();
      newGame.setDeck(gameDeck);
      this.dealCards(newGame);

      return newGame;
    }

    /**
     * Each player picks a God card, the order is random.
     * @param game The game that's being played
     */
    private void dealCards(Game game)
    {
        Deck deck= game.getDeck();
        ArrayList<God> cardsLeft= deck.getChosenCards(game.getType());
        ArrayList <Player> players= game.getAllPlayers();
        while (cardsLeft.size()>0)
        {
            int randomIndex= (new Random().nextInt(players.size()))-1 ; // array index= array size - 1
            Player player= players.get(randomIndex);
            // send cardsLeft to the player view and get a choice back (an int)
            int choice= 0;// random assignment just for testing
            player.setGod(cardsLeft.get(choice));
            cardsLeft.remove(choice);
            players.remove(randomIndex);
        }
    }

    /**
     * Complete set-up of the game.
     * Players choose the initial position of their Workers and an order of Play is established.
     * @param game The game that's being played
     */
    public void setUpGame(Game game)
    {
        ArrayList <Player> playersLeft= game.getAllPlayers();
        ArrayList <Player> orderOfPlay= new ArrayList<>();
        while (playersLeft.size()>0)
        {
            int randomIndex = (new Random().nextInt(playersLeft.size())) - 1;
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
     * @param game The game that's being played
     */
    public void runGame(Game game)
    {
        ArrayList<TurnManager> turnList= new ArrayList<>();
        //Play first turn and create Turn Managers
        for (int currentPlayer=0; currentPlayer<game.getType().getSize();currentPlayer++)
        {
            Player player= game.getPlayer(currentPlayer);
            TurnManager turn= new TurnManager(player);
            turnList.add(turn);
            turn.playTurn();
        }

        // Play all the other turns
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







}
