package it.polimi.ingsw.PSP50.Model.GodsList;

import it.polimi.ingsw.PSP50.Model.*;

import java.util.ArrayList;

public class Apollo extends God {

    public Apollo(){
        super(GodsNames.APOLLO);
        availableSteps.add(Phase.MOVE);
        availableSteps.add(Phase.BUILD);
    }

    @Override
    public ArrayList<Space> getAvailableMove(Player player) {
        Worker thisWorker = player.getSelectedWorker();
        return (new ArrayList<>(thisWorker.getMovableWithWorkers()));
    }

    @Override
    public boolean Move(Player player, Space newSpace)
    {
        if (newSpace.isOccupied())
            {   // Swap positions
                Space oldSpace = player.getSelectedWorker().getPosition();
                newSpace.getWorker().move(oldSpace);
                player.getSelectedWorker().move(newSpace);
                return player.getSelectedWorker().getPosition() == newSpace;
            }

        else
            return super.Move(player, newSpace);
    }


}
