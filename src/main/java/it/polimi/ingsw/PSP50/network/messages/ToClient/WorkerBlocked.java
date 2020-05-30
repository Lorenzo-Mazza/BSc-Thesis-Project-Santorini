package it.polimi.ingsw.PSP50.network.messages.ToClient;

import it.polimi.ingsw.PSP50.Model.Worker;
import it.polimi.ingsw.PSP50.View.ClientView;
import it.polimi.ingsw.PSP50.network.messages.ToClientMessage;

public class WorkerBlocked extends ToClientMessage {
    public WorkerBlocked(Worker data) {
        super(data);
    }

    @Override
    public void doAction(ClientView ui) {
        int XCoordinate= ((Worker) data).getPosition().getXPosition();
        int YCoordinate= ((Worker) data).getPosition().getYPosition();
        ui.workerIsBlocked(XCoordinate,YCoordinate);
    }
}
