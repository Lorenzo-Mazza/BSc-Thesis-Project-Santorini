package it.polimi.ingsw.PSP50.network.messages.ToClient;

import it.polimi.ingsw.PSP50.Model.Game;
import it.polimi.ingsw.PSP50.View.ClientView;
import it.polimi.ingsw.PSP50.network.messages.ToClientMessage;
/**
** Message that sends a new deep copy of the Model to the Client every time there is a change
 * Original reference of the model is not exposed (defensive programming)
 */

public class ModelUpdate extends ToClientMessage {
    /**
     * Constructor
     * @param game Deep copy of the game object
     */
    public ModelUpdate(Game game) {
        super(game);
    }

    /**
     * Update the UI every time the Model changes
     */
    @Override
    public  void doAction(ClientView ui){
        ui.update(this.getData());
    }
}
