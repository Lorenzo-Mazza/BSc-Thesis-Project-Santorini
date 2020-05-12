package it.polimi.ingsw.PSP50.network.messages.ToClient;

import it.polimi.ingsw.PSP50.view.ClientView;
import it.polimi.ingsw.PSP50.network.messages.ToClientMessage;

public class SelectWorkerMessage extends ToClientMessage {

    public SelectWorkerMessage(Object data) {
        super(data);
    }

    @Override
    public void doAction(ClientView ui){

    }

}
