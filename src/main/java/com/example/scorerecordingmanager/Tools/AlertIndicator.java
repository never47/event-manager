package com.example.scorerecordingmanager.Tools;

import com.example.scorerecordingmanager.LogManager;
import javafx.scene.control.Alert;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
import javafx.scene.layout.AnchorPane;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;

/*
    Using this class for alarm messages
 */
public class AlertIndicator {
    public static void showAlarm(Alert.AlertType alertType, String Title,
                                 String Content, boolean isRed){
        // creating alert
        Alert alert = new Alert(alertType);
        alert.setTitle(Title);
        alert.setContentText(Content);

        //style set
        alert.getDialogPane().getStylesheets().add(AlertIndicator.class.
                getResource("/com/example/scorerecordingmanager/css/styles.css").toExternalForm());


        // using red text for registration page(dont need more colours)
        if(isRed){
            alert.getDialogPane().lookup(".content").setStyle("-fx-text-fill: red;");
        }

        alert.showAndWait();
        LogManager.getLogger().debug("showAlarm was called");
    }

    public static void showFAQ(){
        // alert creating
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("How to Use");
        alert.setHeaderText("How To Use");
        alert.setWidth(500);
        alert.setHeight(500);

        // text read
        String content = null;
        try {
            content = new String(Files.readAllBytes(Paths.get("src/main/resources/com/example/scorerecordingmanager/faq.txt")));
        } catch (IOException e) {
            LogManager.getLogger().error(e.getMessage(),e);
            throw new RuntimeException(e);
        }

        // label create
        Label text = new Label(content);
        text.setWrapText(true);
        text.setMaxWidth(500);

        // anchor create
        AnchorPane anchorPane = new AnchorPane();
        anchorPane.getChildren().add(text);

        //scrollpane
        ScrollPane sp = new ScrollPane();
        sp.setHbarPolicy(ScrollPane.ScrollBarPolicy.NEVER);
        sp.setVbarPolicy(ScrollPane.ScrollBarPolicy.ALWAYS);
        sp.setContent(anchorPane);

        alert.getDialogPane().setContent(sp);
        alert.showAndWait();
        LogManager.getLogger().debug("showFaq was called");
    }
}
