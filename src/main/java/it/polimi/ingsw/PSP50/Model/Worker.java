package it.polimi.ingsw.PSP50.Model;

import it.polimi.ingsw.PSP50.Model.Block;
import it.polimi.ingsw.PSP50.Model.Player;
import it.polimi.ingsw.PSP50.Model.Space;

import java.util.ArrayList;

public class Worker {
    private Player owner;
    private Space position;

    public Player getOwner() {
        return owner;
    }

    public void setOwner(Player owner) {
        this.owner = owner;
    }

    public void move(Space movement){
        this.position= movement;
    }

    public void force(Space movement){  // do we really need this?


    }

    public void build(Space movement){          // The method builds a Block without any check nor exception.
                                                // Exceptions should be thrown by the Controller when a Block is not available.

       int height_number= movement.getHeight().getValue() +1; //get current level of the Space and add 1.
       switch (height_number)
       {
           case 1:
               movement.setHeight(Block.LEVEL1);
               break;

           case 2:
               movement.setHeight(Block.LEVEL2);
               break;
           case 3:
               movement.setHeight(Block.LEVEL3);
               break;
           case 4:
               movement.setHeight(Block.DOME);
               break;
       }


    }

    public Space getPosition() {
        return position;
    }

    public Space getNeighbors(){  //DO WE REALLY NEED IT?
        return position;
    }

    public ArrayList<Space> getMovable() {
        ArrayList<Space> movable = new ArrayList<>(this.position.getNeighbors());

        for(int index = 0; index < movable.size(); index++)
        {
            if(((movable.get(index).getHeight().getValue() - this.position.getHeight().getValue()) > 1)
                || (movable.get(index).isOccupied()))

                 { movable.remove(index);} //If the Space is not reachable (too high or occupied), take it out of the list.
        }

        return movable;
    }

    public ArrayList<Space> getBuildable(){
        ArrayList<Space> buildable = new ArrayList<>(this.position.getNeighbors());

        for(int index = 0; index < buildable.size(); index++)
        {
            if((buildable.get(index).getHeight() == Block.DOME) || (buildable.get(index).isOccupied()))
                 {buildable.remove(index);} //If there is a Dome or another Player, take out that Space from the list.
        }

        return buildable;
    }
}
