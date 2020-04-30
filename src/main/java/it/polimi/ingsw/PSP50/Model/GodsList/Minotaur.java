package it.polimi.ingsw.PSP50.Model.GodsList;

import it.polimi.ingsw.PSP50.Model.*;

import java.util.ArrayList;

/**
 * Minotaur card
 */
public class Minotaur extends God {

    /**
     * *Constructor*
     */
    public Minotaur() {
        super(GodsNames.MINOTAUR);
        availableSteps.add(Phase.MOVE);
        availableSteps.add(Phase.BUILD);
    }

    /**
     * Overrides the default getAvailableMove method to implement Minotaur's power.
     * @param player The owner of the God card
     * @return an ArrayList of the available spaces where to perform a "Move" action
     */
    @Override
    public ArrayList<Space> getAvailableMove(Player player) {
        Worker thisWorker = player.getSelectedWorker();
        ArrayList<Space> reachable = thisWorker.getMovableWithWorkers();

        for (Space occupiedSpace: reachable) {
            if (occupiedSpace.isOccupied()) {
                int[] movement = thisWorker.getPosition().getCoordinatesFromSpaces(occupiedSpace);
                if (!occupiedSpace.thereIsNext(movement[0], movement[1]) ||
                        occupiedSpace.getNext(movement[0], movement[1]).isOccupied())
                    reachable.remove(occupiedSpace);
            }
        }

        return reachable;
    }

    /**
     * Overrides the default Move method to implement Minotaur's power.
     * @param player The owner of the God card
     * @param space a Space variable that indicate where to move
     * @return a boolean variable to report the outcome of the action
     */
    @Override
    public boolean Move(Player player, Space space) {


        if (space.isOccupied()) {
            Worker thisWorker = player.getSelectedWorker();
            int[] movement = thisWorker.getPosition().getCoordinatesFromSpaces(space);
            Worker opponentWorker = space.getWorker();
            //free the old space
            space.setWorker(null);
            opponentWorker.move(space.getNext(movement[0], movement[1]));
            //occupy the new opponent's space
            space.getNext(movement[0], movement[1]).setWorker(opponentWorker);

        }

        return super.Move(player, space);
    }
}
