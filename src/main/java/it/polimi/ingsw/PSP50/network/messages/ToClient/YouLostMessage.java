package it.polimi.ingsw.PSP50.network.messages.ToClient;

import it.polimi.ingsw.PSP50.View.ClientView;
import it.polimi.ingsw.PSP50.network.messages.ToClientMessage;

/**
 * Message sent to a client when he lost before there is a winner: it gives to the client
 * the possibility of staying connected and see how the other players end the game.
 * (I.E. in a 3-players game, when a player has all his workers blocked and cannot move anymore)
 */
public class YouLostMessage extends ToClientMessage {
    public YouLostMessage(String data) {
        super(data);
    }

    /**
     * Print a "you lost" message
     * @param ui the Client's UI
     */
    @Override
    public void doAction(ClientView ui) {
        ui.loseAlert();
    }
}
