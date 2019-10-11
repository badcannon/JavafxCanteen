
package com.DBMSproject;

import javafx.scene.image.ImageView;


public class ModelSpecial {
    
    String ItemName,Description, Price,Quantity;
    ImageView file;

    public ModelSpecial(String ItemName, String Description, String Price, String Quantity, ImageView file) {
        this.ItemName = ItemName;
        this.Description = Description;
        this.Price = Price;
        this.Quantity = Quantity;
        this.file = file;
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
