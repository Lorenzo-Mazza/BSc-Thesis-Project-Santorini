package it.polimi.ingsw.PSP50;

public class Player {
    private String name;
    private Worker[] workers;
    private Worker selectedWorker;
    private God god;
    private Space lastMove;
    private Space lastBuild;

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
}
