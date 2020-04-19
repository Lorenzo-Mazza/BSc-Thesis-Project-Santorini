package it.polimi.ingsw.PSP50.Model.GodsList;

import it.polimi.ingsw.PSP50.Model.*;

import java.util.ArrayList;

public class Atlas extends God {
    public Atlas(){
        super(GodsNames.ATLAS);
        availableSteps.add(Phase.MOVE);
        availableSteps.add(Phase.BUILD);
    }

    @Override
    public ArrayList<Block> getAvailableBlock(Player player) {
        ArrayList<Block> list = super.getAvailableBlock(player);
        if (!list.contains(Block.DOME))
            {list.add(Block.DOME);}
        return list;
    }

}
