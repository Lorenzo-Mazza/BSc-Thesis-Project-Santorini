package it.polimi.ingsw.PSP50.network.messages.ToServer;

import it.polimi.ingsw.PSP50.Model.Block;
import it.polimi.ingsw.PSP50.network.messages.ToServerMessage;

/**
 * Message that sends the block choice back to the server
 */
public class BlockChoice extends ToServerMessage {
    /**
     * Constructor
     * @param data Block choice
     * @param sender Sender's ID
     */
    public BlockChoice(Block data, int sender) {
        super(data,sender);
    }

    /**
     * Cast the choice correctly (no casting in this case)
     * @return Block
     */
    @Override
    public Object castChoice() {
        return (Block) this.data;
    }
}