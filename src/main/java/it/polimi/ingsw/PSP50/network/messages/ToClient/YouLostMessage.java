package it.polimi.ingsw.PSP50.network.messages.ToClient;

import it.polimi.ingsw.PSP50.View.ClientView;
import it.polimi.ingsw.PSP50.network.messages.ToClientMessage;

public class YouLostMessage extends ToClientMessage {
    public YouLostMessage(String data) {
        super(data);
    }

    @Override
    public void doAction(ClientView ui) {
        ui.loseAlert();
    }
}
