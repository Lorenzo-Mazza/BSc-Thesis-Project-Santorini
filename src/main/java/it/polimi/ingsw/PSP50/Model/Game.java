package it.polimi.ingsw.PSP50.Model;

import it.polimi.ingsw.PSP50.Model.*;
import it.polimi.ingsw.PSP50.Model.GodsList.Apollo;

import java.util.ArrayList;

/**
 * *Description of class*
 */
public class Game {
    private ArrayList<Player> players;
    private Board board;
  //  private Turn turn;
    private GameType type;
    private Deck deck;

    /**
     * *Description of method*
     * @param deck of cards(God)
     */
    public void setDeck(Deck deck) { this.deck = deck; }

    /**
     * *Description of method*
     * @return a Deck variable
     */
    public Deck getDeck() {
        return deck;
    }

    /**
     * *Description of method*
     * @param type a GameType variable that indicate how many players are playing
     */
    public void setType(GameType type) {
        this.type = type;
    }

    /**
     * *Description of method*
     * @return a GameType variable
     */
    public GameType getType() { return type; }

    /**
     * *Description of method*
     * @param board a Board variable
     */
    public void setBoard(Board board) {
        this.board = board;
    }

    /**
     * *Description of method*
     * @return a Board variable
     */
    public Board getBoard() { return board; }

   // public void setTurn(Turn turn) { this.turn = turn; }

    // public Turn getTurn() {return turn;}

    /**
     * *Description of method*
     * @param players an ArrayList of players
     */
    public void setPlayers(ArrayList<Player> players) {
        this.players = players;
    }

    /**
     * *Description of method*
     * @return an ArrayList of players
     */
    public ArrayList<Player> getAllPlayers() {
        return players;
    }

    /**
     * *Description of method (secondo me non serve position)*
     * @param player a Player variable
     * @param position an integer variable that is used like an index
     */
    public void setPlayer(Player player, int position) { players.add(position,player);}

    /**
     * *Description of method*
     * @param position an integer value used to find the right player
     * @return a Player variable indicated by position from ArrayList of players
     */
    public Player getPlayer(int position) {
        return players.get(position);
    }
}
