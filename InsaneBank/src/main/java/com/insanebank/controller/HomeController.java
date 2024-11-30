package com.insanebank.controller;

import com.insanebank.method.Validacion;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import org.springframework.stereotype.Controller;

import java.io.IOException;

@Controller
public class HomeController {
    @FXML
    public void showLogn(ActionEvent event) throws IOException {
        Validacion.cambiarVentana(event, "/fxml/login-view");
    }

    @FXML
    public void showRgst(ActionEvent event) throws IOException {
        Validacion.cambiarVentana(event, "/fxml/register-view");
    }
}