package it.polimi.ingsw.PSP50.network.server;

import it.polimi.ingsw.PSP50.Controller.GameManager;
import it.polimi.ingsw.PSP50.View.VirtualView;

import java.net.Socket;
import java.util.*;

public class ServerManager {

    private static Server socketServer = new Server();


    public static void main(int[] args){
        socketServer.start();

    }


    public static Server getServer(){
        return socketServer;
    }







}
