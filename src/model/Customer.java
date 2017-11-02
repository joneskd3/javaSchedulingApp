package model;

import controller.LoginViewController;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.beans.property.SimpleStringProperty;


public class Customer {
    
    private static int customerIdCounter = 0;
    public static ArrayList<Customer> customerArray = new ArrayList<>();
    
    private int customerId;
    private String customerName;
    private String address;
    private String address2;
    private String city;
    private String postalCode;
    private String phone;
    private Boolean active;
    private LocalDateTime createdDate;
    private User createdBy;

    public int getCustomerId() {
        return customerId;
    }

    public void setCustomerId(int customerId) {
        this.customerId = customerId;
    }
    public static void getMaxCustomerId() throws SQLException{
        
        String maxQuery = "SELECT MAX(customerId) AS customerId FROM customer";
        ResultSet results = Database.resultQuery(maxQuery);
        while(results.next()){
            customerIdCounter = results.getInt("customerId");
        }
    }
    
    
    public Customer() throws SQLException{
        this("","","","","","");
    }
    public Customer(String customerName,String address, String address2, 
            String city,String postalCode, String phone) throws SQLException{
        
        getMaxCustomerId();
        
        this.setCustomerId(customerIdCounter + 1);
        this.customerName = customerName;
        this.address = address;
        this.address2 = address2;
        this.city = city;
        this.postalCode = postalCode;
        this.phone = phone;
        
        this.addToDB();
    }

    public Customer(int customerId, String customerName,String address, String address2, 
            String city,String postalCode, String phone) throws SQLException{
        
 
        
        this.setCustomerId(customerId);
        this.customerName = customerName;
        this.address = address;
        this.address2 = address2;
        this.city = city;
        this.postalCode = postalCode;
        this.phone = phone;
    }


    public String getCustomerName() {
        return customerName;
    }

    public void setCustomerName(String customerName) {
        this.customerName = customerName;
        try {
            this.updateDB();
        } catch (SQLException ex) {
            Logger.getLogger(Customer.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
        try {
            this.updateDB();
        } catch (SQLException ex) {
            Logger.getLogger(Customer.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public String getAddress2() {
        return address2;
    }

    public void setAddress2(String address2) {
        this.address2 = address2;
        try {
            this.updateDB();
        } catch (SQLException ex) {
            Logger.getLogger(Customer.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
        try {
            this.updateDB();
        } catch (SQLException ex) {
            Logger.getLogger(Customer.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public String getPostalCode() {
        return postalCode;
    }

    public void setPostalCode(String postalCode) {
        this.postalCode = postalCode;
        try {
            this.updateDB();
        } catch (SQLException ex) {
            Logger.getLogger(Customer.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
        try {
            this.updateDB();
        } catch (SQLException ex) {
            Logger.getLogger(Customer.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public Boolean getActive() {
        return active;
    }

    public void setActive(Boolean active) {
        this.active = active;
        try {
            this.updateDB();
        } catch (SQLException ex) {
            Logger.getLogger(Customer.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public LocalDateTime getCreatedDate() {
        return createdDate;
    }

    public void setCreatedDate(LocalDateTime createdDate) {
        this.createdDate = createdDate;
    }

    public User getCreatedBy() {
        return createdBy;
    }

    public void setCreatedBy(User createdBy) {
        this.createdBy = createdBy;
    }

    
    public void addToDB() throws SQLException{
    
        String insertCustomer = 
            "INSERT INTO customer (customerId, customerName, addressId, active, createDate, createdBy, lastUpdate, lastUpdateBy) "
            + "VALUES ("
            + this.customerId + ", "
            + "'" + this.customerName + "', "
            + this.customerId  + ", "
            + 1  + ", "
            + "CURRENT_TIMESTAMP, "
            + "'" + LoginViewController.currentUser + "', "
            + "CURRENT_TIMESTAMP, "
            + "'" + LoginViewController.currentUser + "'"
            + ")";

        System.out.println("INSERT QUERY: \n" + insertCustomer);
        Database.actionQuery(insertCustomer);
        System.out.println("INSERT SUCCESS");
        
        this.updateDB();

        this.addCustomerToDB();
        this.addCityToDB();
    }
    public void addCustomerToDB() throws SQLException{
        String insertAddress = 
        "INSERT INTO address (addressId, address, address2, cityId, postalCode, phone, createDate, createdBy, lastUpdate, lastUpdateBy) "
        + "VALUES ("
        + this.customerId + ", "
        + "'" + this.address + "', "
        + "'" + this.address2 + "', "
        + this.customerId + ", "
        + "'" + this.postalCode + "', "
        + "'" + this.phone + "', "
        + "CURRENT_TIMESTAMP, "
        + "'" + LoginViewController.currentUser + "', "
        + "CURRENT_TIMESTAMP, "
        + "'" + LoginViewController.currentUser + "'"
        + ")";
        
        System.out.println("INSERT QUERY: \n" + insertAddress);
        Database.actionQuery(insertAddress);
        System.out.println("INSERT SUCCESS");
    }
    public void addCityToDB() throws SQLException{
         String insertCity = 
        "INSERT INTO city (cityId, city, countryId, createDate, createdBy, lastUpdate, lastUpdateBy) "
        + "VALUES ("
        + this.customerId + ", "
        + "'" + this.city + "', "
        + this.customerId + ", "
        + "CURRENT_TIMESTAMP, "
        + "'" + LoginViewController.currentUser + "', "
        + "CURRENT_TIMESTAMP, "
        + "'" + LoginViewController.currentUser + "'"
        + ")";   
                
        System.out.println("INSERT QUERY: \n" + insertCity);
        Database.actionQuery(insertCity);
        System.out.println("INSERT SUCCESS"); 
    }
    public static void populateFromDB() throws SQLException{
        String query = "SELECT * FROM customer";
        ResultSet results = Database.resultQuery(query);
        
        customerArray.clear();
        
        while(results.next()){ 
            int customerId = results.getInt("customerId"); 
            String customerName = results.getString("customerName");
            String address = "";
            String address2 = "";
            String city = "";
            String postalCode = "";
            String phone = "";
           
            Customer customer = new Customer(customerId, customerName, address, address2, city, postalCode, phone);
            
            customerArray.add(customer);
            
        }
    }
    public void updateDB() throws SQLException{
                
        String updateCustomer =
            "UPDATE customer "
            + "SET "
                + "customerName = '" + this.customerName + "', "
                + "active = " + 1 + " "
            + "WHERE "
                + "customerId = " + this.customerId;
        System.out.println(
            "===="
            + updateCustomer
            + "===="
        );
        Database.actionQuery(updateCustomer);
        
        
        String updateAddress =
            "UPDATE address "
            + "SET "
                + "address = '" + this.address + "', "
                + "address2 = '" + this.address2 + "', "
                + "postalCode = '" + this.postalCode + "', "
                + "phone = '" + this.phone + "' "
            + "WHERE "
                + "addressId = " + this.customerId;
        System.out.println(
            "===="
            + updateAddress
            + "===="
        );
        Database.actionQuery(updateAddress);
        
        String updateCity =
            "UPDATE city "
            + "SET "
                + "city = '" + this.city + "' "
            + "WHERE "
                + "addressId = " + this.customerId;
        System.out.println(
            "===="
            + updateAddress
            + "===="
        );
        Database.actionQuery(updateAddress);
        
        
    }
}
