package com.insanebank.controller;

import javafx.fxml.FXML;
import javafx.scene.control.TextField;
import javafx.scene.control.Label;
import javafx.stage.Stage;
import org.springframework.stereotype.Controller;

import java.math.BigDecimal;

@Controller
public class AñadirDatosController {

    @FXML
    private TextField montoTextField;

    @FXML
    private TextField tasaInflacionTextField;

    @FXML
    private TextField tiempoEstimadoTextField;

    @FXML
    private Label mensajeErrorLabel;

    @FXML
    private void generarGrafico() {
        if (validarCampos()) {
            BigDecimal monto = new BigDecimal(montoTextField.getText());
            BigDecimal tasaInflacion = new BigDecimal(tasaInflacionTextField.getText());
            int tiempoEstimado = Integer.parseInt(tiempoEstimadoTextField.getText());

            // Aquí iría la lógica para generar el gráfico
            System.out.println("Generando gráfico con los siguientes datos:");
            System.out.println("Monto: " + monto);
            System.out.println("Tasa de inflación: " + tasaInflacion);
            System.out.println("Tiempo estimado: " + tiempoEstimado);

            // Aquí se llamaría al servicio para guardar los datos y generar el gráfico
            // Por ahora, solo simularemos que se ha guardado correctamente
            boolean datosGuardados = simularGuardarDatos(monto, tasaInflacion, tiempoEstimado);

            if (datosGuardados) {
                mostrarMensaje("Datos guardados y gráfico generado correctamente.");
                cerrarVentana();
            } else {
                mostrarMensaje("Error al guardar los datos. Por favor, intente nuevamente.");
            }
        }
    }

    private boolean validarCampos() {
        String monto = montoTextField.getText();
        String tasaInflacion = tasaInflacionTextField.getText();
        String tiempoEstimado = tiempoEstimadoTextField.getText();

        if (monto.isEmpty() || tasaInflacion.isEmpty() || tiempoEstimado.isEmpty()) {
            mostrarMensaje("Todos los campos son obligatorios.");
            return false;
        }

        try {
            BigDecimal montoDecimal = new BigDecimal(monto);
            if (montoDecimal.compareTo(BigDecimal.ZERO) <= 0) {
                mostrarMensaje("El monto debe ser mayor que cero.");
                return false;
            }

            BigDecimal tasaInflacionDecimal = new BigDecimal(tasaInflacion);
            if (tasaInflacionDecimal.compareTo(BigDecimal.ZERO) < 0 || tasaInflacionDecimal.compareTo(new BigDecimal("100")) > 0) {
                mostrarMensaje("La tasa de inflación debe estar entre 0 y 100.");
                return false;
            }

            int tiempoEstimadoInt = Integer.parseInt(tiempoEstimado);
            if (tiempoEstimadoInt <= 0) {
                mostrarMensaje("El tiempo estimado debe ser mayor que cero.");
                return false;
            }
        } catch (NumberFormatException e) {
            mostrarMensaje("Por favor, ingrese valores numéricos válidos.");
            return false;
        }

        return true;
    }

    private void mostrarMensaje(String mensaje) {
        mensajeErrorLabel.setText(mensaje);
        mensajeErrorLabel.setVisible(true);
    }

    private boolean simularGuardarDatos(BigDecimal monto, BigDecimal tasaInflacion, int tiempoEstimado) {
        // Aquí se implementaría la lógica real para guardar los datos
        // Por ahora, simplemente retornamos true para simular éxito
        return true;
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