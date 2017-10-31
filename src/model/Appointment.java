package model;

import java.time.LocalDateTime;
import javafx.beans.property.SimpleStringProperty;

public class Appointment {
    private LocalDateTime time;
    private String type;
    private Customer customer;
    
    public Appointment(){
        this(null,"",null);
    }

    public Appointment(LocalDateTime time, String type, Customer customer){
        this.setTime(time);
        this.setType(type);
        this.setCustomer(customer);
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
    public void setTime(LocalDateTime time) {
        this.time = time;
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
    public void setType(String type) {
        this.type = type;
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
    public void setCustomer(Customer customer) {
        this.customer = customer;
    }
}
