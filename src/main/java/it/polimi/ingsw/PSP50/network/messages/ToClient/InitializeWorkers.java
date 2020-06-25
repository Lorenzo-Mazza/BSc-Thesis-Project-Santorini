package it.polimi.ingsw.PSP50.network.messages.ToClient;

import it.polimi.ingsw.PSP50.Model.Board;
import it.polimi.ingsw.PSP50.Model.Space;
import it.polimi.ingsw.PSP50.View.ClientView;
import it.polimi.ingsw.PSP50.network.messages.ToClientMessage;

import java.util.ArrayList;

/**
 * Message used to initialize the two workers of the client
 */
public class InitializeWorkers extends ToClientMessage {
    /**
     * Constructor
     * @param data The Game board
     */
    public InitializeWorkers(Object data) {
        super(data);
    }

    /**
     * Send to the client the possible spaces where to place his workers
     * @param ui the Client's UI
     */
    @Override
    public void doAction(ClientView ui) {
        ui.initializeWorkers(getPossibleCoordinates(getSpaces()));
    }

    /**
     * Get the available spaces of the game board
     * @return list of available spaces
     */
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
