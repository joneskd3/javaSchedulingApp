package controller;

/* Imports */
import java.io.IOException;
import java.net.URL;
import java.util.Locale;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.TouchEvent;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
import model.MainSchedulingApp;
import model.User;

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
    void Enter(TouchEvent event) {

    }

    @FXML
    void enterKeyHandler(KeyEvent event) {
        //
    }
    @FXML
    void handleSignInBtn(ActionEvent event) throws IOException {

        if(validateSignIn()){
            mainApp.changeScene(event,"/view/CalendarMonthView.fxml","Calendar");
        } else if (Locale.getDefault()!= Locale.ENGLISH) {
            Alert alert = new Alert(Alert.AlertType.WARNING);
            alert.setTitle("Erreur");
            alert.setHeaderText("Nom d'utilisateur/mot de passe incorrect");
            alert.setContentText("Veuillez réessayer");
            alert.showAndWait();
        } else {
            Alert alert = new Alert(Alert.AlertType.WARNING);
            alert.setTitle("Error");
            alert.setHeaderText("Username/password are incorrect");
            alert.setContentText("Please try again");
            alert.showAndWait(); 
        }
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
       changeLanguage();
    }
    public void changeLanguage(){
        if(Locale.getDefault()!=Locale.ENGLISH){
            signInLabel.setText("Se Connecter");
            usernameLabel.setText("Nom d'utilisateur");
            passwordLabel.setText("Mot de passe");
            signInBtn.setText("Se Connecter");
        }
    }
    public boolean validateSignIn(){

        String username = usernameField.getText();
        String password = passwordField.getText();
        
        for(User user : User.getUserArray()){
            if (user.getUserName().equals(username)){
                if (user.getPassword().equals(password)){
                    return true;
                } else {
                    return false;
                }
            }
        }
        return false;
    }
}
