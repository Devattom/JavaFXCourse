package org.project.javafxcourse.models.moviesRecomendations;

import lombok.Data;

@Data
public class MovieRecommendationInfo {
    private int audience_score;
    private int critics_score;
    private String image_url;
    private String title;
}
