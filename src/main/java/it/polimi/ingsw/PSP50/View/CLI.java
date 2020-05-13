package it.polimi.ingsw.PSP50.View;

import it.polimi.ingsw.PSP50.Model.*;
import it.polimi.ingsw.PSP50.network.messages.Message;
import it.polimi.ingsw.PSP50.network.messages.ToClient.ModelMessage;
import it.polimi.ingsw.PSP50.network.messages.ToServer.BlockChoice;
import it.polimi.ingsw.PSP50.network.messages.ToServer.GodChoice;
import it.polimi.ingsw.PSP50.network.messages.ToServer.SpaceChoice;
import it.polimi.ingsw.PSP50.network.messages.ToServerMessage;

import java.util.ArrayList;
import java.util.Scanner;

public class CLI extends ClientView {

    private Game gameCopy;
    private StringBuilder buffer = new StringBuilder();

    public CLI() {
        this.buffer.append("Insert Username");
        this.printBuffer();
        this.name = this.readLine();
    }

    /**
     * Updates the CLI whenever the Model changes
     */
    @Override
    public void update(Object gameCopy){
        this.gameCopy = (Game) gameCopy;

        printBoard();
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
            choice--;
            if((choice < 0) || (choice > possibleChoices.size())) {
                writeLine("Wrong choice, you have to pick an integer between 1 - "+
                        possibleChoices.size() +"\n");
                printBuffer();
            }
        }while ((choice < 0) || (choice > possibleChoices.size()));

        SpaceChoice messageChoice = new SpaceChoice(possibleChoices.get(choice));
        notifySocket(messageChoice);
    }

    @Override
    public void chooseGod(ArrayList<String> possibleChoices) {
        drawSection("Choose the God you want to use (Write an integer between 1 - "+ (possibleChoices.size()));
        writeLine("You can choose the god from this list:\n ");
        for (int index = 0; index < possibleChoices.size(); index++) {
            writeLine(" --> Select "+ (index + 1) +" to choose: ");
            writeLine(possibleChoices.get(index) +"\n");
        }
        printBuffer();

        Scanner scanner = new Scanner(System.in);
        int choice;
        do{
            choice = scanner.nextInt();
            choice--;
            if (!possibleChoices.contains(choice)) {
                writeLine("Wrong choice, you have to pick an integer between 1 - "+
                        (possibleChoices.size()));;
                printBuffer();
            }
        }while ((choice < 0) || (choice > (possibleChoices.size() - 1)));

        GodChoice messageChoice = new GodChoice(choice);
        notifySocket(messageChoice);
    }

    @Override
    public void chooseBlock(Block possibleBlock) {
        printBoard();
        writeLine(" --> Select "+ possibleBlock.getValue() +" to choose: "+ possibleBlock.toString() +"\n");
        writeLine(" --> Select 4 to choose: DOME");
        printBuffer();
        Scanner scanner = new Scanner(System.in);
        int choice;
        do{
            choice = scanner.nextInt();
            if((choice != possibleBlock.getValue()) && (choice != 4))
                writeLine("Wrong choice, you have to pick "+ possibleBlock.getValue() +" or 4\n");
        }while((choice != possibleBlock.getValue()) && (choice != 4));

        if(choice == 4)
            choice = 10;

        BlockChoice messageChoice = new BlockChoice(choice);
        notifySocket(messageChoice);
    }

    private void notifySocket(ToServerMessage messageChoice) {
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
        for (int i = 0; i < 5; i++) {
            writeLine("+---+---+---+---+---+");
            String line = "| ";
            for (int j = 0; j < 5; j++) {
                line += printSpace(gameCopy.getBoard().getSpace(j, i)) + " | ";
            }
            writeLine(line);
        }
        writeLine("+---+---+---+---+---+");
        printBuffer();
    }

    private void printChoices(ArrayList<int[]> coordinates) {
        drawSection("This is the board:");
        printBoard();
        drawSection("Select one of this pairs");

        for(int index = 0; index < coordinates.size(); index++) {
            writeLine(" --> Select "+ (index + 1) +" to choose: ");
            writeLine("("+ coordinates.get(index)[0] +","+ coordinates.get(index)[1] +")\n");
        }

        printBuffer();
    }
}