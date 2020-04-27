package it.polimi.ingsw.PSP50.Model;

import java.util.ArrayList;

/**
 * *Description of class*
 */
public class Space {
    private final int xPosition;
    private final int yPosition;
    private Worker occupied;
    private Block height;
    private Board board;

    /**
     * *Constructor*
     * @param xPosition an integer variable that indicate x-coordinate
     * @param yPosition an integer variable that indicate y-coordinate
     * @param board a Board variable so every space are in the same Board
     */
    public Space(int xPosition, int yPosition, Board board) { //Constructor that sets immediately the position of the space on the board.
        this.xPosition = xPosition;
        this.yPosition = yPosition;
        height =  Block.EMPTY;
        occupied = null;
        this.board = board;
    }

    /**
     * *Description of method*
     * @return an integer variable of x-coordinate
     */
    public int getXPosition() {
        return this.xPosition;
    }

    /**
     * *Description of method*
     * @return an integer variable of y-coordinate
     */
    public int getYPosition() {
        return this.yPosition;
    }

    /**
     * *Description of method*
     * @return an Array that contains x-coordinate and y-coordinate
     */
    public int[] getCoordinates() {
        int[] coordinates = new int[2];
        coordinates[0] = this.getXPosition();
        coordinates[1] = this.getYPosition();
        return coordinates;
    }

    /**
     * *Description of method*
     * @return an enum constant that indicate what type of block
     */
    public Block getHeight() {
        return height;
    }

    /**
     * *Description of method*
     * @param height to set an enum constant on this space
     */
    public void setHeight(Block height) { this.height = height; }

    /**
     * *Description of method*
     * @return a Worker variable
     */
    public Worker getWorker() {
        return occupied;
    }

    /**
     * *Description of method*
     * @param worker a Worker variable
     */
    public void setWorker(Worker worker) { this.occupied = worker; }

    /**
     * *Description of method*
     * @return a boolean variable
     */
    public boolean isOccupied() {
        if(this.occupied == null)
            return false;
        return true;
    }

    /**
     * *Description of method*
     * @return an ArrayList of Space that contains all neighbors of this space
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
     * *Description of method*
     * @param movementOnX an integer variable to add at x-coordinate
     * @param movementOnY an integer variable to add at y-coordinate
     * @return a boolean variable
     */
    public boolean thereIsNext(int movementOnX, int movementOnY) {
        int[] coordinates = {this.xPosition + movementOnX, this.yPosition + movementOnY};
        if ((coordinates[0] < 0 || coordinates[0] > 4) || (coordinates[1] < 0 || coordinates[1] > 4) ||
                (board.getSpace(coordinates[0],coordinates[1]).getHeight()==Block.DOME))
            return false;
        return true;
    }

    /**
     * *Description of method*
     * @param movementOnX an integer variable to add at x-coordinate
     * @param movementOnY an integer variable to add at y-coordinate
     * @return a Space variable
     */
    public Space getNext(int movementOnX, int movementOnY) {
        if (!thereIsNext(this.xPosition + movementOnX, this.yPosition + movementOnY))
            return null;
        else
            return board.getSpace(this.xPosition + movementOnX, this.yPosition + movementOnY);
    }

    /**
     * *Description of method*
     * @param nextSpace a Space variable
     * @return an array of integer that contains the difference of coordinates between the spaces
     */
    public int[] getCoordinatesFromSpaces(Space nextSpace) {
        int[] movement = new int[2];
        movement[0] = nextSpace.getXPosition() - this.xPosition;
        movement[1] = nextSpace.getYPosition() - this.yPosition;
        return movement;
    }
}
