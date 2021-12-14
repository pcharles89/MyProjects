package DAO;

import Model.Appointment;
import Model.Customer;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import java.sql.*;
import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeFormatterBuilder;
import java.time.temporal.TemporalAccessor;

public class AppointmentDAO {
    public static ObservableList<Appointment> getAllAppointments() throws SQLException {
        String sqlSelectAllAppointments = "SELECT Appointment_ID, Title, Description, Location, Type, " +
                "Start, End, Customer_ID, User_ID, Contact_ID FROM client_schedule.appointments";
        //SimpleDateFormat adjustTimes = new SimpleDateFormat("yyyy-MM-dd HH:mm");
        DateTimeFormatter adjustTimes = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
        ObservableList<Appointment> allAppointments = FXCollections.observableArrayList();
        JDBC.openConnection();
        Query.setPreparedStatement(JDBC.getConnection(), sqlSelectAllAppointments);
        ResultSet rs = Query.getPreparedStatement().executeQuery(sqlSelectAllAppointments);
        while(rs.next()) {
            int apptId = rs.getInt("Appointment_ID");
            String title = rs.getString("Title");
            String description = rs.getString("Description");
            String location = rs.getString("Location");
            String type = rs.getString("Type");
            Timestamp start = rs.getTimestamp("Start");
            Timestamp end = rs.getTimestamp("End");
            int customerId = rs.getInt("Customer_ID");
            int userId = rs.getInt("User_ID");
            int contactId = rs.getInt("Contact_ID");

            LocalDateTime startTime = start.toLocalDateTime();
            LocalDateTime endTime = end.toLocalDateTime();
            Appointment appointment = new Appointment(apptId, title, description,location, type, adjustTimes.format(startTime),
                    adjustTimes.format(endTime), customerId, userId, contactId);
            allAppointments.add(appointment);
        }
        JDBC.closeConnection();
        return allAppointments;
    }

    public static void createAppointment(int id, String title, String description, String location, String type, Timestamp start,
                                         Timestamp end, int customerId, int userId, int contactId) throws SQLException {
        String insertStatement = "INSERT INTO client_schedule.customers(Appointment_ID, Title, Description, Location," +
                "Type, Start, End, Customer_ID, User_ID, Contact_ID) VALUES(?,?,?,?,?,?,?,?,?,?)";
        JDBC.openConnection();
        Query.setPreparedStatement(JDBC.getConnection(), insertStatement);
        PreparedStatement ps = Query.getPreparedStatement();
        ps.setInt(1, id);
        ps.setString(2, title);
        ps.setString(3, description);
        ps.setString(4, location);
        ps.setString(5, type);
        ps.setTimestamp(6, start);
        ps.setTimestamp(7, end);
        ps.setInt(8, customerId);
        ps.setInt(9, userId);
        ps.setInt(10, contactId);

        ps.executeUpdate();
    }
}
