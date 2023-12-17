package com.example.scorerecordingmanager.Controllers;

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

public class SignUpController {
    private final String SIGN_UP = "INSERT INTO User (userName, Password) VALUES (?, ?)";
    private final String GET_LAST_INSERTED_ID = "SELECT last_insert_rowid()";
    private final String CHECK_USER = "SELECT * FROM User WHERE userName = ?";
    @FXML
    private PasswordField passwordField;
    @FXML
    private TextField usernameField;

    @FXML
    void login(ActionEvent event) {
        SceneChanger.changeScene("login");
    }

    @FXML
    void signUp(ActionEvent event) {
        try {
            // if text fields are empty -> error
            if(usernameField.getText().isBlank() && passwordField.getText().isBlank()){
                AlertIndicator.showAlarm(Alert.AlertType.ERROR, "ERROR",
                        "Please enter username and password",false);
                return;
            }

            // checking if this username exists in database
            try (PreparedStatement preparedStatement = SQLiteJDBC.getConnection().prepareStatement(CHECK_USER)) {
                preparedStatement.setString(1, usernameField.getText());
                ResultSet resultSet = preparedStatement.executeQuery();

                if(resultSet.next()){
                    AlertIndicator.showAlarm(Alert.AlertType.ERROR, "ERROR",
                            "This user is already created", false);
                    return;
                }
            } catch (SQLException e) {
                LogManager.getLogger().error(e.getMessage(),e);
                throw new RuntimeException(e);
            }

            // creating new user
            PreparedStatement preparedStatement = SQLiteJDBC.getConnection().prepareStatement(SIGN_UP);
            PreparedStatement getIdStatement = SQLiteJDBC.getConnection().prepareStatement(GET_LAST_INSERTED_ID);
            preparedStatement.setString(1, usernameField.getText());
            preparedStatement.setString(2, passwordField.getText());

            if(preparedStatement.executeUpdate() > 0){
                try (ResultSet resultSet = getIdStatement.executeQuery()) {
                    if (resultSet.next()) {
                        SceneChanger.changeScene("login");
                        AlertIndicator.showAlarm(Alert.AlertType.INFORMATION, "Sign Up",
                                "You have successfully registered! Please login", true);
                        LogManager.getLogger().info("User registered");
                    }
                }
            }

        } catch (SQLException e) {
            LogManager.getLogger().error(e.getMessage(),e);
            throw new RuntimeException(e);
        }
    }
}
