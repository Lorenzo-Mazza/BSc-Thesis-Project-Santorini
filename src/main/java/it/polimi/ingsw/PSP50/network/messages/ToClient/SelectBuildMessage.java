package it.polimi.ingsw.PSP50.network.messages.ToClient;

import it.polimi.ingsw.PSP50.Model.Space;
import it.polimi.ingsw.PSP50.view.ClientView;
import it.polimi.ingsw.PSP50.network.messages.ToClientMessage;

import java.util.ArrayList;

public class SelectBuildMessage extends ToClientMessage {

    public SelectBuildMessage(Object data) {
        super(data);
    }

    @Override
    public void doAction(ClientView ui) {
        ui.drawSection("Choose where to build");
        ui.chooseSpace(getPossibleCoordinates((ArrayList<Space>) data));
    }
}
