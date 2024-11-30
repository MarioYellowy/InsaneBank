package com.insanebank.controller;

import Conexion.Conexion;
import com.insanebank.method.AlertHelper;
import com.insanebank.method.Validacion;
import javafx.animation.ScaleTransition;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.util.Duration;
import org.mindrot.jbcrypt.BCrypt;
import org.springframework.stereotype.Controller;

import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.regex.Pattern;

@Controller
public class LoginController {
    @FXML
    void showRegister(ActionEvent event) throws IOException {
        Validacion.cambiarVentana(event, "/fxml/register-view.fxml");
    }

    @FXML
    void showHome(ActionEvent event) throws IOException {
        Validacion.cambiarVentana(event, "/fxml/home-view.fxml");
    }

    @FXML
    private TextField emailField;

    @FXML
    private PasswordField passwordField;

    @FXML
    private Button btnCrearCuenta;
    @FXML
    private Hyperlink hyperCrearCuenta;
    @FXML
    private Button loginButton;

    @FXML
    private void login(ActionEvent event) {
        String usuario_email = emailField.getText();
        String usuario_password = passwordField.getText();

        if (usuario_email.isEmpty() || usuario_password.isEmpty()) {
            AlertHelper.showCustomAlert("Error", "Por favor ingresa tu correo electrónico y contraseña.", "Aceptar");
            return;
        }

        // Validación de formato de correo electrónico
        if (!isValidEmail(usuario_email)) {
            AlertHelper.showCustomAlert("Error", "Correo electrónico inválido.", "Aceptar");
            return;
        }

        try (Connection connection = Conexion.conectar()) {
            if (connection != null) {
                String query = "SELECT usuario_password FROM usuarios WHERE usuario_email = ?";
                try (PreparedStatement statement = connection.prepareStatement(query)) {
                    statement.setString(1, usuario_email);
                    ResultSet resultSet = statement.executeQuery();

                    if (resultSet.next()) {
                        String storedPasswordHash = resultSet.getString("usuario_password");

                        if (BCrypt.checkpw(usuario_password, storedPasswordHash)) {
                            AlertHelper.showCustomAlert("Éxito", "Inicio de sesión exitoso.", "Aceptar");
                            Validacion.cambiarVentana(event, "/fxml/home-view.fxml");  // Redirigir a la ventana principal
                        } else {
                            AlertHelper.showCustomAlert("Error", "Contraseña incorrecta.", "Aceptar");
                        }
                    } else {
                        AlertHelper.showCustomAlert("Error", "Correo electrónico no registrado.", "Aceptar");
                    }
                }
            }
        } catch (IOException | SQLException e) {
            e.printStackTrace();
            AlertHelper.showCustomAlert("Error", "Hubo un problema al conectar con la base de datos.", "Aceptar");
        }
    }

    private boolean isValidEmail(String email) {
        String emailRegex = "^[a-zA-Z0-9._%+-]+@[a-zA-Z0-9.-]+\\.[a-zA-Z]{2,}$";
        return Pattern.matches(emailRegex, email);
    }

    private void showAlert(String title, String content) {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle(title);
        alert.setContentText(content);
        alert.showAndWait();
    }

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
