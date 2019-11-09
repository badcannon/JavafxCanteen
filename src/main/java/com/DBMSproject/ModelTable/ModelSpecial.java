
package com.DBMSproject;

import com.jfoenix.controls.JFXButton;
import javafx.scene.image.ImageView;


public class ModelSpecial {
    
    String ItemName, Price,Quantity,category,totalPrice;

    ImageView file;
    Orders ords = new Orders();
    
    public ModelSpecial(String ItemName, String Price, String Quantity, String category, String totalPrice, ImageView file) {
        this.ItemName = ItemName;
        this.Price = Price;
        this.Quantity = Quantity;
        this.category = category;
        this.totalPrice = totalPrice;
        this.file = file;

        
    }

    public String getItemName() {
        return ItemName;
    }



    public String getPrice() {
        return Price;
    }



    public String getQuantity() {
        return Quantity;
    }


    public String getCategory() {
        return category;
    }



    public String getTotalPrice() {
        return totalPrice;
    }


    public ImageView getFile() {
        return file;
    }

    
    
    
}
