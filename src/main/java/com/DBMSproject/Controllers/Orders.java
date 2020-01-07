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
import com.jfoenix.controls.JFXTabPane;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.layout.StackPane;
import javafx.stage.Stage;






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
    AnchorPane UpdateOrders;
    
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
    private TableColumn<ModelTable,String> Col_Category;

    @FXML
    private TableColumn<ModelTable,ModelTable> Col_Update;
   
    
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
    
    @FXML
    private JFXButton RemoveTable;
    
    ModelTable MainTableSelectedData;
    
    
    
    
    
    
    
    
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
    private JFXComboBox<String> CrOrdersCategory;
    
    @FXML
    private JFXComboBox<String> specialCategory;
    
    @FXML
    private JFXTabPane TabPane;
    
    @FXML
    private JFXButton UpdateSelectedMain;
    
    String WorkingTable;

    
    
    
    //Main Special Dets Object : 
    
    SpecialDets SpObject = new SpecialDets();
    PlaceOrderHelper PlObject = new PlaceOrderHelper();
    

    @Override
    public void initialize(URL arg0, ResourceBundle arg1) {
       
        try {
            ConnectDbOrders();
            CreateTable();
            ToolTipIntialize();
            SpObject.ConnectSpecialDB();
            SpObject.setTableNames();
            SetCatValues();
            BringMainPaneFront();
            
            
        } catch (SQLException | IOException ex) {
            Logger.getLogger(Orders.class.getName()).log(Level.SEVERE, null, ex);
        } catch (ClassNotFoundException ex) {
            Logger.getLogger(Orders.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    
    
    @FXML
    void SetCatValues() throws SQLException{
    
    ObservableList<String> oblist = FXCollections.observableArrayList();
    oblist.clear();
    oblist.addAll(PlObject.setCategoryNames());
    CrOrdersCategory.getItems().addAll(oblist);
    UpdateCategoryMainTable.getItems().addAll(oblist);
    
    
    }
    
    
    
    
    @FXML
    void removeFromMainTable(ActionEvent event) throws SQLException, IOException{
        
    if(event.getSource() == RemoveTable){
        
        if(table.getSelectionModel().getSelectedIndex() < 0){
        
            popupAlert(false);
            System.out.println("Error 1");
        }
        else{
        
        ModelTable Delete = table.getItems().get(table.getSelectionModel().getFocusedIndex());
         boolean flag =  PlObject.RemoveTable(Delete);
        if(flag){
            popupAlert(true);
            CreateTable();
            
        }
        else{
            popupAlert(false);
        }
        
        
        }
         
    
    }
    
    
    }
    
    
    
    
    
    
    @FXML
    String getCatValue(){
        
        return  CrOrdersCategory.getValue();
    
    }
    
    

     @FXML
    void CreateDets(ActionEvent event) throws FileNotFoundException, URISyntaxException {
       //Restriction ! Can be made
        if(event.getSource() == Create)
        {
            
            try {
            String Query = "INSERT INTO `canteen`.`menu` (Itemname,price,Image,description,Itemquantity,Category,createdDateTime,creator) VALUES(?,?,?,?,?,?,?,?);";
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
            pst.setString(6, getCatValue());
            pst.setString(7, getCurrentTime());
            pst.setString(8, getCreateBy());
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
                        "  PRIMARY KEY (`Itemname`)\n" +                                
                        ");");
                        
                       
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
    
    String getCurrentTime(){
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
        Col_Category.setCellValueFactory(new PropertyValueFactory<>("Category"));
        Col_Update.setCellFactory(callback->{
        JFXButton Add = new JFXButton("Add");
        TableCell<ModelTable,ModelTable> cell = new TableCell<>(){
            
            @Override
            public void updateItem(ModelTable tableUsed ,boolean empty){
            super.updateItem(tableUsed,empty);
            if(empty){
                setGraphic(null);            }
            else {
            setGraphic(Add);
            }
            }
            };
           
            Add.setOnAction(e->{
            try{
            addToSoldTable(cell.getIndex());
            }catch(Exception ex){
                System.out.println(ex.getMessage());
               
   
        }
            

        });
            return cell;

        });
        oblist = returnList();
        table.setItems(oblist);
        
    }
    
    private void updateQuantiy(ObservableList<ModelTable> oblist1) throws SQLException, IOException  {
      
        PropertyValueFactory<ModelTable,ImageView> img = new PropertyValueFactory<>("file");
        Col_image.setCellValueFactory(img);        
        Col_ItemName.setCellValueFactory(new PropertyValueFactory<>("ItemName"));
        Col_ItemPrice.setCellValueFactory(new PropertyValueFactory<>("Price"));
        Col_ItemDescription.setCellValueFactory(new PropertyValueFactory<>("Description"));
        Col_Quantity.setCellValueFactory(new PropertyValueFactory<>("Quantity"));
        Col_Category.setCellValueFactory(new PropertyValueFactory<>("Category"));
        Col_Update.setCellFactory(callback->{
        JFXButton Add = new JFXButton("Add");
        TableCell<ModelTable,ModelTable> cell = new TableCell<>(){
            
            @Override
            public void updateItem(ModelTable tableUsed ,boolean empty){
            super.updateItem(tableUsed,empty);
            if(empty){
                setGraphic(null);            }
            else {
            setGraphic(Add);
            }
            }
            };
           
            Add.setOnAction(e->{
            try{
            addToSoldTable(cell.getIndex());
            }catch(Exception ex){
                System.out.println(ex.getMessage());
               
   
        }
            

        });
            return cell;

        });
        table.setItems(oblist1);
        updatedatabase(oblist1);
    }
    
    
    
    void updatedatabase(ObservableList<ModelTable> oblist1) throws SQLException{
        
        
           for (ModelTable x : oblist1){
           
               
               String newQuantity = x.Quantity;
               String Name = x.ItemName;
               String Query = "UPDATE `canteen`.`menu` SET `Itemquantity` = '"+newQuantity+"' WHERE `Itemname` = '"+Name+"';";
               Statement smt = MainApp.Connect.createStatement();
               smt.execute(Query);
                         
           }
        
    
    }

    
        @FXML
    private TableView<SoldTable> ordersTable;

    @FXML
    private TableColumn<SoldTable, String> Col_orderID;

    @FXML
   private TableColumn<SoldTable, String> COL_ItemName;

    @FXML
    private TableColumn<SoldTable, String> COL_price;

    @FXML
    private TableColumn<SoldTable, String> COL_Quantity;

    @FXML
    private TableColumn<SoldTable, String> COL_TP;

    @FXML
    private TableColumn<SoldTable,SoldTable> COL_remove;

    @FXML
    private Label TotalSum;

    @FXML
    private JFXButton PlaceOrder;
    
    private String OrderId = String.valueOf(0);
    
    public static ObservableList<SoldTable> SIlist = FXCollections.observableArrayList();
    
   void  addToSoldTable(int index) throws SQLException, IOException{
       ModelTable x = table.getItems().get(index);
       int indexList = -1;
       boolean Exists = false ;
        if(x.Quantity.equals("0")){
          Exception e = new Exception("Quantity Seems to be 0 Please update!");
           popupAlert(false, e);
       }
        else{
       
           for(SoldTable item : SIlist){
           ++indexList;
           System.out.println(indexList);
           if (item.Name.equals( x.ItemName)){ 
               item.Quantity= Integer.toString(Integer.parseInt(item.Quantity) + Integer.parseInt("1"));
               item.TotalPrice = Float.toString(Float.parseFloat(item.TotalPrice) + Float.parseFloat(item.Price));
               SIlist.set(indexList,item);
               populateSoldTable();
               x.Quantity = Integer.toString(Integer.parseInt(x.Quantity) - Integer.parseInt("1"));
               oblist.set(index, x);
               updateQuantiy(oblist);
               Exists = true;
           }       
       }
//       String Name,Price,Quantity,TotalPrice,OrderId
       if(!Exists) {
       String SIname = x.ItemName;
       String SIprice = x.Price;
       String SIquantity = String.valueOf(1);
       String TotalPrice = SIprice;
       OrderId = Integer.toString(Integer.parseInt(OrderId) + 1);
       
       SIlist.addAll(new SoldTable(OrderId, SIname, SIprice, SIquantity, TotalPrice));
       populateSoldTable();
      
      
       x.Quantity = Integer.toString(Integer.parseInt(x.Quantity) - Integer.parseInt("1"));
       oblist.set(index, x);
       updateQuantiy(oblist);
       Exists = true;
       }
       
        }
        float Total = 0;
        for (int i= 0 ; i < ordersTable.getItems().size(); i++){
            Total += Float.parseFloat(ordersTable.getItems().get(i).TotalPrice);
                
                }
        TotalSum.setText(Float.toString(Total));
   }
    
    void populateSoldTable(){
        Col_orderID.setCellValueFactory(new PropertyValueFactory<>("OrderId"));
        COL_ItemName.setCellValueFactory(new PropertyValueFactory<>("Name"));
        COL_Quantity.setCellValueFactory(new PropertyValueFactory<>("Quantity"));
        COL_price.setCellValueFactory(new PropertyValueFactory<>("Price"));
        COL_TP.setCellValueFactory(new PropertyValueFactory<>("TotalPrice"));
        COL_remove.setCellFactory(callback->{
        JFXButton remove = new JFXButton("del");
        TableCell<SoldTable,SoldTable> cell = new TableCell<>(){
            
            @Override
            public void updateItem(SoldTable tableUsed ,boolean empty){
            super.updateItem(tableUsed,empty);
            if(empty){
                setGraphic(null);            }
            else {
            setGraphic(remove);
            }
            }
            };
           
            remove.setOnAction(e->{
            try{
            removeSoldTable(cell.getIndex());
            }catch(Exception ex){
                System.out.println(ex.getMessage());
               
   
        }
            

        });
            return cell;

        });
        ordersTable.setItems(SIlist);
             
    
    }
    
    
    void removeSoldTable(int index) throws SQLException, IOException{

        SoldTable x = ordersTable.getItems().get(index);
        if(Integer.parseInt(x.Quantity) < 2){
        SIlist.remove(index);
        OrderId = Integer.toString(Integer.parseInt(OrderId) -1 );
        for(int i = index; i < SIlist.size() ; i ++){
             SoldTable item = SIlist.get(i);
             item.OrderId = Integer.toString(Integer.parseInt(item.OrderId) - 1) ;   
             SIlist.set(i,item);
             populateSoldTable();

            }
      
        
        for(int i = 0 ; i < table.getItems().size(); i++){
            ModelTable data = table.getItems().get(i);
            if(data.ItemName.equals(x.Name)){
                data.Quantity  =  Integer.toString(Integer.parseInt(data.Quantity) + 1);
                oblist.set(i,data);
                updateQuantiy(oblist);
            }
        }
       
       
        }
        else{
            
       x.Quantity = Integer.toString(Integer.parseInt(x.Quantity) - Integer.parseInt("1"));
       SIlist.set(index, x);
       populateSoldTable();
       for(int i = 0 ; i < table.getItems().size(); i++){
            ModelTable data = table.getItems().get(i);
            if(data.ItemName.equals(x.Name)){
                data.Quantity  =  Integer.toString(Integer.parseInt(data.Quantity) + 1);
                oblist.set(i,data);
                updateQuantiy(oblist);
            }
        }
            
        }
         
            
    }
   
    @FXML 
    JFXButton PlaceOrderbtn;
    
    
    @FXML
    void placeOrderNow(ActionEvent event) throws SQLException, IOException{
    
        ObservableList<ModelSoldSave> orderedList = FXCollections.observableArrayList();
        ObservableList<SoldTable >x = ordersTable.getItems();
//         String OrderId,ItemName,Quantity,TotalPrice,OrderTime,PlacedBy;
        for(SoldTable item : x  ){
            orderedList.add(new ModelSoldSave(item.OrderId, item.Name,item.Quantity ,item.TotalPrice, getCurrentTime(), getCreateBy()));
        }
        PlaceOrderHelper.CreatePlaceDB();
        PlaceOrderHelper.PlaceInsertTOTable(orderedList);
        AppModel.setList(SIlist);
        loadpopup();
        ordersTable.getItems().clear();
        TotalSum.setText("");
    }


    
    @FXML
    void loadpopup() throws IOException{
         FXMLLoader loader = new FXMLLoader(getClass().getResource("Views/ReciptMain.fxml"));
         Stage SecondaryStage = new Stage();
         Scene scene = new Scene(loader.load());
         SecondaryStage.setScene(scene);
         SecondaryStage.initStyle(StageStyle.UTILITY);
         SecondaryStage.show();
    }
    
    
    @FXML
    JFXTextField UpdateMainItemName;
    @FXML
    JFXTextField UpdateMainTableQuantity;
    @FXML
    JFXTextField UpdateMainTablePrice;
    @FXML
    JFXTextArea UpdateMainTableDes;
    @FXML
    ImageView UpdateMainTableImage;
    @FXML
    JFXComboBox<String> UpdateCategoryMainTable;
    @FXML
    JFXButton UpdateMainTableFinal;
    @FXML
    Label UpdateLabel;
    
    @FXML
    JFXButton CancleMainUpdate;
    
    
    String OldMainTableName;
    
    
    @FXML    
    private void UpdateMainTable(ActionEvent event){
    
        if(event.getSource() ==UpdateSelectedMain){
        
        if (table.getSelectionModel().getSelectedIndex() < 0){
        
            Exception E = new Exception("Please Select a Row !");
            popupAlert(false,E);
        
        }
        
        else{
        
        
            MainTableSelectedData = table.getItems().get(table.getSelectionModel().getSelectedIndex());
            OldMainTableName  = MainTableSelectedData.ItemName;
            UpdateMainItemName.setText(MainTableSelectedData.ItemName);
            UpdateMainTablePrice.setText(MainTableSelectedData.Price);
            UpdateMainTableDes.setText(MainTableSelectedData.Description);
            UpdateMainTableQuantity.setText(MainTableSelectedData.Quantity);
            UpdateMainTableImage.setImage(MainTableSelectedData.file.getImage());
            TabPane.getSelectionModel().select(2);

        }

        }
    
    
    }
     @FXML
    void EditUpdatedImage(MouseEvent event) {
        
        if (event.getSource() == UpdateMainTableImage){
        FileChooser fileChooser = new FileChooser();
        fileChooser.getExtensionFilters().addAll(new  ExtensionFilter("Image Files","*.png","*.jpeg","*.jpg"));
        file = fileChooser.showOpenDialog(MainApp.PrimaryStage);
        System.out.println(file);
             if(file!=null){
                 Image image = new Image(file.toURI().toString(),281,208,true,true); //location,prefwidth,prefheight,preserver ratio
                 UpdateMainTableImage.setImage(image);
                 UpdateOrders.getChildren().remove(UpdateLabel);
                
             }
        
        }

    }
        
    @FXML
    void CancleUpdateMainTable(ActionEvent event){
    
        if(event.getSource() == CancleMainUpdate){
        
        TabPane.getSelectionModel().select(0);
        ClearDetsUpdate();
            
        }
    
    
    }
    
  void ClearDetsUpdate(){
      
      UpdateMainItemName.clear();
      UpdateMainTableDes.clear();
      UpdateMainTablePrice.clear();
      UpdateMainTableQuantity.clear();
      UpdateMainTableImage.setImage(new Image(getClass().getResource("Assets/image-placeholder-1200x800.jpg").toString()));
       if(UpdateOrders.getChildren().contains(UpdateLabel)) {
        UpdateOrders = UpdateOrders;
       }    
       else {
           UpdateOrders.getChildren().add(UpdateLabel);
       }
            
            }
    
    
    @FXML
    void FinalUpdateMainTable(ActionEvent event) throws SQLException{
    if(event.getSource() == UpdateMainTableFinal){
        try{
     String Image,Name,Price,Description,Quantity,Category,Modifier,LastModified;
     Image = UpdateMainTableImage.getImage().getUrl().toString();
     Name = UpdateMainItemName.getText();
     Price = UpdateMainTablePrice.getText();
     Description = UpdateMainTableDes.getText();
     Quantity = UpdateMainTableQuantity.getText();
     Modifier  = getCreateBy();
     LastModified = getCurrentTime();
     Category = UpdateCategoryMainTable.getValue();
     PlObject.UpdateMainTable(Image,Name,Price,Description,Quantity,Category,Modifier,LastModified,OldMainTableName);
     popupAlert(true);
     CreateTable();
     TabPane.getSelectionModel().select(0);
    ClearDetsUpdate();

        }catch(Exception e){
        
            popupAlert(false,e);
        }
     
    
    
    
    }
    
    
    }
    

    private ObservableList<ModelTable> returnList() throws SQLException, IOException {
        ObservableList<ModelTable> oblist = FXCollections.observableArrayList();
        String name,description,price,quantity;
        String image;
        ImageView FinalImage;
        String Category;
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
            Category = rs.getString("Category");
            oblist.add(new ModelTable(name, description, price, FinalImage,quantity,Category)); } 
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
            String creationTime = getCurrentTime();
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
              
        
        
        
    }
    
    private void getUpdateItems(int x) throws SQLException{
    
        System.out.println("Hello");
            
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
    private ImageView SpecialUpdateImage;

    @FXML
    private Label SpecialImageLabelUpdate;

    @FXML
    private JFXComboBox<String> specialCategoryUpdate;
      
    
    private String OldItemName;
    
    private String Modifier;
    
    private File SpecialUpdatedImage;
    
    
      

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
             boolean flag = SpObject.UpdateSpecial(OldItemName,Modifier,getCurrentTime(),itemName,itemPrice,itemQantity,imageUri,category);
                if(flag){
                
                     CreateSpecialTableRefresh();
                     popupAlert(true);
                     BringMainPane3Front();

                    
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
    
//    Printing the Special Table : 
    @FXML
    JFXButton PrintSpecialTable;
    
    @FXML
    void SpecialTablePrint(ActionEvent event) throws SQLException, IOException{
        
           SpObject.SaveTable(); 
           AppModel2.setList(SpecialTable.getItems());
           PopupReciptSpecial();       
    
    }
@FXML
    void PopupReciptSpecial() throws IOException{
         FXMLLoader loader = new FXMLLoader(getClass().getResource("Views/ReciptSpecial.fxml"));
         Stage SecondaryStage = new Stage();
         Scene scene = new Scene(loader.load());
         SecondaryStage.setScene(scene);
         SecondaryStage.initStyle(StageStyle.UTILITY);
         SecondaryStage.show();
    }

 
    
    
    
    
    
  
 
}


//you can do so like add image label to edit image ! 




