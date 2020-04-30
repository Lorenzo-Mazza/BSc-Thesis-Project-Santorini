package it.polimi.ingsw.PSP50.Model;

/**
 * Block is an Enum class that contains all the types of blocks that can be built in a space
 */
public enum Block {
    /**
     * ground floor - value: 0
     */
    EMPTY(0),
    /**
     * first floor - value: 1
     */
    LEVEL1(1),
    /**
     * second floor - value: 2
     */
    LEVEL2(2),
    /**
     * third floor - value: 3
     */
    LEVEL3(3),
    /**
     * dome - value: 10 (the space is unavailable)
     */
    DOME(10);

    private final int value;

    /**
     * *Constructor*
     * @param value an integer that indicate the enum constant value
     */
    private Block(int value) {
        this.value = value;
    }

    /**
     *
     * @return an integer that represents the enum constant value
     */
    public int getValue() {
        return value;
    }
}
