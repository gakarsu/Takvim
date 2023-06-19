package me.clndr.tkvm;

import javafx.collections.FXCollections;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.ComboBox;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;

public class RegisterController {

    @FXML
    private TextField firstNameField;

    @FXML
    private TextField lastNameField;

    @FXML
    private TextField usernameField;

    @FXML
    private PasswordField passwordField;

    @FXML
    private TextField tcField;

    @FXML
    private TextField phoneField;

    @FXML
    private TextField emailField;

    @FXML
    private TextField addressField;

    @FXML
    private ComboBox<String> userTypeComboBox;

    private Stage primaryStage;

    public void goBack() throws IOException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("login.fxml"));
        Parent root = loader.load();
        Stage stage = new Stage();
        stage.setTitle("Takvim");
        stage.setScene(new Scene(root, 300, 200));
        stage.show();
    }

    @FXML
    private void registerButtonClicked() throws IOException {
        String firstName = firstNameField.getText();
        String lastName = lastNameField.getText();
        String username = usernameField.getText();
        String password = passwordField.getText();
        String tc = tcField.getText();
        String phone = phoneField.getText();
        String email = emailField.getText();
        String address = addressField.getText();
        String userType = userTypeComboBox.getValue();

        String userData = firstName + ";" + lastName + ";" + username + ";" + password + ";" + tc + ";" + phone + ";" + email + ";" + address + ";" + userType;

        boolean success = saveUserToFile(userData, "fsql.txt");

        if (success) {
            System.out.println("Kayıt başarılı. Yeni kullanıcı kaydedildi.");
            goBack();
            Stage oldStg = (Stage) usernameField.getScene().getWindow();
            oldStg.close();
        } else {
            System.out.println("Kayıt başarısız. Bir hata oluştu.");
        }
    }

    private boolean saveUserToFile(String userData, String filename) {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(filename, true))) {
            writer.write(userData);
            writer.newLine();
            return true;
        } catch (IOException e) {
            e.printStackTrace();
        }
        return false;
    }
}