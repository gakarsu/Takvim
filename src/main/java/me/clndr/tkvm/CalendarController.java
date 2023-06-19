package me.clndr.tkvm;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.stage.Stage;

import java.io.*;
import java.util.ArrayList;
import java.util.List;

public class CalendarController {

    @FXML
    private TableView<Event> eventTable;

    @FXML
    private TableColumn<Event, String> operationTimeColumn;

    @FXML
    private TableColumn<Event, String> eventStartTimeColumn;

    @FXML
    private TableColumn<Event, String> eventDefinitionColumn;

    @FXML
    private TableColumn<Event, String> eventTypeColumn;

    @FXML
    private TableColumn<Event, String> eventDescriptionColumn;

    private ObservableList<Event> eventList;

    public void initialize() {
        eventList = FXCollections.observableArrayList();
        setupTableColumns();
        loadEventsFromFile();
        eventTable.setItems(eventList);
    }

    private void setupTableColumns() {
        operationTimeColumn.setCellValueFactory(cellData -> cellData.getValue().operationTimeProperty());
        eventStartTimeColumn.setCellValueFactory(cellData -> cellData.getValue().eventStartTimeProperty());
        eventDefinitionColumn.setCellValueFactory(cellData -> cellData.getValue().eventDefinitionProperty());
        eventTypeColumn.setCellValueFactory(cellData -> cellData.getValue().eventTypeProperty());
        eventDescriptionColumn.setCellValueFactory(cellData -> cellData.getValue().eventDescriptionProperty());
    }

    private void loadEventsFromFile() {
        try (BufferedReader br = new BufferedReader(new FileReader("fsql-event.txt"))) {
            String line;
            while ((line = br.readLine()) != null) {
                String[] parts = line.split(";");
                String operationTime = parts[0];
                String eventStartTime = parts[1];
                String eventDefinition = parts[2];
                String eventType = parts[3];
                String eventDescription = parts[4];

                Event event = new Event(operationTime, eventStartTime, eventDefinition, eventType, eventDescription);
                eventList.add(event);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @FXML
    private void addEventButtonClicked() {
        System.out.println("Olay Ekle butonuna tıklandı.");

        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("addEvent.fxml"));
            Parent root = loader.load();
            AddEventController addEventController = loader.getController();
            addEventController.setEventList(eventList);
            addEventController.setCalendarController(this);

            Stage stage = new Stage();
            stage.setTitle("Olay Ekle");
            stage.setScene(new Scene(root));
            stage.showAndWait();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void refreshTable() {
        eventList.clear();
        loadEventsFromFile();
        eventTable.setItems(eventList);
        eventTable.refresh();
    }

    @FXML
    private void editEventButtonClicked() {
        System.out.println("Olay Düzenle butonuna tıklandı.");

        Event selectedEvent = eventTable.getSelectionModel().getSelectedItem();
        if (selectedEvent == null) {
            System.out.println("Düzenlenecek olay seçilmedi.");
            return;
        }

        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("editEvent.fxml"));
            Parent root = loader.load();
            EditEventController editEventController = loader.getController();
            editEventController.setCalendarController(this);
            editEventController.setSelectedEvent(selectedEvent); // Seçili olayı geçir

            Stage stage = new Stage();
            stage.setTitle("Olay Düzenle");
            stage.setScene(new Scene(root));
            stage.showAndWait();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @FXML
    private void deleteEventButtonClicked() {
        System.out.println("Olay Sil butonuna tıklandı.");

        Event selectedEvent = eventTable.getSelectionModel().getSelectedItem();
        if (selectedEvent == null) {
            System.out.println("Silinecek olay seçilmedi.");
            return;
        }

        eventList.remove(selectedEvent);

        deleteEventFromFile(selectedEvent);

        eventTable.refresh();
    }

    private void deleteEventFromFile(Event event) {
        try {
            List<String> lines = new ArrayList<>();

            try (BufferedReader br = new BufferedReader(new FileReader("fsql-event.txt"))) {
                String line;
                while ((line = br.readLine()) != null) {
                    String[] parts = line.split(";");
                    if (!(parts[0].equals(event.getOperationTime()) &&
                            parts[1].equals(event.getEventStartTime()) &&
                            parts[2].equals(event.getEventDefinition()) &&
                            parts[3].equals(event.getEventType()) &&
                            parts[4].equals(event.getEventDescription()))) {
                        lines.add(line);
                    }
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

    @FXML
    private void refreshButtonClicked() {
        System.out.println("Yenile butonuna tıklandı.");

        eventList.clear();

        loadEventsFromFile();
    }
}