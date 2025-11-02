package org.project.javafxcourse.controllers;

import javafx.fxml.FXML;
import javafx.scene.control.Label;
import org.project.javafxcourse.repositories.StreamingAvailabilityApiRepository;

public class HelloController {
    @FXML
    private Label welcomeText;

    @FXML
    protected void onHelloButtonClick() {
        StreamingAvailabilityApiRepository streamingRepository = new StreamingAvailabilityApiRepository();

        try {
            streamingRepository.getByNameAndCountry("Alice in borderlands", "fr");
        } catch (Exception ex) {
            welcomeText.setText(ex.getMessage());
            ex.printStackTrace();
        }
    }
}
