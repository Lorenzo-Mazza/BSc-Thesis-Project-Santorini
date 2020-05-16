package it.polimi.ingsw.PSP50.View;

import it.polimi.ingsw.PSP50.Model.*;
import it.polimi.ingsw.PSP50.network.messages.ToServer.*;
import it.polimi.ingsw.PSP50.network.messages.ToServerMessage;

import java.util.*;
import java.util.concurrent.*;

public class CLI extends ClientView {

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
        drawSection("The board has changed! Here it is an update of it : ");
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
        printBuffer();
    }

    private void writeLine(String line) {
        this.buffer.append(line);
    }

    private String readLine() {
        Scanner scanner = new Scanner(System.in);
        return (scanner.nextLine());
    }
    


    @Override
    public void initializeWorkers(ArrayList<int[]> possibleChoices) {
        ArrayList<int[]> answer = new ArrayList<>();
        int choice;
        printBoard();
        writeLine("\nChoose the space where to put your first worker.");
        printBuffer();
        printAvailableSpaces(possibleChoices);
        choice= getChoiceWithTimeout(possibleChoices.size(),30,false);
        /*
         **If a timer timeout happens, default option is the first choice
         */
        if(choice==0){
            System.out.println("\nTimer is expired. The first option will be selected.");
        } else {
            choice--;
        }
        answer.add(possibleChoices.get(choice));
        possibleChoices.remove(choice);
        writeLine("\nChoose the space where to put your second worker.");
        printBuffer();
        printAvailableSpaces(possibleChoices);
        choice= getChoiceWithTimeout(possibleChoices.size(),15,false);
        /*
         **If a timer timeout happens, default option is the first choice
         */
        if(choice==0){
            System.out.println("\nTimer is expired. The first option will be selected.");
        } else {
            choice--;
        }
        answer.add(possibleChoices.get(choice));
        drawSection("For your first Worker you selected the space " + Arrays.toString(answer.get(0)) + "." +
                "For your second Worker you selected the space " + Arrays.toString(answer.get(1)) );

        notifySocket(new WorkersInitialChoice(answer, this.getPlayerId()));
    }


    public void chooseSpace(ArrayList<int[]> possibleChoices, boolean optional) {
        printAvailableSpaces(possibleChoices);
        if (optional) {
            writeLine("\nThe action is optional. To exit write <0>.");
            printBuffer();
        }
        int choice = getChoiceWithTimeout(possibleChoices.size(),30,optional);
        if(choice==0){
            if (optional) {
                System.out.println("\n No action will be performed.");
                notifySocket(new NoAction(null,this.getPlayerId()));
                return;
            }
            else {
                System.out.println("\nTimer is expired. The first option will be selected.");
            }
        }
        else {
            choice--;
        }
        drawSection("You selected the space ("+
                possibleChoices.get(choice)[0] +","+ possibleChoices.get(choice)[1] +").");
        notifySocket(new SpaceChoice(possibleChoices.get(choice), this.getPlayerId()));
    }

    @Override
    public void chooseGod(ArrayList<String> possibleChoices) {
        drawSection("Choose the God you want to use (Write an integer between 1 - "+ (possibleChoices.size()));
        writeLine("\nYou can choose the god from this list:  ");
        for (int index = 0; index < possibleChoices.size(); index++) {
            writeLine("\n--> Select "+ (index + 1) +" to choose: ");
            writeLine(possibleChoices.get(index) +".");
        }
        printBuffer();

        int choice= getChoiceWithTimeout(possibleChoices.size(),15,false);
        /*
         **If a timer timeout happens, default option is the first choice
         */
        if(choice==0){
            System.out.println("\nTimer is expired. The first option will be selected.");
        } else {
            choice--;
        }
        drawSection("\nYou have selected: "+ possibleChoices.get(choice));
        GodChoice messageChoice = new GodChoice(choice,this.getPlayerId());
        notifySocket(messageChoice);
    }

    private int getChoiceWithTimeout(int choiceSize, int timeout, boolean optional){
        Callable<Integer> k = () -> new Scanner(System.in).nextInt();
        Long start= System.currentTimeMillis();
        int choice=0;
        ExecutorService l = Executors.newFixedThreadPool(1);  ;
        Future<Integer> g;
        System.out.println("\nEnter your choice in "+ timeout + " seconds. " +
                "If you insert a wrong input, the first option will be selected.");
        g= l.submit(k);
        while(System.currentTimeMillis()-start<timeout*1000 && !g.isDone()){
            // Wait for future
        }
        if(g.isDone()){
            try {
                choice=g.get();
                if ((choice<0 || choice>choiceSize) || (!optional && choice==0))
                {
                    //System.out.println("\nWrong input inserted; the first option will be selected ");
                    choice=1;
                }

            } catch (InterruptedException | ExecutionException e) {
                e.printStackTrace();
            }
        }
        g.cancel(true);
        /*
         **WARNING: In the helper method context, value 0 represents a timer timeout
         */
        return choice;
    }

    @Override
    public void chooseBlock(Block possibleBlock) {
        writeLine("\n --> Select "+ possibleBlock.getValue() +" to choose: "+ possibleBlock.toString() );
        writeLine("\n --> Select 4 to choose: DOME");
        printBuffer();
        Scanner scanner = new Scanner(System.in);
        int choice;
        do{
            choice = scanner.nextInt();
            if((choice != possibleBlock.getValue()) && (choice != 4)) {
                writeLine("\nWrong choice, you have to pick "+ possibleBlock.getValue() +" or 4");
                printBuffer();
            }

        }while((choice != possibleBlock.getValue()) && (choice != 4));

        if(choice == 4)
            choice = 10;

        BlockChoice messageChoice = new BlockChoice(choice,this.getPlayerId());
        notifySocket(messageChoice);
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
        writeLine("\nThis is the board.\n Reminder: Space (0,0) is the first one on the left, " +
                "Space (4,4) is the last one on the right; Domes are marked with an 'X'.");
        printBuffer();
        for (int i = 0; i < 5; i++) {
            writeLine("\n+-----+-----+-----+-----+-----+ \n");
            String line = "| ";
            for (int j = 0; j < 5; j++) {
                line += printSpace(gameCopy.getBoard().getSpace(i, j)) + "   | ";
            }
            writeLine(line);
            writeLine("\n+-----+-----+-----+-----+-----+");
        }
        printBuffer();
    }

    private void printAvailableSpaces(ArrayList<int[]> coordinates) {
        writeLine("\nSelect one of these pairs:");
        printBuffer();

        for(int index = 0; index < coordinates.size(); index++) {
            writeLine("\n --> Select "+ (index + 1) +" to choose the space: ");
            writeLine("("+ coordinates.get(index)[0] +","+ coordinates.get(index)[1] +").");
        }

        printBuffer();
    }

    /* To change
    private int spaceChoice(ArrayList<int[]> possibleChoices, boolean optional) {
        Scanner scanner = new Scanner(System.in);
        int choice;
        printAvailableSpaces(possibleChoices);
        do{
            choice = scanner.nextInt();
            choice--;
            if ((choice < 0) || (choice > (possibleChoices.size() - 1))) {
                if(optional && (choice != -1))
                    writeLine("Wrong choice, you have to pick an integer between 1 - "+
                            possibleChoices.size() +" or <0> to exit");
                else
                    writeLine("Wrong choice, you have to pick an integer between 1 - "+
                        (possibleChoices.size()));
                printBuffer();
            }
        }while(((choice < 0) || (choice > possibleChoices.size())) && (!optional || (choice != -1)));

        return choice;
    } */



    private void notifySocket(ToServerMessage messageChoice) {
        this.getSocket().update(messageChoice);
    }
}


