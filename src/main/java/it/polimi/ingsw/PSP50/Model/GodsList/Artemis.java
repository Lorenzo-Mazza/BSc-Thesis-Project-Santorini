package it.polimi.ingsw.PSP50.Model.GodsList;

import it.polimi.ingsw.PSP50.Model.*;

import java.util.ArrayList;

/**
 * *Description of class*
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
     * *Description of method*
     * @param player a Player variable that is playing during this turn
     * @return an ArrayList of Space that indicate where player's worker can move
     */
    @Override
    public ArrayList<Space> getOptionalMove(Player player) {
        Space LastMove = player.getLastMove();
        ArrayList<Space> availableMoves = super.getAvailableMove(player);
        availableMoves.remove(LastMove);  //Artemis cannot move back on the place she already moved from.
        return (availableMoves);
    }
}

