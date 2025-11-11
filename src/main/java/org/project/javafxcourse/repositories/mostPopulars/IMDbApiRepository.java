package org.project.javafxcourse.repositories.mostPopulars;

import com.google.gson.Gson;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import org.project.javafxcourse.config.AppConfig;
import org.project.javafxcourse.interfaces.MoviesManager;
import org.project.javafxcourse.models.IMDb.populars.IMDbMostPopularMovieInfo;
import org.project.javafxcourse.models.IMDb.populars.IMDbMostPopularShowInfo;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class IMDbApiRepository implements MoviesManager {
    private String baseUrl =  "https://imdb236.p.rapidapi.com/api/imdb";
    private String apiKey;

    public IMDbApiRepository() {
        AppConfig config = AppConfig.getInstance();
        this.apiKey = config.get("rapidapi.api.key");
    }

    @Override
    public List<IMDbMostPopularMovieInfo> getMostPopularMovies() throws Exception {
//        OkHttpClient client = new OkHttpClient();
//
//        String url = this.baseUrl + "/most-popular-movies";
//
//        Request request = this.createRequest(url);
//
//        try (Response response = client.newCall(request).execute()) {
//            if (!response.isSuccessful()) {
//                throw new IOException("Unexpected code " + response);
//            }
//
//            String mostPopularMovies = response.body().string();
//
//            Gson gson = new Gson();
//
//            List<IMDbMostPopularMovieInfo> movies = List.of(gson.fromJson(mostPopularMovies, IMDbMostPopularMovieInfo[].class));

        List<IMDbMostPopularMovieInfo> movies = new ArrayList<>();
        IMDbMostPopularMovieInfo movie = new IMDbMostPopularMovieInfo();
        movie.setAverageRating(5.00);
        movie.setDescription("A wayward school bus driver and a dedicated school teacher battle to save 22 children from a terrifying inferno");
        movie.setPrimaryTitle("The Lost Bus");
        movie.setPrimaryImage("https://m.media-amazon.com/images/M/MV5BZTIzNmQzYzUtNTdlNi00NmY5LThmNTYtMGFmZjUxMTgzOGNmXkEyXkFqcGc@.jpg");
        movies.add(movie);
            return movies;
//        }
    }

    public List<IMDbMostPopularShowInfo> getMostPopularShows() throws Exception {
//        OkHttpClient client = new OkHttpClient();
//
//        String url = this.baseUrl + "/most-popular-tv";
//
//        Request request = this.createRequest(url);
//
//        try (Response response = client.newCall(request).execute()) {
//            if (!response.isSuccessful()) {
//                throw new IOException("Unexpected code " + response);
//            }
//
//            String mostPopularShows = response.body().string();
//
//            Gson gson = new Gson();
//
//            List<IMDbMostPopularShowInfo> shows = List.of(gson.fromJson(mostPopularShows, IMDbMostPopularShowInfo[].class));

        List<IMDbMostPopularShowInfo> shows = new ArrayList<>();
        IMDbMostPopularShowInfo show = new IMDbMostPopularShowInfo();
        show.setAverageRating(5.00);
        show.setDescription("Carl, a former top-rated detective, is wracked with guilt following an attack that left his partner paralyzed and another policeman dead. On his return to work, Carl is assigned to a cold case that will consume his life");
        show.setPrimaryTitle("Dept. Q");
        show.setPrimaryImage("https://m.media-amazon.com/images/M/MV5BNWQ3MDQ2MGQtOGM0MC00MzlkLWE0ODQtYzE4Zjc3Mjc1ZWI5XkEyXkFqcGc@.jpg");
        shows.add(show);
            return shows;
//        }
    }

    private Request createRequest(String url) {
        return new Request.Builder()
                .url(url)
                .addHeader("x-rapidapi-key", apiKey)
                .build();
    }
}
