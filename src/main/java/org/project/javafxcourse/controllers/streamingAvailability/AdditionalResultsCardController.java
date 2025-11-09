package org.project.javafxcourse.controllers.streamingAvailability;

import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.image.ImageView;
import org.project.javafxcourse.models.streamingAvailability.StreamingAvailability;
import org.project.javafxcourse.navigation.NavigationManager;
import org.project.javafxcourse.services.ImageService;

public class AdditionalResultsCardController {

    private final ImageService imageService = new ImageService();

    @FXML private ImageView posterImage;
    @FXML private Label titleLabel;
    @FXML private Button selectButton;

    private String showType;
    private String title;

    public void setData(StreamingAvailability result) {
        // callback pour la sélection
        this.title = result.getTitle() != null ? result.getTitle() : "Titre inconnu";
        this.showType = result.getShowType();

        titleLabel.setText(this.title);

        String imageUrl = result.getImageSet() != null && result.getImageSet().getVerticalPoster() != null
                ? result.getImageSet().getVerticalPoster().getW360()
                : null;

        if (imageUrl != null && !imageUrl.isBlank()) {
            imageService.loadImage(posterImage, imageUrl, 180, 260);
        }
    }

    public void onWatchButtonClick() {
        NavigationManager.goToStreamingAvailability(title, showType);
    }
}
