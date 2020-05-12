package it.polimi.ingsw.PSP50.network.messages;

import it.polimi.ingsw.PSP50.Model.Space;
import it.polimi.ingsw.PSP50.View.ClientView;

import java.util.ArrayList;

/*
** Message sent to the client
 */
public abstract class ToClientMessage extends Message {

    private String receiver;

    public ToClientMessage(Object data) {
        super(data);
    }

    public String getReceiver(){
        return this.receiver;
    }

    public void setReceiver(String receiver) {
        this.receiver = receiver;
    }

    public abstract void doAction(ClientView ui);

    public ArrayList<int[]> getPossibleCoordinates(ArrayList<Space> spaces) {
        ArrayList<int[]> coordinates = new ArrayList<>();
        for(Space space: spaces) {
            coordinates.add(space.getCoordinates());
        }
        return coordinates;
    }
}
