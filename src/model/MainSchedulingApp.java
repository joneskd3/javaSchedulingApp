package model;

import java.io.IOException;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Modality;
import javafx.stage.Stage;

public class MainSchedulingApp extends Application {
    /* Variables */
    public static LocalDate currentViewDate = LocalDate.now();
    public static Stage mainStage;
    public static LocalTime businessHoursStart = LocalTime.of (8, 0);
    public static LocalTime businessHoursEnd = LocalTime.of (20, 0);
    
    @Override
    public void start(Stage stage) throws Exception {
        //save stage into static variable for later reference
        mainStage = stage;

        Parent root = FXMLLoader.load(getClass().getResource("/view/LoginView.fxml"));
        Scene scene = new Scene(root);
        stage.setScene(scene);
        stage.show();
    }
    public static void main(String[] args) throws ClassNotFoundException, SQLException {
        //open DB connection
        Database.startNewConnection();
        
        //populate arrays from DB
        User.populateFromDB();
        Customer.populateFromDB();
        Appointment.populateFromDB();
        
        //Create test user 
        createTestUsers();
        
        //Test methods for Local
        //Locale.setDefault(new Locale("fr", "FR")); //French
        //Locale.setDefault(new Locale("en", "EN")); //English
        //Locale.setDefault(new Locale("it", "IT")); //Italian

        launch(args);
    }
    /* JavaFX Methods */
    //returns current stage
    public static Stage getStage(ActionEvent event){
        return (Stage) ((Node) event.getSource()).getScene().getWindow();
    }
    public static void changeScene(ActionEvent event, String fxmlLocation, String title) throws IOException{
        FXMLLoader loader = new FXMLLoader();
        loader.setLocation(MainSchedulingApp.class.getResource(fxmlLocation));
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
    /*Test Methods*/
    public static void createTestAppointments() throws SQLException{
        Appointment appt1 = new Appointment(LocalDateTime.now().plusDays(1),"TEST Event",2);
        Appointment appt2 = new Appointment(LocalDateTime.now().plusDays(3),"event 3",3);
        Appointment appt3 = new Appointment(LocalDateTime.now().plusDays(5),"event 39",4);
    }
    public static void createTestUsers() throws SQLException{
        String query = "SELECT COUNT(*) AS count FROM user";
        ResultSet results = Database.resultQuery(query);
        //creates test users only if table is empty
        while(results.next()){
            if(results.getInt("count") == 0){
                User testUser = new User("test","test",true);
                User testUser2 = new User("pw","pw",true);
            }
        }
    }
    public static void createTestCustomers() throws SQLException{
        Customer testCustomer = new Customer("Joe Johnson","Addr 1","addr 2","city","393929","333239392");
    }
    
}
