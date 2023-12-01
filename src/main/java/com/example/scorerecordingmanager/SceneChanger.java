package com.example.scorerecordingmanager;

import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

public class SceneChanger {
    private static final int SCENE_WIDTH = 550;
    private static final int SCENE_HEIGHT = 400;
    private static Stage stage;
    private static Map<String, Scene> scenes;
    private static final Map<String,String> scenes_path = new HashMap<>(){{
        put("login","fxml/login.fxml");
        put("signUp","fxml/signUp.fxml");
        put("homeScreen","fxml/homeScreen.fxml");
        put("eventCreateScreen","fxml/eventCreateScreen.fxml");
        put("playerScreenController1","fxml/playersScreen.fxml");
        put("playerScreenController2","fxml/playersScreen.fxml");
        put("eventScreen","fxml/eventScreen.fxml");
        put("eventDoneScreen", "fxml/eventDoneScreen.fxml");
        put("teamDoneScreen", "fxml/teamDoneScreen.fxml");
        put("playersDoneScreen", "fxml/playersDoneScreen.fxml");
    }};

    public static void setStage(Stage stage) {
        SceneChanger.stage = stage;
    }

    public static void removeScenes(){
        scenes.clear();
    }

    public static void changeScene(String name){
        if(scenes==null){
            scenes = new HashMap<>();
        }
        if(!scenes.containsKey(name)){
            FXMLLoader fxmlLoader = new FXMLLoader(SceneChanger.class.getResource(scenes_path.get(name)));
            try {
                Scene scene = new Scene(fxmlLoader.load(),SCENE_WIDTH,SCENE_HEIGHT);
                scenes.put(name,scene);
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        }
        stage.setScene(scenes.get(name));
        stage.show();

        if(name.contains("Done")
                || name.contains("login")
                || name.contains("signUp")){
            scenes.remove(name);
        }
    }
}