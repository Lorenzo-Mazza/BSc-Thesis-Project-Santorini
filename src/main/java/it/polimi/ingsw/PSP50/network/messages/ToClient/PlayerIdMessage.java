package it.polimi.ingsw.PSP50.network.messages.ToClient;

import it.polimi.ingsw.PSP50.view.ClientView;
import it.polimi.ingsw.PSP50.network.messages.ToClientMessage;

public class PlayerIdMessage extends ToClientMessage {
    public PlayerIdMessage(Object data) {
        super(data);
    }

    @Override
    public void doAction(ClientView ui) {
        ui.setPlayerId((int)data);
    }
}
