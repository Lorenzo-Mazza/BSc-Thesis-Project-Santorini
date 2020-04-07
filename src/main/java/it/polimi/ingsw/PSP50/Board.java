package it.polimi.ingsw.PSP50;

public class Board {
    private Space[][] spaces;

    public void setSpaces(Space[][] spaces) {
        this.spaces = spaces;
    }

    public Space getSpace(int xPosition, int yPosition) {
        return spaces[xPosition][yPosition];
    }
}
