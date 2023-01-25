package controller;

import database.DBAdministrators;
import database.DBSalespersons;
import database.DBUsers;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import model.Region;
import model.User;

import java.io.IOException;
import java.net.URL;
import java.sql.SQLException;
import java.util.Objects;
import java.util.Optional;
import java.util.ResourceBundle;

/** This is the controller class for the createNewUserScreen.fxml document. It creates new users for the application, either
 * salespersons or administrators, the latter of which are permitted to create new users themselves.
 * @author Gregory Farrell
 * @version 1.1
 */
public class CreateNewUserScreenController implements Initializable {
    /** Combo box for choosing the type of user. */
    public ComboBox<String> userTypeComboBox;
    /** Combo box for choosing the region a salesperson belongs to. */
    public ComboBox<Region> regionComboBox;
    /** Text field for entering user's first name. */
    public TextField firstNameTextField;
    /** Text field for entering user's last name. */
    public TextField lastNameTextField;
    /** Text field for entering user's email. */
    public TextField emailTextField;
    /** Text field for entering user's username. */
    public TextField usernameTextField;
    /** Text field for entering user's password. */
    public TextField passwordTextField;
    /** Text field for confirming user's password. */
    public TextField passwordTextField2;

    /** This method is called by the FXMLLoader.load() call contained in the toCreateNewUser() method of the
     * AdminHomeHomeScreen class. The method populates the userTypeComboBox and regionComboBox.
     * @param resourceBundle An unreferenced ResourceBundle object passed automatically
     * @param url An unreferenced URL object passed automatically
     */
    public void initialize(URL url, ResourceBundle resourceBundle) {
        ObservableList<String> userList = FXCollections.observableArrayList();
        userList.add("Salesperson");
        userList.add("Administrator");
        userTypeComboBox.setItems(userList);
        regionComboBox.setItems(Region.allRegionsList);
    }

    /** This method is an event handler on the Back button.
     * When clicked, the button redirects the program to the adminHomeScreen.
     * @param actionEvent Passed from the On Action event listener on the SignOut button.
     * @throws IOException Exception gets thrown if load() cannot locate the FXML file. */
    public void toAdminHomeScreen(ActionEvent actionEvent) throws IOException {
        Parent root = FXMLLoader.load(Objects.requireNonNull(getClass().getResource("/view/AdminHomeScreen.fxml")));
        Stage stage = (Stage) ((Node) (actionEvent.getSource())).getScene().getWindow();
        Scene scene = new Scene(root, 1000, 500);
        stage.setScene(scene);
        stage.setTitle("Administrator Home Screen");
    }

    /** This method is an event handler on the SignOut button.
     * When clicked, the button redirects the program to the original Login Screen
     * @param actionEvent Passed from the On Action event listener on the SignOut button.
     * @throws IOException Exception gets thrown if load() cannot locate the FXML file. */
    public void toSignOut(ActionEvent actionEvent) throws IOException {
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION, "Do you wish to log out?");
        Optional<ButtonType> result = alert.showAndWait();

        if (result.isPresent() && result.get() == ButtonType.OK) {
            Parent root = FXMLLoader.load(Objects.requireNonNull(getClass().getResource("/view/LoginScreen.fxml")));
            Stage stage = (Stage) ((Node) (actionEvent.getSource())).getScene().getWindow();
            Scene scene = new Scene(root, 600, 400);
            stage.setScene(scene);
            stage.setTitle("Administrator Home Screen");
        }
    }

    /** This method is an event handler on the Submit button. It validates the data and then inserts it into the database.
     * @param actionEvent Passed from the On Action event listener on the SignOut button.
     * @throws IOException Exception gets thrown if load() cannot locate the FXML file
     * @throws SQLException Exception gets thrown if the SQL fails to execute properly. */
    public void onSubmit(ActionEvent actionEvent) throws SQLException, IOException {
        String userType = userTypeComboBox.getValue();
        int adminKey = 0;
        if (userType == null) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Blank Field");
            alert.setContentText("Please enter a first name. ");
            alert.show();
            return;
        }
        if(userType.equals("Administrator")) {
            adminKey = 1;
        }
        else {
            if (regionComboBox.getSelectionModel().getSelectedItem() == null) {
                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.setTitle("Blank Field");
                alert.setContentText("Please select a Region to assign salesperson to. ");
                alert.show();
                return;
            }
        }
        Region selectedRegion = regionComboBox.getSelectionModel().getSelectedItem();
        String userFirstName = firstNameTextField.getText();
        if (userFirstName == null) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Blank Field");
            alert.setContentText("Please enter a first name. ");
            alert.show();
            return;
        }
        String userLastName = lastNameTextField.getText();
        if (userLastName == null) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Blank Field");
            alert.setContentText("Please enter a last name. ");
            alert.show();
            return;
        }
        String userEmail = emailTextField.getText();
        if (userEmail == null) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Blank Field");
            alert.setContentText("Please enter an email. ");
            alert.show();
            return;
        }
        int emailChecker = 0;
        int emailChecker2 = 0;
        int emailChecker3 = 0;
        for(int i = 0; i < userEmail.length(); i++) {
            Character c = (Character) userEmail.charAt(i);
            if(c.equals("@")) {
                emailChecker += 1;
            }
        }
        String userName = usernameTextField.getText();
        if (userName == null) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Blank Field");
            alert.setContentText("No user name entered.");
            alert.show();
            return;
        }
        for(User user : User.allUserList) {
            if(user.getUserName().equals(userName)) {
                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.setTitle("Username Taken");
                alert.setContentText("The username entered is already in use and not available. ");
                alert.show();
                usernameTextField.clear();
                return;
            }
        }
        String password = passwordTextField.getText();
        if (password == null) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Blank Field");
            alert.setContentText("No password entered. ");
            alert.show();
            return;
        }
        if (password.length() < 6) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Invalid Password");
            alert.setContentText("Password must be at least 5 characters. ");
            alert.show();
            passwordTextField.clear();
            passwordTextField2.clear();
            return;
        }
        String password1 = passwordTextField2.getText();
        if (!password.equals(password1)) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Invalid Password");
            alert.setContentText("Both password fields must be identical. ");
            alert.show();
            return;
        }
        DBUsers.insertUser(userName, password, adminKey);
        DBUsers.getAllUsers();

        int userId = DBUsers.getLastInsertedId();

        if (adminKey == 0) {
            int regionId = selectedRegion.getRegionId();
            DBSalespersons.insertSalesperson(userFirstName, userLastName, userEmail, userId, regionId);
            DBSalespersons.getAllSalespersons();
        }
        else {
            DBAdministrators.insertAdministrator(userFirstName, userLastName, userEmail, userId);
        }

        Parent root = FXMLLoader.load(Objects.requireNonNull(getClass().getResource("/view/AdminHomeScreen.fxml")));
        Stage stage = (Stage) ((Node) (actionEvent.getSource())).getScene().getWindow();
        Scene scene = new Scene(root, 1000, 500);
        stage.setScene(scene);
        stage.setTitle("Administrator Home Screen");
    }

    /** This method is an event handler on the userTypeComboBox.
     * If a salesperson is chosen then the regionComboBox is enabled, otherwise it is disabled.
     * @param actionEvent Passed from the On Action event listener on the SignOut button. */
    public void onTypeSelect(ActionEvent actionEvent) {
        regionComboBox.setDisable(!userTypeComboBox.getValue().equals("Salesperson"));
    }
}
