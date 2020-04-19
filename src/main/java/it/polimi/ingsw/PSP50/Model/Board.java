package it.polimi.ingsw.PSP50.Model;

public class Board {
    private Space[][] spaces = new Space[5][5];

    //Constructor
    public Board() {
        for (int row = 0; row < 5; row++){
            for (int column = 0; column < 5; column++)
                spaces[row][column] = new Space(row,column,this);
        }
    }

    public Space getSpace(int xPosition, int yPosition) {
        return spaces[xPosition][yPosition];
    }

}
