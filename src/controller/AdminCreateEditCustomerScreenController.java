package controller;

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
import javafx.stage.Stage;
import model.Customer;
import model.Region;
import model.Salesperson;
import model.State;

import java.io.IOException;
import java.net.URL;
import java.sql.SQLException;
import java.util.Objects;
import java.util.ResourceBundle;

/** This is the controller class for the AdminCreateEditCustomerScreenController.fxml document and is not meant to be instantiated.
 * The class will either load the form with Customer data passed from the previous screen or will begin as blank
 * text fields and combo boxes for the Admin user to enter the Customer data into.
 * @author Gregory Farrell
 * @version 1.1
 * */
public class AdminCreateEditCustomerScreenController implements Initializable {
    /**
     * Static Customer member used to receive a Customer object passed from the allCustomersScreen
     */
    public static Customer tempCustomer = null;
    /**
     * Static boolean used to toggle the heading label on the screen
     */
    public static boolean labelBoolean = false;
    /**
     * Heading Label that adjusts depending on if it is a new or existing customer being entered
     */
    public Label screenLabel;
    /**
     * Disabled TextField used to display the Customer ID, if available.
     */
    public TextField customerIdTextField;
    /**
     * TextField used to enter and display the Customer's first name.
     */
    public TextField customerFirstNameTextField;
    /**
     * TextField used to enter and display the Customer's last name.
     */
    public TextField customerLastNameTextField;
    /**
     * TextField used to enter and display the Customer's address.
     */
    public TextField addressTextField;
    /**
     * TextField used to enter and display the Customer's zip code.
     */
    public TextField zipCodeTextField;
    /**
     * ComboBox used to select the Customer's state.
     */
    public ComboBox<State> stateComboBox;
    /**
     * TextField used to enter and display the Customer phone number.
     */
    public TextField phoneNumberTextField;
    /**
     * TextField used to enter and display the Customer email.
     */
    public TextField emailTextField;
    /**
     * ComboBox used to select the Customer's Region.
     */
    public ComboBox<Region> regionComboBox;
    /**
     * ComboBox used to select the Customer's associated salesperson.
     */
    public ComboBox<Salesperson> salespersonComboBox;
    /** Button that saves the Customer info to the database. */
    public Button submitButton;



    /**
     * This method is called by the FXMLLoader.load() call contained in either the addCustomer() or modifyCustomer()
     * methods of the CustomerScreenController class. If modifying a Customer record, the selected Customer's data is
     * passed from the previous screen into the text fields, otherwise they load blank.
     *
     * @param resourceBundle An unreferenced ResourceBundle object passed automatically
     * @param url            An unreferenced URL object passed automatically
     */
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        if (!labelBoolean) {
            screenLabel.setText("New Customer Info");
        } else {
            screenLabel.setText("Edit Customer Info");
        }
        regionComboBox.setItems(Region.allRegionsList);
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

