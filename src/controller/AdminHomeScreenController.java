package controller;

import database.DBSalespersons;
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
import model.Administrator;
import model.Region;
import model.Salesperson;

import java.io.IOException;
import java.net.URL;
import java.util.Objects;
import java.util.Optional;
import java.util.ResourceBundle;

/** This is the controller class for the AdminHomeScreen.fxml document and is not meant to be instantiated.
 * The class will load from the LoginScreen if the user enters login credentials that identify them as an admin user.
 * @author Gregory Farrell
 * @version 1.1
 * */
public class AdminHomeScreenController implements Initializable {

    /** Static Administrator object corresponding to the User. */
    private static Administrator userAdministrator;

    /** Text field used to search and filter the salespersonTable*/
    public TextField searchTextField;
    /** TableView populated Salesperson data from the database. */
    public TableView<Salesperson> salespersonTable;
    /** TableColumn for SalespersonID. */
    public TableColumn<Salesperson, Integer> salespersonIdCol;
    /** TableColumn for Salesperson's name. */
    public TableColumn<Salesperson, String> salespersonNameCol;
    /** TableColumn for upcoming appointments. */
    public TableColumn<Salesperson, Integer> scheduledAppointmentsCol;
    /** TableColumn for completed appointments. */
    public TableColumn<Salesperson, Integer> completedAppointmentsCol;
    /** TableColumn for total appointments. */
    public TableColumn<Salesperson, Integer> totalAppointmentsCol;
    /** TableColumn for total customers. */
    public TableColumn<Salesperson, Integer> totalClientsCol;
    /** TableColumn for salesperson's region */
    public TableColumn<Salesperson, String> regionCol;
    /** Combo box used to sort customers by region. */
    public ComboBox<Region> regionComboBox;
    /** Radio button used to load all salespersons into the table. */
    public RadioButton allRegionsRadioButton;
    /** Text field used to display the administrator user's name. */
    public Label adminNameTextField;


    /** This method is called by the FXMLLoader.load() call contained in the loginValidation() method of the LoginScreenController class.
     * The method populates the table with all the Salesperson data that is in the database.
     * @param resourceBundle An unreferenced ResourceBundle object passed automatically
     * @param url An unreferenced URL object passed automatically
     * */
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        adminNameTextField.setText(getUserAdministrator().getAdministratorFirstName() + " " + getUserAdministrator().getAdministratorLastName());
        DBSalespersons.getAllSalespersons();
        regionComboBox.setItems(Region.allRegionsList);

        salespersonIdCol.setCellValueFactory(new PropertyValueFactory<>("salespersonId"));
        salespersonIdCol.setStyle("-fx-alignment: CENTER;");
        salespersonNameCol.setCellValueFactory(new PropertyValueFactory<>("fullName"));
        salespersonNameCol.setStyle("-fx-alignment: CENTER;");
        scheduledAppointmentsCol.setCellValueFactory(new PropertyValueFactory<>("totalScheduledAppointments"));
        scheduledAppointmentsCol.setStyle("-fx-alignment: CENTER;");
        completedAppointmentsCol.setCellValueFactory(new PropertyValueFactory<>("totalCompletedAppointments"));
        completedAppointmentsCol.setStyle("-fx-alignment: CENTER;");
        totalAppointmentsCol.setCellValueFactory(new PropertyValueFactory<>("totalAllAppointments"));
        totalAppointmentsCol.setStyle("-fx-alignment: CENTER;");
        totalClientsCol.setCellValueFactory(new PropertyValueFactory<>("totalClients"));
        totalClientsCol.setStyle("-fx-alignment: CENTER;");
        regionCol.setCellValueFactory(new PropertyValueFactory<>("regionName"));
        regionCol.setStyle("-fx-alignment: CENTER;");

