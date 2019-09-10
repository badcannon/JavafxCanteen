 
package com.DBMSproject.Enums;
 
public enum StageManager {
    
    
   ORDERS("Views/Orders.fxml"),MAINWINDOW("Views/MainWindow"),LOGIN("Views/LoginWindow"),DRAWER("Views/DrawerContent.fxml"),
   STATS("Views/Stats.fxml"),SETTINGS("Views/Settings.fxml");
   
   private String link;

    private StageManager(String link) {
        this.link = link;
    }
    
    @Override
    public String toString(){
        return link;
    }
    
    public static String Change(String Word){
        return StageManager.valueOf(Word).toString();
    }
    
}

