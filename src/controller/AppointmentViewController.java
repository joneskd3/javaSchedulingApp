package controller;

import java.io.IOException;
import java.net.URL;
import java.sql.ResultSet;
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
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.DatePicker;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.AnchorPane;
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
    @FXML
    private Button deleteBtn;
    
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
            appointment = new Appointment(dateTime, type, customer.getCustomerId());
        } else {
            appointment.setStart(dateTime);
            appointment.setTitle(type);
            appointment.setCustomer(customer.getCustomerId());
        }

        main.getStage(event).close();
        Appointment.populateFromDB(); 
    }
    @FXML
    void handleDelete (ActionEvent event) throws IOException, SQLException {
        String query = "DELETE FROM appointment WHERE appointmentId = " + appointment.getAppointmentId();
        Database.actionQuery(query);
        Appointment.populateFromDB();
        main.getStage(event).close();
        
    }   
    @FXML
    void handleAddCustomer(ActionEvent event) throws IOException {
        main.changeScene(event,"/view/CustomerView.fxml","Add Customer");
    }
    @FXML
    void handleModifyCustomer(ActionEvent event) throws IOException, SQLException {

        int customerIndex = customerField.getSelectionModel().getSelectedIndex();
        Customer selectedCustomer = customerField.getSelectionModel().getSelectedItem();
        
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
        
        
        
        

//        if(customerIndex >=0){
//            //FXMLLoader loader = new FXMLLoader();       
//           
//                loader.setLocation(MainSchedulingApp.class.getResource("CustomerView.fxml"));
//                Parent root = (Parent) loader.load(); //sets parent to loader
//                Scene scene = new Scene(root); //creates new scene   
//                CustomerViewController controller = loader.getController();
//                controller.editCustomer(selectedCustomer);
//            
//            Stage stage = getStage(event);
//            stage.setTitle("Modify Customer"); //sets title
//            //stage.setScene(scene);
//            stage.show();      
//        } else { //nothing selected
//            Alert alert = new Alert(Alert.AlertType.WARNING);
//            alert.setTitle("No Customer Selected");
//            alert.setHeaderText("No customer selected");
//            alert.setContentText("Select a customer from the table.");
//            alert.showAndWait();
//        }
        
    }

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        createTimeArray();
        try {
            createCustomerArray();
        } catch (SQLException ex) {
            Logger.getLogger(AppointmentViewController.class.getName()).log(Level.SEVERE, null, ex);
        }
    }  
    public void createTimeArray(){
        ObservableList<LocalTime> options = FXCollections.observableArrayList();
        
        LocalTime currentTime = LocalTime.parse("00:00");
        options.add(currentTime);
        
        while (!currentTime.equals(LocalTime.parse("23:30"))){
           currentTime = currentTime.plusMinutes(30);
           options.add(currentTime);
        }
        
        timeField.setItems(options);
        
        //customerField.getSelectionModel().getSelectedItem();
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
    public void editAppt(Appointment appointment) throws SQLException{
        edited = true;
        deleteBtn.setVisible(true);
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
