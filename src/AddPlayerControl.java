import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

import java.io.*;

public class AddPlayerControl {

    @FXML
    private TextField usernameField;

    @FXML
    private TextField passwordField;

    @FXML
    private TextField confirmPasswordField;

    @FXML
    private Label statusLabel;

    @FXML
    public void home(ActionEvent event) {
        switchScene("resource/homepage.fxml", "Home", event);
    }

    @FXML
    public void logout(ActionEvent event) {
        switchScene("resource/login.fxml", "Login", event);
    }

    @FXML
    public void saveUser(ActionEvent event) {
        String username = usernameField.getText();
        String password = passwordField.getText();
        String confirmPassword = confirmPasswordField.getText();

        if (username.isEmpty() || password.isEmpty() || confirmPassword.isEmpty()) {
            statusLabel.setText("All fields are required.");
            return;
        }

        if (password.equals(confirmPassword)) {
            try {
                saveUserToFile(username, password);
                switchScene("resource/success.fxml", "Success", event);
            } catch (IOException e) {
                statusLabel.setText("Failed to save user. Try again.");
            }
        } else {
            statusLabel.setText("Passwords do not match.");
        }
    }

    private void saveUserToFile(String username, String password) throws IOException {
        String userRecord = username + "," + password;
        try (BufferedWriter writer = new BufferedWriter(new FileWriter("data/user.csv", true))) {
            writer.newLine();
            writer.write(userRecord);
        }
    }


    private void switchScene(String fxmlFile, String title, ActionEvent event) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource(fxmlFile));
            Scene scene = new Scene(loader.load());
            Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
            stage.setScene(scene);
            stage.setTitle(title);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
