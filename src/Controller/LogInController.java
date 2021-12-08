package Controller;

import DAO.JDBC;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;
import java.time.ZoneId;
import java.util.Locale;
import java.util.Optional;
import java.util.ResourceBundle;
import java.util.TimeZone;

/** This class allows users to log in to the application.*/
public class LogInController implements Initializable {

    ResourceBundle rb = ResourceBundle.getBundle("Utilities/Nat_fr", Locale.getDefault());
    private Stage stage;
    private Parent scene;

    @FXML
    private Label zoneIdLbl;

    @FXML
    private TextField usernameTextField;

    @FXML
    private TextField passwordTextField;

    @FXML
    private Label usernameLbl;

    @FXML
    private Label pwLbl;

    @FXML
    private Button enterButton;

    @FXML
    private Button exitButton;

    @FXML
    private Label loginLbl;


    @FXML
    void clickEnterButton(ActionEvent event) throws IOException {
        if(((usernameTextField.getText().equals("test")) && (passwordTextField.getText().equals("test"))) ||
                ((usernameTextField.getText().equals("admin")) && (passwordTextField.getText().equals("admin")))) {
            JDBC.openConnection();
            stage = (Stage) ((Button) event.getSource()).getScene().getWindow();
            scene = FXMLLoader.load(getClass().getResource("/View/MainForm.fxml"));
            stage.setScene(new Scene(scene));
            stage.show();
        }
        else {
            if((Locale.getDefault().getLanguage().equals("en"))) {
                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.setTitle("Error");
                alert.setContentText("Invalid username/password, please try again.");
                alert.showAndWait();
            }
            else {
                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.setTitle(rb.getString("error"));
                alert.setContentText("nom d'utilisateur/mot de passe invalide, veuillez réessayer");
                alert.showAndWait();
            }
        }

    }

    @FXML
    void exitApplication(ActionEvent event) {
        if (Locale.getDefault().getLanguage().equals("en")) {
            Alert alert = new Alert(Alert.AlertType.CONFIRMATION, "This will exit the application, are you sure?");
            Optional<ButtonType> result = alert.showAndWait();
            if (result.isPresent() && result.get() == ButtonType.OK) {
                System.exit(0);
            }
        }
        else {
            Alert alert = new Alert(Alert.AlertType.CONFIRMATION, "cela quittera l'application êtes-vous sûr?");
            Optional<ButtonType> result = alert.showAndWait();
            if (result.isPresent() && result.get() == ButtonType.OK) {
                System.exit(0);
            }
        }
    }

    @FXML
    void onEnterKeyPressedPw(KeyEvent event) throws IOException {
        if (event.getCode().equals(KeyCode.ENTER)) {
            if ((usernameTextField.getText().equals("test")) && (passwordTextField.getText().equals("test"))) {
                JDBC.openConnection();
                stage = (Stage) ((TextField) event.getSource()).getScene().getWindow();
                scene = FXMLLoader.load(getClass().getResource("/View/MainForm.fxml"));
                stage.setScene(new Scene(scene));
                stage.show();
            }
            else {
                if ((Locale.getDefault().getLanguage().equals("en"))) {
                    Alert alert = new Alert(Alert.AlertType.ERROR);
                    alert.setTitle("Error");
                    alert.setContentText("Invalid username/password, please try again.");
                    alert.showAndWait();
                }
                else {
                    Alert alert = new Alert(Alert.AlertType.ERROR);
                    alert.setTitle(rb.getString("error"));
                    alert.setContentText("nom d'utilisateur/mot de passe invalide, veuillez réessayer");
                    alert.showAndWait();
                }
            }
        }
    }


    @FXML
    void onEnterKeyPressedUser(KeyEvent event) throws IOException {
        if (event.getCode().equals(KeyCode.ENTER)) {
            if ((usernameTextField.getText().equals("test")) && (passwordTextField.getText().equals("test"))) {
                JDBC.openConnection();
                stage = (Stage) ((TextField) event.getSource()).getScene().getWindow();
                scene = FXMLLoader.load(getClass().getResource("/View/MainForm.fxml"));
                stage.setScene(new Scene(scene));
                stage.show();
            }
            else {
                if ((Locale.getDefault().getLanguage().equals("en"))) {
                    Alert alert = new Alert(Alert.AlertType.ERROR);
                    alert.setTitle("Error");
                    alert.setContentText("Invalid username/password, please try again.");
                    alert.showAndWait();
                }
                else {
                    Alert alert = new Alert(Alert.AlertType.ERROR);
                    alert.setTitle(rb.getString("error"));
                    alert.setContentText("nom d'utilisateur/mot de passe invalide, veuillez réessayer");
                    alert.showAndWait();
                }
            }
        }
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        zoneIdLbl.setText(String.valueOf(ZoneId.systemDefault()));
        if(Locale.getDefault().getLanguage().equals("fr")) {
            enterButton.setText(rb.getString("enter"));
            exitButton.setText(rb.getString("exit"));
            usernameLbl.setText(rb.getString("username"));
            pwLbl.setText(rb.getString("password"));
            loginLbl.setText(rb.getString("login"));

        }
    }
}
