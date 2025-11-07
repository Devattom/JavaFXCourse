package org.project.javafxcourse.repositories;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonSyntaxException;
import com.google.gson.reflect.TypeToken;
import okhttp3.HttpUrl;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import org.project.javafxcourse.adapters.StreamingOptionsAdapter;
import org.project.javafxcourse.interfaces.StreamingAvailabilityManager;
import org.project.javafxcourse.models.IMDb.populars.IMDbMostPopularShowInfo;
import org.project.javafxcourse.models.streamingAvailability.StreamingAvailability;
import org.project.javafxcourse.models.streamingAvailability.StreamingOption;

import java.io.IOException;
import java.lang.reflect.Type;
import java.util.List;
import java.util.Map;
import java.util.Objects;

public class StreamingAvailabilityApiRepository implements StreamingAvailabilityManager {
    private String baseUrl = "https://streaming-availability.p.rapidapi.com/shows/search/title";
    private String apiKey = "3633bbd7ebmshbeec87fdb76e4a9p1edd85jsne9b1652eb2df";


    @Override
    public List<StreamingAvailability> getByNameAndCountry(String name, String country, String showType) throws Exception {
        OkHttpClient client = new OkHttpClient();

        HttpUrl url = Objects.requireNonNull(HttpUrl.parse(baseUrl)).newBuilder()
                .addQueryParameter("series_granularity", "show")
                .addQueryParameter("country", country)
                .addQueryParameter("title", name)
                .addQueryParameter("show_type", showType)
                .build();


        Request request = new Request.Builder()
                .url(url)
                .addHeader("X-RapidAPI-Key", apiKey)
                .build();

        try (Response response = client.newCall(request).execute()) {
            if (!response.isSuccessful()) {
                throw new IOException("Unexpected code " + response);
            }
            String jsonResponse = response.body().string();

//            Type streamingOptionsType = new TypeToken<Map<String, Map<String, StreamingOption>>>(){}.getType();

            Gson gson = new GsonBuilder().create();

            try {
                StreamingAvailability[] results = gson.fromJson(jsonResponse, StreamingAvailability[].class);
                System.out.println("✅ Parsing réussi ! Nombre de résultats : " + (results != null ? results.length : 0));
                return results != null ? List.of(results) : List.of();
            } catch (JsonSyntaxException e) {
                System.err.println("❌ Erreur parsing à la position : " + e.getMessage());
                System.err.println("JSON problématique (premiers 500 chars) : " + jsonResponse.substring(0, Math.min(500, jsonResponse.length())));
                throw e;
            }

//            return streamingAvailabilities;
        }
    }
}
