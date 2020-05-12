package it.polimi.ingsw.PSP50.view;

import it.polimi.ingsw.PSP50.Controller.GameManager;
import it.polimi.ingsw.PSP50.Observable;
import it.polimi.ingsw.PSP50.Observer;
import it.polimi.ingsw.PSP50.network.messages.ToClientMessage;
import it.polimi.ingsw.PSP50.network.messages.Message;
import it.polimi.ingsw.PSP50.network.server.ServerManager;
/**
 *  Server-side View
 */
public class VirtualView extends Observable implements Observer {

    private GameManager gameController;
    private String playerName;
    private int playerId;


    public VirtualView(int playerId,String playerName){
        this.playerId=playerId;
        this.playerName= playerName;
    }

    public int getPlayerId(){
        return this.playerId;
    }

    public String getPlayerName() {
        return this.playerName;
    }

    public GameManager getGameManager() {
        return gameController;
    }

    public void setGameManager(GameManager gameController){
        this.gameController= gameController;
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
        gameController.getGame().register(this);
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
