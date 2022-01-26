package com.example.stockssimulator;

import com.example.stockssimulator.uicomponents.initialization.InitializationController;
import com.example.stockssimulator.uicomponents.simulation.SimulationViewController;
import javafx.application.Application;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import javafx.stage.WindowEvent;

import java.io.IOException;
import java.net.URL;
import java.util.Objects;

/*
 * BACKEND
 * todo - do the documentation in nice way
 * todo - force-stop simulation
 *
 * FRONTEND
 * todo - add listing based on Investor type (Cautious, Normal, Risky, Crazy separately, or show all)
 *
 * (potentially) FUTURE WORK
 * - add user interactive experience - place to play around trading
 * - "tutorial" option - guide to see the features and options
 * - nicer frontend
 *      - user preferences - fullscreen, height and width, font sizes
 */

/**
 * Handles the main screen of the application
 */
public class MainScreen extends Application {
    private Simulation simulation = null;
    private Stage stage; // DO NOT CLOSE

    @Override
    public void start(Stage stage) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(MainScreen.class.getResource("start-view.fxml"));
        fxmlLoader.setControllerFactory(c -> new InitializationController(this));
        Scene scene = new Scene(fxmlLoader.load(), 1280, 720);
        this.stage = stage;
        stage.setTitle("Stocks Simulator");
        stage.setScene(scene);
        stage.show();
    }

    /**
     * The main function of the whole application. It launches it and combines all the other functionalities to form the
     * final product
     */
    public static void main(String[] args) {
        launch();
    }

    public void launchSimulation(String path_data, String path_company, int stocks_no) throws IllegalArgumentException,
            IOException {
        this.simulation = new Simulation(this, path_data, path_company, stocks_no);
        switchScreen();
        simulation.start();
    }

    public void launchSimulation(String path_data, String path_company, int stocks_no, int investors_no)
            throws IllegalArgumentException, IOException {
        this.simulation = new Simulation(this, path_data, path_company, stocks_no, investors_no);
        switchScreen();
        simulation.start();
    }

    public void launchSimulation(String path_data, String path_company, int stocks_no,
                                 int cautious, int normal, int risky, int crazy)
            throws IllegalArgumentException, IOException {
        // try launching the simulation (may throw IAE)
        this.simulation = new Simulation(this, path_data, path_company, stocks_no, cautious, normal, risky, crazy);
        switchScreen();
        simulation.start();
    }

    private void switchScreen() throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(MainScreen.class.getResource("simulation-view.fxml"));
        fxmlLoader.setControllerFactory(c -> new InitializationController(this));
        Scene scene = new Scene(fxmlLoader.load(), 1280, 720);
        stage.setScene(scene);
    }

    public Simulation getSimulation() {
        return simulation;
    }

    public Stage getStage() {
        return stage;
    }
}