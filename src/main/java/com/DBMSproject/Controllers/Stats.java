package com.DBMSproject;

import com.jfoenix.controls.JFXComboBox;
import java.net.URL;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.Observer;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.chart.BarChart;
import javafx.scene.chart.CategoryAxis;
import javafx.scene.chart.LineChart;
import javafx.scene.chart.NumberAxis;
import javafx.scene.chart.PieChart;
import javafx.scene.chart.XYChart;
import javafx.scene.control.Alert;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;

public class Stats implements Initializable{
    
    
    @FXML
    private BarChart<?, ?> Popularity;

    @FXML
    private CategoryAxis ItemX;

    @FXML
    private NumberAxis QuantityY;
    
    @FXML
    private PieChart PieCharts;

    @FXML
    private LineChart<String, Float> Sales;

    @FXML
    private CategoryAxis daysX;

    @FXML
    private NumberAxis SalesY;

    @FXML
    private JFXComboBox<String> ItemsMin;
    
    

    @Override
    public void initialize(URL url, ResourceBundle rb) {
            
        try { 
            CheckForExistance();
            PopulartityChart();
            InitalizeItemNameComboBox();
            InitalizeMenuNameComboBox();
            PieChartVals();
            MenuPopularityChart();
            PieChartSpecialVals();
            CreateListAndPopTable();
            CreateListAndPopMenuTable();
        } catch (SQLException ex) {
            Logger.getLogger(Stats.class.getName()).log(Level.SEVERE, null, ex);
        }
        

    }
   HashMap<String,Integer> hm = new HashMap<>();
   ArrayList<String> a  = new ArrayList();
   String LineItem;


    private void PopulartityChart() throws SQLException {
        XYChart.Series set1 = new XYChart.Series<>();
          createMap();
          for(Map.Entry m : hm.entrySet()){
         set1.getData().add(new XYChart.Data(m.getKey(),m.getValue()));

          }
        Popularity.getData().setAll(set1);

    }

    private void createMap() throws SQLException {

        String query = "SELECT DISTINCT `ItemName` FROM `MainOrderList`";
        Statement smt = MainApp.Connect.createStatement();
        ResultSet rs = smt.executeQuery(query);
        while(rs.next()){
            a.add(rs.getString("ItemName"));
        }
        for(String x : a){
            int y = Count(x);
            hm.put(x, y);            
        }
        
        

    }

    private int Count(String x) throws SQLException {
        String query = "Select sum(Quantity) from `MainOrderList` where `ItemName` =  '"+x+"'" ;
        Statement stm = MainApp.Connect.createStatement();
        ResultSet set = stm.executeQuery(query);
        int Value = 0;
        while(set.next()){
           Value = set.getInt("sum(Quantity)");
        }
        return Value;
    }   

    private void InitalizeItemNameComboBox() {
        
        ItemsMin.getItems().setAll(a);
        
        ItemsMin.getSelectionModel().selectedItemProperty().addListener((v,oldvalue,newValue) -> {
        
            LineItem = newValue;
            try {
                SalesLineChart();
            } catch (SQLException ex) {
                Logger.getLogger(Stats.class.getName()).log(Level.SEVERE, null, ex);
            }
            
        });

    }

    private void SalesLineChart() throws SQLException {
        
        XYChart.Series series = new XYChart.Series<>();
        
        String Query = "SELECT TotalPrice , orderTime from `canteen`.`MainOrderList` where `ItemName` = '"+LineItem+"'" ;
        Statement stm = MainApp.Connect.createStatement();
        Date month = new Date();
        ResultSet rs = stm.executeQuery(Query);
        series.getData().clear();
        while(rs.next()){
           Date Date =  rs.getDate("orderTime");
           
            series.getData().add(new XYChart.Data<>(String.valueOf(Date.getDate()),rs.getFloat("TotalPrice")));
            
        }
        
       Sales.getData().setAll(series);
        
    }

    private void PieChartVals() throws SQLException {
        
        ObservableList<PieChart.Data> pieChartData = FXCollections.observableArrayList();
        pieChartData = PieChartMainData();
        PieCharts.setData(pieChartData);
    }

    private ObservableList<PieChart.Data> PieChartMainData() throws SQLException {
    ObservableList<PieChart.Data> pieChartData = FXCollections.observableArrayList();
    
    String Query = "Select `PlacedBy`,COUNT(*) from `canteen`.`MainOrderList` GROUP BY `PlacedBy`;";
    Statement smt = MainApp.Connect.createStatement();
    ResultSet rs = smt.executeQuery(Query);
    while(rs.next()){
        pieChartData.add(new PieChart.Data(rs.getString("PlacedBy"), rs.getFloat("COUNT(*)")));
    }

    return pieChartData;
    
    }
    
    
//    Special Table :
    
    
     @FXML
    private BarChart<?, ?> MenuPopulartiy;

