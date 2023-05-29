package controller;

import java.io.IOException;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.format.DateTimeParseException;

import app.App;
import jakarta.persistence.EntityTransaction;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.DatePicker;
import javafx.scene.control.TextField;
import javafx.scene.control.Alert.AlertType;
import javafx.stage.Stage;
import model.Activity;
import model.AppData;
import model.CreateAlert;

public class CreateActivityController {
    @FXML
    private Button btnCreate, btnClose;

    @FXML
    private DatePicker dateEnd, dateStart;

    @FXML
    private TextField txtHourEnd, txtHourStart, txtMax, txtPlace, txtTitle, txtDescription;

    public void init (Stage stage){
        btnCreate.setOnAction(e -> create());
        btnClose.setOnAction(e -> loadUserMenu(stage));
    }

    private void create(){
        if (!isAnyDataFieldEmpty()){
            try{
                LocalTime sHour = LocalTime.parse(txtHourStart.getText());
                LocalTime eHour = LocalTime.parse(txtHourEnd.getText());
                LocalDate sDate = dateStart.getValue();
                LocalDate eDate = dateEnd.getValue();
                LocalDateTime sDateTime = sDate.atTime(sHour);
                LocalDateTime eDateTime = eDate.atTime(eHour);
                if (sDate.compareTo(eDate) < 0){
                    try{
                        int max = Integer.parseInt(txtMax.getText());
                        if(max < 1)
                            throw new NumberFormatException();
                        
                        Activity activity = new Activity(txtTitle.getText(),txtDescription.getText(), sDateTime, eDateTime, txtPlace.getText(), AppData.getUser(), max);
                        
                        if (AppData.getActivityPositionByName(txtTitle.getText()) == -1){
                            AppData.getActivities().add(activity);
                            //AppData.saveActivitiesDataFile();
                            EntityTransaction tx = App.em.getTransaction();
                            tx.begin();
                            App.em.persist(activity);
                            tx.commit();
                            AppData.loadActivitiesDataFileSQL();
                            CreateAlert.newAlert(AlertType.INFORMATION, "Activity created");
                        } else {
                            CreateAlert.newAlert(AlertType.ERROR, "Activity title is already in use");
                        }
                    }catch(NumberFormatException e1){
                        CreateAlert.newAlert(AlertType.ERROR, "Max must be a number greater than 0");
                    }
                } else {
                    CreateAlert.newAlert(AlertType.ERROR, "End date must be later than the start date");
                }
            } catch (DateTimeParseException e1){
                CreateAlert.newAlert(AlertType.ERROR, "Hour format must be HH:MM:SS");
            }
        } else {
            CreateAlert.newAlert(AlertType.ERROR, "Data field empty");
        }
    }

    private boolean isAnyDataFieldEmpty(){
        return txtTitle.getText() == null || txtDescription.getText() == null || txtHourStart.getText() == null || txtHourEnd.getText() == null || txtMax.getText() == null || dateStart.getValue() == null || dateEnd.getValue() == null;
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
}
