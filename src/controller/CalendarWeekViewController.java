/*
DOCUMENTATION

//LOG-IN FORM//

- The test login credentials are:
  - username: test
  - Password: test

- The log-in form can be translated into 3 languages (English, Italian, and French)
- These are the methods used to test this:

    Locale.setDefault(new Locale("fr", "FR")); //French
    Locale.setDefault(new Locale("en", "EN")); //English
    Locale.setDefault(new Locale("it", "IT")); //Italian

- A log of successful log-ins resides at the root of KeithJonesSchedulingApp in the logFile.txt file

//REMINDERS//
- Reminders will display if the logged in user is the createdBy user on the appointment and the appointment is within 15 minutes
- A separate user is available to test this:
	*Username: pw
	*Password: pw
- To aid testing, the REMINDER_TIME int at the top of the LoginViewController can be adjusted to a time other than the default of 15

//ADD + MAINTAIN CUSTOMER//

- To add/maintain a customer, click Add Appt/Customer, then click Add Customer or Modify Customer
- To deactivate a customer, click Modify Customer, then Deactivate
- A deactivated customer can be reactivated by following the same method and clicking Reactivate.

//MAINTAINING APPOINTMENTS//
- Appointments can be viewed in a month (default) or week view (click the week button).
- Once an appointment has been added to the calendar, it can be clicked from the Month or Week view to modify.
- On the month calendar, the day of the month can be clicked to add a new appointment (appointments can also be added through the Add Appt/Customer button).
- If more than 1 appointment is scheduled on a day in the month view, the more text can be clicked to bring up a list of the appointments for that day. Any of these can be clicked to modify.
- To delete an appointment, select it to bring up the Modify Appointment window, then click Delete.

//TIMEZONE//
- Appointments will automatically adjust based on the system time zone

//VALIDATION//
- Appointments must be scheduled within business hours (0800-2000).
- Appointments cannot overlap
- All fields on the appointment and customer fields must be completed
- The username/password for login must be correct

//REPORTING//
-Reports are available at the top right of the month view page. These include:
	*number of appt types by month (using title field)
	*Schedules grouped by consultant (createdBy user field)
	*total appointments per day
*/


package controller;

import java.io.IOException;
import java.net.URL;
import java.sql.SQLException;
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
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.RowConstraints;
import javafx.scene.layout.VBox;
import javafx.stage.Modality;
import javafx.stage.Stage;
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
       refreshCalendar();
    }
    @FXML
    void nextMonthBtn(ActionEvent event) {
        clearCalendar();
        main.currentViewDate = main.currentViewDate.plusDays(7);
        populateDates();
        populateTimes();
        populateEvents();
    }
    @FXML
    void previousMonthBtn(ActionEvent event) {
        clearCalendar();
        main.currentViewDate = main.currentViewDate.minusDays(7);
        populateDates();
        populateTimes();
        populateEvents();
    }
    @FXML
    void handleMonthBtn(ActionEvent event) throws IOException {
        MainSchedulingApp.changeScene(event, "/view/CalendarMonthView.fxml", "Calendar - Month View");
    }
    /* INITIALIZE */
    @Override
    public void initialize(URL location, ResourceBundle resources) {
        addVBox();
        addVBoxDates();
        populateDates();
        populateEvents();
    }
    /* Calendar Construction Methods */
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
                final int iTest = i;
                final int jTest = j;
                                         
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
        
        for(Appointment appointment : Appointment.getAppointments() ){
            LocalDate date = appointment.getStart().toLocalDate();
            int dateInt = date.getDayOfWeek().getValue();
            LocalDate firstDayOfWeek = firstDayOfWeek();
            int firstDayOfWeekInt = firstDayOfWeek().getDayOfWeek().getValue();
            
            if (dateInt==7){
                dateInt = 0;
            }
            if (firstDayOfWeekInt == 7){
                firstDayOfWeekInt = 0;
            }

            if (date.isAfter(firstDayOfWeek.minusDays(1)) && date.isBefore(firstDayOfWeek.plusDays(7))){
                int horizontal = dateInt + 1;
                String labelText = (appointment.getStart().toLocalTime().format(DateTimeFormatter.ofPattern("hh:mm a"))
                    + " - " + appointment.getTitle());
                Label eventlbl = new Label(labelText);
                eventlbl.getStyleClass().add("eventLbl");
                
                 eventlbl.setMaxWidth(Double.MAX_VALUE);

                    eventlbl.addEventHandler(MouseEvent.MOUSE_PRESSED, (e)->{
                    try {
                        editEvent(appointment);
                    } catch (IOException ex) {
                        Logger.getLogger(CalendarWeekViewController.class.getName()).log(Level.SEVERE, null, ex);
                    } catch (SQLException ex) {
                        Logger.getLogger(CalendarWeekViewController.class.getName()).log(Level.SEVERE, null, ex);
                    }
                    });

                int vertical = (appointment.getStart().getHour()*2) + (appointment.getStart().getMinute()/30);
                int vHPosition = horizontal + (vertical*8);
                
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
    /*Appointment Manipulation Methods*/
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
        
        refreshCalendar();       
    }
    public void addEvent(int row, int column) throws IOException{
        Label label = (Label)dateVBoxArray.get(column).getChildren().get(0);
        String columnDate = label.getText();

        LocalDate date = LocalDate.parse(columnDate);

        label = (Label)vBoxArray.get((row-1)*8).getChildren().get(0);
        String labelTime = label.getText();

        LocalTime time = LocalTime.parse(labelTime+":00");
        
        Stage stage = new Stage();
        stage.initModality(Modality.APPLICATION_MODAL);
        stage.initOwner(null);
        Scene main;
       
        FXMLLoader loader = new FXMLLoader();       
        loader.setLocation(MainSchedulingApp.class.getResource("/view/AppointmentView.fxml"));
        Parent root = (Parent) loader.load();
        main = new Scene(root);
        AppointmentViewController controller = loader.getController();
        controller.addAppt(date, time);
        stage.setScene(main);
        stage.showAndWait(); 
        
        refreshCalendar();    
    }
    public void refreshCalendar(){
        clearCalendar();
        populateDates();
        populateTimes();
        populateEvents();
    }
    
}