package it.polimi.ingsw.PSP50.network.messages.ToServer;

import it.polimi.ingsw.PSP50.Model.Block;
import it.polimi.ingsw.PSP50.network.messages.ToServerMessage;

public class BlockChoice extends ToServerMessage {
    public BlockChoice(Object data) {
        super(data);
    }

    @Override
    public Object castChoice() {
        return (Block) this.data;
    }
}