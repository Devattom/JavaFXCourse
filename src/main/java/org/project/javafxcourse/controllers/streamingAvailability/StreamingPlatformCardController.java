package org.project.javafxcourse.controllers.streamingAvailability;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.image.ImageView;
import org.project.javafxcourse.models.streamingAvailability.StreamingOption;
import org.project.javafxcourse.services.ImageService;

import java.awt.Desktop;
import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.Optional;

public class StreamingPlatformCardController {

    private final ImageService imageService = new ImageService();

    @FXML
    private ImageView platformImage;

    @FXML
    private Label platformNameLabel;

    @FXML
    private Button watchButton;

    private String watchUrl;

    @FXML
    private void onWatchButtonClick(ActionEvent event) {
        if (this.watchUrl != null && !this.watchUrl.isBlank()) {
            try {
                Desktop.getDesktop().browse(new URI(this.watchUrl));
            } catch (IOException | URISyntaxException e) {
                System.err.println("Erreur lors de l'ouverture du lien : " + this.watchUrl);
                e.printStackTrace();
            }
        }
    }

    /**
     * Initialise la card avec les données du option de streaming
     */
    public void setData(StreamingOption option) {
        var service = option.getService();

        this.setWatchUrl(option.getLink(), service.getHomePage());

        String platformName = Optional.ofNullable(option.getService().getName())
                .filter(name -> !name.isBlank())
                .orElse("Plateforme inconnue");

        platformNameLabel.setText(platformName);

        String imageUrl = service.getImageSet() != null && service.getImageSet().getLightThemeImage() != null
                ? service.getImageSet().getLightThemeImage()
                : null;

        if (imageUrl != null && !imageUrl.isBlank()) {
            imageService.loadImage(platformImage, imageUrl, 80, 50);
        }
    }

    private void setWatchUrl(String link, String homePage) {
        this.watchUrl = link == null || link.isBlank()
                ? homePage
                : link;
    }
}
