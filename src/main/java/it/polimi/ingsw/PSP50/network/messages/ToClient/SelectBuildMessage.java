package it.polimi.ingsw.PSP50.network.messages.ToClient;

import it.polimi.ingsw.PSP50.Model.Space;
import it.polimi.ingsw.PSP50.View.ClientView;
import it.polimi.ingsw.PSP50.network.messages.ToClientMessage;

import java.util.ArrayList;

/**
 * Message that makes the user select a space where to perform a BUILD action
 */
public class SelectBuildMessage extends ToClientMessage {

    /**
     * If the action is optional it's set to true
     */
    private final boolean optional;

    /**
     * Constructor
     * @param data List of the available spaces
     * @param optional If the action is optional
     */
    public SelectBuildMessage(Object data, boolean optional) {
        super(data);
        this.optional=optional;
    }

    /**
     * Make the choice through the UI
     * @param ui the Client's UI
     */
    @Override
    public void doAction(ClientView ui) {
        ui.buildAction(getPossibleCoordinates((ArrayList<Space>) data),optional);
    }
}
