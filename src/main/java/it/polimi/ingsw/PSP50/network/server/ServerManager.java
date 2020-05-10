package it.polimi.ingsw.PSP50.network.server;

public class ServerManager {

    private static Server socketServer = new Server();


    public static void main( String[] args ) {
        socketServer.start();

    }


    public static Server getServer(){
        return socketServer;
    }







}
