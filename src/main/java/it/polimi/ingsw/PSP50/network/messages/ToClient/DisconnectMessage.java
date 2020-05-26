package it.polimi.ingsw.PSP50.network.messages.ToClient;

import it.polimi.ingsw.PSP50.Model.GodsList.God;
import it.polimi.ingsw.PSP50.View.ClientView;
import it.polimi.ingsw.PSP50.network.messages.ToClientMessage;

public class DisconnectMessage extends ToClientMessage {

    public DisconnectMessage(String data) {
        super(data);
    }

    @Override
    public void doAction(ClientView ui) {
        ui.disconnectCLI((String) this.data);
    }
}
