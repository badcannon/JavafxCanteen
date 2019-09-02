package com.DBMSproject;

import java.io.IOException;
import java.net.URL;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ResourceBundle;

import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXPasswordField;
import com.jfoenix.controls.JFXTextField;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.AnchorPane;

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
    void Minimize(ActionEvent event) {

        MainApp.PrimaryStage.setIconified(true);

    }

    @FXML
    void close(ActionEvent event) {
        MainApp.PrimaryStage.close();
    }

    @FXML
    void login(ActionEvent event) throws SQLException {
        String DefInsert = "INSERT INTO canteen.logindets VALUES('global','global')";
        Statement statement = MainApp.Connect.createStatement();

//        statement.execute(DefInsert);

    }


    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        ConnectDbLogin();
        MainApp.Dragable(root);

    }

    private void handelLogin() throws IOException {
        MainApp.setRoot("Views/MainWindow");
    }

    private void ConnectDbLogin() {
        boolean Con = MainApp.Connect();
        if(Con){
            try {
                String defaultStatement = "CREATE TABLE IF NOT EXISTS logindets(Username VARCHAR(20),Password VARCHAR(20)) ";
                Statement statement =MainApp.Connect.createStatement();
                String user = null , pass = null;
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

    private void SetAlert(String Value,boolean flag){
        if(flag){
            AlertLabel.setText(Value);
            AlertLabel.setStyle("-fx-text-fill: lime");
        }
        else {
            AlertLabel.setText(Value);
            AlertLabel.setStyle("-fx-text-fill: tomato");
        }

    }


}
