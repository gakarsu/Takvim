package me.clndr.tkvm;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;

public class LoginController {

    @FXML
    private TextField usernameField;

    @FXML
    private TextField passwordField;

    @FXML
    private void loginButtonClicked() {
        String username = usernameField.getText();
        String password = passwordField.getText();

        if (validateLogin(username, password)) {
            System.out.println("Giriş başarılı.");
            openCalendarInterface();
        } else {
            System.out.println("Hatalı kullanıcı adı veya şifre. Tekrar deneyin.");
        }
    }

    private boolean validateLogin(String username, String password) {
        try (BufferedReader br = new BufferedReader(new FileReader("fsql.txt"))) {
            String line;
            while ((line = br.readLine()) != null) {
                String[] parts = line.split(";");
                String storedUsername = parts[0];
                String storedPassword = parts[1];

                if (storedUsername.equals(username) && storedPassword.equals(password)) {
                    return true;
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

        return false;
    }

    @FXML
    private void registerButtonClicked() {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("register.fxml"));
            Parent root = loader.load();
            Stage stage = new Stage();
            stage.setTitle("Kayıt Ol");
            stage.setScene(new Scene(root, 300, 600));
            stage.show();

            Stage loginStage = (Stage) usernameField.getScene().getWindow();
            loginStage.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void openCalendarInterface() {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("calendar.fxml"));
            Parent root = loader.load();
            Stage stage = new Stage();
            stage.setTitle("Takvim Arayüzü");
            stage.setScene(new Scene(root, 600, 400));
            stage.show();

            Stage loginStage = (Stage) usernameField.getScene().getWindow();
            loginStage.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}