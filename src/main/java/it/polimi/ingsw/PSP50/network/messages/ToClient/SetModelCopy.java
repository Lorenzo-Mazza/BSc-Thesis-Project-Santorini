package it.polimi.ingsw.PSP50.network.messages.ToClient;

import it.polimi.ingsw.PSP50.Model.Game;
import it.polimi.ingsw.PSP50.View.ClientView;
import it.polimi.ingsw.PSP50.network.messages.ToClientMessage;

public class SetModelCopy extends ToClientMessage {
    public SetModelCopy(Game game) {
        super(game);
    }


    @Override
    public void doAction(ClientView ui) {
        ui.setGameCopy((Game)data);
    }
}
