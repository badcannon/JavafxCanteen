package com.DBMSproject;

import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXDrawer;
import com.jfoenix.controls.JFXHamburger;
import com.jfoenix.transitions.hamburger.HamburgerSlideCloseTransition;
import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Cursor;
import javafx.scene.control.Label;
import javafx.scene.control.Tooltip;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.VBox;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.layout.Pane;


public class MainWindowController implements Initializable{
    
    @FXML
    private AnchorPane root;
    
    
    @FXML
    private AnchorPane Displays;
    
    
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
        try {
            Center();
            DrawerProps();
            MainApp.Dragable(root);
            ConnectDb();
            ToolTip();
            transHam();
            loignInfo();
//       Styles(); 
        } catch (IOException ex) {
            Logger.getLogger(MainWindowController.class.getName()).log(Level.SEVERE, null, ex);
        }
        
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

    private void DrawerProps() throws IOException {
        VBox pane = new VBox();
        pane = new FXMLLoader(getClass().getResource("Views/DrawerContent.fxml")).load();
        drawer.setSidePane(pane);
        //Assigning fucntions to the buttons with a loop !
        for (Node node : pane.getChildren()) {
            //checking if its null !
            if(node.getAccessibleText()!=null){
            //Adding Event Handlers :
            node.setOnMouseClicked(new EventHandler<MouseEvent>(){
                @Override
                public void handle(MouseEvent event){
                    try {
                        
                        AnchorPane pane,pane1,pane2;
                        switch(node.getAccessibleText()){
                            case "Orders": pane = FXMLLoader.load(getClass().getResource("Views/Orders.fxml"));
                                            Displays.getChildren().setAll(pane);
                            break;
                            case "Option2": pane1 = FXMLLoader.load(getClass().getResource("Views/Display2.fxml"));
                                            Displays.getChildren().setAll(pane1);
                            break;
                            case "Option3": pane2 = FXMLLoader.load(getClass().getResource("Views/Display3.fxml"));
                                            Displays.getChildren().setAll(pane2);
                            break;
                            case "Option4": MainApp.PrimaryStage.close();
                            break;
                            
                            default : System.out.println("Wrong Option!");
                            
                        }
                    } catch (IOException ex) {
                        Logger.getLogger(MainWindowController.class.getName()).log(Level.SEVERE, null, ex);
                    }
            
                    }
            });
            
            
            }
            
            
            
        }
        
    }
}