package model;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import javafx.beans.property.SimpleStringProperty;

public class Appointment {
    
    
    private static int appointmentIdCounter;
    private int appointmentId;
    private LocalDateTime time;
    private String type;
    private Customer customer;
    
    public Appointment() throws SQLException{
        this(null,"",null);
    }

    public Appointment(LocalDateTime time, String type, Customer customer) throws SQLException{
        getMaxAppointmentID();
        this.setAppointmentId(appointmentIdCounter + 1);
        this.setTime(time);
        this.setType(type);
        this.setCustomer(customer);
        String insert = "INSERT INTO APPOINTMENTS (appointmentId, customerId, title) VALUES ("
                + (appointmentId) +","
                + 10 + ","
                + "'" + type + "'"
                + ")";
        System.out.println(insert);
        addAppointmentToDB(insert);
    }

    public int getAppointmentId() {
        return appointmentId;
    }

    public void setAppointmentId(int appointmentId) {
        this.appointmentId = appointmentId;
    }
    public static void getMaxAppointmentID() throws SQLException{
        String maxAppointmentIDQuery = "SELECT MAX(appointmentId) AS appointmentId FROM APPOINTMENTS";
        ResultSet results = Database.resultQuery(maxAppointmentIDQuery);
        while(results.next()){
            System.out.println("RESULTS2: " + results.getString("appointmentId"));
            //String apptId = results.getString("appointmentId");
            appointmentIdCounter = results.getInt("appointmentID");
        }
    }
    /**
     * @return the time
     */
    public LocalDateTime getTime() {
        return time;
    }

    /**
     * @param time the time to set
     */
    public void setTime(LocalDateTime time) throws SQLException {
        this.time = time;
        updateDB();
    }

    /**
     * @return the type
     */
    public String getType() {
        return type;
    }

    /**
     * @param type the type to set
     */
    public void setType(String type) throws SQLException {
        this.type = type;
        updateDB();
    }

    /**
     * @return the customer
     */
    public Customer getCustomer() {
        return customer;
    }

    /**
     * @param customer the customer to set
     */
    public void setCustomer(Customer customer) throws SQLException {
        this.customer = customer;
        updateDB();
    }
    public void addAppointmentToDB(String insert) throws SQLException{
        Database.actionQuery(insert);
    }
    
    public void updateDB() throws SQLException{
        String date = "";
        if (this.getTime() != null){
             date = this.getTime().format(DateTimeFormatter.ofPattern("yyyy-MM-dd"));
        }
        String update =
            "UPDATE APPOINTMENTS \n"
            + "SET "
                + "title = '" + this.getType() + "', "
                + "start = '" + date + "', "
                + "customerID = 3 \n"
            + "WHERE "
                + "appointmentId = " + this.appointmentId;
        System.out.println(update);
        Database.actionQuery(update);
    }
    
}
