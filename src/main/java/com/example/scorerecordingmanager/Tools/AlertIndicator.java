package com.example.scorerecordingmanager.Tools;

import javafx.scene.control.Alert;

public class AlertIndicator {
    private static final String FAQText = "Lorem Ipsum is simply dummy text of the printing and typesetting industry. Lorem Ipsum has been the industry's standard dummy text ever since the 1500s, when an unknown printer took a galley of type and scrambled it to make a type specimen book. It has survived not only five centuries, but also the leap into electronic typesetting, remaining essentially unchanged. It was popularised in the 1960s with the release of Letraset sheets containing Lorem Ipsum passages, and more recently with desktop publishing software like Aldus PageMaker including versions of Lorem Ipsum";

    private static Alert hepler(Alert.AlertType alertType, String Title,
                               String Content){
        Alert alert = new Alert(alertType);
        alert.setTitle(Title);
        alert.setContentText(Content);

        alert.getDialogPane().getStylesheets().add(AlertIndicator.class.
                getResource("/com/example/scorerecordingmanager/css/styles.css").toExternalForm());

        return alert;
    }
    public static void showAlarm(Alert.AlertType alertType, String Title,
                                 String Content, boolean isRed){

        Alert alert = hepler(alertType, Title, Content);

        if(isRed){
            alert.getDialogPane().lookup(".content").setStyle("-fx-text-fill: red;");
        }

        alert.showAndWait();
    }
    public static void showFAQ(){
        Alert alert = hepler(Alert.AlertType.INFORMATION, "How to Use", FAQText);
        alert.setHeaderText(null);
        alert.showAndWait();
    }
}