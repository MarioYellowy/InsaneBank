package com.example.insanebank;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;

import java.io.IOException;

public class HomeController {
    @FXML
    void showGnancias(ActionEvent event) throws IOException {
        Validacion.cambiarVentana(event, "ganancias-view");
    }

    @FXML
    void showLogn(ActionEvent event) throws IOException {
        Validacion.cambiarVentana(event, "login-view");
    }

    @FXML
    void showRgst(ActionEvent event) throws IOException {
        Validacion.cambiarVentana(event, "register-view");
    }
}