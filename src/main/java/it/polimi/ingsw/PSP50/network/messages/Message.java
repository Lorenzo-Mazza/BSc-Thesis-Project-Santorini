package it.polimi.ingsw.PSP50.network.messages;

import java.io.Serializable;

public abstract class Message implements Serializable {
     abstract void doAction();

}
