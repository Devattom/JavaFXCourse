package org.project.javafxcourse.adapters;

import com.google.gson.*;
import com.google.gson.stream.JsonReader;
import com.google.gson.stream.JsonToken;
import com.google.gson.stream.JsonWriter;
import org.project.javafxcourse.models.streamingAvailability.StreamingOption;

import java.io.IOException;
import java.lang.reflect.Type;
import java.util.HashMap;
import java.util.Map;

public class StreamingOptionsAdapter extends TypeAdapter<Map<String, Map<String, StreamingOption>>> {

    private final Gson gson = new Gson();

    @Override
    public void write(JsonWriter out, Map<String, Map<String, StreamingOption>> value) throws IOException {
        // Pas besoin d'implémenter pour la lecture seulement
        throw new UnsupportedOperationException("Write not supported");
    }

    @Override
    public Map<String, Map<String, StreamingOption>> read(JsonReader in) throws IOException {
        Map<String, Map<String, StreamingOption>> result = new HashMap<>();

        in.beginObject();
        while (in.hasNext()) {
            String country = in.nextName();
            Map<String, StreamingOption> options = new HashMap<>();

            if (in.peek() == JsonToken.BEGIN_ARRAY) {
                // Cas où c'est un tableau
                in.beginArray();
                int index = 0;
                while (in.hasNext()) {
                    StreamingOption option = gson.fromJson(in, StreamingOption.class);
                    options.put(String.valueOf(index++), option);
                }
                in.endArray();
            } else if (in.peek() == JsonToken.BEGIN_OBJECT) {
                // Cas où c'est un objet (ancienne logique)
                in.beginObject();
                while (in.hasNext()) {
                    String index = in.nextName();
                    StreamingOption option = gson.fromJson(in, StreamingOption.class);
                    options.put(index, option);
                }
                in.endObject();
            }

            result.put(country, options);
        }
        in.endObject();

        return result;
    }

}