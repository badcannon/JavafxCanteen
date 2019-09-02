module com.DBMSproject {
    requires javafx.controls;
    requires javafx.fxml;
    requires java.sql;

    opens com.DBMSproject to javafx.fxml;
    exports com.DBMSproject;
    requires javafx.base;
    requires javafx.graphics;
    requires com.jfoenix;
}
