package database;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import model.State;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

/** This abstract class is for accessing the States table data in the database and not meant to be instantiated.
 * The class contains 7 methods for returning various filtered ObservableLists.
 * @author Gregory Farrell
 * @version 1.1
 */
public abstract class DBStates {

    /** This method return all the records in the States table of the database.
     * @return An ObservableList of State objects.
     * */
    public static ObservableList<State> getAllStates() {
        ObservableList<State> tempAllStatesList = FXCollections.observableArrayList();

        try {
            String sqlCommand = "SELECT * FROM states";
            PreparedStatement ps = JDBC.getConnection().prepareStatement(sqlCommand);
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                State tempState;
                int stateId = rs.getInt("State_ID");
                String stateName = rs.getString("State_Name");
                String stateAbbreviation = rs.getString("State_Abbreviation");
                int regionId = rs.getInt("Region_ID");
                tempState = new State(stateId, stateName, stateAbbreviation, regionId);
                tempAllStatesList.add(tempState);
                State.allStatesList.add(tempState);
            }
        }
        catch (SQLException e) {
            System.out.println(e.getMessage());
            e.printStackTrace();
        }
        return tempAllStatesList;
    }
    /** This method return all the Northeast Region states in the states table of the database.
     * @return An ObservableList of State objects.
     * */
    public static ObservableList<State> getNortheastStates() {
        State.northeastRegionStatesList.clear();
        ObservableList<State> tempNortheastRegionStateList = FXCollections.observableArrayList();

        try {
            String sqlCommand = "SELECT * FROM states WHERE Region_ID = 1001 ";
            PreparedStatement ps = JDBC.getConnection().prepareStatement(sqlCommand);
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                State tempState;
                int stateId = rs.getInt("State_ID");
                String stateName = rs.getString("State_Name");
                String stateAbbreviation = rs.getString("State_Abbreviation");
                int regionId = rs.getInt("Region_ID");
                tempState = new State(stateId, stateName, stateAbbreviation, regionId);
                tempNortheastRegionStateList.add(tempState);
                State.northeastRegionStatesList.add(tempState);
            }
        }
        catch (SQLException e) {
            System.out.println(e.getMessage());
            e.printStackTrace();
        }
        return tempNortheastRegionStateList;
    }
    /** This method return all the Southeast Region states in the states table of the database.
     * @return An ObservableList of State objects.
     * */
    public static ObservableList<State> getSoutheastStates() {
        State.southeastRegionStatesList.clear();
        ObservableList<State> tempSoutheastRegionStateList = FXCollections.observableArrayList();

        try {
            String sqlCommand = "SELECT * FROM states WHERE Region_ID = 1002 ";
            PreparedStatement ps = JDBC.getConnection().prepareStatement(sqlCommand);
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                State tempState;
                int stateId = rs.getInt("State_ID");
                String stateName = rs.getString("State_Name");
                String stateAbbreviation = rs.getString("State_Abbreviation");
                int regionId = rs.getInt("Region_ID");
                tempState = new State(stateId, stateName, stateAbbreviation, regionId);
                tempSoutheastRegionStateList.add(tempState);
                State.southeastRegionStatesList.add(tempState);
            }
        }
        catch (SQLException e) {
            System.out.println(e.getMessage());
            e.printStackTrace();
        }
        return tempSoutheastRegionStateList;
    }
    /** This method return all the Mid-West Region states in the states table of the database.
     * @return An ObservableList of State objects.
     * */
    public static ObservableList<State> getMidwestStates() {
        State.midwestRegionStatesList.clear();
        ObservableList<State> tempMidwestRegionStateList = FXCollections.observableArrayList();

        try {
            String sqlCommand = "SELECT * FROM states WHERE Region_ID = 1003 ";
            PreparedStatement ps = JDBC.getConnection().prepareStatement(sqlCommand);
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                State tempState;
                int stateId = rs.getInt("State_ID");
                String stateName = rs.getString("State_Name");
                String stateAbbreviation = rs.getString("State_Abbreviation");
                int regionId = rs.getInt("Region_ID");
                tempState = new State(stateId, stateName, stateAbbreviation, regionId);
                tempMidwestRegionStateList.add(tempState);
                State.midwestRegionStatesList.add(tempState);
            }
        }
        catch (SQLException e) {
            System.out.println(e.getMessage());
            e.printStackTrace();
        }
        return tempMidwestRegionStateList;
    }
    /** This method return all the Texas+ Region states in the states table of the database.
     * @return An ObservableList of State objects.
     * */
    public static ObservableList<State> getTexasPlusStates() {
        State.texasPlusRegionStatesList.clear();
        ObservableList<State> tempTexasPlusRegionStateList = FXCollections.observableArrayList();

        try {
            String sqlCommand = "SELECT * FROM states WHERE Region_ID = 1004 ";
            PreparedStatement ps = JDBC.getConnection().prepareStatement(sqlCommand);
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                State tempState;
                int stateId = rs.getInt("State_ID");
                String stateName = rs.getString("State_Name");
                String stateAbbreviation = rs.getString("State_Abbreviation");
                int regionId = rs.getInt("Region_ID");
                tempState = new State(stateId, stateName, stateAbbreviation, regionId);
                tempTexasPlusRegionStateList.add(tempState);
                State.texasPlusRegionStatesList.add(tempState);
            }
        }
        catch (SQLException e) {
            System.out.println(e.getMessage());
            e.printStackTrace();
        }
        return tempTexasPlusRegionStateList;
    }
    /** This method return all the California+ Region states in the states table of the database.
     * @return An ObservableList of State objects.
     * */
    public static ObservableList<State> getCaliforniaPlusStates() {
        State.californiaPlusRegionStatesList.clear();
        ObservableList<State> tempCaliforniaPlusRegionStateList = FXCollections.observableArrayList();

        try {
            String sqlCommand = "SELECT * FROM states WHERE Region_ID = 1005 ";
            PreparedStatement ps = JDBC.getConnection().prepareStatement(sqlCommand);
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                State tempState;
                int stateId = rs.getInt("State_ID");
                String stateName = rs.getString("State_Name");
                String stateAbbreviation = rs.getString("State_Abbreviation");
                int regionId = rs.getInt("Region_ID");
                tempState = new State(stateId, stateName, stateAbbreviation, regionId);
                tempCaliforniaPlusRegionStateList.add(tempState);
                State.californiaPlusRegionStatesList.add(tempState);
            }
        }
        catch (SQLException e) {
            System.out.println(e.getMessage());
            e.printStackTrace();
        }
        return tempCaliforniaPlusRegionStateList;
    }
    /** This method return all the Northwest Region states in the states table of the database.
     * @return An ObservableList of State objects.
     * */
    public static ObservableList<State> getNorthwestStates() {
        State.northwestRegionStatesList.clear();
        ObservableList<State> tempNorthwestRegionStateList = FXCollections.observableArrayList();

        try {
            String sqlCommand = "SELECT * FROM states WHERE Region_ID = 1006 ";
            PreparedStatement ps = JDBC.getConnection().prepareStatement(sqlCommand);
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                State tempState;
                int stateId = rs.getInt("State_ID");
                String stateName = rs.getString("State_Name");
                String stateAbbreviation = rs.getString("State_Abbreviation");
                int regionId = rs.getInt("Region_ID");
                tempState = new State(stateId, stateName, stateAbbreviation, regionId);
                tempNorthwestRegionStateList.add(tempState);
                State.northeastRegionStatesList.add(tempState);
            }
        }
        catch (SQLException e) {
            System.out.println(e.getMessage());
            e.printStackTrace();
        }
        return tempNorthwestRegionStateList;
    }
}
