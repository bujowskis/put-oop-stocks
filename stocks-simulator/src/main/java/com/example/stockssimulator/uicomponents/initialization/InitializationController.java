package com.example.stockssimulator.uicomponents.initialization;

import com.example.stockssimulator.MainScreen;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.control.RadioButton;
import javafx.scene.control.TextField;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;

/**
 * Controller for Initialization Screen
 * Handles the input of initial parameters needed to launch the simulation
 */
public class InitializationController {
    private final MainScreen mainScreen; // reference used for scene transitions and parameters passing

    // UI components
    @FXML
    private TextField textFieldStocksNo;
    @FXML
    private TextField textFieldPathData;
    @FXML
    private TextField textFieldPathCompany;
    @FXML
    private RadioButton radioButtonStocksOnly;
    @FXML
    private RadioButton radioButtonStocksInvestorsNo;
    @FXML
    private RadioButton radioButtonStocksInvestorsByType;
    @FXML
    private VBox vBoxInvestorsInput;
    @FXML
    private TextField textFieldCautious;
    @FXML
    private TextField textFieldNormal;
    @FXML
    private TextField textFieldRisky;
    @FXML
    private TextField textFieldCrazy;
    @FXML
    private Label labelIllegalArgumentException;

    public InitializationController(MainScreen mainScreen) {
        this.mainScreen = mainScreen;
    }

    // functionalities
    @FXML
    protected void showSimplistic() {
        vBoxInvestorsInput.setVisible(false);
    }

    @FXML
    protected void showStocksAndInvestorsNo() {
        // one text field needed
        vBoxInvestorsInput.setVisible(true);
        textFieldCautious.setPromptText("no. of Investors");
        textFieldNormal.setVisible(false);
        textFieldRisky.setVisible(false);
        textFieldCrazy.setVisible(false);
    }

    @FXML
    protected void showStocksAndInvestorsByType() {
        // all 4 text fields needed
        vBoxInvestorsInput.setVisible(true);
        textFieldCautious.setPromptText("no. of Cautious");
        textFieldNormal.setVisible(true);
        textFieldRisky.setVisible(true);
        textFieldCrazy.setVisible(true);
    }

    @FXML
    protected void launchSimulation() {
        labelIllegalArgumentException.setVisible(false);
        int stocks_no;
        try {
            stocks_no = Integer.parseInt(textFieldStocksNo.getText());
            if (stocks_no <= 0) {
                throw new NumberFormatException();
            }
        } catch (NumberFormatException e) {
            labelIllegalArgumentException.setText("Number of stocks must be int greater than 0!");
            labelIllegalArgumentException.setVisible(true);
            return;
        }
        if (textFieldPathData.getText() == null || textFieldPathCompany.getText() == null
                || textFieldPathData.getText().isEmpty() || textFieldPathCompany.getText().isEmpty()) {
            labelIllegalArgumentException.setText("Data and Company.csv paths cannot be empty or null!");
            labelIllegalArgumentException.setVisible(true);
            return;
        }
        if (radioButtonStocksOnly.isSelected()) {
            try {
                mainScreen.launchSimulation(textFieldPathData.getText(), textFieldPathCompany.getText(), stocks_no);
            } catch (IllegalArgumentException e) {
                labelIllegalArgumentException.setText("(most probably) paths do not contain needed files");
                labelIllegalArgumentException.setVisible(true);
            } catch (IOException e) {
                e.printStackTrace();
                throw new Error("Error while switching screens");
            }
        } else if (radioButtonStocksInvestorsNo.isSelected()) {
            int investors_no;
            try {
                investors_no = Integer.parseInt(textFieldCautious.getText());
                if (investors_no <= 0) {
                    throw new NumberFormatException();
                }
            } catch (NumberFormatException e) {
                labelIllegalArgumentException.setText("Number of Investors must be int greater than 0!");
                labelIllegalArgumentException.setVisible(true);
                return;
            }
            try {
                mainScreen.launchSimulation(textFieldPathData.getText(), textFieldPathCompany.getText(), stocks_no,
                        investors_no);
            } catch (IllegalArgumentException e) {
                labelIllegalArgumentException.setText("(most probably) paths do not contain needed files");
                labelIllegalArgumentException.setVisible(true);
            } catch (IOException e) {
                e.printStackTrace();
                throw new Error("Error while switching screens");
            }
        } else if (radioButtonStocksInvestorsByType.isSelected()) {
            int cautious;
            int normal;
            int risky;
            int crazy;
            try {
                cautious = Integer.parseInt(textFieldCautious.getText());
                normal = Integer.parseInt(textFieldNormal.getText());
                risky = Integer.parseInt(textFieldRisky.getText());
                crazy = Integer.parseInt(textFieldCrazy.getText());
                if (cautious <= 0 || normal <= 0 || risky <= 0 || crazy <= 0) {
                    throw new NumberFormatException();
                }
            } catch (NumberFormatException e) {
                labelIllegalArgumentException.setText("All numbers of Investors must be int greater than 0!");
                labelIllegalArgumentException.setVisible(true);
                return;
            }
            try {
                mainScreen.launchSimulation(textFieldPathData.getText(), textFieldPathCompany.getText(), stocks_no,
                        cautious, normal, risky, crazy);
            } catch (IllegalArgumentException e) {
                labelIllegalArgumentException.setText("(most probably) paths do not contain needed files");
                labelIllegalArgumentException.setVisible(true);
            } catch (IOException e) {
                e.printStackTrace();
                throw new Error("Error while switching screens");
            }
        } else {
            throw new Error("the impossible happened");
        }
        // if everything went well, change scenes

    }
}