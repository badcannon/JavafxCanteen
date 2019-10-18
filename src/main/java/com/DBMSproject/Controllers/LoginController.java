
package com.DBMSproject;
import java.io.IOException;
import java.net.URL;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ResourceBundle;

import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXPasswordField;
import com.jfoenix.controls.JFXTextField;
import javafx.animation.RotateTransition;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Cursor;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.AnchorPane;
import javafx.scene.shape.Circle;
import javafx.util.Duration;
import com.DBMSproject.Enums.StageManager;
import com.jfoenix.controls.JFXComboBox;
import java.io.PrintWriter;
import java.io.StringWriter;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

public class LoginController implements Initializable {

    static String CurrentUser;
    
    public static String getCurrentUser() {
        return CurrentUser;
    }

    @FXML
    private Label AlertLabel;

    @FXML
    private AnchorPane root;

    @FXML
    private JFXTextField Username;

    @FXML
    private JFXPasswordField Password;

    @FXML
    private Label LoginLabel;

    @FXML
    private Button minbtn;

    @FXML
    private Button clsbtn;

    @FXML
    private JFXButton logbtn;

    @FXML
    private Circle Circle;
    
    @FXML
    private JFXComboBox<String> Roles;
    
    private boolean flag = false;

    
    private Map<String,String> RolesHash = new HashMap<>();
    private Map<String,String> LoginHash = new HashMap<>();

    @FXML
    void Minimize(ActionEvent event) {

        MainApp.PrimaryStage.setIconified(true);

    }

    @FXML
    void close(ActionEvent event) {
        MainApp.PrimaryStage.close();
    }

    @FXML
    void login(ActionEvent event) throws SQLException, IOException, InterruptedException {
        Statement statement = MainApp.Connect.createStatement();
        ResultSet rs = statement.executeQuery("SELECT  * from canteen.logindets");
        ResultSet role = statement.executeQuery("SELECT * FROM canteen.roles;");
        Set <Map.Entry<String,String>> st = LoginHash.entrySet();
        for(Map.Entry<String, String> mt:st){
            if(mt.getKey().equals(GetUsername())){
                if(mt.getValue().equals(GetPassword()))
                {
                   if(findRole(GetUsername()))
                        handelLogin();
                   else
                       SetAlert("Role Set Is Wrong !", false);
                
                }
                else{
                    
                    SetAlert("Password Entered is wrong!", false);
                 
                }
            
            }
            else {
                SetAlert("Username is Not found in the Database ! ", false);
            }
        
        }
        

    }

