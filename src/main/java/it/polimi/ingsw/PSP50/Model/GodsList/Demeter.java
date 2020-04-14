package it.polimi.ingsw.PSP50.Model.GodsList;

import it.polimi.ingsw.PSP50.Model.*;

import java.util.ArrayList;

public class Demeter extends God {

    public Demeter(){
        super(GodsNames.DEMETER);
        availableSteps.add(Phase.MOVE);
        availableSteps.add(Phase.BUILD);
        availableSteps.add(Phase.OPTIONALBUILD);
    }

    @Override
    public ArrayList<Space> getOptionalBuild(Player player) {

        ArrayList<Space> availableBuild = super.getAvailableBuild(player);
        Space last_build = player.getLastBuild();
        availableBuild.remove(last_build); //Demeter cannot build where she already built.
        return availableBuild;
    }
}
