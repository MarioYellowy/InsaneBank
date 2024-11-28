package com.insanebank.controller;

import com.insanebank.Validacion;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import org.springframework.stereotype.Controller;

import java.io.IOException;
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
        String email = emailField.getText();
        String password = passwordField.getText();
        String confirmPassword = confirmPasswordField.getText();

        if (!isValidEmail(email)) {
            showAlert("Error", "Correo electrónico inválido. Asegúrate de que sea un formato válido.");
            return;
        }

        if (!isValidPassword(password)) {
            showAlert("Error", "Contraseña inválida. Debe tener entre 8 y 20 caracteres, incluyendo una letra mayúscula, una letra minúscula, un número y un carácter especial.");
            return;
        }

        if (!password.equals(confirmPassword)) {
            showAlert("Error", "La confirmación de la contraseña no coincide.");
            return;
        }

        showAlert("Éxito", "Registro completado con éxito.");
    }

    private boolean isValidEmail(String email) {
        String emailRegex = "^[a-zA-Z0-9._%+-]+@[a-zA-Z0-9.-]+\\.[a-zA-Z]{2,}$";
        return Pattern.matches(emailRegex, email);
    }

    private boolean isValidPassword(String password) {
        String passwordRegex = "^(?=.*[a-z])(?=.*[A-Z])(?=.*\\d)(?=.*[@$!%*?&])[A-Za-z\\d@$!%*?&]{8,20}$";
        return Pattern.matches(passwordRegex, password);
    }

    private void showAlert(String title, String content) {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle(title);
        alert.setContentText(content);
        alert.showAndWait();
    }
}
