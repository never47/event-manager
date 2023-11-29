package com.example.scorerecordingmanager.Controllers;

import com.example.scorerecordingmanager.DBHelper;
import com.example.scorerecordingmanager.SQLiteJDBC;
import com.example.scorerecordingmanager.SceneChanger;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class SignUpController {
    private final String SIGN_UP = "INSERT INTO User (userName, Password) VALUES (?, ?)";
    private final String GET_LAST_INSERTED_ID = "SELECT last_insert_rowid()";
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
            PreparedStatement preparedStatement = SQLiteJDBC.getConnection().prepareStatement(SIGN_UP);
            PreparedStatement getIdStatement = SQLiteJDBC.getConnection().prepareStatement(GET_LAST_INSERTED_ID);
            preparedStatement.setString(1, usernameField.getText());
            preparedStatement.setString(2, passwordField.getText());


            if(preparedStatement.executeUpdate() > 0){
                try (ResultSet resultSet = getIdStatement.executeQuery()) {
                    if (resultSet.next()) {
                        DBHelper.setUser_id(resultSet.getInt(1));
                        SceneChanger.changeScene("homeScreen");
                    }
                }
            }

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
}
