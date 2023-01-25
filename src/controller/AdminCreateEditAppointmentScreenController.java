package controller;

import database.DBAppointments;
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
import model.*;

import java.io.IOException;
import java.net.URL;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.time.*;
import java.time.format.DateTimeFormatter;
import java.util.Objects;
import java.util.Optional;
import java.util.ResourceBundle;

/** This is the controller class for the AdminCreateEditAppointmentScreenController.fxml document and is not meant to be instantiated.
 * The class will either load the form with Appointment data passed from the previous screen or will begin as blank
 * text fields and combo boxes for the User to enter the Appointment data into.
 * @author Gregory Farrell
 * @version 1.1
 * */
public class AdminCreateEditAppointmentScreenController implements Initializable {

    /** Static Appointment member used to receive an Appointment object passed from the allAppointmentsScreen. */
    private static Appointment tempAppointment = null;
    /** Static boolean used to toggle the heading label on the screen. */
    public static boolean labelBoolean = false;
    /** Heading Label that adjusts depending on if it is a new or existing appointment being entered. */
    public Label screenLabel;
    /** Text field to display the appointmentId. */
    public TextField appointmentIdTextField;
    /** Combo box used to select the appointment state. */
    public ComboBox<State> stateComboBox;
    /** Combo box used to select the appointment region. */
    public ComboBox<Region> regionComboBox;
    /** Combo box used to select the appointment salesperson. */
    public ComboBox<Salesperson> salespersonComboBox;
    /** Date picker used to select the appointment date. */
    public DatePicker datePicker;
    /** Combo box used to select the appointment start time. */
    public ComboBox<String> startTimeComboBox;
    /** Combo box used to select the appointment end time. */
    public ComboBox<String> endTimeComboBox;
    /** Text field to display the appointment title. */
    public TextField titleTextField;
    /** Combo box used to select the appointment type. */
    public ComboBox<String> typeComboBox;
    /** Combo box used to select the appointment customer. */
    public ComboBox<Client> customerComboBox;
    /** Button used to reset the region based information back to original state. */
    public Button resetButton;
    /** Button used to take user to the create new customer screen. */
    public Button createNewCustomerButton;

