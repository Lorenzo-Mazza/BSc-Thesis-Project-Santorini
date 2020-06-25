package it.polimi.ingsw.PSP50.network.messages.ToClient;

import it.polimi.ingsw.PSP50.Model.GodsList.God;
import it.polimi.ingsw.PSP50.View.ClientView;
import it.polimi.ingsw.PSP50.network.messages.ToClientMessage;


import java.util.ArrayList;

/**
 * Message sent when a Client has to choose a God Card
 */
public class ChooseGodCard extends ToClientMessage  {

    /**
     * Constructor
     * @param data ArrayList that contains the remaining God cards
     */
    public ChooseGodCard(ArrayList<God> data) {
        super(data);
    }

    /**
     * Calls the UI method that let the Client choose a god
     * @param ui the Client's UI
     */
    @Override
    public void doAction(ClientView ui) {
        ArrayList<String> choices = new ArrayList<>();
        ArrayList<God> godsLeft = (ArrayList<God>) this.getData();
        for (God god: godsLeft){
            choices.add(god.getName().name());
        }
        ui.chooseGod(choices);
    }
}
