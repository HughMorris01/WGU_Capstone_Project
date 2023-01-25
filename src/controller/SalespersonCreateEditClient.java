package controller;

import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import model.Client;

import java.io.IOException;
import java.net.URL;
import java.util.Objects;
import java.util.ResourceBundle;

public class SalespersonCreateEditClient implements Initializable {
    private Client tempClient = null;
    private boolean labelBoolean = true;
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

    }

    /** This method is an event handler on the Back button.
     * When clicked, the button redirects the program to the SalespersonHomeScreen.
     * @param actionEvent Passed from the On Action event listener on the Back button.
     * @throws IOException Exception gets thrown if load() cannot locate the FXML file
     */
    public void toSalespersonHomeScreen(ActionEvent actionEvent) throws IOException {
        tempClient = null;
        labelBoolean = false;

        Parent root = FXMLLoader.load(Objects.requireNonNull(getClass().getResource("/view/SalespersonHomeScreen.fxml")));
        Stage stage = (Stage) ((Node) (actionEvent.getSource())).getScene().getWindow();
        Scene scene = new Scene(root, 1000, 500);
        stage.setScene(scene);
        stage.setTitle("Salesperson Home Screen");

    }


}
