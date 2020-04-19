package it.polimi.ingsw.PSP50.Model;

import java.util.ArrayList;

public class Space {
    private final int xPosition;
    private final int yPosition;
    private Worker occupied;
    private Block height;
    private Board board;

    public Space(int xPosition, int yPosition, Board board) { //Constructor that sets immediately the position of the space on the board.
        this.xPosition = xPosition;
        this.yPosition = yPosition;
        height =  Block.EMPTY;
        occupied = null;
        this.board = board;
    }

    public int getXPosition() {
        return this.xPosition;
    }

    public int getYPosition() {
        return this.yPosition;
    }

    public int[] getCoordinates() {
        int[] coordinates = new int[2];
        coordinates[0] = this.getXPosition();
        coordinates[1] = this.getYPosition();
        return coordinates;
    }

    public Block getHeight() {
        return height;
    }

    public void setHeight(Block height) { this.height = height; }

    public Worker getWorker() {
        return occupied;
    }

    public void setWorker(Worker worker) { this.occupied = worker; }

    public boolean isOccupied() {
        if(this.occupied == null)
            return false;
        return true;
    }

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

        for (; row_inf < row_sup; row_inf++)
        {
            for (; column_inf < column_sup; column_inf++)
            {
                if (row_inf != this.getXPosition() || column_inf != this.getYPosition())
                    neighbors.add(board.getSpace(row_inf, column_inf));
            }
        }
        return neighbors;
    }

    public boolean thereIsNext(int movementOnX, int movementOnY) {
        int[] coordinates = {this.xPosition + movementOnX, this.yPosition + movementOnY};
        if ((coordinates[0] < 0 || coordinates[0] > 4) || (coordinates[1] < 0 || coordinates[1] > 4))
            return false;
        return true;
    }

    public Space getNext(int movementOnX, int movementOnY) {
        if (!thereIsNext(this.xPosition + movementOnX, this.yPosition + movementOnY))
            return null;
        else
            return board.getSpace(this.xPosition + movementOnX, this.yPosition + movementOnY);
    }

    public int[] getCoordinatesFromSpaces(Space nextSpace) {
        int[] movement = new int[2];
        movement[0] = nextSpace.getXPosition() - this.xPosition;
        movement[1] = nextSpace.getYPosition() - this.yPosition;
        return movement;
    }
}
