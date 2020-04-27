package it.polimi.ingsw.PSP50.Model.GodsList;

import it.polimi.ingsw.PSP50.Model.*;

import java.util.ArrayList;

/**
 * *Description of class*
 */
public class Demeter extends God {

    /**
     * *Constructor*
     */
    public Demeter(){
        super(GodsNames.DEMETER);
        availableSteps.add(Phase.MOVE);
        availableSteps.add(Phase.BUILD);
        availableSteps.add(Phase.OPTIONALBUILD);
    }

    /**
     * *Description of method*
     * @param player a Player variable that is playing during this turn
     * @return an ArrayList of Space which contains the allowed Spaces where worker can build
     */
    @Override
    public ArrayList<Space> getOptionalBuild(Player player) {
        ArrayList<Space> availableBuild = super.getAvailableBuild(player);
        Space last_build = player.getLastBuild();
        availableBuild.remove(last_build); //Demeter cannot build where she already built.
        return availableBuild;
    }
}
