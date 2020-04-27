package it.polimi.ingsw.PSP50.Model;

/**
 * *Description of class*
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
     * *Constructor*
     * @param size an integer to determinate size of enum
     */
    private GameType(int size){
        this.size = size;
    }

    /**
     *
     * @return an integer of enum
     */
    public int getSize(){
        return this.size;
    }
}
