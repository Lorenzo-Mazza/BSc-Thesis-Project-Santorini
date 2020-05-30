package it.polimi.ingsw.PSP50.View.GUI;

import it.polimi.ingsw.PSP50.Model.Block;
import it.polimi.ingsw.PSP50.network.messages.ToServer.BlockChoice;
import it.polimi.ingsw.PSP50.network.messages.ToServer.NoAction;
import it.polimi.ingsw.PSP50.network.messages.ToServer.SpaceChoice;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.geometry.Insets;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.Background;
import javafx.scene.layout.BackgroundFill;
import javafx.scene.layout.CornerRadii;
import javafx.scene.paint.Color;

import java.util.ArrayList;

public class BoardController {
    @FXML
    private Label myName;
    @FXML
    private Label myGodName;
    @FXML
    private ImageView myGodImage;
    @FXML
    private Label opp1Name;
    @FXML
    private Label opp1GodName;
    @FXML
    private ImageView opp1GodImage;
    @FXML
    private Label opp2Name;
    @FXML
    private Label opp2GodName;
    @FXML
    private ImageView opp2GodImage;
    @FXML
    private ComboBox<String> spaceChoice;
    @FXML
    private Label commandLabel;
    @FXML
    private Button commandAction;
    @FXML
    private Button commandAction2;
    @FXML
    private Button skipButton;
    @FXML
    private Button blockButton;



    private ArrayList<int[]> possibleChoices;

    private GuiView gui;




    public void setMyPlayer(String godPath) {
        this.myName.setText(gui.getName());
        this.myGodName.setText(gui.getGod());
        setColor(gui.getPlayerColor(),this.myName);
        setColor(gui.getPlayerColor(),this.myGodName);
        this.myGodImage.setImage(new Image(godPath));
    }

    public void setOpponent1(String name, String opp1GodImage) {
        this.opp1Name.setText(name);
        this.opp1GodName.setText(gui.getOpponentsGods().get(name));
        this.opp1GodImage.setImage(new Image(opp1GodImage));
        it.polimi.ingsw.PSP50.Model.Color color = gui.getOpponentsColor().get(name);
        setColor(color,this.opp1Name);
        setColor(color,this.opp1GodName);
        this.opp1Name.setVisible(true);
        this.opp1GodName.setVisible(true);
    }

    public void setOpponent2(String name, String opp2GodImage) {
        this.opp2Name.setText(name);
        this.opp2GodName.setText(gui.getOpponentsGods().get(name));
        this.opp2GodImage.setImage(new Image(opp2GodImage));
        it.polimi.ingsw.PSP50.Model.Color color = gui.getOpponentsColor().get(name);
        setColor(color,this.opp2Name);
        setColor(color,this.opp2GodName);
        this.opp2Name.setVisible(true);
        this.opp2GodName.setVisible(true);
    }

    void setGui(GuiView gui){
        this.gui = gui;
    }

    private void setColor (it.polimi.ingsw.PSP50.Model.Color color, Label label)
    {
        switch (color.getName()) {
            case "Orange":
                label.setBackground(new Background(new BackgroundFill(Color.ORANGE, CornerRadii.EMPTY, Insets.EMPTY)));
                break;
            case "Pink":
                label.setBackground(new Background(new BackgroundFill(Color.PINK, CornerRadii.EMPTY, Insets.EMPTY)));
                break;
            case "Blue":
                label.setBackground(new Background(new BackgroundFill(Color.LIGHTBLUE, CornerRadii.EMPTY, Insets.EMPTY)));
                break;
        }
    }

    public void setSpaceChoice(ArrayList<int[]> choices){
        possibleChoices = choices;
        ArrayList<String> stringChoices = new ArrayList<>();
        for (int[] choice : choices)
        {
            String result = convertChoiceToString(choice);
            stringChoices.add(result);
        }
        ObservableList<String> list = FXCollections.observableArrayList(stringChoices);
        spaceChoice.setItems(list);
        spaceChoice.setVisible(true);
        spaceChoice.setDisable(false);

    }