        salespersonTable.setItems(Salesperson.allSalespersonsList);

    }

    /** This method is an event handler on the searchTextField.
     * Search function searches the allSalespersonsList for matches containing either the string or ID entered.
     * @param actionEvent Passed from the On Action event listener on the text field.
     */
    public void searchSalespersons(ActionEvent actionEvent) {
        try {
            String searchString = searchTextField.getText();
            ObservableList<Salesperson> salespersonFoundList = Salesperson.searchSalespersons(searchString);

            if (salespersonFoundList.size() == 0) {
                int searchInt = Integer.parseInt(searchTextField.getText());
                salespersonFoundList = Salesperson.searchSalespersons(searchInt);
            }
            if (salespersonFoundList.isEmpty()) {
                searchTextField.setText("");
                salespersonTable.setItems(Salesperson.allSalespersonsList);
                Alert alert1 = new Alert(Alert.AlertType.INFORMATION);
                alert1.setTitle("No Results Found");
                alert1.setContentText("There are no Salespersons matching that search criteria.");
                alert1.show();
                return;
            }
            salespersonTable.setItems(salespersonFoundList);
            searchTextField.setText("");
            allRegionsRadioButton.setSelected(false);
        } catch (NumberFormatException exception) {
            searchTextField.setText("");
            salespersonTable.setItems(Salesperson.allSalespersonsList);
            Alert alert1 = new Alert(Alert.AlertType.INFORMATION);
            alert1.setTitle("No Results Found");
            alert1.setContentText("There are no Salespersons matching that search criteria.");
            alert1.show();
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

    /** This method is an event handler on the Salesperson Detail button.
     * When clicked, the button redirects the program to the SalespersonDetailScreen for the selected salesperson.
     * @param actionEvent Passed from the On Action event listener on the Salesperson Detail button.
     * @throws IOException Exception gets thrown if load() cannot locate the FXML file
     */
    public void toSalespersonDetailScreen(ActionEvent actionEvent) throws IOException {
        if(salespersonTable.getSelectionModel().getSelectedItem() != null) {

            SalespersonDetailScreenController.setSelectedSalesperson(salespersonTable.getSelectionModel().getSelectedItem());

            Parent root = FXMLLoader.load(Objects.requireNonNull(getClass().getResource("/view/SalespersonDetailScreen.fxml")));
            Stage stage = (Stage) ((Node) (actionEvent.getSource())).getScene().getWindow();
            Scene scene = new Scene(root, 600, 500);
            stage.setScene(scene);
            stage.setTitle("Salesperson Detail");
            stage.show();
        }

        else {
            Alert alert1 = new Alert(Alert.AlertType.INFORMATION);
            alert1.setTitle("No Selection Made");
            alert1.setContentText("Please select an Salesperson to view. ");
            alert1.show();
        }
    }

    /** This method is an event handler on the Create New User button.
     * When clicked, the button redirects the program to the CreateNewSalespersonScreen
     * @param actionEvent Passed from the On Action event listener on the Create New Salesperson button.
     * @throws IOException Exception gets thrown if load() cannot locate the FXML file
     */
    public void toCreateNewUserScreen(ActionEvent actionEvent) throws IOException {
        Parent root = FXMLLoader.load(Objects.requireNonNull(getClass().getResource("/view/CreateNewUserScreen.fxml")));
        Stage stage = (Stage) ((Node) (actionEvent.getSource())).getScene().getWindow();
        Scene scene = new Scene(root, 600, 500);
        stage.setScene(scene);
        stage.setTitle("Create New User Screen");
        stage.show();
    }

    /** This method is an event handler on the "All Clients" button.
     * When clicked, the button redirects the program to the AllCustomersScreen.
     * @param actionEvent Passed from the On Action event listener on the "All Clients" button.
     * @throws IOException Exception gets thrown if load() cannot locate the FXML file
     */
    public void toAllClientsScreen(ActionEvent actionEvent) throws IOException {
        Parent root = FXMLLoader.load(Objects.requireNonNull(getClass().getResource("/view/AllClientsScreen.fxml")));
        Stage stage = (Stage) ((Node) (actionEvent.getSource())).getScene().getWindow();
        Scene scene = new Scene(root, 1000, 500);
        stage.setScene(scene);
        stage.setTitle("All Clients Screen");
    }

    /** This method is an event handler on the "All Appointments" button.
     * When clicked, the button redirects the program to the AllAppointmentsScreen.
     * @param actionEvent Passed from the On Action event listener on the "All Appointments" button.
     * @throws IOException Exception gets thrown if load() cannot locate the FXML file
     */
    public void toAllAppointmentsScreen(ActionEvent actionEvent) throws IOException {
        Parent root = FXMLLoader.load(Objects.requireNonNull(getClass().getResource("/view/AllAppointmentsScreen.fxml")));
        Stage stage = (Stage) ((Node) (actionEvent.getSource())).getScene().getWindow();
        Scene scene = new Scene(root, 1000, 500);
        stage.setScene(scene);
        stage.setTitle("All Appointments Screen");
    }

    /** This method sets the static Administrator object corresponding with the User.  */
    public static void setUserAdministrator(Administrator paramAdministrator) { userAdministrator = paramAdministrator; }

    /** This method returns the static Administrator object corresponding with the User.  */
    public static Administrator getUserAdministrator() { return userAdministrator; }


    /** This method sorts the allSalespersonsList by region and displays the results in the table.
     * @param actionEvent Passed from the On Action event listener on the regionComboBox. */
    public void sortByRegion(ActionEvent actionEvent) {
        if (regionComboBox.getSelectionModel().getSelectedItem() != null) {
            ObservableList<Salesperson> regionSalespersons = FXCollections.observableArrayList();
            int selectedRegionId = regionComboBox.getSelectionModel().getSelectedItem().getRegionId();

            for (Salesperson salesperson : Salesperson.allSalespersonsList) {
                if (salesperson.getRegionId() == selectedRegionId) {
                    regionSalespersons.add(salesperson);
                }
            }
            salespersonTable.setItems(regionSalespersons);
            allRegionsRadioButton.setSelected(false);
        }
    }

    /** This method restores the salesperson table to display salespersons from all regions/
     * @param actionEvent Passed from the On Action event listener on allRegionsRadioButton. */
    public void displayAllRegions(ActionEvent actionEvent) {
        salespersonTable.setItems(Salesperson.allSalespersonsList);
        regionComboBox.setValue(null);
    }
}
