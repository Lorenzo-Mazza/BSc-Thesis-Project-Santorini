package it.polimi.ingsw.PSP50.View;

import it.polimi.ingsw.PSP50.Model.Block;
import it.polimi.ingsw.PSP50.Model.Color;
import it.polimi.ingsw.PSP50.Model.Game;
import it.polimi.ingsw.PSP50.Utils.Observable;
import it.polimi.ingsw.PSP50.network.client.ClientSocket;
import it.polimi.ingsw.PSP50.network.messages.ToServerMessage;

import java.util.ArrayList;
import java.util.HashMap;

/**
** ClientView is an abstract class that will be implemented by GUI and CLI
 */
public abstract class ClientView extends Observable {
    /**
     * Client's name
     */
    protected String name;
    /**
     * Client's socket
     */
    private ClientSocket socket;
    /**
     * Client's Id
     */
    private int playerId;
    /**
     * Copy of the game the client is playing; it's not the original object (defensive programming)
     */
    Game gameCopy;

    /**
     * @return a copy of the game the client is playing
     */
    public Game getGameCopy() {
        return gameCopy;
    }

    /**
     * @param gameCopy the copy of the game is being set
     */
    public void setGameCopy(Game gameCopy) {
        this.gameCopy = gameCopy;
    }

    /**
     * @return client's Id
     */
    public int getPlayerId() {
        return playerId;
    }

    /**
     * set client's Id
     * @param playerId
     */
    public void setPlayerId(int playerId) {
        this.playerId = playerId;
    }

    /**
     * @return client's name
     */
    public String getName() {
        return this.name;
    }

    /**
     * set client's name
     * @param name
     */
    public void setName(String name){
        this.name=name;
    }

    /**
     * @return socket the client is connected to
     */
    public ClientSocket getSocket() {
        return socket;
    }

    /**
     * set the socket that the client will use
     * @param socket
     */
    public void setSocket(ClientSocket socket) {
        this.socket = socket;
    }

    /**
     * update the local game copy
     * @param modelCopy
     */
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

    /**
     * Notify the server of a new message
     * @param messageChoice
     */
    public void notifySocket(ToServerMessage messageChoice) {
        this.getSocket().update(messageChoice);
    }

    public abstract void disconnectUI(String userDisconnect);
}
