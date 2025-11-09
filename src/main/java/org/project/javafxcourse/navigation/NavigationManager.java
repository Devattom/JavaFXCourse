package org.project.javafxcourse.navigation;

import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

import lombok.Setter;
import org.project.javafxcourse.controllers.streamingAvailability.StreamingAvailabilityController;

public class NavigationManager {

    @Setter
    private static Stage primaryStage;

    public static void goToStreamingAvailability(String title, String showType) {
        try {
            FXMLLoader loader = new FXMLLoader(
                    NavigationManager.class.getResource("/org/project/javafxcourse/streaming-availability/streaming-availability.fxml")
            );
            Parent root = loader.load();

            // Récupération du contrôleur de la vue
            StreamingAvailabilityController controller = loader.getController();

            // On lui passe le titre à rechercher
            controller.searchStreamingAvailabilities(title, showType);

            setScene(root, "Où regarder ?");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static void goToHome() {
        try {
            FXMLLoader loader = new FXMLLoader(
                    NavigationManager.class.getResource("/org/project/javafxcourse/home-view.fxml")
            );
            Parent root = loader.load();

            setScene(root, "Home");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private static void setScene(Parent root, String title) {
        // Sauvegarder la taille et position actuelles de la fenêtre
        double currentWidth = primaryStage.getWidth();
        double currentHeight = primaryStage.getHeight();
        boolean wasMaximized = primaryStage.isMaximized();

        // Créer la nouvelle scène avec la taille actuelle
        Scene currentScene = primaryStage.getScene();
        double sceneWidth = currentScene != null ? currentScene.getWidth() : currentWidth;
        double sceneHeight = currentScene != null ? currentScene.getHeight() : currentHeight;

        primaryStage.setScene(new Scene(root, sceneWidth, sceneHeight));
        primaryStage.setTitle("Où regarder ?");

        // Restaurer l'état de maximisation si nécessaire
        if (wasMaximized) {
            primaryStage.setMaximized(true);
        }

        primaryStage.show();
    }
}
