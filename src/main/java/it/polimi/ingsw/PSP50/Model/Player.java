package it.polimi.ingsw.PSP50.Model;

import it.polimi.ingsw.PSP50.Model.GodsList.God;

/**
 * *Description of class*
 */
public class Player {
    final private String name;
    final private Worker[] workers = new Worker[2];
    private Worker selectedWorker;
    private God god;
    private Space lastMove;
    private Space lastBuild;
    private Space thisBuild;
    private boolean hasMovedUp;
    private boolean hasBuilt;

    /**
     * *Constructor*
     * @param name a String variable that contains player's name
     */
    public Player(String name) {
        this.name = name;
        this.workers[0] = new Worker(this);
        this.workers[1] = new Worker(this);
        this.selectedWorker = null;
        this.lastMove = null;
        this.lastBuild = null;
        this.thisBuild = null;
        this.hasMovedUp = false;
        this.hasBuilt = false;
    }

    /**
     * *Description of method*
     * @return player's name in a String
     */
    public String getName() {
        return name;
    }

    /**
     * *Description of method*
     * @return an Array of Worker
     */
    public Worker[] getWorkers() { return workers; }

    // public void setName(String name) {this.name = name;} Constructor + final

    /**
     * *Description of method*
     * @param worker variable
     */
    public void selectWorker(Worker worker) {
        this.selectedWorker = worker;
    }

    /**
     * *Description of method*
     * @return a Worker variable
     */
    public Worker getSelectedWorker() {
        return selectedWorker;
    }

    /**
     * *Description of method*
     * @return a God variable
     */
    public God getGod() {
        return god;
    }

    /**
     * *Description of method*
     * @param god variable
     */
    public void setGod(God god){this.god = god;}

    /**
     * *Description of method*
     * @return a Space variable
     */
    public Space getLastMove() {
        return lastMove;
    }

    /**
     * *Description of method*
     * @param lastMove a Space variable
     */
    public void setLastMove(Space lastMove) { this.lastMove = lastMove; }

    /**
     * *Description of method*
     * @return a Space variable
     */
    public Space getLastBuild() {
        return lastBuild;
    }

    /**
     * *Description of method*
     * @param lastBuild a Space variable
     */
    public void setLastBuild(Space lastBuild) {
        this.lastBuild = lastBuild;
    }

    /**
     * *Description of method*
     * @return a Space variable
     */
    public Space getThisBuild() { return thisBuild; }

    /**
     * *Description of method*
     * @param thisBuild a Space Variable
     */
    public void setThisBuild(Space thisBuild) { this.thisBuild = thisBuild; }

    /**
     * *Description of method*
     * @return a boolean variable
     */
    public boolean getHasMovedUp() { return hasMovedUp; }

    /**
     * *Description of method*
     * @param hasMovedUp a Space variable
     */
    public void setHasMovedUp(boolean hasMovedUp) { this.hasMovedUp = hasMovedUp; }

    /**
     * *Description of method*
     * @return a boolean variable
     */
    public boolean getHasBuilt() { return hasBuilt; }

    /**
     * *Description of method*
     * @param hasBuilt a boolean variable
     */
    public void setHasBuilt(boolean hasBuilt) { this.hasBuilt = hasBuilt; }
}
