package it.polimi.ingsw.PSP50.network.messages.ToServer;

import it.polimi.ingsw.PSP50.Model.GodsNames;
import it.polimi.ingsw.PSP50.network.messages.ToServerMessage;

public class GodChoice extends ToServerMessage {

    /**
     * Data contains the int representing the God
     */
    public GodChoice(Object data) {
        super(data);
    }

    @Override
    public Object castChoice() {
        return  data;
    }
}
