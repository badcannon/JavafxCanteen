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
                String LoginTableStatement = "CREATE TABLE IF NOT EXISTS canteen.logindets(Username VARCHAR(20) PRIMARY KEY,Password VARCHAR(20)) ";
                String RolesTableStatement = "CREATE TABLE IF NOT EXISTS `canteen`.`roles` (\n" +
                                             "  `Role` varchar(20) NOT NULL,\n" +
                                             "  `Username` varchar(20) NOT NULL,\n" +
                                             "  PRIMARY KEY (`Username`),\n" +
                                             "  KEY `Username_idx` (`Username`),\n" +
                                             "  CONSTRAINT `Username` FOREIGN KEY (`Username`) REFERENCES `logindets` (`Username`) ON DELETE CASCADE ON UPDATE CASCADE\n" +
                                             ");";
                Statement statement =MainApp.Connect.createStatement();
                statement.execute(LoginTableStatement);
                statement.execute(RolesTableStatement);
                }
            catch (SQLException e ){
                System.out.println("Sql Error");
            }
        }
        else{
            return false;
        }
        
        return true;
    
    }
    
    
    
}
