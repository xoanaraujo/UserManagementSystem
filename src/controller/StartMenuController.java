package controller;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.time.LocalDateTime;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.control.Alert.AlertType;
import javafx.stage.Stage;
import model.CreateAlert;
import model.User;
import model.AppData;

public class StartMenuController{

    @FXML Button btnRegister, btnLogin;
    @FXML TextField txtUser, txtPassword;

    
    public void init(Stage stage) {
        AppData.setUser(null);
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
        if(!AppData.isTextFieldAnEmail(txtUser))
            pos = AppData.getUserPositionByName(txtUser.getText());
        else 
            pos = AppData.getUserPositionByEmail(txtUser.getText());
        
        if(pos != -1){
            User user = AppData.getUsers().get(pos);
            if (user.getPassword().equals(txtPassword.getText())){
                regisLogDateTime(user.getName(), true);
                AppData.setUser(user);
                if(txtUser.getText().equals("admin")){
                    logAdmin(stage);
                } else {
                    logUser(stage);
                }
            } else {
                regisLogDateTime(user.getName(), false);
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

    private void regisLogDateTime(String name, boolean isPasswordOk){
        try(BufferedWriter out = new BufferedWriter(new FileWriter("access.log", true))){
            out.newLine();
            out.write(LocalDateTime.now() + " " + (isPasswordOk ? "OK " : "ERROR ") + name);
        } catch(IOException e1){
            e1.printStackTrace();
        }
    }
}