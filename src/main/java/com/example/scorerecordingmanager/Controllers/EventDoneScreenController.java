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
import javafx.scene.control.Button;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.VBox;
import javafx.scene.text.Font;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;


public class EventDoneScreenController {
    private final String SELECT_EVENT = "SELECT * FROM Event WHERE userID = ?";
    @FXML
    private AnchorPane playersField;

    @FXML
    void goBack(ActionEvent event) {
        SceneChanger.changeScene("homeScreen");
    }

    @FXML
    void initialize() {
        /*
            Creating for each event button -> touch button -> getting more info about the team
            using for that vBox
        */

        // creating vBox
        VBox vbox = new VBox();
        vbox.setPadding(new Insets(0,0,0,30));
        vbox.setSpacing(15);

        // database read
        try(PreparedStatement preparedStatement = SQLiteJDBC.getConnection().prepareStatement(SELECT_EVENT)){
            preparedStatement.setInt(1, DBHelper.getUser_id());
            ResultSet resultSet = preparedStatement.executeQuery();
            //reading each event and creating button
            while(resultSet.next()){
                Button button = new Button();
                button.setId(resultSet.getString("eventID").toString());

                //action for each button
                button.setOnAction(event -> {
                    DBHelper.setEvent(Integer.parseInt(button.getId()), button.getText());
                    SceneChanger.changeScene("teamDoneScreen");
                });

                // button style
                button.setFont(new Font(17));
                button.setAlignment(Pos.CENTER_LEFT);
                button.setText(resultSet.getString("eventName"));
                button.getStyleClass().add("customButton");
                button.setPrefWidth(200.);

                // button -> vbox
                vbox.getChildren().add(button);
            }
        } catch (SQLException e) {
            LogManager.getLogger().error(e.getMessage(),e);
            throw new RuntimeException(e);
        }
        playersField.getChildren().add(vbox);
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