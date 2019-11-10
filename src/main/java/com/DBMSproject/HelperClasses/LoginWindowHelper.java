/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.DBMSproject;

import java.sql.SQLException;
import java.sql.Statement;
import javafx.scene.control.Alert;

/**
 *
 * @author shree
 */
public class LoginWindowHelper {
    
    
    
    
     boolean ConnectToDb(){
     boolean Con = MainApp.Connect();
        if(Con){
            try {
                String LoginTableStatement =    "CREATE TABLE IF NOT EXISTS `canteen`.`logindets` (\n" +
                                                "  `Image` varchar(200) NOT NULL,\n" +
                                                "  `Name` varchar(300) NOT NULL,\n" +
                                                "  `Username` varchar(20) NOT NULL PRIMARY KEY,\n" +
                                                "  `Password` varchar(20) DEFAULT NULL,\n" +
                                                "  `Salary` float NOT NULL,\n" +
                                                "  `CreatedTime` DATETIME DEFAULT NULL,\n"+
                                                "  `CreatedBy`  VARCHAR(200) DEFAULT NULL" +
                                                ") ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;";
                String RolesTableStatement = "CREATE TABLE IF NOT EXISTS `canteen`.`roles` (\n" +
                                            "  `Role` varchar(200) DEFAULT NULL,\n" +
                                            "  `Username` varchar(200) DEFAULT NULL,\n" +
                                            "   CONSTRAINT usernames FOREIGN KEY (username) REFERENCES `canteen`.`logindets`(Username)\n " +
                                            "ON UPDATE CASCADE\n" +
                                            "ON DELETE CASCADE) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;";
                 Statement statement =MainApp.Connect.createStatement();
                statement.execute(LoginTableStatement);
                statement.execute(RolesTableStatement);
                System.out.println("Here Done!");
                }
            catch (SQLException e ){
                System.out.println(e.getMessage());
            }
        }
        else{
            return false;
        }
        
        return true;
    
    }
    
    
    
}
