import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;

import java.io.*;
import java.net.URL;
import java.util.ResourceBundle;

public class RemovePlayerScoreControl implements Initializable {
    @FXML
    private TableView<Gamer> tableView;

    @FXML
    private TableColumn<Gamer, String> playerNameColumn;

    @FXML
    private TableColumn<Gamer, String> scoreColumn;

    @FXML
    private TableColumn<Gamer, String> rankColumn;
    @FXML
    private TextField searchField;

    private ObservableList<Gamer> gamerList;

    @FXML
    public void home(ActionEvent event) {
        switchScene("resource/homepage.fxml", "Home", event);
    }

    @FXML
    public void logout(ActionEvent event) {
        switchScene("resource/login.fxml", "Login", event);
    }

    @FXML
    public void remove(ActionEvent event) {
        Gamer selectedGamer = tableView.getSelectionModel().getSelectedItem();
        if (selectedGamer != null) {
            gamerList.remove(selectedGamer);
            updateCSVFile();
        } else {
            switchScene("resource/fail.fxml", "NO selected Player Score", event);
        }
    }

    private void updateCSVFile() {
        try (BufferedWriter bw = new BufferedWriter(new FileWriter("data/gamer.csv"))) {
            for (Gamer gamer : gamerList) {
                bw.write(String.format("%s,%s,%s\n", gamer.getPlayerName(), gamer.getScore(), gamer.getRank()));
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    @Override
    public void initialize(URL location, ResourceBundle resources) {
        gamerList = FXCollections.observableArrayList();
        loadGamerData();
        playerNameColumn.setCellValueFactory(new PropertyValueFactory<>("playerName"));
        scoreColumn.setCellValueFactory(new PropertyValueFactory<>("score"));
        rankColumn.setCellValueFactory(new PropertyValueFactory<>("rank"));

        tableView.setItems(gamerList);

        searchField.textProperty().addListener((observable, oldValue, newValue) -> {
            filterTable(newValue);
        });

        scoreColumn.setSortType(TableColumn.SortType.DESCENDING);
        tableView.getSortOrder().add(scoreColumn);
        tableView.sort();
    }

    private void filterTable(String keyword) {
        if (keyword == null || keyword.isEmpty()) {
            tableView.setItems(gamerList);
            return;
        }

        ObservableList<Gamer> filteredData = FXCollections.observableArrayList(
                gamerList.stream()
                        .filter(gamer -> gamer.getPlayerName().toLowerCase().contains(keyword.toLowerCase()) ||
                                gamer.getScore().toLowerCase().contains(keyword.toLowerCase()) ||
                                gamer.getRank().toLowerCase().contains(keyword.toLowerCase()))
                        .toList()
        );

        tableView.setItems(filteredData);
    }

    private void loadGamerData() {
        try (BufferedReader br = new BufferedReader(new FileReader("data/gamer.csv"))) {
            String line;
            br.readLine();
            while ((line = br.readLine()) != null) {
                String[] data = line.split(",");
                if (data.length == 3) {
                    Gamer gamer = new Gamer(data[0], data[1], data[2]);
                    gamerList.add(gamer);
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void switchScene(String fxmlFile, String title, ActionEvent event) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource(fxmlFile));
            Scene scene = new Scene(loader.load());
            Stage stage = (Stage) ((Button) event.getSource()).getScene().getWindow();
            stage.setScene(scene);
            stage.setTitle(title);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
