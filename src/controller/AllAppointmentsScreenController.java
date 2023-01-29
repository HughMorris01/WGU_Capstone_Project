package controller;

import database.DBAppointments;
import javafx.collections.ObservableList;
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
import java.io.IOException;
import java.net.URL;
import java.sql.SQLException;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Objects;
import java.util.Optional;
import java.util.ResourceBundle;

/** This is the controller class for the AllAppointmentsScreen.fxml document and is not meant to be instantiated.
 *  This class populates the data for the Appointments table, as well as provide the functionality for the buttons,
 *  radio buttons and combo boxes on the screen.
 * @author Gregory Farrell
 * @version 1.1
 * */
public class AllAppointmentsScreenController implements Initializable {
    /** Text field used to search the Appointment records by ID, customer name, title or customerID */
    public TextField searchTextField;
    /** TableView populated Client records from the database. */
    public TableView<Appointment> appointmentsTable;
    /** TableColumn for the AppointmentID. */
    public TableColumn<Appointment, Integer> appointmentIdCol;
    /** TableColumn for the Appointment date. */
    public TableColumn<Appointment, String> dateCol;
    /** TableColumn for the Appointment's start time. */
    public TableColumn<Appointment, String> startTimeStringCol;
    /** TableColumn for the Appointment's end time. */
    public TableColumn<Appointment, String> endTimeStringCol;
    /** TableColumn for the Appointment's associated salesperson. */
    public TableColumn<Appointment, String> salespersonCol;
    /** TableColumn for the Appointment's title */
    public TableColumn<Appointment, String> titleCol;
    /** TableColumn for the Appointment's type */
    public TableColumn<Appointment, String> typeCol;
    /** TableColumn for the Appointment's region. */
    public TableColumn<Appointment, String> regionCol;
    /** TableColumn for the Appointment's state abbreviation. */
    public TableColumn<Appointment, String> stateAbbreviationCol;
    /** TableColumn for the Appointment's associated clientId. */
    public TableColumn<Appointment, Integer> clientIdCol;
    /** TableColumn for the Appointment's associated client's name. */
    public TableColumn<Appointment, String> clientNameCol;
    /** Toggle group for the radio buttons that load the appointments when selected. */
    public ToggleGroup appointmentsRadioGroup;
    /** Date picker for the start of the custom date range. */
    public DatePicker startDatePicker;
    /** Date picker for the end of the custom date range. */
    public DatePicker endDatePicker;
    public RadioButton allAppointmentsRadioButton;
    public RadioButton upcomingAppointmentsRadioButton;
    public RadioButton completedAppointmentsRadioButton;

    /** This method is called by the FXMLLoader.load() call contained in the toAllAppointmentsScreen() method of the
     * AdminHomeScreenController class. The method initially populates the table with every Appointment that is in the database.
     * @param resourceBundle An unreferenced ResourceBundle object passed automatically
     * @param url An unreferenced URL object passed automatically
     */
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        appointmentIdCol.setCellValueFactory(new PropertyValueFactory<>("appointmentId"));
        appointmentIdCol.setStyle("-fx-alignment: CENTER;");
        dateCol.setCellValueFactory(new PropertyValueFactory<>("startDateString"));
        dateCol.setStyle("-fx-alignment: CENTER;");
        salespersonCol.setCellValueFactory(new PropertyValueFactory<>("salespersonFullName"));
        salespersonCol.setStyle("-fx-alignment: CENTER;");
        startTimeStringCol.setCellValueFactory(new PropertyValueFactory<>("startTimeString"));
        startTimeStringCol.setStyle("-fx-alignment: CENTER;");
        endTimeStringCol.setCellValueFactory(new PropertyValueFactory<>("endTimeString"));
        endTimeStringCol.setStyle("-fx-alignment: CENTER;");
        titleCol.setCellValueFactory(new PropertyValueFactory<>("title"));
        titleCol.setStyle("-fx-alignment: CENTER;");
        typeCol.setCellValueFactory(new PropertyValueFactory<>("type"));
        typeCol.setStyle("-fx-alignment: CENTER;");
        regionCol.setCellValueFactory(new PropertyValueFactory<>("regionName"));
        regionCol.setStyle("-fx-alignment: CENTER;");
        stateAbbreviationCol.setCellValueFactory(new PropertyValueFactory<>("stateAbbreviation"));
        stateAbbreviationCol.setStyle("-fx-alignment: CENTER;");
        clientIdCol.setCellValueFactory(new PropertyValueFactory<>("clientId"));
        clientIdCol.setStyle("-fx-alignment: CENTER;");
        clientNameCol.setCellValueFactory(new PropertyValueFactory<>("clientName"));
        clientNameCol.setStyle("-fx-alignment: CENTER;");

