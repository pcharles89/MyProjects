package Main;

import DAO.JDBC;
import DAO.Query;
import Model.MonthCb;
import Model.TypeCb;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.sql.*;
import java.util.Locale;
import java.util.ResourceBundle;

public class Main extends Application {

    @Override
    public void start(Stage stage) throws Exception {
        Parent root = FXMLLoader.load(getClass().getResource("/View/LogIn.fxml"));
        stage.setTitle("Java Scheduling Application");
        stage.setScene(new Scene(root, 800, 600));
        stage.show();
    }

    public static void main(String[] args) throws SQLException {
        String query = "select User_Name from client_schedule.users where User_ID=1";
        JDBC.openConnection();
        Connection conn = JDBC.getConnection();
        /*Statement statement = conn.createStatement();
        ResultSet rs = statement.executeQuery(query); */

        PreparedStatement preparedStatement = conn.prepareStatement(query);
        ResultSet rs = preparedStatement.executeQuery(query);
        rs.next();
        String user = rs.getString(1);
        System.out.println(user);

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
