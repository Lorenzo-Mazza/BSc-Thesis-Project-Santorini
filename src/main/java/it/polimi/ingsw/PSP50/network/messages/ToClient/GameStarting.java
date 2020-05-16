package it.polimi.ingsw.PSP50.network.messages.ToClient;

import it.polimi.ingsw.PSP50.Model.Player;
import it.polimi.ingsw.PSP50.View.ClientView;
import it.polimi.ingsw.PSP50.network.messages.ToClientMessage;

public class GameStarting extends ToClientMessage {
    public GameStarting(Player data) {
        super(data);
    }

    @Override
    public void doAction(ClientView ui) {
        String colorStart;
        String colorEnd = "\u001b[0m";
        colorStart = ((Player)data).getColor().getSequence();
        ui.drawSection("The color of your workers will be: " + colorStart+((Player)data).getColor().getName()+colorEnd);
        ui.drawSection("Game is starting! GET READY \uD83D\uDD25 \uD83D\uDD25 \uD83D\uDD25");

    }
}
