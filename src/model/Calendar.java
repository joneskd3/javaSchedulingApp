package model;

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
                        (appt1, appt2) -> appt1.getTime().compareTo(appt2.getTime()));
    }
}
