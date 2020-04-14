package it.polimi.ingsw.PSP50.Model.GodsList;

import it.polimi.ingsw.PSP50.Model.*;

import java.util.ArrayList;

public class Artemis extends God {

    public Artemis(){
        super(GodsNames.ARTEMIS);
        availableSteps.add(Phase.MOVE);
        availableSteps.add(Phase.BUILD);
        availableSteps.add(Phase.OPTIONALMOVE);
    }

    @Override
    public ArrayList<Space> getOptionalMove(Player player) {
        Space LastMove = player.getLastMove();
        ArrayList<Space> available_moves = super.getAvailableMove(player);
        available_moves.remove(LastMove);  //Artemis cannot move back on the place she already moved from.
        return (available_moves);
    }
}

