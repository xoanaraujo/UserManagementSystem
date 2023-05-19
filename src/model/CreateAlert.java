package model;

import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;

public class CreateAlert{
    public static void newAlert(AlertType alertType, String content){
        Alert alert = new Alert(alertType, content);
        alert.showAndWait();
    }
}
