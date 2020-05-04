package it.polimi.ingsw.PSP50.Model;

/**
 * Colors of the workers on the board
 */
public enum Color {
    RED("Red", "\u001b[31m"),
    GREEN("Green", "\u001b[32m"),
    BLUE("Blue", "\u001b[34m");

    /**
     * Color name
     */
    private String name;

    /**
     * Terminal escape sequence
     */
    private String sequence;

    /**
     * Constructor
     * @param name Color name
     * @param sequence Terminal escape sequence
     */
    Color(String name, String sequence) {
        this.name = name;
        this.sequence = sequence;
    }

    /**
     * @return Color name
     */
    public String getName() {
        return name;
    }

    /**
     * @return Terminal escape sequence
     */
    public String getSequence() {
        return sequence;
    }
}
