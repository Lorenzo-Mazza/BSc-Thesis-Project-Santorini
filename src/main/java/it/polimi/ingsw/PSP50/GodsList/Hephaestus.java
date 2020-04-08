package it.polimi.ingsw.PSP50.GodsList;

import it.polimi.ingsw.PSP50.*;

import java.util.ArrayList;

public class Hephaestus extends God {
    private final Gods name = Gods.HEPHAESTUS;

    @Override
    public ArrayList<Space> power(Turn turn) {

        Player thisPlayer = turn.getPlayer();
        Worker thisWorker = thisPlayer.getSelectedWorker();
        Space last_build = thisPlayer.getLastBuild();
        ArrayList<Space> available_moves = new ArrayList<>();
        if (last_build.getHeight()!=Block.DOME && last_build.getHeight()!=Block.LEVEL3 )
        available_moves.add(last_build); //If you cannot build a Dome, the Space is available for Hephaestus's Power.
        return available_moves; //Check if it's empty in the Controller!
    }
}
