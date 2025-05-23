package entity;

import java.time.Duration;
import java.time.LocalDateTime;
import java.util.List;

public class Event implements Comparable<Event> {
    private static Long idCounter = 1L;
    private Long id;
    private LocalDateTime start;
    private Duration duration;
    private LocalDateTime end;
    private String note;
    private Periodical periodical;
    private List<Reminder> reminders;

    public Event(LocalDateTime start,Duration duration, LocalDateTime end, String note, Periodical periodical, List<Reminder> reminders) {
        this.start = start;
        this.duration = duration;
        this.end = end;
        this.note = note;
        this.periodical = periodical;
        this.reminders = reminders;
    }

    public Long getId() {
        return id;
    }

    public LocalDateTime getStart() {
        return start;
    }

    public void setStart(LocalDateTime start) {
        this.start = start;
    }

    public Duration getDuration() {
        return duration;
    }

    public void setDuration(Duration duration) {
        this.duration = duration;
    }

    public LocalDateTime getEnd() {
        return end;
    }

    public void setEnd(LocalDateTime end) {
        this.end = end;
    }

    public Periodical getPeriodical() {
        return periodical;
    }

    public void setPeriodical(Periodical periodical) {
        this.periodical = periodical;
    }

    public String getNote() {
        return note;
    }

    public void setNote(String note) {
        this.note = note;
    }

    public void updateId(){
        this.id = idCounter;
        idCounter++;
    }

    public List<Reminder> getReminders() {
        return reminders;
    }

    public void setReminders(List<Reminder> reminders) {
        this.reminders = reminders;
    }

    @Override
    public int compareTo(Event o) {
        return this.start.compareTo(o.getStart());
    }

    @Override
    public String toString() {
        return "Event{" +
                "id=" + id +
                ", start=" + start +
                ", duration=" + duration +
                ", end=" + end +
                ", note='" + note + '\'' +
                ", periodical=" + periodical +
                '}';
    }
}
