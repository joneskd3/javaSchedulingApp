package model;

import java.util.ArrayList;

public class Calendar {
    private ArrayList<Appointment> appointmentArray = new ArrayList<>();
    
    
    public void addAppointment(Appointment appointment){
        appointmentArray.add(appointment);
    }
    public ArrayList<Appointment> getAppointments(){
        return appointmentArray;
    }
    
    
    public void createCalendar(){

    }
}
