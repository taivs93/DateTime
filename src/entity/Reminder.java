package entity;

import java.time.LocalDateTime;

public class Reminder {
    private Event event;
    private LocalDateTime timeToRemind;

    public Event getEvent() {
        return event;
    }

    public void setEvent(Event event) {
        this.event = event;
    }

    public LocalDateTime getTimeToRemind() {
        return timeToRemind;
    }

    public void setTimeToRemind(LocalDateTime timeToRemind) {
        this.timeToRemind = timeToRemind;
    }

    public Reminder(LocalDateTime timeToRemind) {
        this.timeToRemind = timeToRemind;
    }
}
