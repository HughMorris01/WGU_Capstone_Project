package controller;

import database.DBAppointments;
import database.DBClients;
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
import java.sql.SQLException;
import java.time.LocalDateTime;
import java.util.Objects;
import java.util.Optional;
import java.util.ResourceBundle;

public class SalespersonHomeScreenController implements Initializable {
    public static Salesperson userSalesperson;
    public Label salespersonFirstNameTextField;
    public Label salespersonFullNameTextField;
    public Label regionTextField;
    public Label emailTextField;
    public Label totalScheduledTextField;
    public Label totalCompletedTextField;
    public Label totalAppointmentsTextField;
    public Label totalClientsTextField;
    
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
        userSalesperson.setSalespersonClients();

        salespersonFirstNameTextField.setText(userSalesperson.getSalespersonFirstName() + "!");
        salespersonFullNameTextField.setText(userSalesperson.getFullName());
        regionTextField.setText(userSalesperson.getRegionName());
        emailTextField.setText(userSalesperson.getEmail());
        totalScheduledTextField.setText(Integer.toString(userSalesperson.getTotalScheduledAppointments()));
        totalCompletedTextField.setText(Integer.toString(userSalesperson.getTotalCompletedAppointments()));
        totalAppointmentsTextField.setText(Integer.toString(userSalesperson.getTotalAllAppointments()));
        totalClientsTextField.setText(Integer.toString(userSalesperson.getTotalClients()));
        
        appointmentIdCol.setCellValueFactory(new PropertyValueFactory<>("appointmentId"));
        appointmentIdCol.setStyle("-fx-alignment: CENTER;");
        appointmentDateCol.setCellValueFactory(new PropertyValueFactory<>("startDateString"));
        appointmentDateCol.setStyle("-fx-alignment: CENTER;");
        appointmentStartCol.setCellValueFactory(new PropertyValueFactory<>("startTimeString"));
        appointmentStartCol.setStyle("-fx-alignment: CENTER;");
        appointmentEndCol.setCellValueFactory(new PropertyValueFactory<>("endTimeString"));
        appointmentEndCol.setStyle("-fx-alignment: CENTER;");
        appointmentClientNameCol.setCellValueFactory(new PropertyValueFactory<>("clientName"));
        appointmentClientNameCol.setStyle("-fx-alignment: CENTER;");
        appointmentTitleCol.setCellValueFactory(new PropertyValueFactory<>("title"));
        appointmentTitleCol.setStyle("-fx-alignment: CENTER;");
        appointmentTypeCol.setCellValueFactory(new PropertyValueFactory<>("type"));
        appointmentTypeCol.setStyle("-fx-alignment: CENTER;");

        clientIdCol.setCellValueFactory(new PropertyValueFactory<>("clientId"));
        clientIdCol.setStyle("-fx-alignment: CENTER;");
        clientNameCol.setCellValueFactory(new PropertyValueFactory<>("clientFullName"));
        clientNameCol.setStyle("-fx-alignment: CENTER;");
        clientAddressCol.setCellValueFactory(new PropertyValueFactory<>("address"));
        clientAddressCol.setStyle("-fx-alignment: CENTER;");
        clientZipCodeCol.setCellValueFactory(new PropertyValueFactory<>("zipCode"));
        clientZipCodeCol.setStyle("-fx-alignment: CENTER;");
        clientStateCol.setCellValueFactory(new PropertyValueFactory<>("stateAbbreviation"));
        clientStateCol.setStyle("-fx-alignment: CENTER;");
        clientPhoneNumberCol.setCellValueFactory(new PropertyValueFactory<>("phone"));
        clientPhoneNumberCol.setStyle("-fx-alignment: CENTER;");
        clientEmailCol.setCellValueFactory(new PropertyValueFactory<>("email"));
        clientEmailCol.setStyle("-fx-alignment: CENTER;");

