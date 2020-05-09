package it.polimi.ingsw.PSP50.network.messages.ToClient;

import it.polimi.ingsw.PSP50.View.ClientView;
import it.polimi.ingsw.PSP50.network.messages.ClientMessage;

public class SelectMoveMessage extends ClientMessage {

    public SelectMoveMessage(Object data) {
        super(data);
    }

    @Override
    public void doAction(ClientView ui){

    }
}
