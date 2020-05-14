package it.polimi.ingsw.PSP50.network.messages;



/*
 ** Message sent to the server
 */
public abstract class ToServerMessage extends Message  {

    private int playerId;

    public ToServerMessage(Object data, int sender) {
        super(data);
        playerId = sender;
    }

    public int getPlayerId() {
        return playerId;
    }

    public void setPlayerId(int playerId) {
        this.playerId = playerId;
    }

    //casts Data correctly
    public abstract Object castChoice();
}
