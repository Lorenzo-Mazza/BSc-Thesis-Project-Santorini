package it.polimi.ingsw.PSP50;

import it.polimi.ingsw.PSP50.network.messages.Message;

public interface Observer {

        //method to update the observer, used by subject
         void update( Message message);

        //attach with subject to observe
         void setObservable(Observable observable);

}
