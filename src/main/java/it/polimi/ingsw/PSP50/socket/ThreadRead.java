package it.polimi.ingsw.PSP50.socket;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.Socket;

/**
 * Thread ran by the client to listen for notifications from the server
 */
public class ThreadRead implements Runnable {
    /**
     * TCP connection with server
     */
    private Socket socket;

    /**
     * Constructor
     * @param socket TCP connection with server
     */
    public ThreadRead(Socket socket) {
        this.socket = socket;
    }

    @Override
    public void run() {
        try {
            BufferedReader in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
            /* read responses from server */
            while (true) {
                String message = in.readLine();
                System.out.println(socket.getInetAddress() + " says: " + message);
            }
        } catch (IOException e) {
            System.out.println("Connection lost");
        }
    }
}
