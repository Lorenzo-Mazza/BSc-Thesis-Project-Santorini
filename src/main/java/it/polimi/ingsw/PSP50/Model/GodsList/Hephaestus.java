package it.polimi.ingsw.PSP50.Model.GodsList;

import it.polimi.ingsw.PSP50.Model.*;

import java.util.ArrayList;

/**
 * Hephaestus card
 */
public class Hephaestus extends God {

    /**
     * *Constructor*
     */
    public Hephaestus(){
        super(GodsNames.HEPHAESTUS);
        availableSteps.add(Phase.MOVE);
        availableSteps.add(Phase.BUILD);
        availableSteps.add(Phase.OPTIONALBUILD);
    }

    /**
     * Overrides the default getOptionalBuild method to implement Hephaestus'power.
     * @param player The owner of the God card
     * @return an ArrayList of the available spaces where to perform an "Optional Build" action
     */
    @Override
    public ArrayList<Space> getOptionalBuild(Player player) {
        ArrayList <Space> optionalBuild= super.getOptionalBuild(player);
        Space lastBuild = player.getLastBuild();
        //If you cannot build a Dome, the Space is available for Hephaestus's Power.
        if ((lastBuild.getHeight() != Block.DOME) && (lastBuild.getHeight() != Block.LEVEL3))
            optionalBuild.add(lastBuild);
        return optionalBuild; //Check if it's empty or not in the Controller!
    }
}
