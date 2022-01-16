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

/** This class deals with accessing appointment information from the database. Queries the appointment table in the database
 * and retrieves the results.*/
public class AppointmentDAO {

    /** Retrieves all appointments from the database. Queries the appointments table in the database and retrieves all
     * appointments.
     * @return returns all appointments from the appointment table in the database.
     */
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

    /** Creates a new appointment in the database. Inserts the new appointment into the appointments table in the database using
     * the arguments provided (which correspond to the tables fields).*/
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

    /** Updates an appointment in the database.
     * @param index the appointment id of the appointment being updated
     * @param appointment the new appointment after changes have been made
     */
    public static void updateAppointment(int index, Appointment appointment) throws SQLException {
        Appointment searchedAppointment = AppointmentDAO.lookUpAppointment(index);
        AppointmentDAO.deleteAppointment(searchedAppointment);
        Timestamp start = Timestamp.valueOf(appointment.getStart());
        Timestamp end = Timestamp.valueOf(appointment.getEnd());
        AppointmentDAO.createAppointment(appointment.getId(), appointment.getTitle(), appointment.getDescription(), appointment.getLocation(),
                appointment.getType(), start, end, appointment.getCustomerId(), appointment.getUserId(),
                appointment.getContactId());
    }

    /** Looks up the appointment by id. Used in conjunction with the updateAppointment method to update an appointment.
     * @param appointmentId the appointment id of the appointment that is to be updated
     * @return returns the appointment that is going to be updated
     */
    public static Appointment lookUpAppointment(int appointmentId) throws SQLException {
        for(Appointment appointment: AppointmentDAO.getAllAppointments()) {
            if(appointment.getId() == appointmentId) {
                return appointment;
            }
        }
        return null;
    }

    /** Deletes an appointment from the database. Queries the appointments table in the database and the appointment from it.
     * @param appointment the appointment to be deleted
     */
    public static void deleteAppointment(Appointment appointment) throws SQLException {
        String deleteStatement = "DELETE FROM client_schedule.appointments WHERE Appointment_ID = ?";
        JDBC.openConnection();
        Query.setPreparedStatement(JDBC.getConnection(), deleteStatement);
        PreparedStatement ps = Query.getPreparedStatement();
        ps.setInt(1, appointment.getId());
        ps.executeUpdate();
    }

    /** Filters appointments by month. Queries the appointments table in the database and filters the results based on month.
     * @return returns all the appointments in the requested month
     */
    public static ObservableList<Appointment> getFilteredAppointmentsMonth() throws SQLException {
        LocalDateTime now = LocalDateTime.now();
        int currentMonth = now.getMonthValue();
        String month = String.format("%02d", currentMonth);
        int currentYear = now.getYear();
        String sqlSelectAllAppointments = "SELECT Appointment_ID, Title, Description, Location, Type, " +
                "Start, End, Customer_ID, User_ID, Contact_ID FROM client_schedule.appointments WHERE" +
                " Start LIKE '" +currentYear+"-" +month+ "%'";
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

    /** Filters appointments by week. Queries the appointments table in the database and filters the results based on week.
     * @return returns all the appointments in the requested week
     */
    public static ObservableList<Appointment> getFilteredAppointmentsWeek() throws SQLException {
        DateTimeFormatter adjustTimes = DateTimeFormatter.ofPattern("yyyy-MM-dd");
        DateTimeFormatter adjustTimes2 = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
        LocalDateTime now = LocalDateTime.now();
        String nowFormatted = adjustTimes.format(now);
        LocalDateTime nowPlus = now.plusDays(6);
        String nowPlusFormatted = adjustTimes.format(nowPlus);
        String sqlSelectAllAppointments = "SELECT Appointment_ID, Title, Description, Location, Type, " +
                "Start, End, Customer_ID, User_ID, Contact_ID FROM client_schedule.appointments WHERE" +
                " Start BETWEEN '" + nowFormatted + "' AND '" + nowPlusFormatted + "'";
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

            Appointment appointment = new Appointment(apptId, title, description, location, type, adjustTimes2.format(dateTimeTableStart),
                    adjustTimes2.format(dateTimeTableEnd), customerId, userId, contactId);
            allAppointments.add(appointment);
        }
        JDBC.closeConnection();
        return allAppointments;
    }
}

