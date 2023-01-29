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
import javafx.stage.Stage;
import model.Client;
import model.Region;
import model.Salesperson;
import model.State;

import java.io.IOException;
import java.net.URL;
import java.sql.SQLException;
import java.util.Objects;
import java.util.Optional;
import java.util.ResourceBundle;

public class SalespersonCreateEditClientController implements Initializable {
    private static Client passedClient = null;
    private static boolean labelBoolean = true;
    public Label screenLabel;

    public TextField clientIdTextField;
    public TextField clientFirstNameTextField;
    public TextField clientLastNameTextField;
    public TextField addressTextField;
    public TextField zipCodeTextField;
    public ComboBox<State> stateComboBox;
    public TextField phoneNumberTextField;
    public TextField emailTextField;
    public ComboBox<Region> regionComboBox;
    public ComboBox<Salesperson> salespersonComboBox;

    /** This method is called by the FXMLLoader.load() call contained in the previous screen.  If modifying a Client record,
     * the selected Client's data is passed into the text fields, otherwise they load blank.
     * @param resourceBundle An unreferenced ResourceBundle object passed automatically
     * @param url An unreferenced URL object passed automatically */
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        if (!labelBoolean) {
            screenLabel.setText("New Client Info");
        } else {
            screenLabel.setText("Edit Client Info");
        }

        regionComboBox.setValue(SalespersonHomeScreenController.getUserSalesperson().getRegion());
        salespersonComboBox.setValue(SalespersonHomeScreenController.getUserSalesperson());
        ObservableList<State> regionStates = FXCollections.observableArrayList();
        int regionId = SalespersonHomeScreenController.getUserSalesperson().getRegionId();
        for (State state : State.allStatesList) {
            if (state.getRegionId() == regionId) {
                regionStates.add(state);
            }
        }
        stateComboBox.setItems(regionStates);

