package com.example.scorerecordingmanager.Controllers;

import com.example.scorerecordingmanager.DBHelper;
import com.example.scorerecordingmanager.SceneChanger;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;

public class HomeScreenController {
    @FXML
    void createEvent(ActionEvent event) {
        SceneChanger.changeScene("eventCreateScreen");
        DBHelper.setNull();
    }

    @FXML
    void howToUse(ActionEvent event) {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("How to Use");
        alert.setHeaderText(null);
        alert.setContentText("Lorem Ipsum is simply dummy text of the printing and typesetting industry. Lorem Ipsum has been the industry's standard dummy text ever since the 1500s, when an unknown printer took a galley of type and scrambled it to make a type specimen book. It has survived not only five centuries, but also the leap into electronic typesetting, remaining essentially unchanged. It was popularised in the 1960s with the release of Letraset sheets containing Lorem Ipsum passages, and more recently with desktop publishing software like Aldus PageMaker including versions of Lorem Ipsum");
        alert.showAndWait();
    }

    public void showCreatedEvents(ActionEvent actionEvent) {
        SceneChanger.changeScene("eventDoneScreen");
        DBHelper.setNull();
    }
}
