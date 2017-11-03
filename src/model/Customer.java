package model;

import controller.LoginViewController;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;


public class Customer {
    
    private static int customerIdCounter;
    public static ArrayList<Customer> customerArray = new ArrayList<>();
    
    private int customerId;
    private String customerName;
    private String address;
    private String address2;
    private String city;
    private String postalCode;
    private String phone;
    private Boolean active;
    
    /*Constructors*/
    public Customer() throws SQLException{
        this("","","","","","");
    }
    public Customer(String customerName,String address, String address2,
            String city,String postalCode, String phone) throws SQLException{
        
        getMaxCustomerId();
        
        this.customerId = (customerIdCounter + 1);
        this.customerName = customerName;
        this.address = address;
        this.address2 = address2;
        this.city = city;
        this.postalCode = postalCode;
        this.phone = phone;
        
        this.addToDB();
    }
    public Customer(int customerId, String customerName,String address, String address2, 
            String city,String postalCode, String phone, Boolean active) throws SQLException{

        this.customerId = customerId;
        this.customerName = customerName;
        this.address = address;
        this.address2 = address2;
        this.city = city;
        this.postalCode = postalCode;
        this.phone = phone;
        this.active = active;
    }
    /*Customer ID */
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
            //convert to string first to check for null
            String id = results.getString("customerId");
            if (id == null){
                customerIdCounter = -1;
            } else {
                customerIdCounter = Integer.parseInt(id);
            }
        }
    }
    /*Customer Name*/
    public String getCustomerName() {
        return customerName;
    }
    public void setCustomerName(String customerName) throws SQLException {
        this.customerName = customerName;
        this.updateDB();
    }
    /*Address*/
    public String getAddress() {
        return address;
    }
    public void setAddress(String address) throws SQLException {
        this.address = address;
        this.updateDB();
    }
    /*Address 2*/
    public String getAddress2() {
        return address2;
    }
    public void setAddress2(String address2) throws SQLException {
        this.address2 = address2;
        this.updateDB();
    }
    /*City*/
    public String getCity() {
        return city;
    }
    public void setCity(String city) throws SQLException {
        this.city = city;
        this.updateDB();
    }
    /*Postal Code */
    public String getPostalCode() {
        return postalCode;
    }
    public void setPostalCode(String postalCode) throws SQLException {
        this.postalCode = postalCode;
        this.updateDB();
    }
    /*Phone*/
    public String getPhone() {
        return phone;
    }
    public void setPhone(String phone) throws SQLException {
        this.phone = phone;
        this.updateDB();
    }
    /*Active*/
    public Boolean getActive() {
        return active;
    }
    public void setActive(Boolean active) throws SQLException {
        this.active = active;
        this.updateDB();
    }
    /*DB Methods*/
    public void addToDB() throws SQLException{
    
        String insertCustomer = 
        "INSERT INTO customer (customerId, customerName, addressId, active, createDate, createdBy, lastUpdate, lastUpdateBy) "
        + "VALUES ("
            + this.customerId + ", "
            + "'" + this.customerName + "', "
            + this.customerId + ", "
            + 1 + ", "
            + "CURRENT_TIMESTAMP, "
            + "'" + LoginViewController.currentUser + "', "
            + "CURRENT_TIMESTAMP, "
            + "'" + LoginViewController.currentUser + "'"
        + ")";

        Database.actionQuery(insertCustomer);

        this.addAddressToDB();
        this.addCityToDB();
    }
    public void addAddressToDB() throws SQLException{
        
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
        
        Database.actionQuery(insertAddress);
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
                
        Database.actionQuery(insertCity);
    }
    public void updateDB() throws SQLException{
                
        String updateCustomer =
            "UPDATE customer "
            + "SET "
                + "customerName = '" + this.customerName + "', "
                + "active = " + this.activeToInt() + " "
            + "WHERE "
                + "customerId = " + this.customerId;
      
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
    
        Database.actionQuery(updateAddress);
        
        String updateCity =
            "UPDATE city "
            + "SET "
                + "city = '" + this.city + "' "
            + "WHERE "
                + "cityId = " + this.customerId;

        Database.actionQuery(updateCity);
        
        
    }
    public static void populateFromDB() throws SQLException{
        String customerQuery = 
                "SELECT "
                    + "customerId, "
                    + "customerName, "
                    + "address.address AS address, "
                    + "address.address2 AS address2, "
                    + "city.city AS city, "
                    + "address.postalCode AS postalCode, "
                    + "address.phone AS phone, "
                    + "active "
                + "FROM customer "
                + "LEFT JOIN address ON customer.addressId = address.addressId "
                + "Left JOIN city ON address.cityId = city.cityId "
                + "ORDER BY customerId";
        
        ResultSet results = Database.resultQuery(customerQuery);
         
        customerArray.clear();
        
        
        Boolean active; 
        while(results.next()){ 
            int customerId = results.getInt("customerId"); 
            String customerName = results.getString("customerName");
            String address = results.getString("address");
            String address2 = results.getString("address2");
            String city = results.getString("city");
            String postalCode = results.getString("postalCode");
            String phone = results.getString("phone");
            int activeInt = results.getInt("active");
            if (activeInt == 0){
                active = true;
            } else {
                active = false;
            }
            
           
            Customer customer = new Customer(customerId, customerName, address, address2, city, postalCode, phone, active);
            
            customerArray.add(customer);
            
        }
        for(Customer customer : customerArray){
            System.out.println(customer.getCustomerId());
        }
    }  
    public int activeToInt (){
        if (this.getActive() == null || this.getActive()){
            return 1;
        } else {
            return 0;
        }
    }
}
