/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.DBMSproject;

import com.jfoenix.controls.JFXButton;
import java.io.File;
import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.concurrent.ExecutionException;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;


public class SpecialDets{
   static ArrayList<String> TableNames = new ArrayList<>();
    static Connection ConnectSpecial ;
    private String FinalWorkingTableName;
    private String workingTable;
    private boolean flag = false;
   
    
    ArrayList<String> returnTables() throws SQLException{
        setTableNames();
        return TableNames;
    
    }
    
    
   
   void ConnectSpecialDB() throws SQLException, ClassNotFoundException {
        
    String defaultStatementSpecial = "CREATE SCHEMA IF NOT EXISTS `canteenextras` ;";
    Class.forName("com.mysql.cj.jdbc.Driver");
    //Defined Earlier !
    ConnectSpecial = DriverManager.getConnection("jdbc:mysql://localhost:3306/","root","shosperm");
    Statement statement = ConnectSpecial.createStatement();
    statement.execute(defaultStatementSpecial);
    ConnectSpecial = DriverManager.getConnection("jdbc:mysql://localhost:3306/canteenextras","root","shosperm");
        

    }
   
       
    public void setTableNames() throws SQLException { 
        TableNames.clear();
        String getTables = "SELECT TABLE_NAME \n" +
                     "FROM INFORMATION_SCHEMA.TABLES\n" +
                     "WHERE TABLE_TYPE = 'BASE TABLE' AND TABLE_SCHEMA='canteenextras';" ;
        
        Statement smt = ConnectSpecial.createStatement();
        ResultSet rs = smt.executeQuery(getTables);
        while(rs.next()){
            
           String Table =  rs.getString("TABLE_NAME");
           TableNames.add(Table);
                   
        }
        
      
    }
       private boolean TableExists(String tableName) throws SQLException {
        TableNames.clear();
        setTableNames();
            for(String name: TableNames){
                if(name.equals(tableName)){
                    System.out.println(flag);
                    return true;
                }
                
            
            }
                
        return false;
    }
    
        
    boolean CreateTable(String TableName) throws SQLException, InterruptedException, IOException {
        FinalWorkingTableName = TableName.replaceAll(" ", "").toLowerCase();
        workingTable = TableName;
        System.out.println(FinalWorkingTableName);
        flag = TableExists(FinalWorkingTableName);
        if(flag){
                
              return false;
           }
          
          
            else{
                System.out.println(FinalWorkingTableName);
                String smt = "CREATE TABLE `canteenextras`.`"+FinalWorkingTableName+"`(" +
                             "`Image` VARCHAR(300)," +
                             "`ItemName` VARCHAR(30) NOT NULL," +
                             "`Price` FLOAT NOT NULL," +
                             "`Quantity` INT(38) NULL," +
                             "`Category` varchar(50) DEFAULT NULL,"+
                             "`createdDateTime` datetime DEFAULT NULL," +
                             "`creator` varchar(100) DEFAULT NULL,"+ 
                             "`Modifier` varchar(100) DEFAULT NULL," +
                             "`LastModified` varchar(100) DEFAULT NULL,"+
                             "PRIMARY KEY (`ItemName`));";
                
                System.out.println(smt);
                
                Statement ptm = ConnectSpecial.createStatement();
                ptm.execute(smt);
                
            
            }
     return true;

            
    }

    boolean InsertToTable(String itemName,String itemPrice,String itemQuantity,String imgpath,String category,String CreateTime,String Creator) {
        try{
        String pmt = "INSERT INTO `canteenextras`.`"+FinalWorkingTableName+"` (`Image`,`ItemName`,`Price`,`Quantity`,`Category`,`createdDateTime`,`creator`) VALUES(?,?,?,?,?,?,?);";
        PreparedStatement stm = ConnectSpecial.prepareStatement(pmt);
        stm.setString(1,imgpath);
        stm.setString(2, itemName);
        stm.setString(3, itemPrice);
        stm.setString(4, itemQuantity);
        stm.setString(5, category);
        stm.setString(6, CreateTime);
        stm.setString(7, Creator);
        stm.execute();
        
        }
        catch(Exception ex){
        
            System.out.println(ex.getMessage());
            return false;
        
        }
        
    return true;
    
    }

