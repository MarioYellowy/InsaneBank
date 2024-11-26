package com.insanebank.controller;

import javafx.fxml.FXML;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import org.springframework.stereotype.Controller;

@Controller
public class AñadirDatosController {

    @FXML
    private TextField montoTextField;

    @FXML
    private TextField tasaInflacionTextField;

    @FXML
    private TextField tiempoEstimadoTextField;

    @FXML
    private void generarGrafico() {
        // Aquí iría la lógica para generar el gráfico
        String monto = montoTextField.getText();
        String tasaInflacion = tasaInflacionTextField.getText();
        String tiempoEstimado = tiempoEstimadoTextField.getText();

        System.out.println("Generando gráfico con los siguientes datos:");
        System.out.println("Monto: " + monto);
        System.out.println("Tasa de inflación: " + tasaInflacion);
        System.out.println("Tiempo estimado: " + tiempoEstimado);

        // Aquí iria la lógica para generar el gráfico
        // Por ahora, solo cerraremos la ventana
        cerrarVentana();
    }

    @FXML
    private void cancelar() {
        cerrarVentana();
    }

    private void cerrarVentana() {
        Stage stage = (Stage) montoTextField.getScene().getWindow();
        stage.close();
    }
}