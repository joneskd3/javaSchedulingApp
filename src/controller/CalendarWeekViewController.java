/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controller;

import java.io.IOException;
import java.net.URL;
import java.time.LocalDate;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.event.ActionEvent;
import javafx.fxml.Initializable;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.RowConstraints;
import javafx.scene.layout.VBox;
import model.Appointment;
import model.MainSchedulingApp;

public class CalendarWeekViewController implements Initializable{

    /* FXML Variables */
    @FXML
    private Label monthYearLabel;
    @FXML
    private Button weekBtn;
    @FXML
    private Button monthBtn;
    @FXML
    private GridPane calendarGrid;
    @FXML
    private GridPane dateHeaderGrid;
    @FXML
    private RowConstraints dayOfWeek;

    /* Other Variables */
    ArrayList<VBox> vBoxArray= new ArrayList<>();
    ArrayList<VBox> dateVBoxArray = new ArrayList<>();
    MainSchedulingApp main;
    
    /* Action Handler Methods */
    @FXML
    void addAppointmentBtn(ActionEvent event) throws IOException {
       MainSchedulingApp.popUpScene(event,"/view/AppointmentView.fxml","Appointment");
    }
    @FXML
    void nextMonthBtn(ActionEvent event) {
        clearCalendar();
        main.currentViewDate = main.currentViewDate.plusDays(7);
        populateDates();
        populateTimes();
    }
    @FXML
    void previousMonthBtn(ActionEvent event) {
        clearCalendar();
        main.currentViewDate = main.currentViewDate.minusDays(7);
        populateDates();
        populateTimes();
    }
    @FXML
    void handleMonthBtn(ActionEvent event) throws IOException {
        MainSchedulingApp.changeScene(event, "/view/CalendarMonthView.fxml", "Calendar - Month View");

    }
    @FXML

    /* Other Methods */
    public void addVBox(){
        LocalDate date = main.currentViewDate;

        LocalTime time = LocalTime.parse("00:00");
        
        //number of rows and columns
        int numRows = 48;
        int numCols = 8;
        
        //start at 1 to account for day of week row
        for (int i = 1; i < numRows + 1; i++){
            for (int j = 0; j < numCols; j++){
                
                //creates box and applies "day" style for .css reference
                VBox timeSlot = new VBox();
                timeSlot.getStyleClass().add("timeSlot");
                
                
                
                //adds VBox to array for later access
                vBoxArray.add(timeSlot);
                
                
                calendarGrid.add(timeSlot,j,i);
                if(j == 0){
                    timeSlot.getChildren().add(new Label(time.toString()));
                    time = time.plusMinutes(30);
                

                }
                
            }
        }
    }
    public void addVBoxDates(){
        
        LocalDate date = main.currentViewDate;

        int numCols = 8;
        for (int i = 0; i < numCols; i++){
            VBox timeSlot = new VBox();
            timeSlot.getStyleClass().add("dateBox");
            
            dateVBoxArray.add(timeSlot);
            dateHeaderGrid.add(timeSlot,i,1);
            /*timeSlot.getChildren().add(new Label(date.toString()));
            date = date.plusDays(1);
            */
        }
    }
    public void populateDates(){
        formatHeader();
        int numCols = 8;
        
        LocalDate firstDayOfWeek = firstDayOfWeek();
        
        for (int i = 1; i < numCols; i++){
            Label dateLabel = new Label(firstDayOfWeek.toString());
            VBox curDate = dateVBoxArray.get(i);
            curDate.getChildren().add(dateLabel);
            firstDayOfWeek = firstDayOfWeek.plusDays(1);
        }
    }
    public void populateTimes(){
        LocalTime time = LocalTime.parse("00:00");

        int numRows = 48;
        int numCols = 8;
        
        for(int i = 0; i < vBoxArray.size(); i+=numCols){
            Label timeLabel = new Label(time.toString());
            time = time.plusMinutes(30);
            vBoxArray.get(i).getChildren().add(timeLabel);
        }
    }
    public void clearCalendar(){
        for (VBox vBox: vBoxArray){
            vBox.getChildren().clear();
        }
        for (VBox vBox: dateVBoxArray){
            vBox.getChildren().clear();
        }
    }
    public void formatHeader(){
        LocalDate date = main.currentViewDate;
        String month = date.getMonth().toString();
        String year = String.valueOf(date.getYear());
        monthYearLabel.setText(month + " " + year);
    }
    public void populateEvents(){
        ArrayList<Appointment> calendar = main.calendarArray.getAppointments();
        
        
        
        for(Appointment appointment : calendar ){
            LocalDate date = appointment.getTime().toLocalDate();
            if (date.isAfter(firstDayOfWeek()) && date.isBefore(firstDayOfWeek().plusDays(7))){
                System.out.println(date.compareTo(firstDayOfWeek()));
                int horizontal = date.compareTo(firstDayOfWeek())+1;
                Label eventlbl = new Label(appointment.getType());
                int vertical = (appointment.getTime().getHour()*2) + (appointment.getTime().getMinute()/30);
                System.out.println(vertical);
                int vHPosition = horizontal + (vertical*8);
                System.out.println(vHPosition);
                vBoxArray.get(vHPosition).getChildren().add(eventlbl);
                
            }  
        }
    }
    public LocalDate firstDayOfWeek(){
        
        LocalDate date = main.currentViewDate;
        int curDayOfWeek = date.getDayOfWeek().getValue();
        if(curDayOfWeek == 7){
            curDayOfWeek = 0;
        }
        LocalDate firstDayOfWeek = date.minusDays(curDayOfWeek);
        return firstDayOfWeek;
    }
    
    /* INITIALIZE */
    @Override
    public void initialize(URL location, ResourceBundle resources) {
       addVBox();
       addVBoxDates();
       populateDates();
       populateEvents();
    }
}