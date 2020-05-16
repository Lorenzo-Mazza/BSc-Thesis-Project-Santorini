package it.polimi.ingsw.PSP50.network.messages.ToClient;

import it.polimi.ingsw.PSP50.Model.GodsList.God;
import it.polimi.ingsw.PSP50.View.ClientView;
import it.polimi.ingsw.PSP50.network.messages.ToClientMessage;


import java.util.ArrayList;

public class ChooseGodCard extends ToClientMessage  {

    /**
     * Data is ArrayList that contains the remaining God cards
     */
    public ChooseGodCard(ArrayList<God> data) {
        super(data);
    }

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
