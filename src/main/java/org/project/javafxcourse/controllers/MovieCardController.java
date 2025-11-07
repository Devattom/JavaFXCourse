package org.project.javafxcourse.controllers;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.HBox;
import org.project.javafxcourse.interfaces.IMDb.IMDbPopularsInfo;
import org.project.javafxcourse.navigation.NavigationManager;

import java.io.InputStream;

public class MovieCardController {

    @FXML
    private ImageView posterImage;
    @FXML
    private Label titleLabel;
    @FXML
    private HBox starsContainer;
    @FXML
    private Button watchButton;

    private String title;
    private String showType;

    @FXML
    private void onWatchButtonClick(ActionEvent event) {
        // On appelle un gestionnaire global (typiquement via un EventBus, ou une interface)
        NavigationManager.goToStreamingAvailability(title, showType);
    }

    // Set data called by le loader after load()
    public void setData(IMDbPopularsInfo movie) {
        // Titre
        titleLabel.setText(safeString(movie.getPrimaryTitle(), "Titre inconnu"));
        this.title = movie.getPrimaryTitle();
        this.showType = movie.getShowType();

        // Image (chargement asynchrone)
        String imageUrl = movie.getPrimaryImage();
        if (imageUrl != null && !imageUrl.isBlank()) {
            try {
                Image img = new Image(imageUrl, /*requestedWidth*/180, /*requestedHeight*/260, true, true, true);
                posterImage.setImage(img);
            } catch (Exception e) {
                // fallback image from resources si problème
                posterImage.setImage(loadResourcePlaceholder());
            }
        } else {
            posterImage.setImage(loadResourcePlaceholder());
        }

        starsContainer.getChildren().clear();

        double critics = movie.getAverageRating(); // Note IMDb : sur 10
        int stars = (int) Math.round((critics / 10.0) * 5); // sur 5 étoiles
        stars = Math.max(0, Math.min(5, stars)); // borne entre 0 et 5

        for (int i = 0; i < 5; i++) {
            Label star = new Label(i < stars ? "★" : "☆");
            star.setStyle("-fx-font-size: 16px; -fx-text-fill: #f4c542;"); // jaune
            starsContainer.getChildren().add(star);
        }
    }

    private String safeString(String s, String def) {
        return (s == null || s.isBlank()) ? def : s;
    }

    // Charge une image placeholder incluse dans les ressources (resources/images/placeholder.png)
    private Image loadResourcePlaceholder() {
        try {
            InputStream is = getClass().getResourceAsStream("/images/placeholder.png");
            if (is != null) {
                return new Image(is);
            }
        } catch (Exception ignored) {}
        // si pas de ressource, retourne une image vide 1x1
        return new Image("data:image/png;base64,iVBORw0KGgoAAAANSUhEUgAAAAEAAAABCAQAAAC1HAwCAAAAC0lEQVR4nGNgYAAAAAMAASsJTYQAAAAASUVORK5CYII=");
    }
}
