package com.insanebank.controller;

import com.insanebank.Validacion;
import javafx.animation.ScaleTransition;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Hyperlink;
import javafx.util.Duration;
import org.springframework.stereotype.Controller;

import java.io.IOException;

@Controller
public class LoginController {
    @FXML
    void showRegister(ActionEvent event) throws IOException {
        Validacion.cambiarVentana(event, "/fxml/register-view");
    }

    @FXML
    void showHome(ActionEvent event) throws IOException {
        Validacion.cambiarVentana(event, "/fxml/home-view");
    }

    @FXML
    private Button btnCrearCuenta;
    @FXML
    private Hyperlink hyperCrearCuenta;

    public void initialize() {

        ScaleTransition scaleTransitionBTN = new ScaleTransition(Duration.millis(200), btnCrearCuenta);
        ScaleTransition scaleTransitionHyper = new ScaleTransition(Duration.millis(200), hyperCrearCuenta);

        scaleTransitionBTN.setFromX(1.0);
        scaleTransitionBTN.setFromY(1.0);
        scaleTransitionBTN.setToX(1.1);
        scaleTransitionBTN.setToY(1.1);

        scaleTransitionHyper.setFromX(1.0);
        scaleTransitionHyper.setFromY(1.0);
        scaleTransitionHyper.setToX(1.1);
        scaleTransitionHyper.setToY(1.1);

        btnCrearCuenta.setOnMouseEntered(event -> scaleTransitionBTN.playFromStart());
        hyperCrearCuenta.setOnMouseEntered(event -> scaleTransitionHyper.playFromStart());

        btnCrearCuenta.setOnMouseExited(event -> {
            scaleTransitionBTN.setRate(-1);
            scaleTransitionBTN.play();
        });
        hyperCrearCuenta.setOnMouseExited(event -> {
            scaleTransitionHyper.setRate(-1);
            scaleTransitionHyper.play();
        });
    }
}