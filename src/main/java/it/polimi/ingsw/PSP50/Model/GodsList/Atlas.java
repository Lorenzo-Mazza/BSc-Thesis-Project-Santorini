package it.polimi.ingsw.PSP50.Model.GodsList;

import it.polimi.ingsw.PSP50.Model.*;

import java.util.ArrayList;

/**
 * *Description of class*
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
     * *Description of method*
     * @param player a Player variable that is playing during this turn
     * @return an ArrayList of Block which contains the allowed Blocks that can be build
     */
    @Override
    public ArrayList<Block> getAvailableBlock(Player player) {
        ArrayList<Block> list = super.getAvailableBlock(player);
        if (!list.contains(Block.DOME))
            {list.add(Block.DOME);}
        return list;
    }

}
