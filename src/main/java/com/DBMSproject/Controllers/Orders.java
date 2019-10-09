package com.DBMSproject;
import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXDrawer;
import com.jfoenix.controls.JFXTextArea;
import com.jfoenix.controls.JFXTextField;
import com.jfoenix.transitions.hamburger.HamburgerSlideCloseTransition;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.PrintWriter;
import java.io.StringWriter;
import java.net.URISyntaxException;
import java.net.URL;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.DialogPane;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextArea;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.stage.FileChooser;
import javafx.stage.FileChooser.ExtensionFilter;
import javafx.stage.StageStyle;






public class Orders implements Initializable {

    private MainWindowController Main = new MainWindowController();
    private JFXDrawer drawer = Main.getdrawer() ;
    private File file;
    private HamburgerSlideCloseTransition transition = Main.gettransition();
    private PreparedStatement pst;
    private FileInputStream Fis; 
    private ObservableList oblist = FXCollections.observableArrayList();
    
    @FXML
    private AnchorPane OrderCreate;
    
    @FXML
    private TableView<ModelTable> table;

    @FXML
    private TableColumn<ModelTable, ImageView> Col_image;

    @FXML
    private TableColumn<ModelTable, String> Col_ItemName;

    @FXML
    private TableColumn<ModelTable, String> Col_ItemPrice;

    @FXML
    private TableColumn<ModelTable, String> Col_ItemDescription;

    @FXML
    private TableColumn<ModelTable, String> Col_Quantity;

    @FXML
    private TableColumn<ModelTable, JFXButton> Col_Add;


    @FXML
    private JFXButton refresh;
    
    @FXML
    private AnchorPane PLOrders;

    @FXML
    private AnchorPane CrOrders;

    @FXML
    private AnchorPane UpOrders;
    
    @FXML
    private ImageView MainImage;

    @FXML
    private JFXButton Edit;

    @FXML
    private JFXTextField ItemName;

    @FXML
    private JFXTextArea description;

    @FXML
    private JFXTextField ItemPrice;
    
    @FXML
    private JFXTextField Quantity;
    
    @FXML
    private JFXButton Create;
    
    @FXML
    private Label LabelRemove;
    
