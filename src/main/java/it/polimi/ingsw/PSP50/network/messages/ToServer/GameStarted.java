package it.polimi.ingsw.PSP50.network.messages.ToServer;

import it.polimi.ingsw.PSP50.network.messages.ToServerMessage;

/**
 * Message notifies the Server that game is ready to start
 */
public class GameStarted extends ToServerMessage {
    public GameStarted(Object data, int sender) {
        super(data, sender);
    }

    @Override
    public Object castChoice() {
        return (boolean) data;
    }
}
