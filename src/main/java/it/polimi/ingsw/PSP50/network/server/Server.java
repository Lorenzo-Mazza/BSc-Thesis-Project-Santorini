package it.polimi.ingsw.PSP50.network.server;

import it.polimi.ingsw.PSP50.View.VirtualView;
import it.polimi.ingsw.PSP50.network.messages.ServerMessage;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;


public class Server extends Thread{
  public final static int SOCKET_PORT = 7777;
  private static Map<String, ClientHandler> connections;
  private static ServerSocket serverSocket;
  private static Map<String, VirtualView> views;

  public Server() {
    connections = new HashMap();
    views = new HashMap<>();
  }

  public Map<String, ClientHandler> getConnections() {
    return connections;
  }

  public Map<String, VirtualView> getViews() {
    return views;
  }



  @Override
  public void run()
  {
    try {
      serverSocket = new ServerSocket(SOCKET_PORT);
    } catch (IOException e) {
      System.out.println("cannot open server socket");
      System.exit(1);
      return;
    }
    while (true) {
      try {
          /* accepts connections; for every connection accepted,
           * create a new Thread executing a ClientHandler */
          Socket client = serverSocket.accept();
          Thread.sleep(500);
          ObjectOutputStream output = new ObjectOutputStream(client.getOutputStream());
          output.flush();
          ObjectInputStream input = new ObjectInputStream(client.getInputStream());
          String user = (String) input.readObject();
          VirtualView virtualView;
          virtualView = new VirtualView(user);


          ClientHandler clientHandler = new ClientHandler(client,this,output,input,virtualView);
          Thread thread = new Thread(clientHandler, "server_" + client.getInetAddress());
          thread.start();

      }
      catch (IOException | ClassNotFoundException | InterruptedException e) {
          System.out.println("connection dropped");
        }
    }

  }

  public void interpretMessage(ServerMessage msg) {
    //virtual view associated to the right sender notifies the controller
    getVirtualView(msg.getSender()).notifyObservers(msg);
  }

  public VirtualView getVirtualView(String user) {
    return views.get(user);
  }

}
