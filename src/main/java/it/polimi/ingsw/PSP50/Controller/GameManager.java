package it.polimi.ingsw.PSP50.Controller;

import it.polimi.ingsw.PSP50.Model.*;

import java.util.ArrayList;

public class GameManager {

    /**
     * players_list is taken from the View data.
     */
    public Game startGame(ArrayList<Player> players_list)  //
    { Game new_game= new Game();
      new_game.setPlayers(players_list);
      Board game_board= new Board();
      new_game.setBoard(game_board);
      if (players_list.size()==2) { new_game.setType(GameType.TWOPLAYERS);}
      else if (players_list.size()==3) {new_game.setType(GameType.THREEPLAYERS);}

      Deck gameDeck= new Deck();
     // gameDeck.setUpDeck();
      new_game.setDeck(gameDeck);

      return new_game;
    }
    /**
     * each player picks a card, the order is random.
     */
    public void dealCards()
    {

    }
    /**
     *  Players choose the initial position of their Workers and an order of Play is established.
     */
    public void setUpGame()
    {
    }
    /**
     *
     */
    public void playTurn()
    {
    }
    /**
     * checking if any player has changed the Win Condition Flag at the end of his turn
     */
    public void checkWinCondition()
    {
    }

    /**
     * end the game and go back to the initial menu
     */
    public void endGame()
    {
    }

    /**
     * Send to the client the current state of the gameboard
     */
    private void sendInfo()  {
        //
    }



}
