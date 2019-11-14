
package com.DBMSproject;

import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXComboBox;
import com.jfoenix.controls.JFXPasswordField;
import com.jfoenix.controls.JFXTextField;
import java.io.File;
import java.net.URL;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.stage.FileChooser;

public class Staff implements Initializable{
    
    @FXML
    private JFXButton createbtn;

    @FXML
    private JFXButton Viewbtn;

    @FXML
    private AnchorPane CreateDisplay;

    @FXML
    private ImageView RemoveImage;

    @FXML
    private JFXComboBox<String> UsersCombo;

    @FXML
    private AnchorPane viewDisplay;

    @FXML
    private TableView<ModelUsers> viewTable;

    @FXML
    private TableColumn<ModelUsers, ImageView> Col_DP;

    @FXML
    private TableColumn<ModelUsers, String> Col_Name;

    @FXML
    private TableColumn<ModelUsers, String> Col_Role;

    @FXML
    private TableColumn<ModelUsers, String> Col_userName;

    @FXML
    private TableColumn<ModelUsers, String> Col_Salary;

    @FXML
    private AnchorPane RemoveDisplay;

    @FXML
    private ImageView ImageCreate;

    @FXML
    private JFXTextField Name;

    @FXML
    private JFXTextField UserName;

    @FXML
    private JFXPasswordField Password;

    @FXML
    private JFXTextField Salary;

    @FXML
    private JFXComboBox<String> RolesCombo;

    @FXML
    private JFXButton SubmitNew;

    @FXML
    private JFXButton removebtn;
    
    @FXML
    private JFXButton Remove;
    
    
        
    
    @FXML
    void ChangePanel(ActionEvent event) {
        
        if(event.getSource() == createbtn){
        
            CreateDisplay.toFront();
        }
        if(event.getSource() == Viewbtn){
        
            viewDisplay.toFront();
           
        }
        if(event.getSource() == removebtn){
        
            RemoveDisplay.toFront();
        }
        

    }

    File file;
    
    
    @FXML
    void EditImage(MouseEvent event) {
            if(event.getSource() == ImageCreate){
                FileChooser fileChooser = new FileChooser();
        fileChooser.getExtensionFilters().addAll(new  FileChooser.ExtensionFilter("Image Files","*.png","*.jpeg","*.jpg"));
        file = fileChooser.showOpenDialog(MainApp.PrimaryStage);
            System.out.println(file);
             if(file!=null){
            try {
                Image image = new Image(file.toURI().toString(),200,150,true,true); //location,prefwidth,prefheight,preserver ratio
                ImageCreate.setImage(image);
                } catch (Exception ex) {
                Logger.getLogger(Orders.class.getName()).log(Level.SEVERE, null, ex);
            }
                
             }
            
            
            }
    }
    