    @FXML
    private PieChart EmployeeShareSpecial;

    @FXML
    private LineChart<?, ?> SalesSpecial;

    @FXML
    private JFXComboBox<String> SpecialTables;
    
    
    String LineItem2;
    
    
//    SELECT `MenuName`,COUNT(*) FROM `ordersdonespecial` GROUP BY `MenuName`;
//         set1.getData().add(new XYChart.Data();
    private void MenuPopularityChart() throws SQLException {
         XYChart.Series set1 = new XYChart.Series<>();
         String query = "SELECT `MenuName`,COUNT(*) FROM `canteenextras`.`ordersdonespecial` GROUP BY `MenuName`;";
         Statement smt = MainApp.Connect.createStatement();
         ResultSet rs = smt.executeQuery(query);
         while(rs.next()){
            set1.getData().add(new XYChart.Data(rs.getString("MenuName"),rs.getInt("COUNT(*)")));
         }
         MenuPopulartiy.getData().setAll(set1);
    }
    
    ArrayList<String> a1 = new ArrayList<>();
 
    private void InitalizeMenuNameComboBox() throws SQLException {

        String query = "SELECT DISTINCT `MenuName` FROM `canteenextras`.`ordersdonespecial`;";
        Statement smt = MainApp.Connect.createStatement();
        ResultSet rs  = smt.executeQuery(query);
        while(rs.next())
        {
            a1.add(rs.getString("MenuName"));
        }
        
        SpecialTables.getItems().setAll(a1);
        SpecialTables.getSelectionModel().selectedItemProperty().addListener((v,oldval,newval)->{
        
            
            LineItem2 = newval;
            try {           
                SalesLineSpecialChart();
            } catch (SQLException ex) {
                Logger.getLogger(Stats.class.getName()).log(Level.SEVERE, null, ex);
            }
        
        });
        
        
    }


    private void SalesLineSpecialChart() throws SQLException {
         XYChart.Series series = new XYChart.Series<>();

        String query = "Select `CreatedTime`,`TotalCost` from `canteenextras`.`ordersdonespecial` Where `MenuName` = '"+LineItem2+"';";
        Statement smt = MainApp.Connect.createStatement();
        ResultSet rs = smt.executeQuery(query);
        while(rs.next())
        {
        Date Date =  rs.getDate("CreatedTime");
        series.getData().add(new XYChart.Data<>(String.valueOf(Date.getDate()),rs.getFloat("TotalCost")));
            
        
        }
        
        SalesSpecial.getData().setAll(series);
    }
    
    
    
    
       private void PieChartSpecialVals() throws SQLException {
        
        ObservableList<PieChart.Data> pieChartData = FXCollections.observableArrayList();
        pieChartData = PieChartSpecialData();
        EmployeeShareSpecial.setData(pieChartData);
    }

    private ObservableList<PieChart.Data> PieChartSpecialData() throws SQLException {
    ObservableList<PieChart.Data> pieChartData = FXCollections.observableArrayList();
    
    String Query = "Select `CreatedBy`,COUNT(*) from `canteenextras`.`ordersdonespecial` GROUP BY `CreatedBy`;";
    Statement smt = MainApp.Connect.createStatement();
    ResultSet rs = smt.executeQuery(Query);
    while(rs.next()){
        pieChartData.add(new PieChart.Data(rs.getString("CreatedBy"), rs.getFloat("COUNT(*)")));
    }

    return pieChartData;
    
    }
    
//    Earning Tables and Stuff !:
    
     @FXML
    private TableView<ModelItem> PopularItemTable;

    @FXML
    private TableColumn<ModelItem, String> Col_PopItem;

    @FXML
    private TableColumn<ModelItem, String> Col_PopQuantity;

   
    ObservableList<ModelItem> ObItem = FXCollections.observableArrayList();
    
