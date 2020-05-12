package it.polimi.ingsw.PSP50.View;

import it.polimi.ingsw.PSP50.Model.*;
import it.polimi.ingsw.PSP50.network.messages.Message;
import it.polimi.ingsw.PSP50.network.messages.ToClient.ModelMessage;
import it.polimi.ingsw.PSP50.network.messages.ToServer.SpaceChoice;

import java.util.ArrayList;
import java.util.Scanner;

public class CLI extends ClientView {

    private String name;
    private Game gameCopy;
    private StringBuilder buffer = new StringBuilder();

    public CLI() {
        this.writeLine("Insert Username");
        this.printBuffer();
        this.name = this.readLine();
    }

    /**
     * Updates the CLI whenever the Model changes
     */
    @Override
    public void update(Object gameCopy){
        this.gameCopy = (Game) gameCopy;
    }



    private void printBuffer() {
        System.out.print(this.buffer.toString());
        System.out.print("\n");

        this.buffer.delete(0, this.buffer.length());
        System.out.flush();
    }

    @Override
    public void drawSection(String line) {
        for (int i = 0; i < (line.length()); i++) {
            this.buffer.append("_");
        }
        this.buffer.append("\n");

        this.buffer.append("|");
        this.buffer.append(line);
        this.buffer.append("|");

        this.buffer.append("\n");

        for (int i = 0; i < (line.length()); i++) {
            this.buffer.append("_");
        }
        this.buffer.append("\n");
    }

    private void writeLine(String line) {
        this.buffer.append(line);
    }

    private String readLine() {
        Scanner scanner = new Scanner(System.in);
        return (scanner.nextLine());
    }
    
    public void displayEmptyMap() {
        StringBuilder stringBuilder = new StringBuilder();
        for (int i = 0; i < 5; i++) {
            stringBuilder.append(" __ __ __ __ __");
            stringBuilder.append("\n");
            stringBuilder.append("|  |  |  |  |  |");
            stringBuilder.append("\n");
            stringBuilder.append(" __ __ __ __ __");
        }
    }

    @Override
    public void chooseSpace(ArrayList<int[]> possibleChoices) {
        Scanner scanner = new Scanner(System.in);
        int choice;

        do{
            printChoices(possibleChoices);
            choice = scanner.nextInt();
            if((choice < 0) || (choice > possibleChoices.size())) {
                this.buffer.append("Wrong choice, you have to pick an integer between 0 - "+
                        (possibleChoices.size() - 1));
                printBuffer();
            }
        }while ((choice < 0) || (choice > possibleChoices.size()));

        SpaceChoice messageChoice = new SpaceChoice(possibleChoices.get(choice));
        notifySocket(messageChoice);
    }

    private void notifySocket(SpaceChoice messageChoice) {
        this.getSocket().update(messageChoice);
    }

    /**
     * @param space
     * @return space height highlighted the color of the worker occupying it
     */
    private String printSpace(Space space) {
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
     */
    private void printBoard() {
        this.buffer.delete(0, this.buffer.length());
        for (int i = 0; i < 5; i++) {
            buffer.append("+---+---+---+---+---+");
            String line = "| ";
            for (int j = 0; j < 5; j++) {
                line += printSpace(gameCopy.getBoard().getSpace(j, i)) + " | ";
            }
            buffer.append(line);
        }
        buffer.append("+---+---+---+---+---+");
        printBuffer();
    }

    private void printChoices(ArrayList<int[]> coordinates) {
        drawSection("Select one of this pairs");

        for(int index = 0; index < coordinates.size(); index++) {
            this.buffer.append(" --> Select "+ index +" to choose: ");
            this.buffer.append("("+ coordinates.get(index)[0] +","+ coordinates.get(index)[1]);
        }

        printBuffer();
    }
}