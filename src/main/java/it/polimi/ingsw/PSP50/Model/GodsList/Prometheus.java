package it.polimi.ingsw.PSP50.Model.GodsList;

import it.polimi.ingsw.PSP50.Model.*;

import java.util.ArrayList;

public class Prometheus extends God {
    public Prometheus(){
        super(GodsNames.PROMETHEUS);
        availableSteps.add(Phase.OPTIONALBUILD);
        availableSteps.add(Phase.MOVE);
        availableSteps.add(Phase.BUILD);
    }

    // if the player has already built in the turn, it makes available only the spaces that
    // don't let the player move up.
    @Override
    public ArrayList<Space> getAvailableMove(Player player) {
        if (player.getHasBuilt()) { //if the player has already built
            Worker worker = player.getSelectedWorker();
            int level = worker.getPosition().getHeight().getValue(); // level where the worker is at right now.
            ArrayList<Space> neighbors = new ArrayList<>(worker.getPosition().getNeighbors());
            ArrayList<Space> available = new ArrayList<>();

            for (int index = 0; index < neighbors.size(); index++) {
                if (level >= neighbors.get(index).getHeight().getValue())
                    available.add(neighbors.get(index));  // if the neighbor space is not higher than level , it's available.
            }
            return available;
        }
        else {
            return (super.getAvailableMove(player)); // else, normal move mechanism.
        }
    }

}
