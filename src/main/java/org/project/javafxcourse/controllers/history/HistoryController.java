package org.project.javafxcourse.controllers.history;

import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Label;
import javafx.scene.layout.Pane;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import org.project.javafxcourse.controllers.streamingAvailability.StreamingAvailabilityController;
import org.project.javafxcourse.models.entities.History;
import org.project.javafxcourse.repositories.history.HistoryRepository;

import java.io.IOException;
import java.util.List;
import java.util.Optional;

public class HistoryController {

    @FXML
    private VBox historyContainer;

    @FXML
    private StackPane loadingPane;

    @FXML
    private Label emptyLabel;

    @FXML
    private Button clearHistoryButton;

    private HistoryRepository historyRepository;

    @FXML
    public void initialize() {
        historyRepository = new HistoryRepository();

        // Action du bouton effacer
        clearHistoryButton.setOnAction(e -> confirmClearHistory());

        // Charger l'historique
        loadHistory();
    }

    private void loadHistory() {
        showLoading(true);

        new Thread(() -> {
            try {
                List<History> historyList = historyRepository.getAllHistory();

                Platform.runLater(() -> {
                    showLoading(false);
                    displayHistory(historyList);
                });

            } catch (Exception e) {
                e.printStackTrace();
                Platform.runLater(() -> {
                    showLoading(false);
                    showError("Erreur lors du chargement de l'historique");
                });
            }
        }).start();
    }

    private void displayHistory(List<History> historyList) {
        historyContainer.getChildren().clear();

        if (historyList == null || historyList.isEmpty()) {
            emptyLabel.setVisible(true);
            clearHistoryButton.setDisable(true);
            return;
        }

        emptyLabel.setVisible(false);
        clearHistoryButton.setDisable(false);

        historyList.forEach(history -> {
            try {
                FXMLLoader loader = new FXMLLoader(
                        getClass().getResource("/org/project/javafxcourse/history/history-card.fxml")
                );
                Pane card = loader.load();

                HistoryCardController cardController = loader.getController();
                cardController.setData(history);

                // Callback pour le bouton "Voir"
                cardController.setOnViewAction(() -> {
                    navigateToStreamingAvailability(history);
                });

                historyContainer.getChildren().add(card);

            } catch (IOException e) {
                e.printStackTrace();
            }
        });
    }

    private void confirmClearHistory() {
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setTitle("Confirmation");
        alert.setHeaderText("Effacer l'historique");
        alert.setContentText("Êtes-vous sûr de vouloir effacer tout l'historique ?");

        Optional<ButtonType> result = alert.showAndWait();
        if (result.isPresent() && result.get() == ButtonType.OK) {
            clearHistory();
        }
    }

    private void clearHistory() {
        showLoading(true);

        new Thread(() -> {
            try {
                historyRepository.clearAll();

                Platform.runLater(() -> {
                    showLoading(false);
                    loadHistory(); // Recharger (affichera le message vide)
                });

            } catch (Exception e) {
                e.printStackTrace();
                Platform.runLater(() -> {
                    showLoading(false);
                    showError("Erreur lors de la suppression");
                });
            }
        }).start();
    }

    private void navigateToStreamingAvailability(History history) {
        try {
            FXMLLoader loader = new FXMLLoader(
                    getClass().getResource("/org/project/javafxcourse/streaming-availability-view.fxml")
            );
            Pane view = loader.load();

            StreamingAvailabilityController controller = loader.getController();
            controller.searchStreamingAvailabilities(history.getTitle(), history.getShowType());

            // Remplacer la vue actuelle
            historyContainer.getScene().setRoot(view);

        } catch (IOException e) {
            e.printStackTrace();
            showError("Erreur lors de la navigation");
        }
    }

    private void showLoading(boolean show) {
        if (loadingPane != null) {
            loadingPane.setVisible(show);
            loadingPane.setManaged(show);
        }
    }

    private void showError(String message) {
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle("Erreur");
        alert.setContentText(message);
        alert.showAndWait();
    }
}