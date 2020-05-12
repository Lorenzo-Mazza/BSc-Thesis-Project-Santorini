package it.polimi.ingsw.PSP50.view;

import it.polimi.ingsw.PSP50.Observable;

import java.util.ArrayList;

/*
** ClientView is an abstract class that will be implemented by GUI and CLI
 */
public abstract class ClientView extends Observable {

    protected String name;

    private int playerId;

    public int getPlayerId() {
        return playerId;
    }

    public void setPlayerId(int playerId) {
        this.playerId = playerId;
    }

    public String getName() {
        return this.name;
    }

    public abstract void update(Object gameCopy);

    public abstract void drawSection(String line);

    public abstract void chooseSpace(ArrayList<int[]> possibleChoices);
}
