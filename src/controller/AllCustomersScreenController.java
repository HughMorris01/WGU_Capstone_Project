package controller;

import database.DBAppointments;
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
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;
import model.Client;
import model.Region;
import model.Salesperson;


import java.io.IOException;
import java.net.URL;
import java.sql.SQLException;
import java.util.Objects;
import java.util.Optional;
import java.util.ResourceBundle;

/** This is the controller class for the AllCustomersScreen.fxml document and is not meant to be instantiated.
 *  This class populates the data for the Customers table, as well as provide the functionality for the buttons on the screen.
 * @author Gregory Farrell
 * @version 1.1
 * */
public class AllCustomersScreenController implements Initializable {
    /** Text field used to search the Client records by name or customerID. */
    public TextField searchTextField;
    /** TableView populated Client records from the database. */
    public TableView<Client> customerTable;
    /** TableColumn for Client ID. */
    public TableColumn<Client, Integer> idCol;
    /** TableColumn for Client's name. */
    public TableColumn<Client, String> nameCol;
    /** TableColumn for Client's address. */
    public TableColumn<Client, String> addressCol;
    /** TableColumn for Client's zip code. */
    public TableColumn<Client, String> zipCodeCol;
    /** TableColumn for Client's state abbreviation. */
    public TableColumn<Client, String> stateCol;
    /** TableColumn for Client's phone number. */
    public TableColumn<Client, String> phoneCol;
    /** TableColumn for Client's email */
    public TableColumn<Client, String> emailCol;
    /** TableColumn for Salesperson associated with Client. */
    public TableColumn<Client, Salesperson> salespersonCol;
    /** TableColumn for Region associated with Client. */
    public TableColumn<Client, Region> regionCol;
    /** Combo box used to sort customers by region. */
    public ComboBox<Region> regionComboBox;
    /** Radio button used to load all customers into the table. */
    public RadioButton allRegionsRadioButton;

    /** This method is called by the FXMLLoader.load() call contained in the toAllCustomersScreen() method of the
     * AdminHomeScreenController class. The method populates the table with every Client that is in the database.
     * @param resourceBundle An unreferenced ResourceBundle object passed automatically
     * @param url An unreferenced URL object passed automatically
     */
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        regionComboBox.setItems(Region.allRegionsList);

        idCol.setCellValueFactory(new PropertyValueFactory<>("customerId"));
        idCol.setStyle("-fx-alignment: CENTER;");
        nameCol.setCellValueFactory(new PropertyValueFactory<>("customerFullName"));
        nameCol.setStyle("-fx-alignment: CENTER;");
        addressCol.setCellValueFactory(new PropertyValueFactory<>("address"));
        addressCol.setStyle("-fx-alignment: CENTER;");
        zipCodeCol.setCellValueFactory(new PropertyValueFactory<>("zipCode"));
        zipCodeCol.setStyle("-fx-alignment: CENTER;");
        stateCol.setCellValueFactory(new PropertyValueFactory<>("stateAbbreviation"));
        stateCol.setStyle("-fx-alignment: CENTER;");
        phoneCol.setCellValueFactory(new PropertyValueFactory<>("phone"));
        phoneCol.setStyle("-fx-alignment: CENTER;");
        emailCol.setCellValueFactory(new PropertyValueFactory<>("email"));
        emailCol.setStyle("-fx-alignment: CENTER;");
        salespersonCol.setCellValueFactory(new PropertyValueFactory<>("customerSalesperson"));
        salespersonCol.setStyle("-fx-alignment: CENTER;");
        regionCol.setCellValueFactory(new PropertyValueFactory<>("customerRegion"));
        regionCol.setStyle("-fx-alignment: CENTER;");

