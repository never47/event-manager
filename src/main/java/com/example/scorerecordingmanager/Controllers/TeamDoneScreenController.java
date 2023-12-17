package com.example.scorerecordingmanager.Controllers;

import com.example.scorerecordingmanager.DBHelper;
import com.example.scorerecordingmanager.LogManager;
import com.example.scorerecordingmanager.SQLiteJDBC;
import com.example.scorerecordingmanager.SceneChanger;
import com.example.scorerecordingmanager.Tools.AlertIndicator;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.image.ImageView;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class TeamDoneScreenController {
    private final String SELECT_TEAM = "SELECT teamID, teamName, teamScore FROM Team WHERE eventID = ?";
    @FXML
    private Label eventName;
    @FXML
    private Label teamName1;

    @FXML
    private Label teamName2;
    @FXML
    private ImageView medal1;

    @FXML
    private ImageView medal2;

    private int teamScore1;
    private int teamScore2;


    @FXML
    void initialize() {
        // database read
        try(PreparedStatement preparedStatement = SQLiteJDBC.getConnection().prepareStatement(SELECT_TEAM)){
            preparedStatement.setInt(1, DBHelper.getEvent_id());
            ResultSet resultSet = preparedStatement.executeQuery();

            resultSet.next();

            // team1 info read and save in helper class
            DBHelper.setTeam1(resultSet.getInt("teamID"),resultSet.getString("teamName"));
            teamScore1 = resultSet.getInt("teamScore");

            resultSet.next();

            // team2 info read and save in helper class
            DBHelper.setTeam2(resultSet.getInt("teamID"),resultSet.getString("teamName"));
            teamScore2 = resultSet.getInt("teamScore");
        } catch (SQLException e) {
            LogManager.getLogger().error(e.getMessage(),e);
            throw new RuntimeException(e);
        }

        // medal show logic
        if(teamScore1>teamScore2){
            medal1.setOpacity(100.);
        }else if(teamScore1<teamScore2){
            medal2.setOpacity(100.);
        }else{
            medal1.setOpacity(100.);
            medal2.setOpacity(100.);
        }

        // text field info add
        eventName.setText(DBHelper.getEventName());
        teamName1.setText(DBHelper.getTeamName1());
        teamName2.setText(DBHelper.getTeamName2());
        LogManager.getLogger().debug("Team info read from database completed successfully");
    }
    /*
        Using in this follow two methods only one scene,
        the argument that makes them difference is activeTeamNumber
     */
    @FXML
    void showSecondTeam(ActionEvent event) {
        DBHelper.setActiveTeam(2);
        SceneChanger.changeScene("playersDoneScreen");
    }

    @FXML
    void showFirstTeam(ActionEvent event) {
        DBHelper.setActiveTeam(1);
        SceneChanger.changeScene("playersDoneScreen");
    }

    @FXML
    public void signOut(ActionEvent actionEvent) {
        SceneChanger.removeScenes();
        DBHelper.resetUser();
        SceneChanger.changeScene("login");
    }

    @FXML
    void goBack(ActionEvent event) {
        SceneChanger.changeScene("eventDoneScreen");
    }

    @FXML
    void howToUse(ActionEvent event) {
        AlertIndicator.showFAQ();
    }
}