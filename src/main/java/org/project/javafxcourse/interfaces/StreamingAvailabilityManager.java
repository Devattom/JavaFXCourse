package org.project.javafxcourse.interfaces;

import org.project.javafxcourse.models.streamingAvailability.StreamingAvailability;

import java.util.List;

public interface StreamingAvailabilityManager {
    public List<StreamingAvailability> getByNameAndCountry(String name, String country, String showType) throws Exception;
}
