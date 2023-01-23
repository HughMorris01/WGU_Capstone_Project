package controller;

import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import model.Customer;
import model.Region;
import model.Salesperson;
import model.State;

import java.io.IOException;
import java.net.URL;
import java.util.Objects;
import java.util.ResourceBundle;

/** This is the controller class for the AdminCreateEditCustomerScreenController.fxml document and is not meant to be instantiated.
 * The class will either load the form with Customer data passed from the previous screen or will begin as blank
 * text fields and combo boxes for the Admin user to enter the Customer data into.
 * @author Gregory Farrell
 * @version 1.1
 * */
public class AdminCreateEditCustomerScreenController implements Initializable {
    /** Static Customer member used to receive a Customer object passed from the allCustomersScreen */
    public static Customer tempCustomer = null;
    /** Static int member used to store the static Customer member's index in the static observable list in the DBCustomers class */
    public static int tempCustomerIndex;
    /** Static boolean used to toggle the heading label on the screen */
    public static boolean labelBoolean = false;
    /** Heading Label that adjusts depending on if it is a new or existing customer being entered */
    public Label screenLabel;
    /** Disabled TextField used to display the Customer ID, if available. */
    public TextField customerIdTextField;
    /** TextField used to enter and display the Customer's first name. */
    public TextField customerFirstNameTextField;
    /** TextField used to enter and display the Customer's last name. */
    public TextField customerLastNameTextField;
    /** TextField used to enter and display the Customer's address. */
    public TextField addressTextField;
    /** TextField used to enter and display the Customer's zip code. */
    public TextField zipCodeTextField;
    /** ComboBox used to select the Customer's state. */
    public ComboBox<State> stateComboBox;
    /** TextField used to enter and display the Customer phone number. */
    public TextField phoneNumberTextField;
    /** TextField used to enter and display the Customer email. */
    public TextField emailTextField;
    /** ComboBox used to select the Customer's Region. */
    public ComboBox<Region> regionComboBox;
    /** ComboBox used to select the Customer's associated salesperson. */
    public ComboBox<Salesperson> salespersonComboBox;
    /** Button that saves the Customer info to the database. */
    public Button submitButton;

    /** This method is called by the FXMLLoader.load() call contained in either the addCustomer() or modifyCustomer()
     * methods of the CustomerScreenController class. If modifying a Customer record, the selected Customer's data is
     * passed from the previous screen into the text fields, otherwise they load blank.
     * @param resourceBundle An unreferenced ResourceBundle object passed automatically
     * @param url An unreferenced URL object passed automatically
     * */
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        if (!labelBoolean) {
            screenLabel.setText("Enter Customer Info");
        } else {
            screenLabel.setText("Edit Customer Info");
        }
        if (tempCustomer != null) {
            customerIdTextField.setText(String.valueOf(tempCustomer.getCustomerId()));
            customerFirstNameTextField.setText(tempCustomer.getCustomerFirstName());
            customerLastNameTextField.setText(tempCustomer.getCustomerLastName());
            addressTextField.setText(tempCustomer.getAddress());
            zipCodeTextField.setText(tempCustomer.getZipCode());
            stateComboBox.setValue(tempCustomer.getCustomerState());
            phoneNumberTextField.setText(tempCustomer.getPhone());
            emailTextField.setText(tempCustomer.getEmail());
            regionComboBox.setValue(tempCustomer.getCustomerRegion());
            salespersonComboBox.setValue(tempCustomer.getCustomerSalesperson());
            salespersonComboBox.setDisable(false);
        }

        regionComboBox.setItems(Region.allRegionsList);
        stateComboBox.setItems(State.allStatesList);
        salespersonComboBox.setItems(Salesperson.allSalespersonsList);
    }

    public void toAllCustomersScreen(ActionEvent actionEvent) throws IOException {
        tempCustomer = null;

        Parent root = FXMLLoader.load(Objects.requireNonNull(getClass().getResource("/view/AllCustomersScreen.fxml")));
        Stage stage = (Stage) ((Node) (actionEvent.getSource())).getScene().getWindow();
        Scene scene = new Scene(root, 1000, 400);
        stage.setScene(scene);
        stage.setTitle("All Customers Screen");
    }
}
