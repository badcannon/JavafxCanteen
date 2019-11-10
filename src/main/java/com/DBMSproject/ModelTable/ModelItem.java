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
public class ModelItem {
    String Item,Variable;

    public ModelItem(String Item, String Variable) {
        this.Item = Item;
        this.Variable = Variable;
    }

    public String getItem() {
        return Item;
    }

    public void setItem(String Item) {
        this.Item = Item;
    }

    public String getVariable() {
        return Variable;
    }

    public void setVariable(String Variable) {
        this.Variable = Variable;
    }
    
}
