/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.DBMSproject;

import com.jfoenix.controls.datamodels.treetable.RecursiveTreeObject;
import javafx.scene.image.ImageView;

/**
 *
 * @author shree
 */
public class ModelTable extends RecursiveTreeObject<ModelTable> {
    String ItemName,Description, Price,Quantity;
    ImageView file;

    public ModelTable(String ItemName,String Description,String Price,ImageView file,String Quantity){
        this.ItemName =  ItemName ;
        this.Description =  Description ;
        this.Price =  Price ;
        this.file = file;
        this.Quantity = Quantity  ;
    } 

    public String getItemName() {
        return ItemName;
    }

    public void setItemName(String ItemName) {
        this.ItemName = ItemName;
    }

    public String getDescription() {
        return Description;
    }

    public void setDescription(String Description) {
        this.Description = Description;
    }

    public String getPrice() {
        return Price;
    }

    public void setPrice(String Price) {
        this.Price = Price;
    }

    public String getQuantity() {
        return Quantity;
    }

    public void setQuantity(String Quantity) {
        this.Quantity = Quantity;
    }

    public ImageView getFile() {
        return file;
    }

    public void setFile(ImageView file) {
        this.file = file;
    }

  
    
    
    
    
}
