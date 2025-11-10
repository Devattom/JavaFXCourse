package org.project.javafxcourse.controllers.history;

import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import lombok.Setter;
import org.project.javafxcourse.models.entities.History;

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
    @Setter
    private Runnable onViewAction;

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
        dateLabel.setText("Consulté le " + history.getCreatedAt().format(formatter));

        // Bouton Voir
        viewButton.setOnAction(e -> {
            if (onViewAction != null) {
                onViewAction.run();
            }
        });
    }
}