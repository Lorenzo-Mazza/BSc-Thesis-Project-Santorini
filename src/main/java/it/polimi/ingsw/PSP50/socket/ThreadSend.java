package it.polimi.ingsw.PSP50.socket;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;

/**
 * Thread ran by the client to send user actions to the server
 */
public class ThreadSend implements Runnable {
    /**
     * TCP connection with server
     */
    private Socket socket;

    /**
     * Constructor
     * @param socket TCP connection with server
     */
    public ThreadSend(Socket socket) {
        this.socket = socket;
    }

    @Override
    public void run() {
        try {
            PrintWriter out = new PrintWriter(socket.getOutputStream(), true);
            BufferedReader stdIn = new BufferedReader( new InputStreamReader(System.in));
            /* send requests to server */
            while (true) {
                out.println(stdIn.readLine()); /* EXAMPLE: strings from standard input */
            }
        } catch (IOException e) {
            System.out.println("Connection lost");
        }
    }
}
