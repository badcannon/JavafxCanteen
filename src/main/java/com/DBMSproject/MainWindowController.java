package com.DBMSproject;

import java.io.IOException;
import javafx.fxml.FXML;

public class MainWindowController {

    @FXML
    private void switchToPrimary() throws IOException {
        MainApp.setRoot("Views/LoginWindow");
    }
}