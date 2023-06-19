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

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
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
        if(eventList != null) {
            eventTable.setItems(eventList);
        }
        loadEventsFromFile();
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

            Stage stage = new Stage();
            stage.setTitle("Olay Ekle");
            stage.setScene(new Scene(root));
            stage.showAndWait();

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @FXML
    private void editEventButtonClicked() {
        System.out.println("Olay Düzenle butonuna tıklandı.");
        // Olay düzenleme işlemleri burada gerçekleştirilebilir.
    }

    @FXML
    private void deleteEventButtonClicked() {
        System.out.println("Olay Sil butonuna tıklandı.");
        // Olay silme işlemleri burada gerçekleştirilebilir.
    }
}