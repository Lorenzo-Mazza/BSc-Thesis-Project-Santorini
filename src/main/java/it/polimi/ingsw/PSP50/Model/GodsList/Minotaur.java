package it.polimi.ingsw.PSP50.Model.GodsList;

import it.polimi.ingsw.PSP50.Model.*;

import java.util.ArrayList;

public class Minotaur extends God {

    public Minotaur() {
        super(GodsNames.MINOTAUR);
        availableSteps.add(Phase.MOVE);
        availableSteps.add(Phase.BUILD);
    }

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

    @Override
    public boolean Move(Player player, Space space) {


        if (space.isOccupied()) {
            Worker thisWorker = player.getSelectedWorker();
            int[] movement = thisWorker.getPosition().getCoordinatesFromSpaces(space);
            Worker opponentWorker = space.getWorker();
            space.setWorker(null);  //free space
            opponentWorker.move(space.getNext(movement[0], movement[1]));
            space.getNext(movement[0], movement[1]).setWorker(opponentWorker);  //occupy new space for the opponent

        }

        return super.Move(player, space);
    }
}
