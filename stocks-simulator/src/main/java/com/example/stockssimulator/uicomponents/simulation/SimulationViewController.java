package com.example.stockssimulator.uicomponents.simulation;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.chart.LineChart;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.VBox;

/**
 * Combines all main UI components into a single whole
 */
public class SimulationViewController {
    public GridPane gridPaneMain;
    public VBox vBoxStocks;
    public ListView listViewStocks;
    public VBox vBoxInvestors;
    public ListView listViewInvestors;
    public VBox vBoxStockOverview;
    public VBox vBoxInvestorOverview;
    public VBox vBoxOwnedStocks;
    public ListView listViewOwnedStocks;
    public VBox vBoxStockChart;
    public LineChart lineChartStock;
    public VBox vBoxGlobalMenu;
    public Label labelCurrentTime;
    public Label labelTimeRate;
    public Button buttonTimeRate1;
    public Button buttonTimeRate5;
    public Button buttonTimeRate10;
    public Button buttonTimeRate30;
    public Button buttonTimeRate60;
    public Button buttonTimeRate120;
    public Button buttonTimeRate300;

    // todo
    public SimulationViewController() {}

    /*
    // GLOBAL MENU
    // todo - these could be done as one function, with single argument (?)
    @FXML
    public void setTimeRate1() {
        // todo
    }
    @FXML
    protected void setTimeRate5() {
        // todo
    }
    @FXML
    protected void setTimeRate10() {
        // todo
    }
    @FXML
    protected void setTimeRate30(){
        // todo
    }
    @FXML
    protected void setTimeRate60() {
        // todo
    }
    @FXML
    protected void setTimeRate120() {
        // todo
    }
    @FXML
    protected void setTimeRate300() {
        // todo
    }
     */
}
