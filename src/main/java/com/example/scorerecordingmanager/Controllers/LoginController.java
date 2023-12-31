package com.example.scorerecordingmanager.Controllers;

import com.example.scorerecordingmanager.DBHelper;
import com.example.scorerecordingmanager.LogManager;
import com.example.scorerecordingmanager.SQLiteJDBC;
import com.example.scorerecordingmanager.SceneChanger;
import com.example.scorerecordingmanager.Tools.AlertIndicator;
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
        // if text fields are empty -> error
        if(usernameField.getText().isBlank() && passwordField.getText().isBlank()){
            AlertIndicator.showAlarm(Alert.AlertType.ERROR, "ERROR",
                    "Please enter username and password",false);
            return;
        }
        // searching user in database if not found -> error
        try (PreparedStatement preparedStatement = SQLiteJDBC.getConnection().prepareStatement(LOGIN)) {
            preparedStatement.setString(1, usernameField.getText());
            preparedStatement.setString(2, passwordField.getText());

            ResultSet resultSet = preparedStatement.executeQuery();

            if(resultSet.next()){
                DBHelper.setUser_id(resultSet.getInt("userID"));
                SceneChanger.changeScene("homeScreen");
                LogManager.getLogger().info("User logged");
            }else{
                AlertIndicator.showAlarm(Alert.AlertType.ERROR, "ERROR",
                        "Incorrect username or password", false);
            }
        } catch (SQLException e) {
            LogManager.getLogger().error(e.getMessage(),e);
            throw new RuntimeException(e);
        }
    }
    @FXML
    void signUp(ActionEvent event) {
        SceneChanger.changeScene("signUp");
    }
}
