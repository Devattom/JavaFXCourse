module org.project.javafxcourse {
    requires javafx.controls;
    requires javafx.fxml;

    requires org.kordamp.bootstrapfx.core;

    opens org.project.javafxcourse to javafx.fxml;
    exports org.project.javafxcourse;
}