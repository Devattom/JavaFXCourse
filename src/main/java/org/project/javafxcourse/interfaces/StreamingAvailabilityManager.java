package org.project.javafxcourse.interfaces;

import org.project.javafxcourse.models.StreamingAvailability;

public interface StreamingAvailabilityManager {
    public StreamingAvailability getByNameAndCountry(String name, String country) throws Exception;
}
