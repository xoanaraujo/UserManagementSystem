package controller;

import java.io.IOException;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.control.Alert.AlertType;
import javafx.stage.Stage;
import model.CreateAlert;
import model.User;
import model.UserData;

public class RegisterMenuController {
    
    @FXML private Button btnBack, btnRegister;

    @FXML private TextField txtEmail ,txtPassword, txtUser;

    public void init(Stage stage){
        btnBack.setOnAction(e -> loadStartMenu(stage));
        btnRegister.setOnAction(e -> register(stage));
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

    private void register(Stage stage){
        if(!isDataEmpty()){
            if(UserData.isTextFieldAnEmail(txtEmail)){
                if(UserData.getUserPositionByName(txtUser.getText()) == -1){
                    if (UserData.getUserPositionByEmail(txtEmail.getText()) == -1){
                        UserData.getUsers().add(new User(txtUser.getText(), txtEmail.getText(), txtPassword.getText()));
                        UserData.saveDataFile();
                        CreateAlert.newAlert(AlertType.INFORMATION, "User successfully registered");
                        loadStartMenu(stage);
                    } else {
                        CreateAlert.newAlert(AlertType.ERROR, "Email already taken");
                    }
                } else {
                    CreateAlert.newAlert(AlertType.ERROR, "Username already taken");
                }
            } else {
                CreateAlert.newAlert(AlertType.ERROR, "Email must be valid");
            }
        } else {
            CreateAlert.newAlert(AlertType.ERROR, "Incomplete Fields");
        }
    }

    private boolean isDataEmpty(){
        return txtUser.getText().equals("") || txtEmail.getText().equals("") || txtPassword.getText().equals("");
    }
}
