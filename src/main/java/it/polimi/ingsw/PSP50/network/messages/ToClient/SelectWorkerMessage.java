package it.polimi.ingsw.PSP50.network.messages.ToClient;

import it.polimi.ingsw.PSP50.Model.Space;
import it.polimi.ingsw.PSP50.Model.Worker;
import it.polimi.ingsw.PSP50.View.ClientView;
import it.polimi.ingsw.PSP50.network.messages.ToClientMessage;

import java.util.ArrayList;
/**
 * Message that makes the user select a worker at the start of his turn
 */
public class SelectWorkerMessage extends ToClientMessage {
    /**
     * Constructor
     * @param data List of the available spaces where the workers are
     */
    public SelectWorkerMessage(Worker[] data) {
        super(data);
    }

    /**
     * Make the choice through the UI
     * @param ui the Client's UI
     */
    @Override
    public void doAction(ClientView ui){
        ArrayList<Space> workersPositions = new ArrayList<>();
        for(Worker worker: ((Worker[]) data))
            workersPositions.add(worker.getPosition());
        ui.selectWorker(getPossibleCoordinates(workersPositions));
    }

}
