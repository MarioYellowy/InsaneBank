package com.insanebank.controller;

import javafx.fxml.FXML;
import javafx.scene.control.TextField;
import javafx.scene.control.Label;
import javafx.scene.control.Button;
import javafx.scene.layout.StackPane;
import javafx.stage.Stage;
import javafx.scene.chart.LineChart;
import javafx.scene.chart.NumberAxis;
import javafx.scene.chart.XYChart;
import org.springframework.stereotype.Controller;
import javafx.application.Platform;

import java.math.BigDecimal;
import java.math.RoundingMode;

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
    private StackPane chartContainer;

    @FXML
    private void generarGrafico() {
        if (validarCampos()) {
            BigDecimal monto = new BigDecimal(montoTextField.getText());
            BigDecimal tasaInflacion = new BigDecimal(tasaInflacionTextField.getText());
            int tiempoEstimado = Integer.parseInt(tiempoEstimadoTextField.getText());

            // Mostrar la animación de carga
            loadingPane.setVisible(true);
            generarGraficoButton.setDisable(true);

            // Generar el gráfico en un hilo separado
            new Thread(() -> {
                LineChart<Number, Number> lineChart = crearGrafico(monto, tasaInflacion, tiempoEstimado);

                // Actualizar la UI en el hilo de JavaFX
                Platform.runLater(() -> {
                    loadingPane.setVisible(false);
                    generarGraficoButton.setDisable(false);

                    if (lineChart != null) {
                        chartContainer.getChildren().clear();
                        chartContainer.getChildren().add(lineChart);
                        mostrarMensaje("Gráfico generado correctamente.");
                    } else {
                        mostrarMensaje("Error al generar el gráfico. Por favor, intente nuevamente.");
                    }
                });
            }).start();
        }
    }

    private LineChart<Number, Number> crearGrafico(BigDecimal montoInicial, BigDecimal tasaInteres, int años) {
        NumberAxis xAxis = new NumberAxis(0, años, 1);
        NumberAxis yAxis = new NumberAxis();
        xAxis.setLabel("Años");
        yAxis.setLabel("Monto");

        LineChart<Number, Number> lineChart = new LineChart<>(xAxis, yAxis);
        lineChart.setTitle("Proyección de Crecimiento del Saldo");

        XYChart.Series<Number, Number> seriesCrecimiento = new XYChart.Series<>();
        seriesCrecimiento.setName("Saldo");

        XYChart.Series<Number, Number> seriesTasaCambio = new XYChart.Series<>();
        seriesTasaCambio.setName("Tasa de Cambio (Derivada)");

        BigDecimal montoActual = montoInicial;
        BigDecimal tasaInteresDecimal = tasaInteres.divide(new BigDecimal("100"), 4, RoundingMode.HALF_UP);
        BigDecimal montoAnterior = BigDecimal.ZERO;

        for (int i = 0; i <= años; i++) {
            seriesCrecimiento.getData().add(new XYChart.Data<>(i, montoActual.doubleValue()));

            if (i > 0) {
                BigDecimal tasaCambio = montoActual.subtract(montoAnterior);
                seriesTasaCambio.getData().add(new XYChart.Data<>(i, tasaCambio.doubleValue()));
            }

            montoAnterior = montoActual;
            montoActual = montoActual.multiply(BigDecimal.ONE.add(tasaInteresDecimal)).setScale(2, RoundingMode.HALF_UP);
        }

        lineChart.getData().addAll(seriesCrecimiento, seriesTasaCambio);
        return lineChart;
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
                mostrarMensaje("La tasa de interés debe estar entre 0 y 100.");
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

    @FXML
    private void cancelar() {
        cerrarVentana();
    }

    private void cerrarVentana() {
        Stage stage = (Stage) montoTextField.getScene().getWindow();
        stage.close();
    }
}
