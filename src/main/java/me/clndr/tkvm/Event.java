package me.clndr.tkvm;

import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;

public class Event {
    private final StringProperty operationTime;
    private final StringProperty eventStartTime;
    private final StringProperty eventDefinition;
    private final StringProperty eventType;
    private final StringProperty eventDescription;

    public Event(String operationTime, String eventStartTime, String eventDefinition, String eventType, String eventDescription) {
        this.operationTime = new SimpleStringProperty(operationTime);
        this.eventStartTime = new SimpleStringProperty(eventStartTime);
        this.eventDefinition = new SimpleStringProperty(eventDefinition);
        this.eventType = new SimpleStringProperty(eventType);
        this.eventDescription = new SimpleStringProperty(eventDescription);
    }

    public String getOperationTime() {
        return operationTime.get();
    }

    public void setOperationTime(String operationTime) {
        this.operationTime.set(operationTime);
    }

    public StringProperty operationTimeProperty() {
        return operationTime;
    }

    public String getEventStartTime() {
        return eventStartTime.get();
    }

    public void setEventStartTime(String eventStartTime) {
        this.eventStartTime.set(eventStartTime);
    }

    public StringProperty eventStartTimeProperty() {
        return eventStartTime;
    }

    public String getEventDefinition() {
        return eventDefinition.get();
    }

    public void setEventDefinition(String eventDefinition) {
        this.eventDefinition.set(eventDefinition);
    }

    public StringProperty eventDefinitionProperty() {
        return eventDefinition;
    }

    public String getEventType() {
        return eventType.get();
    }

    public void setEventType(String eventType) {
        this.eventType.set(eventType);
    }

    public StringProperty eventTypeProperty() {
        return eventType;
    }

    public String getEventDescription() {
        return eventDescription.get();
    }

    public void setEventDescription(String eventDescription) {
        this.eventDescription.set(eventDescription);
    }

    public StringProperty eventDescriptionProperty() {
        return eventDescription;
    }

    public String toFileString() {
        StringBuilder sb = new StringBuilder();
        sb.append(operationTime.get()).append(";");
        sb.append(eventStartTime.get()).append(";");
        sb.append(eventDefinition.get()).append(";");
        sb.append(eventType.get()).append(";");
        sb.append(eventDescription.get());
        return sb.toString();
    }
}
