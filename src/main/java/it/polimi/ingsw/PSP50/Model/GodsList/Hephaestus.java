package it.polimi.ingsw.PSP50.Model.GodsList;

import it.polimi.ingsw.PSP50.Model.*;

import java.util.ArrayList;

public class Hephaestus extends God {

    public Hephaestus(){
        super(GodsNames.HEPHAESTUS);
        availableSteps.add(Phase.MOVE);
        availableSteps.add(Phase.BUILD);
        availableSteps.add(Phase.OPTIONALBUILD);
    }

    @Override
    public ArrayList<Space> getOptionalBuild(Player player) {
        ArrayList <Space> optionalBuild= super.getOptionalBuild(player); // it should be an empty list, check if it's empty in Tests
        Space lastBuild = player.getLastBuild();
        if ((lastBuild.getHeight() != Block.DOME) && (lastBuild.getHeight() != Block.LEVEL3))
        optionalBuild.add(lastBuild); //If you cannot build a Dome, the Space is available for Hephaestus's Power.
        return optionalBuild; //Check if it's empty or not in the Controller!
    }
}