    private void playAnimation() {
        RotateTransition rotateTransition = new RotateTransition();
        rotateTransition.setAutoReverse(false);
        rotateTransition.setByAngle(360);
        rotateTransition.setDuration(Duration.millis(200));
        rotateTransition.setCycleCount(1000);
        rotateTransition.setNode(Circle);
    }


    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        try {
            Center();
            ConnectDbLogin();
            MainApp.Dragable(root);
            Styles();
            InitializeValues();
        } catch (SQLException ex) {
            Logger.getLogger(LoginController.class.getName()).log(Level.SEVERE, null, ex);
        }

    }

    private void handelLogin() throws IOException {
       try
        {
        MainApp.setRoot(StageManager.Change("MAINWINDOW") );
       }catch (Exception e ){
           StringWriter sw = new StringWriter();
           PrintWriter pw = new PrintWriter(sw);
           System.out.println(e.getLocalizedMessage());
       }
    }

    private void ConnectDbLogin() {
        boolean Con = MainApp.Connect();
        if(Con){
            try {
                String LoginTableStatement = "CREATE TABLE IF NOT EXISTS canteen.logindets(Username VARCHAR(20) PRIMARY KEY,Password VARCHAR(20)) ";
                String RolesTableStatement = "CREATE TABLE IF NOT EXISTS `canteen`.`roles` (\n" +
                                             "  `Role` varchar(20) NOT NULL,\n" +
                                             "  `Username` varchar(20) NOT NULL,\n" +
                                             "  PRIMARY KEY (`Username`),\n" +
                                             "  KEY `Username_idx` (`Username`),\n" +
                                             "  CONSTRAINT `Username` FOREIGN KEY (`Username`) REFERENCES `logindets` (`Username`) ON DELETE CASCADE ON UPDATE CASCADE\n" +
                                             ") ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;";
                Statement statement =MainApp.Connect.createStatement();
                statement.execute(LoginTableStatement);
                statement.execute(RolesTableStatement);
                }
            catch (SQLException e ){
                System.out.println("Sql Error");
            }
        }
        else{
            Alert a = new Alert(Alert.AlertType.ERROR);
            a.setTitle("Database Error!");
            a.showAndWait();
        }
    }

    private String GetUsername(){
        CurrentUser = Username.getText();
        return Username.getText();

    }

    private String GetPassword(){
        return Password.getText();
    }

    private void SetUsername(String User){
        Username.setText(User);

    }
    private void SetPassword(String Pass){
        Password.setText(Pass);
    }

    private void SetAlert(String Value,boolean flag) throws InterruptedException {
        if(flag){
            Circle.setStyle("-fx-stroke: lime");
            AlertLabel.setText(Value);
            AlertLabel.setStyle("-fx-text-fill: lime;-fx-font-weight: bold;-fx-font-size: 14px;");
        }
        else {
            Circle.setStyle("-fx-stroke: tomato");
            AlertLabel.setText(Value);
            AlertLabel.setStyle("-fx-text-fill: tomato;-fx-font-weight: bold;-fx-font-size: 14px;");
        }

    }

    private void Center() {
         MainApp.PrimaryStage.setHeight(548);
        MainApp.PrimaryStage.setWidth(713);
        MainApp.PrimaryStage.setX((MainApp.bounds.getWidth()-713)/2);
       MainApp.PrimaryStage.setY((MainApp.bounds.getHeight()-548)/2);
    
    }

    private void Styles() {
        logbtn.setCursor(Cursor.HAND);
        Username.setCursor(Cursor.TEXT);
        Password.setCursor(Cursor.TEXT);
        clsbtn.setCursor(Cursor.HAND);
        minbtn.setCursor(Cursor.HAND);
    }

    private void InitializeValues() throws SQLException {
        String DefInsert = "INSERT INTO canteen.logindets VALUES('global','global')";
        String DefRole = "INSERT INTO `canteen`.`roles`values('RootAdmin','global');";
        Statement statement = MainApp.Connect.createStatement();
        ResultSet rs = statement.executeQuery("SELECT  * from canteen.logindets");

        if(rs.next() == false){
        statement.execute(DefInsert);
        LoginHash.put("global","global");

        }
        else{
            do{
            
            String User = rs.getString("Username");
            String Pass = rs.getString("Password");
            
            LoginHash.put(User, Pass);
            
            
            }while(rs.next());
        
        }
        
        ResultSet role = statement.executeQuery("SELECT * FROM canteen.roles;");

        
        if(role.next() == false ){
                statement.execute(DefRole);
                RolesHash.put("RootAdmin", "global");
                Roles.getItems().add("RootAdmin");

                
         }
         else {
               do{
                    
                    String User = role.getString("Username");
                    String Role = role.getString("Role");
                    RolesHash.put(Role,User);
                    if(!Exists(Role))
                    Roles.getItems().add(Role);
                    
                
               }
                while(role.next());
            }


    }

    private boolean findRole(String UserName) {
       Set <Map.Entry<String,String>> st = RolesHash.entrySet();
       for (Map.Entry<String,String> Role : st){
           if(Role.getValue().equals(UserName)){
               
               if (Role.getKey().equals(Roles.getValue())){
                return true;
               }
                           
           }
       
       }
        
       return false;

    }

    private boolean Exists(String Role) {
        ObservableList<String> oblist= FXCollections.observableArrayList();
        oblist.addAll(Roles.getItems());
        oblist.forEach((role) ->{
        
            if(role.equals(Role)){
              flag = true;  
            }
            
        });
        
        flag = false;
        return flag;
    }
    
    
    
    

}
