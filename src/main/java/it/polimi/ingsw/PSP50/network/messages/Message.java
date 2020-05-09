package it.polimi.ingsw.PSP50.network.messages;

import java.io.Serializable;

public abstract class Message implements Serializable {

    protected Object data;

    public Message(Object data){
        this.data= data;
    }

    public Object getData(){
        return this.data;
    }

}
