package it.polimi.ingsw.PSP50.network.messages.ToClient;

import it.polimi.ingsw.PSP50.View.ClientView;
import it.polimi.ingsw.PSP50.network.messages.ToClientMessage;

/**
 * WinnerMessage tells every player connected that there is a winner
 */
public class WinnerMessage extends ToClientMessage {
    public WinnerMessage(String data) {
        super(data);
    }

    @Override
    public void doAction(ClientView ui) {
        String winnerName= (String)data;
        ui.winAlert(winnerName);
    }
}
