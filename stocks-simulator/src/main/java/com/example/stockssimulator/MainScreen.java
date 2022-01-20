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
 *
 * FRONTEND
 * todo - starting welcome screen w. option for a tutorial see through
 *  - option to turn that off "I'm fine, thanks"
 *  - show what can be found and where
 *
 * todo - options menu
 *  - preferences - fullscreen, height and width specification, font sizes and such
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
    }

    /**
     * The main function of the whole application. It launches it and combines all the other functionalities to form the
     * final product
     * @param args (irrelevant, todo - may be used for development/debugging process)
     */
    public static void main(String[] args) {
        launch();
    }

    public Simulation getSimulation() {
        return simulation;
    }
}