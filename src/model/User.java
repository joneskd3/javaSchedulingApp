/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package model;

import java.time.LocalDateTime;
import java.util.ArrayList;

/**
 *
 * @author owner
 */

public class User {
    private int userId;
    private String userName;
    private String password;
    private boolean active;
    private User createdBy;
    private LocalDateTime createdDate;
    private LocalDateTime lastUpdate;
    private User lastUpdatedBy;

    public static int userIdCounter = 0;
    public static ArrayList<User> userArray = new ArrayList<>();

    
    public User(){
        this("","",false,null,null,null,null);
    }
    
    public User(String userName, String password, boolean active, User createdBy, LocalDateTime createdDate, LocalDateTime lastUpdate, User lastUpdatedBy) {
        this.userId = userIdCounter;
        this.userName = userName;
        this.password = password;
        this.active = active;
        this.createdBy = createdBy;
        this.createdDate = createdDate;
        this.lastUpdate = lastUpdate;
        this.lastUpdatedBy = lastUpdatedBy;
        userIdCounter++;
        addUserToArray(this);
    }
    

    public int getUserId() {
        return userId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public boolean isActive() {
        return active;
    }

    public void setActive(boolean active) {
        this.active = active;
    }

    public User getCreatedBy() {
        return createdBy;
    }

    public void setCreatedBy(User createdBy) {
        this.createdBy = createdBy;
    }

    public LocalDateTime getCreatedDate() {
        return createdDate;
    }

    public void setCreatedDate(LocalDateTime createdDate) {
        this.createdDate = createdDate;
    }

    public LocalDateTime getLastUpdate() {
        return lastUpdate;
    }

    public void setLastUpdate(LocalDateTime lastUpdate) {
        this.lastUpdate = lastUpdate;
    }

    public User getLastUpdatedBy() {
        return lastUpdatedBy;
    }

    public void setLastUpdatedBy(User lastUpdatedBy) {
        this.lastUpdatedBy = lastUpdatedBy;
    }
    public void addUserToArray(User user){
        userArray.add(user);
    }
    public static ArrayList<User> getUserArray(){
        return userArray;
    }
}
