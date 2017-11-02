/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controller;

import java.net.URL;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Collections;
import java.util.ResourceBundle;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
import javafx.scene.layout.VBox;
import model.Appointment;
import model.Database;
import model.MainSchedulingApp;

/**
 * FXML Controller class
 *
 * @author owner
 */
public class AppointmentListController implements Initializable {

    @FXML
    private VBox apptList;
    @FXML
    private Label mainLabel;
    
    MainSchedulingApp main;
    
    
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
        
    }    
    
    
    public void listAppt(LocalDateTime sendDate){
        System.out.println(sendDate.toString());
        
        ArrayList<Appointment> appointmentToday = new ArrayList<>();
        main.calendarArray.sortAppointments();
        for (Appointment appointment:main.calendarArray.getAppointments()){
            if (appointment.getStart().toLocalDate().equals(sendDate.toLocalDate())){
                
                appointmentToday.add(appointment);
            }
        } 
        /*Collections.sort(appointmentToday, 
                        (appt1, appt2) -> appt1.getTime().compareTo(appt2.getTime()));*/
            
        for (Appointment appointments: appointmentToday){    
            
            String labelText = (appointments.getStart().toLocalTime().format(DateTimeFormatter.ofPattern("hh:mm a"))
                    + " - " + appointments.getTitle());
                
                Label apptLabel = new Label(labelText);
                apptLabel.getStyleClass().add("eventLbl");
                apptLabel.setMaxWidth(Double.MAX_VALUE);


                apptList.getChildren().add(apptLabel);

        }
    }
    public void reportTypeByMonth() throws SQLException{
        mainLabel.setText("Appt Type by Month");
        
        ResultSet results;
        
        String query = 
        "SELECT MONTHNAME(start) AS month, title AS title, COUNT(title) AS count "
        + "FROM appointment "
        + "GROUP BY MONTHNAME(start), title";
        
        results = Database.resultQuery(query);

        while (results.next()){
            String labelText = 
                "Month: " + results.getString("month")
                + "  |  Title: " + results.getString("title")
                + "  | Count: " + results.getString("count");
            Label apptLabel = new Label(labelText);
            //apptLabel.getStyleClass().add("eventLbl");
            apptLabel.setMaxWidth(Double.MAX_VALUE);
            apptLabel.setMaxHeight(Double.MAX_VALUE);
            
            apptList.getChildren().add(apptLabel);
        }
 
    }
    public void totalCountByDay() throws SQLException{
        mainLabel.setText("Appt Count by Day");

        ResultSet results;
        
        String query = 
        "SELECT DATE(start) AS date, COUNT(appointmentId) AS count "
        + "FROM appointment "
        + "GROUP BY DATE(start)";
        
        results = Database.resultQuery(query);
        
        while (results.next()){
            String labelText = 
                "Date: " + results.getString("date")
                + "  | Count: " + results.getString("count");
            Label apptLabel = new Label(labelText);
            //apptLabel.getStyleClass().add("eventLbl");
            apptLabel.setMaxWidth(Double.MAX_VALUE);
            apptLabel.setMaxHeight(Double.MAX_VALUE);
            
            apptList.getChildren().add(apptLabel);
        }
    }
}
