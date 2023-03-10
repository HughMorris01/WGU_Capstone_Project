package controller;

import javafx.collections.FXCollections;
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
import model.Client;
import model.Salesperson;

import java.io.IOException;
import java.net.URL;
import java.util.Objects;
import java.util.Optional;
import java.util.ResourceBundle;

public class SalespersonDetailScreenController implements Initializable {
    private static Salesperson selectedSalesperson;
    public Label salespersonNameTextField;
    public Label regionTextField;
    public Label emailTextField;
    public Label totalAppointmentsTextField;
    public Label scheduledAppointmentsTextField;
    public Label completedAppointmentsTextField;
    public ToggleGroup appointmentsToggleGroup;
    public RadioButton allAppointmentsRadioButton;
    public RadioButton upcomingAppointmentsRadioButton;
    public RadioButton completedAppointmentsRadioButton;
    public TableView<Appointment> appointmentsTable;
    public TableColumn<Appointment, String> appointmentDateCol;
    public TableColumn<Appointment, String> appointmentStartCol;
    public TableColumn<Appointment, String> appointmentEndCol;
    public TableColumn<Appointment, String> appointmentClientNameCol;
    public TableView<Client> clientsTable;
    public TableColumn<Client, String> clientNameCol;
    public TableColumn<Client, String> clientAddressCol;
    public TableColumn<Client, Integer> totalClientsAppointmentsCol;
    public Label totalClientsTextField;

    public void initialize(URL url, ResourceBundle resourceBundle) {
        salespersonNameTextField.setText(selectedSalesperson.getFullName());
        regionTextField.setText(selectedSalesperson.getRegionName());
        emailTextField.setText(selectedSalesperson.getEmail());
        totalAppointmentsTextField.setText(Integer.toString(selectedSalesperson.getTotalAllAppointments()));
        scheduledAppointmentsTextField.setText(Integer.toString(selectedSalesperson.getTotalScheduledAppointments()));
        completedAppointmentsTextField.setText(Integer.toString(selectedSalesperson.getTotalCompletedAppointments()));
        totalClientsTextField.setText(Integer.toString(selectedSalesperson.getTotalClients()));


        appointmentDateCol.setCellValueFactory(new PropertyValueFactory<>("startDateString"));
        appointmentDateCol.setStyle("-fx-alignment: CENTER;");
        appointmentStartCol.setCellValueFactory(new PropertyValueFactory<>("startTimeString"));
        appointmentStartCol.setStyle("-fx-alignment: CENTER;");
        appointmentEndCol.setCellValueFactory(new PropertyValueFactory<>("endTimeString"));
        appointmentEndCol.setStyle("-fx-alignment: CENTER;");
        appointmentClientNameCol.setCellValueFactory(new PropertyValueFactory<>("clientName"));
        appointmentClientNameCol.setStyle("-fx-alignment: CENTER;");

        clientNameCol.setCellValueFactory(new PropertyValueFactory<>("clientFullName"));
        clientNameCol.setStyle("-fx-alignment: CENTER;");
        clientAddressCol.setCellValueFactory(new PropertyValueFactory<>("address"));
        clientAddressCol.setStyle("-fx-alignment: CENTER;");
        totalClientsAppointmentsCol.setCellValueFactory(new PropertyValueFactory<>("totalAppointments"));
        totalClientsAppointmentsCol.setStyle("-fx-alignment: CENTER;");

        appointmentsTable.setItems(selectedSalesperson.getSalespersonAppointments());
        clientsTable.setItems(selectedSalesperson.getSalespersonClients());


    }

    public void toAdminHomeScreen(ActionEvent actionEvent) throws IOException {
        selectedSalesperson = null;

        Parent root = FXMLLoader.load(Objects.requireNonNull(getClass().getResource("/view/AdminHomeScreen.fxml")));
        Stage stage = (Stage) ((Node) (actionEvent.getSource())).getScene().getWindow();
        Scene scene = new Scene(root, 1000, 500);
        stage.setScene(scene);
        stage.setTitle("Administrator Home Screen");
    }
    /** This method sets the static selectedSalesperson field with the Salesperson object passed from the previous screen.
    @param passedSalesperson Salesperson object passed from the adminHomeScreen. */
    public static void setSelectedSalesperson(Salesperson passedSalesperson) {
        selectedSalesperson = passedSalesperson;
    }

    public void onUpcomingAppointments(ActionEvent actionEvent) {
        ObservableList<Appointment> localUpcomingAppointments = FXCollections.observableArrayList();
        for(Appointment appointment : Appointment.allUpcomingAppointmentsList) {
            if(appointment.getSalespersonId() == selectedSalesperson.getSalespersonId()) {
                localUpcomingAppointments.add(appointment);
            }
         }
        appointmentsTable.setItems(localUpcomingAppointments);
    }

    public void onAllAppointments(ActionEvent actionEvent) {
        appointmentsTable.setItems(selectedSalesperson.getSalespersonAppointments());
    }

    public void onCompletedAppointments(ActionEvent actionEvent) {
        ObservableList<Appointment> localCompletedAppointments = FXCollections.observableArrayList();
        for(Appointment appointment : Appointment.allCompletedAppointmentsList) {
            if(appointment.getSalespersonId() == selectedSalesperson.getSalespersonId()) {
                localCompletedAppointments.add(appointment);
            }
        }
        appointmentsTable.setItems(localCompletedAppointments);
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
