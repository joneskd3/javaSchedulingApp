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
                System.out.println("Table " + TableName + " already exists. Ready to go!");
            }
            else {
                String query = "CREATE TABLE " + TableName + "("
                        + "appointmentId INT(10) PRIMARY KEY,\n"
                        + "customerId INT(10),\n"
                        + "title VARCHAR(255)"
                        //+ "description TEXT,\n"
                        //+ "location TEXT,\n"
                        + ")";
                statement.execute(query);
            }
        }
        catch (SQLException e) {
            System.err.println(e.getMessage() + "--- setupDatabase");
        } finally {
        }
    }
    public static void insertValues(String table, String insertStatement) throws SQLException{
        statement = connection.createStatement();
        statement.execute(insertStatement);
    }
    public static void printQuery() throws SQLException{
        ResultSet results;
        String query = "SELECT * FROM APPOINTMENTS";
        statement = connection.createStatement();
        results = statement.executeQuery(query);
        while (results.next()){
            System.out.println("Results: " + results.getString("appointmentId"));
        }
        System.out.println(results);
    }
    private static void closeConnection(Connection connection) {
       try {
         if (connection != null)
           connection.close();
       } catch (SQLException e) { }
    }
}
