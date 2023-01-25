package controller;

import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.stage.Stage;
import model.Appointment;
import model.Client;
import model.Salesperson;

import java.io.IOException;
import java.net.URL;
import java.time.LocalDateTime;
import java.util.Objects;
import java.util.ResourceBundle;

public class SalespersonHomeScreenController implements Initializable {
    private static Salesperson userSalesperson;
    public Label salespersonNameTextField;
    public TableView<Appointment> appointmentsTable;
    public TableColumn<Appointment, Integer> appointmentIdCol;
    public TableColumn<Appointment, String> appointmentDateCol;
    public TableColumn<Appointment, String> appointmentStartCol;
    public TableColumn<Appointment, String> appointmentEndCol;
    public TableColumn<Appointment, String> appointmentClientNameCol;
    public TableColumn<Appointment, String> appointmentTitleCol;
    public TableColumn<Appointment, String> appointmentTypeCol;
    public TableView<Client> clientsTable;
    public TableColumn<Client, Integer> clientIdCol;
    public TableColumn<Client, String> clientNameCol;
    public TableColumn<Client, String> clientAddressCol;
    public TableColumn<Client, String> clientZipCodeCol;
    public TableColumn<Client, String> clientStateCol;
    public TableColumn<Client, String> clientPhoneNumberCol;
    public TableColumn<Client, String> clientEmailCol;
    public ToggleGroup appointmentsToggleGroup;
    public RadioButton allAppointmentsRadioButton;
    public RadioButton upcomingAppointmentsRadioButton;
    public RadioButton completedAppointmentsRadioButton;


    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

    }

    /** This method is an event handler on the Edit Appointment button.
     * When clicked, the button redirects the program to the SalespersonCreateEditAppointmentScreen.
     * @param actionEvent Passed from the On Action event listener on the Edit Appointment button.
     * @throws IOException Exception gets thrown if load() cannot locate the FXML file
     */
    public void toSalespersonEditAppointmentScreen(ActionEvent actionEvent) throws IOException {
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

    public static void setUserSalesperson(Salesperson paramSalesperson) {
        userSalesperson = paramSalesperson;
    }

    public static Salesperson getUserSalesperson() {
        return userSalesperson;
    }

    public void displayUpcomingAppointments(ActionEvent actionEvent) {
    }

    public void displayAllAppointments(ActionEvent actionEvent) {
    }

    public void displayCompletedAppointments(ActionEvent actionEvent) {
    }

    public void toLoginScreen(ActionEvent actionEvent) {
    }

    public void toSalespersonEditClientScreen(ActionEvent actionEvent) {
    }
}
