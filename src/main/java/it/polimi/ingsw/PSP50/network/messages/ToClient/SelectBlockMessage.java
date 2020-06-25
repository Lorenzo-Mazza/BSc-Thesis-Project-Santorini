package it.polimi.ingsw.PSP50.network.messages.ToClient;

import it.polimi.ingsw.PSP50.View.ClientView;
import it.polimi.ingsw.PSP50.network.messages.ToClientMessage;
import it.polimi.ingsw.PSP50.Model.Space;

import java.util.ArrayList;

/**
 * Message that makes a block selection (only for Atlas power)
 */
public class SelectBlockMessage extends ToClientMessage {
    /**
     * Constructor
     * @param data Space where the user is building the block
     */
    public SelectBlockMessage(Space data) {
        super(data);
    }

    /**
     * Make the block choice
     * @param ui the Client's UI
     */
    @Override
    public void doAction(ClientView ui){
        ui.chooseBlock(((Space) data).getNextHeight());
    }
}
