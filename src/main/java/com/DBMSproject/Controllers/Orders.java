package com.DBMSproject;
import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXComboBox;
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
import java.util.Optional;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import javafx.scene.control.DialogPane;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextArea;
import javafx.scene.control.Tooltip;
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
    private File file,Specialfile;
    private HamburgerSlideCloseTransition transition = Main.gettransition();
    private PreparedStatement pst;
    private FileInputStream Fis; 
    private ObservableList oblist = FXCollections.observableArrayList();
    
    @FXML
    private JFXButton CreateSpecial;
    
    @FXML
    AnchorPane SpecialPane;
    
    
    
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
    private JFXTextField filter;

    

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
    
    /*
    
    Special Orders ! 
    
    
    */
    
    @FXML
    private AnchorPane MainPane;
    
    @FXML
    private AnchorPane MainPane4;

    @FXML
    private Label MainLabelSpecial;

    @FXML
    private JFXComboBox<String> SpecialCombo;

    @FXML
    private JFXButton CreateSpecialDef;

    @FXML
    private JFXButton SpecialLoad;

    @FXML
    private AnchorPane MainPane3;

    @FXML
    private JFXTextField SpecialItem;

    @FXML
    private ImageView SpecialImage;

    @FXML
    private JFXTextField SpecialPrice;

    @FXML
    private JFXTextField SpecialQuantity;

    @FXML
    private JFXButton SpecialAdd;

    @FXML
    private JFXButton SpecialDone;

    @FXML
    private AnchorPane MainPane2;

    @FXML
    private JFXTextField SpecialTableName;

    @FXML
    private Label Choose;

    @FXML
    private JFXButton SpecialCreateTable;

    @FXML
    private Label AlertLabel;

    @FXML
    private JFXButton SpecialBack;
    
    @FXML
    private Label SpecialImageLabel;
    
    @FXML
    private JFXButton BackHome;
    
    //Main Special Dets Object : 
    
    SpecialDets SpObject = new SpecialDets();
    
    
    
    
    
    
    
    
    
    @Override
    public void initialize(URL arg0, ResourceBundle arg1) {
       
        try {
            ConnectDbOrders();
            CreateTable();
            ToolTipIntialize();
            SpObject.ConnectSpecialDB();
            SpObject.setTableNames();
            BringMainPaneFront();
        } catch (SQLException | IOException ex) {
            Logger.getLogger(Orders.class.getName()).log(Level.SEVERE, null, ex);
        } catch (ClassNotFoundException ex) {
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
                        "  `LastModified` varchar(100) DEFAULT NULL, \n"+
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

    private void ToolTipIntialize() {
        Tooltip tipFilter = new Tooltip();
        tipFilter.setText("Filter Based On ItemName or Category!");
        tipFilter.setStyle("-fx-background-radius: 7 7 7 7;-fx-background-color:linear-gradient(#6600cc , #367ff5 );");
        filter.setTooltip(tipFilter);
        
        Tooltip tipItemName  = new Tooltip();
        tipItemName.setText("Required !");
        tipItemName.setStyle("-fx-background-radius: 7 7 7 7;-fx-background-color:linear-gradient(#6600cc , #367ff5 );");
        ItemName.setTooltip(tipItemName);
        
        Tooltip tipItemPrice = new Tooltip();
        tipItemPrice.setText("Required !");
        tipItemPrice.setStyle("-fx-background-radius: 7 7 7 7;-fx-background-color:linear-gradient(#6600cc , #367ff5 );");
        ItemPrice.setTooltip(tipItemPrice);
    }
    
 
   
    @FXML
    void AddRefresh(ActionEvent event) {
        if(event.getSource() == SpecialAdd){
            
            String itemName = SpecialItem.getText();
            String itemPrice = SpecialPrice.getText();
            String itemQantity = SpecialQuantity.getText();
            String imageUri = Specialfile.toURI().toString();
            boolean flag = SpObject.InsertToTable(itemName, itemPrice, itemQantity, imageUri,getCreationtime(),getCreateBy());
            if(flag){
                popupAlert(flag);
                clearDets();
            }
            else{
                popupAlert(flag,new SQLException("There is problem with the Sql!"));
            
            }
        
        }

    }



    @FXML
    void DoneBack(ActionEvent event) {
        if(event.getSource() == SpecialDone){
            
            popupAlert("Warning");
        
        }

    }



    @FXML
    void GoBack(ActionEvent event) {
          if(event.getSource() == SpecialBack || event.getSource() == BackHome){
              
              MainPane.toFront();
              clearDets();
            
        }

    }


    @FXML
    void SpecialImageEdit(MouseEvent event) {
          if(event.getSource() == SpecialImage || event.getSource() == SpecialImageLabel){
          
              FileChooser filechooser = new FileChooser();
              filechooser.getExtensionFilters().addAll(new ExtensionFilter("Image Files","*.png","*.jpeg","*.jpg"));
              Specialfile = filechooser.showOpenDialog(MainApp.PrimaryStage);
               if(Specialfile!=null){
                Image newImage = new Image(Specialfile.toURI().toString(),200,150,true,true); //location,prefwidth,prefheight,preserver ratio
                SpecialImage.setImage(newImage);
                MainPane3.getChildren().remove(SpecialImageLabel);
               }
          
          
          }
        
        
      

    }

    @FXML
    void SpecialTableCreate(ActionEvent event)  {
        if(event.getSource() == SpecialCreateTable){
            try {
                boolean result ;
                result = SpObject.CreateTable(getTableName());
                if(result){
                    popupAlert(true);
                    clearDets();
                    BringMainPane3Front();
                }
                else{
                    Exception ex = new SQLException("Looks like the Table Name Exists");
                    popupAlert(false,ex);
                }
            } catch (SQLException ex) {
                Logger.getLogger(Orders.class.getName()).log(Level.SEVERE, null, ex);
            } catch (InterruptedException ex) {
                Logger.getLogger(Orders.class.getName()).log(Level.SEVERE, null, ex);
            } catch (IOException ex) {
                Logger.getLogger(Orders.class.getName()).log(Level.SEVERE, null, ex);
            }
        
        }
        
        

    }
    
    @FXML 
    void CreateSpecialNow(ActionEvent event){
        if(event.getSource() == CreateSpecialDef){

            BringMainPane2Front();
            clearDets();
        
        }
    }
    
    
    
    
    void BringMainPaneFront(){
        MainPane.toFront();
    }
    
    void BringMainPane2Front(){
        MainPane2.toFront();
    }
    
    void BringMainPane3Front(){
        MainPane3.toFront();
    }
    
    void BringMainPane4Front(){
        MainPane4.toFront();
    }

    String getTableName(){
        return SpecialTableName.getText();
    }

    private void popupAlert(String warning) {
        Alert a = new Alert(Alert.AlertType.CONFIRMATION);
        a.setHeaderText("Warning!");
        a.setContentText("Please Add the Last entered Values before Clicking on done ,"
                + "If you have added the same Please Click on oK");
        a.setHeight(300);
        a.setWidth(300);
        StyleAlert(a);
        Optional<ButtonType> result = a.showAndWait();
        if(result.get() == ButtonType.OK){
        
            BringMainPane4Front();
        
        }
        


    }

    private void clearDets() {
      SpecialTableName.clear();
      SpecialItem.clear();
      SpecialPrice.clear();
      SpecialQuantity.clear();
      SpecialImage.setImage(new Image(getClass().getResource("Assets/image-placeholder-1200x800.jpg").toString()));
      if(MainPane3.getChildren().contains(SpecialImageLabel))
      MainPane3 = MainPane3;
      else
      MainPane3.getChildren().add(SpecialImageLabel);
        }
   
   
   
      
       
       
  
 
}






