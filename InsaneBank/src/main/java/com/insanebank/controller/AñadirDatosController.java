package com.insanebank.controller;

import javafx.fxml.FXML;
import javafx.scene.control.TextField;
import javafx.scene.control.Label;
import javafx.scene.control.Button;
import javafx.scene.layout.StackPane;
import javafx.stage.Stage;
import org.springframework.stereotype.Controller;
import javafx.application.Platform;

import java.math.BigDecimal;
import java.util.concurrent.CompletableFuture;

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
    private Button generarGraficoButton;

    @FXML
    private StackPane loadingPane;

    @FXML
    private void generarGrafico() {
        if (validarCampos()) {
            BigDecimal monto = new BigDecimal(montoTextField.getText());
            BigDecimal tasaInflacion = new BigDecimal(tasaInflacionTextField.getText());
            int tiempoEstimado = Integer.parseInt(tiempoEstimadoTextField.getText());

            // Mostrar la animación de carga
            loadingPane.setVisible(true);
            generarGraficoButton.setDisable(true);

            // Simular el proceso de generación del gráfico de forma asíncrona
            CompletableFuture.supplyAsync(() -> {
                try {
                    // Simulamos un proceso que toma tiempo
                    Thread.sleep(3000);
                    return simularGuardarDatos(monto, tasaInflacion, tiempoEstimado);
                } catch (InterruptedException e) {
                    return false;
                }
            }).thenAccept(datosGuardados -> {
                // Volvemos al hilo de la UI para actualizar la interfaz
                Platform.runLater(() -> {
                    loadingPane.setVisible(false);
                    generarGraficoButton.setDisable(false);

                    if (datosGuardados) {
                        mostrarMensaje("Datos guardados y gráfico generado correctamente.");
                        cerrarVentana();
                    } else {
                        mostrarMensaje("Error al guardar los datos. Por favor, intente nuevamente.");
                    }
                });
            });
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
        // Aquí se implementaría la lógica real para guardar los datos y generar el gráfico
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