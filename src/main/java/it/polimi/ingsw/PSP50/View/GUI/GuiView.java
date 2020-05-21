package it.polimi.ingsw.PSP50.View.GUI;

import it.polimi.ingsw.PSP50.Model.Block;
import it.polimi.ingsw.PSP50.View.ClientView;
import javafx.application.Application;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.util.ArrayList;

public class GuiView extends ClientView {

    public GuiView(){
        LoginController loginController = new LoginController(this);
        Application.launch(LoginPage.class);
        while (name==null)
            Thread.yield();
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

    }

    @Override
    public void chooseBlock(Block possibleBlock) {

    }
}
