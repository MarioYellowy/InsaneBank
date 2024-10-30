package com.example.insanebank;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;

import java.io.IOException;

public class LoginController {
    @FXML
    void showRegister(ActionEvent event) throws IOException {
        Validacion.cambiarVentana(event, "register-view");
    }
    @FXML
    void showGnancias(ActionEvent event)  throws IOException {
        Validacion.cambiarVentana(event, "ganancias-view");
    }

    @FXML
    void showHome(ActionEvent event)  throws IOException {
        Validacion.cambiarVentana(event, "home-view");
    }

    @FXML
    void showLogn(ActionEvent event)  throws IOException {
        Validacion.cambiarVentana(event, "login-view");
    }
}
