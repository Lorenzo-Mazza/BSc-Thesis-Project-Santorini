package it.polimi.ingsw.PSP50.Model;

public enum GameType {
    TWOPLAYERS(2),
    THREEPLAYER(3);

    private final int size;

    private GameType(int size){
        this.size = size;
    }

    public int getSize(){
        return this.size;
    }
}
