package com.example.insanebank;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;

import java.io.IOException;

public class LoginController {
    @FXML
    private void showRegister(ActionEvent event) throws IOException {
        Validacion.cambiarVentana(event, "register-view");
    }
}
