package it.polimi.ingsw.PSP50.network.messages;

import it.polimi.ingsw.PSP50.View.ClientView;

public abstract class ClientMessage extends Message {

    //Overloading
    public abstract void doAction(ClientView ui);
}
