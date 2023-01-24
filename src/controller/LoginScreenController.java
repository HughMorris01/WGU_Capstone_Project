package controller;

import database.DBUsers;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import model.*;

import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.net.URL;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.util.Locale;
import java.util.Objects;
import java.util.ResourceBundle;

/** This class is the controller for the LoginScreen.fxml document and not meant to be instantiated.
 *  The Main screen authenticates the User's login credentials, as well determines the User's locale.
 * @author Gregory Farrell
 * @version 1.1
 * */
public class LoginScreenController implements Initializable {
    /** User ID text field */
    public TextField userIdTextField;
    /** User Password text field */
    public TextField passwordTextField;
    /** Password label */
    public Label passwordLabel;
    /** User ID label */
    public Label userIdLabel;
    /** Login button */
    public Button loginButton;
    /** User locale label label */
    public Label userLocaleLabel;
    /** Label that displays the User's locale */
    public Label userLocaleData;
    /** User's Locale */
    private static Locale userLocale;
    /** LocalDateTime that is recorded at the time of login */
    private static LocalDateTime loginTime;
    /** User that is created upon login authentication */
    private static User user;
    /** Error message displayed if login attempt fails */
    private static String errorMessage1 = "The username and password entered do not match our records, please try again.";
    /** Error message title if no username or password is entered. */
    private static String blankField = "Blank Field";
    /** Error message title if login attempt fails. */
    private static String invalidLogin = "Invalid Login Attempt";
    /** Error message displayed if no username is entered. */
    private static String blankAlert1 = "A Username Must be Entered.";
    /** Error message displayed if no password is entered. */
    private static String blankAlert2 = "A Password Must be Entered.";
    /** Counter to track and record the number of login attempts. */
    private static int loginCounter = 1;
    /** Boolean used to record if the login attempt was successful or not. */
    private static boolean loginSuccess = false;

    /** This method is called by the FXMLLoader.load() call contained in the start() method of the Main class. The
     * method checks the User's locale in order to change the language to French if appropriate. The method also flushes
     * the login_activity.txt file so that the file is created fresh each time the application is launched.
     * @param rb An unreferenced ResourceBundle object passed automatically
     * @param url An unreferenced URL object passed automatically
     * */
    @Override
    public void initialize(URL url, ResourceBundle rb) {

        lambdaRequirement1.checkLocale();

        //lambdaRequirement2.flushTxtFile("login_activity.txt");
    }

