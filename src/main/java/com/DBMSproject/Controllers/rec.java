/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.DBMSproject;

import com.jfoenix.controls.JFXButton;
import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;

/**
 *
 * @author shree
 */
public class rec implements Initializable{

//    rec(ObservableList<SoldTable> list){
//        this.List = list;
//    }
    
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        try {
            initial();
        } catch (Exception ex) {
            Logger.getLogger(rec.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
       
    
        @FXML
    private TableView<SoldTable> OrderedTable;

    @FXML
    private TableColumn<SoldTable, String> Col_ItemName1;

    @FXML
    private TableColumn<SoldTable, String> Col_Price;

    @FXML
    private TableColumn<SoldTable, String> Col_Quantity1;

    @FXML
    private TableColumn<SoldTable, String> Col_TotalPrice;

    @FXML
    private JFXButton sav_PDF;

    @FXML
    private Label TotalPriceSum;

    @FXML
    void saveasPDF(ActionEvent event) {
    }
    ObservableList<SoldTable> List;
    void initial() throws IOException {
        System.out.println("Here !?");
        Orders ord = new Orders();
        System.out.println("Lol ?");
        List =  AppModel.getList();
        System.out.println(List.size());
        populateTable();
        CalculateSoldPrice();
        
    }
     

    private void CalculateSoldPrice() {
        
        Float TotalPrice = 0.0f;
        for(int i= 0;i <OrderedTable.getItems().size();i++ ){
        TotalPrice += Float.valueOf(OrderedTable.getItems().get(i).TotalPrice);
        }
        TotalPriceSum.setText(String.valueOf(TotalPrice));
    }
    private void populateTable() {
        System.out.println("Working?");
        Col_ItemName1.setCellValueFactory(new PropertyValueFactory<>("Name"));
        Col_Quantity1.setCellValueFactory(new PropertyValueFactory<>("Quantity"));
        Col_Price.setCellValueFactory(new PropertyValueFactory<>("Price"));
        Col_TotalPrice.setCellValueFactory(new PropertyValueFactory<>("TotalPrice"));
        OrderedTable.getItems().addAll(List);
    }
    
    
    
}
