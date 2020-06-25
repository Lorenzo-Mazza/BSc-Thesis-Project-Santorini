package it.polimi.ingsw.PSP50.Utils;

import it.polimi.ingsw.PSP50.network.messages.Message;

import java.util.ArrayList;

//Class inherited by every class that is observable

public abstract class Observable {
    private ArrayList<Observer> observers=new ArrayList<>();

    //methods to register and unregister observers

    public void register(Observer obj){
        observers.add(obj);
    }
    public void unregister(Observer obj){
        observers.remove(obj);
    }

    //method to notify all the observers
    public void notifyObservers(Message msg){
        for (Observer obj:observers
        ) {
            obj.update(msg);
        }
    }

}

