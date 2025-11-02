package org.project.javafxcourse.interfaces;

import org.project.javafxcourse.models.moviesRecomendations.MoviesRecommendations;

public interface MoviesRecommendationsRepository {
    MoviesRecommendations getTodayRecommendation() throws Exception;
}
