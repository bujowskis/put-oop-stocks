package com.example.stockssimulator;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;

/*
 * BACKEND
 * todo - random seed by current date or smth
 * todo - do the documentation in nice way
 * todo - add user interactive experience (once simulated done)
 * todo - force-stop simulation
 *
 * FRONTEND
 * todo - starting welcome screen w. option for a tutorial see through
 *  - option to turn that off "I'm fine, thanks"
 *  - show what can be found and where
 * todo - options menu
 *  - preferences - fullscreen, height and width specification, font sizes and such
 * todo - add listing based on Investor type (Cautious, Normal, Risky, Crazy separately, or show all)
 */

/**
 * Handles the main screen of the application
 */
public class MainScreen extends Application {
    private Simulation simulation = null;

    @Override
    public void start(Stage stage) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(MainScreen.class.getResource("main-view.fxml"));
        Scene scene = new Scene(fxmlLoader.load(), 1280, 720);
        stage.setTitle("Stocks Simulator");
        stage.setScene(scene);
        stage.show();
        launchSimulation(); // todo - remove after fixme in function
    }

    /**
     * The main function of the whole application. It launches it and combines all the other functionalities to form the
     * final product
     * @param args (irrelevant, todo - may be used for development/debugging process)
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