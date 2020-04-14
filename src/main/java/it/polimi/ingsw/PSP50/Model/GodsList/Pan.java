package it.polimi.ingsw.PSP50.Model.GodsList;

import it.polimi.ingsw.PSP50.Model.*;

import java.util.ArrayList;

public class Pan extends God {
    public Pan(){
        super(GodsNames.PAN);
        availableSteps.add(Phase.MOVE);
        availableSteps.add(Phase.BUILD);
    }
}
