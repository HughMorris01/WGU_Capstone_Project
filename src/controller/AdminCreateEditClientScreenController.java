package controller;

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

/** This is the controller class for the AdminCreateEditClientScreenController.fxml document and is not meant to be instantiated.
 * The class will either load the form with Client data passed from the previous screen or will begin as blank
 * text fields and combo boxes for the Admin user to enter the Client data into.
 * @author Gregory Farrell
 * @version 1.1
 * */
public class AdminCreateEditClientScreenController implements Initializable {
    /**
     * Static Client member used to receive a Client object passed from the allCustomersScreen
     */
    public static Client tempClient = null;
    /**
     * Static boolean used to toggle the heading label on the screen
     */
    public static boolean labelBoolean = false;
    /**
     * Heading Label that adjusts depending on if it is a new or existing customer being entered
     */
    public Label screenLabel;
    /**
     * Disabled TextField used to display the Client ID, if available.
     */
    public TextField clientIdTextField;
    /** TextField used to enter and display the Client's first name. */
    public TextField clientFirstNameTextField;
    /** TextField used to enter and display the Client's last name. */
    public TextField clientLastNameTextField;
    /** TextField used to enter and display the Client's address. */
    public TextField addressTextField;
    /** TextField used to enter and display the Client's zip code. */
    public TextField zipCodeTextField;
    /** ComboBox used to select the Client's state. */
    public ComboBox<State> stateComboBox;
    /** TextField used to enter and display the Client phone number. */
    public TextField phoneNumberTextField;
    /** TextField used to enter and display the Client email. */
    public TextField emailTextField;
    /** ComboBox used to select the Client's Region. */
    public ComboBox<Region> regionComboBox;
    /** ComboBox used to select the Client's associated salesperson. */
    public ComboBox<Salesperson> salespersonComboBox;
    /** Button that saves the Client info to the database. */
    public Button submitButton;
    /** Button for directing the program back to the previous screen. */
    public Button backButton;

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
        regionComboBox.setItems(Region.allRegionsList);

        if (tempClient != null) {
            clientIdTextField.setText(String.valueOf(tempClient.getClientId()));
            clientFirstNameTextField.setText(tempClient.getClientFirstName());
            clientLastNameTextField.setText(tempClient.getClientLastName());
            addressTextField.setText(tempClient.getAddress());
            zipCodeTextField.setText(tempClient.getZipCode());
            stateComboBox.setValue(tempClient.getClientState());
            phoneNumberTextField.setText(tempClient.getPhone());
            emailTextField.setText(tempClient.getEmail());
            regionComboBox.setValue(tempClient.getClientRegion());
            salespersonComboBox.setValue(tempClient.getClientSalesperson());

            ObservableList<State> regionStates = FXCollections.observableArrayList();
            int regionId = tempClient.getRegionId();
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

    /** This method is an event handler on the StateComboBox.
     * When a state is initially chosen, it sets the corresponding Region and fills the SalespersonComboBox and with the
     * salespersons for that region. It also filters out the remaining states so that only states from that region are
     * available. To get the full list of states back, the region needs to be changed.
     * @param actionEvent Passed from the On Action event listener on the StateComboBox. */
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
        if (regionComboBox.getSelectionModel().getSelectedItem() != null) {
            int regionId = regionComboBox.getSelectionModel().getSelectedItem().getRegionId();

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
            salespersonComboBox.setValue(null);
            salespersonComboBox.setPromptText("");
        }
    }

    /** Method resets the region based combo boxes back to the original state of the screen.
     * @param actionEvent Passed from the On Action event listener on the Reset button.*/
    public void resetRegionBasedInfo(ActionEvent actionEvent) {
        stateComboBox.setItems(State.allStatesList);
        stateComboBox.setValue(null);
        stateComboBox.setPromptText("Select State");
        regionComboBox.setDisable(true);
        regionComboBox.setValue(null);
        salespersonComboBox.setValue(null);
        salespersonComboBox.setDisable(true);
    }

    /** This method is an event handler for the "Save" button that saves the entered Client info to the database and
     * redirects the application back to the AllClientsScreen.
     * @param actionEvent Passed from the On Action event listener on the Save button.
     * @throws IOException  The FXMLLoader.load() call will throw this exception if the FXML document can't be found.
     * @throws SQLException This execution will be thrown if the SQL does not execute properly.
     */
    public void onSaveButtonClick(ActionEvent actionEvent) throws SQLException, IOException {

        if (clientIdTextField.getText().equals("")) {
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
            if (state == null) {
                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.setTitle("Blank Field");
                alert.setContentText("A Client state must be chosen.");
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

            DBClients.insertClient(firstNameText, lastNameText, addressText, zipCodeText, phoneText, emailText,
                    salesperson.getSalespersonId(), state.getStateId(), region.getRegionId());
            DBClients.getAllClients();
            tempClient = null;

            Parent root = FXMLLoader.load(Objects.requireNonNull(getClass().getResource("/view/AllClientsScreen.fxml")));
            Stage stage = (Stage) ((Node) (actionEvent.getSource())).getScene().getWindow();
            Scene scene = new Scene(root, 1000, 500);
            stage.setScene(scene);
            stage.setTitle("All Clients Screen");
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
            if (state == null) {
                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.setTitle("Blank Field");
                alert.setContentText("A Client state must be chosen.");
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

            DBClients.updateClient(clientId, firstNameText, lastNameText, addressText, zipCodeText, phoneText, emailText,
                    salesperson.getSalespersonId(), state.getStateId(), region.getRegionId());
            DBClients.getAllClients();
            tempClient = null;

            Parent root = FXMLLoader.load(Objects.requireNonNull(getClass().getResource("/view/AllClientsScreen.fxml")));
            Stage stage = (Stage) ((Node) (actionEvent.getSource())).getScene().getWindow();
            Scene scene = new Scene(root, 1000, 500);
            stage.setScene(scene);
            stage.setTitle("All Customers Screen");
            stage.show();
        }
    }

    /** This method is an event handler on the "Back" button.
     * When clicked, the button redirects the program to the AllClientsScreen.
     * @param actionEvent Passed from the On Action event listener on the "Back" button.
     * @throws IOException Exception gets thrown if load() cannot locate the FXML file
     */
    public void toAllClientsScreen(ActionEvent actionEvent) throws IOException {
        tempClient = null;

        Parent root = FXMLLoader.load(Objects.requireNonNull(getClass().getResource("/view/AllClientsScreen.fxml")));
        Stage stage = (Stage) ((Node) (actionEvent.getSource())).getScene().getWindow();
        Scene scene = new Scene(root, 1000, 500);
        stage.setScene(scene);
        stage.setTitle("All Clients Screen");
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
