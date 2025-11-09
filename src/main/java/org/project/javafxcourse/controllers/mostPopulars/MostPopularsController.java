package org.project.javafxcourse.controllers.mostPopulars;

import javafx.concurrent.Task;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.Label;
import javafx.scene.control.ProgressIndicator;
import javafx.scene.layout.HBox;  // ← Changé de FlowPane à HBox
import javafx.scene.layout.Pane;
import org.project.javafxcourse.interfaces.IMDb.IMDbPopularsInfo;

import java.io.IOException;
import java.util.List;
import java.util.function.Supplier;

public class MostPopularsController {

    @FXML
    private HBox popularsContainer;

    @FXML
    private Label errorLabel;

    @FXML
    private ProgressIndicator loadingSpinner;

    @FXML
    private Label sectionTitle;

    /**
     * Charge du contenu de manière générique
     * @param dataSupplier Une fonction qui retourne les données à afficher
     * @param errorMessage Le message d'erreur à afficher en cas d'échec
     * @param <T> Le type de média (Movie ou Show)
     */
    public <T extends IMDbPopularsInfo> void loadContent(
            Supplier<List<T>> dataSupplier,
            String errorMessage
    ) {

        showLoading(true);
        errorLabel.setVisible(false);

        Task<List<T>> mostPopularsTask = new Task<>() {
            @Override
            protected List<T> call() throws Exception {
                return dataSupplier.get();
            }
        };

        mostPopularsTask.setOnSucceeded(e -> {
            showLoading(false);
            List<T> items = mostPopularsTask.getValue();

            if (items == null || items.isEmpty()) {
                showErrorMessage("Aucun contenu disponible");
                return;
            }

            displayItems(items);
        });

        mostPopularsTask.setOnFailed(e -> {
            showLoading(false);
            Throwable ex = mostPopularsTask.getException();
            ex.printStackTrace();
            showErrorMessage(errorMessage != null ? errorMessage : "Erreur lors du chargement");
        });

        Thread thread = new Thread(mostPopularsTask);
        thread.setDaemon(true);
        thread.start();
    }

    public void setSectionTitle(String title) {
        if (sectionTitle != null) {
            sectionTitle.setText(title);
        }
    }

    /**
     * Affiche les recommandations dans le HBox
     */
    private <T extends IMDbPopularsInfo> void displayItems(List<T> moviesRecommendations) {
        popularsContainer.getChildren().clear();

        moviesRecommendations.stream()
                .limit(15)
                .forEach(movie -> {
                    try {
                        FXMLLoader loader = new FXMLLoader(
                                getClass().getResource("/org/project/javafxcourse/mostPopulars/popular-movies-card.fxml")
                        );
                        Pane card = loader.load();

                        MovieCardController cardController = loader.getController();
                        cardController.setData(movie);

                        popularsContainer.getChildren().add(card);
                    } catch (IOException ex) {
                        ex.printStackTrace();
                    }
                });
    }

    /**
     * Affiche un message d'erreur dans l'UI
     */
    private void showErrorMessage(String message) {
        popularsContainer.getChildren().clear();
        if (errorLabel != null) {
            errorLabel.setText(message);
            errorLabel.setVisible(true);
        }
    }

    /**
     * Affiche ou cache le spinner de chargement
     */
    private void showLoading(boolean show) {
        if (loadingSpinner != null) {
            loadingSpinner.setVisible(show);
        }
    }
}