    /** This method initializes all form fields to either correspond with the appointment passed from the previos screen
     * or sets them to a blank state to represent a new appointment.
     * @param resourceBundle An unreferenced ResourceBundle object passed automatically.
     * @param url An unreferenced URL object passed automatically. */
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        if (!labelBoolean) {
            screenLabel.setText("Edit Appointment Info");
        } else {
            screenLabel.setText("Create New Appointment");
        }
        regionComboBox.setItems(Region.allRegionsList);
        ObservableList<String> types = FXCollections.observableArrayList();
        types.add("In-Person");
        types.add("Virtual");
        types.add("Group Session");
        types.add("Seminar");
        typeComboBox.setItems(types);
        if (tempAppointment != null) {
            resetButton.setVisible(false);
            createNewCustomerButton.setVisible(false);

            appointmentIdTextField.setText(String.valueOf(tempAppointment.getAppointmentId()));
            stateComboBox.setValue(tempAppointment.getState());
            regionComboBox.setValue(tempAppointment.getRegion());
            salespersonComboBox.setValue(tempAppointment.getSalesperson());
            ObservableList<Salesperson> regionSalespersons = FXCollections.observableArrayList();
            for (Salesperson salesperson : Salesperson.allSalespersonsList) {
                if (salesperson.getRegionId() == tempAppointment.getRegionId()) {
                    regionSalespersons.add(salesperson);
                }
            }
            salespersonComboBox.setItems(regionSalespersons);
            datePicker.setValue(tempAppointment.getStart().toLocalDate());
            startTimeComboBox.setValue(tempAppointment.getStartTimeString());
            ObservableList<String> startTimes = FXCollections.observableArrayList();
            for(int i = 0; i < 56; i++) {
                LocalTime lt = LocalTime.of(8,0);
                lt = lt.plusMinutes(15 * i);
                String t1 = lt.format(DateTimeFormatter.ofPattern("h:mm a"));
                startTimes.add(t1);
            }
            startTimeComboBox.setItems(startTimes);
            setEndTimes();
            endTimeComboBox.setValue(tempAppointment.getEndTimeString());
            titleTextField.setText(tempAppointment.getTitle());
            typeComboBox.setValue(tempAppointment.getType());
            customerComboBox.setValue(tempAppointment.getCustomer());

        } else {
            appointmentIdTextField.setText(null);
            titleTextField.setText(null);
            typeComboBox.setValue(null);
            datePicker.setValue(null);
            startTimeComboBox.setValue(null);
            ObservableList<String> startTimes = FXCollections.observableArrayList();
            for(int i = 0; i < 56; i++) {
                LocalTime lt = LocalTime.of(8,0);
                lt = lt.plusMinutes(15 * i);
                String t1 = lt.format(DateTimeFormatter.ofPattern("h:mm a"));
                startTimes.add(t1);
            }
            startTimeComboBox.setItems(startTimes);
            endTimeComboBox.setValue(null);
            stateComboBox.setItems(State.allStatesList);
            stateComboBox.setDisable(false);
            regionComboBox.setValue(null);
            salespersonComboBox.setValue(null);
            customerComboBox.setValue(null);
        }
    }

    /** This method is an event handler on the Back button.
     * When clicked, the button redirects the program to the AllAppointmentsScreen.
     * @param actionEvent Passed from the On Action event listener on the Back button.
     * @throws IOException Exception gets thrown if load() cannot locate the FXML file
     */
    public void toAllAppointmentsScreen(ActionEvent actionEvent) throws IOException {
        tempAppointment = null;
        labelBoolean = false;

        Parent root = FXMLLoader.load(Objects.requireNonNull(getClass().getResource("/view/AllAppointmentsScreen.fxml")));
        Stage stage = (Stage) ((Node) (actionEvent.getSource())).getScene().getWindow();
        Scene scene = new Scene(root, 1000, 500);
        stage.setScene(scene);
        stage.setTitle("All Appointments Screen");

    }

    /** This method is an event handler for the Submit button that saves the entered Appointment data to the database
     * and redirects the application back to the allAppointmentScreen. The method gathers the data from the form,
     * executes the SQL to update the database and then loads the FXML document for the allAppointmentScreen.
     * @param actionEvent Passed from the On Action event listener on the Submit button.
     * @throws IOException The FXMLLoader.load() call will throw this exception if the FXML document can't be found.
     * @throws SQLException This execution will be thrown if the SQL does not execute properly.
     */
    public void onSubmit(ActionEvent actionEvent) throws IOException, SQLException {
        DateTimeFormatter timeFormatter = DateTimeFormatter.ofPattern("h:mm a");
        ObservableList<Appointment> localCustomerAppointments = FXCollections.observableArrayList();
        ObservableList<Appointment> localSalespersonAppointments = FXCollections.observableArrayList();
        if (appointmentIdTextField.getText() == null) {
            Client selectedClient = customerComboBox.getSelectionModel().getSelectedItem();
            if (selectedClient == null) {
                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.setTitle("Blank Field");
                alert.setContentText("Please select a customer. ");
                alert.show();
                return;
            }
            int customerId = selectedClient.getCustomerId();
            Salesperson selectedSalesperson = salespersonComboBox.getSelectionModel().getSelectedItem();
            if (selectedSalesperson == null) {
                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.setTitle("Blank Field");
                alert.setContentText("Please select a salesperson. ");
                alert.show();
                return;
            }
            int salespersonId = selectedSalesperson.getSalespersonId();
            State selectedState = stateComboBox.getSelectionModel().getSelectedItem();
            if (selectedState == null) {
                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.setTitle("Blank Field");
                alert.setContentText("Please select a state. ");
                alert.show();
                return;
            }
            int stateId = selectedState.getStateId();
            Region selectedRegion = regionComboBox.getSelectionModel().getSelectedItem();
            if (selectedRegion == null) {
                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.setTitle("Blank Field");
                alert.setContentText("Please select a region. ");
                alert.show();
                return;
            }
            int regionId = selectedRegion.getRegionId();
            String title = titleTextField.getText();
            if (title == null) {
                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.setTitle("Blank Field");
                alert.setContentText("Please enter a title. ");
                alert.show();
                return;
            }
            String type = typeComboBox.getSelectionModel().getSelectedItem();
            if (type == null) {
                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.setTitle("Blank Field");
                alert.setContentText("Please select an appointment type. ");
                alert.show();
                return;
            }
            LocalDate localDate = datePicker.getValue();
            if (localDate == null) {
                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.setTitle("Blank Field");
                alert.setContentText("Please select a date for the appointment.");
                alert.show();
                return;
            }
            String localTimeStartString = startTimeComboBox.getSelectionModel().getSelectedItem();
            if (localTimeStartString == null) {
                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.setTitle("Blank Field");
                alert.setContentText("Please select a start time for the appointment.");
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
            Timestamp timeStampStart = Timestamp.valueOf(localDateTimeStart);
            LocalTime localTimeEnd = LocalTime.from(timeFormatter.parse(localTimeEndString));
            LocalDateTime localDateTimeEnd = LocalDateTime.of(localDate, localTimeEnd);
            Timestamp timeStampEnd = Timestamp.valueOf(localDateTimeEnd);
            for(Appointment appointment : Appointment.allAppointmentsList) {
                if(appointment.getSalespersonId() == salespersonId) {
                    localSalespersonAppointments.add(appointment);
                }
            }
            for(Appointment ap : localSalespersonAppointments) {
                if((ap.getStart().isBefore(localDateTimeStart)) && (ap.getEnd().isAfter(localDateTimeStart))) {
                    Alert alert = new Alert(Alert.AlertType.ERROR);
                    alert.setTitle("Conflicting Appointment");
                    alert.setContentText("Already scheduled " + ap.getStartTimeString() + "to " + ap.getEndTimeString());
                    alert.show();
                    return;
                }
                if(ap.getStart().equals(localDateTimeStart)) {
                    Alert alert = new Alert(Alert.AlertType.ERROR);
                    alert.setTitle("Conflicting Appointment");
                    alert.setContentText("Already scheduled " + ap.getStartTimeString() + "to " + ap.getEndTimeString());
                    alert.show();
                    return;
                }
                if((ap.getStart().isAfter(localDateTimeStart)) && (ap.getStart().isBefore(localDateTimeEnd))) {
                    Alert alert = new Alert(Alert.AlertType.ERROR);
                    alert.setTitle("Conflicting Appointment");
                    alert.setContentText("Already scheduled " + ap.getStartTimeString() + "to " + ap.getEndTimeString());
                    alert.show();
                    return;
                }
            }
            for(Appointment appointment : Appointment.allAppointmentsList) {
                if(appointment.getCustomerId() == customerId) {
                    localCustomerAppointments.add(appointment);
                }
            }
            for(Appointment ap : localCustomerAppointments) {
                if((ap.getStart().isBefore(localDateTimeStart)) && (ap.getEnd().isAfter(localDateTimeStart))) {
                    Alert alert = new Alert(Alert.AlertType.ERROR);
                    alert.setTitle("Conflicting Appointment");
                    alert.setContentText("Already scheduled " + ap.getStartTimeString() + "to " + ap.getEndTimeString());
                    alert.show();
                    return;
                }
                if(ap.getStart().equals(localDateTimeStart)) {
                    Alert alert = new Alert(Alert.AlertType.ERROR);
                    alert.setTitle("Conflicting Appointment");
                    alert.setContentText("Already scheduled " + ap.getStartTimeString() + "to " + ap.getEndTimeString());
                    alert.show();
                    return;
                }
                if((ap.getStart().isAfter(localDateTimeStart)) && (ap.getStart().isBefore(localDateTimeEnd))) {
                    Alert alert = new Alert(Alert.AlertType.ERROR);
                    alert.setTitle("Conflicting Appointment");
                    alert.setContentText("Already scheduled " + ap.getStartTimeString() + "to " + ap.getEndTimeString());
                    alert.show();
                    return;
                }
            }

            DBAppointments.insertAppointment(title, type, timeStampStart, timeStampEnd, customerId, salespersonId, stateId, regionId);

            DBAppointments.getEveryAppointment();

            Parent root = FXMLLoader.load(Objects.requireNonNull(getClass().getResource("/view/AllAppointmentsScreen.fxml")));
            Stage stage = (Stage) ((Node) (actionEvent.getSource())).getScene().getWindow();
            Scene scene = new Scene(root, 1000, 500);
            stage.setScene(scene);
            stage.setTitle("All Appointments Screen");
        }
        else {
            int appointmentId = Integer.parseInt(appointmentIdTextField.getText());
            Client selectedClient = customerComboBox.getSelectionModel().getSelectedItem();
            if (selectedClient == null) {
                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.setTitle("Blank Field");
                alert.setContentText("Please select a customer. ");
                alert.show();
                return;
            }
            int customerId = selectedClient.getCustomerId();
            Salesperson selectedSalesperson = salespersonComboBox.getSelectionModel().getSelectedItem();
            if (selectedSalesperson == null) {
                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.setTitle("Blank Field");
                alert.setContentText("Please select a salesperson. ");
                alert.show();
                return;
            }
            int salespersonId = selectedSalesperson.getSalespersonId();
            State selectedState = stateComboBox.getSelectionModel().getSelectedItem();
            if (selectedState == null) {
                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.setTitle("Blank Field");
                alert.setContentText("Please select a state. ");
                alert.show();
                return;
            }
            int stateId = selectedState.getStateId();
            Region selectedRegion = regionComboBox.getSelectionModel().getSelectedItem();
            if (selectedRegion == null) {
                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.setTitle("Blank Field");
                alert.setContentText("Please select a region. ");
                alert.show();
                return;
            }
            int regionId = selectedRegion.getRegionId();
            String title = titleTextField.getText();
            if (title == null) {
                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.setTitle("Blank Field");
                alert.setContentText("Please enter a title. ");
                alert.show();
                return;
            }
            String type = typeComboBox.getSelectionModel().getSelectedItem();
            if (type == null) {
                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.setTitle("Blank Field");
                alert.setContentText("Please select an appointment type. ");
                alert.show();
                return;
            }
            LocalDate localDate = datePicker.getValue();
            if (localDate == null) {
                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.setTitle("Blank Field");
                alert.setContentText("Please select a date for the appointment.");
                alert.show();
                return;
            }
            String localTimeStartString = startTimeComboBox.getSelectionModel().getSelectedItem();
            if (localTimeStartString == null) {
                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.setTitle("Blank Field");
                alert.setContentText("Please select a start time for the appointment.");
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
            Timestamp timeStampStart = Timestamp.valueOf(localDateTimeStart);
            LocalTime localTimeEnd = LocalTime.from(timeFormatter.parse(localTimeEndString));
            LocalDateTime localDateTimeEnd = LocalDateTime.of(localDate, localTimeEnd);
            Timestamp timeStampEnd = Timestamp.valueOf(localDateTimeEnd);
            for(Appointment appointment : Appointment.allAppointmentsList) {
                if(appointment.getSalespersonId() == salespersonId) {
                    localSalespersonAppointments.add(appointment);
                }
            }
            for(Appointment ap : localSalespersonAppointments) {
                if((ap.getStart().isBefore(localDateTimeStart)) && (ap.getEnd().isAfter(localDateTimeStart))) {
                    Alert alert = new Alert(Alert.AlertType.ERROR);
                    alert.setTitle("Conflicting Appointment");
                    alert.setContentText("Salesperson already scheduled " + ap.getStartTimeString() + " to " + ap.getEndTimeString());
                    alert.show();
                    return;
                }
                if(ap.getStart().equals(localDateTimeStart)) {
                    Alert alert = new Alert(Alert.AlertType.ERROR);
                    alert.setTitle("Conflicting Appointment");
                    alert.setContentText("Salesperson already scheduled " + ap.getStartTimeString() + " to " + ap.getEndTimeString());
                    alert.show();
                    return;
                }
                if((ap.getStart().isAfter(localDateTimeStart)) && (ap.getStart().isBefore(localDateTimeEnd))) {
                    Alert alert = new Alert(Alert.AlertType.ERROR);
                    alert.setTitle("Conflicting Appointment");
                    alert.setContentText("Salesperson already scheduled " + ap.getStartTimeString() + " to " + ap.getEndTimeString());
                    alert.show();
                    return;
                }
            }
            for(Appointment appointment : Appointment.allAppointmentsList) {
                if(appointment.getCustomerId() == customerId) {
                    localCustomerAppointments.add(appointment);
                }
            }
            for(Appointment ap : localCustomerAppointments) {
                if((ap.getStart().isBefore(localDateTimeStart)) && (ap.getEnd().isAfter(localDateTimeStart))) {
                    Alert alert = new Alert(Alert.AlertType.ERROR);
                    alert.setTitle("Conflicting Appointment");
                    alert.setContentText("Client already scheduled " + ap.getStartTimeString() + "to " + ap.getEndTimeString());
                    alert.show();
                    return;
                }
                if(ap.getStart().equals(localDateTimeStart)) {
                    Alert alert = new Alert(Alert.AlertType.ERROR);
                    alert.setTitle("Conflicting Appointment");
                    alert.setContentText("Client already scheduled " + ap.getStartTimeString() + "to " + ap.getEndTimeString());
                    alert.show();
                    return;
                }
                if((ap.getStart().isAfter(localDateTimeStart)) && (ap.getStart().isBefore(localDateTimeEnd))) {
                    Alert alert = new Alert(Alert.AlertType.ERROR);
                    alert.setTitle("Conflicting Appointment");
                    alert.setContentText("Client already scheduled " + ap.getStartTimeString() + "to " + ap.getEndTimeString());
                    alert.show();
                    return;
                }
            }

            DBAppointments.updateAppointment(title, type, timeStampStart, timeStampEnd, customerId, salespersonId, stateId, regionId, appointmentId);

            tempAppointment = null;

            DBAppointments.getEveryAppointment();

            Parent root = FXMLLoader.load(Objects.requireNonNull(getClass().getResource("/view/AllAppointmentsScreen.fxml")));
            Stage stage = (Stage) ((Node) (actionEvent.getSource())).getScene().getWindow();
            Scene scene = new Scene(root, 1000, 500);
            stage.setScene(scene);
            stage.setTitle("All Appointment Screen");
        }
    }

    /** This method changes the available end time options to only show times occurring after the start time that was selected.
     * @param actionEvent ActionEvent object passed from the event listener on the date picker. */
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

    /** This method loads the endTimeComboBox with potential end times, based on the start time that was
     * passed from the selected Appointment. */
    public void setEndTimes() {
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



    /** This method is an event handler on the StateComboBox.
     * When a state is initially chosen, it sets the corresponding Region and fills the salespersonComboBox and
     * customerComboBox with the salespersons and customers for that region. It also filters out the remaining states
     * so that only states from that region are available. To get the full list of states back, the region needs to be changed.
     * @param actionEvent Passed from the On Action event listener on the StateComboBox. */
    public void onStateSelect(ActionEvent actionEvent) {
        if (regionComboBox.getSelectionModel().getSelectedItem() == null) {
            int regionId = stateComboBox.getSelectionModel().getSelectedItem().getRegionId();
            for (Region region : Region.allRegionsList) {
                if (region.getRegionId() == regionId) {
                    regionComboBox.setValue(region);
                    break;
                }
            }

            ObservableList<State> regionStates = FXCollections.observableArrayList();
            for (State state : State.allStatesList) {
                if (state.getRegionId() == regionId) {
                    regionStates.add(state);
                }
            }
            stateComboBox.setItems(regionStates);

            ObservableList<Salesperson> regionSalespersons = FXCollections.observableArrayList();
            for (Salesperson salesperson : Salesperson.allSalespersonsList) {
                if (salesperson.getRegionId() == regionId) {
                    regionSalespersons.add(salesperson);
                }
            }
            salespersonComboBox.setItems(regionSalespersons);

            ObservableList<Client> regionClients = FXCollections.observableArrayList();
            for (Client client : Client.allClientList) {
                if (client.getRegionId() == regionId) {
                    regionClients.add(client);
                }
            }
            customerComboBox.setItems(regionClients);

            regionComboBox.setDisable(false);
            salespersonComboBox.setDisable(false);
            customerComboBox.setDisable(false);
            salespersonComboBox.setPromptText("");
            customerComboBox.setPromptText("");
        }
    }

    /** This method is an event handler on the RegionComboBox.
     * When the region is changed, it resets the StateComboBox with the states belonging to that region and fills
     * the salespersonComboBox and customerComboBox with the salespersons and customers for that region. The only way
     * to change the stateComboBox and customerComboBox items after that is to select a different Region.
     * @param actionEvent Passed from the On Action event listener on the RegionComboBox.
     */
    public void onRegionSelect(ActionEvent actionEvent) {
        if (regionComboBox.getSelectionModel().getSelectedItem() != null) {
            int regionId = regionComboBox.getSelectionModel().getSelectedItem().getRegionId();

            ObservableList<State> regionStates = FXCollections.observableArrayList();
            for (State state : State.allStatesList) {
                if (state.getRegionId() == regionId) {
                    regionStates.add(state);
                }
            }
            stateComboBox.setItems(regionStates);

            ObservableList<Salesperson> regionSalespersons = FXCollections.observableArrayList();
            for (Salesperson salesperson : Salesperson.allSalespersonsList) {
                if (salesperson.getRegionId() == regionId) {
                    regionSalespersons.add(salesperson);
                }
            }
            salespersonComboBox.setItems(regionSalespersons);
            salespersonComboBox.setValue(null);
            salespersonComboBox.setPromptText("");

            ObservableList<Client> regionClients = FXCollections.observableArrayList();
            for (Client client : Client.allClientList) {
                if (client.getRegionId() == regionId) {
                    regionClients.add(client);
                }
            }
            customerComboBox.setItems(regionClients);
            customerComboBox.setValue(null);
            customerComboBox.setPromptText("");
        }
    }

    /** Static method that allows the previous screen to pass an Appointment object to be edited.
     * @param paramAppointment Appointment object passed from the allAppointmentsScreen. */
    public static void setTempAppointment(Appointment paramAppointment) {
        tempAppointment = paramAppointment;
    }

    /** Method resets the region based combo boxes back to the original state of the screen.
     * @param actionEvent Passed from the On Action event listener on the Reset button.*/
    public void resetRegionBasedInfo(ActionEvent actionEvent) {
        stateComboBox.setItems(State.allStatesList);
        stateComboBox.setValue(null);
        stateComboBox.setPromptText("Select State");
        regionComboBox.setDisable(true);
        regionComboBox.setValue(null);
        salespersonComboBox.setValue(null);
        salespersonComboBox.setDisable(true);
        customerComboBox.setValue(null);
        customerComboBox.setDisable(true);
    }

    /** This method is an event handler on the New Client button.
     * When clicked, the button redirects the program to the AddModifyCustomerScreen.
     * @param actionEvent Passed from the On Action event listener on the New Client button.
     * @throws IOException Exception gets thrown if load() cannot locate the FXML file
     */
    public void toAdminCreateCustomerScreen(ActionEvent actionEvent) throws IOException {
        AdminCreateEditCustomerScreenController.labelBoolean = false;

        Parent root = FXMLLoader.load(Objects.requireNonNull(getClass().getResource("/view/AdminCreateEditCustomerScreen.fxml")));
        Stage stage = (Stage) ((Node) (actionEvent.getSource())).getScene().getWindow();
        Scene scene = new Scene(root, 600, 500);
        stage.setScene(scene);
        stage.setTitle("Add New Client Screen");
        stage.show();
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
            stage.setTitle("Administrator Home Screen");
        }
    }
}
