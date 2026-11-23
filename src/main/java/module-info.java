module com.example.demo5 {
    requires javafx.controls;
    requires javafx.fxml;
    requires javafx.media;
    requires java.sql;


    opens com.example.demo5 to javafx.fxml;
    exports com.example.demo5;
    exports com.example.demo5.view;
    opens com.example.demo5.view to javafx.fxml;
    exports com.example.demo5.models;
    opens com.example.demo5.models to javafx.fxml;
    exports com.example.demo5.repository;
    opens com.example.demo5.repository to javafx.fxml;
}