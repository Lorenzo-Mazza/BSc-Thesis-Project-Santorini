package it.polimi.ingsw.PSP50.GodsList;

import it.polimi.ingsw.PSP50.*;

import java.util.ArrayList;

public class Arthemis extends God {
    public final Gods name = Gods.ARTHEMIS;

    @Override
    public ArrayList<Space> power(Player player) {

        Worker thisWorker = player.getSelectedWorker();
        Space LastMove = player.getLastMove();
        ArrayList<Space> available_moves = new ArrayList<>(thisWorker.getMovable());
        available_moves.remove(LastMove);  //Arthemis cannot move back on the place she already moved from.
        return (available_moves);
        }

    }
