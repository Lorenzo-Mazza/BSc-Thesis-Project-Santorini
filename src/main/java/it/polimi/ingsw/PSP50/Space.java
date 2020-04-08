package it.polimi.ingsw.PSP50;

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
        occupied= null;
        this.board= board;
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

    public void setHeight(Block height) {this.height = height;}

    public Worker getWorker() {
        return occupied;
    }

    public boolean isOccupied() {
        if(this.occupied == null)
            return false;
        return true;
    }



    public ArrayList<Space> getNeighbors() {
        ArrayList<Space> neighbors = new ArrayList<>();
        int index = 0;
        for(int row = (this.getXPosition() - 1); row < (this.getXPosition() + 1); row++)
        {
            for(int column = (this.getYPosition() - 1); column < (this.getYPosition() + 1); column++)
            {
                if (row != this.getXPosition() || column != this.getYPosition()) { // removing the space itself
                    neighbors.add(Board.getSpace(row, column));
                    index++;
                }
            }
        }

        return neighbors;
    }
}
