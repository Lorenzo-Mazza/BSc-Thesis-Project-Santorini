package it.polimi.ingsw.PSP50.Model.GodsList;

import it.polimi.ingsw.PSP50.Model.*;

import java.util.ArrayList;

/**
 * *Description of class*
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
     * *Description of method*
     * @param player a Player variable that is playing during this turn
     * @param space a Space variable that indicate where to move
     * @return a boolean variable just for confirmation
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
