package controller;

import database.DBAppointments;
import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;
import model.Appointment;
import model.Salesperson;

import java.io.IOException;
import java.net.URL;
import java.sql.SQLException;
import java.time.LocalTime;
import java.util.Optional;
import java.util.ResourceBundle;

/** This is the controller class for the AppointmentScreen.fxml document and is not meant to be instantiated.
 *  This class populates the data for the Appointments table, as well as provide the functionality for the buttons,
 *  radio buttons and combo boxes on the screen.
 *  page.
 * @author Gregory Farrell
 * @version 1.0
 * */
public class AppointmentScreenController implements Initializable {
    /** ToggleGroup used to filter the table data. */
    public ToggleGroup scheduleToggle;
    /** ComboBox used to filer the table data by Contact. */
    public ComboBox<Salesperson> contactComboBox;
    /** TableView populated Appointment data from the database. */
    public TableView<Appointment> appointmentsTable;
    /** TableColumn for Appointment ID. */
    public TableColumn<Appointment, Integer> appointmentIdCol;
    /** TableColumn for Appointment start date. */
    public TableColumn <Appointment, String> startDateCol;
    /** TableColumn for Appointment start time. */
    public TableColumn <Appointment, LocalTime> startTimeCol;
    /** TableColumn for Appointment end time. */
    public TableColumn <Appointment, LocalTime> endTimeCol;
    /** TableColumn for Appointment title.  */
    public TableColumn <Appointment, Integer> titleCol;
    /** TableColumn for Appointment description. */
    public TableColumn <Appointment, String> descriptionCol;
    /** TableColumn for Appointment location. */
    public TableColumn <Appointment, String> locationCol;
    /** TableColumn for Appointment type. */
    public TableColumn <Appointment, String> typeCol;
    /** TableColumn for Appointment Customer ID. */
    public TableColumn <Appointment, Integer>customerIdCol;
    /** TableColumn for Appointment Contact name. */
    public TableColumn <Appointment, String> contactNameCol;
    /** TableColumn for the User ID of the last User to add or modify the Appointment. */
    public TableColumn <Appointment, Integer> userIdCol;

    /** This method is called by the FXMLLoader.load() call contained in the toAppointmentScreen() method of the UserHomeScreenController class.
     * The method populates the table with all the Appointment data that is in the database. It also populates the
     * Contact combo box used to filter the table by Contact.
     * @param resourceBundle An unreferenced ResourceBundle object passed automatically
     * @param url An unreferenced URL object passed automatically
     * */
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        appointmentIdCol.setCellValueFactory(new PropertyValueFactory<>("appointmentId"));
        startDateCol.setCellValueFactory(new PropertyValueFactory<>("startDateString"));
        startTimeCol.setCellValueFactory(new PropertyValueFactory<>("startTimeString"));
        endTimeCol.setCellValueFactory(new PropertyValueFactory<>("endTimeString"));
        titleCol.setCellValueFactory(new PropertyValueFactory<>("title"));
        descriptionCol.setCellValueFactory(new PropertyValueFactory<>("description"));
        locationCol.setCellValueFactory(new PropertyValueFactory<>("location"));
        typeCol.setCellValueFactory(new PropertyValueFactory<>("type"));
        customerIdCol.setCellValueFactory(new PropertyValueFactory<>("customerId"));
        contactNameCol.setCellValueFactory(new PropertyValueFactory<>("contactName"));
        userIdCol.setCellValueFactory(new PropertyValueFactory<>("userId"));
        //appointmentsTable.setItems(DBAppointments.getAllAppointments());