        appointmentsTable.setItems(Appointment.allUpcomingAppointmentsList);

    }

    /** This method is an event handler on the searchTextField.
     * Search function searches the corresponding Appointment List for matches containing the appointmentID, customerID,
     * title or customerName.
     * @param actionEvent Passed from the On Action event listener on the text field.
     */
    public void searchAppointments(ActionEvent actionEvent) {
        try {
            String searchString = searchTextField.getText();
            ObservableList<Appointment> appointmentsFoundList = Appointment.searchAppointments(searchString);

            if (appointmentsFoundList.size() == 0) {
                int searchInt = Integer.parseInt(searchTextField.getText());
                appointmentsFoundList = Appointment.searchAppointments(searchInt);
            }
            if (appointmentsFoundList.isEmpty()) {
                searchTextField.setText("");
                Alert alert1 = new Alert(Alert.AlertType.INFORMATION);
                alert1.setTitle("No Results Found");
                alert1.setContentText("There are no Appointments matching that search criteria.");
                alert1.show();
                return;
            }
            appointmentsTable.setItems(appointmentsFoundList);
            searchTextField.setText("");
        } catch (NumberFormatException exception) {
            searchTextField.setText("");
            Alert alert1 = new Alert(Alert.AlertType.INFORMATION);
            alert1.setTitle("No Results Found");
            alert1.setContentText("There are no Appointments matching that search criteria.");
            alert1.show();
        }
    }

    /** This method is an event handler for the Back button that sends the program back to the AdminHomeScreen.
     * The method loads the FXML document for the AdminHomeScreen, passes that to a new scene and then sets the
     * stage with the new scene.
     * @param actionEvent Passed from the On Action event listener on the Back button.
     * @throws IOException The FXMLLoader.load() call will throw this exception if the FXML document can't be found.
     */
    public void toAdminHomeScreen(ActionEvent actionEvent) throws IOException {
        Parent root = FXMLLoader.load(Objects.requireNonNull(getClass().getResource("/view/AdminHomeScreen.fxml")));
        Stage stage = (Stage) ((Node) (actionEvent.getSource())).getScene().getWindow();
        Scene scene = new Scene(root, 1000, 500);
        stage.setScene(scene);
        stage.setTitle("Administrator Home Screen");
    }

    /** This method loads the appointmentsTable with both completed and scheduled appointments.
     * @param actionEvent Passed from the On Action event listener on the radio button. */
    public void loadAllAppointments(ActionEvent actionEvent) {
        appointmentsTable.setItems(Appointment.allAppointmentsList);
        startDatePicker.setValue(null);
        endDatePicker.setValue(null);
    }

    /** This method loads the appointmentsTable with upcoming scheduled appointments.
     * @param actionEvent Passed from the On Action event listener on the radio button. */
    public void loadUpcomingAppointments(ActionEvent actionEvent) {
        appointmentsTable.setItems(Appointment.allUpcomingAppointmentsList);
        startDatePicker.setValue(null);
        endDatePicker.setValue(null);
    }

    /** This method loads the appointmentsTable with appointments that have already been completed.
     * @param actionEvent Passed from the On Action event listener on the radio button. */
    public void loadCompletedAppointments(ActionEvent actionEvent) {
        appointmentsTable.setItems(Appointment.allCompletedAppointmentsList);
        startDatePicker.setValue(null);
        endDatePicker.setValue(null);
    }

    /** This method is an event handler on the Create New Appointment button.
     * When clicked, the button redirects the program to the AdminCreateEditAppointmentScreen.
     * @param actionEvent Passed from the On Action event listener on the Create New Appointment button.
     * @throws IOException Exception gets thrown if load() cannot locate the FXML file
     */
    public void toAdminCreateAppointmentScreen(ActionEvent actionEvent) throws IOException {
        AdminCreateEditAppointmentScreenController.labelBoolean = true;

        Parent root = FXMLLoader.load(Objects.requireNonNull(getClass().getResource("/view/AdminCreateEditAppointmentScreen.fxml")));
        Stage stage = (Stage) ((Node) (actionEvent.getSource())).getScene().getWindow();
        Scene scene = new Scene(root, 600, 500);
        stage.setScene(scene);
        stage.setTitle("Create Appointment Screen");
    }

    /** This method is an event handler on the Edit Appointment button.
     * When clicked, the button redirects the program to the AdminCreateEditAppointmentScreen.
     * @param actionEvent Passed from the On Action event listener on the Edit Appointment button.
     * @throws IOException Exception gets thrown if load() cannot locate the FXML file
     */
    public void toAdminEditAppointmentScreen(ActionEvent actionEvent) throws IOException {
        if(appointmentsTable.getSelectionModel().getSelectedItem() != null)  {
            if(appointmentsTable.getSelectionModel().getSelectedItem().getStart().isBefore(LocalDateTime.now())) {
                Alert alert1 = new Alert(Alert.AlertType.INFORMATION);
                alert1.setTitle("Appointment Already Completed");
                alert1.setContentText("An Appointment that has already been completed cannot be edited.");
                alert1.show();
                return;
            }
            AdminCreateEditAppointmentScreenController.setTempAppointment(appointmentsTable.getSelectionModel().getSelectedItem());
        }
        else {
            Alert alert1 = new Alert(Alert.AlertType.INFORMATION);
            alert1.setTitle("No Selection Made");
            alert1.setContentText("Please select an Appointment to update.");
            alert1.show();
            return;
        }

        Parent root = FXMLLoader.load(Objects.requireNonNull(getClass().getResource("/view/AdminCreateEditAppointmentScreen.fxml")));
        Stage stage = (Stage) ((Node) (actionEvent.getSource())).getScene().getWindow();
        Scene scene = new Scene(root, 600, 500);
        stage.setScene(scene);
        stage.setTitle("Edit Appointment Screen");
    }

    /** This method is an event handler on the "Delete Appointment" button.
     * When clicked, the button prompts the user to confirm they wish to delete the selected appointment and once confirmed,
     * the appointment is deleted from the database.
     * @param actionEvent Passed from the On Action event listener on the "Delete Appointment" button.
     */
    public void deleteAppointment(ActionEvent actionEvent) throws SQLException {
        Appointment selectedAppointment = appointmentsTable.getSelectionModel().getSelectedItem();

        if (selectedAppointment == null) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("No Appointment Selected");
            alert.setContentText("Please select the appointment to be deleted");
            alert.show();
        }
        if(appointmentsTable.getSelectionModel().getSelectedItem().getStart().isBefore(LocalDateTime.now())) {
            Alert alert1 = new Alert(Alert.AlertType.INFORMATION);
            alert1.setTitle("Appointment Already Completed");
            alert1.setContentText("An Appointment that has already been completed cannot be deleted.");
            alert1.show();
        }
        else {
            Alert alert = new Alert(Alert.AlertType.CONFIRMATION, "Permanently delete selected appointment?");
            Optional<ButtonType> result = alert.showAndWait();

            if (result.isPresent() && result.get() == ButtonType.OK) {
                assert selectedAppointment != null;
                int appointmentId = selectedAppointment.getAppointmentId();
                DBAppointments.deleteAppointment(appointmentId);

                appointmentsTable.setItems(DBAppointments.getEveryAppointment());
                allAppointmentsRadioButton.setSelected(true);


                Alert alert1 = new Alert(Alert.AlertType.INFORMATION);
                alert1.setTitle("Deletion Confirmation");
                alert1.setContentText("The selected appointment has been deleted");
                alert1.show();
            }
        }
    }

    /** This method loads the appointmentsTable with appointments that fall within the specified custom date range.
     * @param actionEvent Passed from the On Action event listener on the submit button. */
    public void customDateRange(ActionEvent actionEvent) {
        ObservableList<Appointment> customDateRangeList;
        LocalDate localStartDate = startDatePicker.getValue();
        if (localStartDate == null) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Blank Date");
            alert.setContentText("An appointment start date must be selected. ");
            alert.show();
            return;
        }
        LocalDate localEndDate = endDatePicker.getValue();
        if (localEndDate == null) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Blank Date");
            alert.setContentText("An appointment end date must be selected. ");
            alert.show();
            return;
        }
        if(localEndDate.isBefore(localStartDate)) {
                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.setTitle("Blank Date");
                alert.setContentText("End date must be after the start date. ");
                alert.show();
                return;
        }
        customDateRangeList = DBAppointments.getCustomDateRangeList(localStartDate, localEndDate.plusDays(1));
        appointmentsTable.setItems(customDateRangeList);
    }

    /** This method is an event handler on the SignOut button.
     * When clicked, the button redirects the program to the original Login Screen
     * @param actionEvent Passed from the On Action event listener on the SignOut button.
     * @throws IOException Exception gets thrown if load() cannot locate the FXML file
     */
    public void toSignOut(ActionEvent actionEvent) throws IOException {
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION, "Do you wish to log out?");
        Optional<ButtonType> result = alert.showAndWait();

        if (result.isPresent() && result.get() == ButtonType.OK) {
            Parent root = FXMLLoader.load(Objects.requireNonNull(getClass().getResource("/view/LoginScreen.fxml")));
            Stage stage = (Stage) ((Node) (actionEvent.getSource())).getScene().getWindow();
            Scene scene = new Scene(root, 600, 400);
            stage.setScene(scene);
            stage.setTitle("Login Screen");
        }
    }
}
