package com.example.scorerecordingmanager.Controllers;

import com.example.scorerecordingmanager.DBHelper;
import com.example.scorerecordingmanager.SQLiteJDBC;
import com.example.scorerecordingmanager.SceneChanger;
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
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class PlayersScreenController {
    private final String INSERT_PLAYER = "INSERT INTO Player (playerName, teamID) VALUES (?, ?)";
    @FXML
    private AnchorPane playersField;

    @FXML
    private Button enterButton;

    @FXML
    private HBox hBox;

    @FXML
    private AnchorPane innerAnchorPane;

    @FXML
    private Label teamName;
    
    private List<TextField> playersNames = new ArrayList<>();
    private int teamID;

    @FXML
    void initialize() {
        int counter;

        if(DBHelper.getActiveTeam()==1){
            teamID = DBHelper.getTeam_id1();
            counter = DBHelper.getTeam_member1_count();
            teamName.setText(DBHelper.getTeamName1());
        }else{
            teamID = DBHelper.getTeam_id2();
            counter = DBHelper.getTeam_member2_count();
            teamName.setText(DBHelper.getTeamName2());
        }
        // adding player buttoms
        int added_counter = 0;
        //playersField
        VBox vbox = new VBox();
        vbox.setPadding(new Insets(0, 0, 0, 45));
        playersField.getChildren().add(vbox);

        for(int i = 0;i<Math.ceil(counter/2.);i++){
            HBox currHBOX = new HBox();
            currHBOX.setPadding(new Insets(15,0,0,0));
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
                
                currHBOX.setSpacing(25.0);
                currHBOX.getChildren().add(currTF);
                playersNames.add(currTF);
                added_counter++;
            }

            vbox.getChildren().add(currHBOX);
        }
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
        if(!isFilled()){
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Error");
            alert.setContentText("Enter all players names");
            alert.showAndWait();
            return;
        }

        for (TextField playersName : playersNames) {
            try(PreparedStatement preparedStatement = SQLiteJDBC.getConnection().prepareStatement(INSERT_PLAYER)){
                preparedStatement.setString(1, playersName.getText());
                preparedStatement.setInt(2, teamID);
                preparedStatement.executeUpdate();
            } catch (SQLException e) {
                throw new RuntimeException(e);
            }
        }
        SceneChanger.changeScene("eventCreateScreen");
    }

    @FXML
    void howToUse(ActionEvent event) {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("How to Use");
        alert.setHeaderText(null);
        alert.setContentText("Lorem Ipsum is simply dummy text of the printing and typesetting industry. Lorem Ipsum has been the industry's standard dummy text ever since the 1500s, when an unknown printer took a galley of type and scrambled it to make a type specimen book. It has survived not only five centuries, but also the leap into electronic typesetting, remaining essentially unchanged. It was popularised in the 1960s with the release of Letraset sheets containing Lorem Ipsum passages, and more recently with desktop publishing software like Aldus PageMaker including versions of Lorem Ipsum");
        alert.showAndWait();
    }
}
