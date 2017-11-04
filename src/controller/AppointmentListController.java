package controller;

import java.io.IOException;
import java.net.URL;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.VBox;
import javafx.stage.Modality;
import javafx.stage.Stage;
import model.Appointment;
import model.Database;
import model.MainSchedulingApp;

public class AppointmentListController implements Initializable {

    @FXML
    private VBox apptList;
    @FXML
    private Label mainLabel;
    
    MainSchedulingApp main;
    LocalDateTime sendDateSave = null;
    
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        
    }
    //List appointments for selected day
    public void listAppt(LocalDateTime sendDate){        
        
        //Save sendDate for reference
        sendDateSave = sendDate;
        
        ArrayList<Appointment> appointmentToday = new ArrayList<>();
        
        apptList.getChildren().clear();
        Appointment.sortAppointments();
        
        //get today's appointments
        for (Appointment apptToday: Appointment.getAppointments()){
            if (apptToday.getStart().toLocalDate().equals(sendDate.toLocalDate())){
                appointmentToday.add(apptToday);
            }
        }
        
        //create labels
        for (Appointment appointments: appointmentToday){
            
            String labelText = (appointments.getStart().toLocalTime().format(DateTimeFormatter.ofPattern("hh:mm a"))
                    + " - " + appointments.getTitle());
                
            Label apptLabel = new Label(labelText);
            apptLabel.getStyleClass().add("eventLbl");
            apptLabel.setMaxWidth(Double.MAX_VALUE);

            apptList.getChildren().add(apptLabel);

            apptLabel.addEventHandler(MouseEvent.MOUSE_PRESSED, (e)->{
                try {
                    editEvent(appointments);
                } catch (IOException ex) {
                    Logger.getLogger(AppointmentListController.class.getName()).log(Level.SEVERE, null, ex);
                } catch (SQLException ex) {
                    Logger.getLogger(AppointmentListController.class.getName()).log(Level.SEVERE, null, ex);
                }
            });
        }
    }
    public void editEvent(Appointment appointments) throws IOException, SQLException{ 
        Stage stage = new Stage();
        stage.initModality(Modality.APPLICATION_MODAL);
        stage.initOwner(null);
        Scene main;
       
        FXMLLoader loader = new FXMLLoader();       
        loader.setLocation(MainSchedulingApp.class.getResource("/view/AppointmentView.fxml"));
        Parent root = (Parent) loader.load();
        main = new Scene(root);
        AppointmentViewController controller = loader.getController();
        controller.editAppt(appointments);
        stage.setScene(main);
        stage.showAndWait();  
        
        listAppt(sendDateSave);
    }
    /*Reports*/
    public void reportTypeByMonth() throws SQLException{
        
        mainLabel.setText("Appt Type by Month");
         
        String query = 
        "SELECT MONTHNAME(start) AS month, title AS title, COUNT(title) AS count "
        + "FROM appointment "
        + "GROUP BY MONTHNAME(start), title";
        
        ResultSet results = Database.resultQuery(query);

        while (results.next()){
            String labelText = 
                "Month: " + results.getString("month")
                + "  |  Title: " + results.getString("title")
                + "  | Count: " + results.getString("count");
            
            Label apptLabel = new Label(labelText);
            apptLabel.setMaxWidth(Double.MAX_VALUE);
            
            apptList.getChildren().add(apptLabel);
        }
 
    }
    public void totalCountByDay() throws SQLException{
        
        mainLabel.setText("Appt Count by Day");
        
        String query = 
        "SELECT DATE(start) AS date, COUNT(appointmentId) AS count "
        + "FROM appointment "
        + "GROUP BY DATE(start)";
        
        ResultSet results = Database.resultQuery(query);
        
        while (results.next()){
            String labelText = 
                "Date: " + results.getString("date")
                + "  | Count: " + results.getString("count");
            
            Label apptLabel = new Label(labelText);
            apptLabel.setMaxWidth(Double.MAX_VALUE);
            
            apptList.getChildren().add(apptLabel);
        }
    }
    public void reportConsultantSchedule() throws SQLException{
         mainLabel.setText("Consultant Schedules");

        ResultSet results;
        
        String query = 
        "SELECT createdBy AS consultant, start AS date "
        + "FROM appointment "
        + "ORDER BY createdBy, start";
 
        
        results = Database.resultQuery(query);
        
        while (results.next()){
            String labelText = 
                "Consultant: " + results.getString("consultant")
                + "  |  Date: " + results.getString("date");

            Label apptLabel = new Label(labelText);
            //apptLabel.getStyleClass().add("eventLbl");
            apptLabel.setMaxWidth(Double.MAX_VALUE);
            apptLabel.setMaxHeight(Double.MAX_VALUE);
            
            apptList.getChildren().add(apptLabel);
        }
    }  
}
