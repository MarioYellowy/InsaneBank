package com.insanebank.controller;

import com.insanebank.Validacion;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;

import java.io.IOException;

public class HomeController {
    @FXML
    void showLogn(ActionEvent event) throws IOException {
        Validacion.cambiarVentana(event, "login-view");
    }

    @FXML
    void showRgst(ActionEvent event) throws IOException {
        Validacion.cambiarVentana(event, "register-view");
    }
}