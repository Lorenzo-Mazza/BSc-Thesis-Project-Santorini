package it.polimi.ingsw.PSP50.View;

import it.polimi.ingsw.PSP50.Utils.ConsoleInput;
import it.polimi.ingsw.PSP50.Model.*;
import it.polimi.ingsw.PSP50.network.client.ClientSocket;
import it.polimi.ingsw.PSP50.network.messages.ToServer.*;
import it.polimi.ingsw.PSP50.network.messages.ToServerMessage;
import it.polimi.ingsw.PSP50.network.server.Server;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.Socket;
import java.util.*;
import java.util.concurrent.*;

public class CLI extends ClientView {

    private StringBuilder buffer = new StringBuilder();
    private BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));

    public CLI() {
        Socket server = connect();
        this.buffer.append("Insert Username");
        this.printBuffer();
        this.name = this.readLine();
        GameType gameType= chooseGame(new Scanner(System.in));
        /* Create the client socket that will allow communication with the server */
        ClientSocket socket = new ClientSocket(this, gameType, server);
        this.setSocket(socket);
        Thread socketThread = new Thread(socket);
        socketThread.start();
    }

    private Socket connect(){
        Socket server = null;
        try{
            System.out.println("IP address of server?");
            String ip = reader.readLine();

            /* open a connection to the server */
            server = new Socket(ip, Server.SOCKET_PORT);
        } catch (IOException e) {
            System.out.println("server unreachable");
        }
        System.out.println("Connected");
        return server;
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

    @Override
    public void startingGame() {
        drawSection("Game is starting! GET READY \uD83D\uDD25 \uD83D\uDD25 \uD83D\uDD25");
        notifySocket(new GameStarted(true,this.getPlayerId()));
    }

    @Override
    public void selectWorker(ArrayList<int[]> possibleChoices) {
        drawSection("Choose the worker to use for this turn");
        chooseSpace(possibleChoices,false);
    }

    @Override
    public void moveAction(ArrayList<int[]> possibleChoices, boolean optional) {
        drawSection("Choose where you want to move your worker!");
        chooseSpace(possibleChoices,optional);
    }

    @Override
    public void buildAction(ArrayList<int[]> possibleChoices, boolean optional) {
        drawSection("Choose where to build");
        chooseSpace(possibleChoices, optional);

    }

    private void writeLine(String line) {
        this.buffer.append(line);
    }

    private String readLine() {
        Scanner scanner = new Scanner(System.in);
        return (scanner.nextLine());
    }



    private int getChoiceWithTimeout(int range, int timeout, boolean optional) {
        //Callable<Integer> callable = () -> new Scanner(System.in).nextInt();
        ConsoleInput callable= new ConsoleInput(reader);
        long start = System.currentTimeMillis();
        int choice = 0;
        boolean valid=true;
        ExecutorService pool = Executors.newFixedThreadPool(1);
        Future<Integer> future;
        System.out.println("Enter your choice in " + timeout + " seconds :");
        future = pool.submit(callable);
        done: while (System.currentTimeMillis() - start < timeout * 1000) {
            do {
                valid = true;
                if (future.isDone()) {
                    try {
                        choice = future.get();
                        if ((choice >= 1 && choice <= range)||(choice >=0 && choice <= range && optional)) {
                            break done;
                        } else {
                            throw new IllegalArgumentException();
                        }
                    } catch (InterruptedException | ExecutionException | IllegalArgumentException e) {
                        System.out.println("Wrong choice, you have to pick an integer between 1 - " + range);
                        future = pool.submit(callable);
                        valid = false;
                    }
                }
            } while (!valid);
        }


        future.cancel(true);
        if ((choice < 0 || choice > range))
            choice=0;
        return choice;
    }

    @Override
    public void welcomeMessage(HashMap<String,Color> opponentsMap, Color playerColor) {
        //ArrayList<String> opponents = new ArrayList<>(opponentsMap.keySet());
        String colorEnd = "\u001b[0m";
        String message = " Welcome to SANTORINI, " + this.getName().toUpperCase() + "!!! " +
                "The color of your workers will be: " +
                playerColor.getSequence()+ playerColor.getName()+ colorEnd + ". ";
        String secondMessage=  "Your rivals are:  ";
        for (String opponent : opponentsMap.keySet())
            secondMessage+= "---> "+ opponent +" with the color "+
                    (opponentsMap.get(opponent).getSequence())+(opponentsMap.get(opponent).getName())+ colorEnd + ".  ";
        drawSection(message);
        drawSection(secondMessage);
    }

    @Override
    public void winAlert(String winnerName) {
        if (winnerName.equals(this.getName()))
            drawSection("CONGRATULATIONS "+ winnerName.toUpperCase()
                    + ", YOU'RE THE CHAMPION!!! \uD83C\uDFC6 \uD83C\uDFC6 \uD83C\uDFC6");
        else drawSection(winnerName+ " has won the game! Unfortunately You LOST. \uD83D\uDE2D \uD83D\uDE2D \uD83D\uDE2D"
                + "\nTry again next time!");
    }

    @Override
    public void loseAlert() {
        drawSection("You LOST! \uD83D\uDE2D \uD83D\uDE2D \uD83D\uDE2D");
        drawSection("Keep the connection active to see how the game plays out!");
    }

    @Override
    public void nameChanged() {
        drawSection("There is a player with your same name on the lobby. Your new name is " + this.getName());
    }

    @Override
    public void initializeWorkers(ArrayList<int[]> possibleChoices) {
        drawSection("Choose where to place the workers");
        ArrayList<int[]> answer = new ArrayList<>();
        int choice;
        printBoard();
        writeLine("\nChoose the space where to put your first worker.");
        printBuffer();
        printAvailableSpaces(possibleChoices);
        choice= getChoiceWithTimeout(possibleChoices.size(),45,false);
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
        choice= getChoiceWithTimeout(possibleChoices.size(),45,false);
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
        int choice = getChoiceWithTimeout(possibleChoices.size(),45,optional);
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

        int choice= getChoiceWithTimeout(possibleChoices.size(),30,false);
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

    @Override
    public void workerIsBlocked (int x, int y) {
        drawSection("The Worker you selected in position (" + x + ", " + y +
                ") is blocked;try to choose another worker or you will lose.");
    }



        @Override
    public void chooseBlock(Block possibleBlock) {
            drawSection("Choose which block you want to build");
            writeLine("\n --> Select 1 to choose: "+ possibleBlock.toString() );
        writeLine("\n --> Select 2 to choose: DOME");
        printBuffer();
        int choice= getChoiceWithTimeout(2,45, false);
        if (choice==2) {
            drawSection("You have selected: DOME");
            notifySocket(new BlockChoice(Block.DOME,this.getPlayerId()));
            return;
        }
        else if(choice==0){
            System.out.println("\nTimer is expired. The first option will be selected.");
        }
        drawSection("\nYou have selected: " + possibleBlock.toString() );
        notifySocket(new BlockChoice(possibleBlock,this.getPlayerId()));
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

    private void printBoard2(ArrayList<int[]> avaiableMove) {
        writeLine("\nThis is the board.\n Reminder: Space (0,0) is the first one on the left, " +
                "Space (4,4) is the last one on the right; Domes are marked with an 'X'.");
        printBuffer();

        int arrayIndex = 1;
        for (int row = 0; row < 10; row++) {
            writeLine("+-----+-----+-----+-----+-----+\n");
            String line = "| ";
            if((row % 2) == 0) {
                for (int colomn = 0; colomn < 5; colomn++)
                    line += printSpace(gameCopy.getBoard().getSpace(row, colomn)) + "  | ";
            }
            else {
                for (int colomn = 0; colomn < 5; colomn++) {
                    int[] prova = {row, colomn};
                    if(avaiableMove.contains(prova))
                        line += "("+ arrayIndex + ") | ";
                    else
                        line += "    | ";
                }
                writeLine(line);
                writeLine("\n+-----+-----+-----+-----+-----+");
            }
        }
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

    @Override
    public void disconnectCLI(String userDisconnect) {
        drawSection("Player ->"+ userDisconnect +"<- has been disconnect so the game ended.");
        printBuffer();
        Thread.yield();
        System.exit(0);
    }

}