            ObservableList<State> regionStates = FXCollections.observableArrayList();
            int regionId = tempCustomer.getRegionId();
            for (State state : State.allStatesList) {
                if (state.getRegionId() == regionId) {
                    regionStates.add(state);
                }
            }
            stateComboBox.setItems(regionStates);
            regionComboBox.setDisable(false);
            salespersonComboBox.setDisable(false);
            ObservableList<Salesperson> regionSalespersons = FXCollections.observableArrayList();
            for (Salesperson salesperson : Salesperson.allSalespersonsList) {
                if (salesperson.getRegionId() == regionId) {
                    regionSalespersons.add(salesperson);
                }
            }
            salespersonComboBox.setItems(regionSalespersons);
        } else {
            regionComboBox.setItems(Region.allRegionsList);
            stateComboBox.setItems(State.allStatesList);
        }
    }

    /**
     * This method is an event handler on the StateComboBox.
     * When a state is initially chosen, it sets the corresponding Region and fills the SalespersonComboBox with the
     * salespersons for that region. It also filters out the remaining states so that only states from that region are
     * available. To get the full list of states back, the region needs to be changed.
     *
     * @param actionEvent Passed from the On Action event listener on the StateComboBox.
     */
    public void onStateSelect(ActionEvent actionEvent) {
            if (regionComboBox.getSelectionModel().getSelectedItem() == null) {
                int regionId = stateComboBox.getSelectionModel().getSelectedItem().getRegionId();
                for (Region region : Region.allRegionsList) {
                    if (region.getRegionId() == regionId) {
                        regionComboBox.setValue(region);
                        break;
                    }
                }

                ObservableList<State> regionStates = FXCollections.observableArrayList();
                for (State state : State.allStatesList) {
                    if (state.getRegionId() == regionId) {
                        regionStates.add(state);
                    }
                }
                stateComboBox.setItems(regionStates);

                ObservableList<Salesperson> regionSalespersons = FXCollections.observableArrayList();
                for (Salesperson salesperson : Salesperson.allSalespersonsList) {
                    if (salesperson.getRegionId() == regionId) {
                        regionSalespersons.add(salesperson);
                    }
                }
                salespersonComboBox.setItems(regionSalespersons);
                regionComboBox.setDisable(false);
                salespersonComboBox.setDisable(false);
                salespersonComboBox.setPromptText("");
            }
        }

    /** This method is an event handler on the RegionComboBox.
     * When the region is changed, it resets the StateComboBox with the states belonging to that region and fills
     * the SalespersonComboBox with the salespersons for that region. The only way to change the StateComboBox items
     * after that is to select a different Region.
     * @param actionEvent Passed from the On Action event listener on the RegionComboBox.
     */
    public void onRegionSelect(ActionEvent actionEvent) {
        int regionId = regionComboBox.getSelectionModel().getSelectedItem().getRegionId();

        ObservableList<State> regionStates = FXCollections.observableArrayList();
        for(State state : State.allStatesList) {
            if(state.getRegionId() == regionId) {
                regionStates.add(state);
            }
        }
        stateComboBox.setItems(regionStates);

        ObservableList<Salesperson> regionSalespersons = FXCollections.observableArrayList();
        for(Salesperson salesperson : Salesperson.allSalespersonsList) {
            if(salesperson.getRegionId() == regionId) {
                regionSalespersons.add(salesperson);
            }
        }
        salespersonComboBox.setItems(regionSalespersons);
        salespersonComboBox.setValue(null);
        salespersonComboBox.setPromptText("");
    }

    /**
     * This method is an event handler for the Save button that saves the entered Customer info to the database and
     * redirects the application back to the CustomerScreen.
     * The method gathers the data from the form, executes the SQL to update the database and then loads the FXML document
     * for the AllCustomersScreen.
     *
     * @param actionEvent Passed from the On Action event listener on the Save button.
     * @throws IOException  The FXMLLoader.load() call will throw this exception if the FXML document can't be found.
     * @throws SQLException This execution will be thrown if the SQL does not execute properly.
     */
    public void onSaveButtonClick(ActionEvent actionEvent) throws SQLException, IOException {

        if (customerIdTextField.getText().equals("")) {
            String firstNameText = customerFirstNameTextField.getText();
            if (firstNameText.isEmpty()) {
                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.setTitle("Blank Field");
                alert.setContentText("A Customer first name must be entered.");
                alert.show();
                return;
            }
            String lastNameText = customerLastNameTextField.getText();
            if (lastNameText.isEmpty()) {
                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.setTitle("Blank Field");
                alert.setContentText("A Customer last name must be entered.");
                alert.show();
                return;
            }
            String addressText = addressTextField.getText();
            if (addressText.isEmpty()) {
                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.setTitle("Blank Field");
                alert.setContentText("A Customer address must be entered.");
                alert.show();
                return;
            }
            String zipCodeText = zipCodeTextField.getText();
            if (zipCodeText.isEmpty()) {
                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.setTitle("Blank Field");
                alert.setContentText("A Customer zip code must be entered.");
                alert.show();
                return;
            }
            String phoneText = phoneNumberTextField.getText();
            String emailText = emailTextField.getText();
            State state = stateComboBox.getSelectionModel().getSelectedItem();
            if (state == null) {
                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.setTitle("Blank Field");
                alert.setContentText("A Customer state must be chosen.");
                alert.show();
                return;
            }
            Region region = regionComboBox.getSelectionModel().getSelectedItem();
            if (region == null) {
                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.setTitle("Blank Field");
                alert.setContentText("A Region must be chosen.");
                alert.show();
                return;
            }
            Salesperson salesperson = salespersonComboBox.getSelectionModel().getSelectedItem();
            if (salesperson == null) {
                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.setTitle("Blank Field");
                alert.setContentText("An associated salesperson must be chosen.");
                alert.show();
                return;
            }

            DBCustomers.insertCustomer(firstNameText, lastNameText, addressText, zipCodeText, phoneText, emailText,
                    salesperson.getSalespersonId(), state.getStateId(), region.getRegionId());
            DBCustomers.getAllCustomers();
            tempCustomer = null;

            Parent root = FXMLLoader.load(Objects.requireNonNull(getClass().getResource("/view/AllCustomersScreen.fxml")));
            Stage stage = (Stage) ((Node) (actionEvent.getSource())).getScene().getWindow();
            Scene scene = new Scene(root, 1000, 450);
            stage.setScene(scene);
            stage.setTitle("All Customers Screen");
            stage.show();
        } else {
            int customerId = Integer.parseInt(customerIdTextField.getText());
            String firstNameText = customerFirstNameTextField.getText();
            if (firstNameText.isEmpty()) {
                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.setTitle("Blank Field");
                alert.setContentText("A Customer first name must be entered.");
                alert.show();
                return;
            }
            String lastNameText = customerLastNameTextField.getText();
            if (lastNameText.isEmpty()) {
                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.setTitle("Blank Field");
                alert.setContentText("A Customer last name must be entered.");
                alert.show();
                return;
            }
            String addressText = addressTextField.getText();
            if (addressText.isEmpty()) {
                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.setTitle("Blank Field");
                alert.setContentText("A Customer address must be entered.");
                alert.show();
                return;
            }
            String zipCodeText = zipCodeTextField.getText();
            if (zipCodeText.isEmpty()) {
                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.setTitle("Blank Field");
                alert.setContentText("A Customer zip code must be entered.");
                alert.show();
                return;
            }
            String phoneText = phoneNumberTextField.getText();
            String emailText = emailTextField.getText();
            State state = stateComboBox.getSelectionModel().getSelectedItem();
            if (state == null) {
                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.setTitle("Blank Field");
                alert.setContentText("A Customer state must be chosen.");
                alert.show();
                return;
            }
            Region region = regionComboBox.getSelectionModel().getSelectedItem();
            if (region == null) {
                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.setTitle("Blank Field");
                alert.setContentText("A Region must be chosen.");
                alert.show();
                return;
            }
            Salesperson salesperson = salespersonComboBox.getSelectionModel().getSelectedItem();
            if (salesperson == null) {
                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.setTitle("Blank Field");
                alert.setContentText("An associated salesperson must be chosen.");
                alert.show();
                return;
            }

            DBCustomers.updateCustomer(customerId, firstNameText, lastNameText, addressText, zipCodeText, phoneText, emailText,
                    salesperson.getSalespersonId(), state.getStateId(), region.getRegionId());
            DBCustomers.getAllCustomers();
            tempCustomer = null;

            Parent root = FXMLLoader.load(Objects.requireNonNull(getClass().getResource("/view/AllCustomersScreen.fxml")));
            Stage stage = (Stage) ((Node) (actionEvent.getSource())).getScene().getWindow();
            Scene scene = new Scene(root, 1000, 450);
            stage.setScene(scene);
            stage.setTitle("All Customers Screen");
            stage.show();
        }
    }

    /**
     * This method is an event handler on the Back button.
     * When clicked, the button redirects the program to the AllCustomersScreen.
     *
     * @param actionEvent Passed from the On Action event listener on the Back button.
     * @throws IOException Exception gets thrown if load() cannot locate the FXML file
     */
    public void toAllCustomersScreen(ActionEvent actionEvent) throws IOException {
        tempCustomer = null;

        Parent root = FXMLLoader.load(Objects.requireNonNull(getClass().getResource("/view/AllCustomersScreen.fxml")));
        Stage stage = (Stage) ((Node) (actionEvent.getSource())).getScene().getWindow();
        Scene scene = new Scene(root, 1000, 400);
        stage.setScene(scene);
        stage.setTitle("All Customers Screen");
    }
}
