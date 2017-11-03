package model;

import controller.LoginViewController;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Collections;

public class Appointment {
    //global appointmentID counter to identify appt in SQL
    private static int appointmentIdCounter;
    private static ArrayList<Appointment> appointmentArray = new ArrayList<>();

    private int appointmentId;
    private LocalDateTime start;
    private String title;
    private int customerId;
    private Customer customer;
    
    /* Constructor */
    public Appointment() throws SQLException{
        this(null,"",0);
    }
    public Appointment(LocalDateTime time, String type, int customer) throws SQLException{
        //updates static appointmentIDCounter to max in DB
        getMaxAppointmentID();
        
        this.setAppointmentId(appointmentIdCounter + 1);
        this.setStart(time);
        this.setTitle(type);
        this.setCustomerId(customer);
        
        this.addToDB();
    }
    //used if importing appointments with appointmentId from DB
    public Appointment(int appointmentId, LocalDateTime time, String title, int customer) throws SQLException{        
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
            //convert to string first to check for null
            String id = results.getString("appointmentID");
            if (id == null){
                appointmentIdCounter = -1;
            } else {
                appointmentIdCounter = Integer.parseInt(id);
            }
        }
    }
    /* Appointment Start */
    public LocalDateTime getStart() {
        return start;
    }
    public void setStart(LocalDateTime start) throws SQLException {
        this.start = start;
        updateDB();
    }
    /* Appointment Title */
    public String getTitle() {
        return title;
    }
    public void setTitle(String title) throws SQLException {
        this.title = title;
        updateDB();
    }
    /* Appointment Customer */
    public int getCustomerId() {
        return customerId;
    }
    public void setCustomerId(int customerId) throws SQLException {
        this.customerId = customerId;
        
        this.updateDB();
        this.setCustomer(customerId);
    }
    public Customer getCustomer() {
        return customer;
    }
    public void setCustomer(int customerId) throws SQLException {
        this.customer = Customer.customerArray.get(customerId);
        updateDB();
    }
    public void setCustomer(Customer customer) throws SQLException{
        this.customer = customer;
        updateDB();
    }
    /* Database Methods */
    public void addToDB() throws SQLException{
      
        String insert = 
            "INSERT INTO appointment (appointmentId, customerId,"
                + " title, description, location, contact, url, start,"
                + " end, createDate, createdBy, lastUpdate, lastUpdateBy) "
            + " VALUES ("
                + this.appointmentId + ", "
                + this.getCustomer().getCustomerId() + ", "
                + "'" + this.title + "', "
                + "'', "
                + "'', "
                + "'', "
                + "'', "
                + "'" + this.formatDate() + "', "
                + "'" + this.formatDate() + "', "
                + "CURRENT_TIMESTAMP, "
                + "'" + LoginViewController.currentUser + "', "
                + "CURRENT_TIMESTAMP, "
                + "'" + LoginViewController.currentUser + "'"
            + ")";
        
        Database.actionQuery(insert);
    }
    public void updateDB() throws SQLException{
        
        System.out.println(this.getCustomer());
        String update =
            "UPDATE appointment "
            + "SET "
                + "title = '" + this.getTitle() + "', "
                + "start = '" + this.formatDate() + "', "
                + "customerId = " + this.getCustomerId() + ", "
                + "lastUpdate = CURRENT_TIMESTAMP, "
                + "lastUpdateBy = '" + LoginViewController.currentUser + "' "
            + "WHERE "
                + "appointmentId = " + this.appointmentId;
        
        Database.actionQuery(update);
    }
    //converts appointment date to correct format for SQL
    public String formatDate(){
        String date = "";
        if (this.getStart() != null){
             date = this.getStart().format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm"));
        }
        return date;
    }

    public static ArrayList<Appointment> getAppointments() {
        return appointmentArray;
    }
    public static void sortAppointments() {
        Collections.sort(appointmentArray, (Appointment appt1, Appointment appt2) -> appt1.getStart().compareTo(appt2.getStart()));
    }
    public void addAppointment(Appointment appointment) {
        appointmentArray.add(appointment);
    }
    public static void populateFromDB() throws SQLException {
        String query = "SELECT * FROM appointment";
        ResultSet results = Database.resultQuery(query);
        appointmentArray.clear();
        while (results.next()) {
            int appointmentId = results.getInt("appointmentId");
            System.out.println(appointmentId);
            String timeString = results.getString("start");
            System.out.println(timeString);
            LocalDateTime start = LocalDateTime.parse(timeString, DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss.n"));
            String title = results.getString("title");
            int customerId = results.getInt("customerId");
            Appointment appt = new Appointment(appointmentId, start, title, customerId);
            
            appointmentArray.add(appt);
        }
    }
}
