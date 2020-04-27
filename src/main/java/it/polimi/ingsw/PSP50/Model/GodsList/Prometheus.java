package it.polimi.ingsw.PSP50.Model.GodsList;

import it.polimi.ingsw.PSP50.Model.*;

import java.util.ArrayList;

/**
 * *Description of class*
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
     * *Description of method*
     * @param player a Player variable that is playing during this turn
     * @return an ArrayList of Space that indicate where player's worker can move with some limitations
     */
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
            player.setHasBuilt(false);
            return available;
        }
        else return (super.getAvailableMove(player)); // else, normal move mechanism.
    }

    /**
     * *Description of method*
     * @param player a Player variable that is playing during this turn
     * @return an ArrayList of Space which contains the allowed spaces where worker can build
     */
    @Override
    public ArrayList<Space> getOptionalBuild(Player player) {
        ArrayList<Space> availableBuild = super.getAvailableBuild(player);
     //   player.setHasBuilt(true);  // in god.build, here it's still optional
        return availableBuild;
    }
}
