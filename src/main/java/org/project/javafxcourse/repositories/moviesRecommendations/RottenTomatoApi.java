package org.project.javafxcourse.repositories.moviesRecommendations;

import com.google.gson.Gson;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import org.project.javafxcourse.interfaces.MoviesRecommendationsRepository;
import org.project.javafxcourse.models.moviesRecomendations.MoviesRecommendations;

import java.io.IOException;

public class RottenTomatoApi implements MoviesRecommendationsRepository {
    private String baseUrl =  "https://rottentomato.p.rapidapi.com";
    private String apiKey = "3633bbd7ebmshbeec87fdb76e4a9p1edd85jsne9b1652eb2df";
    @Override
    public MoviesRecommendations getTodayRecommendation() throws Exception {
        OkHttpClient client = new OkHttpClient();

        String url = this.baseUrl + "/today-recomendations";

        Request request = new Request.Builder()
                .url(url)
                .addHeader("x-rapidapi-key", apiKey)
                .build();

        try (Response response = client.newCall(request).execute()) {
            if (!response.isSuccessful()) {
                throw new IOException("Unexpected code " + response);
            }

            String recommendations = response.body().string();

            System.out.println(recommendations);

            Gson gson = new Gson();

            return gson.fromJson(recommendations, MoviesRecommendations.class);
        }
    }
}
