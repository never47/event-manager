package com.example.scorerecordingmanager.Controllers;

import com.example.scorerecordingmanager.DBHelper;
import com.example.scorerecordingmanager.SceneChanger;
import com.example.scorerecordingmanager.Tools.AlertIndicator;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;

public class HomeScreenController {
    @FXML
    void createEvent(ActionEvent event) {
        SceneChanger.changeScene("eventCreateScreen");
        DBHelper.resetEvent();
    }

    @FXML
    void howToUse(ActionEvent event) {
        AlertIndicator.showFAQ();
    }

    public void showCreatedEvents(ActionEvent actionEvent) {
        SceneChanger.changeScene("eventDoneScreen");
        DBHelper.resetEvent();
    }

    @FXML
    public void signOut(ActionEvent actionEvent) {
        SceneChanger.removeScenes();
        DBHelper.resetUser();
        SceneChanger.changeScene("login");
    }
}