    public void setCommandText(String text){
        commandLabel.setVisible(true);
        commandLabel.setDisable(false);
        commandLabel.setText(text);
    }


    @FXML
    public void getInitWorkerPosition(){
        spaceChoice.setVisible(false);
        spaceChoice.setDisable(true);
        commandAction.setVisible(false);
        commandAction.setDisable(true);
        commandLabel.setVisible(false);
        commandLabel.setDisable(true);
        if (spaceChoice.getValue() == null || spaceChoice.getValue().isEmpty())
            gui.placeWorker(possibleChoices.get(0));
        else
            gui.placeWorker(convertStringToChoice(spaceChoice.getValue()));
    }

    @FXML
    public void performAction(){
        spaceChoice.setVisible(false);
        spaceChoice.setDisable(true);
        commandAction2.setVisible(false);
        commandAction2.setDisable(true);
        commandLabel.setVisible(false);
        commandLabel.setDisable(true);
        skipButton.setVisible(false);
        skipButton.setDisable(true);
        if (spaceChoice.getValue() == null || spaceChoice.getValue().isEmpty() )
            gui.notifySocket(new SpaceChoice(possibleChoices.get(0),gui.getPlayerId()));
        else
            gui.notifySocket(new SpaceChoice(convertStringToChoice(spaceChoice.getValue()),gui.getPlayerId()));
    }

    @FXML
    public void skipAction(){
        spaceChoice.setVisible(false);
        spaceChoice.setDisable(true);
        commandAction2.setVisible(false);
        commandAction2.setDisable(true);
        skipButton.setVisible(false);
        skipButton.setDisable(true);
        commandLabel.setVisible(false);
        commandLabel.setDisable(true);
        gui.notifySocket(new NoAction(null,gui.getPlayerId()));
    }

    @FXML
    public void chooseBlock(){
        spaceChoice.setVisible(false);
        spaceChoice.setDisable(true);
        blockButton.setVisible(false);
        blockButton.setDisable(true);
        commandLabel.setVisible(false);
        commandLabel.setDisable(true);
        String result;
        if (spaceChoice.getValue() != null)
            result = spaceChoice.getValue();
        else
            result = "";
        Block answer;
        switch (result){
            case "EMPTY":
                answer = Block.EMPTY;
                break;
            case "LEVEL1":
                answer = Block.LEVEL1;
                break;
            case "LEVEL2":
                answer = Block.LEVEL2;
                break;
            case "LEVEL3":
                answer = Block.LEVEL3;
                break;
            default:
                answer = Block.DOME;
                break;
        }
        gui.notifySocket(new BlockChoice(answer,gui.getPlayerId()));
    }


        private String convertChoiceToString(int[] choice) {
        return "("+ choice[0]+","+ choice[1]+")";
    }

    private int[] convertStringToChoice(String value) {
        int x = Integer.parseInt(String.valueOf(value.charAt(1)));
        int y = Integer.parseInt(String.valueOf(value.charAt(3)));
        for (int[] choice : possibleChoices){
            if (choice[0] == x && choice[1] == y)
                return choice;
        }
        return null;
    }

    public void enableFirstButton() {
        commandAction.setDisable(false);
        commandAction.setVisible(true);
    }
    public void enableActionButton() {
        commandAction2.setDisable(false);
        commandAction2.setVisible(true);
    }
    public void enableSkipButton(){
        skipButton.setDisable(false);
        skipButton.setVisible(true);
    }

    public void setBlockChoice(ArrayList<String> blocks) {
        ObservableList<String> list = FXCollections.observableArrayList(blocks);
        spaceChoice.setItems(list);
        spaceChoice.setVisible(true);
        spaceChoice.setDisable(false);
    }

    public void enableBlockButton() {
        blockButton.setDisable(false);
        blockButton.setVisible(true);
    }
}
