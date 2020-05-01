package it.polimi.ingsw.PSP50.socket;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;

public class ServerHandler implements Runnable {
    private Socket socket;

    public ServerHandler(Socket socket) {
        this.socket = socket;
    }

    @Override
    public void run() {
        try {
            BufferedReader in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
            PrintWriter out = new PrintWriter(socket.getOutputStream(), true);

            while (true) {
                /* read request from client */
                String message = in.readLine();
                /* reply with something */
                out.println(message.toUpperCase()); /* EXAMPLE: client message all in caps */
            }
        } catch (IOException e) {
            System.out.println("Client left: " + socket.getInetAddress());
        }
    }
}