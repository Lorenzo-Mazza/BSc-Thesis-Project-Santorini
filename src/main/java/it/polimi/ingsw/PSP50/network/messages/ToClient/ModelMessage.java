package it.polimi.ingsw.PSP50.network.messages.ToClient;

import it.polimi.ingsw.PSP50.Model.Game;
import it.polimi.ingsw.PSP50.View.ClientView;
import it.polimi.ingsw.PSP50.network.messages.ClientMessage;
import org.apache.commons.lang3.SerializationUtils;
/*
** Class has a clone of the Model inside, so that original Representation is not exposed to Client
 */

public class ModelMessage extends ClientMessage {


/*
 ** The constructor creates a deep copy of the model
 */
    public ModelMessage(Game game) {
        super(game);
        this.data = SerializationUtils.clone(game);
    }

/*
** Update the UI every time the Model changes
*/
    @Override
    public  void doAction(ClientView ui){
        ui.update(this.getData());
    }
}
