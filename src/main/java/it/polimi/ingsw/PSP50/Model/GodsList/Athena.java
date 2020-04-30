package it.polimi.ingsw.PSP50.Model.GodsList;

import it.polimi.ingsw.PSP50.Model.*;

import java.util.ArrayList;

/**
 * Athena card
 */
public class Athena extends God {

    /**
     * *Constructor*
     */
    public Athena(){
        super(GodsNames.ATHENA);
        availableSteps.add(Phase.MOVE);
        availableSteps.add(Phase.BUILD);
    }

    /**
     * Overrides the default Move method to implement Athena's power.
     * @param player The owner of the God card
     * @param space a Space variable that indicate where to move
     * @return a boolean variable to report the outcome of the action
     */
    @Override
    public boolean Move(Player player, Space space) {
        player.setHasMovedUp(false);
        Block high = player.getSelectedWorker().getPosition().getHeight();
        if (high.getValue() < space.getHeight().getValue())
            player.setHasMovedUp(true);
        return super.Move(player, space);
    }
}
