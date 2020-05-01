package it.polimi.ingsw.PSP50.socket;

import java.io.IOException;
import java.net.Socket;

public class Client {
    public static void main(String[] args) {
        int port = 12345;
        String addr = "localhost";
        connect(addr, port);
    }

    public static void connect(String addr, int port) {
        try {
            Socket server = new Socket(addr, port);
            System.out.println("Connection established with " + server.getInetAddress());
            /* start 2 separate threads for input and output */
            Thread read = new Thread(new ThreadRead(server));
            read.start();
            Thread write = new Thread(new ThreadSend(server));
            write.start();
        } catch (IOException e) {
            System.out.println("Could not connect to " + addr + " on port " + port);
        }
    }
}
