package it.polimi.ingsw.PSP50.network.messages;



/**
 ** Message sent from the client to the server
 */
public abstract class ToServerMessage extends Message  {
    /**
     * The sender's ID
     */
    private int playerId;

    /**
     * Constructor
     * @param data Payload of the message
     * @param sender Sender's ID
     */
    public ToServerMessage(Object data, int sender) {
        super(data);
        playerId = sender;
    }

    /**
     * Get the ID of the sender
     * @return the ID
     */
    public int getPlayerId() {
        return playerId;
    }

    /**
     * Set player ID of the sender
     * @param playerId the ID
     */
    public void setPlayerId(int playerId) {
        this.playerId = playerId;
    }

    /**
     * Cast Data correctly
     * Used to hide implementation in the UI and cast it correctly only inside the Server
     */
    public abstract Object castChoice();
}
