package it.polimi.ingsw.PSP50.network.messages.ToClient;

import it.polimi.ingsw.PSP50.View.ClientView;
import it.polimi.ingsw.PSP50.network.messages.ToClientMessage;

public class NameChanged extends ToClientMessage {
    public NameChanged(String data) {
        super(data);
    }

    @Override
    public void doAction(ClientView ui) {
        ui.setName((String)data);
        ui.nameChanged();
    }
}