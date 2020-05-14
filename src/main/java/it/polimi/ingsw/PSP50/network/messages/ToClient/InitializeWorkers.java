package it.polimi.ingsw.PSP50.network.messages.ToClient;

import it.polimi.ingsw.PSP50.Model.Board;
import it.polimi.ingsw.PSP50.Model.Space;
import it.polimi.ingsw.PSP50.View.ClientView;
import it.polimi.ingsw.PSP50.network.messages.ToClientMessage;

import java.util.ArrayList;

public class InitializeWorkers extends ToClientMessage {
    public InitializeWorkers(Object data) {
        super(data);
    }

    @Override
    public void doAction(ClientView ui) {
        ui.drawSection("Choose where to place the workers");
        ui.initializeWorkers(getPossibleCoordinates(getSpaces()));
    }

    private ArrayList<Space> getSpaces() {
        ArrayList<Space> spaces = new ArrayList<Space>();
        for(int xCoordinate = 0; xCoordinate < 5; xCoordinate++) {
            for(int yCoordinate = 0; yCoordinate < 5; yCoordinate++)
                if(!((Board) data).getSpace(xCoordinate, yCoordinate).isOccupied())
                    spaces.add(((Board) data).getSpace(xCoordinate, yCoordinate));
        }

        return spaces;
    }
}
