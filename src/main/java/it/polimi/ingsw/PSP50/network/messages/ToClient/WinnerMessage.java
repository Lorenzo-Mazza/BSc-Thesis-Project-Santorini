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
        if (winnerName.equals(ui.getName()))
            ui.drawSection("CONGRATULATIONS "+ winnerName.toUpperCase()
                    + ", YOU'RE THE CHAMPION!!! \uD83C\uDFC6 \uD83C\uDFC6 \uD83C\uDFC6");
        else ui.drawSection(winnerName+ " has won the game! Unfortunately You LOST. \uD83D\uDE2D \uD83D\uDE2D \uD83D\uDE2D"
                + "\nTry again next time!");
    }
}
