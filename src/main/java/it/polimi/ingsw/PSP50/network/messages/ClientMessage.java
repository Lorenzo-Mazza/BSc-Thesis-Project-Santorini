package it.polimi.ingsw.PSP50.network.messages;

import it.polimi.ingsw.PSP50.View.ClientView;

/*
** Message sent to the client
 */
public abstract class ClientMessage extends Message {

    private String receiver;

    public ClientMessage(Object data) {
        super(data);
    }


    public String getReceiver(){
        return this.receiver;
    }

    public void setReceiver(String receiver) {
        this.receiver = receiver;
    }

    public abstract void doAction(ClientView ui);
}
