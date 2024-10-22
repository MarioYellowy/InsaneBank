package com.example.insanebank;

import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;

public class Validacion {
    public static void cambiarVentana(ActionEvent event, String ventana) throws IOException {
        Stage currentStage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        FXMLLoader loader = new FXMLLoader(Validacion.class.getResource(ventana+".fxml"));
        Parent root = loader.load();
        Scene scene = new Scene(root);
        Stage stage = new Stage();
        stage.setScene(scene);
        stage.show();
        currentStage.close();
    }
    public static void mostrarVentana(String ventana) throws IOException {
        FXMLLoader loader = new FXMLLoader(Validacion.class.getResource(ventana+".fxml"));
        Parent root1 = loader.load();
        Scene scene = new Scene(root1);
        Stage stage = new Stage();
        stage.setScene(scene);
        stage.show();
    }
}
