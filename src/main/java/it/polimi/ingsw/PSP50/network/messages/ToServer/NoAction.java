package it.polimi.ingsw.PSP50.network.messages.ToServer;

import it.polimi.ingsw.PSP50.network.messages.ToServerMessage;

/**
 * Message sent when the Client decided to skip an optional action
 */
public class NoAction extends ToServerMessage {
    public NoAction(Object data, int sender) {
        super(data, sender);
    }

    /**
     * Constant representing a No-Action decision
     */
    public static final Integer NO_ACTION = 999;

    /**
     * No casting required
     * @return the no-action constant
     */
    @Override
    public Object castChoice() {
        return NO_ACTION;
    }
}

