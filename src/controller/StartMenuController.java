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
import model.UserData;

public class StartMenuController{

    @FXML Button btnRegister, btnLogin;
    @FXML TextField txtUser, txtPassword;

    
    public void init(Stage stage) {
        btnRegister.setOnAction(e -> register(stage));
        btnLogin.setOnAction(e -> login(stage));
    }

    private void register(Stage stage){
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("../view/RegisterMenu.fxml"));
        try {
            Scene scene = new Scene(fxmlLoader.load());
            ((RegisterMenuController)fxmlLoader.getController()).init(stage);
            stage.setScene(scene);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void login(Stage stage){
        int pos = -1;
        if(!UserData.isTextFieldAnEmail(txtUser))
            pos = UserData.getUserPositionByName(txtUser.getText());
        else 
            pos = UserData.getUserPositionByEmail(txtUser.getText());
        
        if(pos != -1){
            if (UserData.getUsers().get(pos).getPassword().equals(txtPassword.getText())){
                if(txtUser.getText().equals("admin")){
                    logAdmin(stage);
                } else {
                    logUser(stage);
                }
            } else {
                CreateAlert.newAlert(AlertType.ERROR, "User/password incorrect");
            }
        } else {
            CreateAlert.newAlert(AlertType.ERROR, "User/password incorrect");
        }
    }

    private void logAdmin(Stage stage){
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("../view/Admin.fxml"));
        try {
            Scene scene = new Scene(fxmlLoader.load());
            ((AdminController)fxmlLoader.getController()).init(stage);
            stage.setScene(scene);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void logUser(Stage stage){
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("../view/User.fxml"));
        try {
            Scene scene = new Scene(fxmlLoader.load());
            ((UserController)fxmlLoader.getController()).init(stage);
            stage.setScene(scene);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}