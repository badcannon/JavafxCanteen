package com.DBMSproject;

import com.DBMSproject.Enums.StageManager;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.Statement;
import java.util.concurrent.atomic.AtomicReference;
import javafx.geometry.Rectangle2D;
import javafx.stage.Screen;

/**
 * JavaFX App and Mysql!
 */
public class MainApp extends Application {

    private static Scene scene;
    static Connection Connect;
    static Stage PrimaryStage;
//    To centre the window !
    static Screen screen = Screen.getPrimary();
    static Rectangle2D bounds = screen.getBounds();
    
   
    
    @Override
    public void start(Stage stage) throws IOException {
        PrimaryStage = stage;
        scene = new Scene(loadFXML(StageManager.Change("LOGIN")));
        stage.setScene(scene);
        stage.initStyle(StageStyle.UNDECORATED);
        stage.show();
    }

    static void setRoot(String fxml) throws IOException {
        scene.setRoot(loadFXML(fxml));
    }

    private static Parent loadFXML(String fxml) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(MainApp.class.getResource(fxml + ".fxml"));
        return fxmlLoader.load();
    }

    static boolean Connect(){
        try {
            String defaultStatement = "CREATE SCHEMA IF NOT exists `canteen` ;";
            Class.forName("com.mysql.cj.jdbc.Driver");
            //Defined Earlier !
            Connect = DriverManager.getConnection("jdbc:mysql://localhost:3306/","root","shosperm");
            Statement statement = Connect.createStatement();
            statement.execute(defaultStatement);
            Connect = DriverManager.getConnection("jdbc:mysql://localhost:3306/canteen","root","shosperm");

            }
        catch (Exception e){
            System.out.println(e.getMessage());
            return false;
        }
        return true;
    }

    public static void main(String[] args) {
        launch();
    }


    static void Dragable(AnchorPane root) {
        AtomicReference<Double> XOffset = new AtomicReference<>((double) 0);
        AtomicReference<Double> YOffset = new AtomicReference<>((double) 0);
        root.setOnMousePressed(mouseEvent -> {
            XOffset.set(mouseEvent.getSceneX());
            YOffset.set(mouseEvent.getSceneY());
        });
        root.setOnMouseDragged(mouseEvent -> {
            PrimaryStage.setX(mouseEvent.getScreenX()-XOffset.get());
            PrimaryStage.setY(mouseEvent.getScreenY()-XOffset.get());
            PrimaryStage.setOpacity(.8f);
        });
        root.setOnDragDone(dragEvent -> {
            PrimaryStage.setOpacity(1.0f);
        });
        root.setOnMouseReleased(mouseEvent -> {
            PrimaryStage.setOpacity(1.0f);
        });
    }
    
   
   
}