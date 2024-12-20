module com.insane.config {
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

    requires java.sql;
    requires io.github.cdimascio.dotenv.java;

    requires spring.context;
    requires jbcrypt;
    requires org.jfree.jfreechart;
    requires javafx.swing;

    exports com.insanebank;
    exports com.insanebank.controller;

    opens com.insanebank to javafx.fxml;
    opens com.insanebank.controller to javafx.fxml;
    opens com.insanebank.config;
    exports com.insanebank.method;
    opens com.insanebank.method to javafx.fxml;
}
