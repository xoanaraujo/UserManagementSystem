package controller;

import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.control.Alert.AlertType;
import javafx.stage.Stage;
import model.CreateAlert;
import model.User;
import model.UserData;

public class UserController {

    @FXML
    private Button btnDelete, btnSave;

    @FXML
    private Label lblEmail, lblName, lblPassword;

    @FXML private TextField ttxtNewPassword, txtNewEmail, txtNewName;

    public void init(Stage stage){
        User user = UserData.getUser();
        lblName.setText(user.getName());
        lblEmail.setText(user.getEmail());
        lblPassword.setText(user.getPassword());

        btnSave.setOnAction(e -> changeData(user));
    }

    private void changeData(User user){
        int pos;
        pos = UserData.getUserPositionByName(txtNewName.getText());
        if (pos == -1){
            pos = UserData.getUserPositionByEmail(txtNewEmail.getText());
            if (pos == -1){
                user.setName(txtNewName.getText());
                user.setEmail(txtNewEmail.getText());
                user.setPassword(ttxtNewPassword.getText());
                UserData.saveDataFile();
                CreateAlert.newAlert(AlertType.INFORMATION, "Changes saved successfully");
                lblName.setText(user.getName());
                lblEmail.setText(user.getEmail());
                lblPassword.setText(user.getPassword());
            } else {
                CreateAlert.newAlert(AlertType.ERROR, "Email already taken");
            }
        } else {
            CreateAlert.newAlert(AlertType.ERROR, "Username already taken");
        }
    }
}
