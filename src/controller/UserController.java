package controller;

import java.io.IOException;
import java.time.LocalDateTime;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.control.Alert.AlertType;
import javafx.stage.Stage;
import model.CreateAlert;
import model.User;
import model.AppData;

public class UserController {

    @FXML
    private Button btnDelete, btnSave, btnLogOut, btnCreateActivity;

    @FXML
    private Label lblEmail, lblName, lblPassword;

    @FXML private TextField txtNewPassword, txtNewEmail, txtNewName;

    public void init(Stage stage){
        User user = AppData.getUser();
        user.setLastLoginDateTime(LocalDateTime.now());
        
        lblName.setText(user.getName());
        lblEmail.setText(user.getEmail());
        lblPassword.setText(user.getPassword());

        btnSave.setOnAction(e -> changeData(user));
        btnDelete.setOnAction(e -> deleteData(user,stage));
        btnLogOut.setOnAction(e -> loadStartMenu(stage));
        btnCreateActivity.setOnAction(e -> loadCreateActivity(stage));
    }

    private void deleteData(User user, Stage stage){
        Alert alert = new Alert(AlertType.CONFIRMATION, "DELETE account? This change cannot be undone");
        alert.showAndWait();
        if(alert.getResult() == ButtonType.OK){
            AppData.getUsers().remove(user);
            CreateAlert.newAlert(AlertType.INFORMATION, "The user has been removed successfully");
            loadStartMenu(stage);
        }
    }

    private void changeData(User user){
        if (!isDataEmpty()){
            if(txtNewEmail.getText() != "" && !AppData.isTextFieldAnEmail(txtNewEmail)){
                CreateAlert.newAlert(AlertType.ERROR, "Email must be valid");
            } else  {
                int pos;
                pos = AppData.getUserPositionByName(txtNewName.getText());
                if (pos == -1){
                    pos = AppData.getUserPositionByEmail(txtNewEmail.getText());
                    if (pos == -1){
                        
                        if(txtNewName.getText() != "")
                            user.setName(txtNewName.getText());
                        if(txtNewEmail.getText() != "")
                            user.setEmail(txtNewEmail.getText());
                        if(txtNewPassword.getText() != "")
                            user.setPassword(txtNewPassword.getText());
                        AppData.saveDataFile();
                        CreateAlert.newAlert(AlertType.INFORMATION, "Changes saved successfully");

                        lblName.setText(user.getName());
                        lblEmail.setText(user.getEmail());
                        lblPassword.setText(user.getPassword());
                        txtNewName.setText("");
                        txtNewEmail.setText("");
                        txtNewPassword.setText("");
                    } else {
                        CreateAlert.newAlert(AlertType.ERROR, "Email already taken");
                    }
                } else {
                    CreateAlert.newAlert(AlertType.ERROR, "Username already taken");
                }
            }
        }
    }

    private boolean isDataEmpty(){
        return txtNewName.getText() == "" && txtNewEmail.getText() == "" && txtNewPassword.getText() == "";
    }

    private void loadStartMenu(Stage stage){
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("../view/StartMenu.fxml"));
        try {
            Scene scene = new Scene(fxmlLoader.load());{}
            ((StartMenuController)fxmlLoader.getController()).init(stage);
            stage.setScene(scene);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void loadCreateActivity(Stage stage){
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("../view/CreateActivityMenu.fxml"));
        try {
            Scene scene = new Scene(fxmlLoader.load());{}
            ((CreateActivityController)fxmlLoader.getController()).init(stage);
            stage.setScene(scene);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
