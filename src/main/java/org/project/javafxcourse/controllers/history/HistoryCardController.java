package org.project.javafxcourse.controllers.history;

import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import lombok.Setter;
import org.project.javafxcourse.models.entities.History;
import org.project.javafxcourse.navigation.NavigationManager;
import org.project.javafxcourse.repositories.history.HistoryRepository;

import java.time.format.DateTimeFormatter;

public class HistoryCardController {

    @FXML
    private Label iconLabel;

    @FXML
    private Label titleLabel;

    @FXML
    private Label typeLabel;

    @FXML
    private Label dateLabel;

    @FXML
    private Button viewButton;

    private History history;

    /**
     * gestion du clic sur le bouton
     */
    @FXML
    private void onWatchButtonClick() {
        NavigationManager.goToStreamingAvailability(history.getTitle(), history.getShowType());
    }

    public void setData(History history) {
        this.history = history;

        // Titre
        titleLabel.setText(history.getTitle());

        // Type et icône
        String type = history.getShowType();
        if ("movie".equalsIgnoreCase(type)) {
            typeLabel.setText("🎬 Film");
            iconLabel.setText("🎬");
        } else if ("series".equalsIgnoreCase(type)) {
            typeLabel.setText("📺 Série");
            iconLabel.setText("📺");
        } else {
            typeLabel.setText(type);
            iconLabel.setText("🎭");
        }

        // Date
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy à HH:mm");
        dateLabel.setText("Consulté le " + history.getConsultedAt().format(formatter));

    }
}