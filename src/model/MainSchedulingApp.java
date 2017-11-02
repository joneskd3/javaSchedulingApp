package model;

import controller.CalendarMonthViewController;
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
    public static Calendar calendarArray;
    public static LocalDate currentViewDate = LocalDate.now();
        
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
       
        calendarArray = new Calendar();
        
        Calendar.populateFromDB();
        
        //setFrenchLocale();
        setEnglishLocale();
  
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
        popUp.showAndWait();
    }
    public static void setFrenchLocale(){
        Locale.setDefault(Locale.FRENCH);
    }
    public static void setEnglishLocale(){
        Locale.setDefault(Locale.ENGLISH);
    }
    /*Test Methods*/
    public static void createTestAppointments() throws SQLException{
        Appointment appt1 = new Appointment(LocalDateTime.now().plusDays(1),"TEST Event",null);
        Appointment appt2 = new Appointment(LocalDateTime.now().plusDays(3),"event 3",null);
        Appointment appt3 = new Appointment(LocalDateTime.now().plusDays(5),"event 39",null);

        calendarArray.addAppointment(appt1);
        calendarArray.addAppointment(appt2);
        calendarArray.addAppointment(appt3);
    }
    public static void createTestUsers() throws SQLException{
        User testUser = new User("test","test",true,null,null,null,null);
        System.out.println(testUser.getUserName());

    }
    public static void createTestCustomers() throws SQLException{
        Customer testCustomer = new Customer("Joe Johnson","Addr 1","addr 2","city","393929","333239392");
        System.out.println(testCustomer.getCustomerName());

    }
}