    /** This is the lambda expression that flushes the login_activity.txt file. This is necessary so that the file is
     * created fresh each time the application is launched.
     * */
    public LEInterface2 lambdaRequirement2 = (loginFileName) -> {
        try {
            FileWriter fw = new FileWriter(loginFileName);
            fw.flush();
            fw.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    };

    /** This is the lambda expression that checks the User's locale when the application is launched. This is necessary
     * so that the resource bundle may be utilized if appropriate.
     * */
    public LEInterface1 lambdaRequirement1 = () -> {
        userLocale = Locale.getDefault();
        if (userLocale.getLanguage().equals("fr")) {
            ResourceBundle rb = ResourceBundle.getBundle("main/LocaleBundle", userLocale);
            passwordLabel.setText(rb.getString("password"));
            userIdLabel.setText(rb.getString("userId"));
            loginButton.setText(rb.getString("login"));
            loginButton.setPrefWidth(110);
            userLocaleLabel.setText(rb.getString("locale"));
            userLocaleData.setText(ZoneId.systemDefault().getId());
            errorMessage1 = rb.getString("errorMessage1");
            blankField = rb.getString("blankField");
            invalidLogin = rb.getString("invalidLogin");
            blankAlert1 = rb.getString("blankAlert1");
            blankAlert2 = rb.getString("blankAlert2");
        }
        else { userLocaleData.setText(ZoneId.systemDefault().getId()); }
    };

    /** This method records data each time a login is attempted. The characters entered for the username and password, as
     * well as a time stamp and the outcome of the attempt are appended to the login_activity.txt file in the root folder
     * of the application.
     * @throws IOException An IOException is thrown if the file to be written to cannot be located.
     * */
    public void recordLoginAttempt() throws IOException {
        FileWriter fileWriter = new FileWriter("login_activity.txt", true);
        PrintWriter printWriter = new PrintWriter(fileWriter);
        String enteredUsername = userIdTextField.getText();
        String enteredPassword = passwordTextField.getText();
        LocalDateTime loginLDT = LocalDateTime.now();
        DateTimeFormatter dtf = DateTimeFormatter.ofPattern("M/d/yy h:mma");
        String loginLDTString = dtf.format(loginLDT);
        printWriter.println("Login Attempt #" + loginCounter + " - " + loginLDTString + "\nUsername Entered: \"" + enteredUsername
                + "\" | Password Entered: \"" + enteredPassword + "\" | Successful: " + loginSuccess +"\n");
        loginCounter += 1;
        printWriter.close();
    }

    /** This method verifies the User's login credentials. The username and password are checked against the
     * data in the database to verify the user is authorized to use the application.
     * @param actionEvent Passed from the On Action event listener in the MainScreen FXML document
     * */
    public void loginValidation(ActionEvent actionEvent) {
        try {
            ObservableList<User> allUsers = DBUsers.getAllUsers();
            String tempText = userIdTextField.getText();
            if (tempText.isEmpty()) {
                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.setTitle(blankField);
                alert.setContentText(blankAlert1);
                alert.show();
                return;
            }
            String tempText1 = passwordTextField.getText();
            if (tempText1.isEmpty()) {
                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.setTitle(blankField);
                alert.setContentText(blankAlert2);
                alert.show();
                return;
            }
            String enteredName = userIdTextField.getText();
            String enteredPassword = passwordTextField.getText();

            for(User u : allUsers) {
                if(u.getUserName().equals(enteredName) && u.getUserPassword().equals(enteredPassword)) {
                    loginTime = LocalDateTime.now();
                    user = u;
                    loginSuccess = true;

                    try {
                        recordLoginAttempt();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                    if(u.getAdminKey() == 0) {
                        for(Salesperson salesperson : Salesperson.allSalespersonsList) {
                            if(salesperson.getUserId() == user.getUserId()) {
                                SalespersonHomeScreenController.setUserSalesperson(salesperson);
                            }
                        }

                        Parent root = FXMLLoader.load(Objects.requireNonNull(getClass().getResource("/view/SalespersonHomeScreen.fxml")));
                        Stage stage = (Stage) ((Node) (actionEvent.getSource())).getScene().getWindow();
                        Scene scene = new Scene(root, 500, 500);
                        stage.setScene(scene);
                        stage.setTitle("Salesperson Home Screen");
                    }
                    if(u.getAdminKey() == 1) {
                        for(Administrator administrator : Administrator.allAdministratorsList) {
                            if(administrator.getUserId() == user.getUserId()) {
                                AdminHomeScreenController.setUserAdministrator(administrator);
                            }
                        }

                        Parent root = FXMLLoader.load(Objects.requireNonNull(getClass().getResource("/view/AdminHomeScreen.fxml")));
                        Stage stage = (Stage) ((Node) (actionEvent.getSource())).getScene().getWindow();
                        Scene scene = new Scene(root, 1000, 500);
                        stage.setScene(scene);
                        stage.setTitle("Administrator Home Screen");
                    }
                    return;
                }
            }
               Alert alert = new Alert(Alert.AlertType.ERROR);
               alert.setTitle(invalidLogin);
               alert.setContentText(errorMessage1);
               alert.show();

               recordLoginAttempt();
               userIdTextField.clear();
               passwordTextField.clear();
        }
        catch (Exception exception) {
            Alert alert = new Alert(Alert.AlertType.ERROR, "Please enter valid values in the input fields");
            alert.setTitle("Invalid Input Type");
            alert.setContentText(exception.getLocalizedMessage());
            alert.show();
        }
    }

    // Getter functions
    public static Locale getUserLocale() { return userLocale;}
    public static LocalDateTime getLoginTime() { return loginTime; }
    public static User getUser() { return user; }
}
