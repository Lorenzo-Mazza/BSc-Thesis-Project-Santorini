package it.polimi.ingsw.PSP50.socket;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.Socket;

public class ThreadRead implements Runnable {
    private Socket socket;

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
