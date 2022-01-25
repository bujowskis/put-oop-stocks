module com.example.stockssimulator {
    requires javafx.controls;
    requires javafx.fxml;


    opens com.example.stockssimulator to javafx.fxml;
    exports com.example.stockssimulator;
    exports com.example.stockssimulator.investors;
    opens com.example.stockssimulator.investors to javafx.fxml;
    exports com.example.stockssimulator.uicomponents.initialization;
    opens com.example.stockssimulator.uicomponents.initialization to javafx.fxml;
}