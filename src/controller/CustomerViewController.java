package controller;

import java.net.URL;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDateTime;
import java.util.ResourceBundle;
import javafx.beans.property.StringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.AnchorPane;
import model.Customer;
import model.Database;
import model.MainSchedulingApp;
import model.User;

public class CustomerViewController implements Initializable{

    @FXML
    private AnchorPane signInView;

    @FXML
    private Label customerLabel;

    @FXML
    private TextField customerNameField;

    @FXML
    private TextField phoneField;

    @FXML
    private ComboBox<Boolean> activeField;

    @FXML
    private TextField createdDateField;

    @FXML
    private TextField createdUserField;

    @FXML
    private TextField addressField;

    @FXML
    private TextField address2Field;

    @FXML
    private TextField cityField;

    @FXML
    private TextField stateField;

    @FXML
    private TextField postalCodeField;

    @FXML
    private Button saveBtn;
    
    
    Customer customer;
    MainSchedulingApp mainApp;
    Boolean edited = false;

    @FXML
    void handleSave(ActionEvent event) throws SQLException {
        customer = new Customer();
        
        customer.setCustomerName(customerNameField.getText());
        customer.setAddress(addressField.getText());
        customer.setAddress2(address2Field.getText());
        customer.setCity(cityField.getText());
        customer.setPostalCode(postalCodeField.getText());
        customer.setPhone(phoneField.getText());
        customer.setActive(activeField.getValue()); //boolean
        customer.setCreatedDate(LocalDateTime.now()); //LocalDateTime
        customer.setAddress(null);
        customer.setCreatedBy(null); //User, logged in user
        
        mainApp.getStage(event).close();
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        createActiveOptions();
    }
    public void createActiveOptions(){
        ObservableList<Boolean> options = FXCollections.observableArrayList();
        
        options.add(true);
        options.add(false);
        
        activeField.setItems(options);
    }   
    public void editCustomer(Customer customer){
        edited = true;
        this.customer = customer;
        //ustomerNameField.setText("Modify Appt");
        customerNameField.setText(customer.getCustomerName());
        phoneField.setText(customer.getPhone());
        //activeField.getValue(customer.getActive());
        addressField.setText(customer.getAddress());
        address2Field.setText(customer.getAddress2());
        cityField.setText(customer.getCity());
        //stateField.setText(customer.getSt);
        postalCodeField.setText(customer.getPostalCode());

    
    }
//    public void createCountryOptions(){
//        ObservableList<String> options = FXCollections.observableArrayList();
//        
//        String query = "SELECT country FROM country";
//        ResultSet results = Database.resultQuery(query);
//        while (results.next()){
//            options.add(results.getString("country"));
//        }
//        
//        activeField.setItems(options);
//    }        
}
