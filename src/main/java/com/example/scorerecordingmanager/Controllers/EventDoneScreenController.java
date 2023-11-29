package com.example.scorerecordingmanager.Controllers;

import com.example.scorerecordingmanager.DBHelper;
import com.example.scorerecordingmanager.SQLiteJDBC;
import com.example.scorerecordingmanager.SceneChanger;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.TableRow;
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
        VBox vbox = new VBox();
        vbox.setPadding(new Insets(0,0,0,30));
        vbox.setSpacing(15);

        try(PreparedStatement preparedStatement = SQLiteJDBC.getConnection().prepareStatement(SELECT_EVENT)){
            preparedStatement.setInt(1, DBHelper.getUser_id());
            ResultSet resultSet = preparedStatement.executeQuery();
            while(resultSet.next()){
                Button button = new Button();
                button.setId(resultSet.getString("eventID").toString());

                button.setOnAction(event -> {
                    DBHelper.setEvent(Integer.parseInt(button.getId()), button.getText());
                    SceneChanger.changeScene("teamDoneScreen");
                });

                button.setFont(new Font(17));
                button.setAlignment(Pos.CENTER_LEFT);
                button.setText(resultSet.getString("eventName"));
                button.getStyleClass().add("customButton");
                button.setPrefWidth(200.);
                vbox.getChildren().add(button);
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        playersField.getChildren().add(vbox);
    }
}
