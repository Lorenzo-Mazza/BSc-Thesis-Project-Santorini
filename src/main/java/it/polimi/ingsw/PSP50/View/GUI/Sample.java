package it.polimi.ingsw.PSP50.View.GUI;
import javafx.animation.*;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Point3D;
import javafx.scene.*;
import javafx.scene.layout.*;
import javafx.scene.shape.ArcTo;
import javafx.scene.shape.CubicCurveTo;
import javafx.scene.shape.MoveTo;
import javafx.scene.shape.Path;
import javafx.scene.transform.Rotate;
import javafx.scene.transform.Translate;
import javafx.stage.Stage;
import javafx.util.Duration;
import org.fxyz3d.importers.*;

import java.io.IOException;

/**
 *  TESTING CLASS
 */


public class Sample extends Application {

    private Importer3D importer = new Importer3D();

    private static final double EMPTY = 1;
    private static final double LEVEL_1 = 2.4;
    private static final double LEVEL_2 = 3.4;
    private static final double LEVEL_3 = 4;
    private static final double[] NORTH_TRANSLATION= {0,-2.5};
    private static final double[] NORTHWEST_TRANSLATION= {-2.3,-2.5};
    private static final double[] NORTHEAST_TRANSLATION= {2.3,-2.5};
    private static final double[] WEST_TRANSLATION= {-2.3,0};
    private static final double[] EAST_TRANSLATION= {2.3,0};
    private static final double[] SOUTHWEST_TRANSLATION= {-2.3,2.3};
    private static final double[] SOUTHEAST_TRANSLATION= {2.3,2.3};


    private static final int  HEIGHT = 800;


    public Sample() throws IOException {
    }

