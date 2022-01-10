package Controller;

import DAO.AppointmentDAO;
import DAO.JDBC;
import Model.Appointment;
import javafx.collections.ObservableList;
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

import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.net.URL;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;
import java.time.temporal.ChronoUnit;
import java.util.Locale;
import java.util.Optional;
import java.util.ResourceBundle;
import java.util.TimeZone;

/** This class allows users to log in to the application. Logging in by filling in credentials.*/
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

    /** Allows user to login to the application. Brings user to the main form after logging in
     * @param event occurs when a user clicks the enter button
     */
    @FXML
    void clickEnterButton(ActionEvent event) throws IOException {
        int flag = 0;
        if (((usernameTextField.getText().equals("test")) && (passwordTextField.getText().equals("test"))) ||
                ((usernameTextField.getText().equals("admin")) && (passwordTextField.getText().equals("admin")))) {
            JDBC.openConnection();

            LocalDateTime now = LocalDateTime.now();
            ZoneId zoneUtc = ZoneId.of("UTC");
            ZoneId usersZone = ZoneId.systemDefault();
            ZonedDateTime nowZoneUser = ZonedDateTime.of(now, usersZone);
            ZonedDateTime nowZoneUtc = nowZoneUser.withZoneSameInstant(zoneUtc);
            LocalDateTime utcDt = nowZoneUtc.toLocalDateTime();
            DateTimeFormatter adjustTimes2 = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
            String nowUtcFormatted = adjustTimes2.format(utcDt);
            String tf1 = usernameTextField.getText();

            String fileName = "login_activity.txt";
            FileWriter fileWriter = new FileWriter(fileName, true);
            PrintWriter printWriter = new PrintWriter(fileWriter);
            printWriter.print("User " + tf1 + " successfully logged in at " + nowUtcFormatted + "-UTC");
            printWriter.println();
            printWriter.close();

            try {
                ObservableList<Appointment> appointments = AppointmentDAO.getAllAppointments();
                for (Appointment appointment : appointments) {
                    Timestamp appointmentTs = Timestamp.valueOf(appointment.getStart());
                    LocalDateTime appointmentLdt = appointmentTs.toLocalDateTime();
                    DateTimeFormatter adjustTimes = DateTimeFormatter.ofPattern("HH:mm");
                    LocalDate nowDate = appointmentLdt.toLocalDate();
                    String formatter = adjustTimes.format(appointmentLdt);
                    long timeDifference = ChronoUnit.MINUTES.between(now, appointmentLdt);
                    System.out.println(timeDifference);
                    if (timeDifference >= 0 && timeDifference <= 15) {
                        if ((Locale.getDefault().getLanguage().equals("en"))) {
                            ++flag;
                            Alert alert2 = new Alert(Alert.AlertType.INFORMATION);
                            alert2.setTitle("Alert");
                            alert2.setContentText("Appointment [" + appointment.getId() + "] is at " + nowDate + " at " + formatter);
                            alert2.showAndWait();
                            break;
                        }
                        else {
                            ++flag;
                            Alert alert2 = new Alert(Alert.AlertType.INFORMATION);
                            alert2.setTitle(rb.getString("alert"));
                            alert2.setContentText("Le rendez-vous [" + appointment.getId() + "] est à " + nowDate + " à " + formatter);
                            alert2.showAndWait();
                            break;
                        }
                    }
                }
                if (flag == 0) {
                    Alert alert2 = new Alert(Alert.AlertType.INFORMATION);
                    alert2.setTitle("Alert");
                    alert2.setContentText("There are no upcoming appointments");
                    alert2.showAndWait();
                }
                stage = (Stage) ((Button) event.getSource()).getScene().getWindow();
                scene = FXMLLoader.load(getClass().getResource("/View/MainForm.fxml"));
                stage.setScene(new Scene(scene));
                stage.show();

            } catch (SQLException e) {
                e.printStackTrace();
            }
        }

        else {
            LocalDateTime now = LocalDateTime.now();
            ZoneId zoneUtc = ZoneId.of("UTC");
            ZoneId usersZone = ZoneId.systemDefault();
            ZonedDateTime nowZoneUser = ZonedDateTime.of(now, usersZone);
            ZonedDateTime nowZoneUtc = nowZoneUser.withZoneSameInstant(zoneUtc);
            LocalDateTime utcDt = nowZoneUtc.toLocalDateTime();
            DateTimeFormatter adjustTimes2 = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
            String nowUtcFormatted = adjustTimes2.format(utcDt);
            String tf1 = usernameTextField.getText();

            String fileName = "login_activity.txt";
            FileWriter fileWriter = new FileWriter(fileName, true);
            PrintWriter printWriter = new PrintWriter(fileWriter);
            printWriter.print("User " + tf1 + " gave invalid login at " + nowUtcFormatted + "-UTC");
            printWriter.println();
            printWriter.close();

            if ((Locale.getDefault().getLanguage().equals("en"))) {
                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.setTitle("Error");
                alert.setContentText("Invalid username/password, please try again.");
                alert.showAndWait();
            } else {
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
            int flag = 0;
            if (((usernameTextField.getText().equals("test")) && (passwordTextField.getText().equals("test"))) ||
                    ((usernameTextField.getText().equals("admin")) && (passwordTextField.getText().equals("admin")))) {
                JDBC.openConnection();

                LocalDateTime now = LocalDateTime.now();
                ZoneId zoneUtc = ZoneId.of("UTC");
                ZoneId usersZone = ZoneId.systemDefault();
                ZonedDateTime nowZoneUser = ZonedDateTime.of(now, usersZone);
                ZonedDateTime nowZoneUtc = nowZoneUser.withZoneSameInstant(zoneUtc);
                LocalDateTime utcDt = nowZoneUtc.toLocalDateTime();
                DateTimeFormatter adjustTimes2 = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
                String nowUtcFormatted = adjustTimes2.format(utcDt);
                String tf1 = usernameTextField.getText();

                String fileName = "login_activity.txt";
                FileWriter fileWriter = new FileWriter(fileName, true);
                PrintWriter printWriter = new PrintWriter(fileWriter);
                printWriter.print("User " + tf1 + " successfully logged in at " + nowUtcFormatted + "-UTC");
                printWriter.println();
                printWriter.close();
                try {
                    ObservableList<Appointment> appointments = AppointmentDAO.getAllAppointments();
                    for (Appointment appointment : appointments) {
                        Timestamp appointmentTs = Timestamp.valueOf(appointment.getStart());
                        LocalDateTime appointmentLdt = appointmentTs.toLocalDateTime();
                        DateTimeFormatter adjustTimes = DateTimeFormatter.ofPattern("HH:mm");
                        LocalDate nowDate = appointmentLdt.toLocalDate();
                        String formatter = adjustTimes.format(appointmentLdt);
                        long timeDifference = ChronoUnit.MINUTES.between(now, appointmentLdt);
                        System.out.println(timeDifference);
                        if (timeDifference >= 0 && timeDifference <= 15) {
                            if ((Locale.getDefault().getLanguage().equals("en"))) {
                                ++flag;
                                Alert alert2 = new Alert(Alert.AlertType.INFORMATION);
                                alert2.setTitle("Alert");
                                alert2.setContentText("Appointment [" + appointment.getId() + "] is at " + nowDate + " at " + formatter);
                                alert2.showAndWait();
                                break;
                            } else {
                                ++flag;
                                Alert alert2 = new Alert(Alert.AlertType.INFORMATION);
                                alert2.setTitle(rb.getString("alert"));
                                alert2.setContentText("Le rendez-vous [" + appointment.getId() + "] est à " + nowDate + " à " + formatter);
                                alert2.showAndWait();
                                break;
                            }
                        }
                    }
                    if (flag == 0) {
                        Alert alert2 = new Alert(Alert.AlertType.INFORMATION);
                        alert2.setTitle("Alert");
                        alert2.setContentText("There are no upcoming appointments");
                        alert2.showAndWait();
                    }
                    stage = (Stage) ((TextField) event.getSource()).getScene().getWindow();
                    scene = FXMLLoader.load(getClass().getResource("/View/MainForm.fxml"));
                    stage.setScene(new Scene(scene));
                    stage.show();

                } catch (SQLException e) {
                    e.printStackTrace();
                }
            } else {
                LocalDateTime now = LocalDateTime.now();
                ZoneId zoneUtc = ZoneId.of("UTC");
                ZoneId usersZone = ZoneId.systemDefault();
                ZonedDateTime nowZoneUser = ZonedDateTime.of(now, usersZone);
                ZonedDateTime nowZoneUtc = nowZoneUser.withZoneSameInstant(zoneUtc);
                LocalDateTime utcDt = nowZoneUtc.toLocalDateTime();
                DateTimeFormatter adjustTimes2 = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
                String nowUtcFormatted = adjustTimes2.format(utcDt);
                String tf1 = usernameTextField.getText();

                String fileName = "login_activity.txt";
                FileWriter fileWriter = new FileWriter(fileName, true);
                PrintWriter printWriter = new PrintWriter(fileWriter);
                printWriter.print("User " + tf1 + " gave invalid login at " + nowUtcFormatted + "-UTC");
                printWriter.println();
                printWriter.close();

                if ((Locale.getDefault().getLanguage().equals("en"))) {
                    Alert alert = new Alert(Alert.AlertType.ERROR);
                    alert.setTitle("Error");
                    alert.setContentText("Invalid username/password, please try again.");
                    alert.showAndWait();
                } else {
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
            int flag = 0;
            if (((usernameTextField.getText().equals("test")) && (passwordTextField.getText().equals("test"))) ||
                    ((usernameTextField.getText().equals("admin")) && (passwordTextField.getText().equals("admin")))) {
                JDBC.openConnection();

                LocalDateTime now = LocalDateTime.now();
                ZoneId zoneUtc = ZoneId.of("UTC");
                ZoneId usersZone = ZoneId.systemDefault();
                ZonedDateTime nowZoneUser = ZonedDateTime.of(now, usersZone);
                ZonedDateTime nowZoneUtc = nowZoneUser.withZoneSameInstant(zoneUtc);
                LocalDateTime utcDt = nowZoneUtc.toLocalDateTime();
                DateTimeFormatter adjustTimes2 = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
                String nowUtcFormatted = adjustTimes2.format(utcDt);
                String tf1 = usernameTextField.getText();

                String fileName = "login_activity.txt";
                FileWriter fileWriter = new FileWriter(fileName, true);
                PrintWriter printWriter = new PrintWriter(fileWriter);
                printWriter.print("User " + tf1 + " successfully logged in at " + nowUtcFormatted + "-UTC");
                printWriter.println();
                printWriter.close();

                try {
                    ObservableList<Appointment> appointments = AppointmentDAO.getAllAppointments();
                    for (Appointment appointment : appointments) {
                        Timestamp appointmentTs = Timestamp.valueOf(appointment.getStart());
                        LocalDateTime appointmentLdt = appointmentTs.toLocalDateTime();
                        DateTimeFormatter adjustTimes = DateTimeFormatter.ofPattern("HH:mm");
                        LocalDate nowDate = appointmentLdt.toLocalDate();
                        String formatter = adjustTimes.format(appointmentLdt);
                        long timeDifference = ChronoUnit.MINUTES.between(now, appointmentLdt);
                        System.out.println(timeDifference);
                        if (timeDifference >= 0 && timeDifference <= 15) {
                            if ((Locale.getDefault().getLanguage().equals("en"))) {
                                ++flag;
                                Alert alert2 = new Alert(Alert.AlertType.INFORMATION);
                                alert2.setTitle("Alert");
                                alert2.setContentText("Appointment [" + appointment.getId() + "] is at " + nowDate + " at " + formatter);
                                alert2.showAndWait();
                                break;
                            } else {
                                ++flag;
                                Alert alert2 = new Alert(Alert.AlertType.INFORMATION);
                                alert2.setTitle(rb.getString("alert"));
                                alert2.setContentText("Le rendez-vous [" + appointment.getId() + "] est à " + nowDate + " à " + formatter);
                                alert2.showAndWait();
                                break;
                            }
                        }
                    }
                    if (flag == 0) {
                        Alert alert2 = new Alert(Alert.AlertType.INFORMATION);
                        alert2.setTitle("Alert");
                        alert2.setContentText("There are no upcoming appointments");
                        alert2.showAndWait();
                    }
                    stage = (Stage) ((TextField) event.getSource()).getScene().getWindow();
                    scene = FXMLLoader.load(getClass().getResource("/View/MainForm.fxml"));
                    stage.setScene(new Scene(scene));
                    stage.show();

                } catch (SQLException e) {
                    e.printStackTrace();
                }
            } else {
                LocalDateTime now = LocalDateTime.now();
                ZoneId zoneUtc = ZoneId.of("UTC");
                ZoneId usersZone = ZoneId.systemDefault();
                ZonedDateTime nowZoneUser = ZonedDateTime.of(now, usersZone);
                ZonedDateTime nowZoneUtc = nowZoneUser.withZoneSameInstant(zoneUtc);
                LocalDateTime utcDt = nowZoneUtc.toLocalDateTime();
                DateTimeFormatter adjustTimes2 = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
                String nowUtcFormatted = adjustTimes2.format(utcDt);
                String tf1 = usernameTextField.getText();

                String fileName = "login_activity.txt";
                FileWriter fileWriter = new FileWriter(fileName, true);
                PrintWriter printWriter = new PrintWriter(fileWriter);
                printWriter.print("User " + tf1 + " gave invalid login at " + nowUtcFormatted + "-UTC");
                printWriter.println();
                printWriter.close();

                if ((Locale.getDefault().getLanguage().equals("en"))) {
                    Alert alert = new Alert(Alert.AlertType.ERROR);
                    alert.setTitle("Error");
                    alert.setContentText("Invalid username/password, please try again.");
                    alert.showAndWait();
                } else {
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
