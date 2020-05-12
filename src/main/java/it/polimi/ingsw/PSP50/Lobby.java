package it.polimi.ingsw.PSP50;

import it.polimi.ingsw.PSP50.Controller.GameManager;
import it.polimi.ingsw.PSP50.Model.GameType;
import it.polimi.ingsw.PSP50.view.VirtualView;


import java.util.concurrent.ConcurrentHashMap;

public class Lobby {
    private ConcurrentHashMap<Integer, VirtualView> players;
    private ConcurrentHashMap<String, VirtualView> nicknames;
    private GameManager gameManager;
    private boolean isFull;
    private GameType type;

    public Lobby(GameType type){
        this.type = type;
        this.isFull = false;
        players=new ConcurrentHashMap<>();
        nicknames=new ConcurrentHashMap<>();
    }

    public void addPlayer(int user, VirtualView view) {
        players.put(user,view);
        nicknames.put(view.getPlayerName(),view);
        if (players.size()==type.getSize())
            isFull=true;
    }

    public void startGame(){
        if (isFull) {
            this.gameManager= new GameManager(nicknames);
            gameManager.run();
        }
       // else do nothing
    }

    public GameType getType() {
        return type;
    }

    public void setType(GameType type) {
        this.type = type;
    }

    public void setFull(boolean isFull) {
        this.isFull = isFull;
    }

    public boolean isFull() {
        return isFull;
    }

}
