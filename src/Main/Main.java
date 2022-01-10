package Main;

import DAO.JDBC;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.sql.*;
import java.util.Locale;
import java.util.ResourceBundle;

/** This class creates an app that manages customers and their appointments.*/
public class Main extends Application {

    /** Mandatory method when creating JavaFX app. Sets title and scene of the primary stage.*/
    @Override
    public void start(Stage stage) throws Exception {
        Parent root = FXMLLoader.load(getClass().getResource("/View/LogIn.fxml"));
        stage.setTitle("Java Scheduling Application");
        stage.setScene(new Scene(root, 800, 600));
        stage.show();
    }

    /** This is the main method. It will be called when the program runs. C:\Users\bwspc\IdeaProjects\JavaProjectSoftwareII\javadoc*/
    public static void main(String[] args) throws SQLException {
        String query = "select User_Name from client_schedule.users where User_ID=1";
        JDBC.openConnection();
        Connection conn = JDBC.getConnection();

        PreparedStatement preparedStatement = conn.prepareStatement(query);
        ResultSet rs = preparedStatement.executeQuery(query);
        rs.next();
        /*String user = rs.getString(1);
        System.out.println(user); */

        Locale.setDefault(new Locale("fr"));
        ResourceBundle rb = ResourceBundle.getBundle("Utilities/Nat_fr", Locale.getDefault());
        if(Locale.getDefault().getLanguage().equals("fr")) {
            System.out.println(rb.getString("username") + " " + rb.getString("password"));
        }
        //System.out.println(System.getProperties().get("javafx.runtime.version"));
        launch(args);
        JDBC.closeConnection();
    }
}
