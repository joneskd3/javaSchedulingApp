/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package model;

import controller.LoginViewController;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
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

    
    public User() throws SQLException{
        this("","",false,null,null,null,null);
    }
    
    public User(String userName, String password, boolean active, User createdBy, LocalDateTime createdDate, LocalDateTime lastUpdate, User lastUpdatedBy) throws SQLException {
        getMaxUserId();
        this.userId = userIdCounter + 1;
        this.userName = userName;
        this.password = password;
        this.active = active;
        this.createdBy = createdBy;
        this.createdDate = createdDate;
        this.lastUpdate = lastUpdate;
        this.lastUpdatedBy = lastUpdatedBy;
        addUserToArray(this);
        this.addToDB();
    }
    

    public int getUserId() {
        return userId;
        
    }

    public void setUserId(int userId) throws SQLException {
        this.userId = userId;
        this.updateDB();
    }
    public static void getMaxUserId() throws SQLException{
        
        String maxQuery = "SELECT MAX(userId) AS userId FROM user";
        ResultSet results = Database.resultQuery(maxQuery);
        while(results.next()){
            //convert to string first to check for null
            String id = results.getString("userId");
            System.out.println("id test: " + id);
            if (id == null){
                userIdCounter = -1;
            } else {
                userIdCounter = Integer.parseInt(id);
            }
        }
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) throws SQLException {
        this.userName = userName;
        this.updateDB();
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) throws SQLException {
        this.password = password;
        this.updateDB();
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
    public void addToDB() throws SQLException{
        /*+ "userId INT(10) PRIMARY KEY NOT NULL,\n"
                    + "userName VARCHAR(50),\n"
                    + "password VARCHAR(50),\n"
                    + "active TINYINT(1),\n"
                    + "createdBy VARCHAR(40),\n"
                    + "createDate DATETIME,\n"
                    + "lastUpdate TIMESTAMP,\n"
                    + "lastUpdatedBy VARCHAR(50)"
           */
                 
        String insert = "INSERT INTO user (userId, userName, password, active, createBy, createDate, lastUpdatedBy)"
                + " VALUES ("
                + this.getUserId() + ", "
                + "'" + this.getUserName() + "', "
                + "'" + this.getPassword() + "', "
                + "1, "
                + "'" + LoginViewController.currentUser + "', "
                + "CURRENT_TIMESTAMP, "
                + "'" + LoginViewController.currentUser + "' "

                + ")";
        
        Database.actionQuery(insert);
    }
    public void updateDB() throws SQLException{
              
        String update =
            "UPDATE user \n"
            + "SET "
                + "userName = '" + this.getUserName()+ "', "
                + "password = '" + this.getPassword() + "'"
            + "WHERE "
                + "userId = " + this.getUserId();
        System.out.println(
            "===="
            + update
            + "===="
        );
        Database.actionQuery(update);
    }
}
