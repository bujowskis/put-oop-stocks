package com.example.stockssimulator;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;

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

    @Override
    public void start(Stage stage) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(MainScreen.class.getResource("start-view.fxml"));
        Scene scene = new Scene(fxmlLoader.load(), 1280, 720);
        stage.setTitle("Stocks Simulator");
        stage.setScene(scene);
        stage.show();
    }

    /**
     * The main function of the whole application. It launches it and combines all the other functionalities to form the
     * final product
     * @param args
     */
    public static void main(String[] args) {
        launch();
    }

    // fixme - make properly
    public void launchSimulation() {
        this.simulation = new Simulation(this, 60, 0, 1, 0, 0);
        simulation.start();
    }

    public Simulation getSimulation() {
        return simulation;
    }
}