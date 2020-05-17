package it.polimi.ingsw.PSP50.Model.GodsList;

import it.polimi.ingsw.PSP50.Model.*;

import java.util.ArrayList;

/**
 * Prometheus card
 */
public class Prometheus extends God {

    /**
     * *Constructor*
     */
    public Prometheus(){
        super(GodsNames.PROMETHEUS);
        availableSteps.add(Phase.OPTIONALBUILD);
        availableSteps.add(Phase.MOVE);
        availableSteps.add(Phase.BUILD);
    }

    /**
     * Overrides the default getAvailableMove method to implement Prometheus' power.
     * @param player The owner of the God card
     * @return an ArrayList of the available spaces where to perform a "Move" action
     */
    @Override
    public ArrayList<Space> getAvailableMove(Player player) {
        //if the player has already built.
        if (player.getHasBuilt()) {
            Worker worker = player.getSelectedWorker();
            // level is the height where the worker is at right now.
            int level = worker.getPosition().getHeight().getValue();
            ArrayList<Space> neighbors = new ArrayList<>(worker.getMovable());
            ArrayList<Space> available = new ArrayList<>();

            for (int index = 0; index < neighbors.size(); index++) {
                if (level >= neighbors.get(index).getHeight().getValue())
                    // if the neighbor space is not higher than level, it's available.
                    available.add(neighbors.get(index));
            }
            player.setHasBuilt(false);
            return available;
        }
        // else, normal move mechanism.
        else return (super.getAvailableMove(player));
    }

    /**
     * Overrides the default getOptionalBuild method to implement Prometheus's power.
     * @param player The owner of the God card
     * @return an ArrayList of the available spaces where to perform an "Optional Build" action
     */
    @Override
    public ArrayList<Space> getOptionalBuild(Player player) {
        return super.getAvailableBuild(player);
    }



}
