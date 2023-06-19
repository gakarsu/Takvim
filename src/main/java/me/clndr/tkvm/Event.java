package me.clndr.tkvm;

public class Event {
    private String operationTime;
    private String eventStartTime;
    private String eventDefinition;
    private String eventType;
    private String eventDescription;

    public Event(String operationTime, String eventStartTime, String eventDefinition, String eventType, String eventDescription) {
        this.operationTime = operationTime;
        this.eventStartTime = eventStartTime;
        this.eventDefinition = eventDefinition;
        this.eventType = eventType;
        this.eventDescription = eventDescription;
    }

    public String getOperationTime() {
        return operationTime;
    }

    public void setOperationTime(String operationTime) {
        this.operationTime = operationTime;
    }

    public String getEventStartTime() {
        return eventStartTime;
    }

    public void setEventStartTime(String eventStartTime) {
        this.eventStartTime = eventStartTime;
    }

    public String getEventDefinition() {
        return eventDefinition;
    }

    public void setEventDefinition(String eventDefinition) {
        this.eventDefinition = eventDefinition;
    }

    public String getEventType() {
        return eventType;
    }

    public void setEventType(String eventType) {
        this.eventType = eventType;
    }

    public String getEventDescription() {
        return eventDescription;
    }

    public void setEventDescription(String eventDescription) {
        this.eventDescription = eventDescription;
    }
}
