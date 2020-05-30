package it.polimi.ingsw.PSP50.Model;

import java.io.Serializable;
import java.util.ArrayList;

/**
 *  Worker contains all of the worker parameters
 */
public class Worker implements Serializable {
    /**
     *  Reference to his owner
     */
    final private Player owner;

    final private int workerId;
    /**
     *  Reference to the position where the worker is on the board
     */
    private Space position;

    private Space lastPosition;

    /**
     * Constructor
     * @param owner Player owning this worker
     */
    public Worker(Player owner, int workerId) {
        this.owner = owner;
        this.workerId = workerId;
        this.position = null;
        this.lastPosition = null;
    }


    /**
     *
     * @return worker owner
     */
    public Player getOwner() {
        return owner;
    }

    public int getWorkerId() {
        return workerId;
    }

    public Space getLastPosition() {
        return lastPosition;
    }

    /**
     * Moves worker to a new space
     * @param movement The space the worker moves to
     */
    public void move(Space movement){
        this.lastPosition = this.position;
        this.position = movement;
    }

    /**
     * Occupies the position where the worker is been placed (used in set-up phase)
     * @param position Space where the worker has moved
     */
    public void setPosition(Space position) {
        this.position = position;
        this.position.setWorker(this);
    }

    /**
     * Builds a block following the game logic
     * @param space Space where the worker builds the block
     */
    public void build(Space space){
        // The method builds a Block without any check nor exception.
        Block nextHeight = space.getNextHeight();
        space.setHeight(nextHeight);
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
        ArrayList<Space> neighbors = this.position.getNeighbors();

        for (Space neighbor : neighbors) {
            if (((neighbor.getHeight().getValue() - this.position.getHeight().getValue()) > 1)
                    || (neighbor.isOccupied()))
                movable.remove(neighbor); //If the Space is not reachable (too high or occupied), take it out of the list.
            else if (this.getOwner().isPlayerBlocked()) {
                // if the player is blocked, he cannot move up
                if ((neighbor.getHeight().getValue() - this.position.getHeight().getValue()) > 0)
                    movable.remove(neighbor);
            }
        }
        return movable;
    }

    /**
     * Gets all the spaces where a normal worker can perform a build action from its position
     * @return an ArrayList of the spaces
     */
    public ArrayList<Space> getBuildable() {
        ArrayList<Space> buildable = new ArrayList<>(this.position.getNeighbors());
        ArrayList<Space> neighbors= this.position.getNeighbors();

        for(Space neighbor : neighbors)
        {
            if((neighbor.getHeight() == Block.DOME) || (neighbor.isOccupied()))
                 {buildable.remove(neighbor);} //If there is a Dome or another Player, take out that Space from the list.
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
        ArrayList<Space> neighbors= this.position.getNeighbors();

        for (Space neighbor : neighbors) {
            if (((neighbor.getHeight().getValue() - this.position.getHeight().getValue()) > 1) ||
                    ((neighbor.isOccupied()) &&
                            (neighbor.getWorker().getOwner() == this.getOwner())
                    ))
                movable.remove(neighbor); //If the Space is not reachable (too high or occupied), take it out of the list.
            else if (this.getOwner().isPlayerBlocked()) {
                // if the player is blocked, he cannot move up
                if ((neighbor.getHeight().getValue() - this.position.getHeight().getValue()) > 0)
                    movable.remove(neighbor);
            }
        }
        return movable;
    }
}
