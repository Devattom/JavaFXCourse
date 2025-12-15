package org.project.javafxcourse;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;
import org.project.javafxcourse.navigation.NavigationManager;

import java.io.IOException;

public class CineScoutApplication extends Application {
    private static CineScoutApplication instance;

    @Override
    public void start(Stage stage) throws IOException {
        instance = this;
        NavigationManager.setPrimaryStage(stage);
        FXMLLoader fxmlLoader = new FXMLLoader(CineScoutApplication.class.getResource("home-view.fxml"));
        Scene scene = new Scene(fxmlLoader.load());
        stage.setTitle("CineScout");
        stage.setScene(scene);
        stage.setMaximized(true);
        stage.show();
    }

    public static void openWebpage(String url) {
        if (instance != null) {
            instance.getHostServices().showDocument(url);
        } else {
            System.err.println("ERREUR : L'instance de l'application est null !");
        }
    }
}
