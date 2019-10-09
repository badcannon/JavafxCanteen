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
    void Minimize(ActionEvent event) {

        MainApp.PrimaryStage.setIconified(true);

    }

    @FXML
    void close(ActionEvent event) {
        MainApp.PrimaryStage.close();
    }

    @FXML
    void login(ActionEvent event) throws SQLException, IOException, InterruptedException {
        String DefInsert = "INSERT INTO canteen.logindets VALUES('global','global')";
        Statement statement = MainApp.Connect.createStatement();
        ResultSet rs = statement.executeQuery("SELECT  * from canteen.logindets");
        if(rs.next() == false){

            SetAlert("Creating Default Details Try again !",false);
            statement.execute(DefInsert);
        }
        else {
            do{
                String User = rs.getString("Username");
                String Pass = rs.getString("Password");

                if(User.equals(GetUsername()) && Pass.equals(GetPassword())){
                    SetAlert("Login Complete ! ",true);
                    playAnimation();
                    handelLogin();
                }
                else {
                    SetAlert("Wrong Username/Password ,Try Again",false);
                }
            }
            while (rs.next());
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
        Center();
        ConnectDbLogin();
        MainApp.Dragable(root);
        Styles();

    }

    private void handelLogin() throws IOException {
       try
        {
        MainApp.setRoot(StageManager.Change("MAINWINDOW") );
       }catch (Exception e ){
           System.out.println(e.getStackTrace().getClass().getMethods());
       }
    }

    private void ConnectDbLogin() {
        boolean Con = MainApp.Connect();
        if(Con){
            try {
                String LoginTableStatement = "CREATE TABLE IF NOT EXISTS canteen.logindets(Username VARCHAR(20) PRIMARY KEY,Password VARCHAR(20)) ";
                Statement statement =MainApp.Connect.createStatement();
                statement.execute(LoginTableStatement);
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
    
  


}
