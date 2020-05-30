package it.polimi.ingsw.PSP50.network.messages.ToClient;

import it.polimi.ingsw.PSP50.Model.Game;
import it.polimi.ingsw.PSP50.Model.Player;
import it.polimi.ingsw.PSP50.View.ClientView;
import it.polimi.ingsw.PSP50.View.GUI.GuiView;
import it.polimi.ingsw.PSP50.network.messages.ToClientMessage;

import java.util.HashMap;

public class SetModelCopy extends ToClientMessage {
    public SetModelCopy(Game game) {
        super(game);
    }


    @Override
    public void doAction(ClientView ui) {
        ui.setGameCopy((Game)data);

    }
}
