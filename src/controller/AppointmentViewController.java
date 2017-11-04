package controller;

import java.io.IOException;
import java.net.URL;
import java.sql.SQLException;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.DatePicker;
import javafx.scene.control.Label;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.util.StringConverter;
import model.Appointment;
import model.Customer;
import model.Database;
import model.MainSchedulingApp;

public class AppointmentViewController implements Initializable {
    @FXML
    private DatePicker dateField;
    @FXML
    private ComboBox<LocalTime> timeField;
    @FXML
    private  ComboBox<Customer> customerField;
    @FXML
    private Label appointmentLabel;
    @FXML
    private ComboBox<String> typeField;
    @FXML
    private Button deleteBtn;
    
    MainSchedulingApp main;
    static Appointment appointment;
    private boolean edited = false;
    
    @FXML
    void handleSave(ActionEvent event) throws IOException, SQLException {
        LocalDateTime dateTime = null;
        String type = null;
        Customer customer = null;
        Boolean validFields = validateFields();
        
        if(validFields){
            LocalDate date = dateField.getValue();
            LocalTime time = timeField.getValue();

            dateTime = time.atDate(date);
            type = typeField.getValue();
            customer = customerField.getValue();
            
            if(validateAppointment(dateTime)){
                if (! edited){
                    appointment = new Appointment(dateTime, type, customer.getCustomerId());
                } else {
                    appointment.setStart(dateTime);
                    appointment.setTitle(type);
                    appointment.setCustomer(customer.getCustomerId());
                }

                main.getStage(event).close();
                Appointment.populateFromDB(); 
            }
        }
        
        
    }
    @FXML
    void handleDelete (ActionEvent event) throws IOException, SQLException {
        String query = "DELETE FROM appointment WHERE appointmentId = " + appointment.getAppointmentId();
        Database.actionQuery(query);
        
        Appointment.populateFromDB();
        main.getStage(event).close();  
    }   
    @FXML
    void handleAddCustomer(ActionEvent event) throws IOException, SQLException {
        Stage stage = new Stage();
        stage.initModality(Modality.APPLICATION_MODAL);
        stage.initOwner(null);
        Scene main;

        FXMLLoader loader = new FXMLLoader();       
        loader.setLocation(MainSchedulingApp.class.getResource("/view/CustomerView.fxml"));
        Parent root = (Parent) loader.load();
        main = new Scene(root);

        CustomerViewController controller = loader.getController();
        stage.setScene(main);
        stage.showAndWait();  
        createCustomerArray();
    }
    @FXML
    void handleModifyCustomer(ActionEvent event) throws IOException, SQLException {

        int customerIndex = customerField.getSelectionModel().getSelectedIndex();
        Customer selectedCustomer = customerField.getSelectionModel().getSelectedItem();
        
        if (customerIndex >= 0){
            Stage stage = new Stage();
            stage.initModality(Modality.APPLICATION_MODAL);
            stage.initOwner(null);
            Scene main;

            FXMLLoader loader = new FXMLLoader();       
            loader.setLocation(MainSchedulingApp.class.getResource("/view/CustomerView.fxml"));
            Parent root = (Parent) loader.load();
            main = new Scene(root);

            CustomerViewController controller = loader.getController();
            controller.editCustomer(selectedCustomer);
            stage.setScene(main);
            stage.showAndWait();  
            createCustomerArray();
        } else { //nothing selected
            Alert alert = new Alert(Alert.AlertType.WARNING);
            alert.setTitle("No Customer Selected");
            //alert.setHeaderText("No customer selected");
            alert.setContentText("Select a customer from the dropdown.");
            alert.showAndWait();
        }   
    }
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        //create dropdowns
        createTimeArray();
        try {
            createCustomerArray();
        } catch (SQLException ex) {
            Logger.getLogger(AppointmentViewController.class.getName()).log(Level.SEVERE, null, ex);
        }
        createTypeArray();
    }  
    /*Dropdown Methods*/
    public void createTimeArray(){
        ObservableList<LocalTime> options = FXCollections.observableArrayList();
        
        LocalTime currentTime = LocalTime.parse("00:00");
        options.add(currentTime);
        
        while (!currentTime.equals(LocalTime.parse("23:30"))){
           currentTime = currentTime.plusMinutes(30);
           options.add(currentTime);
        }
        
        timeField.setItems(options);        
    }
    public void createTypeArray(){
        ObservableList<String> options = FXCollections.observableArrayList();
        
        String[] typeArray = {"Consult", "Follow Up", "Referral", "Clinic"};
        
        for(String type : typeArray){
            options.add(type);
        }

        typeField.setItems(options); 
    }
    public void createCustomerArray() throws SQLException{
        ObservableList<Customer> observableCustomers = FXCollections.observableArrayList();
        
        Customer.populateFromDB();

        for (Customer customers: Customer.customerArray){
            observableCustomers.add(customers);
         }

        customerField.setConverter(new StringConverter<Customer>(){
            @Override
            public String toString(Customer customer){
                String active;
                if (customer.getActive()){
                    active = "(Active)";
                } else {
                    active = "(Inactive)";
                }
                String display = customer.getCustomerName() + " " + active;
                
                return display;
            }

            @Override
            public Customer fromString(String string) {
                return null;
            }
        });
    
       customerField.setItems(observableCustomers);
    }
    /*Add + Edit Methods*/
    public void editAppt(Appointment appointment) throws SQLException{
        edited = true;
        deleteBtn.setVisible(true);
        AppointmentViewController.appointment = appointment;
        appointmentLabel.setText("Modify Appt");
                
        dateField.setValue(appointment.getStart().toLocalDate());
        timeField.setValue(appointment.getStart().toLocalTime());
        typeField.setValue(appointment.getTitle());
        customerField.setValue(appointment.getCustomer());     
    }
    public void addAppt(LocalDate date){
        dateField.setValue(date);
    }
    public void addAppt(LocalDate date,LocalTime time){
        dateField.setValue(date);
        timeField.setValue(time);
    }
    /*Validation*/
    public static boolean validateAppointment(LocalDateTime localDateTime){
        
        class OutsideBusinessHoursException extends Exception {
            public OutsideBusinessHoursException() {}
        }
        class OverlappingAppointmentException extends Exception {
            public OverlappingAppointmentException() {}
        }
        
        //Business hours validation
        try {
            if (localDateTime.getHour() >= MainSchedulingApp.businessHoursEnd.getHour()
                    || localDateTime.getHour() < MainSchedulingApp.businessHoursStart.getHour()){
                
                throw new OutsideBusinessHoursException();
            }     
        } catch (OutsideBusinessHoursException ex){
            
            String error = "The appointment is outside business hours of " 
                + MainSchedulingApp.businessHoursStart + " to " + MainSchedulingApp.businessHoursEnd;
            
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Outside Business Hours");
            alert.setContentText(error);
            alert.showAndWait();
            return false;
        }
        //Overlapping appointment validation
        try {
            for (Appointment appt : Appointment.getAppointments()){
                //will not throw warning if appointment overlaps itself
                if (localDateTime.equals(appt.getStart()) && appt != appointment){
                    throw new OverlappingAppointmentException();
                }
            }
        } catch (OverlappingAppointmentException ex){
            
            String error = "The appointment overlaps with another appointment"; 
            
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Overlapping Appointment");
            alert.setContentText(error);
            alert.showAndWait();
            return false;
        }
        
        return true;
    }
    public boolean validateFields(){        
        
        if(dateField.getValue() != null &&
                timeField.getValue() != null &&
                typeField.getValue() != null &&
                customerField.getValue() != null){
            
            return true;          
        } else {
                 
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Empty Fields");
            alert.setContentText("All fields must have an entry");
            alert.showAndWait();
            return false;
        }
    }
}
