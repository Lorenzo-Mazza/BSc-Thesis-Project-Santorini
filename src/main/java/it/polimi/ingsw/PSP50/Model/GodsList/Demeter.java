package it.polimi.ingsw.PSP50.Model.GodsList;

import it.polimi.ingsw.PSP50.Model.*;

import java.util.ArrayList;

/**
 * Demeter card
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
     * Overrides the default getOptionalBuild method to implement Demeter's power.
     * @param player The owner of the God card
     * @return an ArrayList of the available spaces where to perform an "Optional Build" action
     */
    @Override
    public ArrayList<Space> getOptionalBuild(Player player) {
        ArrayList<Space> availableBuild = super.getAvailableBuild(player);
        Space lastBuild = player.getLastBuild();
        availableBuild.remove(lastBuild); //Demeter cannot build where she already built.
        return availableBuild;
    }
}
