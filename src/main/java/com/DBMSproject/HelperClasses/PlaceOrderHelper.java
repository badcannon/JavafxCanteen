/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.DBMSproject;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

/**
 *
 * @author shree
 */
public class PlaceOrderHelper {

    static void CreatePlaceDB() throws SQLException {
        
       String DefQuery = "CREATE TABLE IF NOT EXISTS `canteen`.`MainOrderList` ( `OrderId` INT NULL DEFAULT NULL , `ItemName` VARCHAR(400) NULL DEFAULT NULL , `Quantity` INT NOT NULL , `TotalPrice` FLOAT NULL DEFAULT NULL , `orderTime` DATETIME NULL DEFAULT NULL , `PlacedBy` VARCHAR(400) NOT NULL ) ENGINE = InnoDB; ";
       Statement statement = MainApp.Connect.createStatement();
       statement.execute(DefQuery);
        System.out.println("Table Created ! ");

    }

    static void PlaceInsertTOTable(ObservableList<ModelSoldSave> orderedList) throws SQLException {
        
        String query  = "INSERT INTO `MainOrderList` (`OrderId`, `ItemName`, `Quantity`, `TotalPrice`, `orderTime`, `PlacedBy`) VALUES (?,?,?,?,?,?) ;";
        PreparedStatement ptm = MainApp.Connect.prepareStatement(query);
        
        for(ModelSoldSave x : orderedList){
            ptm.setString(1, x.OrderId);
            ptm.setString(2, x.ItemName);
            ptm.setString(3, x.Quantity);
            ptm.setString(4, x.TotalPrice);
            ptm.setString(5, x.OrderTime);
            ptm.setString(6, x.PlacedBy);
            ptm.execute();          
        }
        

    }

 
    
    
    //DbConnection For Place Orders !
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
                        ");");

                        } catch (SQLException ex) {
                Logger.getLogger(PlaceOrderHelper.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
    }
     
     
     //Remove From DataBased !
     
        boolean RemoveTable(ModelTable Delete) throws SQLException {

            String remove= "DELETE FROM `canteen`.`menu` WHERE `Itemname`= '"+Delete.ItemName+"';";
            Statement state = MainApp.Connect.createStatement();
            try{
            state.execute(remove);
            }catch(Exception e){
                System.out.println(e.getMessage());
            }
            return true;
    }
     
    
        
        ObservableList<String >setCategoryNames() throws SQLException{
        
            ObservableList<String> oblist = FXCollections.observableArrayList();
        System.out.println("We Are IN !!!");
        String defCatQuery = "CREATE TABLE IF NOT EXISTS `canteen`.`Maincategory` (\n" +
                             "  `CategoryName` varchar(30)  NOT NULL,\n" +
                             "  PRIMARY KEY (`CategoryName`)\n" +
                             ");";
        Statement smt = MainApp.Connect.createStatement();
        smt.execute(defCatQuery);
        String Select = "SELECT * FROM `canteen`.`Maincategory`;";
        String defaultCategories[] =  {"BreakFast","Lunch","Dinner"};
        ResultSet rs = smt.executeQuery(Select);
        //rs Always points to the tables before first row ! 
        String insStatement = "INSERT INTO `canteen`.`Maincategory`(`CategoryName`) VALUES(?);";
        PreparedStatement ptm = MainApp.Connect.prepareStatement(insStatement);
        
        
        if(rs.next() == false){
            for(String x : defaultCategories){
                ptm.setString(1, x);
                ptm.execute();
                oblist.add(x);
            
            }
          
        }
        
        else {
        
            do {
            
            String x  = rs.getString("CategoryName");
            oblist.add(x);
            
            }while(rs.next());
        
        }
        
        return oblist;
        
        }

    void UpdateMainTable(String Image, String Name, String Price, String Description, String Quantity, String Category, String Modifier, String LastModified,String Oldname) throws SQLException {
       try{
           boolean flag=false;
           String UpdateTableDef = "CREATE TABLE IF NOT EXISTS `canteen`.`menuedits` (\n" +
                                "  `ItemName` VARCHAR(20) NOT NULL,\n" +
                                "  `Modifier` VARCHAR(40) NULL,\n" +
                                "  `LastModified` DATETIME NULL,\n" +
                                "  `countModified` INT NULL,\n" +
                                "   PRIMARY KEY (`ItemName`),\n" +
                                "   CONSTRAINT `ItemName`\n" +
                                "   FOREIGN KEY (`ItemName`)\n" +
                                "   REFERENCES `canteen`.`menu` (`Itemname`)\n" +
                                "   ON DELETE CASCADE\n" +
                                "   ON UPDATE CASCADE);";
           
           Statement Statement1 = MainApp.Connect.createStatement();
           Statement1.execute(UpdateTableDef);
           
           
        String Update = ""
                    + "UPDATE `canteen`.`menu` "
                    + "SET    `Image` = '"+Image+"', "
                    + "       `Itemname` = '"+Name+"', "
                    + "       `description` = '"+Description+"', "
                    + "       `price` = '"+Price+"', "
                    + "       `Itemquantity` = '"+Quantity+"', "
                    + "       `category` = '"+Category+"' "
                    + "WHERE  `itemname` = '"+Oldname+"';";
      
      Statement smt = MainApp.Connect.createStatement();
      smt.execute(Update);
      String CheckIfExists = "SELECT * FROM `canteen`.`menuedits`";
      Statement statement2 = MainApp.Connect.createStatement();
      ResultSet rs = statement2.executeQuery(CheckIfExists);
      String ItemNameExist;
      while(rs.next()){
          
          ItemNameExist = rs.getString("ItemName");
          if(ItemNameExist.equals(Name)){
          
              flag =true;
          
          }
          
          
      
      }
      
      if(flag == true){
          updatemenuEdits(Oldname,Name,Modifier,LastModified);
          System.out.println("Done");
      }
      else {
      
          insertMenuEdits(Oldname,Name,Modifier,LastModified);
          System.out.println(""
                  + "DOne1");
      }
      
       }
       catch(Exception e){
           System.out.println(e.getMessage());
           
       }
        


    }

    private void updatemenuEdits(String Oldname, String Name, String Modifier, String LastModified) throws SQLException {
        String getCount = "SELECT countModified FROM `canteen`.`menuedits` WHERE `ItemName`='"+Name+"'";
        ResultSet rs = MainApp.Connect.createStatement().executeQuery(getCount);
        int count = 0;

        while(rs.next()){
            
            count=rs.getInt("countModified");
            
        
        }
        count = count++;
        String update = "UPDATE `canteen`.`menuedits`\n" +
                        "SET\n" +
                        "`Modifier` = '"+Modifier+"',\n" +
                        "`LastModified` = '"+LastModified+"',\n" +
                        "`countModified` = '"+count+"'\n" +
                        "WHERE `ItemName` = '"+Name+"';";
       Statement smt = MainApp.Connect.createStatement();
       smt.execute(update);        
    }

    private void insertMenuEdits(String Oldname, String Name, String Modifier, String LastModified) throws SQLException {
        int count = 1 ;
        String Insert = "INSERT INTO `canteen`.`menuedits`\n" +
                        "(`ItemName`,\n" +
                        "`Modifier`,\n" +
                        "`LastModified`,\n" +
                        "`countModified`)\n" +
                        "VALUES\n" +
                        "(?,\n" +
                        "?,\n" +
                        "?,\n" +
                        "?);";
        PreparedStatement pmt = MainApp.Connect.prepareStatement(Insert);
        pmt.setString(1,Name);
        pmt.setString(2, Modifier);
        pmt.setString(3, LastModified);
        pmt.setInt(4, count);
        pmt.execute();
        
    
    
    }

 
    
}
