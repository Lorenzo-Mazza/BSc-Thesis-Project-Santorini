package it.polimi.ingsw.PSP50.network.messages.ToClient;

import it.polimi.ingsw.PSP50.Model.Player;
import it.polimi.ingsw.PSP50.View.ClientView;
import it.polimi.ingsw.PSP50.View.GUI.GuiView;
import it.polimi.ingsw.PSP50.network.messages.ToClientMessage;

public class GameStarting extends ToClientMessage {
    public GameStarting(Player data) {
        super(data);
    }

    @Override
    public void doAction(ClientView ui) {
        ui.startingGame();
    }
}
