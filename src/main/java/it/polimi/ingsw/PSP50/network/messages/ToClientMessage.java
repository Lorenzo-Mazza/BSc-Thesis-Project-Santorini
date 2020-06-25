package it.polimi.ingsw.PSP50.network.messages;

import it.polimi.ingsw.PSP50.Model.Space;
import it.polimi.ingsw.PSP50.View.ClientView;

import java.util.ArrayList;

/**
** Message sent from server to client
 */
public abstract class ToClientMessage extends Message {
    /**
     * Name of the client that will receive the message
     */
    private String receiver;

    /**
     * Constructor
     * @param data Payload of the message
     */
    public ToClientMessage(Object data) {
        super(data);
    }

    /**
     * Get the receiver's name
     * @return receiver's name
     */
    public String getReceiver(){
        return this.receiver;
    }

    /**
     * Set the client that will receive the message
     * @param receiver Receiver's name
     */
    public void setReceiver(String receiver) {
        this.receiver = receiver;
    }

    /**
     * Abstract method that will execute UI functions, based on the message type
     * @param ui the Client's UI
     */
    public abstract void doAction(ClientView ui);

    /**
     * Helper method that converts a list of spaces in a list of int[]
     * Used to hide implementation (defensive programming)
     * @param spaces Spaces in input
     * @return the coordinates of the spaces as int[]
     */
    public ArrayList<int[]> getPossibleCoordinates(ArrayList<Space> spaces) {
        ArrayList<int[]> coordinates = new ArrayList<>();
        for(Space space: spaces) {
            coordinates.add(space.getCoordinates());
        }
        return coordinates;
    }
}
