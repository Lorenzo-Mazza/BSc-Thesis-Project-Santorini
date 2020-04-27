package it.polimi.ingsw.PSP50.Model.GodsList;

import it.polimi.ingsw.PSP50.Model.*;

import java.util.ArrayList;

/**
 * *Description of class*
 */
public class Apollo extends God {

    /**
     * *Constructor*
     */
    public Apollo(){
        super(GodsNames.APOLLO);
        availableSteps.add(Phase.MOVE);
        availableSteps.add(Phase.BUILD);
    }

    /**
     * *Description of method*
     * @param player a Player variable that is playing during this turn
     * @return an ArrayList of Space given by getMovableWithWorkers()
     */
    @Override
    public ArrayList<Space> getAvailableMove(Player player) {
        Worker thisWorker = player.getSelectedWorker();
        return (new ArrayList<>(thisWorker.getMovableWithWorkers()));
    }

    /**
     * *Description of method*
     * @param player a Player variable that is playing during this turn
     * @param space a Space variable that indicate where to move
     * @return a boolean variable just for confirmation
     */
    @Override
    public boolean Move(Player player, Space space)
    {
        if (space.isOccupied())
            {   // Swap positions (there is a reason why I don't call super.move to swap, ask me if you notice).
                Space oldSpace = player.getSelectedWorker().getPosition();
                Worker opponentWorker= space.getWorker();
                opponentWorker.move(oldSpace);
                oldSpace.setWorker(opponentWorker);  //set old space
                Worker playerWorker= player.getSelectedWorker();
                playerWorker.move(space);
                space.setWorker(playerWorker);  // set new space

                return playerWorker.getPosition()==space;
            }

        else {
            return super.Move(player, space);
        }
    }


}
