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
        ui.drawSection("The Worker you selected in position (" + XCoordinate + YCoordinate +
                ") is blocked;try to choose another worker or you will lose.");
    }
}
