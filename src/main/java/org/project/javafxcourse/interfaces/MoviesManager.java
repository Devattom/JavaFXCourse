package org.project.javafxcourse.interfaces;

import org.project.javafxcourse.models.IMDb.populars.IMDbMostPopularMovieInfo;
import org.project.javafxcourse.models.IMDb.populars.IMDbMostPopularShowInfo;

import java.util.List;

public interface MoviesManager {
    List<IMDbMostPopularMovieInfo> getMostPopularMovies() throws Exception;

    List<IMDbMostPopularShowInfo> getMostPopularShows() throws Exception;
}
