package it.polimi.ingsw.PSP50.network.client;

import it.polimi.ingsw.PSP50.Observable;
import it.polimi.ingsw.PSP50.Observer;
import it.polimi.ingsw.PSP50.View.ClientView;
import it.polimi.ingsw.PSP50.network.messages.ClientMessage;
import it.polimi.ingsw.PSP50.network.messages.Message;
import it.polimi.ingsw.PSP50.network.messages.ServerMessage;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;

public class ClientSocket implements Runnable, Observer {

    private ClientView userInterface;
    private Socket serverSocket;
    private ObjectOutputStream toServer;
    private ObjectInputStream fromServer;


    ClientSocket(ClientView userInterface, Socket serverSocket) {
        this.userInterface = userInterface;
        this.serverSocket = serverSocket;
    }


    @Override
    public void run() {
        try {
            toServer = new ObjectOutputStream(serverSocket.getOutputStream());
            fromServer = new ObjectInputStream(serverSocket.getInputStream());
            toServer.flush();
            receiveFromServer();

        } catch (IOException e) {
            System.out.println("server has died");
        } catch (ClassCastException | ClassNotFoundException e) {
            System.out.println("protocol violation");
        }

        stop();
    }


    private synchronized void receiveFromServer() throws IOException, ClassNotFoundException {

        // always listening
        while (true) {
            ClientMessage msg;
            msg = (ClientMessage) fromServer.readObject();
            if (msg != null)
                interpretMessage(msg);
            }
    }

    public void interpretMessage(ClientMessage msg) {
        msg.doAction(userInterface);
    }


    /**
     * Sends the updates to the User's Virtual View
     * @param msg Message to be sent to the Server
     */
    private synchronized void sendToServer(ServerMessage msg) {
        try {
            toServer.writeObject(msg);
            toServer.flush();
            toServer.reset();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }


    /**
     * It receives the updates from the User Interface
     * @param arg GUI/CLI message
     */
    @Override
    public void update(Message arg) {
        sendToServer((ServerMessage) arg);
    }

    //do nothing
  //  @Override
    public void setObservable(Observable view){
    }


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

