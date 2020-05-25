package it.polimi.ingsw.PSP50.network.messages.ToClient;

import it.polimi.ingsw.PSP50.Model.Color;
import it.polimi.ingsw.PSP50.Model.Player;
import it.polimi.ingsw.PSP50.View.ClientView;
import it.polimi.ingsw.PSP50.network.messages.ToClientMessage;

import java.util.ArrayList;
import java.util.HashMap;

public class WelcomeMessage extends ToClientMessage {
    public WelcomeMessage(Player data) {
        super(data);
    }

    @Override
    public void doAction(ClientView ui) {
        Color playerColor = ((Player) data).getColor();
        ArrayList<Player> opponents=  ((Player)data).getOpponents();
        HashMap<String,Color> opponentsMap = new HashMap<>();
        for (Player opp : opponents)
            opponentsMap.put(opp.getName(),opp.getColor());
        ui.welcomeMessage(opponentsMap, playerColor);
    }
}
