package me.clndr.tkvm;

import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.DatePicker;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;

import javazoom.jl.player.Player;

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
    private CalendarController calendarController;

    public void setEventList(ObservableList<Event> eventList) {
        this.eventList = eventList;
    }

    public void setCalendarController(CalendarController calendarController) {
        this.calendarController = calendarController;
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

        Event newEvent = new Event(LocalDateTime.now().toString(), formattedDateTime, input1, input2, input3);
        eventList.add(newEvent);

        if (calendarController != null) {
            calendarController.refreshTable();
        }

        Stage stage = (Stage) datePicker.getScene().getWindow();
        stage.close();

        Thread alarmThread = new Thread(() -> {
            LocalDateTime currentDateTime = LocalDateTime.now();
            while (currentDateTime.isBefore(eventDateTime)) {
                currentDateTime = LocalDateTime.now();
                try {
                    Thread.sleep(1000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }

            playAlarmSound(time);
        });
        alarmThread.start();
    }

    private void playAlarmSound(String startTime) {
        Thread countdownThread = new Thread(() -> {
            try {
                DateTimeFormatter formatter = DateTimeFormatter.ofPattern("HH:mm");
                LocalTime eventStartTime = LocalTime.parse(startTime, formatter);

                LocalTime currentTime = LocalTime.now();

                long delayInMillis = eventStartTime.toSecondOfDay() - currentTime.toSecondOfDay();

                if (delayInMillis > 0) {
                    Thread.sleep(delayInMillis * 1000);
                }

                try {
                    URL resourceUrl = getClass().getResource("/sound/alarmses.mp3");

                    InputStream inputStream = resourceUrl.openStream();
                    Player player = new Player(inputStream);
                    player.play();
                } catch (Exception e) {
                    e.printStackTrace();
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        });

        countdownThread.start();
    }
}
