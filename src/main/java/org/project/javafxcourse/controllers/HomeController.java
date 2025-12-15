package org.project.javafxcourse.controllers;

import javafx.fxml.FXML;
import javafx.scene.layout.VBox;
import org.project.javafxcourse.controllers.history.HistoryController;
import org.project.javafxcourse.controllers.mostPopulars.MostPopularsController;
import org.project.javafxcourse.repositories.mostPopulars.IMDbApiRepository;

public class HomeController {

    @FXML
    private VBox popularMoviesSection;
    @FXML
    private MostPopularsController popularMoviesSectionController;

    @FXML
    private VBox popularShowsSection;
    @FXML
    private MostPopularsController popularShowsSectionController;

    @FXML
    private HistoryController historySectionController;

    @FXML
    public void initialize() {
        System.out.println("HomeController::initialize");

        IMDbApiRepository repository = new IMDbApiRepository();

        // Charger les films populaires
        popularMoviesSectionController.loadContent(
                () -> {
                    try {
                        return repository.getMostPopularMovies();
                    } catch (Exception e) {
                        throw new RuntimeException(e);
                    }
                },
                "Erreur lors du chargement des films populaires"
        );

        // Charger les séries populaires
        popularShowsSectionController.loadContent(
                () -> {
                    try {
                        return repository.getMostPopularShows();
                    } catch (Exception e) {
                        throw new RuntimeException(e);
                    }
                },
                "Erreur lors du chargement des séries populaires"
        );

        popularMoviesSectionController.setSectionTitle("⭐ Recommandations de films");
        popularShowsSectionController.setSectionTitle("⭐ Recommandations de séries");

    }
}
