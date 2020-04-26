package it.polimi.ingsw.PSP50.Model;

import it.polimi.ingsw.PSP50.Model.GodsList.God;

public class Player {
    final private String name;
    final private Worker[] workers = new Worker[2];
    private God god;
    private Worker selectedWorker;
    private Space lastMove = null;
    private Space lastBuild = null;
    private Space thisBuild = null;
    private boolean hasMovedUp = false;
    private boolean hasBuilt = false;

    public Player(String name) {
        this.name = name;
        this.workers[0] = new Worker(this);
        this.workers[1] = new Worker(this);
    }

    public String getName() {
        return name;
    }

    public Worker[] getWorkers() { return workers; }

    // public void setName(String name) {this.name = name;} Constructor + final

    public void selectWorker(Worker worker) {
        this.selectedWorker = worker;
    }

    public Worker getSelectedWorker() {
        return selectedWorker;
    }

    public God getGod() {
        return god;
    }

    public void setGod(God god){this.god = god;}

    public Space getLastMove() {
        return lastMove;
    }

    public void setLastMove(Space lastMove) { this.lastMove = lastMove; }

    public Space getLastBuild() {
        return lastBuild;
    }

    public void setLastBuild(Space lastBuild) {
        this.lastBuild = lastBuild;
    }

    public Space getThisBuild() { return thisBuild; }

    public void setThisBuild(Space thisBuild) { this.thisBuild = thisBuild; }

    public boolean getHasMovedUp() { return hasMovedUp; }

    public void setHasMovedUp(boolean hasMovedUp) { this.hasMovedUp = hasMovedUp; }

    public boolean getHasBuilt() { return hasBuilt; }

    public void setHasBuilt(boolean hasBuilt) { this.hasBuilt = hasBuilt; }
}
