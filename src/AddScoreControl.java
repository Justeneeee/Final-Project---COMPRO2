import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

import java.io.*;

public class AddScoreControl {
    private Stage stage;
    public void setStage(Stage stage) {
        this.stage = stage;
    }
    @FXML
    private TextField username;
    @FXML
    private TextField score;
    @FXML
    private TextField rank;

    @FXML
    public void home(ActionEvent event) {
        switchScene("resource/homepage.fxml", "Home", event);
    }

    @FXML
    public void logout(ActionEvent event) {
        switchScene("resource/login.fxml", "Login", event);
    }

    @FXML
    public void saveScore(ActionEvent event) throws IOException {
        String usernameText = username.getText();
        String scoreText = score.getText();
        String rankText = rank.getText();

        if (usernameText.isEmpty() || scoreText.isEmpty() || rankText.isEmpty()) {
            switchScene("resource/fail.fxml", "Fail", event);
        } else {
            saveScoreToCSV(usernameText, scoreText, rankText);
            switchScene("resource/success.fxml", "Success", event);
        }
    }

    private void saveScoreToCSV(String username, String score, String rank) throws IOException{
        String userScore = username + "," + score + "," + rank;
        try (BufferedWriter writer = new BufferedWriter(new FileWriter("data/gamer.csv", true))){
            writer.newLine();
            writer.write(userScore);
        }
    }


    private void switchScene1(String fxmlFile, String title) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource(fxmlFile));
            Scene scene = new Scene(loader.load());
            stage.setScene(scene);
            stage.setTitle(title);
        } catch (IOException e) {
            e.printStackTrace();
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
