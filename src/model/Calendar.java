package model;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Collections;

public class Calendar {
    private ArrayList<Appointment> appointmentArray = new ArrayList<>();
    
    
    
    public void addAppointment(Appointment appointment){
        appointmentArray.add(appointment);
    }
    public ArrayList<Appointment> getAppointments(){
        return appointmentArray;
    }
    public void sortAppointments(){
        Collections.sort(appointmentArray, 
                        (appt1, appt2) -> appt1.getStart().compareTo(appt2.getStart()));
    }
    public static void populateFromDB() throws SQLException{
        String query = "SELECT * FROM APPOINTMENTS";
        ResultSet results = Database.resultQuery(query);
        
        while(results.next()){
            int appointmentId = results.getInt("appointmentId");
            System.out.println(appointmentId);
            String timeString = results.getString("start");
            System.out.println(timeString);
            LocalDateTime start = LocalDateTime.parse(timeString, DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss.n"));
            String title = results.getString("title");
            Customer customer = null;

            Appointment appt = new Appointment(appointmentId, start, title, customer);
            
            MainSchedulingApp.calendarArray.addAppointment(appt);
            
        }
        
                
    }
}
