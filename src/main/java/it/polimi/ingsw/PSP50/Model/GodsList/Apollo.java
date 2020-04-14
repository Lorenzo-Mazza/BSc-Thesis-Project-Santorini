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
        int height= thisWorker.getPosition().getHeight().getValue();
        ArrayList<Space> neighbors= thisWorker.getPosition().getNeighbors();
        ArrayList<Space> reachable= new ArrayList<>();
        for (Space space: neighbors)
        {   int neighborHeight= space.getHeight().getValue();
            if ((neighborHeight - height) <2)
                reachable.add(space);
        }

        for (Space space: reachable) {
            if (space.isOccupied() &&  space.getWorker().getOwner()==player) //a player cannot swap with his other worker.
                reachable.remove(space);
        }

        return reachable;   //qui è più comodo rispetto a chiamare super.
    }

    @Override
    public boolean Move(Player player, Space newSpace)
    {   if (newSpace.isOccupied())
            {   // Swap positions
                Space oldSpace= player.getSelectedWorker().getPosition();
                newSpace.getWorker().move(oldSpace);
                player.getSelectedWorker().move(newSpace);
                return player.getSelectedWorker().getPosition() == newSpace;
            }

        else {
            return super.Move(player, newSpace);
            }
    }


}
