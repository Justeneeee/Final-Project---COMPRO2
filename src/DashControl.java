import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.Node;
import javafx.stage.Stage;
import java.io.IOException;

public class DashControl {
    @FXML
    public void home(ActionEvent event) {
        switchScene("resource/homepage.fxml", "Home", event);
    }

    @FXML
    public void logout(ActionEvent event) {
        switchScene("resource/login.fxml", "Login", event);
    }

    @FXML
    public void addPlayer(ActionEvent event) { switchScene("resource/addPlayer.fxml", "Add Player", event);}

    @FXML
    public void sellSkins(ActionEvent event) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("resource/buySkins.fxml"));
            Parent root = loader.load();
            Stage leaderboardStage = new Stage();
            leaderboardStage.setTitle("Buy Skins");
            Scene scene = new Scene(root);
            leaderboardStage.setScene(scene);
            Stage currentStage = (Stage) ((Node) event.getSource()).getScene().getWindow();
            currentStage.close();
            leaderboardStage.setResizable(false);
            leaderboardStage.show();
        } catch (IOException e) {
            e.printStackTrace();
            System.err.println("Error loading fxml: " + e.getMessage());
        } catch (Exception ex) {
            ex.printStackTrace();
            System.err.println("Error showing leaderboard stage: " + ex.getMessage());
        }
    }
    @FXML
    public void addScore(ActionEvent event) {
        switchScene("resource/addScore.fxml", "Add Score", event);
    }

    @FXML
    public void removeScore(ActionEvent event) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("resource/removeScore.fxml"));
            Parent root = loader.load();
            Stage leaderboardStage = new Stage();
            leaderboardStage.setTitle("Remove Score");
            Scene scene = new Scene(root);
            leaderboardStage.setScene(scene);
            Stage currentStage = (Stage) ((Node) event.getSource()).getScene().getWindow();
            currentStage.close();
            leaderboardStage.setResizable(false);
            leaderboardStage.show();
        } catch (IOException e) {
            e.printStackTrace();
            System.err.println("Error loading fxml: " + e.getMessage());
        } catch (Exception ex) {
            ex.printStackTrace();
            System.err.println("Error showing leaderboard stage: " + ex.getMessage());
        }
    }
    @FXML
    public void viewLeaderboards(ActionEvent event) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("resource/leaderboard.fxml"));
            Parent root = loader.load();
            Stage leaderboardStage = new Stage();
            leaderboardStage.setTitle("Leaderboards");
            Scene scene = new Scene(root);
            leaderboardStage.setScene(scene);
            Stage currentStage = (Stage) ((Node) event.getSource()).getScene().getWindow();
            currentStage.close();
            leaderboardStage.setResizable(false);
            leaderboardStage.show();
        } catch (IOException e) {
            e.printStackTrace();
            System.err.println("Error loading fxml: " + e.getMessage());
        } catch (Exception ex) {
            ex.printStackTrace();
            System.err.println("Error showing leaderboard stage: " + ex.getMessage());
        }
    }


    private void switchScene(String fxmlFile, String title, ActionEvent event) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource(fxmlFile));
            Scene scene = new Scene(loader.load());
            Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
            stage.setScene(scene);
            stage.setResizable(false);
            stage.setTitle(title);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
