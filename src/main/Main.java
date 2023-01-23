package main;

import controller.LoginScreenController;
import database.*;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Objects;

/**
 * This class creates an appointment management application that interfaces with a MySQL database.
 * The class contains the program's main() method and is not intended to be instantiated.
 * @author Greg Farrell
 * @version 1.1
 */
public class Main extends Application {

    /** This method starts the appointment management application and loads the LoginScreen FXML screen.
     * @throws Exception FXMLLoader.load() will throw and exception if it cannot locate the FXML document.
     * @param stage The initial stage for loading the first scene.
     * */
    @Override
    public void start(Stage stage) throws Exception {
        Parent root = FXMLLoader.load(getClass().getResource("/view/LoginScreen.fxml"));
        stage.setTitle("Login Screen");
        stage.setScene(new Scene(root, 600, 400));
        stage.show();
    }

    /** The Javadoc folder is located in the main directory, "/Assessment_Project/Javadoc".
     *  This is the main method that begins the program.
     * @param args command-line arguments.
     * */
    public static void main(String[] args) {
        JDBC.openConnection();
        ZonedDateTime localTime0 = ZonedDateTime.now();
        DateTimeFormatter dtf = DateTimeFormatter.ofPattern("H:mm a");
        String b = dtf.format(localTime0);
        System.out.println(b);
        System.out.println(localTime0);
        System.out.println(localTime0.toInstant());

        DBUsers.getAllUsers();
        DBRegions.getAllRegions();
        DBStates.getAllStates();
        DBStates.getNortheastStates();
        DBStates.getSoutheastStates();
        DBStates.getMidwestStates();
        DBStates.getTexasPlusStates();
        DBStates.getCaliforniaPlusStates();
        DBStates.getNorthwestStates();
        DBSalespersons.getAllSalespersons();
        DBCustomers.getAllCustomers();
        DBAppointments.getEveryAppointment();
        DBAppointments.getUpcomingAppointments();
        DBAppointments.getCompletedAppointments();
        DBSalespersons.getAllSalespersons();


        launch(args);
        JDBC.closeConnection();
    }
}
