/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.DBMSproject;

import javafx.scene.image.ImageView;

/**
 *
 * @author badcannon
 */
public class ModelUsers {
    
    String Name,Role,Salary,UserName;
    ImageView file;
    public ModelUsers(ImageView file,String Name, String Role, String Salary, String UserName) {
        this.file = file;
        this.Name = Name;
        this.Role = Role;
        this.Salary = Salary;
        this.UserName = UserName;
    }

    public ImageView getFile() {
        return file;
    }

    public void setFile(ImageView file) {
        this.file = file;
    }

    public String getName() {
        return Name;
    }

    public void setName(String Name) {
        this.Name = Name;
    }

    public String getRole() {
        return Role;
    }

    public void setRole(String Role) {
        this.Role = Role;
    }

    public String getSalary() {
        return Salary;
    }

    public void setSalary(String Salary) {
        this.Salary = Salary;
    }

    public String getUserName() {
        return UserName;
    }

    public void setUserName(String UserName) {
        this.UserName = UserName;
    }
    
}
