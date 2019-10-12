
package com.DBMSproject;

import com.jfoenix.controls.JFXButton;
import javafx.scene.image.ImageView;


public class ModelSpecial {
    
    String ItemName, Price,Quantity,category,totalPrice;


    ImageView file;
    JFXButton Update;

    public ModelSpecial(String ItemName, String Price, String Quantity, String category, String totalPrice, ImageView file, JFXButton Update) {
        this.ItemName = ItemName;
        this.Price = Price;
        this.Quantity = Quantity;
        this.category = category;
        this.totalPrice = totalPrice;
        this.file = file;
        this.Update = Update;
    }

    public String getItemName() {
        return ItemName;
    }

    public void setItemName(String ItemName) {
        this.ItemName = ItemName;
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

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public String getTotalPrice() {
        return totalPrice;
    }

    public void setTotalPrice(String totalPrice) {
        this.totalPrice = totalPrice;
    }

    public ImageView getFile() {
        return file;
    }

    public void setFile(ImageView file) {
        this.file = file;
    }

    public JFXButton getUpdate() {
        return Update;
    }

    public void setUpdate(JFXButton Update) {
        this.Update = Update;
    }
    
    
    
}
