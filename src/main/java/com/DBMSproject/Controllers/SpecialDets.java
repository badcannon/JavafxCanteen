/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.DBMSproject;

import com.jfoenix.controls.JFXButton;
import java.awt.BorderLayout;
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
    private String UpdateTableEdits;
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
    ConnectSpecial = DriverManager.getConnection("jdbc:mysql://localhost/?useUnicode=true&useJDBCCompliantTimezoneShift=true&useLegacyDatetimeCode=false&serverTimezone=UTC","root","");
    Statement statement = ConnectSpecial.createStatement();
    statement.execute(defaultStatementSpecial);
    ConnectSpecial = DriverManager.getConnection("jdbc:mysql://localhost/canteenextras?useUnicode=true&useJDBCCompliantTimezoneShift=true&useLegacyDatetimeCode=false&serverTimezone=UTC","root","");
        

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
           if(!Table.endsWith("edit")&&!Table.equals("ordersdonespecial"))
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
                UpdateTableEdits = FinalWorkingTableName + "edit";
                String smt = "CREATE TABLE  IF NOT EXISTS `canteenextras`.`"+FinalWorkingTableName+"`(" +
                             "`Image` VARCHAR(300)," +
                             "`ItemName` VARCHAR(30) NOT NULL," +
                             "`Price` FLOAT NOT NULL," +
                             "`Quantity` INT(38) NULL," +
                             "`Category` varchar(50) DEFAULT NULL,"+
                             "`createdDateTime` datetime DEFAULT NULL," +
                             "`creator` varchar(100) DEFAULT NULL,"+ 
                             "PRIMARY KEY (`ItemName`)) ENGINE = InnoDB;";
                
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
                             ");";
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
      boolean flagExist= false;
      String Update = "" 
                    + "UPDATE `canteenextras`.`"+FinalWorkingTableName+"` "
                    + "SET    `image` = '"+imageUri+"', "
                    + "       `itemname` = '"+itemName+"', "
                    + "       `price` = '"+itemPrice+"', "
                    + "       `quantity` = '"+itemQantity+"', "
                    + "       `category` = '"+category+"'"
                    + "WHERE  `itemname` = '"+OldItemName+"';";
      UpdateTableEdits = FinalWorkingTableName + "edit";
      String UpdateEditsDef = "CREATE TABLE IF NOT EXISTS `canteenextras`.`"+UpdateTableEdits+"` (\n" +
                                "  `ItemName` VARCHAR(20) NOT NULL,\n" +
                                "  `Modifier` VARCHAR(40) NULL,\n" +
                                "  `LastModified` DATETIME NULL,\n" +
                                "  `countModified` INT NULL,\n" +
                                "   PRIMARY KEY (`ItemName`),\n" +
                                "   CONSTRAINT `ItemName`\n" +
                                "   FOREIGN KEY (`ItemName`)\n" +
                                "   REFERENCES `canteenextras`.`"+FinalWorkingTableName+"` (`Itemname`)\n" +
                                "   ON DELETE CASCADE\n" +
                                "   ON UPDATE CASCADE);";
      
      
      Statement smt = ConnectSpecial.createStatement();
      smt.execute(UpdateEditsDef);
      smt.execute(Update);
        System.out.println("Here !");
      
      String CheckIfExists = "SELECT * FROM `canteenextras`.`"+UpdateTableEdits+"`";
      Statement statement2 = MainApp.Connect.createStatement();
      ResultSet rs = statement2.executeQuery(CheckIfExists);
      String ItemNameExist;
      while(rs.next()){
          
          ItemNameExist = rs.getString("ItemName");
          if(ItemNameExist.equals(itemName)){
          
              System.out.println(ItemNameExist);
              System.out.println(itemName);
              flagExist =true;
              System.out.println(flagExist);
          
          }
          
          
      
      }
       if(flagExist == true){
           System.out.println("Entered Here !");
          updatemenuEdits(itemName,Modifier,ModifiedAt);
          System.out.println("Done");
      }
      else {
      
          insertMenuEdits(itemName,Modifier,ModifiedAt);
          System.out.println(""
                  + "DOne1");
      }
      
      
    }catch(Exception e)
    {
        System.out.println(e.getMessage());
        return false;
    }
    
        return true;
    
    }
    
       private void updatemenuEdits( String Name, String Modifier, String LastModified) throws SQLException {
        String getCount = "SELECT countModified FROM `canteenextras`.`"+UpdateTableEdits+"` WHERE `ItemName`='"+Name+"'";
        ResultSet rs = MainApp.Connect.createStatement().executeQuery(getCount);
        rs.next();
        int countX = rs.getInt("countModified");
        countX++;
        System.out.println(countX);
        
        String update = "UPDATE `canteenextras`.`"+UpdateTableEdits+"`\n" +
                        "SET\n" +
                        "`ItemName` = '"+Name+"',"+
                        "`Modifier` = '"+Modifier+"',\n" +
                        "`LastModified` = '"+LastModified+"',\n" +
                        "`countModified` = '"+countX+"'\n" +
                        "WHERE `ItemName` = '"+Name+"';";
       Statement smt = MainApp.Connect.createStatement();
       smt.execute(update);        
    }

    private void insertMenuEdits( String Name, String Modifier, String LastModified) throws SQLException {
        int count = 1 ;
        String Insert = "INSERT INTO `canteenextras`.`"+UpdateTableEdits+"`\n" +
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

    void SaveTable() throws SQLException {
        
        Orders ords = new Orders();
        String DefString = "CREATE TABLE IF NOT EXISTS `canteenextras`.`ordersdonespecial` ( `slno` INT NOT NULL AUTO_INCREMENT , `MenuName` VARCHAR(300) NOT NULL , `CreatedTime` DATETIME NOT NULL , `CreatedBy` VARCHAR(300) NOT NULL , `TotalCost` FLOAT NOT NULL,PRIMARY KEY (`slno`)) ENGINE = InnoDB;";
        Statement defSt = MainApp.Connect.createStatement();
        defSt.execute(DefString);
        String instString = "INSERT INTO `canteenextras`.`ordersdonespecial` (`slno`, `MenuName`,`CreatedTime`,`CreatedBy`,`TotalCost`) VALUES (NULL, ?,?,?,?);";
        PreparedStatement ptm = MainApp.Connect.prepareStatement(instString);
        ptm.setString(1, FinalWorkingTableName);
        ptm.setString(2,ords.getCurrentTime());        
        ptm.setString(3,ords.getCreateBy());
        ptm.setString(4, getTotalCost());
        ptm.execute();
    }


       String getTotalCost() throws SQLException {
       
        ModelSpecial x;
        Float TotalCost = 0f;
        String query = "Select * from `canteenextras`.`"+FinalWorkingTableName+"`";
        Statement smt = MainApp.Connect.createStatement();
        ResultSet rs = smt.executeQuery(query);
        while(rs.next()){
            
            TotalCost += rs.getFloat("Price")*rs.getInt("Quantity");
        
        }
        return String.valueOf(TotalCost);

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