package it.polimi.ingsw.PSP50.socket;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;

public class ThreadSend implements Runnable {
    private Socket socket;

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
