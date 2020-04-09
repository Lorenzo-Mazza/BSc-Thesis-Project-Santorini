package it.polimi.ingsw.PSP50.GodsList;

import it.polimi.ingsw.PSP50.*;

import java.util.ArrayList;

public class Atlas extends God {
    private final Gods name = Gods.ATLAS;

    @Override
    public ArrayList<Space> power(Player player) {

        Worker thisWorker = player.getSelectedWorker();
        ArrayList<Space> available_moves = new ArrayList<Space>(thisWorker.getBuildable());
        return available_moves;
    }


    public void buildDome (Space target, Turn turn) {
        target.setHeight(Block.DOME);

    }
}
