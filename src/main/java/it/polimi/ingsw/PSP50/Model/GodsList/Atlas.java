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

}
