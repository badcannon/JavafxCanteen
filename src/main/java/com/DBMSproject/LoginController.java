package com.DBMSproject;

import java.io.IOException;
import java.net.URL;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ResourceBundle;
import java.util.concurrent.TimeUnit;

import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXPasswordField;
import com.jfoenix.controls.JFXTextField;
import javafx.animation.RotateTransition;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.Pane;
import javafx.scene.shape.Circle;
import javafx.util.Duration;

public class LoginController implements Initializable {

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
        MainApp.PrimaryStage.setHeight(548);
        MainApp.PrimaryStage.setWidth(713);
        MainApp.PrimaryStage.setX((MainApp.bounds.getWidth()-713)/2);
       MainApp.PrimaryStage.setY((MainApp.bounds.getHeight()-548)/2);
        ConnectDbLogin();
        MainApp.Dragable(root);

    }

    private void handelLogin() throws IOException {
       try
        {
        MainApp.setRoot("Views/MainWindow");
       }catch (Exception e ){
           System.out.println(e.getStackTrace());
       }
    }

    private void ConnectDbLogin() {
        boolean Con = MainApp.Connect();
        if(Con){
            try {
                String defaultStatement = "CREATE TABLE IF NOT EXISTS canteen.logindets(Username VARCHAR(20),Password VARCHAR(20)) ";
                Statement statement =MainApp.Connect.createStatement();
                statement.execute(defaultStatement);
                }
            catch (Exception e ){
                System.out.println(e.getStackTrace());
            }
        }
        else{
            System.out.println("Problems!");

        }
    }

    private String GetUsername(){
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


}
