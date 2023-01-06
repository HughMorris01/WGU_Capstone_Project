package controller;

import database.DBAppointments;
import database.DBCustomers;
import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;
import model.Customer;

import java.io.IOException;
import java.net.URL;
import java.sql.SQLException;
import java.util.Optional;
import java.util.ResourceBundle;

/** This is the controller class for the CustomerScreen.fxml document and is not meant to be instantiated.
 *  This class populates the data for the Customers table, as well as provide the functionality for the buttons on the
 *  page.
 * @author Gregory Farrell
 * @version 1.0
 * */
public class CustomerScreenController implements Initializable {
    /** TableView that displays customer information pulled from the database. */
    public TableView<Customer> customersTable;
    /** TableColumn for the Customer ID. */
    public TableColumn<Customer, Integer> idCol;
    /** TableColumn for the Customer name. */
    public TableColumn<Customer, String> nameCol;
    /** TableColumn for the Customer address. */
    public TableColumn<Customer, String> addressCol;
    /** TableColumn for the Customer zip code. */
    public TableColumn<Customer, String> postalCol;
    /** TableColumn for the Customer phone number. */
    public TableColumn<Customer, String> phoneCol;
    /** TableColumn for the Customer Division number. */
    public TableColumn<Customer, Integer> divisionCol;

    /** This method is called by the FXMLLoader.load() call contained in the toCustomerScreen() method of the UserHomeScreenController class.
     * The method populates the table with all of the Customer data that is in the database.
     * @param resourceBundle An unreferenced ResourceBundle object passed automatically
     * @param url An unreferenced URL object passed automatically
     * */
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

        idCol.setCellValueFactory(new PropertyValueFactory<>("customerId"));
        nameCol.setCellValueFactory(new PropertyValueFactory<>("customerName"));
        addressCol.setCellValueFactory(new PropertyValueFactory<>("address"));
        postalCol.setCellValueFactory(new PropertyValueFactory<>("postalCode"));
        phoneCol.setCellValueFactory(new PropertyValueFactory<>("phone"));
        divisionCol.setCellValueFactory(new PropertyValueFactory<>("divisionId"));
        customersTable.setItems(DBCustomers.getAllCustomers());
    }

    /** /** This method is an event handler on the New Customer button.
     * When clicked, the button redirects the program toe the AddModifyCustomerScreen.
     * @param actionEvent Passed from the On Action event listener on the New Customer button.
     * @throws IOException Exception gets thrown if load() cannot locate the FXML file
     * */
    public void onAddCustomer(ActionEvent actionEvent) throws IOException {
        AddModifyCustomerScreenController.labelBoolean = false;

        Parent root = FXMLLoader.load(getClass().getResource("/view/AddModifyCustomerScreen.fxml"));
        Stage stage = (Stage) ((Node) (actionEvent.getSource())).getScene().getWindow();
        Scene scene = new Scene(root, 500, 500);
        stage.setScene(scene);
        stage.setTitle("Add New Customer Screen");
        stage.show();
    }

    /** /** This method is an event handler on the Modify Customer button.
     * When clicked, the button redirects the program to the AddModifyCustomerScreen and passes the data from the highlighted
     * Customer. If no Customer is selected an alert will notify the user they must select a Customer.
     * @param actionEvent Passed from the On Action event listener on the Modify Customer button.
     * @throws IOException Exception gets thrown if load() cannot locate the FXML file.
     * */
    public void onModifyCustomer(ActionEvent actionEvent) throws IOException {
        if (customersTable.getSelectionModel().getSelectedItem() != null ) {
            AddModifyCustomerScreenController.labelBoolean = true;
            AddModifyCustomerScreenController.tempCustomer = customersTable.getSelectionModel().getSelectedItem();
            AddModifyCustomerScreenController.tempCustomerIndex = DBCustomers.getAllCustomers().indexOf(AddModifyCustomerScreenController.tempCustomer);

            Parent root = FXMLLoader.load(getClass().getResource("/view/AddModifyCustomerScreen.fxml"));
            Stage stage = (Stage) ((Node) (actionEvent.getSource())).getScene().getWindow();
            Scene scene = new Scene(root, 500, 500);
            stage.setScene(scene);
            stage.setTitle("Modify Customer Screen");
            stage.show();
        }
        else  {
            Alert alert1 = new Alert(Alert.AlertType.INFORMATION);
            alert1.setTitle("No Selection Made");
            alert1.setContentText("Please select a Customer to update.");
            alert1.show();
        }
    }

    /** /** This method is an event handler on the Delete Customer button.
     * When clicked, an alert will pop up asking the User to verify that they wish to delete the Customer and all
     * associated Appointments. Upon confirmation, the Customer will be deleted from the database and the display table
     * updated.
     * @param actionEvent Passed from the On Action event listener on the Delete Customer button.
     * @throws SQLException Exception gets thrown if the SQL code does not compute properly.
     * */
    public void onDeleteCustomer(ActionEvent actionEvent) throws SQLException {
        Customer selectedCustomer = customersTable.getSelectionModel().getSelectedItem();

        if(selectedCustomer == null) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("No Customer Selected");
            alert.setContentText("Please select the customer to be deleted");
            alert.show();
            return;
        }
        else {
            Alert alert = new Alert(Alert.AlertType.CONFIRMATION, "Permanently delete selected customer and all associated appointments?");
            Optional<ButtonType> result = alert.showAndWait();

            if(result.isPresent()  && result.get() == ButtonType.OK) {
                int customerId = selectedCustomer.getCustomerId();
                DBAppointments.deleteAppointmentByCustomer(customerId);

                DBCustomers.deleteCustomer(customerId);
                customersTable.setItems(DBCustomers.getAllCustomers());

                    Alert alert1 = new Alert(Alert.AlertType.INFORMATION);
                    alert1.setTitle("Deletion Confirmation");
                    alert1.setContentText("The selected customer and all associated appointments have been deleted");
                    alert1.show();
                }
            }
    }

    /** This method is an event handler for the Back button that sends the program back to the UserHomeScreen.
     * The method loads the FXML document for the UserHomeScreen, passes that to a new scene and then sets the
     * stage with the new scene.
     * @param actionEvent Passed from the On Action event listener on the Back button.
     * @throws IOException The FXMLLoader.load() call will throw this exception if the FXML document can't be found.
     */
    public void toUserHomeScreen(ActionEvent actionEvent) throws IOException {
            Parent root = FXMLLoader.load(getClass().getResource("/view/UserHomeScreen.fxml"));
            Stage stage = (Stage) ((Node) (actionEvent.getSource())).getScene().getWindow();
            Scene scene = new Scene(root, 500, 500);
            stage.setScene(scene);
            stage.setTitle("User Home Screen");
            stage.show();
    }
}
