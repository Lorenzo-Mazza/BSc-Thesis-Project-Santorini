package it.polimi.ingsw.PSP50.view;

import it.polimi.ingsw.PSP50.Model.Board;
import it.polimi.ingsw.PSP50.Model.Space;
import it.polimi.ingsw.PSP50.Model.Worker;

import java.io.PrintStream;
import java.util.Scanner;

public class View implements Runnable {
    /**
     * @param space
     * @return space height highlighted the color of the worker occupying it
     */
    private static String printSpace(Space space) {
        int height = space.getHeight().getValue();
        String heightString;
        if (height == 10) {
            heightString = "X"; // dome
        } else {
            heightString = String.valueOf(height);
        }

        String colorStart = ""; // escape sequences for player workers color
        String colorEnd = "\u001b[0m"; // reset color of following characters
        Worker occupied = space.getWorker();
        if (occupied != null) {
            colorStart = occupied.getOwner().getColor().getSequence();
        }

        return colorStart + heightString + colorEnd;
    }

    /**
     * Prints the game board to terminal
     * @param board
     */
    private static void printBoard(Board board){
        for (int i = 0; i < 5; i++) {
            System.out.println("+---+---+---+---+---+");
            String line = "| ";
            for (int j = 0; j < 5; j++) {
                line += printSpace(board.getSpace(j, i)) + " | ";
            }
            System.out.println(line);
        }
        System.out.println("+---+---+---+---+---+");
    }

    @Override
    public void run() {
        Scanner scanner = new Scanner(System.in);
        PrintStream outputStream = new PrintStream(System.out);



    }
}
