package Controller;

import Model.Customer;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;
import java.util.Optional;
import java.util.ResourceBundle;

public class UpdateCustomerController implements Initializable {
    private Stage stage;
    private Parent scene;
    private Customer customer;

    @FXML
    private Label custIdLbl;

    @FXML
    private TextField custNameTf;

    @FXML
    private TextField custPostalCodeTf;

    @FXML
    private TextField custPhoneTf;

    @FXML
    private TextField custAddressTf;

    @FXML
    private ComboBox<?> custCountryCB;

    @FXML
    private ComboBox<?> custDivisionCB;

    @FXML
    private Button saveBtn;

    @FXML
    private Button cancelBtn;

    @FXML
    void backToMainMenu(ActionEvent event) throws IOException {
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION, "This will clear any unsaved work, continue?");
        Optional<ButtonType> result = alert.showAndWait();
        if (result.isPresent() && result.get() == ButtonType.OK) {
            stage = (Stage) ((Button) event.getSource()).getScene().getWindow();
            scene = FXMLLoader.load(getClass().getResource("/View/MainForm.fxml"));
            stage.setScene(new Scene(scene));
            stage.show();
        }
    }

    @FXML
    void saveCustomer(ActionEvent event) {

    }

    @FXML
    void selectCountry(ActionEvent event) {

    }

    public void receiveCustomer(Customer customer) {
        this.customer = customer;

        custIdLbl.setText(String.valueOf(customer.getId()));
        custNameTf.setText(customer.getName());
        custAddressTf.setText(customer.getAddress());
        custPhoneTf.setText(customer.getPhone());
        custPostalCodeTf.setText(customer.getPostalCode());
        custCountryCB.setItems(customer.);
    }

    @Override
    public void initialize (URL url, ResourceBundle resourceBundle) {
        custIdLbl.setDisable(true);
    }

}

