package it.polimi.ingsw.PSP50.View.GUI;

import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXTextField;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.stage.Stage;

public class LoginController {

    GuiView gui;

    @FXML
    private JFXTextField usernameText;

    @FXML
    private RadioButton twoPlayersButton;

    @FXML
    private RadioButton threePlayersButton;

    @FXML
    private Button loginButton;

    private String gameType;

    LoginController(GuiView gui) {
        this.gui = gui;
    }

    @FXML
    public void login() {
        if (getUsername().equals("")) {
            Alert alert = new Alert(Alert.AlertType.WARNING);
            alert.setTitle("Error");
            alert.setHeaderText("Invalid username");
            alert.showAndWait();
        }
        else gui.setName(usernameText.getText());
       // return new GuiView(this, getUsername(), (Stage) loginButton.getScene().getWindow(), loginButton.getScene());
    }

    public String getUsername() {
        return usernameText.getText();
    }



}
