package com.insanebank.controller;

import javafx.fxml.FXML;
import javafx.scene.control.TextField;
import javafx.scene.control.Label;
import javafx.scene.control.Button;
import javafx.scene.layout.StackPane;
import javafx.stage.Stage;
import org.jfree.chart.ChartFactory;
import org.jfree.chart.ChartPanel;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.plot.PlotOrientation;
import org.jfree.data.category.DefaultCategoryDataset;
import org.springframework.stereotype.Controller;
import javafx.application.Platform;
import javafx.embed.swing.SwingNode;

import java.awt.*;
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
    private Button cancelarButton;

    @FXML
    private Button generarGraficoButton;

    @FXML
    private StackPane loadingPane;

    @FXML
    private StackPane chartContainer;

    @FXML
    private void generarGrafico() {
        if (!validarCampos()) return;

        loadingPane.setVisible(true);

        montoTextField.setDisable(true);
        tasaInflacionTextField.setDisable(true);
        tiempoEstimadoTextField.setDisable(true);
        generarGraficoButton.setDisable(true);
        cancelarButton.setDisable(true);

        new Thread(() -> {
            try {

                Thread.sleep(1500);

                Platform.runLater(() -> {
                    BigDecimal monto = new BigDecimal(montoTextField.getText());
                    BigDecimal tasa = new BigDecimal(tasaInflacionTextField.getText());
                    int tiempo = Integer.parseInt(tiempoEstimadoTextField.getText());

                    JFreeChart chart = crearGrafico(monto, tasa, tiempo);

                    SwingNode swingNode = new SwingNode();
                    swingNode.setContent(new ChartPanel(chart));

                    chartContainer.getChildren().clear();
                    chartContainer.getChildren().add(swingNode);

                    loadingPane.setVisible(false);

                    montoTextField.setDisable(false);
                    tasaInflacionTextField.setDisable(false);
                    tiempoEstimadoTextField.setDisable(false);
                    generarGraficoButton.setDisable(false);
                    cancelarButton.setDisable(false);
                });
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }).start();
    }


    private JFreeChart crearGrafico(BigDecimal montoInicial, BigDecimal tasaInteres, int años) {
        DefaultCategoryDataset dataset = new DefaultCategoryDataset();

        BigDecimal montoActual = montoInicial;
        BigDecimal tasaInteresDecimal = tasaInteres.divide(new BigDecimal("100"), 4, RoundingMode.HALF_UP);

        for (int i = 0; i <= años; i++) {
            dataset.addValue(montoActual.doubleValue(), "Saldo", String.valueOf(i));
            montoActual = montoActual.multiply(BigDecimal.ONE.add(tasaInteresDecimal)).setScale(2, RoundingMode.HALF_UP);
        }

        JFreeChart chart = ChartFactory.createLineChart(
                "Proyección de Crecimiento del Saldo",
                "Años",
                "Monto",
                dataset,
                PlotOrientation.VERTICAL,
                true,
                true,
                false
        );

        chart.getTitle().setFont(new Font("Yu Gothic Regular", Font.BOLD, 18));
        chart.getTitle().setPaint(Color.decode("#FFFFFF"));
        chart.getCategoryPlot().getDomainAxis().setLabelFont(new Font("Yu Gothic Regular", Font.PLAIN, 14));
        chart.getCategoryPlot().getDomainAxis().setTickLabelFont(new Font("Yu Gothic Regular", Font.ITALIC, 12));
        chart.getCategoryPlot().getRangeAxis().setLabelFont(new Font("Yu Gothic Regular", Font.BOLD, 14));
        chart.getCategoryPlot().getRangeAxis().setTickLabelFont(new Font("Yu Gothic Regular", Font.PLAIN, 12));
        chart.setBackgroundPaint(Color.BLACK);
        chart.getPlot().setBackgroundPaint(Color.WHITE);
        chart.getCategoryPlot().getDomainAxis().setLabelPaint(Color.WHITE);
        chart.getCategoryPlot().getDomainAxis().setTickLabelPaint(Color.WHITE);
        chart.getCategoryPlot().getRangeAxis().setLabelPaint(Color.WHITE);
        chart.getCategoryPlot().getRangeAxis().setTickLabelPaint(Color.WHITE);


        return chart;
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
