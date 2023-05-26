package controller;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.time.LocalDateTime;

import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.control.TextField;
import javafx.scene.control.Alert.AlertType;
import javafx.stage.Stage;
import model.User;
import model.AppData;
import model.CreateAlert;

public class AdminController {

    @FXML
    private Button btnBan, btnDelete, btnLogOut, btnSave, btnExport;

    @FXML
    private Label lblPassword;

    @FXML private TextField txtPassword;
    @FXML private ListView<User> listViewUsers;

    public void init(Stage stage){
        User user = AppData.getUser();
        user.setLastLoginDateTime(LocalDateTime.now());
        lblPassword.setText(user.getPassword());

        updateList();

        listViewUsers.setOnMouseClicked(e -> handlelistViewOnMouseClicked());

        btnSave.setOnAction(e -> changeData(user));
        btnDelete.setOnAction(e -> deleteUser(listViewUsers.getSelectionModel().getSelectedItem()));
        btnLogOut.setOnAction(e -> loadStartMenu(stage));
        btnExport.setOnAction(e -> exportToCsv());
        btnBan.setOnAction(e -> banUser(listViewUsers.getSelectionModel().getSelectedItem()));
    }

    private void exportToCsv() {
        File file = new File("usuarios.csv");
        try(BufferedWriter ficheroSalida = new BufferedWriter(new FileWriter(file))){
            for (int i = 0; i < AppData.getUsers().size(); i++) {
                ficheroSalida.write(AppData.getUsers().get(i).toCsv());
                ficheroSalida.newLine();
            }
        CreateAlert.newAlert(AlertType.INFORMATION, "Datafile exported successfully to csv");
        } catch (Exception e1){
            e1.printStackTrace();
        }
    }

    private void changeData(User user){
        if (txtPassword.getText() != ""){
            user.setPassword(txtPassword.getText());
            AppData.saveDataFile();
            CreateAlert.newAlert(AlertType.INFORMATION, "Changes saved successfully");

            lblPassword.setText(user.getPassword());
            txtPassword.setText("");
        } else {
            CreateAlert.newAlert(AlertType.ERROR, "Empty Field");
        }
    }

    private void banUser(User user){
        if (user != null){
            if (!user.getName().equals("admin")){
                if (user.isBanned())
                user.setBanned(false);
                else
                    user.setBanned(true);
                AppData.saveDataFile();
                updateList();
            } else {
                CreateAlert.newAlert(AlertType.ERROR, "Admin cannot be banned");
            }
        } else {
            CreateAlert.newAlert(AlertType.ERROR, "User not selected");
        }
        setCssToDefault();
    }

    private void deleteUser(User user){
        if (user != null) {
            if (!user.getName().equals("admin")){
                AppData.getUsers().remove(user);
                AppData.saveDataFile();
                updateList();
            } else {
                CreateAlert.newAlert(AlertType.ERROR, "Admin cannot be removed");
            }
        } else {
            CreateAlert.newAlert(AlertType.ERROR, "User not selected");
        }
        setCssToDefault();
    }

    private void updateList(){
        listViewUsers.getItems().setAll(AppData.getUsers());
    }

    private void handlelistViewOnMouseClicked(){
        btnDelete.setText("DELETE");
        btnDelete.setStyle("-fx-background-color: #990000");
        
        if (listViewUsers.getSelectionModel().getSelectedItem().isBanned()){
            btnBan.setText("UNBAN");
            btnBan.setStyle("-fx-background-color: #059900");
        } else {
            btnBan.setText("BAN");
            btnBan.setStyle("-fx-background-color: #990000");
        }
    }

    private void setCssToDefault(){
        btnBan.setText("");
        btnBan.setStyle("-fx-background-color: #202020");
        btnDelete.setText("");
        btnDelete.setStyle("-fx-background-color: #202020");
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