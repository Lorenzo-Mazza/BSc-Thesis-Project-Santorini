package it.polimi.ingsw.PSP50.GodsList;

import it.polimi.ingsw.PSP50.*;

import java.util.ArrayList;

public class Apollo extends God {
    private final Gods name = Gods.APOLLO;



    @Override
    public ArrayList<Space> power(Player player) {

            Worker thisWorker = player.getSelectedWorker();
            ArrayList<Space> available_moves = new ArrayList<Space>(thisWorker.getPosition().getNeighbors());

            return (available_moves);


    /*        for(Space space : thisSpaces)
            {
                if(space.isOccupied() && (space.getWorker().getOwner() != thisPlayer))
                {
                    Space lastPosition = thisWorker.getPosition();
                    thisWorker.move(space.getWorker().getPosition());
                    space.getWorker().move(lastPosition);
                }
            } */

    //    turn.setPhase(Phase.BUILD); non serve, si fa da Controller .
    }
}
