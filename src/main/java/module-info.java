module org.project.javafxcourse {
    requires javafx.controls;
    requires javafx.fxml;

    requires org.kordamp.bootstrapfx.core;
    requires okhttp3;

    opens org.project.javafxcourse to javafx.fxml;
    exports org.project.javafxcourse;
    exports org.project.javafxcourse.controllers;
    opens org.project.javafxcourse.controllers to javafx.fxml;
}