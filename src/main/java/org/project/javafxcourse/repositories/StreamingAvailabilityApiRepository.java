package org.project.javafxcourse.repositories;

import okhttp3.HttpUrl;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import org.project.javafxcourse.interfaces.StreamingAvailabilityRepository;
import org.project.javafxcourse.models.StreamingAvailability;

import java.io.IOException;
import java.util.Objects;

public class StreamingAvailabilityApiRepository implements StreamingAvailabilityRepository {
    private String baseUrl = "https://streaming-availability.p.rapidapi.com/shows/search/title";
    private String apiKey = "3633bbd7ebmshbeec87fdb76e4a9p1edd85jsne9b1652eb2df";


    @Override
    public StreamingAvailability getByNameAndCountry(String name, String country) throws Exception {
        OkHttpClient client = new OkHttpClient();

        HttpUrl url = Objects.requireNonNull(HttpUrl.parse(baseUrl)).newBuilder()
                .addQueryParameter("series_granularity", "show")
                .addQueryParameter("country", country)
                .addQueryParameter("title", name)
                .build();


        Request request = new Request.Builder()
                .url(url)
                .addHeader("X-RapidAPI-Key", apiKey)
                .build();

        try (Response response = client.newCall(request).execute()) {
            if (!response.isSuccessful()) {
                throw new IOException("Unexpected code " + response);
            }
            System.out.println(response.body().string());
        }

        return new StreamingAvailability();
    }

}

