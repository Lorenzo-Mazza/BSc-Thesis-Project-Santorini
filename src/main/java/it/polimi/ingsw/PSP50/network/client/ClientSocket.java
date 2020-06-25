package it.polimi.ingsw.PSP50.network.client;

import it.polimi.ingsw.PSP50.Model.GameType;
import it.polimi.ingsw.PSP50.Utils.Observable;
import it.polimi.ingsw.PSP50.Utils.Observer;
import it.polimi.ingsw.PSP50.View.ClientView;
import it.polimi.ingsw.PSP50.network.messages.ToClientMessage;
import it.polimi.ingsw.PSP50.network.messages.Message;
import it.polimi.ingsw.PSP50.network.messages.ToServerMessage;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;

/**
 * Client Socket is the socket that the client uses to communicate with the server
 */
public class ClientSocket implements Runnable, Observer {
    /**
     * reference to the type of game that the client is playing
     */
    private final GameType gameType;
    /**
     * reference to client's UI
     */
    private ClientView userInterface;
    /**
     * reference to the socket used to send/receive messages
     */
    private Socket serverSocket;
    /**
     * stream used to send messages to server
     */
    private ObjectOutputStream toServer;
    /**
     * stream used to receive messages from server
     */
    private ObjectInputStream fromServer;

    /**
     * Constructor
     * @param userInterface  client's UI
     * @param gameType type of game the client is playing
     * @param serverSocket socket used to communicate
     */
    public ClientSocket(ClientView userInterface, GameType gameType, Socket serverSocket) {
        this.userInterface = userInterface;
        this.serverSocket = serverSocket;
        this.gameType= gameType;
    }

    /**
     * Run method: first send the client's username and type of game to the server;
     * then call receiveFromServer method.
     */
    @Override
    public void run() {
        try {
            toServer = new ObjectOutputStream(serverSocket.getOutputStream());
            fromServer = new ObjectInputStream(serverSocket.getInputStream());
            toServer.writeObject(userInterface.getName());
            toServer.flush();
            toServer.reset();
            toServer.writeObject(gameType);
            toServer.flush();
            receiveFromServer();

        } catch (IOException e) {
            System.out.println("server has died");
        } catch (ClassCastException | ClassNotFoundException e) {
            System.out.println("protocol violation");
        }

        stop();
    }

    /**
     * Always listening to incoming messages from the server
     * @throws IOException invalid input
     * @throws ClassNotFoundException invalid class
     */
    private void receiveFromServer() throws IOException, ClassNotFoundException {

        // always listening
        while (true) {
            ToClientMessage msg;
            msg = (ToClientMessage) fromServer.readObject();
            if (msg != null)
                interpretMessage(msg);
            }
    }

    /**
     * Execute the command specified in the message
     * @param msg Message received from server
     */
    public void interpretMessage(ToClientMessage msg) {
        msg.doAction(userInterface);
    }


    /**
     * Sends the updates to the User's Virtual View
     * @param msg Message to be sent to the Server
     */
    private synchronized void sendToServer(ToServerMessage msg) {
        try {
            toServer.writeObject(msg);
            toServer.flush();
            toServer.reset();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }



    /**
     * Method implements the observer/observable pattern. Whenever the UI calls a notify, this method is called.
     * @param arg Message to be sent to the Server
     */
    @Override
    public void update(Message arg) {
        sendToServer((ToServerMessage) arg);
    }

    /**
     * Method makes the client an Observer of the UI. It implements the observer/observable pattern.
     */
    @Override
    public void setObservable(Observable view){
        this.userInterface.register(this);
    }

    /**
     * Close the streams and the socket
     */
    public void stop() {
        try {
            toServer.close();
            fromServer.close();
            serverSocket.close();
        } catch (IOException e) {
            System.out.println("socket cannot be closed: " + e.getMessage());
        }
    }


}

