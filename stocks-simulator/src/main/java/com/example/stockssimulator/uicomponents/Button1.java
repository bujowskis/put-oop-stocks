package com.example.stockssimulator.uicomponents;

import javafx.fxml.FXML;
import javafx.scene.control.Label;

public class Button1 {
    @FXML
    private Label welcomeText;

    @FXML
    protected void onHelloButtonClick() {
        welcomeText.setText("Welcome to JavaFX Application!");
    }
}