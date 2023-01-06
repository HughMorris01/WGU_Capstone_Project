package controller;

import database.DBCountries;
import database.DBCustomers;
import database.DBFirst_Level_Divisions;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.stage.Stage;
import model.Country;
import model.Customer;
import model.Division;

import java.io.IOException;
import java.net.URL;
import java.sql.SQLException;
import java.util.ResourceBundle;


/** This is the controller class for the AddModifyCustomerScreen.fxml document and is not meant to be instantiated.
 * The class will either load the form with Customer data passed from the previous CustomerScreen or will begin as blank
 * text fields and combo boxes for the User to enter the Customer data into.
 * @author Gregory Farrell
 * @version 1.0
 * */
public class AddModifyCustomerScreenController implements Initializable {
    /** Static Customer member used to receive a Customer object passed from the Customers table */
    public static Customer tempCustomer = null;
    /** Static int member used to store the static Customer member's index in the static observable list in the DBCustomers class */
    public static int tempCustomerIndex;
    /** Static boolean used to toggle the heading label on the screen */
    public static boolean labelBoolean = false;
    /** Heading Label that adjusts depending on if it is a new or existing customer being entered */
    public Label screenLabel;
    /** Disabled TextField used to display the Customer ID, if available. */
    public TextField customerIdTextField;
    /** TextField used to enter and display the Customer name. */
    public TextField customerNameTextField;
    /** TextField used to enter and display the Customer address. */
    public TextField addressTextField;
    /** TextField used to enter and display the Customer postal code. */
    public TextField postalCodeTextField;
    /** TextField used to enter and display the Customer phone number. */
    public TextField phoneNumberTextField;
    /** ComboBox used to select the Customer's Country. */
    public ComboBox<Country> countryComboBox;
    /** ComboBox used to select the Customer's Division. */
    public ComboBox<Division> divisionComboBox;
    public ObservableList<Country> localCountryList;
    /** Button that saves the Customer info to the database. */
    public Button submitButton;

    /** This method is called by the FXMLLoader.load() call contained in either the addCustomer() or modifyCustomer() methods of the
     * CustomerScreenController class.
     * If modifying a Customer record, the selected Customer's data is passed from the previous screen into the text fields,
     * otherwise they load blank.
     * @param resourceBundle An unreferenced ResourceBundle object passed automatically
     * @param url An unreferenced URL object passed automatically
     * */
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        if (!labelBoolean) {
            screenLabel.setText("New Customer");
        } else {
            screenLabel.setText("Modify Customer");
        }
        if (tempCustomer != null) {
            customerIdTextField.setText(String.valueOf(tempCustomer.getCustomerId()));
            customerNameTextField.setText(tempCustomer.getCustomerName());
            addressTextField.setText(tempCustomer.getAddress());
            postalCodeTextField.setText(tempCustomer.getPostalCode());
            phoneNumberTextField.setText(tempCustomer.getPhone());
            countryComboBox.setValue(tempCustomer.getDivision().getCountry());
            divisionComboBox.setValue(tempCustomer.getDivision());
            divisionComboBox.setDisable(false);
        }

        localCountryList = DBCountries.getAllCountries();
        countryComboBox.setItems(localCountryList);


