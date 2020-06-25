package it.polimi.ingsw.PSP50.network.server;

/**
 * Server manager launches the server and has a static reference to it
 */
public class ServerManager {
    /**
     * reference to server
     */
    private static Server socketServer = new Server();


    public static void main( String[] args ) {
        socketServer.start();

    }

    /**
     * get the server reference
     * @return server
     */
    public static Server getServer(){
        return socketServer;
    }







}
