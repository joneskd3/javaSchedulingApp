package controller;

import java.net.URL;
import java.sql.SQLException;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import model.Customer;
import model.MainSchedulingApp;

public class CustomerViewController implements Initializable{

    @FXML
    private Label customerLabel;
    @FXML
    private TextField customerNameField;
    @FXML
    private TextField phoneField;
    @FXML
    private ComboBox<Boolean> activeField;
    @FXML
    private TextField addressField;
    @FXML
    private TextField address2Field;
    @FXML
    private TextField cityField;
    @FXML
    private TextField postalCodeField;
    @FXML
    private Button deleteBtn;
     
    Customer customer;
    MainSchedulingApp mainApp;
    Boolean edited = false;

    @FXML
    void handleSave(ActionEvent event) throws SQLException {
        if(validateFields()){
            if(!edited){
              customer = new Customer();
            }

            customer.setCustomerName(customerNameField.getText());
            customer.setAddress(addressField.getText());
            customer.setAddress2(address2Field.getText());
            customer.setCity(cityField.getText());
            customer.setPostalCode(postalCodeField.getText());
            customer.setPhone(phoneField.getText());

            mainApp.getStage(event).close();  
        }
        
    }
    @FXML
    void handleDelete(ActionEvent event) throws SQLException {
        
        if (customer.getActive()){
            customer.setActive(false);
        } else {
            customer.setActive(true);
        }
        
        mainApp.getStage(event).close();
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
    }
    public void editCustomer(Customer customer){
        edited = true;
        deleteBtn.setVisible(true);
        
        if(!customer.getActive()){
            deleteBtn.setText("Reactivate");
        }
        
        customerLabel.setText("Modify Customer");

        this.customer = customer;
        customerNameField.setText(customer.getCustomerName());
        phoneField.setText(customer.getPhone());
        addressField.setText(customer.getAddress());
        address2Field.setText(customer.getAddress2());
        cityField.setText(customer.getCity());
        postalCodeField.setText(customer.getPostalCode());

    }
    public boolean validateFields(){
        if(customerNameField.getText().isEmpty() ||
                addressField.getText().isEmpty() ||
                address2Field.getText().isEmpty() ||
                cityField.getText().isEmpty() ||
                postalCodeField.getText().isEmpty() ||
                phoneField.getText().isEmpty()
                ){
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Empty Fields");
            alert.setContentText("All fields must have an entry");
            alert.showAndWait();
            return false;
        }
        return true;
    }
         
}