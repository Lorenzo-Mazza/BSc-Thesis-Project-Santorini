package it.polimi.ingsw.PSP50;

public class Player {
    private String name;
    private Worker[] workers;
    private Worker selectedWorker;
    private God god;
    private Space lastMove;
    private Space lastBuild;

    public void selectWorker(Worker worker)
    {

    }

    public void setGod(God god)
    {
        this.god = god;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
