package it.polimi.ingsw.PSP50.Model.GodsList;

import it.polimi.ingsw.PSP50.Model.*;

import java.util.ArrayList;

public class Prometheus extends God {
    private final Gods name = Gods.PROMETEUS;

    // Power is called after the player chooses to build before moving. It makes available only the spaces that
    // don't let the player move up.
    @Override
    public ArrayList<Space> power(Player player) {

        Worker worker= player.getSelectedWorker();
        int level = worker.getPosition().getHeight().getValue();
        ArrayList<Space> neighbors= new ArrayList<>(worker.getPosition().getNeighbors());
        ArrayList<Space> available = new ArrayList<>();

        for (int index=0; index< neighbors.size(); index++){
            if (level<=neighbors.get(index).getHeight().getValue())
                available.add(neighbors.get(index));
        }
        return available;
    }
}
