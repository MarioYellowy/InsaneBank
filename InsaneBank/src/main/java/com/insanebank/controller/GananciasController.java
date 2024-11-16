package com.insanebank.controller;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import org.springframework.stereotype.Controller;

@Controller
public class GananciasController {

    @FXML
    private ImageView imageViewGrafico; // Imagen donde se mostrará el gráfico

    private String graficoDeBarrasPath = "src/main/resources/img/graficos/graficoBarras.png";  // Ruta del gráfico de barras
    private String graficoLinealPath = "C:\\Users\\Valentin\\OneDrive\\Proyectos UNIVERSIDAD\\Calculo 1\\Capstone_Insane_Bank\\InsaneBank\\src\\main\\resources\\com\\example\\insanebank\\img\\graficos\\graficoLineal.png";  // Ruta del gráfico lineal

    @FXML
    void mostrarGraficoDeBarras(ActionEvent event) {
        // Cambiar la imagen en el ImageView para mostrar el gráfico de barras
        Image imagenGrafico = new Image("file:" + graficoDeBarrasPath);
        imageViewGrafico.setImage(imagenGrafico);
    }

    @FXML
    void mostrarGraficoDeLineal(ActionEvent event) {
        // Cambiar la imagen en el ImageView para mostrar el gráfico lineal
        Image imagenGrafico = new Image("file:" + graficoLinealPath);
        imageViewGrafico.setImage(imagenGrafico);
    }


    @FXML
    void showGnancias(ActionEvent event) {

    }

    @FXML
    void showHome(ActionEvent event) {

    }

}