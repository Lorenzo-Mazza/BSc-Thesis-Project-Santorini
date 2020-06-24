package it.polimi.ingsw.PSP50.Model;

import it.polimi.ingsw.PSP50.Model.GodsList.God;

import java.io.Serializable;
import java.util.ArrayList;

/**
 * Player contains all of the player's data.
 */
public class Player implements Cloneable, Serializable {

    /**
     * A String that contains player's name
     */
    final private String name;
    /**
     * The 2 workers owned by the player
     */
    final private Worker[] workers = new Worker[2];
    /**
     * Worker that's been selected by the player
     */
    private Worker selectedWorker;
    /**
     * God card that's been assigned to the player
     */
    private God god;
    /**
     * The very last space where the player moved from in the previous turn
     */
    private Space lastMove;
    /**
     * The very last space where the player built
     */
    private Space lastBuild;
    /**
     * Space where the player is currently building
     */
    private Space thisBuild;
    /**
     * Boolean that checks if the player moved up in the current turn
     * Used to manage Athena's power
     */
    private boolean hasMovedUp;
    /**
     * Boolean that checks if the player has already built in the current turn
     * Used to manage Prometheus's power
     */
    private boolean hasBuilt;

    /**
     * Boolean that checks if the player has lost in the current turn
     */
    private boolean hasLost;

    private boolean playerBlocked;

    private ArrayList<Player> opponents ;

    /**
     * Color of the player workers on the board
     */
    private Color color;



    /**
     * Player Constructor
     * @param name Player's name
     */
    public Player(String name) {
        this.name = name;
        this.workers[0] = new Worker(this,0);
        this.workers[1] = new Worker(this,1);
        this.selectedWorker = null;
        this.lastMove = null;
        this.lastBuild = null;
        this.thisBuild = null;
        this.hasMovedUp = false;
        this.hasBuilt = false;
        this.hasLost=false;
        this.playerBlocked=false;
        this.opponents= new ArrayList<>();
    }

    /**
     *
     * @return player's name
     */
    public String getName() {
        return name;
    }

    /**
     *
     * @return an Array that contains the two workers owned by the player
     */
    public Worker[] getWorkers() { return workers; }

    // public void setName(String name) {this.name = name;} Constructor + final

    /**
     * Selects a worker
     * @param worker The worker that will be selected
     */
    public void selectWorker(Worker worker) {
        this.selectedWorker = worker;
    }

    /**
     *
     * @return the selected worker
     */
    public Worker getSelectedWorker() {
        return selectedWorker;
    }

    /**
     *
     * @return player's God
     */
    public God getGod() {
        return god;
    }

    /**
     * assigns a God card to the player
     * @param god The God that's been assigned to the player
     */
    public void setGod(God god){this.god = god;}

    /**
     *
     * @return the very last space from where the player moved in the previous turn
     */
    public Space getLastMove() {
        return lastMove;
    }

    /**
     * updates correctly the last move
     * @param lastMove The very last space from where the player moved in the previous turn
     */
    public void setLastMove(Space lastMove) { this.lastMove = lastMove; }

    /**
     *
     * @return the very last space in which the player built
     */
    public Space getLastBuild() {
        return lastBuild;
    }

    /**
     * updates correctly the Last Build
     * @param lastBuild The very last space in which the player built
     */
    public void setLastBuild(Space lastBuild) {
        this.lastBuild = lastBuild;
    }

    /**
     *
     * @return the space where the player is currently building
     */
    public Space getThisBuild() { return thisBuild; }

    /**
     *
     * @param thisBuild The space where the player is currently building
     */
    public void setThisBuild(Space thisBuild) { this.thisBuild = thisBuild; }

    /**
     *
     * @return a boolean: true if the player moved up in the current turn, false otherwise
     */
    public boolean getHasMovedUp() { return hasMovedUp; }

    /**
     *
     * @param hasMovedUp Indicates whether the player has moved up in the current turn
     */
    public void setHasMovedUp(boolean hasMovedUp) { this.hasMovedUp = hasMovedUp; }

    /**
     *
     * @return a boolean: true if the player has already built in the current turn, false otherwise
     */
    public boolean getHasBuilt() { return hasBuilt; }

    /**
     *
     * @param hasBuilt Indicates whether the player has already built in the current turn
     */
    public void setHasBuilt(boolean hasBuilt) { this.hasBuilt = hasBuilt; }

    /**
     *
     * @return a boolean: true if the player has lost in the current turn, false otherwise
     */
    public boolean getHasLost() {
        return hasLost;
    }

    /**
     *
     * @param hasLost Indicates whether the player has lost in the current turn
     */
    public void setHasLost(boolean hasLost) {
        this.hasLost = hasLost;
    }

    /**
     *
     * @return a boolean that indicates whether the player is blocked in the current turn
     */
    public boolean isPlayerBlocked() {
        return playerBlocked;
    }
    /**
     *
     * @param playerBlocked Indicates whether the player is blocked in the current turn
     */
    public void setPlayerBlocked(boolean playerBlocked) {
        this.playerBlocked = playerBlocked;
    }
    /**
     *
     * @return a list containing all the player's opponents
     */
    public ArrayList<Player> getOpponents() {
        return opponents;
    }
    /**
     *
     * @param opponent add an opponent to the player's opponents list
     */
    public void addOpponent(Player opponent) {
        opponents.add(opponent);
    }

    /**
     * @param color Color of the player workers on the board
     */
    public void setColor(Color color) {
        this.color = color;
    }

    /**
     * @return Color of the player workers on the board
     */
    public Color getColor() {
        return color;
    }

}
