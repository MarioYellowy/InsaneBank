module com.insanebank {
    requires javafx.controls;
    requires javafx.fxml;
    requires javafx.web;
    requires org.controlsfx.controls;
    requires com.dlsc.formsfx;
    requires net.synedra.validatorfx;
    requires org.kordamp.ikonli.javafx;
    requires org.kordamp.bootstrapfx.core;
    requires eu.hansolo.tilesfx;
    requires com.almasb.fxgl.all;

    requires java.desktop;
    requires spring.context;
    requires jakarta.persistence;
    requires static lombok;
    requires spring.data.jpa;
    requires org.slf4j;

    exports com.insanebank;
    exports com.insanebank.controller;

    opens com.insanebank to javafx.fxml;
    opens com.insanebank.controller to javafx.fxml;
    opens com.insanebank.config;
}
