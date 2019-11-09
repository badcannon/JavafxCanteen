/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.DBMSproject;

/**
 *
 * @author shree
 */
public class SoldTable {
    String Name,Price,Quantity,TotalPrice,OrderId;
        public SoldTable(String OrderId,String Name, String Price, String Quantity, String TotalPrice) {
        this.OrderId = OrderId;
        this.Name = Name;
        this.Price = Price;
        this.Quantity = Quantity;
        this.TotalPrice = TotalPrice;
    }

    public String getName() {
        return Name;
    }

    public void setName(String Name) {
        this.Name = Name;
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

    public String getTotalPrice() {
        return TotalPrice;
    }

    public void setTotalPrice(String TotalPrice) {
        this.TotalPrice = TotalPrice;
    }

    public String getOrderId() {
        return OrderId;
    }

    public void setOrderId(String OrderId) {
        this.OrderId = OrderId;
    }
        
    
}
