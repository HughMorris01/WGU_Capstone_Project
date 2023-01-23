package controller;

import database.DBAppointments;
import database.DBFirst_Level_Divisions;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import model.Appointment;
import model.Division;

import java.io.IOException;
import java.net.URL;
import java.time.LocalDateTime;
import java.time.Month;
import java.time.format.DateTimeFormatter;
import java.util.ResourceBundle;

/** This is the controller class for the UserHomeScreen.fxml document and is not meant to be instantiated.
 *  This class provides the functionality to the two reports contained on the screen, as well as the buttons that redirect
 *  to the Customer and Appointment screens.
 * @author Gregory Farrell
 * @version 1.0
 * */
public class UserHomeScreenController implements Initializable {

    /** Label that displays the User's login time. */
    public Label loginTimeTextField;
    /** Label that displays the User's username. */
    public Label userName;
    /** ComboBox listing the appointment Types. */
    public ComboBox<String> typeComboBox;
    /** ComboBox listing months of the year. */
    public ComboBox<Month> monthComboBox;
    /** Output TextField for the first report. */
    public TextField outputBox;
    /** ComboBox listing appointment locations. */
    public ComboBox<Division> locationComboBox;
    /** ComboBox listing months of the year. */
    public ComboBox<Month> monthComboBox2;
    /** Output TextField for the second report. */
    public TextField outputBox2;
    /** Boolean to track if this is the User's initial login for the purpose of showing the alert about upcoming appointments. */
    private static boolean initialLogin = true;

    /** This method is called by the FXMLLoader.load() call contained in the loginValidation() method of the LoginScreenController class.
     * The method displays an alert message advising the User if there is an appointment beginning within 15 minutes of the local time.
     * It also populates the data for the combo boxes that are used on the screen to run reports and displays the User's
     * name and login time.
     * @param resourceBundle An unreferenced ResourceBundle object passed automatically
     * @param url An unreferenced URL object passed automatically
     * */
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        LocalDateTime loginSuccess = LoginScreenController.getLoginTime();
        DateTimeFormatter dtf = DateTimeFormatter.ofPattern("hh:mm a");
        String loginTimeString = dtf.format(loginSuccess);
        loginTimeTextField.setText(loginTimeString);
        userName.setText(LoginScreenController.getUser().getUserName());
        ObservableList<String> typeStrings = FXCollections.observableArrayList();
        typeStrings.add("In-Person");
        typeStrings.add("Remote");
        typeStrings.add("Group Session");
        typeComboBox.setItems(typeStrings);
        locationComboBox.setItems(DBFirst_Level_Divisions.getAllDivisions());
        ObservableList<Month> months = FXCollections.observableArrayList();
        Month month1 = Month.JANUARY;
        months.add(month1);
        Month month2 = Month.FEBRUARY;
        months.add(month2);
        Month month3 = Month.MARCH;
        months.add(month3);
        Month month4 = Month.APRIL;
        months.add(month4);
        Month month5 = Month.MAY;
        months.add(month5);
        Month month6 = Month.JUNE;
        months.add(month6);
        Month month7 = Month.JULY;
        months.add(month7);
        Month month8 = Month.AUGUST;
        months.add(month8);
        Month month9 = Month.SEPTEMBER;
        months.add(month9);
        Month month10 = Month.OCTOBER;
        months.add(month10);
        Month month11 = Month.NOVEMBER;
        months.add(month11);
        Month month12 = Month.DECEMBER;
        months.add(month12);
        monthComboBox.setItems(months);
        monthComboBox2.setItems(months);


