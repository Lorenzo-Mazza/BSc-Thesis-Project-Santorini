package it.polimi.ingsw.PSP50.Model;

/**
 * Constant Enumeration that contains the phases of the game turn
 */
public enum Phase {
    /**
     * Move phase
     */
    MOVE,
    /**
     * Build phase
     */
    BUILD,
    /**
     * there may be an optional move phase
     */
    OPTIONALMOVE,
    /**
     * there may be an optional build phase
     */
    OPTIONALBUILD
}