    @Override
    public void initialize(URL arg0, ResourceBundle arg1) {
       
        try {
            ConnectDbOrders();
            CreateTable();
        } catch (SQLException | IOException ex) {
            Logger.getLogger(Orders.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    

     @FXML
    void CreateDets(ActionEvent event) throws FileNotFoundException, URISyntaxException {
       //Restriction ! Can be made
        if(event.getSource() == Create)
        {
            
            try {
            String Query = "INSERT INTO `canteen`.`menu` (Itemname,price,Image,description,Itemquantity,createdDateTime,creator) VALUES(?,?,?,?,?,?,?);";
            pst = MainApp.Connect.prepareStatement(Query);
            pst.setString(1,getItemName());
            pst.setString(2, getPrice());
            try{
            pst.setString(3,file.toURI().toString());
            }catch(Exception e){
             pst.setString(3,getClass().getResource("Assets/image-placeholder-1200x800.jpg").toString());
            }
            pst.setString(4, getDescription());
            pst.setString(5, getQuantity());
            pst.setString(6, getCreationtime());
            pst.setString(7, getCreateBy());
            pst.execute();
            pst.close();
            popupAlert(true);
            clearCreate();
        } catch (SQLException ex) {
                popupAlert(false,ex);
        }
        
        }
    }
    
    
    
    
      @FXML
    void EditImage(MouseEvent event) {
        
        if (event.getSource() == MainImage || event.getSource() == LabelRemove){
        FileChooser fileChooser = new FileChooser();
        fileChooser.getExtensionFilters().addAll(new  ExtensionFilter("Image Files","*.png","*.jpeg","*.jpg"));
        file = fileChooser.showOpenDialog(MainApp.PrimaryStage);
            System.out.println(file);
             if(file!=null){
            try {
                Image image = new Image(file.toURI().toString(),281,208,true,true); //location,prefwidth,prefheight,preserver ratio
                MainImage.setImage(image);
                CrOrders.getChildren().remove(LabelRemove);
                Fis = new FileInputStream(file);
            } catch (FileNotFoundException ex) {
                Logger.getLogger(Orders.class.getName()).log(Level.SEVERE, null, ex);
            }
                
             }
        
        }

    }
    
    
       @FXML
    void refresh(ActionEvent event) throws SQLException, IOException {
        if(event.getSource() == refresh){
            oblist.clear();
            CreateTable();
        }

    }


    private void ConnectDbOrders() {
        boolean con = MainApp.Connect();
        if(con){
            try {
                Statement smt = MainApp.Connect.createStatement();
                        smt.execute("CREATE TABLE IF NOT EXISTS`canteen`.`menu` (\n" +
                        "  `Itemname` varchar(100) NOT NULL,\n" +
                        "  `price` float DEFAULT NULL,\n" +
                        "  `Image` varchar(400) DEFAULT NULL,\n" +
                        "  `description` varchar(200) DEFAULT 'None',\n" +
                        "  `Itemquantity` int(11) DEFAULT NULL,\n" +
                        "  `createdDateTime` datetime DEFAULT NULL,\n" +
                        "  `creator` varchar(100) DEFAULT NULL,\n"+ 
                        "  PRIMARY KEY (`Itemname`)\n" +
                                
                    ") ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;");

                        } catch (SQLException ex) {
                Logger.getLogger(Orders.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
    }
 
    
    String getItemName(){
       return ItemName.getText();
    }
    
    String getDescription(){
        if("".equals(description.getText()) || " ".equals(description.getText())){
            return "Not Entered!";
        }
        return description.getText();
    }
    
    String getPrice(){
        return ItemPrice.getText();
    }    
    
    String getQuantity(){
        if("".equals(Quantity.getText()) || " ".equals(Quantity.getText())){
            return null;   
        }
        return Quantity.getText();
    }
    String getCreationtime(){
        DateFormat df = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss");
        return df.format(new Date());
    }
    
    String getCreateBy(){
        return LoginController.getCurrentUser();
    }

    private void CreateTable() throws SQLException, IOException  {
      
        PropertyValueFactory<ModelTable,ImageView> img = new PropertyValueFactory<>("file");
        Col_image.setCellValueFactory(img);        
        Col_ItemName.setCellValueFactory(new PropertyValueFactory<>("ItemName"));
        Col_ItemPrice.setCellValueFactory(new PropertyValueFactory<>("Price"));
        Col_ItemDescription.setCellValueFactory(new PropertyValueFactory<>("Description"));
        Col_Quantity.setCellValueFactory(new PropertyValueFactory<>("Quantity"));
        oblist = returnList();
        table.setItems(oblist);
        
        
     
    }

    private ObservableList<ModelTable> returnList() throws SQLException, IOException {
        ObservableList<ModelTable> oblist = FXCollections.observableArrayList();
        String name,description,price,quantity;
        String image;
        ImageView FinalImage;
        String Query = "SELECT * FROM `canteen`.`menu`;";
        Statement smt = MainApp.Connect.createStatement();
        ResultSet rs =  smt.executeQuery(Query);
        while(rs.next())
        {
            Image imgs;
            name = rs.getString("Itemname");
            description = rs.getString("description");
            quantity = rs.getString("Itemquantity");
            price = rs.getString("price");
            image = rs.getString("Image");
            imgs = new Image(image);
            imgs = new Image(getClass().getResource("Assets/image-placeholder-1200x800.jpg").toString());
            FinalImage = new ImageView(image);
            FinalImage.setFitHeight(100);
            FinalImage.setFitWidth(150);              
            oblist.add(new ModelTable(name, description, price, FinalImage,quantity)); } 
      return oblist;          
    }

 private void popupAlert(boolean b,Exception e) {
        
        if(b){
            Alert a = new Alert(Alert.AlertType.INFORMATION);
            StyleAlert(a);
            a.setTitle("Successful!");
            a.setHeaderText("Successful !");
            a.setContentText("The Opertion was Successful !");
            a.showAndWait();
        }
        else{
            Alert a = new Alert(Alert.AlertType.ERROR);
            StyleAlert(a);
            a.setTitle("Something Went Wrong!");
            a.setHeaderText("Look Like something went wrong!");
            a.setContentText("Please Look into the error details below ! ");
            StringWriter sw = new StringWriter();
            PrintWriter pw = new PrintWriter(sw);
            e.printStackTrace(pw);
            TextArea area  = new TextArea(sw.toString());
            a.getDialogPane().setExpandableContent(area);
            a.showAndWait();
            
          
        }
        
    }

     private void popupAlert(boolean b) {
        
        if(b){
            Alert a = new Alert(Alert.AlertType.INFORMATION );
            StyleAlert(a);
            a.setTitle("Successful!");
            a.setHeaderText("Successful !");
            a.setContentText("The Opertion was Successful !");
            a.showAndWait();
            
        }
        else{
           Alert a = new Alert(Alert.AlertType.ERROR);
           StyleAlert(a);
           a.setTitle("Something Went Wrong !");
            a.setHeaderText("Looks Like Something Went Wrong !");
            a.setContentText("Error Details are not Available at the moment , Please contact "
                    + "the Dev ! ");
            a.showAndWait();   
        }
        
    }

    public void StyleAlert(Alert a) {
        DialogPane pane = a.getDialogPane();
        a.initStyle(StageStyle.UNDECORATED);
//        pane.getStylesheets().add(getClass().getResource("Styles/DialogPane.css").toExternalForm());
        
    }

    private void clearCreate() {
        ItemName.clear();
        Quantity.clear();
        description.clear();
        ItemPrice.clear();
        MainImage.setImage(new Image(getClass().getResource("Assets/image-placeholder-1200x800.jpg").toString()));
        CrOrders.getChildren().add(LabelRemove);
    }

  
 
}






