package it.polimi.ingsw.PSP50.network.messages.ToClient;

import it.polimi.ingsw.PSP50.view.ClientView;
import it.polimi.ingsw.PSP50.network.messages.ToClientMessage;

public class SelectMoveMessage extends ToClientMessage {

    public SelectMoveMessage(Object data) {
        super(data);
    }

    @Override
    public void doAction(ClientView ui){

    }
}
