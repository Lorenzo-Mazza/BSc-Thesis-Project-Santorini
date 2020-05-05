package it.polimi.ingsw.PSP50.network.messages;

public abstract class ServerMessage {
    private String sender;
    public abstract void doAction();

    public String getSender() {
        return sender;
    }

    public void setSender(String sender) {
        this.sender = sender;
    }
}
