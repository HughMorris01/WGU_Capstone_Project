package controller;

import database.DBAppointments;
import database.DBContacts;
import database.DBCustomers;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.stage.Stage;
import model.Appointment;
import model.Contact;
import model.Customer;

import java.io.IOException;
import java.net.URL;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.time.*;
import java.time.format.DateTimeFormatter;
import java.util.ResourceBundle;
import java.util.TimeZone;

/** This is the controller class for the AddModifyAppointmentScreen.fxml document and is not meant to be instantiated.
 * The class will either load the form with Appointment data passed from the previous AppointmentScreen or will begin as blank
 * text fields and combo boxes for the User to enter the Appointment data into.
 * @author Gregory Farrell
 * @version 1.0
 * */
public class AddModifyAppointmentScreenController implements Initializable {
    /** Label used to display if it will be a new or existing Appointment. */
    public Label screenLabel;
    /** Button used to gather the entered information and update the database. */
    public Button submitButton;
    /** Boolean used to track if it will be a new or existing Appointment. */
    public static boolean isNewAppointment = false;
    /** ComboBox populated with the Contacts from the database. */
    public ComboBox<Contact> contactComboBox;
    /** ComboBox populated with the Customers from the database. */
    public ComboBox<Customer> customerComboBox;
    /** Disabled TextField that displays the Appointment ID, if appropriate. */
    public TextField appointmentIdTextField;
    /** Disabled TextField that displays the User ID. */
    public TextField userIdTextField;
    /** DatePicker for selecting the date. */
    public DatePicker datePickerComboBox;
    /** ComboBox populated with potential starting times. */
    public ComboBox<String> startTimeComboBox;
    /** ComboBox populated with potential ending times. */
    public ComboBox<String> endTimeComboBox;
    /** TextField for entering the Appointment title. */
    public TextField titleTextField;
    /** Disabled TextField for entering the Appointment location. */
    public TextField locationTextField;
    /** TextField for entering the Appointment description. */
    public TextField descriptionTextField;
    /** ComboBox populated with Appointment types. */
    public ComboBox<String> typeComboBox;
    /** Static member used to pass a selected Appointment from the previous screen. */
    public static Appointment tempAppointment = null;


    /** This method is called by the FXMLLoader.load() call contained in either the onNewAppointment() or onUpdateAppointment() methods of the
     * AppointmentScreenController class.
     * If modifying an Appointment record, the selected Appointment's data is passed from the previous screen into the text fields
     * and combo boxes, otherwise they load blank.
     * @param resourceBundle An unreferenced ResourceBundle object passed automatically
     * @param url An unreferenced URL object passed automatically
     * */
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        if (isNewAppointment) { screenLabel.setText("Enter Appointment Information");
        submitButton.setText("Confirm Appointment");}
        else {screenLabel.setText("Update Existing Appointment");
        submitButton.setText("Save Changes");}

        ObservableList<String> types = FXCollections.observableArrayList();
        types.add("In-Person");
        types.add("Remote");
        types.add("Group Session");

        customerComboBox.setItems(DBCustomers.getAllCustomers());
        contactComboBox.setItems(DBContacts.getAllContacts());
        typeComboBox.setItems(types);
        userIdTextField.setText(Integer.toString(LoginScreenController.getUser().getUserId()));

        if (tempAppointment != null) {
            appointmentIdTextField.setText(String.valueOf(tempAppointment.getAppointmentId()));
            customerComboBox.setValue(tempAppointment.getCustomer());
            datePickerComboBox.setValue(tempAppointment.getStart().toLocalDate());
            startTimeComboBox.setValue(tempAppointment.getStartTimeString());
            onStartTime();
            endTimeComboBox.setValue(tempAppointment.getEndTimeString());
            titleTextField.setText(tempAppointment.getTitle());
            descriptionTextField.setText(tempAppointment.getDescription());
            locationTextField.setText(tempAppointment.getLocation());
            typeComboBox.setValue(tempAppointment.getType());
            contactComboBox.setValue(tempAppointment.getContact());
        }
        else {
            appointmentIdTextField.setText(null);
            customerComboBox.setValue(null);
            datePickerComboBox.setValue(null);
            startTimeComboBox.setValue(null);
            endTimeComboBox.setValue(null);
            endTimeComboBox.setDisable(true);
            titleTextField.setText(null);
            descriptionTextField.setText(null);
            locationTextField.setText(null);
            typeComboBox.setValue(null);
            contactComboBox.setValue(null);
        }

