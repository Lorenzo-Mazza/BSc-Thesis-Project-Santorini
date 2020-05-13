package it.polimi.ingsw.PSP50.network.messages.ToClient;

import it.polimi.ingsw.PSP50.View.ClientView;
import it.polimi.ingsw.PSP50.network.messages.ToClientMessage;

public class WinnerMessage extends ToClientMessage {
    public WinnerMessage(Object data) {
        super(data);
    }

    @Override
    public void doAction(ClientView ui) {

    }
}
