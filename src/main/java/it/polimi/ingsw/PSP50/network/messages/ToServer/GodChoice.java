package it.polimi.ingsw.PSP50.network.messages.ToServer;

import it.polimi.ingsw.PSP50.Model.GodsNames;
import it.polimi.ingsw.PSP50.network.messages.ToServerMessage;

/**
 * Message sends the God choice back to server
 */
public class GodChoice extends ToServerMessage {

    /**
     * Constructor
     * @param data Data contains the int representing the God choice
     * @param sender Sender's ID
     */
    public GodChoice(Object data, int sender) {
        super(data,sender);
    }

    /**
     * No casting required
     * @return an integer
     */
    @Override
    public Object castChoice() {
        return data;
    }
}
