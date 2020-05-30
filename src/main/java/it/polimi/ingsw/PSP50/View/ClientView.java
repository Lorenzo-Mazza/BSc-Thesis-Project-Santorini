package it.polimi.ingsw.PSP50.View;

import it.polimi.ingsw.PSP50.Model.Block;
import it.polimi.ingsw.PSP50.Model.Color;
import it.polimi.ingsw.PSP50.Model.Game;
import it.polimi.ingsw.PSP50.Utils.Observable;
import it.polimi.ingsw.PSP50.network.client.ClientSocket;
import it.polimi.ingsw.PSP50.network.messages.ToServerMessage;

import java.util.ArrayList;
import java.util.HashMap;

/*
** ClientView is an abstract class that will be implemented by GUI and CLI
 */
public abstract class ClientView extends Observable {

    protected String name;

    private ClientSocket socket;

    private int playerId;

    Game gameCopy;

    public Game getGameCopy() {
        return gameCopy;
    }

    public void setGameCopy(Game gameCopy) {
        this.gameCopy = gameCopy;
    }

    public int getPlayerId() {
        return playerId;
    }

    public void setPlayerId(int playerId) {
        this.playerId = playerId;
    }

    public String getName() {
        return this.name;
    }

    public void setName(String name){
        this.name=name;
    }

    public ClientSocket getSocket() {
        return socket;
    }

    public void setSocket(ClientSocket socket) {
        this.socket = socket;
    }

    public abstract void update(Object modelCopy);


    public abstract void startingGame();

    public abstract void selectWorker(ArrayList<int[]> possibleChoices);

    public abstract void moveAction (ArrayList<int[]> possibleChoices, boolean optional);

    public abstract void buildAction (ArrayList<int[]> possibleChoices, boolean optional);

    public abstract void chooseSpace(ArrayList<int[]> possibleChoices, boolean optional);

    public abstract void initializeWorkers(ArrayList<int[]> possibleChoices);

    public abstract void chooseGod(ArrayList<String> possibleChoices);

    public abstract void chooseBlock(Block possibleBlock);

    public abstract void workerIsBlocked (int x, int y);

    public abstract void welcomeMessage(HashMap<String,Color> opponents, Color playerColor);

    public abstract void winAlert (String winner) ;

    public abstract void loseAlert ();

    public abstract void nameChanged();

    public void notifySocket(ToServerMessage messageChoice) {
        this.getSocket().update(messageChoice);
    }

    public abstract void disconnectUI(String userDisconnect);
}
