package it.polimi.ingsw.PSP50;

public class GameManager {

    public Game startGame(Player[] players_list)  // players_list is taken from the View data
    { Game new_game= new Game();
      new_game.setPlayers(players_list);
      Board game_board= new Board();
      new_game.setBoard(game_board);
      Deck game_deck= new Deck();
      new_game.setDeck;
      if (players_list.length==2) { new_game.setType(GameType.TWOPLAYERS);}
      else if (players_list.length==3) {new_game.setType(GameType.THREEPLAYER);}

      return new_game;
    }

    public void dealCards()
    {
      /* The oldest player chooses the Gods to use in the game, and every player,
      starting from the youngest to the oldest, picks one. Otherwise randomly the system assigns a God to each player*/

    }

    public void setUpGame()
    {
        // Players choose the initial position of their Workers and an order of Play is established.
    }

    public void nextTurn()
    {
        Turn current_turn = new Turn();
    }

    public void checkWinCondition()
    {
      //checking if any player has changed the Win Condition Flag at the end of his turn
    }

    public void endGame()
    {
       //end the game and go back to the initial menu
    }

}
