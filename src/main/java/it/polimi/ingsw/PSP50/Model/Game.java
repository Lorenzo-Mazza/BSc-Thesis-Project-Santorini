package it.polimi.ingsw.PSP50.Model;

import it.polimi.ingsw.PSP50.Observable;
import it.polimi.ingsw.PSP50.view.VirtualView;
import it.polimi.ingsw.PSP50.network.messages.ToClient.ModelMessage;
import org.apache.commons.lang3.SerializationUtils;

import java.io.Serializable;
import java.util.ArrayList;

/**
 * Game contains all of the parameters needed to play a game
 */
public class Game extends Observable implements Serializable{

    private ArrayList<VirtualView> views= new ArrayList<>();

    /**
     * List of players in the game
     */
    private ArrayList<Player> players= new ArrayList<>();

    /**
     * Player that won the game
     */
    private Player winner=null;

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
     * gets the winner of the game
     * @return the Player that won the game
     */
    public Player getWinner() {
        return winner;
    }

    /**
     * assigns a winner to the game
     * @param winner Player that won the game
     */
    public void setWinner(Player winner) {
        this.winner = winner;
    }

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

    public void setOpponents(){
        ArrayList <Player> playersList= this.getAllPlayers();
        int index=0;
        for (this.getPlayer(index);index<playersList.size();index++)
        {
            Player thisPlayer= this.getPlayer(index);
            for (int secondIndex=0;secondIndex<playersList.size();secondIndex++)
            {
                Player opponent= this.getPlayer(secondIndex);
                if (thisPlayer!= opponent)
                {
                    thisPlayer.addOpponent(opponent);
                }
            }
        }
    }

    public VirtualView getView(Player player){
        VirtualView vv = null;
        boolean found=false;
        for (int i = 0; i<views.size() && !found; i++){
            vv= views.get(i);
            if (vv.getPlayerName().equals(player.getName()))
                found=true;
        }
        return vv;
    }

    public ArrayList<VirtualView> getViews() {
        return views;
    }

    public void setViews(ArrayList<VirtualView> views) {
        this.views = views;
    }

    /*
     ** Method sends to every virtual view an updated copy of the model. It implements the Observer/Observable pattern
     */
    public void notifyChange(){
        Game modelCopy= copyModel();
        notifyObservers(new ModelMessage(modelCopy));
    }

    /*
    ** Method creates a deep copy of the model using the commons.lang library
     */
    public Game copyModel() {
        Game copy= SerializationUtils.clone(this);
        return copy;
    }

}
