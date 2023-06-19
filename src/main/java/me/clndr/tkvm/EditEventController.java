package me.clndr.tkvm;

import javafx.fxml.FXML;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

import java.io.*;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

public class EditEventController {

    @FXML
    private TextField operationTimeTextField;

    @FXML
    private TextField eventStartTimeTextField;

    @FXML
    private TextField eventDefinitionTextField;

    @FXML
    private TextField eventTypeTextField;

    @FXML
    private TextField eventDescriptionTextField;

    private CalendarController calendarController;
    private Event selectedEvent;

    public void setCalendarController(CalendarController calendarController) {
        this.calendarController = calendarController;
        calendarController.refreshTable();
    }

    public void setSelectedEvent(Event selectedEvent) {
        this.selectedEvent = selectedEvent;
        fillFields();
        calendarController.refreshTable();
    }

    private void fillFields() {
        operationTimeTextField.setText(selectedEvent.getOperationTime());
        eventStartTimeTextField.setText(selectedEvent.getEventStartTime());
        eventDefinitionTextField.setText(selectedEvent.getEventDefinition());
        eventTypeTextField.setText(selectedEvent.getEventType());
        eventDescriptionTextField.setText(selectedEvent.getEventDescription());
    }

    @FXML
    private void saveButtonClicked() {
        String operationTime = operationTimeTextField.getText();
        String eventStartTime = eventStartTimeTextField.getText();
        String eventDefinition = eventDefinitionTextField.getText();
        String eventType = eventTypeTextField.getText();
        String eventDescription = eventDescriptionTextField.getText();

        Event event = new Event(operationTime, eventStartTime, eventDefinition, eventType, eventDescription);

        updateEventInFile(event);

        calendarController.refreshTable();

        Stage stage = (Stage) operationTimeTextField.getScene().getWindow();
        stage.close();
    }

    private void updateEventInFile(Event event) {
        try {
            List<String> lines = new ArrayList<>();

            try (BufferedReader br = new BufferedReader(new FileReader("fsql-event.txt"))) {
                String line;
                while ((line = br.readLine()) != null) {
                    lines.add(line);
                }
            }

            for (int i = 0; i < lines.size(); i++) {
                String[] parts = lines.get(i).split(";");
                if (parts[0].equals(selectedEvent.getOperationTime()) &&
                        parts[1].equals(selectedEvent.getEventStartTime()) &&
                        parts[2].equals(selectedEvent.getEventDefinition()) &&
                        parts[3].equals(selectedEvent.getEventType()) &&
                        parts[4].equals(selectedEvent.getEventDescription())) {
                    String updatedLine = event.toFileString();
                    lines.set(i, updatedLine);
                    break;
                }
            }

            try (BufferedWriter bw = new BufferedWriter(new FileWriter("fsql-event.txt"))) {
                for (String line : lines) {
                    bw.write(line);
                    bw.newLine();
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
