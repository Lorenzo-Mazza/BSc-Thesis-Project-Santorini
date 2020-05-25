package it.polimi.ingsw.PSP50.View.GUI;

import it.polimi.ingsw.PSP50.Model.Color;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.ProgressIndicator;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class LobbyController {
    private List<Label> playerLabels = new ArrayList<>();

    @FXML
    private Label player1;
    @FXML
    private Label player2;
    @FXML
    private Label labelWaiting;
    @FXML
    private ProgressIndicator bar;

    public void initialize() {
        playerLabels.add(player1);
        playerLabels.add(player2);
    }

    public void update(HashMap <String, Color> opponents) {
        int index = 0;
        for (String user : opponents.keySet()) {
            playerLabels.get(index).setText(user + " joined the game. His workers ");
            index++;
        }
        for (int i = opponents.size(); i < playerLabels.size(); i++)
            playerLabels.get(i).setText("");
    }


}
