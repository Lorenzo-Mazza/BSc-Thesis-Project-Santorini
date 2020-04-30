package it.polimi.ingsw.PSP50.Model;

import java.util.ArrayList;

/**
 *  Worker contains all of the worker parameters
 */
public class Worker {
    /**
     *  Reference to his owner
     */
    final private Player owner;
    /**
     *  Reference to the position where the worker is on the board
     */
    private Space position;

    /**
     * Constructor
     * @param owner Player owning this worker
     */
    public Worker(Player owner) {
        this.owner = owner;
        this.position = null;
    }

    /**
     *
     * @return worker owner
     */
    public Player getOwner() {
        return owner;
    }

    /**
     *  We don't need it
     * @param owner Player owning the worker
     */
   // public void setOwner(Player owner) {
   //     this.owner = owner;
   // }

    /**
     * Moves worker to a new space
     * @param movement The space the worker moves to
     */
    public void move(Space movement){
        this.position = movement;
    }

    /**
     * Occupies the position where the worker has moved
     * @param position Space where the worker has moved
     */
    public void setPosition(Space position) {
        this.position = position;
        this.position.setWorker(this);
    }

    /**
     * Builds a block following the game logic
     * @param movement Space where the worker builds the block
     */
    public void build(Space movement){
        // The method builds a Block without any check nor exception.
        // Exceptions should be thrown by the Controller when a Block is not available.

        //get current level of the Space and add 1.
       int height_number= movement.getHeight().getValue() +1;
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
     *
     * @return position where the worker is
     */
    public Space getPosition() {
        return position;
    }

    /**
     * Gets all the spaces where a normal worker can perform a move action from its position
     * @return an ArrayList of the spaces
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
     * Gets all the spaces where a normal worker can perform a build action from its position
     * @return an ArrayList of the spaces
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
     * Used only to manage some Gods powers
     * Gets all the spaces where a worker can perform a move action, even in the case they're already occupied by an opponent
     * @return an ArrayList of the spaces
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
