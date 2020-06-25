package it.polimi.ingsw.PSP50.network.messages.ToServer;

import it.polimi.ingsw.PSP50.Model.Space;
import it.polimi.ingsw.PSP50.network.messages.ToServerMessage;
import it.polimi.ingsw.PSP50.network.server.ServerManager;

import java.util.ArrayList;

/**
 * Message send to the server the initial positions of the player's workers
 */
public class WorkersInitialChoice extends ToServerMessage {
    /**
     * Constructor
     * @param data List of int[] representing the initial spaces where the workers are
     * @param sender Sender's ID
     */
    public WorkersInitialChoice(ArrayList<int[]> data, int sender) {
        super(data, sender);
    }

    /**
     * Cast the two choices from int[] to the concrete Space of the Game Board
     * @return the two original Spaces in a list
     */
    @Override
    public Object castChoice() {
        ArrayList<int[]> coordinates = (ArrayList<int[]>) this.data;
        Space firstChoice = ServerManager.getServer().getVirtualView(getPlayerId()).getGameManager()
                .getGame().getBoard().getSpace(coordinates.get(0)[0],coordinates.get(0)[1]);
        Space secondChoice = ServerManager.getServer().getVirtualView(getPlayerId()).getGameManager()
                .getGame().getBoard().getSpace(coordinates.get(1)[0],coordinates.get(1)[1]);
        ArrayList<Space> answer = new ArrayList<>();
        answer.add(firstChoice);
        answer.add(secondChoice);
        return answer;
    }
}
