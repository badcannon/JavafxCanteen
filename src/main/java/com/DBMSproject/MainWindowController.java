package com.DBMSproject;

import com.jfoenix.controls.JFXButton;
import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Tooltip;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;

public class MainWindowController implements Initializable{
    
    @FXML
    private AnchorPane root;
    
    @FXML
    private JFXButton signout;
 
    @FXML
    private ImageView logo;

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
       MainApp.PrimaryStage.setHeight(720);
       MainApp.PrimaryStage.setWidth(1200);
       MainApp.PrimaryStage.setX((MainApp.bounds.getWidth()-1200)/2);
       MainApp.PrimaryStage.setY((MainApp.bounds.getHeight()-720)/2);
       MainApp.Dragable(root);
       ToolTip();
        
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
}