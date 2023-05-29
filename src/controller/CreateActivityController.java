package controller;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.format.DateTimeParseException;

import javafx.fxml.FXML;
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
    }

    private void create(){
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
                    AppData.getUser().getActivities().add(
                    new Activity(txtTitle.getText(),txtDescription.getText(), sDateTime, eDateTime, txtPlace.getText(), AppData.getUser(), max));
                    AppData.saveDataFile();
                }catch(NumberFormatException e1){
                    CreateAlert.newAlert(AlertType.ERROR, "Max must be a number greater than 0");
                }
            } else {
                CreateAlert.newAlert(AlertType.ERROR, "End date must be later than the start date");
            }
        } catch (DateTimeParseException e1){
            CreateAlert.newAlert(AlertType.ERROR, "Hour format must be HH:MM:SS");
        }
    }
}
