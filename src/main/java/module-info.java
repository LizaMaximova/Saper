module com.example.saper {
    requires javafx.controls;
    requires javafx.fxml;


    opens com.example.saper to javafx.fxml;
    exports com.example.saper;
    exports com.example.saper.controller;
    opens com.example.saper.controller to javafx.fxml;
}