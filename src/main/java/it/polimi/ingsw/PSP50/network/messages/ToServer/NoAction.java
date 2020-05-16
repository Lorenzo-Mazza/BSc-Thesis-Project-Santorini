package it.polimi.ingsw.PSP50.network.messages.ToServer;

import it.polimi.ingsw.PSP50.network.messages.ToServerMessage;


public class NoAction extends ToServerMessage {
    public NoAction(Object data, int sender) {
        super(data, sender);
    }
    public static final Integer NO_ACTION = 999;


    @Override
    public Object castChoice() {
        return NO_ACTION;
    }
}

