package it.polimi.ingsw.PSP50.Model;

import it.polimi.ingsw.PSP50.Model.*;
import it.polimi.ingsw.PSP50.Model.GodsList.Apollo;

import java.util.ArrayList;

/**
 * Game contains all of the parameters needed to play a game
 */
public class Game {

    /**
     * List of players in the game
     */
    private ArrayList<Player> players;

    /**
     * Reference to the game board
     */
    private Board board;
  //  private Turn turn;

    /**
     * Type of game played (2 or 3 players)
     */
    private GameType type;

    /**
     * Deck used for the game
     */
    private Deck deck;

    /**
     * assigns a deck to the game
     * @param deck Deck of God cards
     */
    public void setDeck(Deck deck) { this.deck = deck; }

    /**
     *
     * @return the game deck
     */
    public Deck getDeck() {
        return deck;
    }

    /**
     * Sets the game type
     * @param type a GameType variable that indicates how many players are playing
     */
    public void setType(GameType type) {
        this.type = type;
    }

    /**
     *
     * @return the game type
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
     *
     * @return the game board
     */
    public Board getBoard() { return board; }

   // public void setTurn(Turn turn) { this.turn = turn; }

    // public Turn getTurn() {return turn;}

    /**
     * Assigns a list of players to the game
     * @param players ArrayList of the Players in the game
     */
    public void setPlayers(ArrayList<Player> players) {
        this.players = players;
    }

    /**
     *
     * @return the ArrayList of players
     */
    public ArrayList<Player> getAllPlayers() {
        return players;
    }

    /**
     * Add a player to the ArrayList of the players
     * @param player Player that is being added
     */
    public void setPlayer(Player player) { players.add(player);}

    /**
     * Get a player from the ArrayList of the players
     * @param position an index used to identify the player
     * @return a Player from the ArrayList of players
     */
    public Player getPlayer(int position) {
        return players.get(position);
    }
}
