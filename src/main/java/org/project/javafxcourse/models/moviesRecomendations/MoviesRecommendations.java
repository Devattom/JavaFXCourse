package org.project.javafxcourse.models.moviesRecomendations;

import lombok.Data;

import java.util.ArrayList;

@Data
public class MoviesRecommendations {
    private ArrayList<MovieRecommendationInfo> recommendations;
}
