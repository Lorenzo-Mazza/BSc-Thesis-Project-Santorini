package it.polimi.ingsw.PSP50.Model.GodsList;

import it.polimi.ingsw.PSP50.Model.*;

import java.util.ArrayList;

public class Athena extends God {
    public Athena(){
        super(GodsNames.ATHENA);
        availableSteps.add(Phase.MOVE);
        availableSteps.add(Phase.BUILD);
    }

    @Override
    public boolean Move(Player player, Space space) {
        player.setHasMovedUp(false);
        Block high = player.getSelectedWorker().getPosition().getHeight();
        if (high.getValue() < space.getHeight().getValue())
            player.setHasMovedUp(true);
        return super.Move(player, space);
    }
}
