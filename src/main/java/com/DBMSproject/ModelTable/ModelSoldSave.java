/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.DBMSproject;

/**
 *
 * @author badcannon
 */
class ModelSoldSave {
    
    String OrderId,ItemName,Quantity,TotalPrice,OrderTime,PlacedBy;

    public ModelSoldSave(String OrderId, String ItemName, String Quantity, String TotalPrice, String OrderTime, String PlacedBy) {
        this.OrderId = OrderId;
        this.ItemName = ItemName;
        this.Quantity = Quantity;
        this.TotalPrice = TotalPrice;
        this.OrderTime = OrderTime;
        this.PlacedBy = PlacedBy;
    }
    
    
}
