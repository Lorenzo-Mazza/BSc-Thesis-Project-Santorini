package it.polimi.ingsw.PSP50.network.messages.ToClient;

import it.polimi.ingsw.PSP50.View.ClientView;
import it.polimi.ingsw.PSP50.network.messages.ToClientMessage;
import it.polimi.ingsw.PSP50.Model.Space;

import java.util.ArrayList;

public class SelectBlockMessage extends ToClientMessage {

    public SelectBlockMessage(Object data) {
        super(data);
    }

    @Override
    public void doAction(ClientView ui){
        ui.drawSection("Choose which block you want to build");
        ui.chooseSpace(getPossibleCoordinates((ArrayList<Space>) data));
    }
}
