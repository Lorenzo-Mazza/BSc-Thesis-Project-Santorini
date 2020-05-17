package it.polimi.ingsw.PSP50.network.messages.ToClient;

import it.polimi.ingsw.PSP50.Model.Player;
import it.polimi.ingsw.PSP50.View.ClientView;
import it.polimi.ingsw.PSP50.network.messages.ToClientMessage;

import java.util.ArrayList;

public class WelcomeMessage extends ToClientMessage {
    public WelcomeMessage(Player data) {
        super(data);
    }

    @Override
    public void doAction(ClientView ui) {
        String colorEnd = "\u001b[0m";
        String message = " Welcome to SANTORINI, " + ((Player) data).getName().toUpperCase() + "!!! " +
                "The color of your workers will be: " +
                ((Player)data).getColor().getSequence()+((Player)data).getColor().getName()+colorEnd + ". ";
        ArrayList<Player> opponents=  ((Player)data).getOpponents();
        String secondMessage=  "Your rivals are:  ";
        for (Player opp: opponents)
            secondMessage+= "---> "+(opp.getName())+" with the color "+
                    (opp.getColor().getSequence())+(opp.getColor().getName())+ colorEnd + ".  ";
        ui.drawSection(message);
        ui.drawSection(secondMessage);
    }
}
