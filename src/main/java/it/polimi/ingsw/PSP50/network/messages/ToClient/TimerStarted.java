package it.polimi.ingsw.PSP50.network.messages.ToClient;

import it.polimi.ingsw.PSP50.View.ClientView;
import it.polimi.ingsw.PSP50.network.messages.ToClientMessage;

public class TimerStarted extends ToClientMessage {
    public TimerStarted(Object data) {
        super(data);
    }

    @Override
    public void doAction(ClientView ui) {
        ui.drawSection("\n the timer for your action has started. You have 30 seconds to perform a valid action," +
                "otherwise a random action will be performed\n");
    }
}
