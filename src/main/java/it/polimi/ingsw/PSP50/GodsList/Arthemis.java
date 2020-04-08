package it.polimi.ingsw.PSP50.GodsList;

import it.polimi.ingsw.PSP50.*;

import java.util.ArrayList;
import java.util.Arrays;

public class Arthemis extends God {
    public final Gods name = Gods.ARTHEMIS;

    @Override
    public ArrayList<Space> power(Turn turn) {

        Player thisPlayer = turn.getPlayer();
        Worker thisWorker = thisPlayer.getSelectedWorker();
        Space LastMove = thisPlayer.getLastMove();
        ArrayList<Space> available_moves = new ArrayList<>(thisWorker.getMovable());
        available_moves.remove(LastMove);  //Arthemis cannot move back on the place she already moved from.
        return (available_moves);
        }

    }
