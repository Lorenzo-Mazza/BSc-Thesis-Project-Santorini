package it.polimi.ingsw.PSP50.View.GUI;
import javafx.application.Application;
import javafx.scene.Group;
import javafx.scene.Node;
import javafx.scene.PerspectiveCamera;
import javafx.scene.Scene;
import javafx.scene.transform.Rotate;
import javafx.scene.transform.Translate;
import javafx.stage.Stage;
import org.fxyz3d.importers.*;
import org.fxyz3d.importers.obj.ObjImporter;

import java.io.IOException;

public class Sample extends Application {

    private ObjImporter importer = new ObjImporter();

    private static final int WIDTH = 1400;
    private static final int  HEIGHT = 800;


    public Sample() throws IOException {
    }

    @Override
    public void start(Stage primaryStage) throws Exception {

        PerspectiveCamera camera = new PerspectiveCamera(true);
         camera.setTranslateZ(-15);
         camera.setTranslateY(-1.5);

        Model3D model = importer.loadAsPoly(getClass().getResource("/orangemalebuilder.obj"));


        Group group = model.getRoot();

        group.getTransforms().add(new Rotate(90, Rotate.Y_AXIS));
        group.getTransforms().add(new Rotate(180, Rotate.X_AXIS));
        group.getTransforms().add(new Translate(0,-1));


        Group root = new Group(group);

        Scene scene = new Scene(root, 1280, 720, true);
        scene.setCamera(camera);
        primaryStage.setScene(scene);
        primaryStage.show();

    }

    private Group loadModel(Model3D model3D) throws IOException {
        Group modelRoot = new Group();

        for (Node view : model3D.getMeshViews()) {
            modelRoot.getChildren().add(view);
        }

        return modelRoot;
    }


    public static void main(String[] args) {
        launch(args);
    }




}
