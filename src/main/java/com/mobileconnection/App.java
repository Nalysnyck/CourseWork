package com.mobileconnection;

import com.mobileconnection.controller.InitializeStage;
import javafx.application.Application;
import javafx.stage.Stage;

public class App extends Application {
    public static void main(String[] args) {
        Application.launch();
    }

    @Override
    public void start(Stage stage) {
        new InitializeStage(stage);
    }
}