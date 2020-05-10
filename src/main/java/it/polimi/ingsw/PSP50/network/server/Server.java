package it.polimi.ingsw.PSP50.network.server;

import it.polimi.ingsw.PSP50.View.VirtualView;
import it.polimi.ingsw.PSP50.network.messages.ToClientMessage;
import it.polimi.ingsw.PSP50.network.messages.ToServerMessage;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.InetAddress;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.HashMap;
import java.util.Map;

public class Server extends Thread{
  public final static int SOCKET_PORT = 7777;
  private static Map<String, ClientHandler> connections;
  private static ServerSocket serverSocket;
  private static Map<String, VirtualView> views;

   Server() {
    connections = new HashMap();
    views = new HashMap<>();
  }

  public Map<String, ClientHandler> getConnections() {
    return connections;
  }

  public Map<String, VirtualView> getViews() {
    return views;
  }

  public boolean isConnected(String user) {
        return connections.containsKey(user);
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
              InetAddress ip = client.getInetAddress();
              System.out.println(ip);
              Thread.sleep(500);
              ObjectOutputStream output = new ObjectOutputStream(client.getOutputStream());
              output.flush();
              ObjectInputStream input = new ObjectInputStream(client.getInputStream());
              String user = (String) input.readObject();
              VirtualView virtualView = new VirtualView(user);

              ClientHandler clientHandler = new ClientHandler(client, this, output, input, virtualView);
              Thread thread = new Thread(clientHandler, "server_" + client.getInetAddress());
              thread.start();

          } catch (IOException | ClassNotFoundException | InterruptedException e) {
              System.out.println("connection dropped");
          }
      }


  }

  public void interpretMessage(ToServerMessage msg) {
    //virtual view associated to the right sender notifies the controller
    getVirtualView(msg.getSender()).notifyObservers(msg);
  }

  public VirtualView getVirtualView(String user) {
    return views.get(user);
  }


  public void messageClient(ToClientMessage msg, String user) {
      try {
          ClientHandler client=connections.get(user);
          ObjectOutputStream outStream = client.getOutput();
          outStream.writeObject(msg);
          outStream.flush();
          outStream.reset();
      } catch (IOException e){
          System.out.println("invalid stream from server");
      }
  }



}
