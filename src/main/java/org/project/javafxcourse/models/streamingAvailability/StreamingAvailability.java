package org.project.javafxcourse.models.streamingAvailability;

import lombok.Data;

import java.util.List;
import java.util.Map;

@Data
public class StreamingAvailability {
    private String title;
    private String overview;
    private Map<String, List<StreamingOption>> streamingOptions;
    private ImageSet imageSet;
}
