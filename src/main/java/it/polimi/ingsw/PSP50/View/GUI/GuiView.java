package it.polimi.ingsw.PSP50.View.GUI;

import it.polimi.ingsw.PSP50.Model.*;
import it.polimi.ingsw.PSP50.View.ClientView;
import it.polimi.ingsw.PSP50.network.client.ClientSocket;
import it.polimi.ingsw.PSP50.network.messages.ToServer.GameStarted;
import it.polimi.ingsw.PSP50.network.messages.ToServer.GodChoice;
import it.polimi.ingsw.PSP50.network.messages.ToServer.WorkersInitialChoice;
import javafx.animation.ParallelTransition;
import javafx.animation.RotateTransition;
import javafx.animation.SequentialTransition;
import javafx.animation.TranslateTransition;
import javafx.application.Platform;
import javafx.event.EventHandler;
import javafx.fxml.FXMLLoader;
import javafx.scene.*;
import javafx.scene.control.Alert;
import javafx.scene.control.RadioButton;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.scene.transform.Rotate;
import javafx.scene.transform.Translate;
import javafx.stage.Stage;
import javafx.stage.WindowEvent;
import javafx.util.Duration;
import org.fxyz3d.importers.Importer3D;
import org.fxyz3d.importers.Model3D;

import java.io.IOException;
import java.net.Socket;
import java.util.ArrayList;
import java.util.HashMap;

public class GuiView extends ClientView {

    private Stage primaryStage;

    private Scene welcome;
    private Scene chooseGod;
    private Scene gameBoard;
    private Scene endGame;
    private SubScene subScene3D;

    private WelcomeController welcomeController;
    private ChooseGodController chooseGodController;
    private BoardController boardController;

    private ArrayList<String> opponents = new ArrayList<>();
    private HashMap<String, Color> opponentsColor;
    private Color playerColor;
    private String god;
    private HashMap<String, String> opponentsGods;
    private HashMap<String, Group[]> opponentsWorkers = new HashMap<>();
    private ArrayList<Group>myWorkers = new ArrayList<>();
    private ArrayList<int[]> myWorkersPosition = new ArrayList<>();
    private Group board = new Group();
    private ArrayList<int[]> possibleChoices = new ArrayList<>();
    private static final double EMPTY = 0;
    private static final double LEVEL_1 = 1.4;
    private static final double LEVEL_2 = 2.4;
    private static final double LEVEL_3 = 3;
    private final double POSITIVE_Z_TRANSLATION= 2.3;
    private final double NEGATIVE_Z_TRANSLATION=2.5;
    private final double X_TRANSLATION=2.3;





    GuiView(GameType gameType, Socket server, String name, Stage primaryStage){
        this.setName(name);
        ClientSocket socket = new ClientSocket(this, gameType, server);
        this.setSocket(socket);
        this.primaryStage = primaryStage;
        Thread socketThread = new Thread(socket);
        socketThread.start();
    }

    void startGame(){
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/fxml/Lobby.fxml"));
        try {
            Parent root = loader.load();
            Scene lobby = new Scene(root);
            primaryStage.setScene(lobby);
            primaryStage.setTitle("Lobby");
            primaryStage.setResizable(false);
            primaryStage.show();
        } catch (IOException e) {
            System.out.println("Error creating the lobby");
        }
    }

    void loadingGameBoard(){
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/fxml/LoadingBoard.fxml"));
        try {
            Parent root = loader.load();
            primaryStage.setTitle("Loading Board");
            Scene loading = new Scene(root);
            primaryStage.setScene(loading);
            primaryStage.setResizable(false);
            primaryStage.show();
        } catch (IOException e) {
            System.out.println("Error loading the board");
        }
    }

    @Override
    public void update(Object modelCopy) {
        Platform.runLater(
                () -> {
        Board newCopy = ((Game) modelCopy).getBoard();
        updateOpponents((Game) modelCopy);
        updateBoard(newCopy);
        this.setGameCopy( (Game) modelCopy);
                });
    }

