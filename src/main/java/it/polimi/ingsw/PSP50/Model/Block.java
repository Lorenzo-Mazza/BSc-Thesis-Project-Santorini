package it.polimi.ingsw.PSP50.Model;

/**
 * *Description of class*
 */
public enum Block {
    /**
     * no build - value: 0
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
     * doom - value: 10
     */
    DOME(10);

    private final int value;

    /**
     * *Constructor*
     * @param value an integer that indicate constant enum's value
     */
    private Block(int value) {
        this.value = value;
    }

    /**
     * *Descriprion of method*
     * @return an integer that represents enum's value
     */
    public int getValue() {
        return value;
    }
}
