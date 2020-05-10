package it.polimi.ingsw.PSP50.network.messages;


/*
 ** Message sent to the server
 */
public abstract class ToServerMessage extends Message {

    private String sender;

    public ToServerMessage(Object data) {
        super(data);
    }

    public String getSender() {
        return sender;
    }

    public void setSender(String sender) {
        this.sender = sender;
    }

    //casts Data correctly
    public abstract Object castChoice();
}
