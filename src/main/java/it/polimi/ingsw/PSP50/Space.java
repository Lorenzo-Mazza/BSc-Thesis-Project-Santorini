package it.polimi.ingsw.PSP50;

public class Space {
    private Worker occupied;
    private Block height;
    private Space[] neighbors;

    public Block getHeight(){
        return height;
    }

    public Worker getWorker(){
        return occupied;
    }
}
