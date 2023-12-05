package com.example.scorerecordingmanager.Controllers;

import com.example.scorerecordingmanager.DBHelper;
import com.example.scorerecordingmanager.SQLiteJDBC;
import com.example.scorerecordingmanager.SceneChanger;
import com.example.scorerecordingmanager.Tools.AlertIndicator;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.HBox;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.scene.text.Font;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.util.Duration;

import java.sql.PreparedStatement;
import java.sql.SQLException;


public class EventScreenController {
    private final String INSERT_SCORE = "UPDATE Team SET teamScore = ? WHERE teamID = ?";
    @FXML
    private Label eventName;

    @FXML
    private Label scoreLabel1;
    @FXML
    private Label scoreLabel2;
    @FXML
    private Label teamName1;
    @FXML
    private Label teamName2;
    @FXML
    private Label timerLabel;
    private Timeline timeline;
    private int elapsedSeconds = 0;
    private int scoreTeam1;
    private int scoreTeam2;

    private void updateLabel() {
        int minutes = elapsedSeconds / 60;
        int seconds = elapsedSeconds % 60;

        String formattedTime = String.format("%02d:%02d", minutes, seconds);
        timerLabel.setText(formattedTime);
    }

    @FXML
    void initialize() {
        // initialize scores with 0
        scoreTeam1 = 0;
        scoreTeam2 = 0;

        // label text set
        eventName.setText(DBHelper.getEventName());
        teamName1.setText(DBHelper.getTeamName1());
        teamName2.setText(DBHelper.getTeamName2());

        // timeLine logic
        timeline = new Timeline(new KeyFrame(Duration.seconds(1), event -> {
            elapsedSeconds++;
            updateLabel();
        }));
        timeline.setCycleCount(Timeline.INDEFINITE);
    }

    ////////// Timeline logic
    @FXML
    void startTimeline(MouseEvent event) {
        timeline.play();
    }

    @FXML
    void PauseTimeLine(MouseEvent event) {
        timeline.stop();
    }
    /////////
    @FXML
    void finishEvent(ActionEvent event) {
        // cannot finish if timeline has not started yet
        if(elapsedSeconds<1){
            AlertIndicator.showAlarm(Alert.AlertType.ERROR, "ERROR","Timer is not started yet",false);
            return;
        }
        timeline.stop();
        // save score for each team
        if(!DBHelper.isIsScoreSaved()){
            try(PreparedStatement preparedStatement = SQLiteJDBC.getConnection().prepareStatement(INSERT_SCORE)){

                //saving first team score
                preparedStatement.setInt(1, scoreTeam1);
                preparedStatement.setInt(2, DBHelper.getTeam_id1());

                preparedStatement.executeUpdate();

                //saving second team score
                preparedStatement.setInt(1, scoreTeam2);
                preparedStatement.setInt(2, DBHelper.getTeam_id2());

                preparedStatement.executeUpdate();
            } catch (SQLException e) {
                throw new RuntimeException(e);
            }
            // checking isScoreSaved for not to saving one eventTwice
            DBHelper.setIsScoreSaved(true);
        }

        // overlay window create (making it with new stage that is modal)
        Stage overlayStage = new Stage();
        overlayStage.setResizable(false);
        overlayStage.initModality(Modality.APPLICATION_MODAL);
        overlayStage.setTitle("WINNER");

        // stackpane creating
        StackPane overlayRoot = new StackPane();
        Scene overlayScene = new Scene(overlayRoot);
        overlayScene.getStylesheets().add(EventScreenController.class.
                getResource("/com/example/scorerecordingmanager/css/styles.css").toExternalForm());

        VBox vBox = new VBox();
        vBox.setPrefSize(300,200);
        vBox.setAlignment(Pos.CENTER);

        Label winner = new Label();
        winner.setText("WINNER");
        winner.setFont(new Font(24));
        winner.setStyle("-fx-text-fill: green;");
        vBox.getChildren().add(winner);

        Label teamWinner = new Label();

        // result show, which one won
        if(scoreTeam1>scoreTeam2){
            teamWinner.setText(teamName1.getText());
        }else if(scoreTeam1<scoreTeam2){
            teamWinner.setText(teamName2.getText());
        }else{
            teamWinner.setText("TIE");
        }

        teamWinner.setFont(new Font(30));
        teamWinner.setPadding(new Insets(20));
        teamWinner.setStyle("-fx-border-style: dashed; -fx-border-width: 2px;");
        vBox.getChildren().add(teamWinner);

        // return button
        Button returnButton = new Button();
        returnButton.setText("return");
        returnButton.setFont(new Font(15));
        returnButton.getStyleClass().add("customButton");
        // return button action
        returnButton.setOnAction(e -> overlayStage.close());

        // back to home button
        Button backToHome = new Button();
        backToHome.setText("Back To Tome");
        backToHome.setFont(new Font(15));
        backToHome.getStyleClass().add("customButton");
        // back to gome button action
        backToHome.setOnAction(e-> {
            overlayStage.close();
            SceneChanger.removeScenes();
            DBHelper.resetEvent();
            SceneChanger.changeScene("homeScreen");
        });

        //adding it to hbox(for arrangement)
        HBox hBox = new HBox();
        hBox.setPadding(new Insets(30,0,20,15));
        hBox.setAlignment(Pos.CENTER_LEFT);
        hBox.setSpacing(85);
        hBox.getChildren().add(returnButton);
        hBox.getChildren().add(backToHome);

        // hbox->vbox
        vBox.getChildren().add(hBox);

        // vbox->stackpane
        overlayRoot.getChildren().add(vBox);

        // set scene
        overlayStage.setScene(overlayScene);
        overlayStage.showAndWait();
    }

    //////1
    /*
        Score increase and decrease for team1
     */
    @FXML
    void makeFirstTeamDecrease(MouseEvent event) {
        if(scoreTeam1>0){
            scoreTeam1--;
            scoreLabel1.setText(Integer.toString(scoreTeam1));
        }else{
            AlertIndicator.showAlarm(Alert.AlertType.ERROR, "Error",
                    "Score can't be less than 0", false);
        }
    }

    @FXML
    void makeFirstTeamIncrease(MouseEvent event) {
        scoreTeam1++;
        scoreLabel1.setText(Integer.toString(scoreTeam1));
    }
    ////////1

    //////////2
    /*
        Score increase and decrease for team2
     */
    @FXML
    void makeSecondTeamDecrease(MouseEvent event) {
        if(scoreTeam2>0){
            scoreTeam2--;
            scoreLabel2.setText(Integer.toString(scoreTeam2));
        }else{
            AlertIndicator.showAlarm(Alert.AlertType.ERROR, "Error",
                    "Score can't be less than 0", false);
        }
    }

    @FXML
    void makeSecondTeamIncrease(MouseEvent event) {
        scoreTeam2++;
        scoreLabel2.setText(Integer.toString(scoreTeam2));
    }
    //////////2

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
