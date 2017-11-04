package model;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class Database {
    private static Connection connection;
    public static Statement statement;
    
    public static void startNewConnection() throws ClassNotFoundException{
        
        connection = null;
        String driver       = "com.mysql.jdbc.Driver";
        String db           = "U04RGT";
        String url          = "jdbc:mysql://52.206.157.109/" + db;
        String username     = "U04RGT";
        String password     = "53688321456";
        
        try {
            Class.forName(driver);
            connection = DriverManager.getConnection(url,username,password);
        } catch (SQLException e) { 
            System.out.println("Exception: " + e.getMessage());
            System.out.println("State: " + e.getSQLState());
            System.out.println("Error: " + e.getErrorCode());
        }
    }
    public static void closeConnection(Connection connection) {
       try {
         if (connection != null)
           connection.close();
       } catch (SQLException e) { }
    }
    public static void actionQuery(String query) throws SQLException{
        statement = connection.createStatement();
        statement.execute(query);
    }
    public static ResultSet resultQuery(String query) throws SQLException{
        ResultSet results;
        statement = connection.createStatement();
        results = statement.executeQuery(query);
        
        return results;
    }
}
