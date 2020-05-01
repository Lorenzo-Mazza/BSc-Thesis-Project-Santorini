package it.polimi.ingsw.PSP50.Model.GodsList;

import it.polimi.ingsw.PSP50.Model.*;

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
    public boolean move(Player player, Space space) {
        player.setHasMovedUp(false);
        Block currentPosition = player.getSelectedWorker().getPosition().getHeight();
        if (currentPosition.getValue() < space.getHeight().getValue())
        {
            player.setHasMovedUp(true);
            for (Player opponent : player.getOpponents())
            {
                opponent.setPlayerBlocked(true);
            }
        }

        return super.move(player, space);
    }
}
