package it.polimi.ingsw.PSP50.View.GUI;

import javafx.application.Application;
import javafx.application.Platform;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.net.URL;

public class LoginPage extends Application {


    @Override
    public void start(Stage primaryStage) throws Exception {

        FXMLLoader loader = new FXMLLoader();
        loader.setLocation(new URL("file:///Users/lollo/Desktop/ing-sw-2019-miraka-mazza-garofalo/src/main/resources/fxml/LoginPage.fxml"));
        Parent root = loader.<Parent>load();
        primaryStage.setTitle("SANTORINI");
        Scene scene = new Scene(root, 1100, 700);
        primaryStage.setScene(scene);
        primaryStage.setResizable(false);
        primaryStage.show();
        primaryStage.setOnCloseRequest(event -> {
            Platform.exit();
            System.exit(0);
        });
    }

    public static void loginPage() {
        launch();
    }

}
