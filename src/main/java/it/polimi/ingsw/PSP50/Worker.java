package it.polimi.ingsw.PSP50;

public class Worker {
    private Player owner;
    private Space position;

    public boolean move(Space movement){
        return true;
    }

    public void force(Space movement){

    }

    public void build(Space movement){

    }

    public Space getReachable(){
        return position;
    }

    public Space getBuildable(){
        return position;
    }
}
