package controller;

import database.DBAppointments;
import database.DBCustomers;
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
import model.Customer;
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
    /** Text field used to search the Customer records by name or customerID. */
    public TextField searchTextField;
    /** TableView populated Customer records from the database. */
    public TableView<Customer> customerTable;
    /** TableColumn for Customer ID. */
    public TableColumn<Customer, Integer> idCol;
    /** TableColumn for Customer's name. */
    public TableColumn<Customer, String> nameCol;
    /** TableColumn for Customer's address. */
    public TableColumn<Customer, String> addressCol;
    /** TableColumn for Customer's zip code. */
    public TableColumn<Customer, String> zipCodeCol;
    /** TableColumn for Customer's state abbreviation. */
    public TableColumn<Customer, String> stateCol;
    /** TableColumn for Customer's phone number. */
    public TableColumn<Customer, String> phoneCol;
    /** TableColumn for Customer's email */
    public TableColumn<Customer, String> emailCol;
    /** TableColumn for Salesperson associated with Customer. */
    public TableColumn<Customer, Salesperson> salespersonCol;
    /** TableColumn for Region associated with Customer. */
    public TableColumn<Customer, Region> regionCol;

    /** This method is called by the FXMLLoader.load() call contained in the toAllCustomersScreen() method of the
     * AdminHomeScreenController class. The method populates the table with every Customer that is in the database.
     * @param resourceBundle An unreferenced ResourceBundle object passed automatically
     * @param url An unreferenced URL object passed automatically
     */
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        idCol.setCellValueFactory(new PropertyValueFactory<>("customerId"));
        nameCol.setCellValueFactory(new PropertyValueFactory<>("customerFullName"));
        addressCol.setCellValueFactory(new PropertyValueFactory<>("address"));
        zipCodeCol.setCellValueFactory(new PropertyValueFactory<>("zipCode"));
        stateCol.setCellValueFactory(new PropertyValueFactory<>("stateAbbreviation"));
        phoneCol.setCellValueFactory(new PropertyValueFactory<>("phone"));
        emailCol.setCellValueFactory(new PropertyValueFactory<>("email"));
        salespersonCol.setCellValueFactory(new PropertyValueFactory<>("customerSalesperson"));
        regionCol.setCellValueFactory(new PropertyValueFactory<>("customerRegion"));

        customerTable.setItems(Customer.allCustomerList);
    }

    /** This method is an event handler on the searchTextField.
     * Search function searches the allCustomersList for matches containing either the string or ID entered.
     * @param actionEvent Passed from the On Action event listener on the text field.
     */
    public void searchCustomers(ActionEvent actionEvent) {
        try {
            String searchString = searchTextField.getText();
            ObservableList<Customer> customersFoundList = Customer.searchCustomers(searchString);

            if (customersFoundList.size() == 0) {
                int searchInt = Integer.parseInt(searchTextField.getText());
                customersFoundList = Customer.searchCustomers(searchInt);
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

    /** This method is an event handler on the Delete Customer button.
     * When clicked, an alert will pop up asking the User to verify that they wish to delete the Customer and all
     * associated Appointments. Upon confirmation, the Customer will be deleted from the database and the display table
     * updated.
     * @param actionEvent Passed from the On Action event listener on the Delete Customer button.
     * @throws SQLException Exception gets thrown if the SQL code does not compute properly.
     */
    public void onDeleteCustomer(ActionEvent actionEvent) throws SQLException {
        Customer selectedCustomer = customerTable.getSelectionModel().getSelectedItem();

        if (selectedCustomer == null) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("No Customer Selected");
            alert.setContentText("Please select the customer to be deleted");
            alert.show();
        } else {
            Alert alert = new Alert(Alert.AlertType.CONFIRMATION, "Permanently delete selected customer and all associated appointments?");
            Optional<ButtonType> result = alert.showAndWait();

            if (result.isPresent() && result.get() == ButtonType.OK) {
                int customerId = selectedCustomer.getCustomerId();
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


    /** This method is an event handler on the New Customer button.
     * When clicked, the button redirects the program to the AddModifyCustomerScreen.
     * @param actionEvent Passed from the On Action event listener on the New Customer button.
     * @throws IOException Exception gets thrown if load() cannot locate the FXML file
     */
    public void toAdminCreateCustomerScreen(ActionEvent actionEvent) throws IOException {
            AdminCreateEditCustomerScreenController.labelBoolean = false;

            Parent root = FXMLLoader.load(Objects.requireNonNull(getClass().getResource("/view/AdminCreateEditCustomerScreen.fxml")));
            Stage stage = (Stage) ((Node) (actionEvent.getSource())).getScene().getWindow();
            Scene scene = new Scene(root, 600, 500);
            stage.setScene(scene);
            stage.setTitle("Add New Customer Screen");
            stage.show();

    }

    /** This method is an event handler on the Edit Customer Info button.
     * When clicked, the button redirects the program to the AddModifyCustomerScreen.
     * @param actionEvent Passed from the On Action event listener on the Edit Customer Info button.
     * @throws IOException Exception gets thrown if load() cannot locate the FXML file
     */
    public void toAdminEditCustomerScreen (ActionEvent actionEvent) throws IOException {
        if (customerTable.getSelectionModel().getSelectedItem() != null) {
            AdminCreateEditCustomerScreenController.labelBoolean = true;
            AdminCreateEditCustomerScreenController.tempCustomer = customerTable.getSelectionModel().getSelectedItem();

            Parent root = FXMLLoader.load(Objects.requireNonNull(getClass().getResource("/view/AdminCreateEditCustomerScreen.fxml")));
            Stage stage = (Stage) ((Node) (actionEvent.getSource())).getScene().getWindow();
            Scene scene = new Scene(root, 600, 500);
            stage.setScene(scene);
            stage.setTitle("Edit Customer Info Screen");
            stage.show();
        } else {
                Alert alert1 = new Alert(Alert.AlertType.INFORMATION);
                alert1.setTitle("No Selection Made");
                alert1.setContentText("Please select a Customer to update.");
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

}


