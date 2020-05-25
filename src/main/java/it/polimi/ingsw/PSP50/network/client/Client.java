package it.polimi.ingsw.PSP50.network.client;

import it.polimi.ingsw.PSP50.Model.GameType;
import it.polimi.ingsw.PSP50.View.CLI;
import it.polimi.ingsw.PSP50.View.ClientView;
import it.polimi.ingsw.PSP50.View.GUI.GuiView;
import it.polimi.ingsw.PSP50.View.GUI.LoginController;
import it.polimi.ingsw.PSP50.View.GUI.LoginPage;
import it.polimi.ingsw.PSP50.network.server.Server;

import java.io.IOException;
import java.net.Socket;
import java.util.Scanner;


public class Client  {


    public static void main( String[] args ) {
        Scanner scanner = new Scanner(System.in);
        String ui;
        System.out.println("\nWhat interface do you wanna use to play Santorini? Options are: <cli>,<gui>");
        while(true) {
            ui=scanner.nextLine();
            if ("cli".equals(ui)) {
                new CLI();
                break;
            }
            else if ("gui".equals(ui)) {
                LoginPage.loginPage();
                break;
            }
            else {
                System.out.println("Wrong ui param. Valid are <cli>,<gui>");
            }
        }
    }



   /* private GameType chooseGame(Scanner scanner){
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
  }*/

}
