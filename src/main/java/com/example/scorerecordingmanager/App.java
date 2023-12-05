package com.example.scorerecordingmanager;

import javafx.application.Application;
import javafx.stage.Stage;
import java.io.IOException;

public class App extends Application {
    @Override
    public void start(Stage stage) throws IOException {
        stage.setResizable(false);
        stage.setTitle("Score Recording Manager");
        SceneChanger.setStage(stage);
        SceneChanger.changeScene("login");
    }

    public static void main(String[] args) {
        launch();
    }
}