        // Provide alert if an upcoming appointment is within the next 15 minutes of local time.
        if(initialLogin) {
            ObservableList<Appointment> everyAppointment = DBAppointments.getEveryAppointment();
            for(int i = 0; i < everyAppointment.size(); i++) {
                Appointment tempAppointment = everyAppointment.get(i);
                if (tempAppointment.getStart().isBefore(loginSuccess.plusMinutes(15)) && tempAppointment.getStart().isAfter(loginSuccess)) {
                    initialLogin = false;
                    int appointmentId = everyAppointment.get(i).getAppointmentId();
                    String appointmentDate = everyAppointment.get(i).getStartDateString();
                    String appointmentTime = everyAppointment.get(i).getStartTimeString();
                    Alert alert = new Alert(Alert.AlertType.INFORMATION);
                    alert.setTitle("Upcoming Appointment");
                    alert.setContentText(appointmentDate + "\nAppointment ID#" + appointmentId + " starts at " + appointmentTime + ".");
                    alert.show();
                    return;
                }
            }
        }
            if(initialLogin) {
                initialLogin = false;
                Alert alert = new Alert(Alert.AlertType.INFORMATION);
                alert.setTitle("No Upcoming Appointments");
                alert.setContentText("There are no appointments in the next 15 minutes.");
                alert.show();
            }
        }

    /** This method is an event handler on the Customers button.
     * When clicked, the button loads and redirects the program to the Customer Screen FXML document.
     * @param actionEvent Passed from the On Action event listener on the Customers' button.
     * @throws IOException Exception gets thrown if load() cannot locate the FXML file
     * */
    public void toCustomerScreen(ActionEvent actionEvent) throws IOException {
        Parent root = FXMLLoader.load(getClass().getResource("/view/CustomerScreen.fxml"));
        Stage stage = (Stage) ((Node) (actionEvent.getSource())).getScene().getWindow();
        Scene scene = new Scene(root, 600, 450);
        stage.setScene(scene);
        stage.setTitle("Customer Screen");
        stage.show();
    }

    /** This method is an event handler on the Appointments button.
     * When clicked, the button loads and redirects the program to the Appointment Screen FXML document.
     * @param actionEvent Passed from the On Action event listener in the User Home Screen FXML document
     * @throws IOException Exception gets thrown if load() cannot locate the FXML file
     * */
    public void toAppointmentScreen(ActionEvent actionEvent) throws IOException {
            Parent root = FXMLLoader.load(getClass().getResource("/view/AppointmentScreen.fxml"));
            Stage stage = (Stage) ((Node) (actionEvent.getSource())).getScene().getWindow();
            Scene scene = new Scene(root, 1050, 450);
            stage.setScene(scene);
            stage.setTitle("Appointment Screen");
            stage.show();
    }

    /** This method is an event handler on the top Calculate button.
     * When clicked, the button runs the report for the chosen criteria.
     * @param actionEvent Passed from the On Action event listener on the top Calculate button.
     * @throws IOException Exception gets thrown if load() cannot locate the FXML file
     * */
    /*public void onSubmit(ActionEvent actionEvent) {
        Month selectedMonth = monthComboBox.getSelectionModel().getSelectedItem();
        if(selectedMonth == null) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("No Month Selected");
            alert.setContentText("Please select a month before computing");
            alert.show();
            return;
        }
        String selectedType = typeComboBox.getSelectionModel().getSelectedItem();
        if(selectedType == null) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("No Type Selected");
            alert.setContentText("Please select a type before computing");
            alert.show();
            return;
        }
        ObservableList<Appointment> reportAppointments = DBAppointments.getAppointmentsByMonthAndType(selectedType, selectedMonth);
        outputBox.setText(String.valueOf(reportAppointments.size()));
    } */

    /** This method is an event handler on the bottom Calculate button.
     * When clicked, the button runs the report for the chosen criteria.
     * @param actionEvent Passed from the On Action event listener on the bottom Calculate button.
     * @throws IOException Exception gets thrown if load() cannot locate the FXML file
     * */
    /*public void onSubmit2(ActionEvent actionEvent) {
        Month selectedMonth = monthComboBox2.getSelectionModel().getSelectedItem();
        if(selectedMonth == null) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("No Month Selected");
            alert.setContentText("Please select a month before computing");
            alert.show();
            return;
        }
        Division selectedDivision = locationComboBox.getSelectionModel().getSelectedItem();
        if(selectedDivision == null) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("No Location Selected");
            alert.setContentText("Please select a location before computing");
            alert.show();
            return;
        }
        ObservableList<Appointment> reportAppointments = DBAppointments.getAppointmentsByMonthAndLocation(selectedDivision, selectedMonth);
        outputBox2.setText(String.valueOf(reportAppointments.size()));
    } */
}
