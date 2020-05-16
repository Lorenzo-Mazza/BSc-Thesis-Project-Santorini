package it.polimi.ingsw.PSP50.network.messages.ToServer;

import it.polimi.ingsw.PSP50.Model.Space;
import it.polimi.ingsw.PSP50.network.messages.ToServerMessage;
import it.polimi.ingsw.PSP50.network.server.ServerManager;

import java.io.Serializable;

public class SpaceChoice extends ToServerMessage {
    public SpaceChoice(Object data, int sender) {
        super(data,sender);
    }

    //Method casts the coordinates given by the CLI to a concrete Space of the Model
    @Override
    public Object castChoice() {
        int[] coordinates = (int[]) this.data;
        Space choice = ServerManager.getServer().getVirtualView(getPlayerId()).getGameManager()
                .getGame().getBoard().getSpace(coordinates[0],coordinates[1]);
        return choice;
    }
}
