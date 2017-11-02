package model;

import controller.LoginViewController;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import javafx.beans.property.SimpleStringProperty;

public class Appointment {
    
        
    //global appointmentID counter to identify appt in SQL
    private static int appointmentIdCounter;
    
    private int appointmentId;
    private LocalDateTime start;
    private String title;
    private Customer customer;
    
    /* Constructor */
    public Appointment() throws SQLException{
        this(null,"",null);
    }
    public Appointment(LocalDateTime time, String type, Customer customer) throws SQLException{
        //updates static appointmentIDCounter to max in DB
        getMaxAppointmentID();
        
        this.setAppointmentId(appointmentIdCounter + 1);
        this.setStart(time);
        this.setTitle(type);
        this.setCustomer(customer);
        
        this.addToDB();
    }
    public Appointment(int appointmentId, LocalDateTime time, String title, Customer customer) throws SQLException{        
        this.setAppointmentId(appointmentId);
        this.setStart(time);
        this.setTitle(title);
        this.setCustomer(customer);
    }
    /* Appointment ID */
    public int getAppointmentId() {
        return appointmentId;
    }
    public void setAppointmentId(int appointmentId) {
        this.appointmentId = appointmentId;
    }
    public static void getMaxAppointmentID() throws SQLException{
        
        String maxQuery = "SELECT MAX(appointmentId) AS appointmentId FROM appointment";
        ResultSet results = Database.resultQuery(maxQuery);
        while(results.next()){
            appointmentIdCounter = results.getInt("appointmentID");
        }
    }
    /* Appointment Time */
    public LocalDateTime getStart() {
        return start;
    }
    public void setStart(LocalDateTime start) throws SQLException {
        this.start = start;
        updateDB();
    }
    /* Appointment Description */
    public String getTitle() {
        return title;
    }
    public void setTitle(String title) throws SQLException {
        this.title = title;
        updateDB();
    }
    /* Appointment Customer */
    public Customer getCustomer() {
        return customer;
    }
    public void setCustomer(Customer customer) throws SQLException {
        this.customer = customer;
        updateDB();
    }
    /* Database Methods */
    public void addToDB() throws SQLException{
        String date = "";
        if (this.getStart() != null){
             date = this.getStart().format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm"));
        }
        
        
        
        String insert = "INSERT INTO appointment (appointmentId, customerId,"
            + " title, description, location, contact, url, start,"
            + " end, createDate, createdBy, lastUpdate, lastUpdateBy) \n"
            + " VALUES ("
            + this.appointmentId + ", "
            + 10 + ", "
            + "'" + this.title + "', "
            + "'', "
            + "'', "
            + "'', "
            + "'', "
            + "'" + date + "', "
            + "'" + date + "', "
            + "CURRENT_TIMESTAMP, "
            + "'" + LoginViewController.currentUser + "', "
            + "CURRENT_TIMESTAMP, "
            + "'" + LoginViewController.currentUser + "'"
            + ")";
        
        System.out.println("INSERT QUERY: \n" + insert);
        Database.actionQuery(insert);
        System.out.println("INSERT SUCCESS");
    }
    public void updateDB() throws SQLException{
        String date = "";
        if (this.getStart() != null){
             date = this.getStart().format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm"));
        }
        
        String update =
            "UPDATE appointment \n"
            + "SET "
                + "title = '" + this.getTitle() + "', "
                + "start = '" + date + "', "
                + "customerID = 3 \n"
            + "WHERE "
                + "appointmentId = " + this.appointmentId;
        System.out.println(
            "===="
            + update
            + "===="
        );
        Database.actionQuery(update);
    }
}
