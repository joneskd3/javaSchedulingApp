package controller;

/* Imports */
import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
import model.MainSchedulingApp;

public class LoginViewController implements Initializable{

    /* FXML Variables */
    @FXML
    private AnchorPane signInView;
    @FXML
    private Label signInLabel;
    @FXML
    private Label usernameLabel;
    @FXML
    private TextField usernameField;
    @FXML
    private Label passwordLabel;
    @FXML
    private TextField passwordField;
    @FXML
    private Button signInBtn;
    @FXML
    private Label errorMessageLabel;
    
    /* Other Variables */
    Stage stage;
    MainSchedulingApp mainApp;
    
    @FXML
    void handleSignInBtn(ActionEvent event) throws IOException {
        mainApp.changeScene(event,"/view/CalendarMonthView.fxml","Calendar");
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        //throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }
}
