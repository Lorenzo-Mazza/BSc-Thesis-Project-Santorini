package it.polimi.ingsw.PSP50;

import it.polimi.ingsw.PSP50.network.messages.Message;

import java.util.ArrayList;

//Class inherited by every class that is observable

public abstract class Observable {
    private ArrayList<Observer> observers;

    //methods to register and unregister observers
    public void register(Observer obj){
         observers.add(obj);
         obj.setObservable(this);
     }
     public void unregister(Observer obj){
         observers.remove(obj);
         obj.setObservable(null);
     }

    //method to notify all the observers
     public void notifyObservers(Message msg){
         for (Observer obj:observers
              ) {
             obj.update(msg);
         }
     }

  //  public abstract Observer getUpdate(Observer obj);

}

