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
            {   // Swap positions (there is a reason why I don't call super.move to swap, ask me if you notice).
                Space oldSpace = player.getSelectedWorker().getPosition();
                Worker opponentWorker= newSpace.getWorker();
                opponentWorker.move(oldSpace);
                oldSpace.setWorker(opponentWorker);  //set old space
                Worker playerWorker= player.getSelectedWorker();
                playerWorker.move(newSpace);
                newSpace.setWorker(playerWorker);  // set new space

                return playerWorker.getPosition()==newSpace;
            }

        else {
            return super.Move(player, newSpace);
        }
    }


}
