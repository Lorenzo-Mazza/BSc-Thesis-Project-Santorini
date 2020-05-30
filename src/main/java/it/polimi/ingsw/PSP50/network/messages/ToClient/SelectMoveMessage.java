package it.polimi.ingsw.PSP50.network.messages.ToClient;

import it.polimi.ingsw.PSP50.Model.Space;
import it.polimi.ingsw.PSP50.View.ClientView;
import it.polimi.ingsw.PSP50.network.messages.ToClientMessage;

import java.util.ArrayList;

public class SelectMoveMessage extends ToClientMessage {

    private final boolean optional;

    public SelectMoveMessage(Object data, boolean optional) {
        super(data);
        this.optional=optional;
    }

    @Override
    public void doAction(ClientView ui){
        ui.moveAction(getPossibleCoordinates((ArrayList<Space>) data),optional);
    }
}
