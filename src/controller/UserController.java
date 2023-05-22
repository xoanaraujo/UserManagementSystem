package controller;

import java.io.IOException;

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
import model.UserData;

public class UserController {

    @FXML
    private Button btnDelete, btnSave;

    @FXML
    private Label lblEmail, lblName, lblPassword;

    @FXML private TextField txtNewPassword, txtNewEmail, txtNewName;

    public void init(Stage stage){
        User user = UserData.getUser();
        lblName.setText(user.getName());
        lblEmail.setText(user.getEmail());
        lblPassword.setText(user.getPassword());

        btnSave.setOnAction(e -> changeData(user));
        btnDelete.setOnAction(e -> deleteData(user,stage));
    }

    private void deleteData(User user, Stage stage){
        Alert alert = new Alert(AlertType.CONFIRMATION, "DELETE account? This change cannot be undone");
        alert.showAndWait();
        if(alert.getResult() == ButtonType.OK){
            UserData.getUsers().remove(user);
            CreateAlert.newAlert(AlertType.INFORMATION, "The user has been removed successfully");
            loadStartMenu(stage);
        }
    }

    private void changeData(User user){
        if (!isDataEmpty()){
            int pos;
            pos = UserData.getUserPositionByName(txtNewName.getText());
            if (pos == -1){
                pos = UserData.getUserPositionByEmail(txtNewEmail.getText());
                if (pos == -1){
                    if(txtNewName.getText() != "")
                        user.setName(txtNewName.getText());
                    if(txtNewEmail.getText() != "")
                        if(UserData.isTextFieldAnEmail(txtNewEmail))
                            user.setEmail(txtNewEmail.getText());
                    if(txtNewPassword.getText() != "")
                        user.setPassword(txtNewPassword.getText());
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
}
