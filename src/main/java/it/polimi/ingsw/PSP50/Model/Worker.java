package it.polimi.ingsw.PSP50.Model;

import java.util.ArrayList;

/**
 * *Description of class*
 */
public class Worker {
    private Player owner;
    private Space position;

    /**
     * *Constructor*
     * @param owner a Player variable that indicate worker's owner
     */
    public Worker(Player owner) {
        this.owner = owner;
        this.position = null;
    }

    /**
     * *Description of method*
     * @return owner a Player variable
     */
    public Player getOwner() {
        return owner;
    }

    /**
     * *Description of method*
     * @param owner is Player variable
     */
    public void setOwner(Player owner) {
        this.owner = owner;
    }

    /**
     * *Description of method*
     * @param movement a Space variable
     */
    public void move(Space movement){
        this.position = movement;
    }

    /**
     * *Description of method*
     * @param position a Space variable
     */
    public void setPosition(Space position) {
        this.position = position;
        this.position.setWorker(this);
    }

    /**
     * *Description of method*
     * @param movement a Space variable that indicate where worker have to build
     */
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

    /**
     * *Description of method*
     * @return position a Space variable
     */
    public Space getPosition() {
        return position;
    }

    /**
     * *Description of method*
     * @return an ArrayList of Space that contains where Worker can normally move
     */
    public ArrayList<Space> getMovable() {
        ArrayList<Space> movable = new ArrayList<>(this.position.getNeighbors());

        for(int index = 0; index < movable.size(); index++)
        {
            if(((movable.get(index).getHeight().getValue() - this.position.getHeight().getValue()) > 1)
                || (movable.get(index).isOccupied()))

                 { movable.remove(index); } //If the Space is not reachable (too high or occupied), take it out of the list.
        }
        return movable;
    }

    /**
     * *Description of method*
     * @return an ArrayList of Space that contains where Worker can build
     */
    public ArrayList<Space> getBuildable() {
        ArrayList<Space> buildable = new ArrayList<>(this.position.getNeighbors());

        for(int index = 0; index < buildable.size(); index++)
        {
            if((buildable.get(index).getHeight() == Block.DOME) || (buildable.get(index).isOccupied()))
                 {buildable.remove(index);} //If there is a Dome or another Player, take out that Space from the list.
        }

        return buildable;
    }

    /**
     * *Description of method*
     * @return an ArrayList of Space that contains where Worker can move even there is an opponent worker
     */
    public ArrayList<Space> getMovableWithWorkers() {
        ArrayList<Space> movable = new ArrayList<>(this.position.getNeighbors());

        for(int index = 0; index < movable.size(); index++)
        {
            if(((movable.get(index).getHeight().getValue() - this.position.getHeight().getValue()) > 1) ||
                    (movable.get(index).getWorker().getOwner() == this.getOwner()))
                movable.remove(index); //If the Space is not reachable (too high or occupied), take it out of the list.
        }
        return movable;
    }
}
