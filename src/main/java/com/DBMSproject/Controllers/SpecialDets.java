/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.DBMSproject;

import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import javafx.scene.control.Alert;


public class SpecialDets{
   static ArrayList<String> TableNames = new ArrayList<>();
    static Connection ConnectSpecial ;
    private String FinalWorkingTableName;
    private String workingTable;
    private boolean flag = false;
   
   
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
                             "`createdDateTime` datetime DEFAULT NULL," +
                             "`creator` varchar(100) DEFAULT NULL,"+ 
                             "`LastModified` varchar(100) DEFAULT NULL,"+
                             "PRIMARY KEY (`ItemName`));";
                
                System.out.println(smt);
                
                Statement ptm = ConnectSpecial.createStatement();
                ptm.execute(smt);
                
            
            }
     return true;

            
    }

    boolean InsertToTable(String itemName,String itemPrice,String itemQuantity,String imgpath,String CreateTime,String Creator) {
        try{
        String pmt = "INSERT INTO `canteenextras`.`"+FinalWorkingTableName+"` (`Image`,`ItemName`,`Price`,`Quantity`,`createdDateTime`,`creator`) VALUES(?,?,?,?,?,?);";
        PreparedStatement stm = ConnectSpecial.prepareStatement(pmt);
        stm.setString(1,imgpath);
        stm.setString(2, itemName);
        stm.setString(3, itemPrice);
        stm.setString(4, itemQuantity);
        stm.setString(5, CreateTime);
        stm.setString(6, Creator);
        stm.execute();
        
        }
        catch(Exception ex){
        
            System.out.println(ex.getMessage());
            return false;
        
        }
        
    return true;
    
    }
      
}
