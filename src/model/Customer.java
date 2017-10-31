package model;

import java.time.LocalDateTime;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;


public class Customer {
    
    private StringProperty customerName;
    private StringProperty address;
    private StringProperty address2;
    private StringProperty city;
    private StringProperty postalCode;
    private StringProperty phone;
    private Boolean active;
    private LocalDateTime createdDate;
    private User createdBy;
    
    
    public Customer(){
        this("","","","","","",false,null,null);
    }
    public Customer(String customerName,String address, String address2, 
            String city,String postalCode, String phone, Boolean active, LocalDateTime createdDate, User createdBy){
        
        this.customerName = new SimpleStringProperty(customerName);
        this.address = new SimpleStringProperty(address);
        this.address2 = new SimpleStringProperty(address2);
        this.city = new SimpleStringProperty(city);
        this.postalCode = new SimpleStringProperty(postalCode);
        this.phone = new SimpleStringProperty(phone);
        this.active = active;
        this.createdDate = createdDate;
        this.createdBy = createdBy;     
    }

    /**
     * @return the customerName
     */
    public String getCustomerName() {
        return customerName.get();
    }

    /**
     * @param customerName the customerName to set
     */
    public void setCustomerName(String customerName) {
        this.customerName.set(customerName);
    }
    public StringProperty getCustomerNameProperty(){
        return customerName;
    }
    /**
     * @return the address
     */
    public String getAddress() {
        return address.get();
    }

    /**
     * @param address the address to set
     */
    public void setAddress(String address) {
        this.address.set(address);
    }

    /**
     * @return the address2
     */
    public String getAddress2() {
        return address2.get();
    }

    /**
     * @param address2 the address2 to set
     */
    public void setAddress2(String address2) {
        this.address2.set(address2);
    }

    /**
     * @return the city
     */
    public String getCity() {
        return city.get();
    }

    /**
     * @param city the city to set
     */
    public void setCity(String city) {
        this.city.set(city);
    }

    /**
     * @return the postalCode
     */
    public String getPostalCode() {
        return postalCode.get();
    }

    /**
     * @param postalCode the postalCode to set
     */
    public void setPostalCode(String postalCode) {
        this.postalCode.set(postalCode);
    }

    /**
     * @return the phone
     */
    public String getPhone() {
        return phone.get();
    }

    /**
     * @param phone the phone to set
     */
    public void setPhone(String phone) {
        this.phone.set(phone);
    }

    /**
     * @return the active
     */
    public Boolean getActive() {
        return active;
    }

    /**
     * @param active the active to set
     */
    public void setActive(Boolean active) {
        this.active = active;
    }

    /**
     * @return the createdDate
     */
    public LocalDateTime getCreatedDate() {
        return createdDate;
    }

    /**
     * @param createdDate the createdDate to set
     */
    public void setCreatedDate(LocalDateTime createdDate) {
        this.createdDate = createdDate;
    }

    /**
     * @return the createdBy
     */
    public User getCreatedBy() {
        return createdBy;
    }

    /**
     * @param createdBy the createdBy to set
     */
    public void setCreatedBy(User createdBy) {
        this.createdBy = createdBy;
    }
    
}
