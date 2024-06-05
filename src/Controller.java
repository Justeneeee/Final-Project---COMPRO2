import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.stage.Stage;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.Objects;
import java.util.ResourceBundle;

public class Controller implements Initializable {
    private ArrayList<User> users;

    @FXML
    private Button login;

    @FXML
    private TextField username;

    @FXML
    private PasswordField password;

    @FXML
    private ImageView img;

    @FXML
    private ImageView logo;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        loadImage(img, "resource/pics/background1.jpg");
        loadImage(logo, "resource/pics/logo.png");
    }

    private void loadImage(ImageView imageView, String imagePath) {
        try {
            Image image = new Image(Objects.requireNonNull(getClass().getResourceAsStream(imagePath)));
            imageView.setImage(image);
        } catch (NullPointerException e) {
            System.out.println("Image not found: " + imagePath);
            e.printStackTrace();
        }
    }

    public User searchUser(String username) {
        for (User user : users) {
            if (user.getUsername().equals(username)) {
                return user;
            }
        }
        return null;
    }

    @FXML
    void login(ActionEvent event) {
        String userInput = username.getText();
        String passwordInput = password.getText();

        User user = searchUser(userInput);

        if (user == null) {
            showAlert(event);
            return;
        }

        String passwordHash = user.getPassword();

        if (passwordHash == null) {
            showAlert(event);
            return;
        }

        String salt = BCrypt.gensalt(12);
        String hashedPasswordInput = BCrypt.hashpw(passwordInput, salt);

        if (BCrypt.checkpw(passwordInput, hashedPasswordInput)) {
            switchToHomepage(event);
        } else {
            showAlert(event);
        }
    }


    private void switchToHomepage(ActionEvent event) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("resource/homepage.fxml"));
            Scene scene = new Scene(loader.load());
            Stage stage = (Stage) username.getScene().getWindow();
            stage.setScene(scene);
            stage.setTitle("Homepage");
        } catch (Exception e) {
            e.printStackTrace();
            showAlert(event);
        }
    }


    private void showAlert(ActionEvent event) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("resource/fail.fxml"));
            Scene scene = new Scene(loader.load());
            Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
            stage.setScene(scene);
            stage.setTitle("Invalid!");
        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    public Controller() {
        users = new ArrayList<>();
        try {
            BufferedReader br = new BufferedReader(new FileReader("data/user.csv"));
            String line;
            br.readLine();
            while ((line = br.readLine()) != null) {
                String[] data = line.split(",");
                User user = new User(data[0], data[1]);
                users.add(user);
            }
            br.close();
        } catch (IOException e) {
            System.out.println("Error: " + e.getMessage());
            e.printStackTrace();
        }
    }
}