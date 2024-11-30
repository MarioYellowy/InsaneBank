package com.insanebank.controller;

import com.insanebank.method.Validacion;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import org.springframework.stereotype.Controller;

import java.io.IOException;

@Controller
public class GananciasController {

    @FXML
    private ImageView imageViewGrafico;

    private String graficoDeBarrasPath = "src/main/resources/img/graficos/graficoBarras.png";
    private String graficoLinealPath = "src/main/resources/img/graficos/graficoLineal.png";

    @FXML
    void mostrarGraficoDeBarras(ActionEvent event) {
        Image imagenGrafico = new Image("file:" + graficoDeBarrasPath);
        imageViewGrafico.setImage(imagenGrafico);
    }

    @FXML
    void mostrarGraficoDeLineal(ActionEvent event) {
        Image imagenGrafico = new Image("file:" + graficoLinealPath);
        imageViewGrafico.setImage(imagenGrafico);
    }

    @FXML
    void showGnancias(ActionEvent event) {
    }

    @FXML
    void showHome(ActionEvent event) {
    }

    @FXML
    void showVentanaAñadirDatos(ActionEvent event) {
        try {
            Validacion.mostrarVentanaModal("añadir-datos");
        } catch (IOException e) {
            e.printStackTrace();
            System.err.println("Error al abrir la ventana de añadir datos: " + e.getMessage());
        }
    }
}