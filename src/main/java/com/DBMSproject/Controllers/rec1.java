
package com.DBMSproject;

import com.jfoenix.controls.JFXButton;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;


public class rec1 implements Initializable {

    
    @FXML
    private TableView<ModelSpecial> OrderedTable;

    @FXML
    private TableColumn<ModelSpecial, String> Col_ItemName1;

    @FXML
    private TableColumn<ModelSpecial, String> Col_Quantity1;

    @FXML
    private TableColumn<ModelSpecial, String> Col_Price;

    @FXML
    private TableColumn<ModelSpecial, String> Col_TotalPrice;

    @FXML
    private JFXButton sav_PDF;

    @FXML
    private Label TotalPriceSum;

    @FXML
    void saveasPDF(ActionEvent event) {

    }

    ObservableList<ModelSpecial> list ;
    @Override
    public void initialize(URL url, ResourceBundle rb) {

        list = AppModel2.getList();
        createTable();
        CalculateTotalPrice();
     
    }
//     String ItemName, Price,Quantity,category,totalPrice;
    
    void createTable(){
        Col_ItemName1.setCellValueFactory(new PropertyValueFactory<>("ItemName"));
        Col_Price.setCellValueFactory(new PropertyValueFactory<>("Price"));
        Col_Quantity1.setCellValueFactory(new PropertyValueFactory<>("Quantity"));
        Col_TotalPrice.setCellValueFactory(new PropertyValueFactory<>("totalPrice"));
        
        OrderedTable.setItems(list);
        
    }   
    
   void  CalculateTotalPrice(){
        Float TotalPrice = 0.0f;
        for(int i= 0;i <OrderedTable.getItems().size();i++ ){
        TotalPrice += Float.valueOf(OrderedTable.getItems().get(i).totalPrice);
        }
        TotalPriceSum.setText(String.valueOf(TotalPrice));
    }
    
    
}
