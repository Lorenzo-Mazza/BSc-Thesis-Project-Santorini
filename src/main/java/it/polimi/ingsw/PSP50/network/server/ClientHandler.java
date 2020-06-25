package it.polimi.ingsw.PSP50.network.server;

import it.polimi.ingsw.PSP50.Controller.GameManager;
import it.polimi.ingsw.PSP50.Lobby;
import it.polimi.ingsw.PSP50.Model.GameType;
import it.polimi.ingsw.PSP50.View.VirtualView;
import it.polimi.ingsw.PSP50.network.messages.ToClient.DisconnectMessage;
import it.polimi.ingsw.PSP50.network.messages.ToClient.PlayerIdMessage;
import it.polimi.ingsw.PSP50.network.messages.ToClientMessage;
import it.polimi.ingsw.PSP50.network.messages.ToServerMessage;

import java.io.*;
import java.net.Socket;

/**
 * Client Handler is used by Server to handle at the same time multiple client connections
 */
public class ClientHandler implements Runnable
{
    /**
     * Socket used to communicate with the client
     */
    private Socket client;
    /**
     * Reference to the server
     */
    private Server server;
    /**
     * Client's name
     */
    private String user;
    /**
     * Client's ID
     */
    private int playerId;
    /**
     * Stream to send messages to the client
     */
    private ObjectOutputStream out;
    /**
     * Stream to receive messages from the client
     */
    private ObjectInputStream in;
    /**
     * Virtual view associated with the client
     */
    private VirtualView view;
    /**
     * Type of game that the client chose to play
     */
    private GameType gameType;
    /**
     * Lobby where the client is in
     */
    private Lobby playerLobby;

    /**
     * Constructor
     */
    ClientHandler(Socket client, Server server, ObjectOutputStream out, ObjectInputStream in, VirtualView view, GameType gameType)
    {
        this.client = client;
        this.server = server;
        this.out = out;
        this.in = in;
        this.view = view;
        this.user = view.getPlayerName();
        this.playerId = view.getPlayerId();
        this.gameType = gameType;
    }

    /**
     * Run the Client Handler for the entire course of the game
     */
    @Override
    public void run() {
        try {
            handleClientConnection();
        } catch (IOException e) {
            System.out.println("client " + client.getInetAddress() + " connection dropped!");
            if(!playerLobby.isInGame()) {
              playerLobby.removeClient(this.user, this.playerId, this.view);
            }
            else
                manageDisconnection();
        }
    }

    /**
     * Handle the connection: be ready to receive messages from the client in any moment
     * @throws IOException Invalid input/output
     */
  private void handleClientConnection() throws IOException
  {
    registry(client);
    System.out.println("Connected to " + client.getInetAddress());
    addToLobby();

    try {
      while (!client.isClosed()) {
        ToServerMessage msg;
        msg = (ToServerMessage) in.readObject();
        if (msg != null)
          server.interpretMessage(msg);
        Thread.sleep(100);
      }
    } catch (ClassNotFoundException | ClassCastException | InterruptedException e) {
      System.out.println("invalid stream from client");
    }
    in.close();
    out.close();
    client.close();
  }

    /**
     * Add client to a compatible lobby
     */
  private void addToLobby() {
      playerLobby = this.server.getFirstAvailableLobby(gameType);
      if (!playerLobby.isFull()){
          playerLobby.addPlayer(playerId,view);
          if (playerLobby.isFull()){
              playerLobby.setInGame();
              server.startLobby(playerLobby);
          }
      }
    }

    /**
     * Register the client in the Server data
     * @param client the client that is being registered
     */
    private void registry(Socket client)  {
        server.getConnections().put(playerId,this);
        server.getNicknames().put(playerId,user);
        server.getViews().put(playerId,view);
        //give to client his player ID
        ToClientMessage idMessage= new PlayerIdMessage(playerId);
        server.messageClient(idMessage,playerId);
        System.out.println("User" + playerId+ "accepted on SocketServer");
    }

    /**
     * Manage the disconnection of a client
     */
    private synchronized void manageDisconnection() {
        for (int playerId : playerLobby.getPlayers().keySet()){
            if (playerLobby.getGameManager()!=null) {
                // notify only if the player that has disconnected didn't lose before
                if (playerId != this.playerId && playerLobby.getGameManager().getGame().getWinner()== null
                        && !view.hasLost()) {
                    server.messageClient(new DisconnectMessage(this.user), playerId);
                    playerLobby.getGameManager().stopThread();
                    playerLobby.setGameManager(null);
                }
            }

        }
        server.getViews().remove(this.playerId);
        server.getNicknames().remove(this.playerId);
        server.getConnections().remove(this.playerId);

        playerLobby.removeClient(this.user, this.playerId, this.view);
    }

    /**
     * Get the output stream
     * @return the stream
     */
  public ObjectOutputStream getOutput() {
    return out;
  }

    /**
     * Get the input stream
     * @return the stream
     */
  public ObjectInputStream getInput() {
    return in;
  }
}
