package it.polimi.ingsw.PSP50.View.GUI;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.RadioButton;
import javafx.scene.control.ToggleGroup;
import javafx.scene.image.ImageView;

import java.util.ArrayList;

public class ChooseGodController {

    private ArrayList<ImageView> godImages= new ArrayList<>();
    private ArrayList<RadioButton> buttons = new ArrayList<>();
    private ArrayList<ImageView> godPowers = new ArrayList<>();

    @FXML
    private ImageView godImage1;
    @FXML
    private ImageView godImage2;
    @FXML
    private ImageView godImage3;
    @FXML
    private ImageView godPower1;
    @FXML
    private ImageView godPower2;
    @FXML
    private ImageView godPower3;
    @FXML
    private RadioButton selectFirstGod;
    @FXML
    private RadioButton selectSecondGod;
    @FXML
    private RadioButton selectThirdGod;
    @FXML
    private ToggleGroup toggleGroup;

    private GuiView gui;

    public void initialize() {
        godImages.add(godImage1);
        godImages.add(godImage2);
        godImages.add(godImage3);
        godPowers.add(godPower1);
        godPowers.add(godPower2);
        godPowers.add(godPower3);
        buttons.add(selectFirstGod);
        buttons.add(selectSecondGod);
        buttons.add(selectThirdGod);
    }

    @FXML
    private void selectGod(){
        RadioButton button= (RadioButton) toggleGroup.getSelectedToggle();
        String selection = button.getText();
        int answer=0;
        if (selection.equals(selectFirstGod.getText()))
            answer=0;
        else if (selection.equals(selectSecondGod.getText()))
            answer=1;
        else
            answer=3;
        System.out.println("\n" + answer);
        gui.setGod(selection);
        gui.sendGodChoice(answer);
    }


    ArrayList<ImageView> getGodImages(){
        return godImages;
    }

    ArrayList<ImageView> getGodPowers() {
        return godPowers;
    }

    public ArrayList<RadioButton> getButtons() {
        return buttons;
    }

    void setGui(GuiView gui){
        this.gui = gui;
    }
}
