package org.project.javafxcourse.controllers;

import javafx.application.Platform;
import javafx.concurrent.Task;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.Label;
import javafx.scene.control.ProgressIndicator;
import javafx.scene.layout.FlowPane;
import javafx.scene.layout.Pane;
import org.project.javafxcourse.interfaces.MoviesRecommendationsRepository;
import org.project.javafxcourse.models.moviesRecomendations.MovieRecommendationInfo;
import org.project.javafxcourse.models.moviesRecomendations.MoviesRecommendations;
import org.project.javafxcourse.repositories.moviesRecommendations.RottenTomatoApi;

import java.io.IOException;
import java.util.List;

public class TodayRecommendationsController {

    @FXML
    private FlowPane recommendationsContainer;

    @FXML
    private Label errorLabel;

    @FXML
    private ProgressIndicator loadingSpinner;

    /**
     * Méthode principale appelée depuis HomeController
     */
    public void loadTodayRecommendations() {
        System.out.println("TodayRecommendationsController::loadTodayRecommendations");

        showLoading(true); // afficher le spinner
        errorLabel.setVisible(false);

        Task<MoviesRecommendations> task = new Task<>() {
            @Override
            protected MoviesRecommendations call() throws Exception {
                MoviesRecommendationsRepository api = new RottenTomatoApi();
                return api.getTodayRecommendation();
            }
        };

        // Callback succès
        task.setOnSucceeded(e -> {
            showLoading(false);
            MoviesRecommendations movies = task.getValue();

            if (movies == null || movies.getRecommendations() == null || movies.getRecommendations().isEmpty()) {
                showErrorMessage("Aucune recommandation disponible");
                return;
            }

            displayRecommendations(movies);
        });

        // Callback échec
        task.setOnFailed(e -> {
            showLoading(false);
            Throwable ex = task.getException();
            ex.printStackTrace();
            showErrorMessage("Erreur lors du chargement des recommandations");
        });

        Thread thread = new Thread(task);
        thread.setDaemon(true); // le thread se ferme automatiquement à la fin de l'application
        thread.start();
    }

    /**
     * Affiche les recommandations dans le FlowPane
     */
    private void displayRecommendations(MoviesRecommendations moviesRecommendations) {
        recommendationsContainer.getChildren().clear();

        List<MovieRecommendationInfo> list = moviesRecommendations.getRecommendations();
        list.stream()
                .limit(10)
                .forEach(movie -> {
                    try {
                        System.out.println(TodayRecommendationsController.class.getResource("movie-card.fxml"));

                        FXMLLoader loader = new FXMLLoader(TodayRecommendationsController.class.getResource("/org/project/javafxcourse/views/movie-card.fxml"));
                        Pane card = loader.load();

                        MovieCardController cardController = loader.getController();
                        cardController.setData(movie);

                        recommendationsContainer.getChildren().add(card);
                    } catch (IOException ex) {
                        ex.printStackTrace();
                    }
                });
    }

    /**
     * Affiche un message d'erreur dans l'UI
     */
    private void showErrorMessage(String message) {
        recommendationsContainer.getChildren().clear();
        if (errorLabel != null) {
            errorLabel.setText(message);
            errorLabel.setVisible(true);
        } else {
            Label label = new Label(message);
            label.setStyle("-fx-text-fill: red; -fx-font-size: 14px;");
            recommendationsContainer.getChildren().add(label);
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
