/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.DBMSproject;

import com.DBMSproject.Enums.StageManager;
import com.jfoenix.controls.JFXButton;
import java.io.IOException;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.layout.AnchorPane;

/**
 *
 * @author shree
 */
public class HomeWindowController {
    @FXML
    private JFXButton getStartedOrders;

    @FXML
    private JFXButton getStartedStats;

    @FXML
    private JFXButton getStartedStaff;

    @FXML
    private JFXButton getStartedSettings;
    
    @FXML
    private AnchorPane Home;
    

    @FXML
    void HomeGetStarted(ActionEvent event) throws IOException {
        AnchorPane pane;
        if(event.getSource() == getStartedOrders){
           pane = FXMLLoader.load(getClass().getResource(StageManager.Change("ORDERS")));
           Home.getChildren().removeAll();
           Home.getChildren().setAll(pane);
            System.out.println("Done");
        }
        else if(event.getSource() == getStartedSettings ){
           pane = FXMLLoader.load(getClass().getResource(StageManager.Change("SETTINGS")));
           Home.getChildren().setAll(pane);
           System.out.println("Done2");

        }

        else if(event.getSource() == getStartedStats ){
           pane = FXMLLoader.load(getClass().getResource(StageManager.Change("STATS")));
           Home.getChildren().removeAll();
           Home.getChildren().setAll(pane);
                       System.out.println("Done3");

        }

        else {
           pane = FXMLLoader.load(getClass().getResource(StageManager.Change("STAFF")));
           Home.getChildren().removeAll();
           Home.getChildren().setAll(pane);
                       System.out.println("Done4");

        }

    }
    
}
