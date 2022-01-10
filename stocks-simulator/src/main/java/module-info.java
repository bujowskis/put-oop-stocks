module com.example.stockssimulator {
    requires javafx.controls;
    requires javafx.fxml;


    opens com.example.stockssimulator to javafx.fxml;
    exports com.example.stockssimulator;
}