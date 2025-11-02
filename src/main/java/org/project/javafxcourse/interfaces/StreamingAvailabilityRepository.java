package org.project.javafxcourse.interfaces;

import org.project.javafxcourse.models.StreamingAvailability;

import java.util.ArrayList;

public interface StreamingAvailabilityRepository {
    public StreamingAvailability getByNameAndCountry(String name, String country) throws Exception;
}
