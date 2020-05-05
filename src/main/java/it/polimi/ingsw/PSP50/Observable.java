package it.polimi.ingsw.PSP50;

public interface Observable {
    //methods to register and unregister observers
     void register(Observer obj);
     void unregister(Observer obj);

    //method to notify observers of change
     void notifyObservers();

    //method to get updates from subject
     Observer getUpdate(Observer obj);

}

