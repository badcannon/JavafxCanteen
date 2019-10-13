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
import java.util.ArrayList;
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
import javafx.scene.control.TableCell;
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
    private JFXComboBox<String> SpecialComboTable;

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
    
    
    @FXML
    private JFXComboBox<String> specialCategory;
    
    
//    Special TableView : 
    
    @FXML
    private TableView<ModelSpecial> SpecialTable;
    
    @FXML
    private TableColumn<ModelSpecial, ImageView> col_SpecialImage;

    @FXML
    private TableColumn<ModelSpecial, String> col_SpecialName;

    @FXML
    private TableColumn<ModelSpecial, String> col_SpecialPrice;

    @FXML
    private TableColumn<ModelSpecial, String> col_ItemQuantity;

    @FXML
    private TableColumn<ModelSpecial, String> col_Category;

    @FXML
    private TableColumn<ModelSpecial, String> col_totalPrice;

    @FXML
    private TableColumn<ModelSpecial, ModelSpecial> col_Update ;
    
    
    @FXML
    private JFXButton removeSpecial; 
 
    
    
    String WorkingTable;

    
    
    
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
                        "  `Category` varchar(50) DEFAULT NULL,\n"+
                        "  `createdDateTime` datetime DEFAULT NULL,\n" +
                        "  `creator` varchar(100) DEFAULT NULL,\n"+ 
                        "  `Modifier` varchar(100) DEFAULT NULL," +
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
        Image imgs = new Image(getClass().getResource("Assets/image-placeholder-1200x800.jpg").toString());
        String Query = "SELECT * FROM `canteen`.`menu`;";
        Statement smt = MainApp.Connect.createStatement();
        ResultSet rs =  smt.executeQuery(Query);
        while(rs.next())
        {
            name = rs.getString("Itemname");
            description = rs.getString("description");
            quantity = rs.getString("Itemquantity");
            price = rs.getString("price");
            image = rs.getString("Image");
            if(new File(image).exists() && !(new File(image).isDirectory()))
            imgs = new Image(image);
            
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
        if(CrOrders.getChildren().contains(LabelRemove))
        {
            CrOrders = CrOrders;
        }
        else{
        CrOrders.getChildren().add(LabelRemove);
                }
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
    void AddRefresh(ActionEvent event) throws SQLException {
        if(event.getSource() == SpecialAdd){
            
            String itemName = SpecialItem.getText();
            String itemPrice = SpecialPrice.getText();
            String itemQantity = SpecialQuantity.getText();
            String imageUri = Specialfile.toURI().toString();
            String category = specialCategory.getValue();
            String creationTime = getCreationtime();
            String creator = getCreateBy();
            boolean flag = SpObject.InsertToTable(itemName, itemPrice, itemQantity, imageUri,category,creationTime,creator);
            if(flag){
                popupAlert(flag);
                clearDets();
                CreateSpecialTableRefresh();
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
    void GoBack(ActionEvent event) throws SQLException {
          if(event.getSource() == SpecialBack || event.getSource() == BackHome){
              
              BringMainPaneFront();
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
                    WorkingTable = getTableName().replaceAll(" ", "").toLowerCase();
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
    
    
    
    
    void BringMainPaneFront() throws SQLException{
        SpecialComboTable.getItems().clear();
        InitializeSpecialCombo();
        MainPane.toFront();
    }
    
    void BringMainPane2Front(){
        MainPane2.toFront();
    }
    
    void BringMainPane3Front() throws SQLException{
        specialCategory.getItems().clear();
        initSpecialCategory();
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

    private void initSpecialCategory() throws SQLException {
        ObservableList<String> oblist = FXCollections.observableArrayList();
        oblist.clear();
        oblist.addAll(SpObject.CreateAndAddSpecialCats());
        specialCategory.getItems().addAll(oblist);
    }

    private void CreateSpecialTableRefresh() throws SQLException {
        
//    String ItemName,Description, Price,Quantity,category;
//    ImageView file;
//    JFXButton Update;


        PropertyValueFactory<ModelSpecial,ImageView> img = new PropertyValueFactory<>("file");
        PropertyValueFactory<ModelSpecial,JFXButton> btn = new PropertyValueFactory<>("Update");
        col_SpecialImage.setCellValueFactory(img);
        col_Category.setCellValueFactory(new PropertyValueFactory<>("category"));
        col_SpecialName.setCellValueFactory(new PropertyValueFactory<>("ItemName"));
        col_SpecialPrice.setCellValueFactory(new PropertyValueFactory<>("Price"));
        col_ItemQuantity.setCellValueFactory(new PropertyValueFactory<>("Quantity"));
        col_totalPrice.setCellValueFactory(new PropertyValueFactory<>("totalPrice"));
        
        //Creating a Cell factory to add the button :
        col_Update.setCellFactory(col ->{
        JFXButton update = new JFXButton("Update");
            
        TableCell<ModelSpecial,ModelSpecial> cell = new TableCell<>(){
        @Override
        public void updateItem(ModelSpecial table,boolean empty){
        super.updateItem(table, empty);
        if(empty){
            setGraphic(null);
        }
        else {
            setGraphic(update);
        }
        }
        
        
        };
        update.setOnAction(e -> {
            try {
                getUpdateItems(cell.getIndex());
            } catch (SQLException ex) {
                Logger.getLogger(Orders.class.getName()).log(Level.SEVERE, null, ex);
            }
        });
           
        return cell;
        
        });
        
        ObservableList<ModelSpecial> oblist = FXCollections.observableArrayList();
        oblist.addAll( SpObject.PrePareList());
        SpecialTable.setItems(oblist);
        SpecialTable.setEditable(true);
              
        
        
        
    }
    
    private void getUpdateItems(int x) throws SQLException{
    
        System.out.println("Hello");
        Alert a = new Alert(Alert.AlertType.WARNING);
        a.setHeaderText("Warning feature in Development!");
        a.showAndWait();
        
        String Name = SpecialTable.getItems().get(x).ItemName;
        String Quantity = SpecialTable.getItems().get(x).Price;
        String Price = SpecialTable.getItems().get(x).Price;
        ImageView img = SpecialTable.getItems().get(x).file;
        Image image = img.getImage();

        BringUpdatePaneFront(Name,Quantity,Price,image);
    }
    
    
    @FXML 
    void LoadTable(ActionEvent event){
        if(event.getSource() == SpecialLoad){
        
       String Table =  SpecialComboTable.getValue();
       try{
       SpObject.setWorkingTable(Table);
       CreateSpecialTableRefresh();
       initSpecialCategory();
       
       BringMainPane3Front();
       }catch(Exception e){
       
           popupAlert(false, e);
       
       }
        }
    
    
    
    }

    private void InitializeSpecialCombo() throws SQLException {
        
        ArrayList<String> ArList = new ArrayList<>();
        ArList.addAll(SpObject.returnTables());
        if(ArList.isEmpty()){
         SpecialComboTable.setPlaceholder(new Label("Sorry No Menu Exists"));
                 }
        else {
        
        SpecialComboTable.getItems().addAll(ArList);
        }
        
    }
    
    
    
        
    @FXML
    private void removeFromSpecialTable(ActionEvent event) throws SQLException{
        
        if(event.getSource() == removeSpecial){
        try{
         if(SpecialTable.getSelectionModel().getSelectedIndex() < 0)
             popupAlert(false);
         else{  
         ModelSpecial Mustbermoved = SpecialTable.getItems().get(SpecialTable.getSelectionModel().getSelectedIndex());
         
         SpObject.removeSelected(Mustbermoved.ItemName);
             popupAlert(true);
         CreateSpecialTableRefresh();
         }
        }catch(Exception e){
            popupAlert(false,e);
        }
         
        }
        
        
        
        
        
    
    
    
    }

//    Button Part
   @FXML
    private AnchorPane UpdatePane;

    @FXML
    private JFXTextField SpecialUpdateItem;

    @FXML
    private JFXTextField SpecialPriceUpdate;

    @FXML
    private JFXTextField SpecialQuantityUpdate;

    @FXML
    private JFXButton SpecialAddUpdate;

    @FXML
    private JFXButton SpecialUpdateBack;

    @FXML
    private ImageView SpecialUpdateImage;

    @FXML
    private Label SpecialImageLabelUpdate;

    @FXML
    private JFXComboBox<String> specialCategoryUpdate;
  
    private String OldItemName;
    
    private String Modifier;
    
    private File SpecialUpdatedImage;
 
   
      
   @FXML
    void UpdateBack(ActionEvent event) throws SQLException {
        if(event.getSource() == SpecialUpdateBack){
        
         BringMainPane3Front();
        
        }

    }

    @FXML
    void UpdateSpecial(ActionEvent event) throws SQLException {
        if(event.getSource() == SpecialAddUpdate){
        
            Alert a = new Alert(Alert.AlertType.CONFIRMATION);
            a.setHeaderText("Overwrite Data ?");
            a.setContentText("You are About to Over write the Data Are you sure ?");
            StyleAlert(a);
            Optional<ButtonType> result = a.showAndWait();
            if(result.get() == ButtonType.OK){
            String itemName = SpecialUpdateItem.getText();
            String itemPrice = SpecialPriceUpdate.getText();
            String itemQantity = SpecialQuantityUpdate.getText();
            String imageUri =  SpecialUpdateImage.getImage().getUrl().toString();
            String category = specialCategoryUpdate.getValue();
            setModifierName();
             boolean flag = SpObject.UpdateSpecial(OldItemName,Modifier,getCreationtime(),itemName,itemPrice,itemQantity,imageUri,category);
                if(flag){
                
                     CreateSpecialTableRefresh();

                    
                }
                else {
                    popupAlert(false);
                }
            
            }
        
        }

    }  
    @FXML
    void SpecialImageUpdate(MouseEvent event) {
        if(event.getSource() == SpecialUpdateImage || event.getSource() == SpecialImageLabelUpdate){
            
            
            FileChooser filechooser = new FileChooser();
            filechooser.setSelectedExtensionFilter(new ExtensionFilter("Images", "*.jpeg,*.jpg,*.png"));
            SpecialUpdatedImage = filechooser.showOpenDialog(MainApp.PrimaryStage);
            if(SpecialUpdatedImage!=null){
                
                Image image = new Image(SpecialUpdatedImage.toURI().toString(),200,150,true,true); //location,prefwidth,prefheight,preserver ratio
                SpecialUpdateImage.setImage(image);
                
            }
            
        
        
        }
            
    }

    void BringUpdatePaneFront(String Name,String Price, String Quantity,Image img) throws SQLException {
        
        SpecialUpdateItem.setText(Name);
        setOldUpdateName(Name);
        SpecialPriceUpdate.setText(Price);
        SpecialQuantityUpdate.setText(Quantity);
        SpecialUpdateImage.setImage(img);
        ObservableList<String> oblist = FXCollections.observableArrayList();
        oblist.clear();
        oblist.addAll(SpObject.CreateAndAddSpecialCats());
        specialCategoryUpdate.getItems().addAll(oblist);
        UpdatePane.toFront();

    }
    
    
    void setOldUpdateName(String Name){
    
     OldItemName = Name;
    
    }
       
    void setModifierName()
    {
    
        Modifier = LoginController.getCurrentUser();
    
    }
 
}


//you can do so like add image label to edit image ! 




