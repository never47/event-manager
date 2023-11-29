package com.example.scorerecordingmanager.Controllers;

import com.example.scorerecordingmanager.DBHelper;
import com.example.scorerecordingmanager.SQLiteJDBC;
import com.example.scorerecordingmanager.SceneChanger;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class LoginController {
    private final String LOGIN = "SELECT * FROM User WHERE userName = ? AND password = ?";
    @FXML
    private PasswordField passwordField;
    @FXML
    private TextField usernameField;

    @FXML
    void login(ActionEvent event) {
        try (PreparedStatement preparedStatement = SQLiteJDBC.getConnection().prepareStatement(LOGIN)) {
            preparedStatement.setString(1, usernameField.getText());
            preparedStatement.setString(2, passwordField.getText());
            ResultSet resultSet = preparedStatement.executeQuery();

            if(resultSet.next()){
                DBHelper.setUser_id(resultSet.getInt("userID"));
                SceneChanger.changeScene("homeScreen");
            }else{
                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.setTitle("Error");
                alert.setContentText("Incorrect username or password");
                alert.showAndWait();
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
    @FXML
    void signUp(ActionEvent event) {
        SceneChanger.changeScene("signUp");
    }
}
