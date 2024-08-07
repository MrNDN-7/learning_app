package org.example.cuoiki_code_tutorial.Controllers;

import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;

public class SceneLoader {
    public static void loadScene(String resourcePath, String cssPath, Stage stage) {
        try {
            FXMLLoader fxmlLoader = new FXMLLoader(SceneLoader.class.getResource(resourcePath));
            Parent root = fxmlLoader.load();
            root.getStyleClass().add("background");
            Scene scene = new Scene(root, 1000, 600);
            // Kết nối tệp CSS với scene
            scene.getStylesheets().add(SceneLoader.class.getResource(cssPath).toExternalForm());
            stage.setScene(scene);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