    @Override
    public void startingGame() {
        Platform.runLater(
                () -> {
                    setOpponentsGods();
                    PerspectiveCamera camera = new PerspectiveCamera(true);
                    camera.setTranslateZ(-11);
                    camera.setTranslateY(-31);
                    camera.getTransforms().add(new Rotate(-70,Rotate.X_AXIS));
                    try {
                        Model3D boardModel = Importer3D.loadAsPoly(getClass().getResource("/boardcliff2.obj"));
                        Model3D seaModel = Importer3D.loadAsPoly(getClass().getResource("/sea.obj"));
                        board = boardModel.getRoot();
                        Group sea = seaModel.getRoot();

                        board.getTransforms().add(new Rotate(-180, Rotate.X_AXIS));
                        board.getTransforms().add(new Translate(0.9, 0));
                        sea.getTransforms().add(new Rotate(-180, Rotate.X_AXIS));
                        sea.getTransforms().add(new Translate(0,-6,-2.5));

                        Group root3D = new Group(board,sea);
                        this.subScene3D = new SubScene(root3D, 1200, 700, true,SceneAntialiasing.BALANCED);
                        this.subScene3D.setCamera(camera);
                        FXMLLoader loader = new FXMLLoader(getClass().getResource("/fxml/GameBoard.fxml"));
                        AnchorPane testing = loader.load();
                        boardController = loader.getController();
                        boardController.setGui(this);
                        boardController.setMyPlayer(findGodImage(this.god));
                        String firstOpponent = opponents.get(0);
                        boardController.setOpponent1(firstOpponent,findGodImage(opponentsGods.get(firstOpponent)));
                        if (opponents.size()>1){
                            String secondOpponent = opponents.get(1);
                            boardController.setOpponent2(secondOpponent, findGodImage(opponentsGods.get(secondOpponent)));
                        }

                        testing.getChildren().add(this.subScene3D);
                        //testing.toFront();
                        this.subScene3D.toBack();
                        Scene gameBoard = new Scene(testing);
                        this.gameBoard = gameBoard;
                        primaryStage.setScene(gameBoard);
                        primaryStage.setResizable(false);
                        primaryStage.show();
                        notifySocket(new GameStarted(true,this.getPlayerId()));
                    } catch (IOException e){
                        e.printStackTrace();
                    }
                });
    }

    @Override
    public void selectWorker(ArrayList<int[]> possibleChoices) {
        Platform.runLater(
                () -> {
                    this.possibleChoices = possibleChoices;
                    boardController.setSpaceChoice(possibleChoices);
                    boardController.enableActionButton();
                    boardController.setCommandText("Choose which worker you wanna use in this turn. " +
                            " Reminder: Space (0,0) is the top one on the left, " +
                            "Space (4,4) is the bottom one on the right");
                });
    }


    @Override
    public void chooseSpace(ArrayList<int[]> possibleChoices, boolean optional) {

    }

    @Override
    public void initializeWorkers(ArrayList<int[]> possibleChoices) {
        Platform.runLater(
                () -> {
        this.possibleChoices = possibleChoices;
        boardController.setSpaceChoice(possibleChoices);
        boardController.enableFirstButton();
        boardController.setCommandText("Choose where to place your first worker." +
                " Reminder: Space (0,0) is the top one on the left, " +
                "Space (4,4) is the bottom one on the right");
                });
    }

    @Override
    public void moveAction(ArrayList<int[]> possibleChoices, boolean optional) {
        Platform.runLater(
                () -> {
                    boardController.setSpaceChoice(possibleChoices);
                    boardController.enableActionButton();
                    String text = "Choose where to MOVE your selected worker."+
                            " Reminder: Space (0,0) is the top one on the left, " +
                            "Space (4,4) is the bottom one on the right. ";
                    if (optional)
                    {
                        text += "The MOVE IS OPTIONAL, CLICK SKIP IF YOU DON'T WANNA MOVE.";
                        boardController.enableSkipButton();
                    }
                            boardController.setCommandText(text);
                });
    }

    @Override
    public void buildAction(ArrayList<int[]> possibleChoices, boolean optional) {
        Platform.runLater(
                () -> {
                    boardController.setSpaceChoice(possibleChoices);
                    boardController.enableActionButton();
                    String text = "Choose where to BUILD with your selected worker."+
                            " Reminder: Space (0,0) is the top one on the left, " +
                            "Space (4,4) is the bottom one on the right. ";
                    if (optional)
                    {
                        text += "The MOVE IS OPTIONAL, CLICK SKIP IF YOU DON'T WANNA MOVE.";
                        boardController.enableSkipButton();
                    }
                    boardController.setCommandText(text);
                });
    }



