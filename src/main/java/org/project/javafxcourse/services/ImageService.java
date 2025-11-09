package org.project.javafxcourse.services;

import javafx.application.Platform;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.Pane;
import javafx.scene.web.WebView;

import java.util.Objects;

public class ImageService {
    
    /**
     * Charge une image dans un ImageView, en gérant automatiquement les SVG avec un WebView
     * @param imageView L'ImageView à remplacer si c'est un SVG
     * @param imageUrl L'URL de l'image
     * @param width La largeur souhaitée
     * @param height La hauteur souhaitée (0 pour préserver le ratio de l'ImageView)
     */
    public void loadImage(ImageView imageView, String imageUrl, double width, double height) {
        if (imageUrl == null || imageUrl.isBlank()) {
            return;
        }
        if (isSvg(imageUrl)) {
            System.out.println("IS SVG");
            // Si height est 0 ou négatif, calculer la hauteur à partir de l'ImageView
            double actualHeight = height > 0 ? height : calculateHeight(imageView, width);
            loadSvgImage(imageView, imageUrl, width, actualHeight);
        } else {
            loadRegularImage(imageView, imageUrl);
        }
    }
    
    /**
     * Calcule la hauteur à partir de l'ImageView si elle n'est pas spécifiée
     */
    private double calculateHeight(ImageView imageView, double width) {
        // Essayer d'obtenir fitHeight
        double fitHeight = imageView.getFitHeight();
        if (fitHeight > 0) {
            return fitHeight;
        }
        
        // Si fitHeight n'est pas défini, utiliser la hauteur actuelle de l'ImageView
        double currentHeight = imageView.getBoundsInLocal().getHeight();
        if (currentHeight > 0) {
            return currentHeight;
        }
        
        // Si l'ImageView a une image chargée, utiliser son ratio
        if (imageView.getImage() != null) {
            double imageHeight = imageView.getImage().getHeight();
            double imageWidth = imageView.getImage().getWidth();
            if (imageWidth > 0 && imageHeight > 0) {
                return (width * imageHeight) / imageWidth;
            }
        }
        
        // Valeur par défaut : ratio 2:3 (poster typique)
        return (width * 3) / 2;
    }
    
    /**
     * Vérifie si l'URL pointe vers un fichier SVG
     */
    private boolean isSvg(String imageUrl) {
        return imageUrl.endsWith(".svg") || imageUrl.contains(".svg");
    }
    
    /**
     * Charge une image SVG dans un WebView et remplace l'ImageView
     */
    private void loadSvgImage(ImageView imageView, String imageUrl, double width, double height) {
        WebView webView = new WebView();
        webView.setPrefSize(width, height);
        webView.setMaxSize(width, height);
        webView.setMinSize(width, height);
        webView.setZoom(1.0);
        webView.setStyle("-fx-background-color: transparent;");
        
        // Désactiver les scrollbars et adapter le SVG à la taille
        webView.getEngine().getLoadWorker().stateProperty().addListener((obs, oldState, newState) -> {
            if (newState == javafx.concurrent.Worker.State.SUCCEEDED) {
                String script = 
                    "if (document.body) { " +
                    "  document.body.style.margin = '0'; " +
                    "  document.body.style.padding = '0'; " +
                    "  document.body.style.overflow = 'hidden'; " +
                    "} " +
                    "if (document.documentElement) { " +
                    "  document.documentElement.style.margin = '0'; " +
                    "  document.documentElement.style.padding = '0'; " +
                    "  document.documentElement.style.overflow = 'hidden'; " +
                    "} " +
                    "var svg = document.querySelector('svg') || document.documentElement; " +
                    "if (svg && svg.tagName === 'svg') { " +
                    "  svg.style.width = '100%'; " +
                    "  svg.style.height = '100%'; " +
                    "  svg.style.display = 'block'; " +
                    "  svg.setAttribute('preserveAspectRatio', 'xMidYMid meet'); " +
                    "}";
                webView.getEngine().executeScript(script);
            }
        });
        
        webView.getEngine().load(imageUrl);
        imageView.setVisible(false);
        
        // Remplacer l'ImageView par le WebView dans son parent
        // Utiliser Platform.runLater pour s'assurer que le layout est fait
        Platform.runLater(() -> {
            if (imageView.getParent() instanceof Pane parentPane) {
                int index = parentPane.getChildren().indexOf(imageView);
                if (index >= 0) {
                    parentPane.getChildren().remove(imageView);
                    parentPane.getChildren().add(index, webView);
                    System.out.println("WebView ajouté au parent à l'index " + index);
                } else {
                    System.err.println("ImageView non trouvé dans le parent");
                }
            } else {
                System.err.println("Parent n'est pas un Pane: " + (imageView.getParent() != null ? imageView.getParent().getClass().getName() : "null"));
            }
        });
    }
    
    /**
     * Charge une image régulière (non-SVG) dans l'ImageView
     */
    private void loadRegularImage(ImageView imageView, String imageUrl) {
        try {
            imageView.setImage(new Image(imageUrl, true));
        } catch (Exception e) {
            System.err.println("Erreur chargement image : " + e.getMessage());
        }
    }
}

