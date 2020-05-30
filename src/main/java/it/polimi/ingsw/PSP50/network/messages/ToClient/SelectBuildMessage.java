package it.polimi.ingsw.PSP50.network.messages.ToClient;

import it.polimi.ingsw.PSP50.Model.Space;
import it.polimi.ingsw.PSP50.View.ClientView;
import it.polimi.ingsw.PSP50.network.messages.ToClientMessage;

import java.util.ArrayList;

public class SelectBuildMessage extends ToClientMessage {

    private final boolean optional;

    public SelectBuildMessage(Object data, boolean optional) {
        super(data);
        this.optional=optional;
    }

    @Override
    public void doAction(ClientView ui) {
        ui.buildAction(getPossibleCoordinates((ArrayList<Space>) data),optional);
    }
}
