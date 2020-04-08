package it.polimi.ingsw.PSP50;

public enum Block {
    EMPTY(0),
    LEVEL1(1),
    LEVEL2(2),
    LEVEL3(3),
    DOME(10);

    private final int value;

    private Block(int value) {
        this.value = value;
    }

    public int getValue() {
        return value;
    }
}
