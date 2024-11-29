package com.insanebank.controller;

import Conexion.Conexion;
import com.insanebank.method.Validacion;
import com.insanebank.method.AlertHelper;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import org.mindrot.jbcrypt.BCrypt;
import org.springframework.stereotype.Controller;

import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.regex.Pattern;

@Controller
public class RegisterController {

    @FXML
    private TextField emailField;

    @FXML
    private PasswordField passwordField;

    @FXML
    private PasswordField confirmPasswordField;

    @FXML
    void goToLogin(ActionEvent event) {
        try {
            Validacion.cambiarVentana(event, "/fxml/login-view");
        } catch (IOException e) {
            e.printStackTrace();
            showAlert("Error", "No se pudo cargar la ventana de inicio de sesión.");
        }
    }

    @FXML
    private void register(ActionEvent event) {
        String usuario_email = emailField.getText();
        String usuario_password = passwordField.getText();
        String confirmPassword = confirmPasswordField.getText();

        if (!isValidEmail(usuario_email)) {
            AlertHelper.showCustomAlert("Error", "Correo electrónico inválido. Asegúrate de que sea un formato válido.", "Aceptar");
            return;
        }

        if (!isValidPassword(usuario_password)) {
            AlertHelper.showCustomAlert("Error", "Contraseña inválida. Debe tener entre 8 y 20 caracteres, incluyendo una letra mayúscula, una letra minúscula, un número y un carácter especial.", "Aceptar");
            return;
        }

        if (!usuario_password.equals(confirmPassword)) {
            AlertHelper.showCustomAlert("Error", "La confirmación de la contraseña no coincide.", "Aceptar");
            return;
        }

        String hashedPassword = BCrypt.hashpw(usuario_password, BCrypt.gensalt());

        try (Connection connection = Conexion.conectar()) {
            if (connection != null) {
                String query = "INSERT INTO usuarios (usuario_email, usuario_password) VALUES (?, ?)";
                try (PreparedStatement statement = connection.prepareStatement(query)) {
                    statement.setString(1, usuario_email);
                    statement.setString(2, hashedPassword);
                    int rowsInserted = statement.executeUpdate();
                    if (rowsInserted > 0) {
                        // Mostrar mensaje de éxito
                        Alert successAlert = new Alert(Alert.AlertType.INFORMATION);
                        successAlert.setTitle("Éxito");
                        successAlert.setHeaderText("Registro completado con éxito.");
                        successAlert.setContentText("Tu cuenta ha sido creada con éxito.");
                        successAlert.showAndWait();

                        // Redirigir a la pantalla de login después de cerrar el mensaje
                        try {
                            Validacion.cambiarVentana(event, "/fxml/login-view");
                        } catch (IOException e) {
                            e.printStackTrace();
                            showAlert("Error", "No se pudo cargar la ventana de inicio de sesión.");
                        }
                    } else {
                        AlertHelper.showCustomAlert("Error", "No se pudo registrar el usuario.", "Aceptar");
                    }
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
            AlertHelper.showCustomAlert("Error", "Hubo un problema al conectar con la base de datos.", "Aceptar");
        }
    }

    private boolean isValidEmail(String email) {
        String emailRegex = "^[a-zA-Z0-9._%+-]+@[a-zA-Z0-9.-]+\\.[a-zA-Z]{2,}$";
        return Pattern.matches(emailRegex, email);
    }

    private boolean isValidPassword(String password) {
        String passwordRegex = "^(?=.*[a-z])(?=.*[A-Z])(?=.*\\d)(?=.*[!\"#$%&'()*+,-./:;<=>?@\\[\\]^_{|}~])[A-Za-z\\d!\"#$%&'()*+,-./:;<=>?@\\[\\]^_{|}~]{8,20}$";
        return Pattern.matches(passwordRegex, password);
    }


    private void showAlert(String title, String content) {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle(title);
        alert.setContentText(content);
        alert.showAndWait();
    }
}
