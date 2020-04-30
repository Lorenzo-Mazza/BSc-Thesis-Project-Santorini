package it.polimi.ingsw.PSP50.Model.GodsList;

import it.polimi.ingsw.PSP50.Model.*;

import java.util.ArrayList;

/**
 * Artemis card
 */
public class Artemis extends God {

    /**
     * *Constructor*
     */
    public Artemis(){
        super(GodsNames.ARTEMIS);
        availableSteps.add(Phase.MOVE);
        availableSteps.add(Phase.BUILD);
        availableSteps.add(Phase.OPTIONALMOVE);
    }

    /**
     * Overrides the default getOptionalMove method to implement Artemis'power.
     * @param player The owner of the God card
     * @return an ArrayList of the available spaces where to perform an "Optional Move" action
     */
    @Override
    public ArrayList<Space> getOptionalMove(Player player) {
        Space LastMove = player.getLastMove();
        ArrayList<Space> availableMoves = super.getAvailableMove(player);
        //Artemis cannot move back on the place she already moved from.
        availableMoves.remove(LastMove);
        return (availableMoves);
    }
}

