package it.polimi.ingsw.PSP50.Model;

/**
 * GameType determines the number of players in the game
 */
public enum GameType {
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
    private GameType(int size){
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
