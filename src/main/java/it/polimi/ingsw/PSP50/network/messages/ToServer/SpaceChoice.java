package it.polimi.ingsw.PSP50.network.messages.ToServer;

import it.polimi.ingsw.PSP50.Model.Space;
import it.polimi.ingsw.PSP50.network.messages.ToServerMessage;
import it.polimi.ingsw.PSP50.network.server.ServerManager;

import java.io.Serializable;

/**
 * Message that sends the space choice back to the server
 */
public class SpaceChoice extends ToServerMessage {
    /**
     * Constructor
     * @param data Space choice (int[])
     * @param sender Sender's ID
     */
    public SpaceChoice(Object data, int sender) {
        super(data,sender);
    }

    /**
     * Method casts the coordinates given by the UI to the concrete Space of the Model
     * @return The correct reference to the space selected
     */
    @Override
    public Object castChoice() {
        int[] coordinates = (int[]) this.data;
        Space choice = ServerManager.getServer().getVirtualView(getPlayerId()).getGameManager()
                .getGame().getBoard().getSpace(coordinates[0],coordinates[1]);
        return choice;
    }
}
