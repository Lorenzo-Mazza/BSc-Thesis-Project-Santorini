package it.polimi.ingsw.PSP50.View.GUI;

import it.polimi.ingsw.PSP50.Model.Color;
import javafx.fxml.FXML;
import javafx.scene.control.Label;

import java.util.HashMap;

public class WelcomeController {

    @FXML
    private Label textLabel;

    public void setTextLabel(HashMap<String, Color> map, String playerName, Color playerColor){
        String message =
                " Welcome to SANTORINI, " + playerName.toUpperCase() + "!!! " +
                        "The color of your workers will be: " +
                         playerColor.getName() + ". "+
                        "Your rivals are:  ";
        for (String opponent : map.keySet())
            message+= "---> "+ opponent +" with the color "+
                    (map.get(opponent).getName()) + ".  ";
        textLabel.setText(message);
    }
}