    @Override
    public void start(Stage primaryStage) throws Exception {

        PerspectiveCamera camera = new PerspectiveCamera(true);
        //camera.setVerticalFieldOfView(true);

        camera.setTranslateZ(-9);
        camera.setTranslateY(-35);
        camera.getTransforms().add(new Rotate(-75,Rotate.X_AXIS));

        Model3D boardModel = Importer3D.loadAsPoly(getClass().getResource("/boardcliff2.obj"));
        Model3D seaModel = Importer3D.loadAsPoly(getClass().getResource("/sea.obj"));
        Model3D builder = Importer3D.loadAsPoly(getClass().getResource("/orangemalebuilder.obj"));
        Model3D builder2 = Importer3D.loadAsPoly(getClass().getResource("/orangemalebuilder.obj"));
        Model3D block1 = Importer3D.loadAsPoly(getClass().getResource("/firstblock2.obj"));
        Model3D block1bis = Importer3D.loadAsPoly(getClass().getResource("/firstblock2.obj"));
        Model3D block2 = Importer3D.loadAsPoly(getClass().getResource("/secondblock2.obj"));
        Model3D block2bis = Importer3D.loadAsPoly(getClass().getResource("/secondblock2.obj"));
        Model3D block3 = Importer3D.loadAsPoly(getClass().getResource("/thirdblock.obj"));
        Model3D domeModel = Importer3D.loadAsPoly(getClass().getResource("/dome.obj"));


        Group board = boardModel.getRoot();
        //board.setScaleX(1.5);
        //board.setScaleY(1.5);
        //board.setScaleZ(1.5);
        Group sea = seaModel.getRoot();
        Group build1 = builder.getRoot();
        Group build2 = builder2.getRoot();
        Group firstBlock = block1.getRoot();
        Group firstBlock2 = block1bis.getRoot();
        Group secondBlock = block2.getRoot();
        Group secondBlock2 = block2bis.getRoot();
        Group thirdBlock = block3.getRoot();
        Group dome = domeModel.getRoot();

        board.getTransforms().add(new Rotate(-180, Rotate.X_AXIS));
        board.getTransforms().add(new Translate(0.7,0));
        sea.getTransforms().add(new Rotate(-180, Rotate.X_AXIS));
        sea.getTransforms().add(new Translate(0,-6,-2.5));
        build1.getTransforms().add(new Rotate(-180, Rotate.X_AXIS));
        build1.getTransforms().add(new Translate(2.3,EMPTY));
        build2.getTransforms().add(new Rotate(-180, Rotate.X_AXIS));
        build2.getTransforms().add(new Translate(-4.6,2.4,-2.5));
      //  build2.getTransforms().add(new Translate(0,0,4.6));
       // build2.getTransforms().add(new Translate(-4.6,0,0));
        firstBlock.getTransforms().add(new Rotate(-180, Rotate.X_AXIS));
        firstBlock2.getTransforms().add(new Rotate(-180, Rotate.X_AXIS));
        firstBlock.getTransforms().add(new Translate(-4.6,0,-2.5));
        firstBlock2.getTransforms().add(new Translate(-2.3,0,0));
        secondBlock.getTransforms().add(new Rotate(-180, Rotate.X_AXIS));
        secondBlock.getTransforms().add(new Translate(-4.6,0,-2.5));
        secondBlock2.getTransforms().add(new Rotate(-180, Rotate.X_AXIS));
        secondBlock2.getTransforms().add(new Translate(-2.3,0,0));
        thirdBlock.getTransforms().add(new Rotate(-180, Rotate.X_AXIS));
        thirdBlock.getTransforms().add(new Translate(-4.6,0,-2.5));
        dome.getTransforms().add(new Rotate(-180, Rotate.X_AXIS));
        dome.getTransforms().add(new Translate(-2.3,1-LEVEL_3,-2.5));



        Group root3D = new Group(board,build1,sea,firstBlock,firstBlock2,dome);
        SubScene subScene = new SubScene(root3D, 1200, 700, true,SceneAntialiasing.BALANCED);
        subScene.setCamera(camera);
        build1.setDisable(true);
        build1.setVisible(false);
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/fxml/GameBoard.fxml"));
        AnchorPane testing = loader.load();
        testing.getChildren().add(subScene);
        //testing.toFront();
        subScene.toBack();
        Scene scene = new Scene(testing);
        primaryStage.setScene(scene);
        primaryStage.setResizable(false);
        primaryStage.show();
        Model3D worker = Importer3D.loadAsPoly(getClass().getResource("/maleblueworker.obj"));
        Group workerObject = worker.getRoot();
        workerObject.getTransforms().add(new Rotate(-180, Rotate.X_AXIS));
        workerObject.getTransforms().add(new Translate(0,EMPTY,0));
        TranslateTransition preventCollisions = new TranslateTransition(Duration.seconds(0.2),workerObject);
        TranslateTransition preventCollisions2 = new TranslateTransition(Duration.seconds(0.2),workerObject);

        // workerObject.setRotationAxis(Rotate.Z_AXIS);
       // preventCollisions.setToX(-2.3);
       // preventCollisions.setToY(-0.8);
        preventCollisions.setToY(-5);
        preventCollisions2.setToY(-5);
        RotateTransition rotateTransition = new RotateTransition(Duration.seconds(5),workerObject);
        rotateTransition.setByAngle(360);
        rotateTransition.setCycleCount(1);
        rotateTransition.setAxis(Rotate.Z_AXIS);
        SequentialTransition sequentialTransition = new SequentialTransition();

         // transition.setToY(-LEVEL_1);
       // TranslateTransition transition2 = new TranslateTransition(Duration.seconds(1),workerObject);
       // TranslateTransition transition3 = new TranslateTransition(Duration.seconds(2.5),workerObject);
      //  transition2.setToZ(-8);
     //   transition3.setToZ(-1.4);
        TranslateTransition newTransition = new TranslateTransition(Duration.seconds(5),workerObject);
        newTransition.setToX(-4.6);
        newTransition.setToY(-LEVEL_1);
        newTransition.setToZ(2.3);
        TranslateTransition secondTransition = new TranslateTransition(Duration.seconds(5),workerObject);
        secondTransition.setToX(-2.3);
        secondTransition.setToY(-LEVEL_1);
        secondTransition.setToZ(0);
        ParallelTransition parallel = new ParallelTransition();
        ParallelTransition parallel2 = new ParallelTransition();
        parallel.getChildren().addAll(rotateTransition,newTransition);
        parallel2.getChildren().addAll(rotateTransition,secondTransition);
        sequentialTransition.getChildren().addAll(preventCollisions,parallel,preventCollisions2,parallel2);
        sequentialTransition.play();

    /*  Timeline timeline = new Timeline();
      timeline.getKeyFrames().add(new KeyFrame(Duration.seconds(3),new KeyValue(workerObject.translateXProperty(),-2.3),new KeyValue(workerObject.translateYProperty(),-2.5)));
      timeline.play(); */
      root3D.getChildren().add(workerObject);
    }




    public static void main(String[] args) {
        launch(args);
    }



}
