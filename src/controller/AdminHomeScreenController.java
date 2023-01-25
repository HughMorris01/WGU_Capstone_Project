package controller;

import database.DBSalespersons;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;
import model.Administrator;
import model.Customer;
import model.Salesperson;

import java.io.IOException;
import java.net.URL;
import java.util.Objects;
import java.util.ResourceBundle;

/** This is the controller class for the AdminHomeScreen.fxml document and is not meant to be instantiated.
 * The class will load from the LoginScreen if the user enters login credentials that identify them as an admin user.
 * @author Gregory Farrell
 * @version 1.1
 * */
public class AdminHomeScreenController implements Initializable {

    /** Text field used to search and filter the salespersonTable*/
    public TextField searchTextField;
    /** TableView populated Salesperson data from the database. */
    public TableView<Salesperson> salespersonTable;
    /** TableColumn for SalespersonID. */
    public TableColumn<Salesperson, Integer> salespersonId;
    /** TableColumn for Salesperson's name. */
    public TableColumn<Salesperson, String> salespersonNameCol;
    /** TableColumn for upcoming appointments. */
    public TableColumn<Salesperson, Integer> scheduledApptsCol;
    /** TableColumn for completed appointments. */
    public TableColumn<Salesperson, Integer> completedApptsCol;
    /** TableColumn for total appointments. */
    public TableColumn<Salesperson, Integer> totalApptsCol;
    /** TableColumn for total customers. */
    public TableColumn<Salesperson, Integer> totalCustomersCol;
    /** TableColumn for salesperson's region */
    public TableColumn<Salesperson, String> regionCol;
    /** Static Administrator object corresponding to the User. */
    private static Administrator userAdministrator;


    /** This method is called by the FXMLLoader.load() call contained in the loginValidation() method of the LoginScreenController class.
     * The method populates the table with all the Salesperson data that is in the database.
     * @param resourceBundle An unreferenced ResourceBundle object passed automatically
     * @param url An unreferenced URL object passed automatically
     * */
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        DBSalespersons.getAllSalespersons();

        salespersonId.setCellValueFactory(new PropertyValueFactory<>("salespersonId"));
        salespersonNameCol.setCellValueFactory(new PropertyValueFactory<>("fullName"));
        scheduledApptsCol.setCellValueFactory(new PropertyValueFactory<>("scheduledAppointments"));
        completedApptsCol.setCellValueFactory(new PropertyValueFactory<>("completedAppointments"));
        totalApptsCol.setCellValueFactory(new PropertyValueFactory<>("totalAppointments"));
        totalCustomersCol.setCellValueFactory(new PropertyValueFactory<>("totalCustomers"));
        regionCol.setCellValueFactory(new PropertyValueFactory<>("regionName"));
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
        } catch (NumberFormatException exception) {
            searchTextField.setText("");
            salespersonTable.setItems(Salesperson.allSalespersonsList);
            Alert alert1 = new Alert(Alert.AlertType.INFORMATION);
            alert1.setTitle("No Results Found");
            alert1.setContentText("There are no Salespersons matching that search criteria.");
            alert1.show();
        }
    }

    /** This method is an event handler on the Logout button.
     * When clicked, the button redirects the program to the original Login Screen
     * @param actionEvent Passed from the On Action event listener on the Logout button.
     * @throws IOException Exception gets thrown if load() cannot locate the FXML file
     */
    public void toLogin(ActionEvent actionEvent) throws IOException {
        Parent root = FXMLLoader.load(getClass().getResource("/view/LoginScreen.fxml"));
        Stage stage = (Stage) ((Node) (actionEvent.getSource())).getScene().getWindow();
        Scene scene = new Scene(root, 600, 400);
        stage.setScene(scene);
        stage.setTitle("Login Screen");
    }

    /** This method is an event handler on the Salesperson Detail button.
     * When clicked, the button redirects the program to the SalespersonDetailScreen
     * @param actionEvent Passed from the On Action event listener on the Salesperson Detail button.
     * @throws IOException Exception gets thrown if load() cannot locate the FXML file
     */
    public void toSalespersonDetailScreen(ActionEvent actionEvent) throws IOException {
    }

    /** This method is an event handler on the Create New Salesperson button.
     * When clicked, the button redirects the program to the CreateNewSalespersonScreen
     * @param actionEvent Passed from the On Action event listener on the Create New Salesperson button.
     * @throws IOException Exception gets thrown if load() cannot locate the FXML file
     */
    public void toCreateNewSalespersonScreen(ActionEvent actionEvent) throws IOException {
    }

    /** This method is an event handler on the All Customers button.
     * When clicked, the button redirects the program to the AllCustomersScreen.
     * @param actionEvent Passed from the On Action event listener on the All Customers button.
     * @throws IOException Exception gets thrown if load() cannot locate the FXML file
     */
    public void toAllCustomersScreen(ActionEvent actionEvent) throws IOException {
        Parent root = FXMLLoader.load(Objects.requireNonNull(getClass().getResource("/view/AllCustomersScreen.fxml")));
        Stage stage = (Stage) ((Node) (actionEvent.getSource())).getScene().getWindow();
        Scene scene = new Scene(root, 1000, 500);
        stage.setScene(scene);
        stage.setTitle("All Customers Screen");
    }

    /** This method is an event handler on the All Appointments button.
     * When clicked, the button redirects the program to the AllAppointmentsScreen.
     * @param actionEvent Passed from the On Action event listener on the All Appointments button.
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



}