  void CreateListAndPopTable() throws SQLException{
      
      String DropTable = "Drop View IF EXISTS `PopItem`;";
      String CreateView = "Create View `PopItem` As Select `ItemName`,SUM(Quantity) from `canteen`.`MainOrderList` GROUP BY `ItemName` ORDER By `SUM(Quantity)` DESC;";
      Statement smt = MainApp.Connect.createStatement();
      smt.execute(DropTable);
      smt.execute(CreateView);
      String ExtractView = "Select * from `PopItem`";
      ResultSet rs = smt.executeQuery(ExtractView);
      while(rs.next()){
          
        ObItem.add(new ModelItem(rs.getString("ItemName"), String.valueOf(rs.getInt("SUM(Quantity)"))));
      
      }
        
      Col_PopItem.setCellValueFactory(new PropertyValueFactory<>("Item"));
      Col_PopQuantity.setCellValueFactory(new PropertyValueFactory<>("Variable"));
      
      PopularItemTable.getItems().setAll(ObItem);
  
  }
    
  
    
    
    
    
    
    
//    Create A cost Price and then Subtract the same from the Total Cost , and get the highest profit items :
    @FXML
    private TableView<ModelItem> ProfitItemTable;

    @FXML
    private TableColumn<ModelItem, String> Col_ProfItemName;

    @FXML
    private TableColumn<ModelItem, String> Col_ProfItemEarnings;

//Menu Populartiy : 
    
    @FXML
    private TableView<ModelMenu> PopularMenuTable;

    @FXML
    private TableColumn<ModelMenu, String> Col_PopMenu;

    @FXML
    private TableColumn<ModelMenu, String> Col_PopMenuQuantity;
    
    
    
    
   ObservableList<ModelMenu> ObMenu = FXCollections.observableArrayList();


    void CreateListAndPopMenuTable() throws SQLException{
        
    String DropView = "Drop View IF EXISTS `PopMenu`;";
    String CreateView = "Create View `PopMenu` As Select `MenuName`,Count(*) from `canteenextras`.`ordersdonespecial` GROUP BY `MenuName` ORDER By `Count(*)` DESC;";
    Statement smt = MainApp.Connect.createStatement();
    smt.execute(DropView);
    smt.execute(CreateView);
    String ExtractView = "Select * from `PopMenu`";
    ResultSet rs = smt.executeQuery(ExtractView);
    while(rs.next()){
          
        ObMenu.add(new ModelMenu(rs.getString("MenuName"), String.valueOf(rs.getInt("Count(*)"))));
      
      }

    Col_PopMenu.setCellValueFactory(new PropertyValueFactory<>("Item"));
    Col_PopMenuQuantity.setCellValueFactory(new PropertyValueFactory<>("Variable"));
    
    PopularMenuTable.getItems().setAll(ObMenu);
    }

    private void CheckForExistance() throws SQLException {
        boolean flagExist = false;
        boolean flagExist1 = false;
        String check1 = "SELECT TABLE_NAME \n" +
                     "FROM INFORMATION_SCHEMA.TABLES\n" +
                     "WHERE TABLE_TYPE = 'BASE TABLE' AND TABLE_SCHEMA='canteenextras';" ;
        String check2 = "SELECT TABLE_NAME \n" +
                     "FROM INFORMATION_SCHEMA.TABLES\n" +
                     "WHERE TABLE_TYPE = 'BASE TABLE' AND TABLE_SCHEMA='canteen';" ;
        
       Statement smt = MainApp.Connect.createStatement();
       ResultSet rs = smt.executeQuery(check1);
       while(rs.next()){
   
          String TableName = rs.getString("TABLE_NAME");
          if(TableName.equals("ordersdonespecial")){
              flagExist= true;
              System.out.println("Hell Ya?");
          }
          
       }
       rs = smt.executeQuery(check2);
       while(rs.next()){
           
             String TableName = rs.getString("TABLE_NAME");
          if(TableName.equals("mainorderlist")){
              flagExist1= true;
              System.out.println("Working?");
          }
       
       }
       
       if(flagExist && flagExist1){
       
           
           popupAlert(true);
       }
       
       else{
       
           popupAlert(false);
       }

    }
    
    
     private void popupAlert(boolean b) {
        Orders ord = new Orders ();
        if(b){
            Alert a = new Alert(Alert.AlertType.INFORMATION );
            ord.StyleAlert(a);
            a.setTitle("Successful!");
            a.setHeaderText("Successful !");
            a.setContentText("Data Loaded Successful !");
            a.showAndWait();
            
        }
        else{
           Alert a = new Alert(Alert.AlertType.ERROR);
           ord.StyleAlert(a);
           a.setTitle("Something Went Wrong !");
            a.setHeaderText("Looks Like Something Went Wrong !");
            a.setContentText("Error Details are not Available at the moment , Please contact "
                    + "the Dev ! ");
            a.showAndWait();   
        }
        
    }
        

}