package it.polimi.ingsw.PSP50.View.GUI;

import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXTextField;
import it.polimi.ingsw.PSP50.Model.GameType;
import it.polimi.ingsw.PSP50.network.server.Server;
import javafx.fxml.*;
import javafx.scene.control.*;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.Socket;

public class LoginController {


    @FXML
    private TextField ipAddress;

    @FXML
    private TextField usernameText;

    @FXML
    private RadioButton twoPlayersButton;

    @FXML
    private RadioButton threePlayersButton;

    @FXML
    private Button loginButton;

    private String gameType = "";


    @FXML
    public void login() {
        Socket server= null;
        try{
            String ip = ipAddress.getText();
            if (ip.equals(""))
                ip = "127.0.1";
            /* open a connection to the server */
            server = new Socket(ip, Server.SOCKET_PORT);
        } catch (IOException e) {
            Alert alert = new Alert(Alert.AlertType.WARNING);
            alert.setTitle("Error");
            alert.setHeaderText("Server unreachable");
            alert.showAndWait();
            return;
        }
        if (getUsername().equals("")) {
            Alert alert = new Alert(Alert.AlertType.WARNING);
            alert.setTitle("Error");
            alert.setHeaderText("Invalid username");
            alert.showAndWait();
        }

        GameType type;
        while (true) {
            if (gameType.equals("")) {
                Alert alert = new Alert(Alert.AlertType.WARNING);
                alert.setTitle("Error");
                alert.setHeaderText("Invalid GameType");
                alert.showAndWait();
            }
            else break;
        }
        if (gameType.equals("TWO PLAYERS")) {
            type= GameType.TWOPLAYERS;
        }
        else {
            type=GameType.THREEPLAYERS;
        }
        GuiView gui= new GuiView(type, server, usernameText.getText(), (Stage) loginButton.getScene().getWindow());
        gui.startGame();
    }

    public String getUsername() {
        return usernameText.getText();
    }

    @FXML
    public void setThreePlayersButton(){
        gameType= threePlayersButton.getText();
    }
    @FXML
    public void setTwoPlayersButton(){
        gameType= twoPlayersButton.getText();
    }




}
