package com.example.insanebank;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;

import java.io.IOException;

public class GananciasController {

    @FXML
    void showGnancias(ActionEvent event) {

    }


    @FXML
    void showHome(ActionEvent event) throws IOException {
        Validacion.cambiarVentana(event, "home-view");
    }

}
