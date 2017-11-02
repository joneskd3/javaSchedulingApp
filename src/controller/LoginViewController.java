package controller;

/* Imports */
import java.io.IOException;
import java.net.URL;
import java.sql.ResultSet;
import java.sql.SQLException;
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
import model.Database;
import model.MainSchedulingApp;

public class LoginViewController implements Initializable{

    /* FXML Variables */
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
    
    /* Other Variables */
    Stage stage;
    MainSchedulingApp mainApp;
    public static String currentUser = "admin";
    
      @FXML
    void Enter(TouchEvent event) {

    }

    @FXML
    void enterKeyHandler(KeyEvent event) {
        //
    }
    @FXML
    void handleSignInBtn(ActionEvent event) throws IOException, SQLException {

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
    public boolean validateSignIn() throws SQLException{

        String username = usernameField.getText();
        String password = passwordField.getText();
        
        String query = "SELECT * FROM user WHERE userName = '" + username + "'";
        ResultSet results = Database.resultQuery(query);
        
        while (results.next()){
            System.out.println("UN: " + results.getString("Username"));
            System.out.println("PW: " + results.getString("Password"));
            if (results.getString("password").equals(password)){
                currentUser = username;
                return true;
                
            } else {
                return false;
            }
        }
//        for(User user : User.getUserArray()){
//            if (user.getUserName().equals(username)){
//                if (user.getPassword().equals(password)){
//                    return true;
//                } else {
//                    return false;
//                }
//            }
        return false;
    }
}
