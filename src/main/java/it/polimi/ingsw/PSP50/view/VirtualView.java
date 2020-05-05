package it.polimi.ingsw.PSP50.View;

import it.polimi.ingsw.PSP50.Controller.GameManager;
import it.polimi.ingsw.PSP50.Observable;
import it.polimi.ingsw.PSP50.Observer;
import it.polimi.ingsw.PSP50.network.messages.ClientMessage;
import it.polimi.ingsw.PSP50.network.messages.Message;
import it.polimi.ingsw.PSP50.network.server.ServerManager;


/*
**  Server-side View
 */
public class VirtualView extends Observable implements Observer {

    private GameManager gameController;
    private String playerName;


    public VirtualView(String playerName){
        this.playerName= playerName;
    }




    public String getPlayerName() {
        return this.playerName;
    }

    public void setGameManager(GameManager gameController){
        this.gameController= gameController;
    }

    /* Virtual View is an observer of the Model*/
    @Override
    public void update(Message message){
        //update view
         sendToClient((ClientMessage) message);
    }


    //attach with subject to observe
    @Override
    public void setObservable(Observable observable){
        //set observable of the model
    }


    /* Virtual View is observable for the controller*/

    @Override
    public void register(Observer observer){
    }

    @Override
    public void unregister(Observer observer){
    }

   /* public void notifyObservers(Object arg, Message msg){
    } */

    @Override
    public Observer getUpdate(Observer obj){
        return obj;
    }



    /**
     * Method sends messages from Virtual View to the related client
     * @param msg Message for the client that owns the Virtual View
     */
    public void sendToClient(ClientMessage msg) {
        if (ServerManager.getServer().isConnected(playerName))
            ServerManager.getServer().messageClient(msg, playerName);
    }
}
