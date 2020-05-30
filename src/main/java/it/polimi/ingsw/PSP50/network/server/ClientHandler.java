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


public class ClientHandler implements Runnable
{
    private Socket client;
    private Server server;
    private String user;
    private int playerId;
    private ObjectOutputStream out;
    private ObjectInputStream in;
    private VirtualView view;
    private GameType gameType;
    private Lobby playerLobby;

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


    private void registry(Socket client)  {
        server.getConnections().put(playerId,this);
        server.getNicknames().put(playerId,user);
        server.getViews().put(playerId,view);
        //give to client his player ID
        ToClientMessage idMessage= new PlayerIdMessage(playerId);
        server.messageClient(idMessage,playerId);
        System.out.println("User" + playerId+ "accepted on SocketServer");
    }

    private synchronized void manageDisconnection() {
        for (int playerId : playerLobby.getPlayers().keySet()){
            if (playerId != this.playerId && playerLobby.getGameManager().getGame().getWinner()== null)
                server.messageClient(new DisconnectMessage(this.user), playerId);
        }
        playerLobby.getGameManager().stopThread();
        server.getViews().remove(this.playerId);
        server.getNicknames().remove(this.playerId);
        server.getConnections().remove(this.playerId);

        this.playerLobby.removeClient(this.user, this.playerId, this.view);
    }


  public ObjectOutputStream getOutput() {
    return out;
  }
  public ObjectInputStream getInput() {
    return in;
  }
}
