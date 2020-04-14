package it.polimi.ingsw.PSP50.Model.GodsList;

import it.polimi.ingsw.PSP50.Model.*;

import java.util.ArrayList;

public class Athena extends God {
    public Athena(){
        super(GodsNames.ATHENA);
        availableSteps.add(Phase.MOVE);
        availableSteps.add(Phase.BUILD);
    }

}
