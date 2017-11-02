package model;

import java.sql.Connection;
import java.sql.DatabaseMetaData;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class Database {
    private static Connection connection;
    public static Statement statement;
    
    public static void startNewConnection() throws ClassNotFoundException{
        connection = null;
        String driver   = "com.mysql.jdbc.Driver";
        String db       = "U04RGT";
        String url      = "jdbc:mysql://52.206.157.109/" + db;
        String user     = "U04RGT";
        String pass     = "53688321456";
        try {
            Class.forName(driver);
            connection = DriverManager.getConnection(url,user,pass);
            System.out.println("Connected to database : " + db);
        } catch (SQLException e) { 
            System.out.println("SQLException: "+e.getMessage());
            System.out.println("SQLState: "+e.getSQLState());
            System.out.println("VendorError: "+e.getErrorCode());
        }
    }
    public static void createAppointmentTable(){
        /*
        CREATE TABLE APPOINTMENTS (
            appointmentId INT(10) PRIMARY KEY NOT NULL,
            customerId INT(10),
            title VARCHAR(255),
            description TEXT,
            location TEXT,
            contact TEXT,
            url VARCHAR(255),
            start DATETIME,
            end DATETIME,
            createDate DATETIME,
            createdBy VARCHAR(40),
            lastUpdate TIMESTAMP,
            lastUpdatedBy VARCHAR(40)
        )
        */
        
        
        
        String TableName = "APPOINTMENTS";
        try {
            statement = connection.createStatement();
            
            DatabaseMetaData dbm = connection.getMetaData();
            ResultSet listOfTables = dbm.getTables(null,null, TableName.toUpperCase(), null);
            
            if (listOfTables.next()) {
                System.out.println("Table " + TableName + " exists");
            }
            else {
                String query = "CREATE TABLE " + TableName + "("
                        + "appointmentId INT(10) NOT NULL PRIMARY KEY,\n"
                        + "customerId INT(10),\n"
                        + "title VARCHAR(255),\n"
                        + "description TEXT,\n"
                        + "location TEXT,\n"
                        + "contact TEXT,\n"
                        + "url VARCHAR(255),\n"
                        + "start DATETIME,\n"
                        + "end DATETIME,\n"
                        + "createDate DATETIME,\n"
                        + "createdBy VARCHAR(40),\n"
                        + "lastUpdate TIMESTAMP,\n"
                        + "lastUpdatedBy VARCHAR(40)"
                        + ")";
                statement.execute(query);
            }
        }
        catch (SQLException e) {
            System.err.println(e.getMessage() + "--- setupDatabase");
        }
    }
    public static void createCustomerTable(){
        /*
        CREATE TABLE APPOINTMENTS (
            customerId INT(10) PRIMARY KEY NOT NULL,
            customerName VARCHAR(45),
            addressId INT(10),
            active TINYINT(1),
            createDate DATETIME,
            createdBy VARCHAR(40),
            lastUpdate TIMESTAMP,
            lastUpdateBy VARCHAR(40)
        )           
        */
 
        String TableName = "CUSTOMERS";
        try {
            statement = connection.createStatement();
            
            DatabaseMetaData dbm = connection.getMetaData();
            ResultSet listOfTables = dbm.getTables(null,null, TableName.toUpperCase(), null);
            
            if (listOfTables.next()) {
                System.out.println("Table " + TableName + " exists");
            }
            else {
                String query = "CREATE TABLE " + TableName + "("
                        + "customerId INT(10) PRIMARY KEY NOT NULL,\n"
                        + "customerName VARCHAR(45),\n"
                        + "addressId INT(10),\n"
                        + "active TINYINT(1),\n"
                        + "createDate DATETIME,\n"
                        + "createdBy VARCHAR(40),\n"
                        + "lastUpdate TIMESTAMP,\n"
                        + "lastUpdateBy VARCHAR(40)"
                        + ")";
                statement.execute(query);
            }
        }
        catch (SQLException e) {
            System.err.println(e.getMessage() + "--- setupDatabase");
        }
    }
    public static void createUserTable(){
        /*
        CREATE TABLE USERS (
            userId INT(10) PRIMARY KEY NOT NULL,
            userName VARCHAR(50),
            password VARCHAR(50),
            active TINYINT(1),
            createdBy VARCHAR(40),
            createDate DATETIME,
            lastUpdate TIMESTAMP,
            lastUpdatedBy VARCHAR(50)
        )       
        */
 
        String TableName = "USERS";
        try {
            statement = connection.createStatement();
            
            DatabaseMetaData dbm = connection.getMetaData();
            ResultSet listOfTables = dbm.getTables(null,null, TableName.toUpperCase(), null);
            
            if (listOfTables.next()) {
                System.out.println("Table " + TableName + " exists");
            }
            else {
                String query = "CREATE TABLE " + TableName + "("
                    + "userId INT(10) PRIMARY KEY NOT NULL,\n"
                    + "userName VARCHAR(50),\n"
                    + "password VARCHAR(50),\n"
                    + "active TINYINT(1),\n"
                    + "createdBy VARCHAR(40),\n"
                    + "createDate DATETIME,\n"
                    + "lastUpdate TIMESTAMP,\n"
                    + "lastUpdatedBy VARCHAR(50)"
                    + ")";
                statement.execute(query);
            }
        }
        catch (SQLException e) {
            System.err.println(e.getMessage() + "--- setupDatabase");
        }
    }
    public static void createAddressTable(){
        /*
        CREATE TABLE ADDRESS (
            addressId INT(10),
            address VARCHAR(50),
            address2 VARCHAR(50),
            cityId INT(10),
            postalCode VARCHAR(10),
            phone VARCHAR(20),
            createDate DATETIME,
            createdBy VARCHAR(40),
            lastUpdate TIMESTAMP,
            lastUpdateBy VARCHAR(40)
        )       
        */
 
        String TableName = "ADDRESS";
        try {
            statement = connection.createStatement();
            
            DatabaseMetaData dbm = connection.getMetaData();
            ResultSet listOfTables = dbm.getTables(null,null, TableName.toUpperCase(), null);
            
            if (listOfTables.next()) {
                System.out.println("Table " + TableName + " exists");
            }
            else {
                String query = "CREATE TABLE " + TableName + "("
                    + "addressId INT(10),\n"
                    + "address VARCHAR(50),\n"
                    + "address2 VARCHAR(50),\n"
                    + "cityId INT(10),\n"
                    + "postalCode VARCHAR(10),\n"
                    + "phone VARCHAR(20),\n"
                    + "createDate DATETIME,\n"
                    + "createdBy VARCHAR(40),\n"
                    + "lastUpdate TIMESTAMP,\n"
                    + "lastUpdateBy VARCHAR(40)"
                    + ")";
                statement.execute(query);
            }
        }
        catch (SQLException e) {
            System.err.println(e.getMessage() + "--- setupDatabase");
        }
    }
    public static void createCityTable(){
        /*
        CREATE TABLE USERS (
            userId INT(10) PRIMARY KEY NOT NULL,
            userName VARCHAR(50),
            password VARCHAR(50),
            active TINYINT(1),
            createdBy VARCHAR(40),
            createDate DATETIME,
            lastUpdate TIMESTAMP,
            lastUpdatedBy VARCHAR(50)
        )       
        */
 
        String TableName = "USERS";
        try {
            statement = connection.createStatement();
            
            DatabaseMetaData dbm = connection.getMetaData();
            ResultSet listOfTables = dbm.getTables(null,null, TableName.toUpperCase(), null);
            
            if (listOfTables.next()) {
                System.out.println("Table " + TableName + " exists");
            }
            else {
                String query = "CREATE TABLE " + TableName + "("
                    + "userId INT(10) PRIMARY KEY NOT NULL,\n"
                    + "userName VARCHAR(50),\n"
                    + "password VARCHAR(50),\n"
                    + "active TINYINT(1),\n"
                    + "createdBy VARCHAR(40),\n"
                    + "createDate DATETIME,\n"
                    + "lastUpdate TIMESTAMP,\n"
                    + "lastUpdatedBy VARCHAR(50)"
                    + ")";
                statement.execute(query);
            }
        }
        catch (SQLException e) {
            System.err.println(e.getMessage() + "--- setupDatabase");
        }
    }
    public static void createCountryTable(){
        /*
        CREATE TABLE USERS (
            userId INT(10) PRIMARY KEY NOT NULL,
            userName VARCHAR(50),
            password VARCHAR(50),
            active TINYINT(1),
            createdBy VARCHAR(40),
            createDate DATETIME,
            lastUpdate TIMESTAMP,
            lastUpdatedBy VARCHAR(50)
        )       
        */
 
        String TableName = "USERS";
        try {
            statement = connection.createStatement();
            
            DatabaseMetaData dbm = connection.getMetaData();
            ResultSet listOfTables = dbm.getTables(null,null, TableName.toUpperCase(), null);
            
            if (listOfTables.next()) {
                System.out.println("Table " + TableName + " exists");
            }
            else {
                String query = "CREATE TABLE " + TableName + "("
                    + "userId INT(10) PRIMARY KEY NOT NULL,\n"
                    + "userName VARCHAR(50),\n"
                    + "password VARCHAR(50),\n"
                    + "active TINYINT(1),\n"
                    + "createdBy VARCHAR(40),\n"
                    + "createDate DATETIME,\n"
                    + "lastUpdate TIMESTAMP,\n"
                    + "lastUpdatedBy VARCHAR(50)"
                    + ")";
                statement.execute(query);
            }
        }
        catch (SQLException e) {
            System.err.println(e.getMessage() + "--- setupDatabase");
        }
    }
    public static void actionQuery(String query) throws SQLException{
        statement = connection.createStatement();
        statement.execute(query);
    }
    public static void printQuery(String query) throws SQLException{
        ResultSet results;
        statement = connection.createStatement();
        results = statement.executeQuery(query);
        while (results.next()){
            System.out.println(
                "appointmentId: " + results.getString("appointmentId")
                + " | title: " + results.getString("title")
                + " | time: " + results.getString("start")
            );
        }
        System.out.println(results);
    }
    public static void closeConnection(Connection connection) {
       try {
         if (connection != null)
           connection.close();
       } catch (SQLException e) { }
    }
    public static ResultSet resultQuery(String query) throws SQLException{
        ResultSet results;
        statement = connection.createStatement();
        results = statement.executeQuery(query);
        
        return results;
    }
    
    
}
