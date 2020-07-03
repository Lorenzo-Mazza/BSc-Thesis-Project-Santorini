package it.polimi.ingsw.PSP50.View;

import it.polimi.ingsw.PSP50.Controller.GameManager;
import it.polimi.ingsw.PSP50.Utils.Observable;
import it.polimi.ingsw.PSP50.Utils.Observer;
import it.polimi.ingsw.PSP50.network.messages.ToClientMessage;
import it.polimi.ingsw.PSP50.network.messages.Message;
import it.polimi.ingsw.PSP50.network.server.ServerManager;

/**
 *  Server-side View
 */
public class VirtualView extends Observable implements Observer {
    /**
     * Game manager referenced by the virtual view
     */
    private GameManager gameController;
    /**
     * Name of the virtual view's owner
     */
    private String playerName;
    /**
     * Id of the virtual view's owner
     */
    private int playerId;
    /**
     * Boolean that indicates whether a player has lost or not
     */
    private boolean hasLost;

    /**
     * Constructor
     * @param playerId player's ID
     * @param playerName player's name
     */
    public VirtualView(int playerId,String playerName){
        this.playerId = playerId;
        this.playerName = playerName;
        this.hasLost = false;
    }
    /**
     * @return the Id of the virtual view's owner
     */
    public int getPlayerId(){
        return this.playerId;
    }

    /**
     * @return the name of the virtual view's owner
     */
    public String getPlayerName() {
        return this.playerName;
    }

    /**
     * @param playerName the name that is being set as the owner's name
     */
    public void setPlayerName(String playerName) {
        this.playerName = playerName;
    }

    /**
     * @return the game manager referenced by the virtual view
     */
    public GameManager getGameManager() {
        return gameController;
    }

    /**
     * @param gameController the game manager that is being set
     */
    public void setGameManager(GameManager gameController){
        this.gameController= gameController;
    }

    /**
     * @return true if the player lost
     */
    public boolean hasLost() {
        return hasLost;
    }

    /**
     * @param hasLost set hasLost condition to true if player lost
     */
    public void setHasLost(boolean hasLost) {
        this.hasLost = hasLost;
    }

    /* Virtual View is an observer of the Model*/
    @Override
    public void update(Message message){
        //update client view
         sendToClient((ToClientMessage) message);
    }


    // the virtual view is added to the observers list of Game
    @Override
    public void setObservable(Observable observable){
        observable.register(this);
    }

    /* Virtual View is observable for the Turn Manager*/

    /**
     * Method sends messages from Virtual View to the related client
     * @param msg Message for the client that owns the Virtual View
     */
    public void sendToClient(ToClientMessage msg) {
        if (ServerManager.getServer().isConnected(playerId))
            ServerManager.getServer().messageClient(msg, playerId);
    }
}
