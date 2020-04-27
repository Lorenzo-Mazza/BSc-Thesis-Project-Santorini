package it.polimi.ingsw.PSP50.Model.GodsList;

import it.polimi.ingsw.PSP50.Model.*;

import java.util.ArrayList;

/**
 * *Description of class*
 */
public class Pan extends God {

    /**
     * *Constructor*
     */
    public Pan(){
        super(GodsNames.PAN);
        availableSteps.add(Phase.MOVE);
        availableSteps.add(Phase.BUILD);
    }
}
