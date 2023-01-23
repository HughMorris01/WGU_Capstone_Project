package controller;

import database.DBContacts;
import database.DBSalespersons;
import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;
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
    /** TableView populated Salesperson data from the database. */
    public TableView<Salesperson> salespersonTable;
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


    /** This method is called by the FXMLLoader.load() call contained in the loginValidation() method of the LoginScreenController class.
     * The method populates the table with all the Salesperson data that is in the database.
     * @param resourceBundle An unreferenced ResourceBundle object passed automatically
     * @param url An unreferenced URL object passed automatically
     * */
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        salespersonNameCol.setCellValueFactory(new PropertyValueFactory<>("fullName"));
        scheduledApptsCol.setCellValueFactory(new PropertyValueFactory<>("scheduledAppointments"));
        completedApptsCol.setCellValueFactory(new PropertyValueFactory<>("completedAppointments"));
        totalApptsCol.setCellValueFactory(new PropertyValueFactory<>("totalAppointments"));
        totalCustomersCol.setCellValueFactory(new PropertyValueFactory<>("totalCustomers"));
        regionCol.setCellValueFactory(new PropertyValueFactory<>("regionName"));
        salespersonTable.setItems(Salesperson.allSalespersonsList);
    }

    public void toLogin(ActionEvent actionEvent) throws IOException {
        Parent root = FXMLLoader.load(getClass().getResource("/view/LoginScreen.fxml"));
        Stage stage = (Stage) ((Node) (actionEvent.getSource())).getScene().getWindow();
        Scene scene = new Scene(root, 600, 400);
        stage.setScene(scene);
        stage.setTitle("Login Screen");
    }

    public void toSalespersonDetailScreen(ActionEvent actionEvent) {
    }

    public void toCreateNewSalespersonScreen(ActionEvent actionEvent) {
    }

    public void toAllCustomersScreen(ActionEvent actionEvent) throws IOException {
        Parent root = FXMLLoader.load(Objects.requireNonNull(getClass().getResource("/view/AllCustomersScreen.fxml")));
        Stage stage = (Stage) ((Node) (actionEvent.getSource())).getScene().getWindow();
        Scene scene = new Scene(root, 1000, 400);
        stage.setScene(scene);
        stage.setTitle("Login Screen");
    }

    public void toAllAppointmentsScreen(ActionEvent actionEvent) {
    }
}
