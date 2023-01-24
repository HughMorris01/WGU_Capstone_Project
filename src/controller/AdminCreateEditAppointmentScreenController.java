package controller;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.ComboBox;
import javafx.scene.control.DatePicker;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import model.*;

import java.io.IOException;
import java.net.URL;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.Objects;
import java.util.ResourceBundle;

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
    /** Combo box used to select the appointment customer */
    public ComboBox<Customer> customerComboBox;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        if (!labelBoolean) {
            screenLabel.setText("New Appointment Info");
        } else {
            screenLabel.setText("Edit Appointment Info");
        }
        regionComboBox.setItems(Region.allRegionsList);
        if (tempAppointment != null) {
            appointmentIdTextField.setText(String.valueOf(tempAppointment.getCustomerId()));
            stateComboBox.setValue(tempAppointment.getState());
            regionComboBox.setValue(tempAppointment.getRegion());
            salespersonComboBox.setValue(tempAppointment.getSalesperson());
            datePicker.setValue(tempAppointment.getStart().toLocalDate());
            startTimeComboBox.setValue(tempAppointment.getStartTimeString());
            onStartTime();
            endTimeComboBox.setValue(tempAppointment.getEndTimeString());
            titleTextField.setText(tempAppointment.getTitle());
            typeComboBox.setValue(tempAppointment.getType());
            customerComboBox.setValue(tempAppointment.getCustomer());

            ObservableList<State> regionStates = FXCollections.observableArrayList();
            int regionId = tempAppointment.getRegionId();
            for (State state : State.allStatesList) {
                if (state.getRegionId() == regionId) {
                    regionStates.add(state);
                }
            }
            stateComboBox.setItems(regionStates);
            regionComboBox.setDisable(false);
            ObservableList<Salesperson> regionSalespersons = FXCollections.observableArrayList();
            for (Salesperson salesperson : Salesperson.allSalespersonsList) {
                if (salesperson.getRegionId() == regionId) {
                    regionSalespersons.add(salesperson);
                }
            }
            salespersonComboBox.setItems(regionSalespersons);
            salespersonComboBox.setDisable(false);
            ObservableList<Customer> regionCustomers = FXCollections.observableArrayList();
            for (Customer customer : Customer.allCustomerList) {
                if (customer.getRegionId() == regionId) {
                    regionCustomers.add(customer);
                }
            }
            customerComboBox.setItems(regionCustomers);
            customerComboBox.setDisable(false);
        } else {
            stateComboBox.setItems(State.allStatesList);
        }
    }

    /** This method is an event handler on the Back button.
     * When clicked, the button redirects the program to the AllAppointmentsScreen.
     * @param actionEvent Passed from the On Action event listener on the Back button.
     * @throws IOException Exception gets thrown if load() cannot locate the FXML file
     */
    public void toAllAppointmentsScreen(ActionEvent actionEvent) throws IOException {
        Parent root = FXMLLoader.load(Objects.requireNonNull(getClass().getResource("/view/AllAppointmentsScreen.fxml")));
        Stage stage = (Stage) ((Node) (actionEvent.getSource())).getScene().getWindow();
        Scene scene = new Scene(root, 1000, 500);
        stage.setScene(scene);
        stage.setTitle("All Appointments Screen");

    }

    public void onSubmit(ActionEvent actionEvent) {
    }

    public void onSalespersonSelect(ActionEvent actionEvent) {
        customerComboBox.setDisable(false);

        ObservableList<Customer> regionCustomers = FXCollections.observableArrayList();

        Salesperson selectedSalesperson = salespersonComboBox.getSelectionModel().getSelectedItem();
        int selectedSalespersonId = selectedSalesperson.getSalespersonId();

        for (Customer customer : Customer.allCustomerList) {
            if (customer.getSalespersonId() == selectedSalespersonId) {
                regionCustomers.add(customer);
            }
        }

        customerComboBox.setItems(regionCustomers);
        customerComboBox.setValue(null);
    }

    public void onStartTime(ActionEvent actionEvent) {
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

    public void onDateSelect(ActionEvent actionEvent) {
    }

    public void onStateSelect(ActionEvent actionEvent) {
    }

    public void onRegionSelect(ActionEvent actionEvent) {

        Region selectedRegion = regionComboBox.getSelectionModel().getSelectedItem();
        int selectedRegionId = selectedRegion.getRegionId();

        ObservableList<State> regionStates = FXCollections.observableArrayList();
        ObservableList<Salesperson> regionSalespersons = FXCollections.observableArrayList();

        for (State state : State.allStatesList) {
            if (state.getRegionId() == selectedRegionId) {
                regionStates.add(state);
            }
        }
        for (Salesperson salesperson : Salesperson.allSalespersonsList) {
            if (salesperson.getRegionId() == selectedRegionId) {
                regionSalespersons.add(salesperson);
            }
        }

        stateComboBox.setItems(regionStates);
        salespersonComboBox.setItems(regionSalespersons);

        stateComboBox.setValue(null);
        salespersonComboBox.setValue(null);

        customerComboBox.setDisable(true);

    }

    public static void setTempAppointment(Appointment paramAppointment) {
        tempAppointment = paramAppointment;
    }
}
