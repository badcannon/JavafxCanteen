/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.DBMSproject;

import com.jfoenix.controls.datamodels.treetable.RecursiveTreeObject;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

/**
 *
 * @author shree
 */
public class ModelTable extends RecursiveTreeObject<ModelTable> {
    StringProperty ItemName,Description, Price;
    Image file;
    
    public ModelTable(String ItemName,String Description,String Price,Image file){
        this.ItemName = new SimpleStringProperty(ItemName);
        this.Description = new SimpleStringProperty(Description);
        this.Price = new SimpleStringProperty(Price);
        this.file = file;
    } 

    public StringProperty getItemName() {
        return ItemName;
    }

    public void setItemName(StringProperty ItemName) {
        this.ItemName = ItemName;
    }

    public StringProperty getDescription() {
        return Description;
    }

    public void setDescription(StringProperty Description) {
        this.Description = Description;
    }

    public StringProperty getPrice() {
        return Price;
    }

    public void setPrice(StringProperty Price) {
        this.Price = Price;
    }

    public Image getFile() {
        return file;
    }

    public void setFile(Image file) {
        this.file = file;
    }
    
    
    
    
}