        if (countryComboBox.getSelectionModel().getSelectedItem() != null) {
            Country selectedCountry = countryComboBox.getSelectionModel().getSelectedItem();

            if (selectedCountry.getCountryId() == 1) {
            DBFirst_Level_Divisions.getUSDivisions();
            divisionComboBox.setItems(DBFirst_Level_Divisions.uSDivisionsList);
            divisionComboBox.setDisable(false);
            }
            else if (selectedCountry.getCountryId() == 2) {
            DBFirst_Level_Divisions.getUKDivisions();
            divisionComboBox.setItems(DBFirst_Level_Divisions.uKDivisionsList);
            divisionComboBox.setDisable(false);
            }
            else {
            DBFirst_Level_Divisions.getCanadaDivisions();
            divisionComboBox.setItems(DBFirst_Level_Divisions.canadaDivisionsList);
            divisionComboBox.setDisable(false);
            }
        }
    }

    /** This method is an event handler for the Back button that sends the program back to the CustomerScreen.
     * The method loads the FXML document for the CustomerScreen, passes that to a new scene and then sets the
     * stage with the new scene.
     * @param actionEvent Passed from the On Action event listener on the Back button.
     * @throws IOException The FXMLLoader.load() call will throw this exception if the FXML document can't be found.
     */
    public void toCustomerScreen(ActionEvent actionEvent) throws IOException {
        tempCustomer = null;

        Parent root = FXMLLoader.load(getClass().getResource("/view/CustomerScreen.fxml"));
        Stage stage = (Stage) ((Node) (actionEvent.getSource())).getScene().getWindow();
        Scene scene = new Scene(root, 600, 450);
        stage.setScene(scene);
        stage.setTitle("Customer Screen");
        stage.show();
    }

    /** This method is an event handler for the Country combo box.
     * The method loads the data for the Division combo box, determined by the Country selection.
     * @param actionEvent Passed from the On Action event listener on the Country combo box.
     */
    public void onCountrySelect(ActionEvent actionEvent) {
            Country selectedCountry = countryComboBox.getSelectionModel().getSelectedItem();
            if (selectedCountry.getCountryId() == 1) {
                DBFirst_Level_Divisions.getUSDivisions();
                divisionComboBox.setItems(DBFirst_Level_Divisions.uSDivisionsList);
                divisionComboBox.setDisable(false);
                divisionComboBox.setValue(null);
            }
            else if (selectedCountry.getCountryId() == 2) {
                DBFirst_Level_Divisions.getUKDivisions();
                divisionComboBox.setItems(DBFirst_Level_Divisions.uKDivisionsList);
                divisionComboBox.setDisable(false);
                divisionComboBox.setValue(null);
            }
            else {
                DBFirst_Level_Divisions.getCanadaDivisions();
                divisionComboBox.setItems(DBFirst_Level_Divisions.canadaDivisionsList);
                divisionComboBox.setDisable(false);
                divisionComboBox.setValue(null);
            }
        }

    /** This method is an event handler for the Save button that saves the entered Customer data to the database and redirects
     * the application back to the CustomerScreen.
     * The method gathers the data from the form, executes the SQL to update the database and then loads the FXML document
     * for the CustomerScreen.
     * @param actionEvent Passed from the On Action event listener on the Save button.
     * @throws IOException The FXMLLoader.load() call will throw this exception if the FXML document can't be found.
     * @throws SQLException This execution will be thrown if the SQL does not execute properly.
     */
    public void onSubmitButtonClick(ActionEvent actionEvent) throws SQLException, IOException {

        if (customerIdTextField.getText() == "") {
            String tempText = customerNameTextField.getText();
            if (tempText.isEmpty()) {
                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.setTitle("Blank Field");
                alert.setContentText("A Customer Name Must Be Entered");
                alert.show();
                return;
            }
            String customerName = customerNameTextField.getText();
            String address = addressTextField.getText();
            String postalCode = postalCodeTextField.getText();
            String phone = phoneNumberTextField.getText();
            Division division = divisionComboBox.getSelectionModel().getSelectedItem();
            int divisionId = division.getDivisionId();

            DBCustomers.insertCustomer(customerName, address, postalCode, phone, divisionId);

            Parent root = FXMLLoader.load(getClass().getResource("/view/CustomerScreen.fxml"));
            Stage stage = (Stage) ((Node) (actionEvent.getSource())).getScene().getWindow();
            Scene scene = new Scene(root, 600, 450);
            stage.setScene(scene);
            stage.setTitle("Customer Screen");
            stage.show();
        }
        else {
           int customerId = Integer.parseInt(customerIdTextField.getText());
           String customerName = customerNameTextField.getText();
           String address = addressTextField.getText();
           String postalCode = postalCodeTextField.getText();
           String phone = phoneNumberTextField.getText();
           Division division = divisionComboBox.getSelectionModel().getSelectedItem();
           int divisionId = division.getDivisionId();

           DBCustomers.updateCustomer(customerId, customerName, address, postalCode, phone, divisionId);

           tempCustomer = null;

           Parent root = FXMLLoader.load(getClass().getResource("/view/CustomerScreen.fxml"));
           Stage stage = (Stage) ((Node) (actionEvent.getSource())).getScene().getWindow();
           Scene scene = new Scene(root, 600, 450);
           stage.setScene(scene);
           stage.setTitle("Customer Screen");
           stage.show();
        }
    }
}
