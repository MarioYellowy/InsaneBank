package com.example.insanebank;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.input.MouseEvent;

import java.io.IOException;

public class RegisterController {

    @FXML
    void showlogin(ActionEvent event) throws IOException {
        Validacion.cambiarVentana(event, "login-view");
    }

}