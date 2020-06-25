package it.polimi.ingsw.PSP50.network.messages.ToClient;

import it.polimi.ingsw.PSP50.View.ClientView;
import it.polimi.ingsw.PSP50.network.messages.ToClientMessage;

/**
 * Message that set the UI player ID value that the client has on the server
 */
public class PlayerIdMessage extends ToClientMessage {
    /**
     * Constructor
     * @param data Int that represents the player ID
     */
    public PlayerIdMessage(Object data) {
        super(data);
    }

    /**
     * Set the player ID
     * @param ui the Client's UI
     */
    @Override
    public void doAction(ClientView ui) {
        ui.setPlayerId((int)data);
    }
}
