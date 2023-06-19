package me.clndr.tkvm;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.stage.Stage;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;

public class AddEventController {

    @FXML
    private DatePicker datePicker;

    @FXML
    private TextField timeField;

    @FXML
    private TextField input1Field;

    @FXML
    private TextField input2Field;

    @FXML
    private TextField input3Field;

    private ObservableList<Event> eventList;

    public void setEventList(ObservableList<Event> eventList) {
        this.eventList = eventList;
    }

    @FXML
    private void saveEventButtonClicked() {
        LocalDate date = datePicker.getValue();
        String time = timeField.getText();
        String input1 = input1Field.getText();
        String input2 = input2Field.getText();
        String input3 = input3Field.getText();

        LocalDateTime eventDateTime = LocalDateTime.of(date, LocalTime.parse(time));
        String formattedDateTime = eventDateTime.toString();

        String eventRecord = LocalDateTime.now() + ";" + formattedDateTime + ";" + input1 + ";" + input2 + ";" + input3;

        try (BufferedWriter writer = new BufferedWriter(new FileWriter("fsql-event.txt", true))) {
            writer.write(eventRecord);
            writer.newLine();
        } catch (IOException e) {
            e.printStackTrace();
        }

        Event newEvent = new Event(LocalDate.now().toString(), formattedDateTime, input1, input2, input3);
        eventList.add(newEvent);

        Stage stage = (Stage) datePicker.getScene().getWindow();
        stage.close();
    }
}
