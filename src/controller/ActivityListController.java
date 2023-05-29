package controller;

import java.io.IOException;

import app.App;
import jakarta.persistence.EntityTransaction;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ListView;
import javafx.scene.control.Alert.AlertType;
import javafx.stage.Stage;
import model.Activity;
import model.AppData;
import model.CreateAlert;

public class ActivityListController {
    @FXML
    private Button btnClose, btnJoinActivity;
    @FXML
    private ListView<Activity> listViewActivities;

    public void init(Stage stage){
        btnClose.setOnAction(e -> loadUserMenu(stage));
        btnJoinActivity.setOnAction(e -> joinActivity());
        updateList();
    }

    private void updateList(){
        listViewActivities.getItems().setAll(AppData.getActivities());
    }

    private void loadUserMenu(Stage stage){
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("../view/User.fxml"));
        try {
            Scene scene = new Scene(fxmlLoader.load());{}
            ((UserController)fxmlLoader.getController()).init(stage);
            stage.setScene(scene);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void joinActivity(){
        Activity activity = listViewActivities.getSelectionModel().getSelectedItem();

        if (activity != null){
            if (activity.getParticipantByName(AppData.getUser().getName()) == -1 && activity.getParticipants().size() < activity.getPlazas()){
                EntityTransaction tx = App.em.getTransaction();
                tx.begin();
                activity.getParticipants().add(AppData.getUser());
                tx.commit();
                AppData.loadActivitiesDataFileSQL();
                btnJoinActivity.setText("Leave");
                btnJoinActivity.setStyle("-fx-background-color: #990000");
            } else {
                EntityTransaction tx = App.em.getTransaction();
                tx.begin();
                activity.getParticipants().remove(AppData.getUser());
                tx.commit();
                AppData.loadActivitiesDataFileSQL();
                btnJoinActivity.setText("Join");
                btnJoinActivity.setStyle("-fx-background-color: #059900");
            }
        } else {
            CreateAlert.newAlert(AlertType.ERROR, "Activity not selected");
        }

    }
}
