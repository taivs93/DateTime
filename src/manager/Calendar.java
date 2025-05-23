package manager;

import entity.Event;
import entity.Periodical;
import entity.Reminder;

import java.time.*;
import java.time.temporal.ChronoUnit;
import java.util.*;
public class Calendar {
    private final PriorityQueue<Event> events = new PriorityQueue<>();
    public void addEvent(Event event){
        for (Event existedEvent : events){
            if (isOverLapped(event,existedEvent)){
                System.out.println("The time is overlapped");
                return;
            }
        }
        event.updateId();
        events.add(event);
        for(Reminder reminder : event.getReminders()){
            reminder.setEvent(event);
        }
        System.out.println("Add event successfully");
        System.out.println(event);
    }

    public void updateEvent(Long id, Event updateEvent){
        Optional<Event> optionalExistedEvent = findEventById(id);
        if(optionalExistedEvent.isEmpty()){
            System.out.println("Id not found");
        }
        else{
            for (Event event : events){
                if (isOverLapped(event,updateEvent)){
                    System.out.println("The time is overlapped");
                    return;
                }
            }
            Event existedEvent = optionalExistedEvent.get();
            existedEvent.setStart(updateEvent.getStart());
            existedEvent.setEnd(updateEvent.getEnd());
            existedEvent.setPeriodical(updateEvent.getPeriodical());
            existedEvent.setNote(updateEvent.getNote());
            existedEvent.setDuration(updateEvent.getDuration());
            System.out.println("Update event successfully");
            System.out.println(existedEvent);
        }
    }

    public void deleteEvent(Long id){
        Optional<Event> optionalExistedEvent = findEventById(id);
        if (optionalExistedEvent.isEmpty()){
            System.out.println("Id not found");
        }
        else{
            events.remove(optionalExistedEvent.get());
        }
    }

    public void timeToNextEvent(){
        Optional<Event> optionalNextEvent = Optional.ofNullable(events.peek());
        if (optionalNextEvent.isEmpty()){
            System.out.println("You have no event in your calendar");
            return;
        }
        long days =  ChronoUnit.DAYS.between(LocalDateTime.now(),optionalNextEvent.get().getStart());
        long hours =  ChronoUnit.HOURS.between(LocalDateTime.now(),optionalNextEvent.get().getStart())
                - days*24;
        System.out.println("Time to next event: Days " + days + " hours " + hours);
    }

    public void timeBetweenTwoEvents(Long eventId1, Long eventId2){
        Optional<Event> optionalEvent1 = findEventById(eventId1);
        Optional<Event> optionalEvent2 = findEventById(eventId2);

        if(optionalEvent2.isEmpty() || optionalEvent1.isEmpty()){
            System.out.println("Event id not found");
        }
        else {
            LocalDateTime start1 = optionalEvent1.get().getStart();
            LocalDateTime start2 = optionalEvent2.get().getStart();

            LocalDateTime earlier = start1.isBefore(start2) ? start1 : start2;
            LocalDateTime later = start1.isAfter(start2) ? start1 : start2;

            long days = ChronoUnit.DAYS.between(earlier, later);
            long hours = ChronoUnit.HOURS.between(earlier, later) - days * 24;

            System.out.println("Time between two events: " + days + " days " + hours + " hours");
        }
    }

    public void showEventInThisWeek(){
        System.out.println("Event in this week:");
        for (Event event : events){
            LocalDate monday1 = LocalDate.now().with(DayOfWeek.MONDAY);
            LocalDate monday2 = event.getStart().toLocalDate().with(DayOfWeek.MONDAY);
            LocalDate monday3 = event.getStart().toLocalDate().with(DayOfWeek.MONDAY);
            boolean isSameWeek = monday1.equals(monday2) || monday1.equals(monday3);
            if (!isSameWeek) break;
            System.out.println(event);
        }
    }

    public void showEventInMonth(YearMonth yearMonth){
        System.out.println("Event in " + yearMonth.getMonth() + " " + yearMonth.getYear()+":");
        for (Event event : events){
            if(YearMonth.from(event.getStart()).equals(yearMonth)
                    ||YearMonth.from(event.getEnd()).equals(yearMonth) ){
                System.out.println(event);
            }
        }
    }
    private boolean isOverLapped(Event event1, Event event2) {
        List<LocalDateTime> startTimesOfEvent1 = getStartTimesOfEvent(event1);
        List<LocalDateTime> startTimesOfEvent2 = getStartTimesOfEvent(event2);
        Duration duration1 = event1.getDuration();
        Duration duration2 = event2.getDuration();

        for (LocalDateTime start1 : startTimesOfEvent1) {
            LocalDateTime end1 = start1.plus(duration1);

            for (LocalDateTime start2 : startTimesOfEvent2) {
                LocalDateTime end2 = start2.plus(duration2);
                if (start1.isBefore(end2) && start2.isBefore(end1)) {
                    return true;
                }
            }
        }
        return false;
    }
    private List<LocalDateTime> getStartTimesOfEvent(Event event){
        List<LocalDateTime> startTimesOfEvent = new LinkedList<>();
        LocalDateTime startTimeOfEvent = event.getStart();
        if(event.getPeriodical().equals(Periodical.DAY)){
            while(!startTimeOfEvent.isAfter(event.getEnd()))
            {
                startTimesOfEvent.add(startTimeOfEvent);
                startTimeOfEvent = startTimeOfEvent.plusDays(1);
            }
        }
        if(event.getPeriodical().equals(Periodical.WEEK)){
            while(!startTimeOfEvent.isAfter(event.getEnd()))
            {
                startTimesOfEvent.add(startTimeOfEvent);
                startTimeOfEvent = startTimeOfEvent.plusWeeks(1);
            }

        }
        if(event.getPeriodical().equals(Periodical.MONTH)){
            while(!startTimeOfEvent.isAfter(event.getEnd()))
            {
                startTimesOfEvent.add(startTimeOfEvent);
                startTimeOfEvent = startTimeOfEvent.plusMonths(1);
            }
        }

        return startTimesOfEvent;
    }

    private Optional<Event> findEventById(Long id){
        return events.stream().filter(event -> event.getId().equals(id)).findFirst();
    }

    public PriorityQueue<Event> getEvents() {
        return events;
    }
}
