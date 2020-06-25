package it.polimi.ingsw.PSP50.network.messages.ToClient;

import it.polimi.ingsw.PSP50.Model.Player;
import it.polimi.ingsw.PSP50.View.ClientView;
import it.polimi.ingsw.PSP50.View.GUI.GuiView;
import it.polimi.ingsw.PSP50.network.messages.ToClientMessage;

/**
 * Message that informs Clients that game is starting
 */
public class GameStarting extends ToClientMessage {
    public GameStarting(Player data) {
        super(data);
    }

    /**
     * Print the "Starting Game" screen on the UI
     * @param ui the Client's UI
     */
    @Override
    public void doAction(ClientView ui) {
        ui.startingGame();
    }
}
