package DAO;

import Model.Appointment;
import Model.Customer;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
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
        DateTimeFormatter adjustTimes = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm");
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
}
