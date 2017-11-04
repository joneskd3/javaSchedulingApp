package model;

import controller.LoginViewController;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDateTime;
import java.util.ArrayList;

public class User {
    
    /*Static Variables*/
    private static int userIdCounter = 0;
    public static ArrayList<User> userArray = new ArrayList<>();
    /*Instance Variables*/
    private int userId;
    private String userName;
    private String password;
    private boolean active;
    private User createdBy;
    private LocalDateTime createdDate;
    private LocalDateTime lastUpdate;
    private User lastUpdatedBy;

    /*Constructor*/
    public User() throws SQLException{
        this("","",true);
    }
    public User(String userName, String password, boolean active) throws SQLException {
        getMaxUserId();
        
        this.userId = userIdCounter + 1;
        this.userName = userName;
        this.password = password;
        this.active = active;

        this.addToDB();
    }
    public User(int userId, String userName, String password, boolean active) throws SQLException {
        
        this.userId = userId;
        this.userName = userName;
        this.password = password;
        this.active = active;
    }
    /*User ID*/
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
            if (id == null){
                userIdCounter = -1;
            } else {
                userIdCounter = Integer.parseInt(id);
            }
        }
    }
    /*Username*/
    public String getUserName() {
        return userName;
    }
    public void setUserName(String userName) throws SQLException {
        this.userName = userName;
        this.updateDB();
    }
    /*Password*/
    public String getPassword() {
        return password;
    }
    public void setPassword(String password) throws SQLException {
        this.password = password;
        this.updateDB();
    }
    /*Active*/
    public boolean isActive() {
        return active;
    }
    public void setActive(boolean active) {
        this.active = active;
    }
    /*Array Methods*/
    public void addUserToArray(User user){
        userArray.add(user);
    }
    public static ArrayList<User> getUserArray(){
        return userArray;
    }
    /*DB Methods*/
    public void addToDB() throws SQLException{
                 
        String insert = 
                "INSERT INTO user (userId, userName, password, active, createBy, createDate, lastUpdatedBy) "
                + "VALUES ("
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
            "UPDATE user "
            + "SET "
                + "userName = '" + this.getUserName()+ "', "
                + "password = '" + this.getPassword() + "'"
            + "WHERE "
                + "userId = " + this.getUserId();
        
        Database.actionQuery(update);
    }
    public static void populateFromDB() throws SQLException{
        userArray.clear();

        String userQuery = 
                "SELECT "
                    + "userId, "
                    + "userName, "
                    + "password, "
                    + "active "
                + "FROM user ";
        
        ResultSet results = Database.resultQuery(userQuery);
        
        Boolean active;
        while(results.next()){ 
            int userId = results.getInt("userId"); 
            String userName = results.getString("userName");
            String password = results.getString("password");
            int activeInt = results.getInt("active");
            if (activeInt == 0){
                active = true;
            } else {
                active = false;
            }
            
            User user = new User(userId, userName, password, active);
            
            userArray.add(user);
        }
    }
}
