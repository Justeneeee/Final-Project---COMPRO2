import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;

public class SkinBuyControl {
    @FXML
    private TableView<Gun> tableView;
    @FXML
    private TableColumn<Gun, String> gunNameCol;
    @FXML
    private TableColumn<Gun, Integer> quantityCol;
    @FXML
    private TableColumn<Gun, Integer> priceCol;
    @FXML
    private Label totalLabel;
    @FXML
    private TextField cashField;
    @FXML
    private Label changeLabel;
    @FXML
    private TextArea receiptArea;

    private final ObservableList<Gun> data = FXCollections.observableArrayList();
    @FXML
    public void home(ActionEvent event) {
        switchScene("resource/homepage.fxml", "Home", event);
    }
    @FXML
    public void logout(ActionEvent event) {
        switchScene("resource/login.fxml", "Login", event);
    }

    @FXML
    private void initialize() {
        gunNameCol.setCellValueFactory(new PropertyValueFactory<>("gunName"));
        quantityCol.setCellValueFactory(new PropertyValueFactory<>("quantity"));
        priceCol.setCellValueFactory(new PropertyValueFactory<>("price"));

        tableView.setItems(data);
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

    // Buttons with Image View
    // Vandal

    @FXML
    public void buyPrimeVandal(ActionEvent event) {
        addGun("Prime Vandal", 1775);
    }

    @FXML
    public void buyGaiasVandal(ActionEvent event) {
        addGun("Gaia's Vandal", 1775);
    }

    @FXML
    public void buyIonVandal(ActionEvent event) {
        addGun("Ion Vandal", 1775);
    }

    @FXML
    public void buyKuronamiVandal(ActionEvent event) {
        addGun("Kuronami Vandal", 2175);
    }

    @FXML
    public void buyReaverVandal(ActionEvent event) {
        addGun("Reaver Vandal", 1775);
    }

    @FXML
    public void buyPreludeVandal(ActionEvent event) {
        addGun("Prelude to Chaos Vandal", 2175);
    }

    // Phantom

    @FXML
    public void buyPrimePhantom(ActionEvent event) {
        addGun("Prime Phantom", 1775);
    }

    @FXML
    public void buyIonPhantom(ActionEvent event) {
        addGun("Ion Phantom", 1775);
    }

    @FXML
    public void buyOniPhantom(ActionEvent event) {
        addGun("Oni Phantom", 1775);
    }

    @FXML
    public void buyReaverPhantom(ActionEvent event) {
        addGun("Reaver Phantom", 1775);
    }

    @FXML
    public void buyRadiantPhantom(ActionEvent event) {
        addGun("Radiant Entertainment System Phantom", 2175);
    }

    @FXML
    public void buyReconPhantom(ActionEvent event) {
        addGun("Recon Phantom", 1775);
    }

    // Operator

    @FXML
    public void buyIonOp(ActionEvent event) {
        addGun("Ion Operator",1775);
    }

    @FXML
    public void buyForsakenOp(ActionEvent event) {
        addGun("Forsaken Operator", 1775);
    }

    @FXML
    public void buySentinelsOp(ActionEvent event) {
        addGun("Sentinels of Light Operator", 2175);
    }

    @FXML
    public void buyReaverOp(ActionEvent event) {
        addGun("Reaver Operator", 1775);
    }

    @FXML
    public void buyOriginOp(ActionEvent event) {
        addGun("Origin Operator", 1775);
    }

    @FXML
    public void buyPreludeOp(ActionEvent event) {
        addGun("Prelude to Chaos Operator", 2175);
    }

    private void addGun(String name, int price) {
        Gun gun = new Gun(name, 1, price);

        boolean exists = false;
        for (Gun g : data) {
            if (g.equals(gun)) {
                g.setQuantity(g.getQuantity() + 1);
                g.setPrice(g.getPrice() + price);
                exists = true;
                break;
            }
        }

        if (!exists) {
            data.add(gun);
        }

        tableView.refresh();
        updateTotal();
    }

    private void updateTotal() {
        int total = data.stream().mapToInt(g -> g.getQuantity() * g.getPrice()).sum();
        totalLabel.setText(String.valueOf(total));
    }

    @FXML
    private void handlePay(ActionEvent event) {
        if (totalLabel.getText().isEmpty()) {
            switchScene("resource/insuf.fxml", "Insufficient Balance", event);
            return;
        }

        int total;
        try {
            total = Integer.parseInt(totalLabel.getText());
        } catch (NumberFormatException e) {
            switchScene("resource/insuf.fxml", "Invalid Total", event);
            return;
        }

        if (cashField.getText() == null || cashField.getText().isEmpty()) {
            switchScene("resource/fail.fxml", "No Money!", event);
            return;
        }

        int cash;
        try {
            cash = Integer.parseInt(cashField.getText());
        } catch (NumberFormatException e) {
            switchScene("resource/insuf.fxml", "Invalid Cash Input", event);
            return;
        }

        int change = cash - total;

        if (change < 0) {
            switchScene("resource/insuf.fxml", "Insufficient Balance", event);
        } else {
            changeLabel.setText(String.valueOf(change));
            String receipt = generateReceipt(total, cash, change);
            receiptArea.setText(receipt);
        }
    }

    private String generateReceipt(int total, int cash, int change) {
        StringBuilder receipt = new StringBuilder();
        receipt.append("Valorant Fourm\n");
        receipt.append("Address: San Juan, 23-10\n");
        receipt.append("Telp. 09619523507\n");
        receipt.append("\n");
        receipt.append("CASH RECEIPT\n");
        receipt.append("*************************\n");
        for (Gun g : data) {
            receipt.append(String.format("%s\t%d\t%d\n", g.getGunName(), g.getQuantity(), g.getQuantity() * g.getPrice()));
        }
        receipt.append("\n");
        receipt.append(String.format("Total\t%d\n", total));
        receipt.append(String.format("Cash\t%d\n", cash));
        receipt.append(String.format("Change\t%d\n", change));
        receipt.append("*************************\n");
        receipt.append("THANK YOU! MISS KO NA SIYA!\n");

        writeReceipt();
        return receipt.toString();
    }

    private void writeReceipt() {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter("data/receipt.csv", true))){
            writer.newLine();
            for (Gun g : data) {
                writer.write(String.format("%s,%d,%d\n", g.getGunName(), g.getQuantity(), g.getQuantity() * g.getPrice()));
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }


    @FXML
    private void clear(ActionEvent event) {
        data.clear();
        tableView.refresh();
        receiptArea.clear();
        totalLabel.setText("");
        cashField.clear();
        changeLabel.setText("");
    }
}
