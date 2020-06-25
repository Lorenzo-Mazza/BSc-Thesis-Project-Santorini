package it.polimi.ingsw.PSP50;

import it.polimi.ingsw.PSP50.Controller.GameManager;
import it.polimi.ingsw.PSP50.Model.GameType;
import it.polimi.ingsw.PSP50.View.VirtualView;
import it.polimi.ingsw.PSP50.network.messages.ToClient.NameChanged;
import it.polimi.ingsw.PSP50.network.server.ServerManager;


import java.util.concurrent.ConcurrentHashMap;

/**
 * Lobby class is where the clients are put to play against each other
 * Every lobby has a type (Two or Three players)
 * When the game ends, the lobby is freed and can be reused
 */
public class Lobby implements Runnable{
    /**
     * Map (Player ID, Player's virtual view) of every player inside a lobby
     */
    private ConcurrentHashMap<Integer, VirtualView> players;
    /**
     * Map (Player name, Player's virtual view) of every player inside a lobby
     */
    private ConcurrentHashMap<String, VirtualView> nicknames;
    /**
     * Game manager of the game played in the lobby
     */
    private GameManager gameManager;
    /**
     * Boolean flag set to true if the lobby is full
     */
    private boolean isFull;
    /**
     * Type of game played in the lobby
     */
    private GameType type;
    /**
     * Boolean flag set to true if the game is over
     */
    private boolean isOver;
    /**
     * Boolean flag set to true if the game is on
     */
    private boolean inGame;

    /**
     * Constructor
     * @param type type of game played in the lobby (2/3 players)
     */
    public Lobby(GameType type){
        this.type = type;
        this.isFull = false;
        this.inGame = false;
        isOver = false;
        players = new ConcurrentHashMap<>();
        nicknames = new ConcurrentHashMap<>();
    }

    /**
     * Add a player to the lobby
     * @param user Player ID
     * @param view Player's virtual view
     */
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

    /**
     * Create a game manager and run the game in the lobby
     */
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

    /**
     * Get the lobby type of game
     * @return the type of game
     */
    public GameType getType() {
        return type;
    }

    /**
     * Set the lobby type of game
     * @param type the type of game
     */
    public void setType(GameType type) {
        this.type = type;
    }

    /**
     * Set the lobby to full when the maximum capacity is reached
     * @param isFull Boolean flag to indicate that the lobby is full
     */
    public void setFull(boolean isFull) {
        this.isFull = isFull;
    }

    /**
     * Check if the lobby is full
     * @return Boolean flag set to true if the lobby is full
     */
    public boolean isFull() {
        return isFull;
    }

    /**
     * Free the lobby
     */
    public void freeLobby(){
        isOver=true;
    }

    /**
     * Check if the game inside the lobby is over
     * @return Boolean flag set to true if the game is over
     */
    public boolean isOver(){
        return isOver;
    }

    /**
     * Set the inGame flag to true when the game starts
     */
    public void setInGame() {
        this.inGame = true;
    }

    /**
     * Check if the game in the lobby already started
     * @return Boolean flag inGame
     */
    public boolean isInGame() {
        return  this.inGame;
    }

    /**
     * Get the players inside the lobby
     * @return Map of (Player ID, virtual view)
     */
    public ConcurrentHashMap<Integer, VirtualView> getPlayers() {
        return players;
    }

    /**
     * Remove player from the lobby
     * @param name Player's name
     * @param id Player ID
     * @param client Player's virtual view
     */
    public void removeClient(String name, int id, VirtualView client) {
            this.players.remove(id, client);
            this.nicknames.remove(name, client);
            if(players.size() == 0)
                freeLobby();
    }

    /**
     * Get game manager of the lobby game
     * @return Game manager
     */
    public GameManager getGameManager() {
        return gameManager;
    }

    /**
     * Set game manager of the lobby
     * @param gameManager The game manager
     */
    public void setGameManager(GameManager gameManager) {
        this.gameManager = gameManager;
    }

    /**
     * Run the game in the lobby and at the end free the lobby
     */
    @Override
    public void run() {
        startGame();
        freeLobby();
    }

}
