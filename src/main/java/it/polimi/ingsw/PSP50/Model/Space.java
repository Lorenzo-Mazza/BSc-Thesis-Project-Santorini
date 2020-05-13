package it.polimi.ingsw.PSP50.Model;

import java.io.Serializable;
import java.util.ArrayList;

/**
 * Space contains all the space data and a reference to the board it belongs to
 */
public class Space implements Serializable {

    /**
     * X-coordinate on the board
     */
    private final int xPosition;
    /**
     * Y-coordinate on the board
     */
    private final int yPosition;
    /**
     * Worker that's currently occupying this space
     */
    private Worker occupied;
    /**
     * Space height in terms of Blocks that have been build on it
     */
    private Block height;
    /**
     * Reference to the space board
     */
    private Board board;


    /**
     * Constructor
     * @param xPosition  X-coordinate on the board
     * @param yPosition  Y-coordinate on the board
     * @param board The board that the space belongs to
     */
    public Space(int xPosition, int yPosition, Board board) {

        //the Constructor sets immediately the position of the space on the board.
        this.xPosition = xPosition;
        this.yPosition = yPosition;
        height =  Block.EMPTY;
        occupied = null;
        this.board = board;
    }


    /**
     *
     * @return the space X-coordinate on the board
     */
    public int getXPosition() {
        return this.xPosition;
    }

    /**
     *
     * @return the space Y-coordinate on the board
     */
    public int getYPosition() {
        return this.yPosition;
    }

    /**
     *
     * @return an Array that contains both the space coordinates
     */
    public int[] getCoordinates() {
        int[] coordinates = new int[2];
        coordinates[0] = this.getXPosition();
        coordinates[1] = this.getYPosition();
        return coordinates;
    }

    /**
     *
     * @return space height. Possible values are:
     * - LEVEL 0
     * - LEVEL 1
     * - LEVEL 2
     * - LEVEL 3
     * - DOME
     */
    public Block getHeight() {
        return height;
    }

    public Block getNextHeight() {
        int height = this.getHeight().getValue();
        switch (height) {
            case 0:
                return Block.LEVEL1;
            case 1:
                return Block.LEVEL2;
            case 2:
                return Block.LEVEL3;
            default:
                return Block.DOME;
        }
    }

    /**
     *
     * @param height Refers to the space height
     */
    public void setHeight(Block height) { this.height = height; }

    /**
     *
     * @return the worker that currently occupies the space
     */
    public Worker getWorker() {
        return occupied;
    }

    /**
     *
     * @param worker Indicates that the space is now occupied by this worker
     */
    public void setWorker(Worker worker) { this.occupied = worker; }

    /**
     *
     * @return a boolean: true if the space is occupied, false otherwise
     */
    public boolean isOccupied() {
        if(this.occupied == null)
            return false;
        return true;
    }

    /**
     *  Used to get all the neighboring spaces
     * @return an ArrayList of the neighboring spaces
     */
    public ArrayList<Space> getNeighbors() {
        ArrayList<Space> neighbors = new ArrayList<>();
        int row_inf, column_inf;
        int row_sup, column_sup;

        if (this.getXPosition() == 0) {
            row_inf = this.getXPosition();
            row_sup = this.getXPosition() + 1;
        }
        else if (this.getXPosition() == 4) {
            row_inf = this.getXPosition() - 1;
            row_sup = this.getXPosition();
        }
        else {
            row_inf = this.getXPosition() - 1;
            row_sup = this.getYPosition() + 1;
        }

        if (this.getYPosition() == 0) {
            column_inf = this.getYPosition();
            column_sup = this.getYPosition() + 1;
        }
        else if (this.getYPosition() == 4) {
            column_inf = this.getYPosition() - 1;
            column_sup = this.getYPosition();
        }
        else {
            column_inf = this.getYPosition() - 1;
            column_sup = this.getYPosition() + 1;
        }

        for (; row_inf <= row_sup; row_inf++)
        {
            for (int index= column_inf; index <= column_sup; index++)
            {
                if (row_inf != this.getXPosition() || index != this.getYPosition())
                    neighbors.add(board.getSpace(row_inf, index));
            }
        }
        return neighbors;
    }

    /**
     * Checks if there's a neighboring space in the direction (X,Y) from the actual space.
     * Direction (1,0) is East, (1,1) North-East, (0,1) North, (-1,1) North-West, (-1,0) West, (0,-1) South, (-1,-1) South-West
     * @param movementOnX X-coordinate of the movement
     * @param movementOnY Y-coordinate of the movement
     * @return a boolean: true if a space exists and it's not blocked permanently by a DOME, false otherwise
     */
    public boolean thereIsNext(int movementOnX, int movementOnY) {
        int[] coordinates = {this.xPosition + movementOnX, this.yPosition + movementOnY};
        if ((coordinates[0] < 0 || coordinates[0] > 4) || (coordinates[1] < 0 || coordinates[1] > 4) ||
                (board.getSpace(coordinates[0],coordinates[1]).getHeight()==Block.DOME))
            return false;
        return true;
    }

    /**
     *
     * @param movementOnX X-coordinate of the movement
     * @param movementOnY Y-coordinate of the movement
     * @return the neighboring space in the direction (X,Y) from the actual space
     */
    public Space getNext(int movementOnX, int movementOnY) {
        if (!thereIsNext(this.xPosition + movementOnX, this.yPosition + movementOnY))
            return null;
        else
            return board.getSpace(this.xPosition + movementOnX, this.yPosition + movementOnY);
    }

    /**
     * gets the (X,Y) direction needed to reach nextSpace from this space
     * @param nextSpace Space where to move
     * @return the difference of coordinates between nextSpace and this space
     */
    public int[] getCoordinatesFromSpaces(Space nextSpace) {
        int[] movement = new int[2];
        movement[0] = nextSpace.getXPosition() - this.xPosition;
        movement[1] = nextSpace.getYPosition() - this.yPosition;
        return movement;
    }
}
