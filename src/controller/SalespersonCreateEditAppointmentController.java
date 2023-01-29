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
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.Objects;
import java.util.Optional;
import java.util.ResourceBundle;

public class SalespersonCreateEditAppointmentController implements Initializable {
    private static Appointment passedAppointment = null;
    private static boolean labelBoolean = false;

    public Label screenLabel;
    public TextField appointmentIdTextField;
    public DatePicker datePicker;
    public ComboBox<String> startTimeComboBox;
    public ComboBox<String> endTimeComboBox;
    public ComboBox<String> typeComboBox;
    public TextField titleTextField;
    public ComboBox<Client> clientComboBox;
    public ComboBox<Salesperson> salespersonComboBox;
    public ComboBox<State> stateComboBox;
    public ComboBox<Region> regionComboBox;


    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        if (!labelBoolean) {
            screenLabel.setText("Edit Appointment Info");
        } else {
            screenLabel.setText("Create New Appointment");
        }
        ObservableList<String> types = FXCollections.observableArrayList();
        types.add("In-Person");
        types.add("Virtual");
        types.add("Group Session");
        types.add("Seminar");
        typeComboBox.setItems(types);
        salespersonComboBox.setValue(SalespersonHomeScreenController.getUserSalesperson());
        regionComboBox.setValue(SalespersonHomeScreenController.getUserSalesperson().getRegion());
        if (passedAppointment != null) {
            appointmentIdTextField.setText(String.valueOf(passedAppointment.getAppointmentId()));
            stateComboBox.setValue(passedAppointment.getState());
            datePicker.setValue(passedAppointment.getStart().toLocalDate());
            startTimeComboBox.setValue(passedAppointment.getStartTimeString());
            ObservableList<String> startTimes = FXCollections.observableArrayList();
            for(int i = 0; i < 56; i++) {
                LocalTime lt = LocalTime.of(8,0);
                lt = lt.plusMinutes(15 * i);
                String t1 = lt.format(DateTimeFormatter.ofPattern("h:mm a"));
                startTimes.add(t1);
            }
            startTimeComboBox.setItems(startTimes);
            setEndTimes();
            endTimeComboBox.setValue(passedAppointment.getEndTimeString());
            titleTextField.setText(passedAppointment.getTitle());
            typeComboBox.setValue(passedAppointment.getType());
            clientComboBox.setValue(passedAppointment.getClient());

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
            ObservableList<State> regionStates = FXCollections.observableArrayList();
            for(State state : State.allStatesList) {
                if(state.getRegionId() == SalespersonHomeScreenController.getUserSalesperson().getRegionId()) {
                    regionStates.add(state);
                }
            }
            stateComboBox.setItems(regionStates);
            stateComboBox.setDisable(false);
            clientComboBox.setDisable(false);
            clientComboBox.setValue(null);
            clientComboBox.setItems(SalespersonHomeScreenController.getUserSalesperson().getSalespersonClients());
        }
    }

    public static void setTempAppointment(Appointment selectedItem) {
        passedAppointment = selectedItem;
    }

    public static void setLabelBoolean(Boolean bool) { labelBoolean = bool; }

    /** This method is an event handler on the Back button.
     * When clicked, the button redirects the program to the SalespersonHomeScreen.
     * @param actionEvent Passed from the On Action event listener on the Back button.
     * @throws IOException Exception gets thrown if load() cannot locate the FXML file
     */
    public void toSalespersonHomeScreen(ActionEvent actionEvent) throws IOException {
        passedAppointment = null;
        labelBoolean = false;

        Parent root = FXMLLoader.load(Objects.requireNonNull(getClass().getResource("/view/SalespersonHomeScreen.fxml")));
        Stage stage = (Stage) ((Node) (actionEvent.getSource())).getScene().getWindow();
        Scene scene = new Scene(root, 1000, 500);
        stage.setScene(scene);
        stage.setTitle("Salesperson Home Screen");

    }

    /** This method is an event handler on the "Sign Out" button.
     * When clicked, the button redirects the program to the original Login Screen
     * @param actionEvent Passed from the On Action event listener on the "Sign Out" button.
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

    /** This method is an event handler for the Submit button that saves the entered Appointment data to the database
     * and redirects the application back to the allAppointmentScreen. The method gathers the data from the form,
     * executes the SQL to update the database and then loads the FXML document for the allAppointmentScreen.
     * @param actionEvent Passed from the On Action event listener on the Submit button.
     * @throws IOException The FXMLLoader.load() call will throw this exception if the FXML document can't be found.
     * @throws SQLException This execution will be thrown if the SQL does not execute properly.
     */
    public void onSubmit(ActionEvent actionEvent) throws IOException, SQLException {
        DateTimeFormatter timeFormatter = DateTimeFormatter.ofPattern("h:mm a");
        ObservableList<Appointment> localClientAppointments1 = FXCollections.observableArrayList();
        ObservableList<Appointment> localClientAppointments2 = FXCollections.observableArrayList();
        ObservableList<Appointment> localSalespersonAppointments1 = FXCollections.observableArrayList();
        ObservableList<Appointment> localSalespersonAppointments2 = FXCollections.observableArrayList();
        if (appointmentIdTextField.getText() == null) {
            Client selectedClient = clientComboBox.getSelectionModel().getSelectedItem();
            if (selectedClient == null) {
                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.setTitle("Blank Field");
                alert.setContentText("Please select a customer. ");
                alert.show();
                return;
            }
            int clientId = selectedClient.getClientId();
            Salesperson selectedSalesperson = salespersonComboBox.getSelectionModel().getSelectedItem();
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
            LocalDate today = LocalDate.now();
            if(localDate.isBefore(today)) {
                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.setTitle("Blank Date");
                alert.setContentText("An appointment can only be scheduled for a future date. ");
                alert.show();
                datePicker.setValue(null);
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
                alert.setContentText("An end time must be selected.");
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
                    localSalespersonAppointments1.add(appointment);
                }
            }
            for(Appointment ap : localSalespersonAppointments1) {
                if((ap.getStart().isBefore(localDateTimeStart)) && (ap.getEnd().isAfter(localDateTimeStart))) {
                    Alert alert = new Alert(Alert.AlertType.ERROR);
                    alert.setTitle("Conflicting Appointment");
                    alert.setContentText("Salesperson already scheduled " + ap.getStartTimeString() + "to " + ap.getEndTimeString());
                    alert.show();
                    return;
                }
                if(ap.getStart().equals(localDateTimeStart)) {
                    Alert alert = new Alert(Alert.AlertType.ERROR);
                    alert.setTitle("Conflicting Appointment");
                    alert.setContentText("Salesperson already scheduled " + ap.getStartTimeString() + "to " + ap.getEndTimeString());
                    alert.show();
                    return;
                }
                if((ap.getStart().isAfter(localDateTimeStart)) && (ap.getStart().isBefore(localDateTimeEnd))) {
                    Alert alert = new Alert(Alert.AlertType.ERROR);
                    alert.setTitle("Conflicting Appointment");
                    alert.setContentText("Salesperson already scheduled " + ap.getStartTimeString() + "to " + ap.getEndTimeString());
                    alert.show();
                    return;
                }
            }
            for(Appointment appointment : Appointment.allAppointmentsList) {
                if(appointment.getClientId() == clientId) {
                    localClientAppointments1.add(appointment);
                }
            }
            for(Appointment ap : localClientAppointments1) {
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

            DBAppointments.insertAppointment(title, type, timeStampStart, timeStampEnd, clientId, salespersonId, stateId, regionId);

            DBAppointments.getEveryAppointment();
            SalespersonHomeScreenController.getUserSalesperson().setSalespersonAppointments();
            SalespersonHomeScreenController.getUserSalesperson().setScheduledAppointments();
            SalespersonHomeScreenController.getUserSalesperson().setCompletedAppointments();

            Parent root = FXMLLoader.load(Objects.requireNonNull(getClass().getResource("/view/SalespersonHomeScreen.fxml")));
            Stage stage = (Stage) ((Node) (actionEvent.getSource())).getScene().getWindow();
            Scene scene = new Scene(root, 1000, 500);
            stage.setScene(scene);
            stage.setTitle("Salesperson Home Screen");
        }
        else {
            int appointmentId = Integer.parseInt(appointmentIdTextField.getText());
            Client selectedClient = clientComboBox.getSelectionModel().getSelectedItem();
            if (selectedClient == null) {
                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.setTitle("Blank Field");
                alert.setContentText("Please select a client. ");
                alert.show();
                return;
            }
            int clientId = selectedClient.getClientId();
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
            LocalDate today = LocalDate.now();
            if(localDate.isBefore(today)) {
                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.setTitle("Blank Date");
                alert.setContentText("An appointment can only be scheduled for a future date. ");
                alert.show();
                datePicker.setValue(null);
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
                alert.setContentText("Please select an end time. ");
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
                    localSalespersonAppointments1.add(appointment);
                }
            }
            for(Appointment apt : localSalespersonAppointments1) {
                if(apt.getAppointmentId() != appointmentId){
                    localSalespersonAppointments2.add(apt);
                }
            }
            for(Appointment ap : localSalespersonAppointments2) {
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
                if(appointment.getClientId() == clientId) {
                    localClientAppointments1.add(appointment);
                }
            }
            for(Appointment apt : localClientAppointments1) {
                if(apt.getAppointmentId() != appointmentId){
                    localClientAppointments2.add(apt);
                }
            }
            for(Appointment ap : localClientAppointments2) {
                if((ap.getStart().isBefore(localDateTimeStart)) && (ap.getEnd().isAfter(localDateTimeStart))) {
                    Alert alert = new Alert(Alert.AlertType.ERROR);
                    alert.setTitle("Conflicting Appointment");
                    alert.setContentText("Client already scheduled " + ap.getStartTimeString() + " to " + ap.getEndTimeString());
                    alert.show();
                    return;
                }
                if(ap.getStart().equals(localDateTimeStart)) {
                    Alert alert = new Alert(Alert.AlertType.ERROR);
                    alert.setTitle("Conflicting Appointment");
                    alert.setContentText("Client already scheduled " + ap.getStartTimeString() + " to " + ap.getEndTimeString());
                    alert.show();
                    return;
                }
                if((ap.getStart().isAfter(localDateTimeStart)) && (ap.getStart().isBefore(localDateTimeEnd))) {
                    Alert alert = new Alert(Alert.AlertType.ERROR);
                    alert.setTitle("Conflicting Appointment");
                    alert.setContentText("Client already scheduled " + ap.getStartTimeString() + " to " + ap.getEndTimeString());
                    alert.show();
                    return;
                }
            }

            DBAppointments.updateAppointment(title, type, timeStampStart, timeStampEnd, clientId, salespersonId, stateId, regionId, appointmentId);

            passedAppointment = null;

            DBAppointments.getEveryAppointment();

            Parent root = FXMLLoader.load(Objects.requireNonNull(getClass().getResource("/view/SalespersonHomeScreen.fxml")));
            Stage stage = (Stage) ((Node) (actionEvent.getSource())).getScene().getWindow();
            Scene scene = new Scene(root, 1000, 500);
            stage.setScene(scene);
            stage.setTitle("Salesperson Home Screen");
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




}
