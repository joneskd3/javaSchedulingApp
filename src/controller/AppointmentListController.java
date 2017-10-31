/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controller;

import java.net.URL;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Collections;
import java.util.ResourceBundle;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import javafx.scene.layout.VBox;
import model.Appointment;
import model.MainSchedulingApp;

/**
 * FXML Controller class
 *
 * @author owner
 */
public class AppointmentListController implements Initializable {

    @FXML
    private VBox apptList;
    
    
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
            if (appointment.getTime().toLocalDate().equals(sendDate.toLocalDate())){
                
                appointmentToday.add(appointment);
            }
        } 
        /*Collections.sort(appointmentToday, 
                        (appt1, appt2) -> appt1.getTime().compareTo(appt2.getTime()));*/
            
        for (Appointment appointments: appointmentToday){    
            
            String labelText = (appointments.getTime().toLocalTime().format(DateTimeFormatter.ofPattern("hh:mm a"))
                    + " - " + appointments.getType());
                
                Label apptLabel = new Label(labelText);
                apptLabel.getStyleClass().add("eventLbl");
                apptLabel.setMaxWidth(Double.MAX_VALUE);


                apptList.getChildren().add(apptLabel);

        }
    }
}
