package controller;

import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

public class UserController {

    @FXML
    private Button btnDelete, btnSave;

    @FXML
    private Label lblEmail, lblName, lblPassword;

    @FXML private TextField ttxtNewPassword, txtNewEmail, txtNewName;

    public void init(Stage stage){

    }
}
