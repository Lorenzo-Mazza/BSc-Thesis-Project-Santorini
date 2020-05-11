package it.polimi.ingsw.PSP50.network.server;

import it.polimi.ingsw.PSP50.Lobby;
import it.polimi.ingsw.PSP50.Model.GameType;
import it.polimi.ingsw.PSP50.View.VirtualView;
import it.polimi.ingsw.PSP50.network.messages.ToClientMessage;
import it.polimi.ingsw.PSP50.network.messages.ToServerMessage;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

public class Server extends Thread{

    public final static int SOCKET_PORT = 7777;
    private static  Map<Integer, ClientHandler> connections = new HashMap<>();
    private static Map<Integer, VirtualView> views = new HashMap<>();
    private final Map<Integer, String> nicknames = new HashMap<>();
    private static ConcurrentHashMap<Integer, Lobby> lobbies= new ConcurrentHashMap<>();
    //integer that identifies how many lobbies are active on the server
    private int numberOfLobbies =0;
    //integer that identifies every single player on the server
    private int playerId=-1;

    public Map<Integer, String> getNicknames() {
        return nicknames;
    }

    public Map<Integer, ClientHandler> getConnections() {
        return connections;
    }


    public Map<Integer, VirtualView> getViews() {
        return views;
    }

    public int getNumberOfLobbies() {
        return numberOfLobbies;
    }

    public static ConcurrentHashMap<Integer, Lobby> getLobbies() {
        return lobbies;
    }
    /*
    ** WARNING: Method called in the context of the Client Handler class. The last added client starts the game.
     **  Starts the lobby and the game main engine.
     */
    public synchronized void startLobby(Lobby lobby){
        lobby.startGame();
    }

    /*
    **  Return the first available Lobby for a specific type of game.
     */
    public Lobby getFirstAvailableLobby(GameType type){
        Lobby firstAvailableLobby=null;
        int index=0;
        while ((index < this.numberOfLobbies) && (lobbies.get(index) != null)) {
            if ((!lobbies.get(index).isFull()) && (lobbies.get(index).getType()==type)) {
                firstAvailableLobby=lobbies.get(index);
            }
            index++;
        }
        if (firstAvailableLobby==null){
            addLobby(index,type);
            firstAvailableLobby=lobbies.get(index);
        }
        return firstAvailableLobby;
    }

    /*
     **  Creates a new Lobby for a specific type of game.
     */
    private synchronized void addLobby (int lobbyNumber, GameType type){
        lobbies.put(lobbyNumber, new Lobby(type));
        numberOfLobbies++;
    }


    public boolean isConnected(Integer playerId) {
        return connections.containsKey(playerId);
    }



    @Override
    public void run()
    {
        ServerSocket serverSocket;
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
                //debugging
                System.out.println("Client name=" + user);
                GameType gameType= (GameType) input.readObject();
                VirtualView virtualView = new VirtualView(++playerId,user);
                ClientHandler clientHandler = new ClientHandler(client, this, output, input, virtualView,gameType);
                Thread thread = new Thread(clientHandler, "server_" + client.getInetAddress());
                thread.start();

            } catch (IOException | ClassNotFoundException | InterruptedException e) {
                System.out.println("connection dropped");
            }
        }


    }

    public void interpretMessage(ToServerMessage msg) {
        //virtual view associated to the right sender notifies the controller
        getVirtualView(msg.getPlayerId()).notifyObservers(msg);
    }

    public VirtualView getVirtualView(int playerId) {
        return views.get(playerId);
    }


    public void messageClient(ToClientMessage msg, int playerId) {
        try {
            ClientHandler client=connections.get(playerId);
            ObjectOutputStream outStream = client.getOutput();
            outStream.writeObject(msg);
            outStream.flush();
            outStream.reset();
        } catch (IOException e){
            System.out.println("invalid stream from server");
        }
    }



}
