package org.project.javafxcourse.repositories.mostPopulars;

import com.google.gson.Gson;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import org.project.javafxcourse.interfaces.MoviesManager;
import org.project.javafxcourse.models.IMDb.populars.IMDbMostPopularMovieInfo;
import org.project.javafxcourse.models.IMDb.populars.IMDbMostPopularShowInfo;

import java.io.IOException;
import java.util.List;

public class IMDbApiRepository implements MoviesManager {
    private String baseUrl =  "https://imdb236.p.rapidapi.com/api/imdb";
    private String apiKey = "3633bbd7ebmshbeec87fdb76e4a9p1edd85jsne9b1652eb2df";

    @Override
    public List<IMDbMostPopularMovieInfo> getMostPopularMovies() throws Exception {
        OkHttpClient client = new OkHttpClient();

        String url = this.baseUrl + "/most-popular-movies";

        Request request = this.createRequest(url);

        try (Response response = client.newCall(request).execute()) {
            if (!response.isSuccessful()) {
                throw new IOException("Unexpected code " + response);
            }

            String mostPopularMovies = response.body().string();

            System.out.println(mostPopularMovies);

            Gson gson = new Gson();

            List<IMDbMostPopularMovieInfo> movies = List.of(gson.fromJson(mostPopularMovies, IMDbMostPopularMovieInfo[].class));

//        List<IMDbMostPopularMovieInfo> movies = new ArrayList<>();
//        IMDbMostPopularMovieInfo movie = new IMDbMostPopularMovieInfo();
//        movie.setAverageRating(5.00);
//        movie.setDescription("Super description");
//        movie.setPrimaryTitle("Super title");
//        movie.setPrimaryImage("https://google.com");
//        movies.add(movie);
            return movies;
        }
    }

    public List<IMDbMostPopularShowInfo> getMostPopularShows() throws Exception {
        OkHttpClient client = new OkHttpClient();

        String url = this.baseUrl + "/most-popular-tv";

        Request request = this.createRequest(url);

        try (Response response = client.newCall(request).execute()) {
            if (!response.isSuccessful()) {
                throw new IOException("Unexpected code " + response);
            }

            String mostPopularShows = response.body().string();

            System.out.println(mostPopularShows);

            Gson gson = new Gson();

            List<IMDbMostPopularShowInfo> shows = List.of(gson.fromJson(mostPopularShows, IMDbMostPopularShowInfo[].class));

//        List<IMDbMostPopularMovieInfo> movies = new ArrayList<>();
//        IMDbMostPopularMovieInfo movie = new IMDbMostPopularMovieInfo();
//        movie.setAverageRating(5.00);
//        movie.setDescription("Super description");
//        movie.setPrimaryTitle("Super title");
//        movie.setPrimaryImage("https://google.com");
//        movies.add(movie);
            return shows;
        }
    }

    private Request createRequest(String url) {
        return new Request.Builder()
                .url(url)
                .addHeader("x-rapidapi-key", apiKey)
                .build();
    }
}
