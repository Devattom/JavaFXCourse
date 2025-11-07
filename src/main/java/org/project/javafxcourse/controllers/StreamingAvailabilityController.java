package org.project.javafxcourse.controllers;

import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ProgressIndicator;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import org.project.javafxcourse.models.streamingAvailability.StreamingAvailability;
import org.project.javafxcourse.repositories.StreamingAvailabilityApiRepository;

import java.util.List;

public class StreamingAvailabilityController {

    @FXML
    private Button backButton;

    @FXML
    private Label titleLabel;

    @FXML
    private Label errorLabel;

    @FXML
    private Label infoLabel;

    @FXML
    private StackPane loadingPane;

    @FXML
    private ProgressIndicator loadingSpinner;

    @FXML
    private VBox resultsContainer;

    public void searchFor(String title, String showType) {
        // Réinitialiser l'UI
        Platform.runLater(() -> {
            titleLabel.setText("");
            errorLabel.setVisible(false);
            resultsContainer.getChildren().clear();
            showLoading(true);
            infoLabel.setText("Recherche en cours pour : " + title);
        });

        new Thread(() -> {
            try {
                StreamingAvailabilityApiRepository streamingAvailability = new StreamingAvailabilityApiRepository();
                List<StreamingAvailability> results = streamingAvailability.getByNameAndCountry(title, "fr", showType);

                System.out.println("Résultats trouvés : " + (results != null ? results.size() : 0));

                // Mise à jour de l'UI sur le thread JavaFX
                Platform.runLater(() -> {
                    showLoading(false);

                    if (results == null || results.isEmpty()) {
                        errorLabel.setText("Aucun résultat trouvé pour : " + title);
                        errorLabel.setVisible(true);
                        return;
                    }

                    // Afficher le premier résultat
                    StreamingAvailability firstResult = results.get(0);
                    titleLabel.setText(firstResult.getTitle());

                    // Afficher les plateformes de streaming
                    displayStreamingPlatforms(firstResult);
                });

            } catch (Exception e) {
                e.printStackTrace();
                Platform.runLater(() -> {
                    showLoading(false);
                    errorLabel.setText("Erreur : " + e.getMessage());
                    errorLabel.setVisible(true);
                });
            }
        }).start();
    }

    private void displayStreamingPlatforms(StreamingAvailability stream) {
        resultsContainer.getChildren().clear();

        // Ajouter un label avec les informations
        Label platformInfo = new Label("Disponible sur : " + stream.toString());
        platformInfo.setStyle("-fx-font-size: 14px; -fx-text-fill: #333;");
        platformInfo.setWrapText(true);

        resultsContainer.getChildren().add(platformInfo);

        // TODO: Créer des cartes pour chaque plateforme de streaming
    }

    /**
     * Affiche ou cache le spinner de chargement
     */
    private void showLoading(boolean show) {
        if (loadingPane != null) {
            loadingPane.setVisible(show);
            loadingPane.setManaged(show);  // Important : libère l'espace quand caché
        }
    }
}