        contactComboBox.setItems(Salesperson.allSalespersonsList);
    }

    /** /** This method is an event handler on the New Appointment button.
     * When clicked, the button redirects the program to the AddModifyAppointmentScreen and sets the new appointment
     * tracking boolean to true.
     * @param actionEvent Passed from the On Action event listener on the New Appointment button.
     * @throws IOException Exception gets thrown if load() cannot locate the FXML file
     * */
    public void onNewAppointment(ActionEvent actionEvent) throws IOException {
        AddModifyAppointmentScreenController.isNewAppointment = true;

        Parent root = FXMLLoader.load(getClass().getResource("/view/AddModifyAppointment.fxml"));
        Stage stage = (Stage) ((Node) (actionEvent.getSource())).getScene().getWindow();
        Scene scene = new Scene(root, 600, 500);
        stage.setScene(scene);
        stage.setTitle("New Appointment");
    }

    /** /** This method is an event handler on the Update Appointment button.
     * When clicked, the button redirects the program to the AddModifyAppointmentScreen and passes the data from the highlighted
     * Appointment. If no Appointment is selected an alert will notify the user they must select an Appointment.
     * @param actionEvent Passed from the On Action event listener on the Update Appointment button.
     * @throws IOException Exception gets thrown if load() cannot locate the FXML file.
     * */
    public void onUpdateAppointment(ActionEvent actionEvent) throws IOException {

        if (appointmentsTable.getSelectionModel().getSelectedItem() != null ) {
            AddModifyAppointmentScreenController.isNewAppointment = false;
            AddModifyAppointmentScreenController.tempAppointment = appointmentsTable.getSelectionModel().getSelectedItem();

            Parent root = FXMLLoader.load(getClass().getResource("/view/AddModifyAppointment.fxml"));
            Stage stage = (Stage) ((Node) (actionEvent.getSource())).getScene().getWindow();
            Scene scene = new Scene(root, 600, 500);
            stage.setScene(scene);
            stage.setTitle("Update Existing Appointment");
        }
        else  {
            Alert alert1 = new Alert(Alert.AlertType.INFORMATION);
            alert1.setTitle("No Selection Made");
            alert1.setContentText("Please Select an Appointment to Update.");
            alert1.show();
        }
    }

    /** This method is an event handler on the Delete Appointment button.
     * When clicked, an alert will pop up asking the User to verify that they wish to delete the selected Appointment.
     * Upon confirmation, the Appointment will be deleted from the database and the display table
     * updated.
     * @param actionEvent Passed from the On Action event listener on the Delete Appointment button.
     * @throws SQLException Exception gets thrown if the SQL code does not compute properly.
     * */
    public void onDeleteAppointment(ActionEvent actionEvent) throws SQLException {
        Appointment selectedAppointment = appointmentsTable.getSelectionModel().getSelectedItem();
        if (selectedAppointment != null ) {

            Alert alert = new Alert(Alert.AlertType.CONFIRMATION, "Permanently Delete Selected Appointment?");
            Optional<ButtonType> result = alert.showAndWait();

            if(result.isPresent()  && result.get() == ButtonType.OK) {

                int appointmentId = selectedAppointment.getAppointmentId();
                String type = selectedAppointment.getType();
                DBAppointments.deleteAppointment(appointmentId);
                appointmentsTable.setItems(DBAppointments.getEveryAppointment());

                Alert alert1 = new Alert(Alert.AlertType.INFORMATION);
                alert1.setTitle("Deletion Confirmation");
                alert1.setContentText("The Selected Appointment ID#" + appointmentId + " of Type " + type +  " Has Been Deleted");
                alert1.show();
            }
        }
        else  {
            Alert alert1 = new Alert(Alert.AlertType.INFORMATION);
            alert1.setTitle("No Selection Made");
            alert1.setContentText("Please Select an Appointment to Delete.");
            alert1.show();
        }
    }

    /** This method is an event handler on the Monthly Schedule radio button.
     * When clicked, the table will repopulate with only Appointments occurring within the next 30 days.
     * @param actionEvent Passed from the On Action event listener on Monthly Schedule radio button.
     * @throws SQLException Exception gets thrown if the SQL code does not compute properly.
     * */
    public void loadMonthlySchedule(ActionEvent actionEvent) {
        //appointmentsTable.setItems(DBAppointments.getAppointmentsByMonth());
    }

    /** This method is an event handler on the Weekly Schedule radio button.
     * When clicked, the table will repopulate with only Appointments occurring within the next week.
     * @param actionEvent Passed from the On Action event listener on Weekly Schedule radio button.
     * */
    public void loadWeeklySchedule(ActionEvent actionEvent) {
        //appointmentsTable.setItems(DBAppointments.getAppointmentsByWeek());
    }

    /** This method is an event handler on the All Upcoming Appointments radio button.
     * When clicked, the table will repopulate with all Appointments in the database that have yet to occur.
     * @param actionEvent Passed from the On Action event listener on All Upcoming Appointments radio button.
     * */
    public void loadAllAppointments(ActionEvent actionEvent) {
        appointmentsTable.setItems(DBAppointments.getEveryAppointment());
    }

    /** This method is an event handler on the Contact's combo box.
     * When clicked, the table will repopulate with all Appointments associated with the selected Contact.
     * @param actionEvent Passed from the On Action event listener on Contacts combo box.
     * */
    public void onSelectContact(ActionEvent actionEvent) {
        Salesperson selectedContact = contactComboBox.getSelectionModel().getSelectedItem();
        //appointmentsTable.setItems(DBAppointments.getAppointmentsByContact(selectedContact));
    }

    /** This method is an event handler for the Back button that sends the program back to the UserHomeScreen.
     * The method loads the FXML document for the UserHomeScreen, passes that to a new scene and then sets the
     * stage with the new scene.
     * @param actionEvent Passed from the On Action event listener on the Back button.
     * @throws IOException The FXMLLoader.load() call will throw this exception if the FXML document can't be found.
     */
    public void toUserHome(ActionEvent actionEvent) throws IOException {
        Parent root = FXMLLoader.load(getClass().getResource("/view/UserHomeScreen.fxml"));
        Stage stage = (Stage) ((Node) (actionEvent.getSource())).getScene().getWindow();
        Scene scene = new Scene(root, 500, 500);
        stage.setScene(scene);
        stage.setTitle("User Home Screen");
    }
}
