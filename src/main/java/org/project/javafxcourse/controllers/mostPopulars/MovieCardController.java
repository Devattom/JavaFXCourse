package org.project.javafxcourse.controllers.mostPopulars;

import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.HBox;
import org.project.javafxcourse.interfaces.IMDb.IMDbPopularsInfo;
import org.project.javafxcourse.navigation.NavigationManager;
import org.project.javafxcourse.repositories.history.HistoryRepository;

public class MovieCardController {

    @FXML
    public Button watchButton;
    @FXML
    private ImageView posterImage;
    @FXML
    private Label titleLabel;
    @FXML
    private HBox starsContainer;

    private String title;
    private String showType;

    /**
     * gestion du clic sur le bouton
     */
    @FXML
    private void onWatchButtonClick() {
        var historyRepo = new HistoryRepository();
        historyRepo.addHistory(title, showType);
        NavigationManager.goToStreamingAvailability(title, showType);
    }

    /**
     * Set les data pour les movie card
     * @param movie infos sur l"élément à afficher
     */
    public void setData(IMDbPopularsInfo movie) {
        // Titre
        titleLabel.setText(movie.getPrimaryTitle() != null && !movie.getPrimaryTitle().isBlank()
                ? movie.getPrimaryTitle()
                : "Titre inconnu"
        );

        this.title = movie.getPrimaryTitle();
        this.showType = movie.getShowType();

        String imageUrl = movie.getPrimaryImage();
        if (imageUrl != null && !imageUrl.isBlank()) {
            try {
                Image img = new Image(imageUrl, /*requestedWidth*/180, /*requestedHeight*/260, true, true, true);
                posterImage.setImage(img);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        starsContainer.getChildren().clear();

        Double ratingObj = movie.getAverageRating();
        double critics = (ratingObj != null) ? ratingObj : 0.0;

        int stars = (int) Math.round((critics / 10.0) * 5); // sur 5 étoiles
        stars = Math.max(0, Math.min(5, stars)); // borne entre 0 et 5

        for (int i = 0; i < 5; i++) {
            Label star = new Label(i < stars ? "★" : "☆");
            star.setStyle("-fx-font-size: 16px; -fx-text-fill: #f4c542;"); // jaune
            starsContainer.getChildren().add(star);
        }
    }
}