        appointmentsTable.setItems(DBAppointments.getUpcomingAppointments(userSalesperson.getSalespersonId()));
        clientsTable.setItems(userSalesperson.getSalespersonClients());

    }


    public static void setUserSalesperson(Salesperson paramSalesperson) {
        userSalesperson = paramSalesperson;
    }

    public static Salesperson getUserSalesperson() {
        return userSalesperson;
    }

    /** This method loads the appointmentsTable with both completed and scheduled appointments.
     * @param actionEvent Passed from the On Action event listener on the radio button. */
    public void loadAllAppointments(ActionEvent actionEvent) {
        appointmentsTable.setItems(userSalesperson.getSalespersonAppointments());
    }

    /** This method loads the appointmentsTable with upcoming scheduled appointments.
     * @param actionEvent Passed from the On Action event listener on the radio button. */
    public void loadUpcomingAppointments(ActionEvent actionEvent) {
        ObservableList<Appointment> upcomingAppointments = FXCollections.observableArrayList();
            for(Appointment apt : Appointment.allUpcomingAppointmentsList){
                if(apt.getSalespersonId() == userSalesperson.getSalespersonId()) {
                    upcomingAppointments.add(apt);
                }
            }
    appointmentsTable.setItems(upcomingAppointments);
    }

    /** This method loads the appointmentsTable with appointments that have already been completed.
     * @param actionEvent Passed from the On Action event listener on the radio button. */
    public void loadCompletedAppointments(ActionEvent actionEvent) {
        ObservableList<Appointment> completedAppointments = FXCollections.observableArrayList();
        for(Appointment apt : Appointment.allCompletedAppointmentsList){
            if(apt.getSalespersonId() == userSalesperson.getSalespersonId()) {
                completedAppointments.add(apt);
            }
        }
        appointmentsTable.setItems(completedAppointments);
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


    /** This method is an event handler on the "Delete Appointment" button.
     * When clicked, the button prompts the user to confirm they wish to delete the selected appointment and once confirmed,
     * the appointment is deleted from the database.
     * @param actionEvent Passed from the On Action event listener on the "Delete Appointment" button.
     */
    public void onDeleteAppointment(ActionEvent actionEvent) throws SQLException {
        Appointment selectedAppointment = appointmentsTable.getSelectionModel().getSelectedItem();

        if (selectedAppointment == null) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("No Appointment Selected");
            alert.setContentText("Please select the appointment to be deleted");
            alert.show();
        }
        else if(appointmentsTable.getSelectionModel().getSelectedItem().getStart().isBefore(LocalDateTime.now())) {
            Alert alert1 = new Alert(Alert.AlertType.INFORMATION);
            alert1.setTitle("Appointment Already Completed");
            alert1.setContentText("An appointment that has already been completed cannot be deleted unless the entire client record is deleted.");
            alert1.show();
        }
        else {
            Alert alert = new Alert(Alert.AlertType.CONFIRMATION, "Permanently delete selected appointment?");
            Optional<ButtonType> result = alert.showAndWait();

            if (result.isPresent() && result.get() == ButtonType.OK) {
                int appointmentId = selectedAppointment.getAppointmentId();
                DBAppointments.deleteAppointment(appointmentId);

                DBAppointments.getEveryAppointment();
                DBAppointments.getCompletedAppointments();
                DBAppointments.getUpcomingAppointments();
                userSalesperson.setScheduledAppointments();
                userSalesperson.setCompletedAppointments();
                userSalesperson.setSalespersonAppointments();
                totalScheduledTextField.setText(Integer.toString(userSalesperson.getTotalScheduledAppointments()));
                totalCompletedTextField.setText(Integer.toString(userSalesperson.getTotalCompletedAppointments()));
                totalAppointmentsTextField.setText(Integer.toString(userSalesperson.getTotalAllAppointments()));
                totalClientsTextField.setText(Integer.toString(userSalesperson.getTotalClients()));

                if(appointmentsToggleGroup.getSelectedToggle() == upcomingAppointmentsRadioButton) {
                    appointmentsTable.setItems(DBAppointments.getUpcomingAppointments(userSalesperson.getSalespersonId()));
                }
                else {
                    appointmentsTable.setItems(DBAppointments.getEveryAppointment(userSalesperson.getSalespersonId()));
                }
                Alert alert1 = new Alert(Alert.AlertType.INFORMATION);
                alert1.setTitle("Deletion Confirmation");
                alert1.setContentText("The selected appointment has been deleted");
                alert1.show();
            }
        }
    }

    /** This method is an event handler on the "Create New Appointment" button.
     * When clicked, the button redirects the program to the SalespersonCreateEditAppointmentScreen.
     * @param actionEvent Passed from the On Action event listener on the "Create New Appointment" button.
     * @throws IOException Exception gets thrown if load() cannot locate the FXML file
     */
    public void toCreateAppointment(ActionEvent actionEvent) throws IOException {
        if(userSalesperson.getSalespersonClients().isEmpty()) {
            Alert alert1 = new Alert(Alert.AlertType.INFORMATION);
            alert1.setTitle("No Clients Exist");
            alert1.setContentText("You must enter at least 1 client in order to schedule an appointment. ");
            alert1.show();
            return;
        }

        SalespersonCreateEditAppointmentController.setLabelBoolean(true);

        Parent root = FXMLLoader.load(Objects.requireNonNull(getClass().getResource("/view/SalespersonCreateEditAppointmentScreen.fxml")));
        Stage stage = (Stage) ((Node) (actionEvent.getSource())).getScene().getWindow();
        Scene scene = new Scene(root, 600, 500);
        stage.setScene(scene);
        stage.setTitle("Create Appointment Screen");
    }

    /** This method is an event handler on the "Edit Appointment Button".
     * When clicked, the button redirects the program to the SalespersonCreateEditAppointmentScreen.
     * @param actionEvent Passed from the On Action event listener on the Edit Appointment button.
     * @throws IOException Exception gets thrown if load() cannot locate the FXML file
     */
    public void toEditAppointment(ActionEvent actionEvent) throws IOException {
        if(userSalesperson.getSalespersonClients().isEmpty()) {
            Alert alert1 = new Alert(Alert.AlertType.INFORMATION);
            alert1.setTitle("No Clients Exist");
            alert1.setContentText("You must enter at least 1 client in order to schedule an appointment. ");
            alert1.show();
            return;
        }
        if(appointmentsTable.getSelectionModel().getSelectedItem() != null)  {
            if(appointmentsTable.getSelectionModel().getSelectedItem().getStart().isBefore(LocalDateTime.now())) {
                Alert alert1 = new Alert(Alert.AlertType.INFORMATION);
                alert1.setTitle("Appointment Passed");
                alert1.setContentText("An appointment that has already been completed cannot be edited.");
                alert1.show();
                return;
            }
            SalespersonCreateEditAppointmentController.setTempAppointment(appointmentsTable.getSelectionModel().getSelectedItem());
        }
        else {
            Alert alert1 = new Alert(Alert.AlertType.INFORMATION);
            alert1.setTitle("No Selection Made");
            alert1.setContentText("Please select an appointment to update.");
            alert1.show();
            return;
        }

        Parent root = FXMLLoader.load(Objects.requireNonNull(getClass().getResource("/view/SalespersonCreateEditAppointmentScreen.fxml")));
        Stage stage = (Stage) ((Node) (actionEvent.getSource())).getScene().getWindow();
        Scene scene = new Scene(root, 600, 500);
        stage.setScene(scene);
        stage.setTitle("Edit Appointment Screen");
    }

    /** This method is an event handler on the "Delete Client" button.
     * When clicked, an alert will pop up asking the User to verify that they wish to delete the Client and all
     * associated Appointments. Upon confirmation, the Client will be deleted from the database and the display table
     * updated.
     * @param actionEvent Passed from the On Action event listener on the "Delete Client" button.
     * @throws SQLException Exception gets thrown if the SQL code does not compute properly.
     */
    public void onDeleteClient(ActionEvent actionEvent) throws SQLException {
        Client selectedClient = clientsTable.getSelectionModel().getSelectedItem();

        if (selectedClient == null) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("No Client Selected");
            alert.setContentText("Please select the client to be deleted");
            alert.show();
        } else {
            Alert alert = new Alert(Alert.AlertType.CONFIRMATION, "Permanently delete selected customer and all associated appointments?");
            Optional<ButtonType> result = alert.showAndWait();

            if (result.isPresent() && result.get() == ButtonType.OK) {
                int clientId = selectedClient.getClientId();
                DBAppointments.deleteAppointmentsByClient(clientId);
                DBClients.deleteClient(clientId);
                DBClients.getAllClients();
                DBAppointments.getEveryAppointment();
                userSalesperson.setSalespersonClients();
                userSalesperson.setTotalClients();
                userSalesperson.setSalespersonAppointments();
                userSalesperson.setScheduledAppointments();
                userSalesperson.setCompletedAppointments();
                clientsTable.setItems(userSalesperson.getSalespersonClients());
                if(appointmentsToggleGroup.getSelectedToggle() == upcomingAppointmentsRadioButton) {
                    appointmentsTable.setItems(DBAppointments.getUpcomingAppointments(userSalesperson.getSalespersonId()));
                }
                else {
                    appointmentsTable.setItems(DBAppointments.getEveryAppointment(userSalesperson.getSalespersonId()));
                }
                totalScheduledTextField.setText(Integer.toString(userSalesperson.getTotalScheduledAppointments()));
                totalCompletedTextField.setText(Integer.toString(userSalesperson.getTotalCompletedAppointments()));
                totalAppointmentsTextField.setText(Integer.toString(userSalesperson.getTotalAllAppointments()));
                totalClientsTextField.setText(Integer.toString(userSalesperson.getTotalClients()));


                Alert alert1 = new Alert(Alert.AlertType.INFORMATION);
                alert1.setTitle("Deletion Confirmation");
                alert1.setContentText("The selected customer and all associated appointments have been deleted");
                alert1.show();
            }
        }
    }


    /** This method is an event handler on the "Create New Client" button.
     * When clicked, the button redirects the program to the SalespersonCreateEditClientScreen.
     * @param actionEvent Passed from the On Action event listener on the "New Client" button.
     * @throws IOException Exception gets thrown if load() cannot locate the FXML file
     */
    public void toCreateClient(ActionEvent actionEvent) throws IOException {
        SalespersonCreateEditClientController.setLabelBoolean(false);

        Parent root = FXMLLoader.load(Objects.requireNonNull(getClass().getResource("/view/SalespersonCreateEditClientScreen.fxml")));
        Stage stage = (Stage) ((Node) (actionEvent.getSource())).getScene().getWindow();
        Scene scene = new Scene(root, 600, 500);
        stage.setScene(scene);
        stage.setTitle("Create New Client Screen");
        stage.show();

    }

    /** This method is an event handler on the Edit Client Info button.
     * When clicked, the button redirects the program to the AddModifyCustomerScreen.
     * @param actionEvent Passed from the On Action event listener on the Edit Client Info button.
     * @throws IOException Exception gets thrown if load() cannot locate the FXML file
     */
    public void toEditClient(ActionEvent actionEvent) throws IOException {
        if (clientsTable.getSelectionModel().getSelectedItem() != null) {
            SalespersonCreateEditClientController.setLabelBoolean(true);
            SalespersonCreateEditClientController.setPassedClient(clientsTable.getSelectionModel().getSelectedItem());

            Parent root = FXMLLoader.load(Objects.requireNonNull(getClass().getResource("/view/SalespersonCreateEditClientScreen.fxml")));
            Stage stage = (Stage) ((Node) (actionEvent.getSource())).getScene().getWindow();
            Scene scene = new Scene(root, 600, 500);
            stage.setScene(scene);
            stage.setTitle("Edit Client Info Screen");
            stage.show();
        } else {
            Alert alert1 = new Alert(Alert.AlertType.INFORMATION);
            alert1.setTitle("No Selection Made");
            alert1.setContentText("Please select a client to update.");
            alert1.show();
        }
    }
}
