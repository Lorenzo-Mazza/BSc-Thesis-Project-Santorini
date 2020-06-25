package it.polimi.ingsw.PSP50.network.messages.ToClient;

import it.polimi.ingsw.PSP50.Model.Worker;
import it.polimi.ingsw.PSP50.View.ClientView;
import it.polimi.ingsw.PSP50.network.messages.ToClientMessage;

/**
 * Message sent to the UI when the user selected a worker that he cannot move
 * Used to give the user another chance to the user and not make him lose immediately
 */
public class WorkerBlocked extends ToClientMessage {
    /**
     * Constructor
     * @param data Coordinates of the blocked worker
     */
    public WorkerBlocked(Worker data) {
        super(data);
    }

    /**
     * Print correct message on the screen and make another worker selection
     * @param ui the Client's UI
     */
    @Override
    public void doAction(ClientView ui) {
        int XCoordinate= ((Worker) data).getPosition().getXPosition();
        int YCoordinate= ((Worker) data).getPosition().getYPosition();
        ui.workerIsBlocked(XCoordinate,YCoordinate);
    }
}
