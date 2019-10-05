package com.DBMSproject;
import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXDrawer;
import com.jfoenix.controls.JFXTextArea;
import com.jfoenix.controls.JFXTextField;
import com.jfoenix.controls.JFXTreeTableColumn;
import com.jfoenix.controls.JFXTreeTableView;
import com.jfoenix.controls.RecursiveTreeItem;
import com.jfoenix.controls.datamodels.treetable.RecursiveTreeObject;
import com.jfoenix.transitions.hamburger.HamburgerSlideCloseTransition;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.net.URISyntaxException;
import java.net.URL;
import java.sql.Blob;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.beans.property.SimpleObjectProperty;
import javafx.beans.value.ObservableObjectValue;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.embed.swing.SwingFXUtils;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.TreeItem;
import javafx.scene.control.TreeTableColumn;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.stage.FileChooser;
import javafx.stage.FileChooser.ExtensionFilter;
import javafx.util.Callback;
import javax.imageio.ImageIO;






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
    private JFXTreeTableView<ModelTable> table;

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
    private JFXButton Create;
    
    @Override
    public void initialize(URL arg0, ResourceBundle arg1) {
       
            ConnectDbOrders();
           
     
            
    }
    

     @FXML
    void CreateDets(ActionEvent event) throws FileNotFoundException, URISyntaxException {
        if(event.getSource() == Create)
        {
            
            try {
            String Query = "INSERT INTO `canteen`.`menu` (Itemname,price,Image,description) VALUES(?,?,?,?);";
            pst = MainApp.Connect.prepareStatement(Query);
            pst.setString(1,getItemName());
            pst.setString(2, getPrice());
            try{
            pst.setBinaryStream(3,(InputStream)Fis,(int)file.length());
            }catch(Exception e){
                pst.setBinaryStream(3, null,0);
            }
            pst.setString(4, getDescription());
            pst.execute();
            pst.close();
                System.out.println("Done!");
        } catch (SQLException ex) {
                System.out.println("Error!");
        }
        
        }
    }
    
    
    
    
      @FXML
    void EditImage(MouseEvent event) {
        
        if (event.getSource() == MainImage){
        FileChooser fileChooser = new FileChooser();
        fileChooser.getExtensionFilters().addAll(new  ExtensionFilter("Image Files","*.png","*.jpeg","*.jpg"));
        file = fileChooser.showOpenDialog(MainApp.PrimaryStage);
            System.out.println(file);
             if(file!=null){
            try {
                Image image = new Image(file.toURI().toString(),281,208,true,true); //location,prefwidth,prefheight,preserver ratio
                MainImage.setImage(image);
                Fis = new FileInputStream(file);
            } catch (FileNotFoundException ex) {
                Logger.getLogger(Orders.class.getName()).log(Level.SEVERE, null, ex);
            }
                
             }
        
        }

    }
    
    
       @FXML
    void refresh(ActionEvent event) {
        if(event.getSource() == refresh){
            System.out.println("Clicked !");
        }

    }


    private void ConnectDbOrders() {
        boolean con = MainApp.Connect();
        if(con){
            try {
                Statement smt = MainApp.Connect.createStatement();
                smt.execute("CREATE TABLE IF NOT EXISTS `canteen`.`menu` (\n" +
                        "  `Itemname` VARCHAR(100) NOT NULL,\n" +
                        "  `price` FLOAT NULL,\n" +
                        "  `Image` BLOB NULL,\n" +
                        "  `description` VARCHAR(200) NULL DEFAULT 'None',\n" +
                        "  PRIMARY KEY (`Itemname`));");

                        } catch (SQLException ex) {
                Logger.getLogger(Orders.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
    }
 
    
    String getItemName(){
       return ItemName.getText();
    }
    
    String getDescription(){
        return description.getText();
    }
    
    String getPrice(){
        return ItemPrice.getText();
    }    

    private void CreateTable() throws SQLException, IOException {
       try{
        JFXTreeTableColumn<ModelTable,ImageView> Images = new JFXTreeTableColumn<>("Image");
        Images.setPrefWidth(75);
        //To show what kind of data is to be shown !
        Images.setCellValueFactory(new Callback<TreeTableColumn.CellDataFeatures<ModelTable, ImageView>, ObservableValue<ImageView>>() {
            @Override
            public ObservableValue<ImageView> call(TreeTableColumn.CellDataFeatures<ModelTable, ImageView> param) {
                //Treeitem , user object and then the parameter! 
                return (ObservableObjectValue<ImageView>)new ImageView(param.getValue().getValue().file);
            }
        }
        
                
        );
        
        
        JFXTreeTableColumn<ModelTable,String> ItemNames = new JFXTreeTableColumn<>("Item Name");
        ItemNames.setCellValueFactory(new Callback<TreeTableColumn.CellDataFeatures<ModelTable, String>, ObservableValue<String>>() {
            @Override
            public ObservableValue<String> call(TreeTableColumn.CellDataFeatures<ModelTable, String> param) {
                return param.getValue().getValue().ItemName;
            }
        });
        
        JFXTreeTableColumn<ModelTable,String> ItemPrice = new JFXTreeTableColumn<>("Item Price");
        ItemPrice.setCellValueFactory(new Callback<TreeTableColumn.CellDataFeatures<ModelTable, String>, ObservableValue<String>>() {
            @Override
            public ObservableValue<String> call(TreeTableColumn.CellDataFeatures<ModelTable, String> param) {
                return param.getValue().getValue().Price;
            }
        });
        JFXTreeTableColumn<ModelTable,String> ItemDescription = new JFXTreeTableColumn<>("Description");
        ItemDescription.setCellValueFactory(new Callback<TreeTableColumn.CellDataFeatures<ModelTable, String>, ObservableValue<String>>() {
            @Override
            public ObservableValue<String> call(TreeTableColumn.CellDataFeatures<ModelTable, String> param) {
                return param.getValue().getValue().Description;
            }
        });
        
        
        ObservableList<ModelTable> oblist = FXCollections.observableArrayList();
        oblist = returnList();
        
        final TreeItem<ModelTable> root = new RecursiveTreeItem<>(oblist, RecursiveTreeObject::getChildren);
        table.getColumns().setAll(Images,ItemNames,ItemPrice,ItemDescription);
        table.setRoot(root);
        table.setShowRoot(false);
        
        
       }
       catch(Exception ex){
           System.out.println("Errors ! ");
       }
    }

    private ObservableList<ModelTable> returnList() throws SQLException, IOException {
        ObservableList<ModelTable> oblist = FXCollections.observableArrayList();
        String name,description,price;
        Blob image;
        ImageView FinalImage;
        String Query = "SELECT * FROM `canteen`.`menu`;";
        Statement smt = MainApp.Connect.createStatement();
        ResultSet rs =  smt.executeQuery(Query);
        while(rs.next())
        {
            name = rs.getString("Itemname");
            description = rs.getString("description");
            price = rs.getString("price");
            image = rs.getBlob("Image");
            Image imgs;
            if (image == null){
               imgs = new Image(getClass().getResourceAsStream("Assets/image-placeholder-1200x800.jpg"));
            }
            else{
               InputStream Is = image.getBinaryStream(0,image.length());
                BufferedImage img = ImageIO.read(Is);
                 imgs = SwingFXUtils.toFXImage(img,null);  
            }
            oblist.add(new ModelTable(name, description, price, imgs));
            
            
        }
      return oblist;          
    }
    
    
   


   
}





