package com.insanebank.method;

import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.DialogPane;
import javafx.scene.control.Label;
import javafx.scene.layout.Pane;
import javafx.scene.layout.StackPane;
import javafx.scene.text.Font;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

public class AlertHelper {

    public static void showCustomAlert(String title, String message, String buttonText) {
        Stage stage = new Stage(StageStyle.UNDECORATED);
        stage.setTitle(title);

        // Crear el fondo personalizado
        Pane root = new Pane();
        root.setStyle(
                "-fx-background-size: cover;" + "-fx-background-color: #0a4e5a;"
                );

        // Título de la alerta
        Label alertTitle = new Label(title);
        alertTitle.setFont(new Font("Yu Gothic UI Semibold", 20));
        alertTitle.setStyle("-fx-text-fill: white;");
        alertTitle.setLayoutX(38);
        alertTitle.setLayoutY(75);

        // Mensaje de la alerta
        Label alertMessage = new Label(message);
        alertMessage.setFont(new Font("Yu Gothic Medium", 25));
        alertMessage.setStyle("-fx-text-fill: white;");
        alertMessage.setWrapText(true);
        alertMessage.setPrefWidth(460);
        alertMessage.setLayoutX(55);
        alertMessage.setLayoutY(115);

        // Botón de cierre
        Button closeButton = new Button(buttonText);
        closeButton.setFont(new Font("Yu Gothic UI Semibold", 25));
        closeButton.setStyle("-fx-background-color: White; -fx-text-fill: #344660E5;");
        closeButton.setPrefSize(210, 40);
        closeButton.setLayoutX(280);
        closeButton.setLayoutY(290);

        // Acción del botón para cerrar la alerta
        closeButton.setOnAction(e -> stage.close());

        // Agregar todos los elementos al contenedor raíz
        root.getChildren().addAll(alertTitle, alertMessage, closeButton);

        // Crear la escena y mostrar la alerta
        Scene scene = new Scene(root, 525, 350);
        stage.setScene(scene);
        stage.showAndWait();
    }
}