    void placeWorker(int[] coordinates){
        Platform.runLater(
                () -> {
                    try {
                        Model3D builder;
                        boolean male = checkIfMaleWorker(this.getGod());
                        String color = this.getPlayerColor().getName();
                       builder = loadCorrectWorker(male,color);
            Group workerObject = builder.getRoot();
            myWorkers.add(workerObject);
            myWorkersPosition.add(coordinates);
            setInitialWorker(workerObject,coordinates[0],coordinates[1]);
            this.possibleChoices.remove(coordinates);
            if (myWorkers.size()==1)
                initializeSecondWorker();
            else {
                // Update Model Copy to prevent a double animation
                ArrayList<Player> playersCopy = this.getGameCopy().getAllPlayers();
                Board boardCopy = this.getGameCopy().getBoard();
                for (Player pl : playersCopy)
                {
                    if (pl.getName().equals(this.getName())) {
                        pl.getWorkers()[0].setPosition(boardCopy.getSpace
                                (myWorkersPosition.get(0)[0],myWorkersPosition.get(0)[1]));
                        pl.getWorkers()[1].setPosition(boardCopy.getSpace
                                (myWorkersPosition.get(1)[0],myWorkersPosition.get(1)[1]));
                    }
                }
                notifySocket(new WorkersInitialChoice(myWorkersPosition, this.getPlayerId()));
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
                });
    }

    private void initializeSecondWorker(){
        Platform.runLater(
                () -> {
        boardController.setSpaceChoice(possibleChoices);
        boardController.enableFirstButton();
        boardController.setCommandText("Choose where to place your second worker." +
                " Reminder: Space (0,0) is the first one on the left, " +
                "Space (4,4) is the last one on the right");
                });
    }


    @Override
    public void chooseGod(ArrayList<String> possibleChoices) {
        Platform.runLater(
                () -> {
                    FXMLLoader loader = new FXMLLoader(getClass().getResource("/fxml/ChooseGod.fxml"));
                    try {
                        Parent root = loader.load();
                        primaryStage.setTitle("Choose God");
                        chooseGod = new Scene(root);
                        chooseGodController = loader.getController();
                        chooseGodController.setGui(this);
                        primaryStage.setScene(chooseGod);
                        primaryStage.setResizable(false);
                        ArrayList<ImageView> gods = chooseGodController.getGodImages();
                        ArrayList<ImageView> powers = chooseGodController.getGodPowers();
                        ArrayList<RadioButton> buttons = chooseGodController.getButtons();
                        for (int index=0;index<possibleChoices.size(); index++){

                            if (possibleChoices.get(index).equals("APOLLO")) {
                                gods.get(index).setImage(new Image("Sprite/Cards/Full/full_0000s_0043_god_and_hero_cards_0013_apollo.png"));
                                powers.get(index).setImage(new Image("Sprite/Cards/Powers/_0000s_0044_god_and_hero_powers0014.png"));
                                buttons.get(index).setText(possibleChoices.get(index));
                            }
                            if (possibleChoices.get(index).equals("ARTEMIS")){
                                gods.get(index).setImage(new Image("Sprite/Cards/Full/full_0000s_0054_god_and_hero_cards_0002_Artemis.png"));
                                powers.get(index).setImage(new Image("Sprite/Cards/Powers/_0000s_0048_god_and_hero_powers0010.png"));
                                buttons.get(index).setText(possibleChoices.get(index));
                            }
                            if (possibleChoices.get(index).equals("ATHENA")){
                                gods.get(index).setImage(new Image("Sprite/Cards/Full/full_0000s_0052_god_and_hero_cards_0004_Athena.png"));
                                powers.get(index).setImage(new Image("Sprite/Cards/Powers/_0000s_0050_god_and_hero_powers0008.png"));
                                buttons.get(index).setText(possibleChoices.get(index));
                            }
                            if (possibleChoices.get(index).equals("ATLAS")){
                                gods.get(index).setImage(new Image("Sprite/Cards/Full/full_0000s_0053_god_and_hero_cards_0003_Atlas.png"));
                                powers.get(index).setImage(new Image("Sprite/Cards/Powers/_0000s_0049_god_and_hero_powers0009.png"));
                                buttons.get(index).setText(possibleChoices.get(index));
                            }
                            if (possibleChoices.get(index).equals("DEMETER")) {
                                gods.get(index).setImage(new Image("Sprite/Cards/Full/full_0000s_0050_god_and_hero_cards_0006_Demeter.png"));
                                powers.get(index).setImage(new Image("Sprite/Cards/Powers/_0000s_0052_god_and_hero_powers0006.png"));
                                buttons.get(index).setText(possibleChoices.get(index));
                            }
                            if (possibleChoices.get(index).equals("HEPHAESTUS")) {
                                gods.get(index).setImage(new Image("Sprite/Cards/Full/full_0000s_0009_god_and_hero_cards_0047_Hephaestus.png"));
                                powers.get(index).setImage(new Image("Sprite/Cards/Powers/_0000s_0010_god_and_hero_powers0048.png"));
                                buttons.get(index).setText(possibleChoices.get(index));
                            }
                            if (possibleChoices.get(index).equals("MINOTAUR")) {
                                gods.get(index).setImage(new Image("Sprite/Cards/Full/full_0000s_0008_god_and_hero_cards_0048_Minotaur.png"));
                                powers.get(index).setImage(new Image("Sprite/Cards/Powers/_0000s_0009_god_and_hero_powers0049.png"));
                                buttons.get(index).setText(possibleChoices.get(index));
                            }
                            if (possibleChoices.get(index).equals("PAN")) {
                                gods.get(index).setImage(new Image("Sprite/Cards/Full/full_0000s_0046_god_and_hero_cards_0010_Pan.png"));
                                powers.get(index).setImage(new Image("Sprite/Cards/Powers/_0000s_0056_god_and_hero_powers0002.png"));
                                buttons.get(index).setText(possibleChoices.get(index));
                            }
                            if (possibleChoices.get(index).equals("PROMETHEUS")) {
                                gods.get(index).setImage(new Image("Sprite/Cards/Full/full_0000s_0004_god_and_hero_cards_0052_Prometheus.png"));
                                powers.get(index).setImage(new Image("Sprite/Cards/Powers/_0000s_0005_god_and_hero_powers0053.png"));
                                buttons.get(index).setText(possibleChoices.get(index));
                            }
                            buttons.get(index).setDisable(false);
                        }

                        primaryStage.show();
                    } catch (IOException e) {
                        System.out.println("Error choosing the god");
                    }
                });

    }

    void sendGodChoice(int choice){
        this.notifySocket(new GodChoice(choice,this.getPlayerId()));
    }

    @Override
    public void chooseBlock(Block possibleBlock) {
        Platform.runLater(
                () -> {
                    ArrayList<String> blocks = new ArrayList<>();
                    blocks.add(possibleBlock.name());
                    blocks.add("DOME");
                    boardController.setBlockChoice(blocks);
                    boardController.enableBlockButton();
                    boardController.setCommandText("Choose which BLOCK you want to build (ATLAS POWER)");
                });
    }


    @Override
    public void workerIsBlocked (int x, int y) {
        Platform.runLater(
                () -> {
                    Alert alert = new Alert(Alert.AlertType.WARNING);
                    alert.setTitle("Error");
                    alert.setHeaderText("The Worker you selected in position (" + x + ", " + y +
                                            ") is blocked;try to choose another worker or you will lose." );
                    alert.showAndWait();
                });
                }

    @Override
    public void welcomeMessage(HashMap<String, Color> opponentsColor, Color playerColor) {
        Platform.runLater(
                () -> {
        this.opponentsColor = opponentsColor;
        this.playerColor = playerColor;
        this.opponents.addAll(opponentsColor.keySet());
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/fxml/Welcome.fxml"));
        try {
            Parent root = loader.load();
            primaryStage.setTitle("Welcome");
            welcome = new Scene(root);
            welcomeController = loader.getController();
            primaryStage.setScene(welcome);
            primaryStage.setResizable(false);
            welcomeController.setTextLabel(opponentsColor, this.name, playerColor);
            primaryStage.show();
        } catch (IOException  e) {
            System.out.println("Error starting the game");
        }
                });
    }

    @Override
    public void winAlert(String winner) {
        Platform.runLater(
                () -> {
                    FXMLLoader loader;
                    if (winner.equals(this.getName()))
                        loader = new FXMLLoader(getClass().getResource("/fxml/YouWin.fxml"));
                    else
                        loader = new FXMLLoader(getClass().getResource("/fxml/YouLost.fxml"));
                    try {
                        Parent root = loader.load();
                        primaryStage.setTitle("EndGame");
                        endGame = new Scene(root);
                        primaryStage.setScene(endGame);
                        primaryStage.setResizable(false);
                        primaryStage.show();
                        primaryStage.setOnCloseRequest(windowEvent -> System.exit(0));
                    } catch (IOException  e) {
                            System.out.println("Error ending the game");
                        }
                });
    }

    @Override
    public void loseAlert() {
        Platform.runLater(
                () -> {
                    Alert alert = new Alert(Alert.AlertType.ERROR);
                    alert.setTitle("You Lost");
                    alert.setHeaderText("You LOST, we're sorry. Keep your connection active to see how the game plays out!");
                    alert.showAndWait();
                });
    }

    @Override
    public void nameChanged() {
        Platform.runLater(
                () -> {
                    Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
                    alert.setTitle("Name Changed");
                    alert.setHeaderText("There is a player with your same name on the lobby. Your new name is " + this.getName());
                    alert.showAndWait();
                });
    }

    @Override
    public void disconnectUI(String userDisconnect) {
        Platform.runLater(
                () -> {
                    Alert alert = new Alert(Alert.AlertType.ERROR);
                    alert.setTitle("Disconnection");
                    alert.setHeaderText("Player ->"+ userDisconnect +"<- is been disconnected so the game ended.");
                    alert.showAndWait();
                    System.exit(0);
                });
    }

    public void setGod(String god) {
        this.god = god;
    }

    public String getGod() {
        return god;
    }

    private void setOpponentsGods() {
        HashMap<String,String> opponentsGods = new HashMap<>();
        for (Player player : this.getGameCopy().getAllPlayers()){
            if (!player.getName().equals(this.getName()))
            {
                opponentsGods.put(player.getName(),player.getGod().getName().name());
            }
        }
        this.opponentsGods = opponentsGods;
    }

    HashMap<String, String> getOpponentsGods() {
        return opponentsGods;
    }

    HashMap<String, Color> getOpponentsColor() {
        return opponentsColor;
    }

    Color getPlayerColor() {
        return playerColor;
    }

    private Model3D loadCorrectWorker (boolean male, String color) throws IOException {
        Model3D builder;
        if (male)
        {
            if (color.equals("Orange"))
                builder = Importer3D.loadAsPoly(getClass().getResource("/orangemalebuilder.obj"));
            else if (color.equals("Blue"))
                builder = Importer3D.loadAsPoly(getClass().getResource("/maleblueworker.obj"));
            else
                builder = Importer3D.loadAsPoly(getClass().getResource("/maleworkerpink.obj"));
        }
        else {
            if (color.equals("Orange"))
                builder = Importer3D.loadAsPoly(getClass().getResource("/femaleorange.obj"));
            else if (color.equals("Blue"))
                builder = Importer3D.loadAsPoly(getClass().getResource("/femaleblue.obj"));
            else
                builder = Importer3D.loadAsPoly(getClass().getResource("/femalepink.obj"));
        }
        return builder;
    }

    private String findGodImage(String god){
        if (god.equals("APOLLO"))
            return("Sprite/Cards/Small/podium-characters-Apolo.png");
        if (god.equals("ARTEMIS"))
            return("Sprite/Cards/Small/podium-characters-Artemis.png");
        if (god.equals("ATHENA"))
            return("Sprite/Cards/Small/podium-characters-Athena.png");
        if (god.equals("ATLAS"))
            return("Sprite/Cards/Small/podium-characters-Atlas.png");
        if (god.equals("DEMETER"))
            return("Sprite/Cards/Small/podium-characters-Demeter.png");
        if (god.equals("HEPHAESTUS"))
            return("Sprite/Cards/Small/podium-characters-Hephaestus.png");
        if (god.equals("MINOTAUR"))
            return("Sprite/Cards/Small/podium-characters-Minotaur.png");
        if (god.equals("PAN"))
            return("Sprite/Cards/Small/podium-characters-Pan.png");
        if (god.equals("PROMETHEUS"))
            return("Sprite/Cards/Small/podium-characters-Prometheus.png");
        return "";
    }

    /**
     * Update the board through animations every time the Server validates a new Move/Build Action.
     * @param newCopy the new version of the board
     */
    private void updateBoard(Board newCopy){
        Board oldCopy = this.getGameCopy().getBoard();
        for (int row = 0; row<5; row++) {
            for (int column = 0; column < 5; column++) {
                Space newSpace = newCopy.getSpace(row, column);
                Space oldSpace = oldCopy.getSpace(row, column);
                // first check the workers positions
                if (newSpace.getWorker() != null) {
                    Worker worker = newCopy.getSpace(row, column).getWorker();
                    // if the worker doesn't have a Last Position, it might need to be initialized.
                    if (worker.getLastPosition()==null)
                        newWorkerAnimation(worker);
                    else{
                        // if the worker moved, make a move animation.
                        if (oldSpace.getWorker()==null || (
                                oldSpace.getWorker().getWorkerId()!=newSpace.getWorker().getWorkerId() ||
                                        !oldSpace.getWorker().getOwner().getName().equals(newSpace.getWorker().getOwner().getName())))
                            moveAnimation(worker);
                        // else the worker has not moved.
                    }
                }
                //  check the blocks positions
                if (newSpace.getHeight() != oldCopy.getSpace(row, column).getHeight()) {
                    buildAnimation(oldCopy.getSpace(row, column).getHeight(),newSpace);
                }
            }
        }
    }

    private void newWorkerAnimation(Worker worker){
        // check if worker is already in the gui. If not proceed with the animation
        String opponentName = worker.getOwner().getName();
        if ( opponents.contains(opponentName)  && (opponentsWorkers.size()==0 ||
                !opponentsWorkers.containsKey(opponentName)
                || opponentsWorkers.get(opponentName)[worker.getWorkerId()] ==null )){
            try {
                //animation
                Model3D builder;
                boolean male = checkIfMaleWorker(worker.getOwner().getGod().getName().name());
                String color = worker.getOwner().getColor().getName();
                builder = loadCorrectWorker(male,color);
                Group workerObject = builder.getRoot();
                setInitialWorker(workerObject,worker.getPosition().getXPosition(),
                        worker.getPosition().getYPosition());
                if (opponentsWorkers.containsKey(opponentName))
                    opponentsWorkers.get(opponentName)[worker.getWorkerId()]= workerObject;
                else {
                    Group[] workers = new Group[2];
                    workers[worker.getWorkerId()] = workerObject;
                    opponentsWorkers.put(opponentName, workers);
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    private boolean checkIfMaleWorker(String God) {
        return God.equals("APOLLO") || God.equals("ATLAS") || God.equals("HEPHAESTUS") ||
                God.equals("MINOTAUR") || God.equals("PAN") || God.equals("PROMETHEUS");
    }


    private void setInitialWorker(Group worker, int x, int z){
        double[] translation = getTranslationNeeded(x,z);
        worker.getTransforms().add(new Translate(-0.8,1,0));
        TranslateTransition preventCollisions = new TranslateTransition(Duration.seconds(0.2),worker);
        preventCollisions.setToY(5);
        TranslateTransition newTransition = new TranslateTransition(Duration.seconds(2),worker);
        newTransition.setToX(translation[1]);
        newTransition.setToY(0);
        newTransition.setToZ(translation[0]);
        SequentialTransition sequentialTransition = new SequentialTransition();
        sequentialTransition.getChildren().addAll(preventCollisions,newTransition);
        sequentialTransition.play();
        board.getChildren().add(worker);
        primaryStage.show();
    }


    private void moveAnimation(Worker worker){
        int x = worker.getPosition().getXPosition();
        int y = worker.getPosition().getYPosition();
        double[] translation = getTranslationNeeded(x,y);
        int height = worker.getPosition().getHeight().getValue();
        double z = 0;
        switch (height) {
            case 3:
                z = LEVEL_3;
                break;
            case 2:
                z = LEVEL_2;
                break;
            case 1:
                z = LEVEL_1;
                break;
            case 0:
                z = EMPTY;
                break;
        }
        // check which worker you have to move
        Group workerObject;
        if (worker.getOwner().getName().equals(this.getName()))
            workerObject = this.myWorkers.get(worker.getWorkerId());
        else
            workerObject = this.opponentsWorkers.get(worker.getOwner().getName())
                    [worker.getWorkerId()];
        animateMove(workerObject,translation[1],z,translation[0]);
    }

    private void animateMove(Group workerObject, double x, double y, double z) {
        TranslateTransition preventCollisions = new TranslateTransition(Duration.seconds(0.2),workerObject);
        preventCollisions.setToY(6);
        RotateTransition rotateTransition = new RotateTransition(Duration.seconds(2.5),workerObject);
        rotateTransition.setByAngle(360);
        rotateTransition.setCycleCount(1);
        rotateTransition.setAxis(Rotate.Z_AXIS);
        TranslateTransition newTransition = new TranslateTransition(Duration.seconds(2.5),workerObject);
        newTransition.setToX(x);
        newTransition.setToY(y);
        newTransition.setToZ(z);
        ParallelTransition parallel = new ParallelTransition();
        parallel.getChildren().addAll(rotateTransition,newTransition);
        SequentialTransition sequentialTransition = new SequentialTransition();
        sequentialTransition.getChildren().addAll(preventCollisions,parallel);
        sequentialTransition.play();
    }



    private void buildAnimation(Block oldHeight, Space space){
        try {
            int x = space.getXPosition();
            int y = space.getYPosition();
            double[] translation = getTranslationNeeded(x,y);
            double height = 0;
            Model3D model3D;

            switch (space.getHeight().getValue()){
                case 1:
                    model3D = Importer3D.loadAsPoly(getClass().getResource("/firstblock2.obj"));
                    break;
                case 2:
                    model3D = Importer3D.loadAsPoly(getClass().getResource("/secondblock2.obj"));
                    break;
                case 3:
                    model3D = Importer3D.loadAsPoly(getClass().getResource("/thirdblock.obj"));
                    break;
                default:
                    switch (oldHeight.getValue()){
                        case 0:
                            height = EMPTY-LEVEL_3;
                            break;
                        case 1:
                            height = LEVEL_1-LEVEL_3;
                            break;
                        case 2:
                            height = LEVEL_2-LEVEL_3;
                            break;
                        default:
                            height = 0;
                            break;
                    }
                    model3D = Importer3D.loadAsPoly(getClass().getResource("/dome.obj"));
            }
            Group block = model3D.getRoot();
            block.getTransforms().add(new Translate(-0.6, 0));
            if (space.getHeight().getValue()==10)
                block.getTransforms().add(new Translate(0, 0.3,0));
            block.getTransforms().add(new Translate(translation[1],height,translation[0]));
            board.getChildren().add(block);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private double[] getTranslationNeeded
            ( int toXCoordinate, int toZCoordinate)
    {
        double xTranslation;
        if ((toXCoordinate-2)>0)
            xTranslation = (toXCoordinate-2)*POSITIVE_Z_TRANSLATION;
        else
            xTranslation = (toXCoordinate-2)*NEGATIVE_Z_TRANSLATION;
        double zTranslation = (toZCoordinate - 2)*X_TRANSLATION;


        double[] result = new double[2];
        result[0] = xTranslation;
        result[1] = zTranslation;
        return result;
    }

    /**
     * Removes the opponent workers from the screen and remove opponent from various Lists/Maps
     * @param opponent The opponent to remove
     */
    private void eliminateOpponent (String opponent ){
        Group[] toRemove = opponentsWorkers.get(opponent);
        toRemove[0].setDisable(true);
        toRemove[0].setVisible(false);
        toRemove[1].setDisable(true);
        toRemove[1].setVisible(false);
        opponentsWorkers.remove(opponent);
        opponents.remove(opponent);
        opponentsColor.remove(opponent);
        opponentsGods.remove(opponent);
    }

    /**
     * Update the opponents list in case someone has lost before the end.
     * @param newGameCopy The new game update
     */
    private void updateOpponents(Game newGameCopy){
        ArrayList<String> activePlayers = new ArrayList<>();
        for (Player active : newGameCopy.getAllPlayers() )
            activePlayers.add(active.getName());
        // if the opponent is not in the active players List, eliminate the opponent
        for (String player : opponents) {
            if (!activePlayers.contains(player))
                eliminateOpponent(player);
        }
    }
}
