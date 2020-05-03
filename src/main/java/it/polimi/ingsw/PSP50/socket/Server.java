package it.polimi.ingsw.PSP50.socket;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;

public class Server {
    public static void main(String[] args) {
        int port = 12345;
        open(port);
    }

    /**
     * Opens a TCP server socket
     * @param port server port number - suggested range from 49152 to 65535 (ephemeral ports)
     */
    public static void open(int port) {
        try (ServerSocket socket = new ServerSocket(port)) {
            System.out.println("Server listening on port " + port);
            while (true) {
                Socket client = new Socket();
                try {
                    client = socket.accept();
                    System.out.println("New client accepted: " + client.getInetAddress());
                    /* start a thread to handle requests from new client */
                    Thread handler = new Thread(new ServerHandler(client));
                    handler.start();
                } catch (IOException e) {
                    System.out.println("Client dropped: " + client.getInetAddress());
                }
            }
        } catch (IOException e) {
            System.out.println("Server stopped");
        }
    }
}
