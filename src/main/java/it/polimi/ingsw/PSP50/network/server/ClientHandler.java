package it.polimi.ingsw.PSP50.network.server;

import it.polimi.ingsw.PSP50.View.VirtualView;
import it.polimi.ingsw.PSP50.network.messages.ServerMessage;

import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.Scanner;
import java.util.concurrent.TimeUnit;


public class ClientHandler implements Runnable
{
  private Socket client;
  private Server server;
  private String user;
  private ObjectOutputStream out;
  private ObjectInputStream in;
  private VirtualView view;


  ClientHandler(Socket client, Server server, ObjectOutputStream out, ObjectInputStream in, VirtualView view)
  {
    this.client = client;
    this.server = server;
    this.out = out;
    this.in = in;
    this.view = view;
    this.user = view.getPlayerName();
  }


  @Override
  public void run()
  {
    try {
      handleClientConnection();
    } catch (IOException e) {
      System.out.println("client " + client.getInetAddress() + " connection dropped");
    }
  }

  private void registry(Socket client)  {
    server.getConnections().put(user,this);
    System.out.println("User accepted on SocketServer");
  }

  private void handleClientConnection() throws IOException
  {
    registry(client);
    System.out.println("Connected to " + client.getInetAddress());

    try {
      while (!client.isClosed()) {
        ServerMessage msg;
        msg = (ServerMessage) in.readObject();

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


  public ObjectOutputStream getOutput() {
    return out;
  }
  public ObjectInputStream getInput() {
    return in;
  }
}
