package it.polimi.ingsw.PSP50.network.client;

import it.polimi.ingsw.PSP50.Model.GameType;
import it.polimi.ingsw.PSP50.View.CLI;
import it.polimi.ingsw.PSP50.View.ClientView;
import it.polimi.ingsw.PSP50.network.server.Server;

import java.io.IOException;
import java.net.Socket;
import java.util.Scanner;


public class Client implements Runnable {

    private ClientView userView;
    private GameType gameType;


    public static void main( String[] args ) {
        Client client = new Client();
        client.run();
    }



    @Override
    public void run() {
        /*
         *  this method executes IN THE CONTEXT OF THE MAIN THREAD
         */

        Scanner scanner = new Scanner(System.in);

        System.out.println("IP address of server?");
        String ip = scanner.nextLine();

        /* open a connection to the server */
        Socket server;
        try {
            server = new Socket(ip, Server.SOCKET_PORT);
        } catch (IOException e) {
            System.out.println("server unreachable");
            return;
        }
        System.out.println("Connected");
        this.userView=genUi(scanner);
        this.gameType=chooseGame(scanner);

        /* Create the client socket that will allow communication with the server */
        ClientSocket socket = new ClientSocket(userView, gameType, server);
        Thread socketThread = new Thread(socket);
        socketThread.start();
    }


    private ClientView genUi(Scanner scanner)  {
        ClientView generatedUi;
        String ui;
        System.out.println("\nWhat interface do you wanna use to play Santorini? Options are: cli");
        while(true) {
            ui=scanner.nextLine();
            if ("cli".equals(ui)) {
                generatedUi = new CLI();
                System.out.println("your CLI has been generated!");
                break;
            }
   /* else if ("gui".equals(ui)) {
      generatedUi = new GUI();
      System.out.println("your GUI has been generated!");
    }*/
            else {
                System.out.println("Wrong ui param. Valid are <cli>");
            }
        }
        return generatedUi;
    }

    private GameType chooseGame(Scanner scanner){
      int numberOfPlayers;
      GameType type;
      System.out.println("\nHow many players do you wanna play against? (write an integer, options are: 1,2");
      while(true) {
          numberOfPlayers=scanner.nextInt();
          if (numberOfPlayers==1) {
              type=GameType.TWOPLAYERS;
              System.out.println("\nStart looking for a two-players lobby!");
              break;
          }
          if (numberOfPlayers==2) {
              type=GameType.THREEPLAYERS;
              System.out.println("\nStart looking for a three-players lobby!");
              break;
          }
          else {
              System.out.println("Wrong param. Valid are <1>,<2>.");
          }
      }
      return type;
  }

}
