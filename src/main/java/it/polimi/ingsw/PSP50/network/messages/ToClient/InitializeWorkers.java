package it.polimi.ingsw.PSP50.network.messages.ToClient;

import it.polimi.ingsw.PSP50.View.ClientView;
import it.polimi.ingsw.PSP50.network.messages.ToClientMessage;

public class InitializeWorkers extends ToClientMessage {
    public InitializeWorkers(Object data) {
        super(data);
    }

    @Override
    public void doAction(ClientView ui) {

    }
}
