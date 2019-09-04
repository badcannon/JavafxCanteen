package com.DBMSproject;

import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXDrawer;
import com.jfoenix.controls.JFXHamburger;
import com.jfoenix.transitions.hamburger.HamburgerSlideCloseTransition;
import java.io.IOException;
import java.net.URL;
import java.sql.Date;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Cursor;
import javafx.scene.control.Label;
import javafx.scene.control.Tooltip;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import java.util.*;

public class MainWindowController implements Initializable{
    
    @FXML
    private AnchorPane root;
    
    @FXML
    private JFXButton signout;
 
    @FXML
    private ImageView logo;
    
    @FXML
    private JFXHamburger Ham1;
    
    @FXML
    private Label loginInfo;
    
    @FXML
    private JFXDrawer drawer;
    
    @FXML
    void Minimize(MouseEvent event) {
        if(event.getSource() == logo){
            MainApp.PrimaryStage.setIconified(true);
        }
    }
    
    @FXML
    void Sign(ActionEvent event) throws IOException{
        handelSignout();
    }

    @FXML
    private void handelSignout() throws IOException {
        MainApp.setRoot("Views/LoginWindow");
        System.out.println("Done!");
    }

    @Override
    public void initialize(URL arg0, ResourceBundle arg1) {
        Center();
       MainApp.Dragable(root);
       ConnectDb();
       ToolTip();
       transHam();
       loignInfo();
//       Styles(); 
        
    }
    
    public void transHam(){
    
        HamburgerSlideCloseTransition transition = new HamburgerSlideCloseTransition(Ham1);
        transition.setRate(-1);
        Ham1.addEventHandler(MouseEvent.MOUSE_CLICKED, (e)->{
                //if its open close if its closed open :D
                transition.setRate(transition.getRate()*-1);
                transition.play();
                //if its open close if not open :D 
                drawer.toggle();
        });
    
    }

    public void ToolTip() {
       try{
        Tooltip tt = new Tooltip();
        tt.setText("Logout");
        tt.setStyle("-fx-font: normal bold 4 Langdon; "
    + "-fx-base: #AE3522; "
    + "-fx-text-fill: orange;");
        signout.setTooltip(tt);
    }
       catch(Exception e){
            System.out.println(e.getMessage());
    }
}

    private void loignInfo() {
            
  
        
           loginInfo.setText("Welcome "
           +"Global " + "You have logged in at \n" + new java.util.Date().toString());

    }

    private void ConnectDb() {
        boolean Con = MainApp.Connect();
        if(Con){
            

        }
        else {
            System.out.println("Oopsi");
        }
    }

    private void Center() {
       MainApp.PrimaryStage.setHeight(720);
       MainApp.PrimaryStage.setWidth(1200);
       MainApp.PrimaryStage.setX((MainApp.bounds.getWidth()-1200)/2);
       MainApp.PrimaryStage.setY((MainApp.bounds.getHeight()-720)/2);
    
    }

    private void Styles() {
       logo.setCursor(Cursor.HAND);
        signout.setCursor(Cursor.HAND);
    }
}