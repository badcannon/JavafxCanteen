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
                        ") ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;");

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
                             ") ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;";
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
    
}
