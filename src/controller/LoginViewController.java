package controller;

/* Imports */
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.net.URL;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Locale;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import model.Appointment;
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
    MainSchedulingApp mainApp;
    public static String currentUser = "admin";
    Boolean frenchLanguage = false;
    Boolean italianLanguage = false;
    Boolean englishLanguage = false;
    
    @FXML
    void handleSignInBtn(ActionEvent event) throws IOException, SQLException {

        if(validateSignIn()){
            reminderPopup();
            mainApp.changeScene(event,"/view/CalendarMonthView.fxml","Calendar");
        } else if (frenchLanguage) {
            Alert alert = new Alert(Alert.AlertType.WARNING);
            alert.setTitle("Erreur");
            alert.setHeaderText("Nom d'utilisateur/mot de passe incorrect");
            alert.setContentText("Veuillez réessayer");
            alert.showAndWait();
        } else if (italianLanguage){
            Alert alert = new Alert(Alert.AlertType.WARNING);
            alert.setTitle("errore");
            alert.setHeaderText("Nome utente/password non sono corretti");
            alert.setContentText("Riprova");
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
        if(Locale.getDefault().getLanguage().equals("fr")){ 
            mainApp.mainStage.setTitle("Se Connecter");
            signInLabel.setText("Se Connecter");
            usernameLabel.setText("Nom d'utilisateur");
            passwordLabel.setText("Mot de passe");
            signInBtn.setText("Se Connecter");
            frenchLanguage = true;
        } else if (Locale.getDefault().getLanguage().equals("it")){
            mainApp.mainStage.setTitle("Registrati");
            signInLabel.setText("Registrati");
            usernameLabel.setText("Nome utente");
            passwordLabel.setText("Parola d'ordine");
            signInBtn.setText("Registrati");
            frenchLanguage = true;
        } else {
            mainApp.mainStage.setTitle("Sign In");
        }
    }
    public boolean validateSignIn() throws SQLException, FileNotFoundException, IOException{

        String username = usernameField.getText();
        String password = passwordField.getText();
        
        String query = "SELECT * FROM user WHERE userName = '" + username + "'";
        ResultSet results = Database.resultQuery(query);
        
        while (results.next()){
            if (results.getString("password").equals(password)){
                currentUser = username;
                printToLog(username);

                return true;             
            } 
        }
        return false;
    }
    public void reminderPopup() throws SQLException{
        
        String reminders = "";
        
        String query = 
                "SELECT start, customer.customerName AS name, title "
                + "FROM appointment LEFT JOIN customer ON appointment.customerId = customer.customerId "
                + "WHERE TIMESTAMPDIFF(MINUTE,start,CURRENT_TIMESTAMP) BETWEEN 0 AND 15 AND appointment.createdBy = '" + currentUser + "'";
        
        ResultSet results = Database.resultQuery(query);
        
        while(results.next()){
            LocalDateTime start = LocalDateTime.parse(results.getString("start"),DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss.n"));
            start = Appointment.toLocalDateTime(start);
            String startFormatted = start.format(DateTimeFormatter.ofPattern("HH:mm"));
            String title = results.getString("title");
            String name = results.getString("name");
            
            reminders = reminders + " - " + title + " at " + startFormatted + " with " + name + "\n";           
        }
        if (reminders.length() > 0){
            reminders = "Upcoming appointments with " + currentUser + ": \n" + reminders;
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("Upcoming Appointments");
            //alert.setHeaderText("");
            alert.setContentText(reminders);
            alert.showAndWait();
        }
    }
    public void printToLog(String user) throws FileNotFoundException, IOException{
        try (FileWriter file = new FileWriter("logFile.txt", true);
            PrintWriter print = new PrintWriter(file);){
                print.println(user + " -  " + LocalDateTime.now().toString());
        }
    }
}