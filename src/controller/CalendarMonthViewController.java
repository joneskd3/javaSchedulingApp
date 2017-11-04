package controller;

import java.io.IOException;
import java.net.URL;
import java.sql.SQLException;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.VBox;
import javafx.stage.Modality;
import javafx.stage.Stage;
import model.Appointment;
import model.MainSchedulingApp;

public class CalendarMonthViewController implements Initializable {
    /* FXML Variables */
    @FXML
    private GridPane calendarGrid;
    @FXML
    private Label monthYearLabel;
    @FXML
    private Button monthBtn;
    @FXML
    private Button weekBtn;
     
    /* Other Variables */
    ArrayList<VBox> vBoxArray = new ArrayList<>();
    MainSchedulingApp main;
    
    /*Report Buttons*/
    @FXML
    void apptByDayBtn(ActionEvent event) throws SQLException, IOException {
        Stage stage = new Stage();
        stage.initModality(Modality.APPLICATION_MODAL);
        stage.initOwner(null);
        Scene main;
       
        FXMLLoader loader = new FXMLLoader();       
        loader.setLocation(MainSchedulingApp.class.getResource("/view/AppointmentList.fxml"));
        Parent root = (Parent) loader.load();
        main = new Scene(root);
        
        AppointmentListController controller = loader.getController();
        controller.totalCountByDay();
        
        stage.setScene(main);
        stage.showAndWait();  
        refreshCalendar();  
    }
    @FXML
    void apptTypeByMonthBtn(ActionEvent event) throws SQLException, IOException {
        
        Stage stage = new Stage();
        stage.initModality(Modality.APPLICATION_MODAL);
        stage.initOwner(null);
        Scene main;
       
        FXMLLoader loader = new FXMLLoader();       
        loader.setLocation(MainSchedulingApp.class.getResource("/view/AppointmentList.fxml"));
        Parent root = (Parent) loader.load();
        main = new Scene(root);
        
        AppointmentListController controller = loader.getController();
        controller.reportTypeByMonth();
        
        stage.setScene(main);
        stage.showAndWait();  
        refreshCalendar();  
    }
    @FXML
    void consultantScheduleBtn(ActionEvent event) throws IOException, SQLException {
         
        Stage stage = new Stage();
        stage.initModality(Modality.APPLICATION_MODAL);
        stage.initOwner(null);
        Scene main;
       
        FXMLLoader loader = new FXMLLoader();       
        loader.setLocation(MainSchedulingApp.class.getResource("/view/AppointmentList.fxml"));
        Parent root = (Parent) loader.load();
        main = new Scene(root);
        
        AppointmentListController controller = loader.getController();
        controller.reportConsultantSchedule();
        
        stage.setScene(main);
        stage.showAndWait();  
        refreshCalendar();    
    }
    /*Calendar Methods*/
    @FXML
    void addAppointmentBtn(ActionEvent event) throws IOException {
        MainSchedulingApp.popUpScene(event,"/view/AppointmentView.fxml","Appointment");
        refreshCalendar();
    }
    @FXML
    void nextMonthBtn(ActionEvent event) {
        clearCalendar();
        main.currentViewDate = main.currentViewDate.plusMonths(1);
        formatHeader();
        createCalendar();
    }
    @FXML
    void previousMonthBtn(ActionEvent event) {
        clearCalendar();
        main.currentViewDate = main.currentViewDate.minusMonths(1);
        formatHeader();
        createCalendar();
    }
    @FXML
    void handleWeekBtn(ActionEvent event) throws IOException {
        MainSchedulingApp.changeScene(event, "/view/CalendarWeekView.fxml", "Calendar - Week View");
    }
    
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        try {
            Appointment.populateFromDB();
        } catch (SQLException ex) {
            Logger.getLogger(CalendarMonthViewController.class.getName()).log(Level.SEVERE, null, ex);
        }
        addVBox();
        formatHeader();
        createCalendar();
    }      
    /*Calendar Creation Methods*/
    public void addVBox(){
         /* Adds a VBox to each cell of the Gridpane (CalendarGrid)*/

        //number of rows and columns
        int numRows = 6;
        int numCols = 7;
        
        //start at 1 to account for day of week row
        for (int i = 1; i < numRows + 1; i++){
            for (int j = 0; j < numCols; j++){
                
                //creates box and applies "day" style for .css reference
                VBox day = new VBox();
                day.getStyleClass().add("day");
                
                //adds VBox to array for later access
                vBoxArray.add(day);
                      
                calendarGrid.add(day,j,i);
            }
        }
    }
    public void createCalendar(){
        
        int firstDayOfMonth = getFirstDay();
        
        //current day of month
        int currentDay = 1;
        //grid goes from 0-42; starts on first week day
        int vBoxArrayPosition = 0;
               
        for (int i = 0; i < 6; i++){
            for (int j = 0; j < 7; j++){
                //shortcuts row iteration on first pass to account for different start days
                if (currentDay == 1){
                    //shade cells before month's date range
                    //move first label position to first week day of month
                    while (j < firstDayOfMonth){
                        vBoxArray.get(vBoxArrayPosition).setStyle("-fx-background-color: #eee");
                        j++;
                        vBoxArrayPosition++;
                    }                    
                }
                if (currentDay <= main.currentViewDate.lengthOfMonth()){
                    vBoxArray.get(vBoxArrayPosition).setStyle("-fx-background-color: #FFF");
                    Label dayLabel = new Label(String.valueOf(currentDay));
                    vBoxArray.get(vBoxArrayPosition).getChildren().add(dayLabel);
                    dayLabel.setMaxWidth(Double.MAX_VALUE); 
                    
                    dayLabel.addEventHandler(MouseEvent.MOUSE_PRESSED, (e)->{
                        try {
                            addEvent(dayLabel);
                        } catch (IOException ex) {
                            Logger.getLogger(CalendarMonthViewController.class.getName()).log(Level.SEVERE, null, ex);
                        }
                    });

                    currentDay++;
                    vBoxArrayPosition++;
                    
                } else {
                    //shade cells after month's date range
                    while (vBoxArrayPosition < vBoxArray.size()){
                        vBoxArray.get(vBoxArrayPosition).setStyle("-fx-background-color: #eee");
                        vBoxArrayPosition++;
                    }
                }
            }
        }
        populateEvents();
    }
    public void populateEvents(){
        Appointment.sortAppointments();
        for (Appointment appointment : Appointment.getAppointments()){
            if (appointment.getStart().getYear() == main.currentViewDate.getYear()
                && appointment.getStart().getMonthValue() == main.currentViewDate.getMonthValue()){
                
                //create appt label format
                String labelText = (appointment.getStart().toLocalTime().format(DateTimeFormatter.ofPattern("hh:mm a"))
                    + " - " + appointment.getTitle());
                
                Label apptLabel = new Label(labelText);
                apptLabel.getStyleClass().add("eventLbl");
                
                //gets appointment day, adds offset for first day of month, subtract 1 to index on 0
                int arrayPosition = appointment.getStart().getDayOfMonth() + getFirstDay() - 1;
                
                if(vBoxArray.get(arrayPosition).getChildren().size() < 2){
                    vBoxArray.get(arrayPosition).getChildren().add(apptLabel);

                    apptLabel.setMaxWidth(Double.MAX_VALUE);

                    apptLabel.addEventHandler(MouseEvent.MOUSE_PRESSED, (e)->{
                        try {
                            try {
                                editEvent(appointment);
                            } catch (SQLException ex) {
                                Logger.getLogger(CalendarMonthViewController.class.getName()).log(Level.SEVERE, null, ex);
                            }
                        } catch (IOException ex) {
                            Logger.getLogger(CalendarMonthViewController.class.getName()).log(Level.SEVERE, null, ex);
                        }
                    });
                } else if (vBoxArray.get(arrayPosition).getChildren().size() == 2){
                    Label moreLabel = new Label("1 more...");
                    vBoxArray.get(arrayPosition).getChildren().add(moreLabel);
                    moreLabel.setMaxWidth(Double.MAX_VALUE);
                    moreLabel.addEventHandler(MouseEvent.MOUSE_PRESSED, (e)->{
                        try {
                            listEvents(appointment.getStart());
                        } catch (IOException ex) {
                            Logger.getLogger(CalendarMonthViewController.class.getName()).log(Level.SEVERE, null, ex);
                        }
                    });
                    
                } else {
                    Label moreLabel = (Label) vBoxArray.get(arrayPosition).getChildren().get(2);
                    int spaceIndex = moreLabel.getText().indexOf(" ");
                    int count = Integer.parseInt(moreLabel.getText().substring(0, spaceIndex)) + 1;
                    moreLabel.setText(count + " more...");
                }
            }
        }
    }
    public void clearCalendar(){
        for (VBox vbox: vBoxArray){
            vbox.getChildren().clear();
        }
    }
    public void formatHeader(){
        String month = main.currentViewDate.getMonth().toString();
        String year = String.valueOf(main.currentViewDate.getYear());
        monthYearLabel.setText(month + " " + year);
    }
    public void refreshCalendar(){
        clearCalendar();
        createCalendar();
    }
    public int getFirstDay(){
        //Gets first day of month by subtracting (current day of month - 1) from current day
        LocalDate firstDay = main.currentViewDate.minusDays(main.currentViewDate.getDayOfMonth()-1);
        
        //Gets first day of week
        int firstDayOfWeek = firstDay.getDayOfWeek().getValue();
       
        //converts 7 to 0 since the week starts on Sunday
        if(firstDayOfWeek == 7){
            firstDayOfWeek = 0;
        }
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
    public void addEvent(Label day) throws IOException{
        
        int dayInt = Integer.parseInt(day.getText());
        LocalDate sendDate = main.currentViewDate.withDayOfMonth(dayInt);
        
        Stage stage = new Stage();
        stage.initModality(Modality.APPLICATION_MODAL);
        stage.initOwner(null);
        Scene main;
       
        FXMLLoader loader = new FXMLLoader();       
        loader.setLocation(MainSchedulingApp.class.getResource("/view/AppointmentView.fxml"));
        Parent root = (Parent) loader.load();
        main = new Scene(root);
        
        AppointmentViewController controller = loader.getController();
        controller.addAppt(sendDate);
        stage.setScene(main);
        stage.showAndWait();  
        
        refreshCalendar();
       
         
    }
    public void listEvents(LocalDateTime day) throws IOException{
    
        
        Stage stage = new Stage();
        stage.initModality(Modality.APPLICATION_MODAL);
        stage.initOwner(null);
        Scene main;
       
        FXMLLoader loader = new FXMLLoader();       
        loader.setLocation(MainSchedulingApp.class.getResource("/view/AppointmentList.fxml"));
        Parent root = (Parent) loader.load();
        main = new Scene(root);
        AppointmentListController controller = loader.getController();
        controller.listAppt(day);
        stage.setScene(main);
        stage.showAndWait();  
        refreshCalendar();  
    }
}