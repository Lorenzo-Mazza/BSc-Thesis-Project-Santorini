package it.polimi.ingsw.PSP50.network.messages.ToServer;

import it.polimi.ingsw.PSP50.Model.Space;
import it.polimi.ingsw.PSP50.Model.Worker;
import it.polimi.ingsw.PSP50.network.messages.ToServerMessage;
import it.polimi.ingsw.PSP50.network.server.ServerManager;

public class WorkerSelection extends ToServerMessage {
    public WorkerSelection(Object data) {
        super(data);
    }

    @Override
    public Object castChoice() {
        int[] coordinates= (int[])this.data;
        Worker choice= ServerManager.getServer().getVirtualView(getPlayerId()).getGameManager().getGame().getBoard().getSpace(coordinates[0],coordinates[1]).getWorker();
        return choice;
    }
}
