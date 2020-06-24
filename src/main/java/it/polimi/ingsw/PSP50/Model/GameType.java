package it.polimi.ingsw.PSP50.Model;

import java.io.Serializable;

/**
 * GameType determines the number of players in the game
 */
public enum GameType implements Serializable {
    /**
     * 2 players
     */
    TWOPLAYERS(2),
    /**
     * 3 players
     */
    THREEPLAYERS(3);

    private final int size;

    /**
     * Constructor
     * @param size an integer to determine the number of players in the game
     */
    GameType(int size){
        this.size = size;
    }

    /**
     * Gets the number of players in the game
     * @return the size of the enum constant
     */
    public int getSize(){
        return this.size;
    }
}
