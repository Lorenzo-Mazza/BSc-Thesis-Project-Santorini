package it.polimi.ingsw.PSP50;

import it.polimi.ingsw.PSP50.Controller.GameManager;
import it.polimi.ingsw.PSP50.Model.GameType;
import it.polimi.ingsw.PSP50.View.VirtualView;
import it.polimi.ingsw.PSP50.network.messages.ToClient.NameChanged;
import it.polimi.ingsw.PSP50.network.server.ServerManager;


import java.util.concurrent.ConcurrentHashMap;

public class Lobby implements Runnable{
    private ConcurrentHashMap<Integer, VirtualView> players;
    private ConcurrentHashMap<String, VirtualView> nicknames;
    private GameManager gameManager;
    private boolean isFull;
    private GameType type;
    private boolean isOver;
    private boolean inGame;

    public Lobby(GameType type){
        this.type = type;
        this.isFull = false;
        this.inGame = false;
        isOver = false;
        players = new ConcurrentHashMap<>();
        nicknames = new ConcurrentHashMap<>();
    }

    public void addPlayer(int user, VirtualView view) {
        players.put(user,view);

        // If two players have the same name in the same lobby, it gives a unique name to every player
        int duplicate=1;
        for (String name: nicknames.keySet())
        {
            if (view.getPlayerName().equals(name))
            {
                duplicate++;
                view.setPlayerName(view.getPlayerName() + duplicate);
                view.sendToClient(new NameChanged(view.getPlayerName()));
            }

        }
        nicknames.put(view.getPlayerName(),view);
        if (players.size()==type.getSize())
            isFull=true;
    }

    public void startGame(){
        this.gameManager= new GameManager(nicknames);
        for (String name: nicknames.keySet()) {
                /*
                Set the Game Manager for every Virtual View
                */
            nicknames.get(name).setGameManager(this.gameManager);
        }
        gameManager.run();
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

    public void freeLobby(){
        isOver=true;
    }
    public boolean isOver(){
        return isOver;
    }
    public void setInGame() {
        this.inGame = true;
    }
    public boolean isInGame() {
        return  this.inGame;
    }
    public ConcurrentHashMap<Integer, VirtualView> getPlayers() {
        return players;
    }

    public void removeClient(String name, int id, VirtualView client) {
            this.players.remove(id, client);
            this.nicknames.remove(name, client);
            if(players.size() == 0)
                freeLobby();
    }

    @Override
    public void run() {
        startGame();
        freeLobby();
    }

}
