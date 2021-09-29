module com.jam.jamcougstatistics {
    requires javafx.controls;
    requires javafx.fxml;
    requires javafx.web;

    requires org.controlsfx.controls;
    requires com.dlsc.formsfx;
    requires validatorfx;
    requires org.kordamp.bootstrapfx.core;
    requires eu.hansolo.tilesfx;
    requires commons.math3;

    opens com.jam.jamcougstatistics to javafx.fxml;
    exports com.jam.jamcougstatistics;
}