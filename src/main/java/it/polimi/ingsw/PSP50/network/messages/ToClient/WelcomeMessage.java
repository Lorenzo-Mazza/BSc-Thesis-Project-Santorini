package it.polimi.ingsw.PSP50.network.messages.ToClient;

import it.polimi.ingsw.PSP50.Model.Color;
import it.polimi.ingsw.PSP50.Model.Player;
import it.polimi.ingsw.PSP50.View.ClientView;
import it.polimi.ingsw.PSP50.network.messages.ToClientMessage;

import java.util.ArrayList;
import java.util.HashMap;

/**
 * Message welcomes the client to the game
 */
public class WelcomeMessage extends ToClientMessage {
    /**
     * Constructor
     * @param data Player object representing the client
     */
    public WelcomeMessage(Player data) {
        super(data);
    }

    /**
     * Set various parameters in the Client's UI (color, opponents)
     * @param ui the Client's UI
     */
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
