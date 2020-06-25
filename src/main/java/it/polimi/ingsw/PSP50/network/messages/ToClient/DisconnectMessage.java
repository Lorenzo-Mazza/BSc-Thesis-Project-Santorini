package it.polimi.ingsw.PSP50.network.messages.ToClient;

import it.polimi.ingsw.PSP50.View.ClientView;
import it.polimi.ingsw.PSP50.network.messages.ToClientMessage;
/**
 * Message sent when a Player has disconnected from the game
 */
public class DisconnectMessage extends ToClientMessage {
    /**
     * Constructor
     * @param data Name of the disconnected player
     */
    public DisconnectMessage(String data) {
        super(data);
    }

    /**
     * Call the method to handle the disconnection
     * @param ui the Client's UI
     */
    @Override
    public void doAction(ClientView ui) {
        ui.disconnectUI((String) this.data);
    }
}
