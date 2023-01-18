package controller;

import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import java.io.IOException;

/** This is the controller class for the AdminHomeScreen.fxml document and is not meant to be instantiated.
 * The class will load from the LoginScreen if the user enters login credentials that identify them as an admin user.
 * @author Gregory Farrell
 * @version 1.1
 * */
public class AdminHomeScreenController {
    public void toLogin(ActionEvent actionEvent) throws IOException {
        Parent root = FXMLLoader.load(getClass().getResource("/view/LoginScreen.fxml"));
        Stage stage = (Stage) ((Node) (actionEvent.getSource())).getScene().getWindow();
        Scene scene = new Scene(root, 600, 400);
        stage.setScene(scene);
        stage.setTitle("Login Screen");
    }
}
