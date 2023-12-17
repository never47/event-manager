package com.example.scorerecordingmanager.Controllers;

import com.example.scorerecordingmanager.DBHelper;
import com.example.scorerecordingmanager.LogManager;
import com.example.scorerecordingmanager.SQLiteJDBC;
import com.example.scorerecordingmanager.SceneChanger;
import com.example.scorerecordingmanager.Tools.AlertIndicator;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.text.Font;

import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class PlayersScreenController {
    private final String INSERT_PLAYER = "INSERT INTO Player (playerName, teamID) VALUES (?, ?)";
    @FXML
    private AnchorPane playersField;
    @FXML
    private Label teamName;

    private List<TextField> playersNames = new ArrayList<>();
    private int teamID;

    @FXML
    void initialize() {
        int counter; // players count in this team

        // using this scene twice, some fields are different
        if(DBHelper.getActiveTeam()==1){
            teamID = DBHelper.getTeam_id1();
            counter = DBHelper.getTeam_member1_count();
            teamName.setText(DBHelper.getTeamName1());
        }else{
            teamID = DBHelper.getTeam_id2();
            counter = DBHelper.getTeam_member2_count();
            teamName.setText(DBHelper.getTeamName2());
        }

        // adding textFields

        int added_counter = 0; // checking for not to add additional textfield(appears in even count)

        // vbox -> scrollBar
        VBox vbox = new VBox();
        vbox.setPadding(new Insets(0, 0, 0, 45));
        playersField.getChildren().add(vbox);

        /*
               Using hbox for arranging textField (two labels in each hbox)
               and adding them in vBox

               So, cycle in cycle, the external one has Math.ceil(counter/2.)
               iterations and inner one just 2 iterations:
        */

        for(int i = 0;i<Math.ceil(counter/2.);i++){
            HBox currHBOX = new HBox();
            currHBOX.setPadding(new Insets(15,0,0,0));
            currHBOX.setSpacing(25.0);
            //Box.pad
            for(int j=0;j<2;j++){
                if(added_counter>=counter){
                    break;
                }
                TextField currTF = new TextField();
                currTF.getStyleClass().add("text");
                currTF.setAlignment(Pos.CENTER);
                currTF.setFont(new Font(15.0));
                currTF.setPromptText("Player " + (added_counter+1));

                currHBOX.getChildren().add(currTF);
                playersNames.add(currTF);
                added_counter++;
            }
            vbox.getChildren().add(currHBOX);
        }
        LogManager.getLogger().debug("Players textfields was created successfully");
    }

    private boolean isFilled(){
        for (TextField playersName : playersNames) {
            if(playersName.getText().isBlank()){
                return false;
            }
        }
        return true;
    }

    @FXML
    void addPlayers(ActionEvent event) {
        // checking if each text field is not empty
        if(!isFilled()){
            AlertIndicator.showAlarm(Alert.AlertType.ERROR, "Error",
                    "Enter all players names", false);
            return;
        }

        // adding each player in database
        for (TextField playersName : playersNames) {
            try(PreparedStatement preparedStatement = SQLiteJDBC.getConnection().prepareStatement(INSERT_PLAYER)){
                preparedStatement.setString(1, playersName.getText());
                preparedStatement.setInt(2, teamID);
                preparedStatement.executeUpdate();
                LogManager.getLogger().info("Players added to database");
            } catch (SQLException e) {
                LogManager.getLogger().error(e.getMessage(),e);
                throw new RuntimeException(e);
            }
        }
        SceneChanger.changeScene("eventCreateScreen");
    }

    @FXML
    void howToUse(ActionEvent event) {
        AlertIndicator.showFAQ();
    }
    @FXML
    public void signOut(ActionEvent actionEvent) {
        SceneChanger.removeScenes();
        DBHelper.resetUser();
        SceneChanger.changeScene("login");
    }
}