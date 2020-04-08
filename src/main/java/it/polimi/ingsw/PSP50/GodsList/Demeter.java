package it.polimi.ingsw.PSP50.GodsList;

import it.polimi.ingsw.PSP50.*;

import java.util.ArrayList;

public class Demeter extends God {
    private final Gods name = Gods.DEMETER;

    @Override
    public ArrayList<Space> power(Turn turn) {

        Player thisPlayer = turn.getPlayer();
        Worker thisWorker = thisPlayer.getSelectedWorker();
        ArrayList<Space> available_moves = new ArrayList<>(thisWorker.getBuildable());
        Space last_build = thisPlayer.getLastBuild();
        available_moves.remove(last_build); //Demeter cannot build where He already built.
        return available_moves;
    }
}