      @FXML
    void CreateNewAccount(ActionEvent event) throws SQLException {
        
        if(event.getSource() == SubmitNew){
            
            Orders ord = new Orders();
            
            InsertIntoTable(ImageCreate.getImage().getUrl().toString(),Name.getText(),UserName.getText(),Password.getText(),Salary.getText(),RolesCombo.getSelectionModel().getSelectedItem(),ord.getCurrentTime(),ord.getCreateBy());          

//Clear all Details         
        
        }
        
        
    }

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        
        try {
            InitalizeRoles();
            InitalizeUsers();
            InitalizeViewTable();
        } catch (SQLException ex) {
            Logger.getLogger(Staff.class.getName()).log(Level.SEVERE, null, ex);
        }

    }

    private void InitalizeRoles() throws SQLException {
        ArrayList arr = new ArrayList();
        String query ="CREATE TABLE IF NOT EXISTS `canteen`.`rolecategory` ( `id` INT NULL , `Role` VARCHAR(40) NOT NULL ) ENGINE = InnoDB; ";
        Statement smt = MainApp.Connect.createStatement();
        smt.execute(query);
        String SelQuery = "Select * from `canteen`.`rolecategory`";
        ResultSet rs = smt.executeQuery(SelQuery);
        if (!rs.next()){
        String InsertDef = "INSERT INTO `rolecategory`(`id`, `Role`) VALUES (NULL,?);";
        PreparedStatement ptm = MainApp.Connect.prepareStatement(InsertDef);
        String Arr[] = {"RootAdmin","Clerk","Manager"};
        for (int i =0 ; i < Arr.length ; i ++){
        
            ptm.setString(1, Arr[i]);
            arr.add(Arr[i]);
            ptm.execute();
        
        }
        }
        else {                         
            arr.add(rs.getString("Role"));
            while(rs.next()){
            arr.add(rs.getString("Role"));
            }
        
        }
        
        RolesCombo.getItems().setAll(arr);
    }

    private void InsertIntoTable(String image, String text, String text0, String text1, String text2, String selectedItem,String time,String creator) throws SQLException {
        
        String query = "INSERT INTO `logindets`(`Image`, `Name`, `Username`, `Password`, `Salary`, `CreatedTime`, `CreatedBy`) VALUES (?,?,?,?,?,?,?);";
        String query2 ="INSERT INTO `roles`(`Role`, `Username`) VALUES (?,?)";
        PreparedStatement ptm = MainApp.Connect.prepareStatement(query);
        PreparedStatement ptm2 = MainApp.Connect.prepareStatement(query2);
        ptm.setString(1, image);
        ptm.setString(2, text);
        ptm.setString(3, text0);
        ptm.setString(4, text1);
        ptm.setString(5, text2);
        ptm.setString(6, time);
        ptm.setString(7, creator);
        ptm2.setString(1, selectedItem);
        ptm2.setString(2,text0);
        ptm.execute();
        ptm2.execute();
        
        
    }

    private void InitalizeUsers() throws SQLException {
        ArrayList<String> arr = new ArrayList<>();
        String query = "Select `UserName` from `canteen`.`logindets`";
        Statement smt = MainApp.Connect.createStatement();
        ResultSet rs = smt.executeQuery(query);
        while(rs.next()){
            
           arr.add(rs.getString("UserName"));
        }
        UsersCombo.getItems().setAll(arr);
        UsersCombo.getSelectionModel().selectedItemProperty().addListener((v,oldValue,NewValue)->{
            
            try {        
                SetImage(NewValue);
            } catch (SQLException ex) {
                Logger.getLogger(Staff.class.getName()).log(Level.SEVERE, null, ex);
            }
        
        });
        
        
    }
    
    @FXML
    void RemoveUser(ActionEvent event) throws SQLException{
        
        if(event.getSource() == Remove){
            
            String Username = UsersCombo.getSelectionModel().getSelectedItem();
            //Alert()
            String Query = "DELETE FROM `logindets` WHERE `UserName` = '"+Username+"';";
            Statement smt = MainApp.Connect.createStatement();
            smt.execute(Query);
//            Alert!
        InitalizeUsers();
        
        }
    
    
    }

    private void SetImage(String NewValue) throws SQLException {
        
        String Query = "Select `Image` from `canteen`.`logindets` where `UserName` ='"+NewValue+"';";
        Statement smt = MainApp.Connect.createStatement();
        ResultSet rs = smt.executeQuery(Query);
        while(rs.next()){
            
            RemoveImage.setImage(new Image(rs.getString("Image")));
        
        }
        
    }
        ObservableList<ModelUsers> oblist = FXCollections.observableArrayList();

    private void InitalizeViewTable() throws SQLException {
        Col_Name.setCellValueFactory(new PropertyValueFactory<>("Name"));
        Col_Role.setCellValueFactory(new PropertyValueFactory<>("Role"));
        Col_Salary.setCellValueFactory(new PropertyValueFactory<>("Salary"));
        Col_userName.setCellValueFactory(new PropertyValueFactory<>("UserName"));
        PropertyValueFactory<ModelUsers,ImageView> img = new PropertyValueFactory<>("file");
        Col_DP.setCellValueFactory(img);  
        oblist = returnList();
        viewTable.getItems().setAll(oblist);
    }

    private ObservableList<ModelUsers> returnList() throws SQLException {
        String Query = "SELECT * from `canteen`.`logindets` INNER JOIN `canteen`.`roles` on `logindets`.`Username` = `roles`.`Username` GROUP By `logindets`.`Username` ;";
         ObservableList<ModelUsers> oblists = FXCollections.observableArrayList();
        String name,Username,role;
        String image;
        Float salary;
        ImageView FinalImage;
        Image imgs = new Image(getClass().getResource("Assets/image-placeholder-1200x800.jpg").toString());
        Statement smt = MainApp.Connect.createStatement();
        ResultSet rs =  smt.executeQuery(Query);
        while(rs.next())
        {
            name = rs.getString(2);
            Username = rs.getString(3);

            image = rs.getString(1);
            if(new File(image).exists() && !(new File(image).isDirectory()))
            imgs = new Image(image);
            
            FinalImage = new ImageView(image);
            FinalImage.setFitHeight(100);
            FinalImage.setFitWidth(150); 
            salary = rs.getFloat(5);
            role = rs.getString(8);
            oblists.add(new ModelUsers(FinalImage, name, role,salary.toString(),Username));
             
        } 
      return oblists;       
    }


    
    
    
}
