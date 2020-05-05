package it.polimi.ingsw.PSP50.network.client;

import it.polimi.ingsw.PSP50.View.CLI;
import it.polimi.ingsw.PSP50.View.UI;
import it.polimi.ingsw.PSP50.network.server.Server;

import java.io.IOException;
import java.net.Socket;
import java.util.Scanner;


public class Client implements Runnable, ServerObserver
{
  /* auxiliary variable used for implementing the consumer-producer pattern*/
  private String response = null;


  public static void main( String[] args )
  {
    /* Instantiate a new Client which will also receive events from
     * the server by implementing the ServerObserver interface */
    Client client = new Client();
    client.run();
  }



  @Override
  public void run()
  {
    /*
     * WARNING: this method executes IN THE CONTEXT OF THE MAIN THREAD
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

    /* Create the adapter that will allow communication with the server
     * in background, and start running its thread */
    ServerAdapter serverAdapter = new ServerAdapter(server);
    serverAdapter.addObserver(this);
    Thread serverAdapterThread = new Thread(serverAdapter);
    serverAdapterThread.start();

    System.out.println("\nWhat interface do you wanna use to play Santorini? Options are: cli");
    genUi(scanner.nextLine());
    String str = scanner.nextLine();
    while (!"".equals(str)) {

      synchronized (this) {
        /* reset the variable that contains the next string to be consumed
         * from the server */
        response = null;

        serverAdapter.requestConversion(str);

        /* While we wait for the server to respond, we can do whatever we want.
         * In this case we print a count-up of the number of seconds since we
         * requested the conversion to the server. */
        int seconds = 0;
        while (response == null) {
          System.out.println("been waiting for " + seconds + " seconds");
          try {
            wait(1000);
          } catch (InterruptedException e) { }
          seconds++;
        }

        /* we have the response, print it */
        System.out.println(response);
      }

      str = scanner.nextLine();
    }

    serverAdapter.stop();
  }


  @Override
  public synchronized void didReceiveConvertedString(String oldStr, String newStr)
  {
    /*
     * WARNING: this method executes IN THE CONTEXT OF `serverAdapterThread`
     * because it is called from inside the `run` method of ServerAdapter!
     */

    /* Save the string and notify the main thread */
    response = newStr;
    notifyAll();
  }

  private UI genUi(String ui)  {
    UI generatedUi;
    if ("cli".equals(ui)) {
      generatedUi = new CLI();
      System.out.println("your CLI has been generated!");
    }
    else {
      throw new RuntimeException("Wrong ui param. Valid are <cli>");
    }
    return generatedUi;
  }
}
