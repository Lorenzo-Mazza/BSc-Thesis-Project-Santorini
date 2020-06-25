package it.polimi.ingsw.PSP50.network.messages;

import java.io.Serializable;

/**
 * Abstract class representing a generic message
 */
public abstract class Message implements Serializable {
    /**
     * Data inside the message (payload)
     */
    protected Object data;

    /**
     * Constructor
     * @param data Payload
     */
    public Message(Object data){
        this.data= data;
    }

    /**
     * Get the data inside the message
     * @return data
     */
    public Object getData(){
        return this.data;
    }

}