    ObservableList<String> CreateAndAddSpecialCats() throws SQLException {
        
        ObservableList<String> oblist = FXCollections.observableArrayList();
        System.out.println("We Are IN !!!");
        String defCatQuery = "CREATE TABLE IF NOT EXISTS`canteen`.`specialcategory` (\n" +
                             "  `CategoryName` varchar(30)  NOT NULL,\n" +
                             "  PRIMARY KEY (`CategoryName`)\n" +
                             ") ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;";
        Statement smt = ConnectSpecial.createStatement();
        smt.execute(defCatQuery);
        String Select = "SELECT * FROM `canteen`.`specialcategory`;";
        String defaultCategories[] =  {"BreakFast","Lunch","Dinner"};
        ResultSet rs = smt.executeQuery(Select);
        //rs Always points to the tables before first row ! 
        String insStatement = "INSERT INTO `canteen`.`specialcategory`(`CategoryName`) VALUES(?);";
        PreparedStatement ptm = MainApp.Connect.prepareStatement(insStatement);
        
        
        if(rs.next() == false){
            for(String x : defaultCategories){
                ptm.setString(1, x);
                ptm.execute();
                oblist.add(x);
                System.out.println(oblist.isEmpty());
            
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

    ObservableList<ModelSpecial> PrePareList() throws SQLException {

        ObservableList<ModelSpecial> oblist = FXCollections.observableArrayList();
        String name,category;
        String quantity,price,finalPrice;
        Float totalprice = new Float(0);
        String image;
        Image imgs;
        ImageView FinalImage;
        String Select = "SELECT * FROM  `canteenextras`.`"+FinalWorkingTableName+"`;";
        Statement smt = ConnectSpecial.createStatement();
        ResultSet rs = smt.executeQuery(Select);
        while(rs.next()){
        name = rs.getString("ItemName");
        category = rs.getString("Category");
        price = rs.getString("Price");
        quantity = rs.getString("Quantity");
        totalprice = (Integer.parseInt(quantity) * Float.parseFloat(price));
        finalPrice = totalprice.toString();
        image = rs.getString("Image");
        try{
         imgs = new Image(image);
        }catch(Exception e){
         imgs = new Image(getClass().getResource("Assets/image-placeholder-1200x800.jpg").toString());
  
        }
        FinalImage = new ImageView(imgs);
        FinalImage.setFitHeight(100);
        FinalImage.setFitWidth(150); 
        oblist.add(new ModelSpecial(name,price,quantity, category,finalPrice, FinalImage));
        
        
        
        
        
        }
        return oblist;
    }
      
    
    void setWorkingTable(String TableName){
    
        FinalWorkingTableName = TableName;
    
    }

    void removeSelected(String ItemName) throws SQLException {

      
        String remove= "DELETE FROM `canteenextras`.`"+FinalWorkingTableName+"` WHERE `ItemName` = '"+ItemName+"';";
        Statement smt = ConnectSpecial.createStatement();
        smt.execute(remove);
        
    }

    boolean UpdateSpecial(String OldItemName,String Modifier,String ModifiedAt, String itemName, String itemPrice, String itemQantity, String imageUri, String category) throws SQLException {
    try{
      String Update = ""
                    + "UPDATE `canteenextras`.`"+FinalWorkingTableName+"` "
                    + "SET    `image` = '"+imageUri+"', "
                    + "       `itemname` = '"+itemName+"', "
                    + "       `price` = '"+itemPrice+"', "
                    + "       `quantity` = '"+itemQantity+"', "
                    + "       `category` = '"+category+"', "
                    + "       `lastmodified` = '"+ModifiedAt+"', "
                    + "       `Modifier` = '"+Modifier+"'"
                    + "WHERE  `itemname` = '"+OldItemName+"';";
      
      Statement smt = ConnectSpecial.createStatement();
      smt.execute(Update);
    }catch(Exception e)
    {
        System.out.println(e.getMessage());
        return false;
    }
    
        return true;
    
    }

    

    
}
//
//"`Image` VARCHAR(300)," +
//                             "`ItemName` VARCHAR(30) NOT NULL," +
//                             "`Price` FLOAT NOT NULL," +
//                             "`Quantity` INT(38) NULL," +
//                             "`Category` varchar(50) DEFAULT NULL,"+
//                             "`createdDateTime` datetime DEFAULT NULL," +
//                             "`creator` varchar(100) DEFAULT NULL,"+ 
//                             "`LastModified` varchar(100) DEFAULT NULL,"+
//                             "PRIMARY KEY (`ItemName`));";