package org.project.javafxcourse.controllers;

import javafx.fxml.FXML;

import org.project.javafxcourse.models.moviesRecomendations.MoviesRecommendations;

import java.util.List;

public class HomeController {

    @FXML
    private TodayRecommendationsController todayRecommendationsSectionController;

    @FXML
    public void initialize() {
        System.out.println("HomeController::initialize");
        todayRecommendationsSectionController.loadTodayRecommendations();
    }
}
