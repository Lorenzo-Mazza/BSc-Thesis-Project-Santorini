package it.polimi.ingsw.PSP50.View;

import it.polimi.ingsw.PSP50.Controller.GameManager;
import it.polimi.ingsw.PSP50.Observable;
import it.polimi.ingsw.PSP50.Observer;
import it.polimi.ingsw.PSP50.network.client.Client;
import it.polimi.ingsw.PSP50.network.server.ServerManager;


/*
**  Server-side View
 */
public class VirtualView implements Observer,Observable {


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
    public void update(){
      //update view
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


    @Override
    public void notifyObservers(){
    }

    @Override
    public Observer getUpdate(Observer obj){
        return obj;
    }
}
