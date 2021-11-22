package Controller;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.input.KeyEvent;

import java.net.URL;
import java.time.ZoneId;
import java.util.Locale;
import java.util.Optional;
import java.util.ResourceBundle;

/** This class allows users to log in to the application.*/
public class LogInController implements Initializable {
    @FXML
    private Label zoneIdLbl;

    @FXML
    private TextField usernameTextField;

    @FXML
    private TextField passwordTextField;

    @FXML
    private Button enterButton;

    @FXML
    private Button exitButton;

    @FXML
    void enterApplication(ActionEvent event) {

    }

    @FXML
    void exitApplication(ActionEvent event) {
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION, "This will exit the application, are you sure?");
        Optional<ButtonType> result = alert.showAndWait();
        if (result.isPresent() && result.get() == ButtonType.OK) {
            System.exit(0);
        }
    }

    @FXML
    void onEnterKeyPressedPw(KeyEvent event) {

    }

    @FXML
    void onEnterKeyPressedUser(KeyEvent event) {

    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        zoneIdLbl.setText(String.valueOf(ZoneId.systemDefault()));
        ResourceBundle rb = ResourceBundle.getBundle("Utilities/Nat_fr", Locale.getDefault());
        if(Locale.getDefault().getLanguage().equals("fr")) {
            enterButton.setText(rb.getString("enter"));
            exitButton.setText(rb.getString("exit"));

        }
    }
}
