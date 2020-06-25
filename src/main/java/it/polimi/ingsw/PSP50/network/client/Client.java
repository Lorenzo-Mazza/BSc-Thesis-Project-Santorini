package it.polimi.ingsw.PSP50.network.client;

import it.polimi.ingsw.PSP50.View.CLI;
import it.polimi.ingsw.PSP50.View.GUI.LoginPage;

import java.util.Scanner;


public class Client  {
    /**
     *Main method: launches the CLI or the GUI depending on the user choice
     * @param args main args
     */
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
}
