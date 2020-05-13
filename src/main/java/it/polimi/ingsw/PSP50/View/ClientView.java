package it.polimi.ingsw.PSP50.View;

import it.polimi.ingsw.PSP50.Model.Block;
import it.polimi.ingsw.PSP50.Observable;
import it.polimi.ingsw.PSP50.network.client.ClientSocket;
import it.polimi.ingsw.PSP50.network.messages.Message;

import java.util.ArrayList;

/*
** ClientView is an abstract class that will be implemented by GUI and CLI
 */
public abstract class ClientView extends Observable {

    protected String name;

    private ClientSocket socket;

    private int playerId;

    public int getPlayerId() {
        return playerId;
    }

    public void setPlayerId(int playerId) {
        this.playerId = playerId;
    }

    public String getName() {
        return this.name;
    }

    public ClientSocket getSocket() {
        return socket;
    }

    public void setSocket(ClientSocket socket) {
        this.socket = socket;
    }

    public abstract void update(Object modelCopy);

    public abstract void drawSection(String line);

    public abstract void chooseSpace(ArrayList<int[]> possibleChoices);

    public abstract void chooseGod(ArrayList<String> possibleChoices);

    public abstract void chooseBlock(Block possibleBlock);
}