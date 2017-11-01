package model;

import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.DayOfWeek;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.Locale;
import java.util.Map;
import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.stage.Modality;
import javafx.stage.Stage;


public class MainSchedulingApp extends Application {
    /* Variables */
    //private static Connection connection;
    public static Calendar calendarArray;
    public static LocalDate currentViewDate = LocalDate.now();
    
    //TEST DATA
    
    static Appointment  appt1;
    static Appointment  appt2;
    static Appointment  appt3;
    
    
    
    @Override
    public void start(Stage stage) throws Exception {
        Parent root = FXMLLoader.load(getClass().getResource("/view/LoginView.fxml"));
        Scene scene = new Scene(root);
        stage.setScene(scene);
        stage.setTitle("Login");
        stage.show();
    }
    public static void main(String[] args) throws ClassNotFoundException, SQLException {
        Database.startNewConnection();
        Database.createAppointmentTable();
        populateTestCalendar();

        
        String insertQuery = "INSERT INTO APPOINTMENTS (appointmentId) VALUES (500)";
        //Database.actionQuery(insertQuery);
        String printQuery = "SELECT * FROM APPOINTMENTS";
        Database.printQuery(printQuery);
        
        appt1.setType("MORE TEST");
        Database.printQuery(printQuery);

        
        setFrenchLocale();
        //setEnglishLocale();
        createTestUsers();
        launch(args);
        
    }
    /* JavaFX Methods */
    public static Stage getStage(ActionEvent event){
        return (Stage) ((Node) event.getSource()).getScene().getWindow();
    }
    public static void changeScene(ActionEvent event, String location, String title) throws IOException{
         
        FXMLLoader loader = new FXMLLoader();
        loader.setLocation(MainSchedulingApp.class.getResource(location));
        Parent root = (Parent) loader.load(); //sets parent to loader
        Scene scene = new Scene(root); //creates new scene   
        Stage stage = getStage(event);
        stage.setTitle(title); //sets title
        stage.setScene(scene); //sets scene on stage
        stage.show();
    }
    public static void popUpScene(ActionEvent event, String location, String title) throws IOException{
        Stage popUp = new Stage();
        popUp.initModality(Modality.APPLICATION_MODAL);
        popUp.initOwner(getStage(event));
        
        FXMLLoader loader = new FXMLLoader();
        loader.setLocation(MainSchedulingApp.class.getResource(location));
        Parent root = (Parent) loader.load(); //sets parent to loader
        Scene scene = new Scene(root); //creates new scene 
        
        popUp.setTitle(title); //sets title
        popUp.setScene(scene); //sets scene on stage
        popUp.show(); 
    }
    /* SQL Methods */
    /*public static void startNewConnection() throws ClassNotFoundException{
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
    public static void createNewTable(){
        
    }
    public static ResultSet createNewQuery(String query){    
        ResultSet resultSet = null;
        try {
            PreparedStatement statement = connection.prepareStatement("");
            resultSet = statement.executeQuery(query);
          
            printQueryResult(connection, resultSet);
            return resultSet;
        } catch (SQLException e) { 
            System.out.println("SQLException: "+e.getMessage());
            System.out.println("SQLState: "+e.getSQLState());
            System.out.println("VendorError: "+e.getErrorCode());
        }
        return resultSet;
    }
    public static void printQueryResult(Connection connection, ResultSet resultSet) throws SQLException{
        Map<Integer, String> idToNameMap = new HashMap<>();
        
        try{
            while(resultSet.next()) {
                int id = resultSet.getInt("id");
                String name = resultSet.getString("name");
                idToNameMap.put(id, name);
            }
        } finally {
            closeConnection(connection);
        }
        System.out.println(idToNameMap); // {1=African Elephant, 2=Zebra}
    }
    private static void closeConnection(Connection connection) {
       try {
         if (connection != null)
           connection.close();
       } catch (SQLException e) { }
    }*/
    public static LocalDate getToday(){
        LocalDate today = LocalDate.now();
        //LocalDate weekStart = today.minusDays(today.getDayOfWeek().getValue() - 1);
        //LocalDate weekEnd = weekStart.plusDays(6);
        //today.get
       return today;
    }
    public static int getFirstDay(LocalDate date){
        LocalDate firstDay = date.minusDays(date.getDayOfMonth()-1);
        int firstDayInt = firstDay.getDayOfWeek().getValue();
            if (firstDayInt == 7){
                firstDayInt = 0;
            }
        return firstDayInt;
    }
    public static void populateTestCalendar() throws SQLException{
        calendarArray = new Calendar();
        
         appt1 = new Appointment(LocalDateTime.now().plusDays(1),"TEST Event",null);
         appt2 = new Appointment(LocalDateTime.now().plusDays(3),"event 3",null);
         appt3 = new Appointment(LocalDateTime.now().plusDays(5),"event 39",null);
        
        calendarArray.addAppointment(appt1);
        calendarArray.addAppointment(appt2);
        calendarArray.addAppointment(appt3);
    }
    public static void setFrenchLocale(){
        Locale.setDefault(Locale.FRENCH);
    }
    public static void setEnglishLocale(){
        Locale.setDefault(Locale.ENGLISH);
    }
    public static void createTestUsers(){
        User testUser = new User("test","test",true,null,null,null,null);
        System.out.println(testUser.getUserName());

    }
    
    /*public static checkExistence(){
        DatabaseMetaData dbm = con.getMetaData();
        // check if "employee" table is there
        ResultSet tables = dbm.getTables(null, null, "employee", null);
        if (tables.next()) {
          // Table exists
        }
        else {
          // Table does not exist
        }
    }*/
}
