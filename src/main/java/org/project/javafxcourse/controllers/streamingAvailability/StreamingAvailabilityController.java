package org.project.javafxcourse.controllers.streamingAvailability;

import javafx.concurrent.Task;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ProgressIndicator;
import javafx.scene.image.ImageView;
import javafx.scene.control.ScrollPane;
import javafx.scene.layout.FlowPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import org.project.javafxcourse.models.streamingAvailability.StreamingAvailability;
import org.project.javafxcourse.models.streamingAvailability.StreamingOption;
import org.project.javafxcourse.navigation.NavigationManager;
import org.project.javafxcourse.services.ImageService;
import org.project.javafxcourse.services.StreamingAvailabilityService;

import java.io.IOException;
import java.util.List;
import java.util.Map;

public class StreamingAvailabilityController {

    private final StreamingAvailabilityService streamingService;
    private final ImageService imageService;

    @FXML
    private Button backButton;

    @FXML
    private Label titleLabel;

    @FXML
    private Label descriptionLabel;

    @FXML
    private Label errorLabel;

    @FXML
    private Label infoLabel;

    @FXML
    private StackPane loadingPane;

    @FXML
    private ProgressIndicator loadingSpinner;

    @FXML
    private HBox availabilityContainer;

    @FXML
    private ImageView posterImage;

    @FXML
    private FlowPane streamingPlatformsContainer;

    @FXML
    private VBox additionalResultsContainer;

    @FXML
    private ScrollPane additionalResultsScrollPane;

    @FXML
    private HBox additionalResultsHBox;

    public StreamingAvailabilityController() {
        this.streamingService = new StreamingAvailabilityService();
        this.imageService = new ImageService();
    }

    @FXML
    private void onBackButtonClick(ActionEvent event) {
        NavigationManager.goToHome();
    }

    public void searchStreamingAvailabilities(String title, String showType) {
        // Réinitialiser l'UI
        resetUI(title);
        
        // Créer la task via le service
        Task<List<StreamingAvailability>> streamingAvailabilitiesTask = streamingService.searchStreamingAvailability(title, "fr", showType);

        streamingAvailabilitiesTask.setOnSucceeded(e -> {
            showLoading(false);
            List<StreamingAvailability> results = streamingAvailabilitiesTask.getValue();
            
            if (results == null || results.isEmpty()) {
                showError("Aucun résultat trouvé pour : " + title);
                return;
            }
            
            displayResults(results);
        });

        streamingAvailabilitiesTask.setOnFailed(e -> {
            showLoading(false);
            Throwable ex = streamingAvailabilitiesTask.getException();
            ex.printStackTrace();
            showError("Erreur : " + ex.getMessage());
        });

        Thread thread = new Thread(streamingAvailabilitiesTask);
        thread.setDaemon(true);
        thread.start();
    }

    /**
     * Réinitialise l'interface utilisateur avant une nouvelle recherche
     */
    private void resetUI(String title) {
        titleLabel.setText("");
        descriptionLabel.setText("");
        errorLabel.setVisible(false);
        streamingPlatformsContainer.getChildren().clear();
        additionalResultsContainer.setVisible(false);
        if (additionalResultsHBox != null) {
            additionalResultsHBox.getChildren().clear();
        }
        posterImage.setImage(null);
        showLoading(true);
        infoLabel.setText("Recherche en cours pour : " + title);
    }

    /**
     * Affiche les résultats de la recherche
     */
    private void displayResults(List<StreamingAvailability> results) {
        StreamingAvailability mainResult = results.get(0);

        displayAvailability(mainResult);

        if (results.size() > 1) {
            List<StreamingAvailability> additionalResults = results.subList(1, results.size());
            displayAdditionalResults(additionalResults);
            additionalResultsContainer.setVisible(true);
        }
    }

    /**
     * Affiche un message d'erreur
     */
    private void showError(String message) {
        errorLabel.setText(message);
        errorLabel.setVisible(true);
    }

    private void displayAvailability(StreamingAvailability streamingAvailability) {

        if (streamingAvailability.getImageSet() != null &&
            streamingAvailability.getImageSet().getVerticalPoster() != null
        ) {
            String imageUrl = streamingAvailability.getImageSet().getVerticalPoster().getW360();

            if (imageUrl != null && !imageUrl.isBlank()) {
                // Utiliser 0 pour la hauteur afin de préserver le ratio de l'ImageView
                imageService.loadImage(posterImage, imageUrl, 360, 0);
            }
        }

        titleLabel.setText(streamingAvailability.getTitle() != null
                ? streamingAvailability.getTitle()
                : "Titre inconnu");

        descriptionLabel.setText(streamingAvailability.getOverview() != null
                ? streamingAvailability.getOverview()
                : "Aucune description disponible");

        displayStreamingPlatforms(streamingAvailability.getStreamingOptions());
    }

    /**
     * Affiche les plateformes disponibles
     * @param streamingOptions liste des options de plateformes
     */
    private void displayStreamingPlatforms(Map<String, List<StreamingOption>> streamingOptions) {
        streamingPlatformsContainer.getChildren().clear();

        if (streamingOptions == null || streamingOptions.isEmpty()) {
            Label noPlatformLabel = new Label("Aucune plateforme disponible");
            noPlatformLabel.setStyle("-fx-font-size: 14px; -fx-text-fill: #666;");
            streamingPlatformsContainer.getChildren().add(noPlatformLabel);
            return;
        }

        for (Map.Entry<String, List<StreamingOption>> entry : streamingOptions.entrySet()) {
            List<StreamingOption> options = entry.getValue();
            if (options != null) {
                for (StreamingOption option : options) {
                    if (option == null) {
                        continue;
                    }

                    String serviceName = option.getService().getName();

                    if (serviceName != null && !serviceName.isBlank()) {
                        // Charger la card depuis le FXML
                        try {
                            FXMLLoader loader = new FXMLLoader(
                                    getClass().getResource("/org/project/javafxcourse/streaming-availability/streaming-platform-card.fxml")
                            );
                            Pane card = loader.load();

                            StreamingPlatformCardController cardController = loader.getController();
                            cardController.setData(option);

                            streamingPlatformsContainer.getChildren().add(card);
                        } catch (IOException ex) {
                            System.err.println("Erreur lors du chargement de la card de plateforme : " + ex.getMessage());
                            ex.printStackTrace();
                        }
                    }
                }
            }
        }
    }

    private void displayAdditionalResults(List<StreamingAvailability> additionalResults) {
        if (additionalResultsHBox != null) {
            additionalResultsHBox.getChildren().clear();
        }

        if (additionalResults == null || additionalResults.isEmpty()) {
            additionalResultsContainer.setVisible(false);
            return;
        }

        additionalResultsContainer.setVisible(true);

        for (StreamingAvailability result : additionalResults) {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/org/project/javafxcourse/streaming-availability/additional-results-card.fxml"));

            try {
                VBox card = loader.load();

                AdditionalResultsCardController cardController = loader.getController();

                cardController.setData(result);

                if (additionalResultsHBox != null) {
                    additionalResultsHBox.getChildren().add(card);
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    /**
     * Affiche ou cache le spinner de chargement
     */
    private void showLoading(boolean show) {
        if (loadingPane != null) {
            loadingPane.setVisible(show);
            loadingPane.setManaged(show);
        }
    }
}
