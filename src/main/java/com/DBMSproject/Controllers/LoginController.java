
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

//    Varibales For User,flag,Maping variables etc..
    static String CurrentUser;
    
    
    private boolean flag = false;

    
    private Map<String,String> RolesHash = new HashMap<>();
    
    
    private Map<String,String> LoginHash = new HashMap<>();


//    FXML variables ! 

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
    
    
    
//    LoginWindow Evaluator :
    LoginWindowHelper winHelp = new LoginWindowHelper();
    

//    Initalize the App :


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
    
    
    
    
    
        
//    Get the current User 
       public static String getCurrentUser() {
        return CurrentUser;
    }
    
    
    
    
//    Minimize the App 
    
    @FXML
    void Minimize(ActionEvent event) {

        MainApp.PrimaryStage.setIconified(true);

    }
    
        
 
//    Close the app !
    @FXML
    void close(ActionEvent event) {
        MainApp.PrimaryStage.close();
    }

    
//    Login Evaluator !
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
        boolean x = winHelp.ConnectToDb();
        if(!x){
            Alert a = new Alert(Alert.AlertType.ERROR);
            a.setTitle("Database Error!");
            a.showAndWait();
        }
    }
    
//   Get App UserName and Password Details 
    
    
    private String GetUsername(){
        CurrentUser = Username.getText();
        return Username.getText();

    }

    private String GetPassword(){
        return Password.getText();
    }

    

//Send Alert When the Password Is wrong!
    private void SetAlert(String Value,boolean flag) throws InterruptedException {
        if(!flag){
            AlertLabel.setText(Value);
            AlertLabel.setStyle("-fx-text-fill: tomato;-fx-font-weight: bold;-fx-font-size: 14px;");
        }

    }



    private void InitializeValues() throws SQLException {
        String Link = getClass().getResource("Assets/code.png").toString();
        Orders ord = new Orders();
        String DefInsert = "INSERT INTO canteen.logindets VALUES('"+Link+"','Dev','root','root',0,'"+ord.getCurrentTime()+"','Dev')";
        String DefRole = "INSERT INTO `canteen`.`roles`values('RootAdmin','root');";
        Statement statement = MainApp.Connect.createStatement();
        ResultSet rs = statement.executeQuery("SELECT  * from canteen.logindets");

        if(rs.next() == false){
        statement.execute(DefInsert);
        LoginHash.put("root","root");

        }
        else{
            do{
            
            String User = rs.getString("Username");
            String Pass = rs.getString("Password");
            System.out.println(User + ":" + Pass);
            LoginHash.put(User, Pass);
            
            
            }while(rs.next());
        
        }
        
        ResultSet role = statement.executeQuery("SELECT * FROM canteen.roles;");

        
        if(role.next() == false ){
                statement.execute(DefRole);
                System.out.println("Here ?");
                RolesHash.put("RootAdmin", "root");
                Roles.getItems().add("RootAdmin");

                
         }
         else {
            Roles.getItems().clear();
               do{
                    String User = role.getString("Username");
                    String Role = role.getString("Role");
                    System.out.println(User +":"+Role);
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
        
            System.out.println(role +":"+Role);
            if(role.equals(Role)){
              flag = true;  
            }
            
        });
        
        return flag;
    }
    
    
    
//    Center the app in the Given Display!
    
        private void Center() {
        MainApp.PrimaryStage.setHeight(548);
        MainApp.PrimaryStage.setWidth(713);
        MainApp.PrimaryStage.setX((MainApp.bounds.getWidth()-713)/2);
        MainApp.PrimaryStage.setY((MainApp.bounds.getHeight()-548)/2);
    
    }

//  Set Cursor Style For certain Elements/controls :
    private void Styles() {
        logbtn.setCursor(Cursor.HAND);
        Username.setCursor(Cursor.TEXT);
        Password.setCursor(Cursor.TEXT);
        clsbtn.setCursor(Cursor.HAND);
        minbtn.setCursor(Cursor.HAND);
    }
    
    
    
    

}
