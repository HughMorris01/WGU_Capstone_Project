package controller;

import javafx.fxml.Initializable;
import model.Salesperson;

import java.net.URL;
import java.util.ResourceBundle;

public class SalespersonHomeScreenController implements Initializable {
    private static Salesperson userSalesperson;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

    }

    public static void setUserSalesperson(Salesperson paramSalesperson) {
        userSalesperson = paramSalesperson;
    }

    public static Salesperson getUserSalesperson() {
        return userSalesperson;
    }

}
