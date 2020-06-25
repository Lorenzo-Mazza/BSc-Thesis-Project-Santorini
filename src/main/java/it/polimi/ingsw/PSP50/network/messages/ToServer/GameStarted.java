package it.polimi.ingsw.PSP50.network.messages.ToServer;

import it.polimi.ingsw.PSP50.network.messages.ToServerMessage;

/**
 * Message notifies the Server that game is ready to start
 */
public class GameStarted extends ToServerMessage {
    /**
     * Constructor
     * @param data Boolean
     * @param sender Sender's ID
     */
    public GameStarted(Object data, int sender) {
        super(data, sender);
    }

    /**
     * Return true when the game is ready to start
     * @return true
     */
    @Override
    public Object castChoice() {
        return (boolean) data;
    }
}
