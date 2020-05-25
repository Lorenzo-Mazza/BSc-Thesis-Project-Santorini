package it.polimi.ingsw.PSP50.View.GUI;

import it.polimi.ingsw.PSP50.Model.Block;
import it.polimi.ingsw.PSP50.Model.Color;
import it.polimi.ingsw.PSP50.Model.GameType;
import it.polimi.ingsw.PSP50.View.ClientView;
import it.polimi.ingsw.PSP50.network.client.ClientSocket;
import it.polimi.ingsw.PSP50.network.messages.ToServer.GodChoice;
import javafx.application.Platform;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.RadioButton;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.Socket;
import java.util.ArrayList;
import java.util.HashMap;

public class GuiView extends ClientView {

    private Stage primaryStage;

    private Scene lobby;
    private Scene welcome;
    private Scene chooseGod;

    private LobbyController lobbyController;
    private WelcomeController welcomeController;
    private ChooseGodController chooseGodController;

    private HashMap<String, Color> opponents;

    private Color playerColor;



    public GuiView(GameType gameType, Socket server, String name, Stage primaryStage){
        this.setName(name);
        ClientSocket socket = new ClientSocket(this, gameType, server);
        this.setSocket(socket);
        this.primaryStage = primaryStage;
        Thread socketThread = new Thread(socket);
        socketThread.start();
    }

    public void startGame(){
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/fxml/Lobby.fxml"));
        try {
            Parent root = loader.load();
            primaryStage.setTitle("Lobby");
            lobby = new Scene(root);
            lobbyController = loader.getController();
            primaryStage.setScene(lobby);
            primaryStage.setTitle("Lobby");
            primaryStage.setResizable(false);
            primaryStage.show();
        } catch (IOException e) {
            System.out.println("Error creating the lobby");
        }
    }


    @Override
    public void update(Object modelCopy) {

    }

    @Override
    public void drawSection(String line) {

    }

    @Override
    public void chooseSpace(ArrayList<int[]> possibleChoices, boolean optional) {

    }

    @Override
    public void initializeWorkers(ArrayList<int[]> possibleChoices) {

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

    public void sendGodChoice(int choice){
        this.notifySocket(new GodChoice(choice,this.getPlayerId()));
    }

    @Override
    public void chooseBlock(Block possibleBlock) {

    }

    @Override
    public void welcomeMessage(HashMap<String, Color> opponents, Color playerColor) {
        Platform.runLater(
                () -> {
        this.opponents = opponents;
        this.playerColor = playerColor;
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/fxml/Welcome.fxml"));
        try {
            Parent root = loader.load();
            primaryStage.setTitle("Welcome");
            welcome = new Scene(root);
            welcomeController = loader.getController();
            primaryStage.setScene(welcome);
            primaryStage.setResizable(false);
            welcomeController.setTextLabel(opponents, this.name, playerColor);
            primaryStage.show();
        } catch (IOException  e) {
            System.out.println("Error starting the game");
        }
                });
    }

}