        if (passedClient != null) {
            clientIdTextField.setText(String.valueOf(passedClient.getClientId()));
            clientFirstNameTextField.setText(passedClient.getClientFirstName());
            clientLastNameTextField.setText(passedClient.getClientLastName());
            addressTextField.setText(passedClient.getAddress());
            zipCodeTextField.setText(passedClient.getZipCode());
            stateComboBox.setValue(passedClient.getClientState());
            phoneNumberTextField.setText(passedClient.getPhone());
            emailTextField.setText(passedClient.getEmail());

        } else {
            stateComboBox.setValue(null);
        }
    }

    /** This method is an event handler on the "Back" button.
     * When clicked, the button redirects the program to the SalespersonHomeScreen.
     * @param actionEvent Passed from the On Action event listener on the "Back" button.
     * @throws IOException Exception gets thrown if load() cannot locate the FXML file
     */
    public void toSalespersonHomeScreen(ActionEvent actionEvent) throws IOException {
        passedClient = null;
        labelBoolean = false;

        Parent root = FXMLLoader.load(Objects.requireNonNull(getClass().getResource("/view/SalespersonHomeScreen.fxml")));
        Stage stage = (Stage) ((Node) (actionEvent.getSource())).getScene().getWindow();
        Scene scene = new Scene(root, 1000, 500);
        stage.setScene(scene);
        stage.setTitle("Salesperson Home Screen");

    }

    public static void setPassedClient(Client clientParam) { passedClient = clientParam; }
    public static void setLabelBoolean(Boolean bool) { labelBoolean = bool; }

    /** This method is an event handler for the "Save" button that saves the entered Client info to the database and
     * redirects the application back to the SalespersonHomeScreen.
     * @param actionEvent Passed from the On Action event listener on the Save button.
     * @throws IOException  The FXMLLoader.load() call will throw this exception if the FXML document can't be found.
     * @throws SQLException This execution will be thrown if the SQL does not execute properly.
     */
    public void onSaveButtonClick(ActionEvent actionEvent) throws SQLException, IOException {

        if (clientIdTextField.getText().isEmpty()) {
            String firstNameText = clientFirstNameTextField.getText();
            if (firstNameText.isEmpty()) {
                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.setTitle("Blank Field");
                alert.setContentText("A client first name must be entered.");
                alert.show();
                return;
            }
            String lastNameText = clientLastNameTextField.getText();
            if (lastNameText.isEmpty()) {
                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.setTitle("Blank Field");
                alert.setContentText("A Client last name must be entered.");
                alert.show();
                return;
            }
            String addressText = addressTextField.getText();
            if (addressText.isEmpty()) {
                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.setTitle("Blank Field");
                alert.setContentText("A Client address must be entered.");
                alert.show();
                return;
            }
            String zipCodeText = zipCodeTextField.getText();
            if (zipCodeText.isEmpty()) {
                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.setTitle("Blank Field");
                alert.setContentText("A Client zip code must be entered.");
                alert.show();
                return;
            }
            String phoneText = phoneNumberTextField.getText();
            String emailText = emailTextField.getText();
            State state = stateComboBox.getSelectionModel().getSelectedItem();
            if (state == null) {
                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.setTitle("Blank Field");
                alert.setContentText("A state must be selected. ");
                alert.show();
                return;
            }
            Region region = regionComboBox.getSelectionModel().getSelectedItem();
            Salesperson salesperson = salespersonComboBox.getSelectionModel().getSelectedItem();

            DBClients.insertClient(firstNameText, lastNameText, addressText, zipCodeText, phoneText, emailText,
                    salesperson.getSalespersonId(), state.getStateId(), region.getRegionId());
            DBClients.getAllClients();
            DBAppointments.getEveryAppointment();
            SalespersonHomeScreenController.userSalesperson.setSalespersonClients();
            SalespersonHomeScreenController.userSalesperson.setTotalClients();
            SalespersonHomeScreenController.userSalesperson.setSalespersonAppointments();
            SalespersonHomeScreenController.userSalesperson.setScheduledAppointments();
            SalespersonHomeScreenController.userSalesperson.setCompletedAppointments();

            Parent root = FXMLLoader.load(Objects.requireNonNull(getClass().getResource("/view/SalespersonHomeScreen.fxml")));
            Stage stage = (Stage) ((Node) (actionEvent.getSource())).getScene().getWindow();
            Scene scene = new Scene(root, 1000, 500);
            stage.setScene(scene);
            stage.setTitle("Salesperson Home Screen");
            stage.show();
        } else {
            int clientId = Integer.parseInt(clientIdTextField.getText());
            String firstNameText = clientFirstNameTextField.getText();
            if (firstNameText.isEmpty()) {
                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.setTitle("Blank Field");
                alert.setContentText("A Client first name must be entered.");
                alert.show();
                return;
            }
            String lastNameText = clientLastNameTextField.getText();
            if (lastNameText.isEmpty()) {
                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.setTitle("Blank Field");
                alert.setContentText("A Client last name must be entered.");
                alert.show();
                return;
            }
            String addressText = addressTextField.getText();
            if (addressText.isEmpty()) {
                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.setTitle("Blank Field");
                alert.setContentText("A Client address must be entered.");
                alert.show();
                return;
            }
            String zipCodeText = zipCodeTextField.getText();
            if (zipCodeText.isEmpty()) {
                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.setTitle("Blank Field");
                alert.setContentText("A Client zip code must be entered.");
                alert.show();
                return;
            }
            String phoneText = phoneNumberTextField.getText();
            String emailText = emailTextField.getText();
            State state = stateComboBox.getSelectionModel().getSelectedItem();
            Region region = regionComboBox.getSelectionModel().getSelectedItem();
            Salesperson salesperson = salespersonComboBox.getSelectionModel().getSelectedItem();

            DBClients.updateClient(clientId, firstNameText, lastNameText, addressText, zipCodeText, phoneText, emailText,
                    salesperson.getSalespersonId(), state.getStateId(), region.getRegionId());
            DBClients.getAllClients();
            DBAppointments.getEveryAppointment();
            SalespersonHomeScreenController.userSalesperson.setSalespersonClients();
            SalespersonHomeScreenController.userSalesperson.setTotalClients();
            SalespersonHomeScreenController.userSalesperson.setSalespersonAppointments();
            SalespersonHomeScreenController.userSalesperson.setScheduledAppointments();
            SalespersonHomeScreenController.userSalesperson.setCompletedAppointments();
            passedClient = null;

            Parent root = FXMLLoader.load(Objects.requireNonNull(getClass().getResource("/view/SalespersonHomeScreen.fxml")));
            Stage stage = (Stage) ((Node) (actionEvent.getSource())).getScene().getWindow();
            Scene scene = new Scene(root, 1000, 500);
            stage.setScene(scene);
            stage.setTitle("Salesperson Home Screen");
            stage.show();
        }
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
}
