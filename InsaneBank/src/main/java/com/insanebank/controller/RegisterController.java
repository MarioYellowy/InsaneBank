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
    private TextField nameField;

    @FXML
    private TextField emailField;

    @FXML
    private PasswordField passwordField;

    @FXML
    private PasswordField confirmPasswordField;

    @FXML
    void goToLogin(ActionEvent event) {
        try {
            Validacion.cambiarVentana(event, "/fxml/login-view.fxml");
        } catch (IOException e) {
            e.printStackTrace();
            showAlert("Error", "No se pudo cargar la ventana de inicio de sesión.");
        }
    }

    @FXML
    private void register(ActionEvent event) {
        String usuario_nombre = nameField.getText();
        String usuario_email = emailField.getText();
        String usuario_password = passwordField.getText();
        String confirmPassword = confirmPasswordField.getText();

        if (usuario_nombre.isEmpty() || usuario_email.isEmpty() || usuario_password.isEmpty() || confirmPassword.isEmpty()) {
            AlertHelper.showCustomAlert("Error", "Todos los campos son obligatorios. Por favor, completa el formulario.", "Aceptar");
            return;
        }
        if (!isValidEmail(usuario_email)) {
            AlertHelper.showCustomAlert("Error", "Correo electrónico inválido. Por favor, ingresa un formato válido.", "Aceptar");
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

        try (Connection connection = Conexion.conectar()) {
            if (connection != null) {
                String checkEmailQuery = "SELECT COUNT(*) FROM usuarios WHERE usuario_email = ?";
                try (PreparedStatement checkStatement = connection.prepareStatement(checkEmailQuery)) {
                    checkStatement.setString(1, usuario_email);
                    var resultSet = checkStatement.executeQuery();
                    if (resultSet.next() && resultSet.getInt(1) > 0) {
                        AlertHelper.showCustomAlert("Error", "Este correo ya está registrado. Usa otro correo.", "Aceptar");
                        return;
                    }
                }

                String hashedPassword = BCrypt.hashpw(usuario_password, BCrypt.gensalt());

                String query = "INSERT INTO usuarios (usuario_nombre, usuario_email, usuario_password) VALUES (?, ?, ?)";
                try (PreparedStatement statement = connection.prepareStatement(query)) {
                    statement.setString(1, usuario_nombre);
                    statement.setString(2, usuario_email);
                    statement.setString(3, hashedPassword);
                    int rowsInserted = statement.executeUpdate();
                    if (rowsInserted > 0) {
                        AlertHelper.showCustomAlert("Éxito", "Registro completado con éxito.", "Aceptar");
                        Validacion.cambiarVentana(event, "/fxml/login-view.fxml");
                    } else {
                        AlertHelper.showCustomAlert("Error", "No se pudo registrar el usuario.", "Aceptar");
                    }
                }
            }
        } catch (SQLException | IOException e) {
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
