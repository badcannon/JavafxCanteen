package com.DBMSproject;

import com.DBMSproject.Enums.StageManager;
import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXDrawer;
import com.jfoenix.controls.JFXHamburger;
import com.jfoenix.transitions.hamburger.HamburgerSlideCloseTransition;
import java.io.IOException;
import java.net.URL;
import java.util.Optional;
import java.util.ResourceBundle;
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
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;


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
    

    
//    This must be accessed by orders.java

   
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
        Orders ord = new Orders();
        Alert a= new Alert (Alert.AlertType.CONFIRMATION);
        ord.StyleAlert(a);
        a.setHeaderText("Log Off?");
        a.setContentText("Are you Sure You want to Log Out "+LoginController.getCurrentUser()+"? ");
        Optional<ButtonType> result = a.showAndWait();
        if(result.get() == ButtonType.OK){
        MainApp.setRoot(StageManager.Change("LOGIN") );

        }
        else{
            System.out.println("Cancle!");
        }
        
    }
    

    @Override
    public void initialize(URL arg0, ResourceBundle arg1) {
        try {
            Center();
            DrawerProps();
            MainApp.Dragable(root);
            ConnectDb();
            transHam();
            CloseDrawer();
            loignInfo();
//       Styles(); 
        } catch (IOException ex) {
                        System.out.println("Io first!");
        }
        
    }
    private HamburgerSlideCloseTransition transition;
    public void transHam(){
            
        HamburgerSlideCloseTransition transition1 = new HamburgerSlideCloseTransition(Ham1);
        this.transition = transition1;
        transition.setRate(-1);
        Ham1.addEventHandler(MouseEvent.MOUSE_CLICKED, (e)->{
                //if its open close if its closed open :D
               if(drawer.isOpened()){
                       transition.setRate(-1);
                       transition.play();
                       drawer.close();
                       drawer.toBack();
                    }
               if(drawer.isClosed()){
                   transition.setRate(1);
                   transition.play();
                   drawer.open();
                   drawer.toFront();
               }
        });
      
        
    
    
    }
  
      
    public void CloseDrawer(){
       root.addEventHandler(MouseEvent.MOUSE_CLICKED, (e)->{
              
             {  
                    if(drawer.isOpened()){
                       transition.setRate(-1);
                       transition.play();
                       drawer.close();      
                    }
             }
        
        });
    }

    private void loignInfo() {
    
           loginInfo.setText("Welcome "
           +LoginController.getCurrentUser() + "You have logged in at \n" + new java.util.Date().toString());

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
        AnchorPane pane = new AnchorPane();
        pane = new FXMLLoader(getClass().getResource(StageManager.Change("DRAWER"))).load();
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
                            
                            case "Orders":  pane = FXMLLoader.load(getClass().getResource(StageManager.Change("ORDERS")));
                                            Displays.getChildren().removeAll();
                                            Displays.getChildren().setAll(pane);
                            break;
                            case "Option2": pane1 = FXMLLoader.load(getClass().getResource(StageManager.Change("STATS")));
                                            Displays.getChildren().setAll(pane1);
                            break;
                            case "Option3": pane2 = FXMLLoader.load(getClass().getResource(StageManager.Change("SETTINGS")));
                                            Displays.getChildren().setAll(pane2);
                            break;
                            case "Option4": handelExit();
                            break;
                            
                            default : System.out.println("Wrong Option!");
                            
                        }
                    } catch (IOException ex) {
                            ex.printStackTrace();
                    }
            
                    }
            });
            
            
            }
            
            
            
        }
        
    }
    
    void handelExit(){
         Orders ord = new Orders();
        Alert a= new Alert (Alert.AlertType.CONFIRMATION);
        ord.StyleAlert(a);
        a.setHeaderText("Exit");
        a.setContentText("Are you Sure You want to Quit "+LoginController.getCurrentUser()+"? ");
        Optional<ButtonType> result = a.showAndWait();
        if(result.get() == ButtonType.OK){
       
            MainApp.PrimaryStage.close();
        }
        else{
            System.out.println("Cancle!");
        }
        
    
    }

   public JFXDrawer getdrawer() {
       return drawer;
    }

    public HamburgerSlideCloseTransition gettransition() {
        return transition;
    }

}