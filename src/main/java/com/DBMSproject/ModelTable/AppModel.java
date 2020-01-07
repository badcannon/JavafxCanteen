/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.DBMSproject;

import javafx.collections.ObservableList;

/**
 *
 * @author shree
 */
public class AppModel {
    
    static ObservableList<SoldTable> list;

    public AppModel(ObservableList<SoldTable> list) {
        this.list = list;
        System.out.println(list.size());
    }
    public  AppModel(){
    
    }

    public static ObservableList<SoldTable> getList() {
        return list;
    }

    public static void setList(ObservableList<SoldTable> list) {
        AppModel.list = list;
    }
    
    
    
    
}
