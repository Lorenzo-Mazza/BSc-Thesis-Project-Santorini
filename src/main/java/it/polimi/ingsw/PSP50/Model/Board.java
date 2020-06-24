package it.polimi.ingsw.PSP50.Model;

import java.io.Serializable;

/**
 * Board represents the game board, made by an array of 5*5 spaces
 */
public class Board implements Serializable {

    private Space[][] spaces = new Space[5][5];

    /**
     * Constructor
     */
    public Board() {
        for (int row = 0; row < 5; row++){
            for (int column = 0; column < 5; column++)
                spaces[row][column] = new Space(row,column,this);
        }
    }

    /**
     *
     * @param xPosition an integer that indicates the X-coordinate on the board
     * @param yPosition an integer that indicates the Y-coordinate on the board
     * @return the board space identified by the coordinates
     */
    public Space getSpace(int xPosition, int yPosition) {
        return spaces[xPosition][yPosition];
    }

}
