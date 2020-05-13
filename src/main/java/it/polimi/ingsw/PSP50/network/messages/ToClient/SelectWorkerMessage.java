package it.polimi.ingsw.PSP50.network.messages.ToClient;

import it.polimi.ingsw.PSP50.Model.Space;
import it.polimi.ingsw.PSP50.Model.Worker;
import it.polimi.ingsw.PSP50.View.ClientView;
import it.polimi.ingsw.PSP50.network.messages.ToClientMessage;

import java.util.ArrayList;

public class SelectWorkerMessage extends ToClientMessage {

    public SelectWorkerMessage(Object data) {
        super(data);
    }

    @Override
    public void doAction(ClientView ui){
        ArrayList<Space> workersPositions = new ArrayList<>();
        for(Worker worker: ((ArrayList<Worker>) data))
            workersPositions.add(worker.getPosition());
        ui.drawSection("Choose the worker to use");
        ui.chooseSpace(getPossibleCoordinates(workersPositions),false);
    }

}
