package DAO;

import Model.Appointment;
import Model.Customer;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import java.sql.*;
import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeFormatterBuilder;
import java.time.temporal.TemporalAccessor;

public class AppointmentDAO {
    public static ObservableList<Appointment> getAllAppointments() throws SQLException {
        String sqlSelectAllAppointments = "SELECT Appointment_ID, Title, Description, Location, Type, " +
                "Start, End, Customer_ID, User_ID, Contact_ID FROM client_schedule.appointments";
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
            ZoneId databaseZone = ZoneId.of("UTC");
            ZonedDateTime databaseDateTimeStart = ZonedDateTime.of(startTime, databaseZone);
            ZoneId usersZone = ZoneId.systemDefault();
            ZonedDateTime userDateTimeStart = databaseDateTimeStart.withZoneSameInstant(usersZone);
            LocalDateTime dateTimeTableStart = userDateTimeStart.toLocalDateTime();

            LocalDateTime endTime = end.toLocalDateTime();
            ZonedDateTime databaseDateTimeEnd = ZonedDateTime.of(endTime, databaseZone);
            ZonedDateTime userDateTimeEnd = databaseDateTimeEnd.withZoneSameInstant(usersZone);
            LocalDateTime dateTimeTableEnd = userDateTimeEnd.toLocalDateTime();

            Appointment appointment = new Appointment(apptId, title, description, location, type, adjustTimes.format(dateTimeTableStart),
                    adjustTimes.format(dateTimeTableEnd), customerId, userId, contactId);
            allAppointments.add(appointment);
        }
        JDBC.closeConnection();
        return allAppointments;
    }

    public static void createAppointment(int id, String title, String description, String location, String type, Timestamp start,
                                         Timestamp end, int customerId, int userId, int contactId) throws SQLException {
        String insertStatement = "INSERT INTO client_schedule.appointments(Appointment_ID, Title, Description, Location," +
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

    public static void updateAppointment(int index, Appointment appointment) throws SQLException {
        Appointment searchedAppointment = AppointmentDAO.lookUpAppointment(index);
        AppointmentDAO.deleteAppointment(searchedAppointment);
        Timestamp start = Timestamp.valueOf(appointment.getStart());
        Timestamp end = Timestamp.valueOf(appointment.getEnd());
        AppointmentDAO.createAppointment(appointment.getId(), appointment.getTitle(), appointment.getDescription(), appointment.getLocation(),
                appointment.getType(), start, end, appointment.getCustomerId(), appointment.getUserId(),
                appointment.getContactId());
    }

    public static Appointment lookUpAppointment(int appointmentId) throws SQLException {
        for(Appointment appointment: AppointmentDAO.getAllAppointments()) {
            if(appointment.getId() == appointmentId) {
                return appointment;
            }
        }
        return null;
    }

    public static void deleteAppointment(Appointment appointment) throws SQLException {
        String deleteStatement = "DELETE FROM client_schedule.appointments WHERE Appointment_ID = ?";
        JDBC.openConnection();
        Query.setPreparedStatement(JDBC.getConnection(), deleteStatement);
        PreparedStatement ps = Query.getPreparedStatement();
        ps.setInt(1, appointment.getId());
        ps.executeUpdate();
    }

    public static ObservableList<Appointment> getFilteredAppointmentsMonth() throws SQLException {
        LocalDateTime now = LocalDateTime.now();
        int currentMonth = now.getMonthValue();
        String sqlSelectAllAppointments = "SELECT Appointment_ID, Title, Description, Location, Type, " +
                "Start, End, Customer_ID, User_ID, Contact_ID FROM client_schedule.appointments WHERE" +
                " Start LIKE '202__" +currentMonth+ "%'";
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
            ZoneId databaseZone = ZoneId.of("UTC");
            ZonedDateTime databaseDateTimeStart = ZonedDateTime.of(startTime, databaseZone);
            ZoneId usersZone = ZoneId.systemDefault();
            ZonedDateTime userDateTimeStart = databaseDateTimeStart.withZoneSameInstant(usersZone);
            LocalDateTime dateTimeTableStart = userDateTimeStart.toLocalDateTime();

            LocalDateTime endTime = end.toLocalDateTime();
            ZonedDateTime databaseDateTimeEnd = ZonedDateTime.of(endTime, databaseZone);
            ZonedDateTime userDateTimeEnd = databaseDateTimeEnd.withZoneSameInstant(usersZone);
            LocalDateTime dateTimeTableEnd = userDateTimeEnd.toLocalDateTime();

            Appointment appointment = new Appointment(apptId, title, description, location, type, adjustTimes.format(dateTimeTableStart),
                    adjustTimes.format(dateTimeTableEnd), customerId, userId, contactId);
            allAppointments.add(appointment);
        }
        JDBC.closeConnection();
        return allAppointments;
    }
}