        customerTable.setItems(Client.allClientList);
    }

    /** This method is an event handler on the searchTextField.
     * Search function searches the allCustomersList for matches containing either the string or ID entered.
     * @param actionEvent Passed from the On Action event listener on the text field.
     */
    public void searchCustomers(ActionEvent actionEvent) {
        try {
            String searchString = searchTextField.getText();
            ObservableList<Client> customersFoundList = Client.searchCustomers(searchString);

            if (customersFoundList.size() == 0) {
                int searchInt = Integer.parseInt(searchTextField.getText());
                customersFoundList = Client.searchCustomers(searchInt);
            }
            if (customersFoundList.isEmpty()) {
                searchTextField.setText("");
                Alert alert1 = new Alert(Alert.AlertType.INFORMATION);
                alert1.setTitle("No Results Found");
                alert1.setContentText("There are no Customers matching that search criteria.");
                alert1.show();
                return;
            }
            customerTable.setItems(customersFoundList);
            searchTextField.setText("");
        } catch (NumberFormatException exception) {
            searchTextField.setText("");
            Alert alert1 = new Alert(Alert.AlertType.INFORMATION);
            alert1.setTitle("No Results Found");
            alert1.setContentText("There are no Customers matching that search criteria.");
            alert1.show();
        }
    }

    /** This method is an event handler on the Delete Client button.
     * When clicked, an alert will pop up asking the User to verify that they wish to delete the Client and all
     * associated Appointments. Upon confirmation, the Client will be deleted from the database and the display table
     * updated.
     * @param actionEvent Passed from the On Action event listener on the Delete Client button.
     * @throws SQLException Exception gets thrown if the SQL code does not compute properly.
     */
    public void onDeleteCustomer(ActionEvent actionEvent) throws SQLException {
        Client selectedClient = customerTable.getSelectionModel().getSelectedItem();

        if (selectedClient == null) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("No Client Selected");
            alert.setContentText("Please select the customer to be deleted");
            alert.show();
        } else {
            Alert alert = new Alert(Alert.AlertType.CONFIRMATION, "Permanently delete selected customer and all associated appointments?");
            Optional<ButtonType> result = alert.showAndWait();

            if (result.isPresent() && result.get() == ButtonType.OK) {
                int customerId = selectedClient.getCustomerId();
                DBAppointments.deleteAppointmentByCustomer(customerId);

                DBCustomers.deleteCustomer(customerId);
                customerTable.setItems(DBCustomers.getAllCustomers());

                Alert alert1 = new Alert(Alert.AlertType.INFORMATION);
                alert1.setTitle("Deletion Confirmation");
                alert1.setContentText("The selected customer and all associated appointments have been deleted");
                alert1.show();
            }
        }
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
            stage.setTitle("Create New Client Screen");
            stage.show();

    }

    /** This method is an event handler on the Edit Client Info button.
     * When clicked, the button redirects the program to the AddModifyCustomerScreen.
     * @param actionEvent Passed from the On Action event listener on the Edit Client Info button.
     * @throws IOException Exception gets thrown if load() cannot locate the FXML file
     */
    public void toAdminEditCustomerScreen (ActionEvent actionEvent) throws IOException {
        if (customerTable.getSelectionModel().getSelectedItem() != null) {
            AdminCreateEditCustomerScreenController.labelBoolean = true;
            AdminCreateEditCustomerScreenController.tempClient = customerTable.getSelectionModel().getSelectedItem();

            Parent root = FXMLLoader.load(Objects.requireNonNull(getClass().getResource("/view/AdminCreateEditCustomerScreen.fxml")));
            Stage stage = (Stage) ((Node) (actionEvent.getSource())).getScene().getWindow();
            Scene scene = new Scene(root, 600, 500);
            stage.setScene(scene);
            stage.setTitle("Edit Client Info Screen");
            stage.show();
        } else {
                Alert alert1 = new Alert(Alert.AlertType.INFORMATION);
                alert1.setTitle("No Selection Made");
                alert1.setContentText("Please select a Client to update.");
                alert1.show();
            }
    }

    /** This method is an event handler for the Back button that sends the program back to the AdminHomeScreen.
     * The method loads the FXML document for the AdminHomeScreen, passes that to a new scene and then sets the
     * stage with the new scene.
     * @param actionEvent Passed from the On Action event listener on the Back button.
     * @throws IOException The FXMLLoader.load() call will throw this exception if the FXML document can't be found. */
    public void toAdminHomeScreen(ActionEvent actionEvent) throws IOException {
        Parent root = FXMLLoader.load(Objects.requireNonNull(getClass().getResource("/view/AdminHomeScreen.fxml")));
        Stage stage = (Stage) ((Node) (actionEvent.getSource())).getScene().getWindow();
        Scene scene = new Scene(root, 1000, 500);
        stage.setScene(scene);
        stage.setTitle("Administrator Home Screen");
    }

    /** This method sorts the allCustomersList by region and displays the results in the table.
     * @param actionEvent Passed from the On Action event listener on the regionComboBox. */
    public void sortCustomersByRegion(ActionEvent actionEvent) {
        if (regionComboBox.getSelectionModel().getSelectedItem() != null) {
        ObservableList<Client> regionClients = FXCollections.observableArrayList();
        int selectedRegionId = regionComboBox.getSelectionModel().getSelectedItem().getRegionId();

        for(Client client : Client.allClientList) {
            if(client.getRegionId() == selectedRegionId) {
                regionClients.add(client);
            }
        }
        customerTable.setItems(regionClients);
        allRegionsRadioButton.setSelected(false);
        }
    }

    /** This method restores the customer table to display customers from all regions/
     * @param actionEvent Passed from the On Action event listener on allRegionsRadioButton. */
    public void displayAllRegions(ActionEvent actionEvent) {
        customerTable.setItems(Client.allClientList);
        regionComboBox.setValue(null);
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


