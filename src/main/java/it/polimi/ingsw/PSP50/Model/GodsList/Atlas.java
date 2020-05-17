package it.polimi.ingsw.PSP50.Model.GodsList;

import it.polimi.ingsw.PSP50.Model.*;

import java.util.ArrayList;

/**
 * Atlas Card
 */
public class Atlas extends God {

    /**
     * *Constructor*
     */
    public Atlas(){
        super(GodsNames.ATLAS);
        availableSteps.add(Phase.MOVE);
        availableSteps.add(Phase.BUILD);
    }

    /**
     * Overrides the default getAvailableBlock method to implement Atlas'power.
     * @param player The owner of the God card
     * @return an ArrayList that contains the default available block plus a Dome
     */
    @Override
    public ArrayList<Block> getAvailableBlock(Player player) {
        ArrayList<Block> list = super.getAvailableBlock(player);
        if (!list.contains(Block.DOME))
            {list.add(Block.DOME);}
        return list;
    }

    /**
     * Overrides the default Build method to implement Atlas's power.
     * @param player The owner of the God card
     * @param space a Space variable that indicate where to build
     * @param piece a Block variable that indicate what to build
     * @return a boolean variable to report the outcome of the action
     */
    @Override
    public boolean build(Player player, Space space, Block piece)
    {
        space.setHeight(piece);
        // if the outcome is correct
        if (space.getHeight() == piece) {
            player.setHasBuilt(true);
            player.setLastBuild(space);
            return true;
        }
        // if the outcome is wrong
        else
            return false;
    }

}
