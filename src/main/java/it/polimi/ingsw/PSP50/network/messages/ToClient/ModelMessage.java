package it.polimi.ingsw.PSP50.network.messages.ToClient;

import it.polimi.ingsw.PSP50.Model.Game;
import it.polimi.ingsw.PSP50.view.ClientView;
import it.polimi.ingsw.PSP50.network.messages.ToClientMessage;
/*
** Class has a clone of the Model inside, so that original Representation is not exposed to Client
 */

public class ModelMessage extends ToClientMessage {


/*
 ** The constructor assigns to data a deep copy of the model
 */
    public ModelMessage(Game game) {
        super(game);
    }

/*
** Update the UI every time the Model changes
*/
    @Override
    public  void doAction(ClientView ui){
        ui.update(this.getData());
    }
}
