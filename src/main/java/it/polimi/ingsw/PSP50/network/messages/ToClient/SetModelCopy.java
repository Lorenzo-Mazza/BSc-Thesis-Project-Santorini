package it.polimi.ingsw.PSP50.network.messages.ToClient;

import it.polimi.ingsw.PSP50.Model.Game;
import it.polimi.ingsw.PSP50.Model.Player;
import it.polimi.ingsw.PSP50.View.ClientView;
import it.polimi.ingsw.PSP50.View.GUI.GuiView;
import it.polimi.ingsw.PSP50.network.messages.ToClientMessage;

import java.util.HashMap;

/**
 ** Message that sets an initial deep copy of the Model inside the client-side view
 *  Original reference of the model is not exposed (defensive programming)
 */
public class SetModelCopy extends ToClientMessage {
    /**
     * Constructor
     * @param game Deep copy of the game object
     */
    public SetModelCopy(Game game) {
        super(game);
    }

    /**
     * Set the deep copy inside the UI
     * @param ui the Client's UI
     */
    @Override
    public void doAction(ClientView ui) {
        ui.setGameCopy((Game)data);

    }
}