        ObservableList<String> startTimes = FXCollections.observableArrayList();
        for(int i = 0; i < 56; i++) {
            LocalTime lt = LocalTime.of(8,0);
            lt = lt.plusMinutes(15 * i);
            String t1 = lt.format(DateTimeFormatter.ofPattern("h:mm a"));
            startTimes.add(t1);
        }
        startTimeComboBox.setItems(startTimes);
    }

    /** This method is an event handler for the Customer combo box.
     * Upon selection of a Customer, the disabled location text field is populated automatically with the Customer's location.
     * @param actionEvent Passed from the On Action event listener on the Customer combo box.
     */
    public void onCustomerSelect(ActionEvent actionEvent) {
        Customer selectedCustomer = customerComboBox.getSelectionModel().getSelectedItem();
        locationTextField.setText(selectedCustomer.getDivision().getDivisionName());
    }

    /** This method is an event handler for the Start Time combo box.
     * The method loads the data for End Time combo box so that no end times are entered that are not after the start time.
     * @param actionEvent Passed from the On Action event listener on the Start Time combo box.
     */
    public void onStartTime(ActionEvent actionEvent) {
        String startTimeString = startTimeComboBox.getSelectionModel().getSelectedItem();
        DateTimeFormatter timeFormatter = DateTimeFormatter.ofPattern("h:mm a");
        LocalTime localStartTime = LocalTime.from(timeFormatter.parse(startTimeString));
        ObservableList<String> endTimes = FXCollections.observableArrayList();
        while(localStartTime.isBefore(LocalTime.of(22,0))) {
            localStartTime = localStartTime.plusMinutes(15);
            String t1 = localStartTime.format(DateTimeFormatter.ofPattern("h:mm a"));
            endTimes.add(t1);
        }
        endTimeComboBox.setItems(endTimes);
        endTimeComboBox.setValue(null);
        endTimeComboBox.setDisable(false);
    }

    /** This overloaded method loads the End Times combo box with potential end times, based on the start time that was
     * passed from the selected Appointment. */
    public void onStartTime() {
        String start = startTimeComboBox.getSelectionModel().getSelectedItem();
        DateTimeFormatter timeFormatter = DateTimeFormatter.ofPattern("h:mm a");
        LocalTime lt = LocalTime.from(timeFormatter.parse(start));

        ObservableList<String> endTimes = FXCollections.observableArrayList();
        while(lt.isBefore(LocalTime.of(22,0))) {
            lt = lt.plusMinutes(15);
            String t1 = lt.format(DateTimeFormatter.ofPattern("h:mm a"));
            endTimes.add(t1);
        }
        endTimeComboBox.setItems(endTimes);
        endTimeComboBox.setValue(null);
        endTimeComboBox.setDisable(false);
    }

    /** This method is an event handler for the Save button that saves the entered Appointment data to the database and redirects
     * the application back to the AppointmentScreen.
     * The method gathers the data from the form, executes the SQL to update the database and then loads the FXML document
     * for the AppointmentScreen.
     * @param actionEvent Passed from the On Action event listener on the Save button.
     * @throws IOException The FXMLLoader.load() call will throw this exception if the FXML document can't be found.
     * @throws SQLException This execution will be thrown if the SQL does not execute properly.
     */
    public void onSubmit(ActionEvent actionEvent) throws IOException, SQLException {
        DateTimeFormatter timeFormatter = DateTimeFormatter.ofPattern("h:mm a");
        ObservableList<Appointment> localCustomerAppointments;
        if (appointmentIdTextField.getText() == null) {
            Customer selectedCustomer = customerComboBox.getSelectionModel().getSelectedItem();
            if (selectedCustomer == null) {
                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.setTitle("Blank Field");
                alert.setContentText("A Customer Name Must Be Selected");
                alert.show();
                return;
            }
            int customerId = selectedCustomer.getCustomerId();
            LocalDate localDate = datePickerComboBox.getValue();
            if (localDate == null) {
                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.setTitle("Blank Field");
                alert.setContentText("An Appointment Date Must be Selected.");
                alert.show();
                return;
            }
            String localTimeStartString = startTimeComboBox.getSelectionModel().getSelectedItem();
            if (localTimeStartString == null) {
                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.setTitle("Blank Field");
                alert.setContentText("A Start Time Must be Selected.");
                alert.show();
                return;
            }
            String localTimeEndString = endTimeComboBox.getSelectionModel().getSelectedItem();
            if (localTimeEndString == null) {
                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.setTitle("Blank Field");
                alert.setContentText("An End Time Must be Selected.");
                alert.show();
                return;
            }
            LocalTime localTimeStart = LocalTime.from(timeFormatter.parse(localTimeStartString));
            LocalDateTime localDateTimeStart = LocalDateTime.of(localDate, localTimeStart);
            ZoneId localZoneId = ZoneId.of(TimeZone.getDefault().getID());
            ZonedDateTime zonedDateTimeStart = ZonedDateTime.of(localDateTimeStart, localZoneId);
            ZonedDateTime businessHoursStart = ZonedDateTime.of(localDate, LocalTime.of(8, 00), ZoneId.of("US/Eastern"));
            if((zonedDateTimeStart.isBefore(businessHoursStart)) || (zonedDateTimeStart.getDayOfWeek() == DayOfWeek.SATURDAY) || (zonedDateTimeStart.getDayOfWeek() == DayOfWeek.SUNDAY)) {
                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.setTitle("Outside Business Hours");
                alert.setContentText("Please Select a Start Time that is After 8:00am EST, M-F");
                alert.show();
                return;
            }
            Timestamp timeStampStart = Timestamp.valueOf(localDateTimeStart);
            LocalTime localTimeEnd = LocalTime.from(timeFormatter.parse(localTimeEndString));
            LocalDateTime localDateTimeEnd = LocalDateTime.of(localDate, localTimeEnd);
            ZonedDateTime zonedDateTimeEnd = ZonedDateTime.of(localDateTimeEnd, localZoneId);
            ZonedDateTime businessHoursEnd = ZonedDateTime.of(localDate, LocalTime.of(22, 0), ZoneId.of("US/Eastern"));
            if(zonedDateTimeEnd.isAfter(businessHoursEnd)) {
                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.setTitle("Outside Business Hours");
                alert.setContentText("Please Select an End Time that is Before 10:00pm EST");
                alert.show();
                return;
            }
            Timestamp timeStampEnd = Timestamp.valueOf(localDateTimeEnd);
            localCustomerAppointments = DBAppointments.getAppointmentsByCustomerId(customerId);
            for(Appointment ap : localCustomerAppointments) {
                if((ap.getStart().isBefore(localDateTimeStart)) && (ap.getEnd().isAfter(localDateTimeStart))) {
                    Alert alert = new Alert(Alert.AlertType.ERROR);
                    alert.setTitle("Conflicting Appointment");
                    alert.setContentText("The Customer Already has An Appointment Scheduled in this Window.");
                    alert.show();
                    return;
                }
                if(ap.getStart().equals(localDateTimeStart)) {
                    Alert alert = new Alert(Alert.AlertType.ERROR);
                    alert.setTitle("Conflicting Appointment");
                    alert.setContentText("The Customer Already has An Appointment Scheduled in this Window.");
                    alert.show();
                    return;
                }
                if((ap.getStart().isAfter(localDateTimeStart)) && (ap.getStart().isBefore(localDateTimeEnd))) {
                    Alert alert = new Alert(Alert.AlertType.ERROR);
                    alert.setTitle("Conflicting Appointment");
                    alert.setContentText("The Customer Already has An Appointment Scheduled in this Window.");
                    alert.show();
                    return;
                }

            }
            String title = titleTextField.getText();
            if (title == null) {
                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.setTitle("Blank Field");
                alert.setContentText("An Title Must be Entered.");
                alert.show();
                return;
            }
            String description = descriptionTextField.getText();
            if (description == null) {
                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.setTitle("Blank Field");
                alert.setContentText("An Description Must be Entered.");
                alert.show();
                return;
            }
            String location = locationTextField.getText();
            String type = typeComboBox.getSelectionModel().getSelectedItem();
            if (type == null) {
                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.setTitle("Blank Field");
                alert.setContentText("An Type Must be Entered.");
                alert.show();
                return;
            }
            Contact contact = contactComboBox.getSelectionModel().getSelectedItem();
            if (contact == null) {
                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.setTitle("Blank Field");
                alert.setContentText("An Contact Must be Selected.");
                alert.show();
                return;
            }
            int contactId = contact.getId();
            int userId = Integer.parseInt(userIdTextField.getText());

            DBAppointments.insertAppointment(title, description, location, type, timeStampStart, timeStampEnd, customerId, contactId, userId);

            Parent root = FXMLLoader.load(getClass().getResource("/view/AppointmentScreen.fxml"));
            Stage stage = (Stage) ((Node) (actionEvent.getSource())).getScene().getWindow();
            Scene scene = new Scene(root, 1050, 450);
            stage.setScene(scene);
            stage.setTitle("Appointment Screen");
        }
        else {
            int appointmentId = Integer.parseInt(appointmentIdTextField.getText());
            Customer selectedCustomer = customerComboBox.getSelectionModel().getSelectedItem();
            if (selectedCustomer == null) {
                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.setTitle("Blank Field");
                alert.setContentText("A Customer Name Must Be Selected");
                alert.show();
                return;
            }
            int customerId = selectedCustomer.getCustomerId();
            LocalDate localDate = datePickerComboBox.getValue();
            if (localDate == null) {
                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.setTitle("Blank Field");
                alert.setContentText("An Appointment Date Must be Selected.");
                alert.show();
                return;
            }
            String localTimeStartString = startTimeComboBox.getSelectionModel().getSelectedItem();
            String localTimeEndString = endTimeComboBox.getSelectionModel().getSelectedItem();
            if (localTimeStartString == null) {
                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.setTitle("Blank Field");
                alert.setContentText("A Start Time Must be Selected.");
                alert.show();
                return;
            }
            if (localTimeEndString == null) {
                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.setTitle("Blank Field");
                alert.setContentText("An End Time Must be Selected.");
                alert.show();
                return;
            }
            LocalTime localTimeStart = LocalTime.from(timeFormatter.parse(localTimeStartString));
            LocalDateTime localDateTimeStart = LocalDateTime.of(localDate, localTimeStart);
            ZoneId localZoneId = ZoneId.of(TimeZone.getDefault().getID());
            ZonedDateTime zonedDateTimeStart = ZonedDateTime.of(localDateTimeStart, localZoneId);
            ZonedDateTime businessHoursStart = ZonedDateTime.of(localDate, LocalTime.of(8, 00), ZoneId.of("US/Eastern"));
            if((zonedDateTimeStart.isBefore(businessHoursStart)) || (zonedDateTimeStart.getDayOfWeek() == DayOfWeek.SATURDAY) || (zonedDateTimeStart.getDayOfWeek() == DayOfWeek.SUNDAY)) {
                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.setTitle("Outside Business Hours");
                alert.setContentText("Please Select a Start Time that is After 8:00am EST, M-F");
                alert.show();
                return;
            }
            Timestamp timeStampStart = Timestamp.valueOf(localDateTimeStart);
            LocalTime localTimeEnd = LocalTime.from(timeFormatter.parse(localTimeEndString));
            LocalDateTime localDateTimeEnd = LocalDateTime.of(localDate, localTimeEnd);
            ZonedDateTime zonedDateTimeEnd = ZonedDateTime.of(localDateTimeEnd, localZoneId);
            ZonedDateTime businessHoursEnd = ZonedDateTime.of(localDate, LocalTime.of(22, 0), ZoneId.of("US/Eastern"));
            if(zonedDateTimeEnd.isAfter(businessHoursEnd)) {
                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.setTitle("Outside Business Hours");
                alert.setContentText("Please Select an End Time that is Before 10:00pm EST");
                alert.show();
                return;
            }
            Timestamp timeStampEnd = Timestamp.valueOf(localDateTimeEnd);
            localCustomerAppointments = DBAppointments.getAppointmentsByCustomerId(customerId);
            for(Appointment ap : localCustomerAppointments) {
                if(ap.getAppointmentId() == appointmentId) {
                    //localCustomerAppointments.remove(ap);
                    continue;
                }
                if((ap.getStart().isBefore(localDateTimeStart)) && (ap.getEnd().isAfter(localDateTimeStart))) {
                    Alert alert = new Alert(Alert.AlertType.ERROR);
                    alert.setTitle("Conflicting Appointment");
                    alert.setContentText("The Customer Already has An Appointment Scheduled in this Window.");
                    alert.show();
                    return;
                }
                if(ap.getStart().equals(localDateTimeStart)) {
                    Alert alert = new Alert(Alert.AlertType.ERROR);
                    alert.setTitle("Conflicting Appointment");
                    alert.setContentText("The Customer Already has An Appointment Scheduled in this Window.");
                    alert.show();
                    return;
                }
                if((ap.getStart().isAfter(localDateTimeStart)) && (ap.getStart().isBefore(localDateTimeEnd))) {
                    Alert alert = new Alert(Alert.AlertType.ERROR);
                    alert.setTitle("Conflicting Appointment");
                    alert.setContentText("The Customer Already has An Appointment Scheduled in this Window.");
                    alert.show();
                    return;
                }
            }
            String title = titleTextField.getText();
            if (title == "") {
                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.setTitle("Blank Field");
                alert.setContentText("An Title Must be Entered.");
                alert.show();
                return;
            }
            String description = descriptionTextField.getText();
            if (description == "") {
                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.setTitle("Blank Field");
                alert.setContentText("An Description Must be Entered.");
                alert.show();
                return;
            }
            String location = locationTextField.getText();
            String type = typeComboBox.getSelectionModel().getSelectedItem();
            if (type == "") {
                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.setTitle("Blank Field");
                alert.setContentText("An Type Must be Entered.");
                alert.show();
                return;
            }
            Contact contact = contactComboBox.getSelectionModel().getSelectedItem();
            if (contact == null) {
                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.setTitle("Blank Field");
                alert.setContentText("An Contact Must be Selected.");
                alert.show();
                return;
            }
            int contactId = contact.getId();
            int userId = Integer.parseInt(userIdTextField.getText());

            DBAppointments.updateAppointment(title, description, location, type, timeStampStart, timeStampEnd, customerId, contactId, userId, appointmentId);

            tempAppointment = null;

            Parent root = FXMLLoader.load(getClass().getResource("/view/AppointmentScreen.fxml"));
            Stage stage = (Stage) ((Node) (actionEvent.getSource())).getScene().getWindow();
            Scene scene = new Scene(root, 1050, 450);
            stage.setScene(scene);
            stage.setTitle("Appointment Screen");
        }
    }

    /** This method is an event handler for the Back button that sends the program back to the AppointmentScreen.
     * The method loads the FXML document for the AppointmentScreen, passes that to a new scene and then sets the
     * stage with the new scene.
     * @param actionEvent Passed from the On Action event listener on the Back button.
     * @throws IOException The FXMLLoader.load() call will throw this exception if the FXML document can't be found.
     */
    public void toBack(ActionEvent actionEvent) throws IOException {
        tempAppointment = null;

        Parent root = FXMLLoader.load(getClass().getResource("/view/AppointmentScreen.fxml"));
        Stage stage = (Stage) ((Node) (actionEvent.getSource())).getScene().getWindow();
        Scene scene = new Scene(root, 1050, 450);
        stage.setScene(scene);
        stage.setTitle("Appointment Screen");
    }
}
