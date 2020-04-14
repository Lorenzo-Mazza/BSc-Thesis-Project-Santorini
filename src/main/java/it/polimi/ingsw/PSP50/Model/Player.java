package it.polimi.ingsw.PSP50.Model;

import it.polimi.ingsw.PSP50.Model.GodsList.God;

public class Player {
    private String name;
    private Worker[] workers;
    private Worker selectedWorker;
    private God god;
    private Space lastMove;
    private Space lastBuild;
    private Space thisBuild;
    private boolean hasMovedUp=false;
    private boolean hasBuilt=false;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void selectWorker(Worker worker) {

    }

    public Worker getSelectedWorker() {
        return selectedWorker;
    }

    public God getGod() {
        return god;
    }


    public void setGod(God god)
    {
        this.god = god;
    }

    public Space getLastMove() {
        return lastMove;
    }

    public void setLastMove(Space lastMove) {
        this.lastMove = lastMove;
    }

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
