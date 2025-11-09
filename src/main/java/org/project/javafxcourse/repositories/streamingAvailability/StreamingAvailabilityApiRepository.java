package org.project.javafxcourse.repositories.streamingAvailability;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import okhttp3.HttpUrl;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import org.project.javafxcourse.config.AppConfig;
import org.project.javafxcourse.interfaces.StreamingAvailabilityManager;
import org.project.javafxcourse.models.streamingAvailability.StreamingAvailability;

import java.io.IOException;
import java.util.List;
import java.util.Objects;

public class StreamingAvailabilityApiRepository implements StreamingAvailabilityManager {
    private String baseUrl = "https://streaming-availability.p.rapidapi.com/shows/search/title";
    private String apiKey;

    public StreamingAvailabilityApiRepository() {
        AppConfig config = AppConfig.getInstance();
        this.apiKey = config.get("rapidapi.api.key");
    }

    @Override
    public List<StreamingAvailability> getByNameAndCountry(String name, String country, String showType) throws Exception {
        OkHttpClient client = new OkHttpClient();

        HttpUrl url = Objects.requireNonNull(HttpUrl.parse(baseUrl)).newBuilder()
                .addQueryParameter("series_granularity", "show")
                .addQueryParameter("country", country)
                .addQueryParameter("title", name)
                .addQueryParameter("show_type", showType)
                .addQueryParameter("output_language", "fr")
                .build();


        Request request = new Request.Builder()
                .url(url)
                .addHeader("X-RapidAPI-Key", apiKey)
                .build();

        try (Response response = client.newCall(request).execute()) {
            if (!response.isSuccessful()) {
                throw new IOException("Unexpected code " + response);
            }
            String streamingAvailabilities = response.body().string();

            Gson gson = new GsonBuilder().create();

            return List.of(gson.fromJson(streamingAvailabilities, StreamingAvailability[].class));
        }
    }
}
