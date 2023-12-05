package com.example.scorerecordingmanager.Controllers;

import com.example.scorerecordingmanager.DBHelper;
import com.example.scorerecordingmanager.SQLiteJDBC;
import com.example.scorerecordingmanager.SceneChanger;
import com.example.scorerecordingmanager.Tools.AlertIndicator;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.Label;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.text.Font;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class PlayersDoneScreenController {
    private final String SELECT_PLAYER = "SELECT playerName FROM Player WHERE teamID = ?";
    private final String PLAYER_COUNT = "SELECT COUNT(*) FROM PLAYER WHERE teamID = ?";
    @FXML
    private AnchorPane playersField;
    @FXML
    private Label teamName;

    private int teamID;

    @FXML
    void initialize() {
        /*
            This scene is used twice: for team1 and team2, so I need to understand which one
            is calling scene now: (using getActiveTeam() in DBHelper class)
         */
        if(DBHelper.getActiveTeam()==1){
            teamID = DBHelper.getTeam_id1();
            teamName.setText(DBHelper.getTeamName1());
        }else{
            teamName.setText(DBHelper.getTeamName2());
            teamID = DBHelper.getTeam_id2();
        }

        // reading from database
        try(PreparedStatement countStatement = SQLiteJDBC.getConnection().prepareStatement(PLAYER_COUNT);
            PreparedStatement selectStatement = SQLiteJDBC.getConnection().prepareStatement(SELECT_PLAYER)){

            // getting players count (need it for dynamic page in cycle)
            countStatement.setInt(1, teamID);
            ResultSet countRS = countStatement.executeQuery();
            int counter = countRS.getInt(1);

            // getting playerNames
            selectStatement.setInt(1, teamID);
            ResultSet resultSet = selectStatement.executeQuery();
            VBox vBox = new VBox();
            vBox.setPadding(new Insets(0, 0, 0, 30));

            /*
                Using hbox for arranging labels (two labels in each hbox)
                and adding them in vBox

                So, cycle in cycle, the external one has Math.ceil(counter/2.)
                iterations and inner one just 2 iterations:
             */
            for(int i = 0;i<Math.ceil(counter/2.);i++){
                HBox currHBOX = new HBox();
                currHBOX.setSpacing(25);
                currHBOX.setPadding(new Insets(20,0,0,0));
                for(int j = 0; j<2;j++){
                    resultSet.next();
                    // if labels count is odd (appearing empty label at the end)
                    if(i*2+j>=counter){
                        break;
                    }
                    //label creating
                    Label currLabel = new Label();
                    currLabel.setText(resultSet.getString("playerName"));
                    currLabel.getStyleClass().add("text");
                    currLabel.setAlignment(Pos.CENTER);
                    currLabel.setPrefSize(200,40);
                    currLabel.setFont(new Font(15.));
                    //add it to hBox
                    currHBOX.getChildren().add(currLabel);
                }
                //add it to vBox
                vBox.getChildren().add(currHBOX);
            }
            //and then add vBox in scrollPane
            playersField.getChildren().add(vBox);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @FXML
    public void signOut(ActionEvent actionEvent) {
        SceneChanger.removeScenes();
        DBHelper.resetUser();
        SceneChanger.changeScene("login");
    }

    @FXML
    void goBack(ActionEvent event) {
        SceneChanger.changeScene("teamDoneScreen");
    }

    @FXML
    void howToUse(ActionEvent event) {
        AlertIndicator.showFAQ();
    }
}
