package it.polimi.ingsw.PSP50.network.messages.ToClient;

import it.polimi.ingsw.PSP50.View.ClientView;
import it.polimi.ingsw.PSP50.network.messages.ToClientMessage;

/**
 * Message notifies the Client that his name changed because there is already another player
 * in the game with the same name
 */
public class NameChanged extends ToClientMessage {
    /**
     * Constructor
     * @param data New name of the Client
     */
    public NameChanged(String data) {
        super(data);
    }

    /**
     * Set the UI new name and notify the client
     * @param ui the Client's UI
     */
    @Override
    public void doAction(ClientView ui) {
        ui.setName((String)data);
        ui.nameChanged();
    }
}
