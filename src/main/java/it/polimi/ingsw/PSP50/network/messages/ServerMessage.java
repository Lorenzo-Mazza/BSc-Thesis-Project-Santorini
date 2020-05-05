package it.polimi.ingsw.PSP50.network.messages;

public abstract class ServerMessage extends Message {
    private String sender;

    public abstract void doAction();

    public String getSender() {
        return sender;
    }

    public void setSender(String sender) {
        this.sender = sender;
    }
}
