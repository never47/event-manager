module com.example.scorerecordingmanager {
    requires javafx.controls;
    requires javafx.fxml;
    requires org.xerial.sqlitejdbc;
    requires org.slf4j;

    opens com.example.scorerecordingmanager to javafx.fxml;
    opens com.example.scorerecordingmanager.Controllers to javafx.fxml;
    exports com.example.scorerecordingmanager;
}