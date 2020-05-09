package it.polimi.ingsw.PSP50.network.messages;

/*
 ** Message sent to the server
 */
public abstract class ServerMessage extends Message {

    private String sender;

    public ServerMessage(Object data) {
        super(data);
    }

    public String getSender() {
        return sender;
    }

    public void setSender(String sender) {
        this.sender = sender;
    }

    public abstract void doAction();
}
