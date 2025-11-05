package org.project.javafxcourse.models.IMDb.populars;

import lombok.Data;
import org.project.javafxcourse.interfaces.IMDb.IMDbPopularsInfo;

@Data
public class IMDbMostPopularShowInfo implements IMDbPopularsInfo {
    private Double averageRating;
    private String primaryImage;
    private String primaryTitle;
    private String description;
}
