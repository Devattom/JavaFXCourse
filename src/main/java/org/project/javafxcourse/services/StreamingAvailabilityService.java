package org.project.javafxcourse.services;

import javafx.concurrent.Task;
import org.project.javafxcourse.models.streamingAvailability.StreamingAvailability;
import org.project.javafxcourse.repositories.streamingAvailability.StreamingAvailabilityApiRepository;

import java.util.List;

public class StreamingAvailabilityService {
    
    private final StreamingAvailabilityApiRepository repository;
    
    public StreamingAvailabilityService() {
        this.repository = new StreamingAvailabilityApiRepository();
    }
    
    /**
     * Crée une Task pour rechercher les disponibilités de streaming
     * @param title Le titre à rechercher
     * @param country Le pays (ex: "fr")
     * @param showType Le type (movie ou series)
     * @return Une Task qui retourne la liste des résultats
     */
    public Task<List<StreamingAvailability>> searchStreamingAvailability(
            String title, 
            String country, 
            String showType
    ) {
        Task<List<StreamingAvailability>> task = new Task<>() {
            @Override
            protected List<StreamingAvailability> call() throws Exception {
                return repository.getByNameAndCountry(title, country, showType);
            }
        };
        
        return task;
    }
}

