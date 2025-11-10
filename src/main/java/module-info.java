module org.project.javafxcourse {
    requires javafx.controls;
    requires javafx.fxml;

    requires org.kordamp.bootstrapfx.core;
    requires okhttp3;
    requires com.google.gson;
    requires static lombok;
    requires javafx.graphics;
    requires java.desktop;
    requires javafx.web;
    requires jakarta.persistence;
    requires org.hibernate.orm.core;

    // Ouvre les contrôleurs à JavaFX (pour FXML)
    opens org.project.javafxcourse.controllers to javafx.fxml;

    // Ouvre les modèles à Gson et JavaFX (pour la désérialisation et les bindings)
    opens org.project.javafxcourse.models.IMDb.populars to com.google.gson, javafx.base;
    opens org.project.javafxcourse.models.streamingAvailability to com.google.gson, javafx.base;

    // Exporte les packages utiles
    exports org.project.javafxcourse;
    exports org.project.javafxcourse.controllers;
    exports org.project.javafxcourse.models.IMDb.populars;
    exports org.project.javafxcourse.controllers.streamingAvailability;
    opens org.project.javafxcourse.controllers.streamingAvailability to javafx.fxml;
    exports org.project.javafxcourse.controllers.mostPopulars;
    opens org.project.javafxcourse.controllers.mostPopulars to javafx.fxml;
    exports org.project.javafxcourse.config;
}
