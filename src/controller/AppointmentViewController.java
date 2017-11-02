package controller;

import java.io.IOException;
import java.net.URL;
import java.sql.SQLException;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.ResourceBundle;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.DatePicker;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.AnchorPane;
import javafx.util.StringConverter;
import model.Appointment;
import model.Customer;
import model.MainSchedulingApp;

public class AppointmentViewController implements Initializable {
    @FXML
    private DatePicker dateField;
    @FXML
    private ComboBox<LocalTime> timeField;
    
    @FXML
    private ComboBox<Customer> customerField;
    @FXML
    private AnchorPane signInView;
    @FXML
    private Label appointmentLabel;
    @FXML
    private Label usernameLabel2;
    @FXML
    private Label usernameLabel1;
    @FXML
    private Label usernameLabel;
    @FXML
    private TextField typeField;
    @FXML
    private Label passwordLabel;
    @FXML
    private Button saveBtn;
    @FXML
    private Button addCustomerBtn;
    @FXML
    private Button modifyCustomerBtn;
    
    MainSchedulingApp main;
    Appointment appointment;
    private boolean edited = false;
    
    @FXML
    void handleSave(ActionEvent event) throws IOException, SQLException {
        LocalDate date = dateField.getValue();
        LocalTime time = timeField.getValue();
        LocalDateTime dateTime = time.atDate(date);
        String type = typeField.getText();
        Customer customer = customerField.getValue();
        
        if (! edited){
            appointment = new Appointment(dateTime, type, customer);
            main.calendarArray.addAppointment(appointment);

        } else {
            appointment.setStart(dateTime);
            appointment.setTitle(type);
            appointment.setCustomer(customer);
        }

        main.getStage(event).close();
        
    }
    @FXML
    void handleDelete (ActionEvent event) throws IOException {

        
    }
    
    @FXML
    void handleAddCustomer(ActionEvent event) throws IOException {
        main.changeScene(event,"/view/CustomerView.fxml","Add Customer");
    }

    @FXML
    void handleModifyCustomer(ActionEvent event) throws IOException {
        main.changeScene(event,"/view/CustomerView.fxml","Edit Customer");
    }
    


    
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        createTimeArray();
        createCustomerArray();   
    }  
    public void createTimeArray(){
        ObservableList<LocalTime> options = FXCollections.observableArrayList();
        
        LocalTime currentTime = LocalTime.parse("00:00");
        options.add(currentTime);
        
        while (!currentTime.equals(LocalTime.parse("23:30"))){
           currentTime = currentTime.plusMinutes(30);
           options.add(currentTime);
           System.out.println(currentTime);
        }
        
        timeField.setItems(options);
        
        //customerField.getSelectionModel().getSelectedItem();
    }
    public void createCustomerArray(){
        ObservableList<Customer> customers = FXCollections.observableArrayList();
        
        Customer addNewCustomer = new Customer("Add New Customer","","","","","",true,null,null);
        Customer customer = new Customer("Sam Jones","1340","","","","",true,null,null);

        customers.add(addNewCustomer);
        customers.add(customer);
        
        
        
        
        
        customerField.setConverter(new StringConverter<Customer>(){
            @Override
            public String toString(Customer customer){
                return customer.getCustomerName();
            }

            @Override
            public Customer fromString(String string) {
                return null;
            }
        });
    
       customerField.setItems(customers);
    }
    public void editAppt(Appointment appointment){
        edited = true;
        this.appointment = appointment;
        appointmentLabel.setText("Modify Appt");
        
        dateField.setValue(appointment.getStart().toLocalDate());
        timeField.setValue(appointment.getStart().toLocalTime());
        typeField.setText(appointment.getTitle());
        customerField.setValue(appointment.getCustomer());
     
    }
    public void addAppt(LocalDate date){
        dateField.setValue(date);
    }
    public void addAppt(LocalDate date,LocalTime time){
        dateField.setValue(date);
        timeField.setValue(time);
    }
    
}
