package org.project.javafxcourse.navigation;

import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

import lombok.Setter;
import org.project.javafxcourse.controllers.StreamingAvailabilityController;


public class NavigationManager {

    @Setter
    private static Stage primaryStage;

    public static void goToStreamingAvailability(String title, String showType) {
        try {
            FXMLLoader loader = new FXMLLoader(
                    NavigationManager.class.getResource("/org/project/javafxcourse/streaming-availability.fxml")
            );
            Parent root = loader.load();

            // Récupération du contrôleur de la vue
            StreamingAvailabilityController controller = loader.getController();

            // On lui passe le titre à rechercher
            controller.searchFor(title, showType);

            primaryStage.setScene(new Scene(root));
            primaryStage.show();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
