package com.example.scorerecordingmanager.Controllers;

import com.example.scorerecordingmanager.DBHelper;
import com.example.scorerecordingmanager.SQLiteJDBC;
import com.example.scorerecordingmanager.SceneChanger;
import com.example.scorerecordingmanager.Tools.AlertIndicator;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.HBox;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class EventCreateScreenController {
    private final String INSERT_EVENT = "INSERT INTO Event (eventName, userID) VALUES (?, ?)";
    private final String GET_LAST_INSERTED_ID = "SELECT last_insert_rowid()";
    private final String INSERT_TEAM = "INSERT INTO Team (teamName, teamScore, eventID) VALUES (?, ?, ?)";


    @FXML
    private TextField eventNameField;


    @FXML
    private HBox hBox;

    @FXML
    private AnchorPane innerAnchorPane;

    @FXML
    private Button playerButton1;

    @FXML
    private Button playerButton2;

    @FXML
    private Spinner<Integer> spinner1;

    @FXML
    private Spinner<Integer> spinner2;


    @FXML
    private TextField teamName1;

    @FXML
    private TextField teamName2;

    @FXML
    void initialize() {
        // spinner1
        SpinnerValueFactory<Integer> valueFactory1 = new SpinnerValueFactory.IntegerSpinnerValueFactory(1,10);
        valueFactory1.setValue(1);
        spinner1.setValueFactory(valueFactory1);

        //spinner2
        SpinnerValueFactory<Integer> valueFactory2 = new SpinnerValueFactory.IntegerSpinnerValueFactory(1,10);
        valueFactory2.setValue(1);
        spinner2.setValueFactory(valueFactory2);
    }

    private void createEvent(){
        try (PreparedStatement eventStatement = SQLiteJDBC.getConnection().prepareStatement(INSERT_EVENT);
             PreparedStatement getIdStatement = SQLiteJDBC.getConnection().prepareStatement(GET_LAST_INSERTED_ID);) {

            eventStatement.setString(1, eventNameField.getText());
            eventStatement.setInt(2, DBHelper.getUser_id());

            if(eventStatement.executeUpdate() > 0){
                try (ResultSet resultSet = getIdStatement.executeQuery()) {
                    if (resultSet.next()) {
                        DBHelper.setEvent(resultSet.getInt(1), eventNameField.getText());
                    }
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    private void createTeam(String teamName, int teamNumber){
        try (PreparedStatement teamStatement = SQLiteJDBC.getConnection().prepareStatement(INSERT_TEAM);
             PreparedStatement getIdStatement = SQLiteJDBC.getConnection().prepareStatement(GET_LAST_INSERTED_ID);) {

            teamStatement.setString(1, teamName);
            teamStatement.setInt(2, 0);
            teamStatement.setInt(3, DBHelper.getEvent_id());

            if(teamStatement.executeUpdate() > 0){
                try (ResultSet resultSet = getIdStatement.executeQuery()) {
                    if (resultSet.next()) {
                        if(teamNumber==1){
                            DBHelper.setTeam1(resultSet.getInt(1), teamName1.getText());
                            DBHelper.setTeam_member1_count(spinner1.getValue());
                            DBHelper.setActiveTeam(1);
                            SceneChanger.changeScene("playerScreenController1");
                        }else{
                            DBHelper.setTeam2(resultSet.getInt(1), teamName2.getText());
                            DBHelper.setTeam_member2_count(spinner2.getValue());
                            DBHelper.setActiveTeam(2);
                            SceneChanger.changeScene("playerScreenController2");
                        }
                    }
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @FXML
    void createFirstTeam(ActionEvent event) {
        if(eventNameField.getText().isBlank()
                || teamName1.getText().isBlank()){
            AlertIndicator.showAlarm(Alert.AlertType.ERROR, "Information",
                    "Enter event and team nam", false);
            return;
        }

        if(DBHelper.getEvent_id()==-1){
            createEvent();
        }

        if(DBHelper.getTeam_id1()==-1){
            createTeam(teamName1.getText(),1);
        }else{
            AlertIndicator.showAlarm(Alert.AlertType.ERROR, "Information",
                    "You already register first team", false);
        }
    }

    @FXML
    void CreateSecondTeam(ActionEvent event) {
        if(eventNameField.getText().isBlank()
            || teamName2.getText().isBlank()){
            AlertIndicator.showAlarm(Alert.AlertType.ERROR, "Information",
                    "Enter event and team name", false);
            return;
        }

        if(DBHelper.getEvent_id()==-1){
            createEvent();
        }

        if(DBHelper.getTeam_id2()==-1){
            createTeam(teamName2.getText(),2);
        }else{
            AlertIndicator.showAlarm(Alert.AlertType.ERROR, "Information",
                    "You already register second team", false);
        }
    }

    @FXML
    void StartEvent(ActionEvent event) {
        if(DBHelper.getTeam_id1()==-1 && DBHelper.getTeam_id2()==-1){
            AlertIndicator.showAlarm(Alert.AlertType.ERROR, "ERROR",
                    "Create teams", false);
        }else{
            SceneChanger.changeScene("eventScreen");
        }
    }

    @FXML
    public void signOut(ActionEvent actionEvent) {
        SceneChanger.removeScenes();
        DBHelper.resetUser();
        SceneChanger.changeScene("login");
    }

    @FXML
    void howToUse(ActionEvent event) {
        AlertIndicator.showFAQ();
    }
}
