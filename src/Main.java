import entity.Event;
import entity.Periodical;
import entity.Reminder;
import entity.Selection;
import manager.Calendar;
import java.time.Duration;
import java.time.LocalDateTime;
import java.time.YearMonth;
import java.time.format.DateTimeFormatter;
import java.util.LinkedList;
import java.util.List;
import java.util.Scanner;

public class Main {
    private static final Calendar calendar = new Calendar();
    private static final Scanner scanner = new Scanner(System.in);
    public static void main(String[] args) {
        boolean isInUsed = true;
        int choice;
        while (isInUsed){
            notification();
            calendarMenu();
            choice = scanner.nextInt();
            Selection selection = Selection.getSelectionByValue(choice);
            switch (selection){
                case ADD -> {
                    scanner.nextLine();
                    Event inputEven = inputEvent();
                    calendar.addEvent(inputEven);
                }
                case UPDATE -> {
                    long id = scanner.nextInt();
                    scanner.nextLine();
                    Event inputEven = inputEvent();
                    calendar.updateEvent(id,inputEven);
                }
                case DELETE -> {
                    System.out.print("Enter event ID: ");
                    long id = scanner.nextLong();
                    calendar.deleteEvent(id);
                }
                case TIMETONEXTEVENT -> {
                    calendar.timeToNextEvent();
                }
                case TIMEBETWEENTOEVENTS -> {
                    long idEvent1 = scanner.nextLong();
                    long idEvent2 = scanner.nextLong();
                    calendar.timeBetweenTwoEvents(idEvent1,idEvent2);
                }
                case SHOWEVENTINWEEK -> {
                    calendar.showEventInThisWeek();
                }
                case SHOWEVENTINMONTH -> {
                    YearMonth yearMonth = inputYearMonth();
                    calendar.showEventInMonth(yearMonth);
                }
                case EXIT -> {
                    isInUsed = false;
                }
            }
        }
    }
    private static Event inputEvent(){
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm");
        System.out.print("Enter start time (dd/MM/yyyy HH:mm): ");
        String startInput = scanner.nextLine();
        System.out.print("Enter duration(hours): ");
        int durationHours = scanner.nextInt();
        System.out.print("Enter duration(minutes): ");
        int durationMinutes = scanner.nextInt();
        Duration duration = Duration.ofHours(durationHours).plusMinutes(durationMinutes);
        scanner.nextLine();
        LocalDateTime start = LocalDateTime.parse(startInput, formatter);
        System.out.print("Enter end time (dd/MM/yyyy HH:mm): ");
        String endInput = scanner.nextLine();
        LocalDateTime end = LocalDateTime.parse(endInput, formatter);
        if (start.plus(duration).isAfter(end)) throw new IllegalArgumentException("Time can not be over the end.");
        if(start.plus(duration).toLocalDate().isAfter(start.toLocalDate())) throw new IllegalArgumentException("Event can not be over to the next day");
        System.out.print("Enter note: ");
        String note = scanner.nextLine();
        periodicalMenu();
        int choice = scanner.nextInt();
        Periodical periodical = Periodical.getPeriodicalByValue(choice);
        boolean isSettingReminder = true;
        List<Reminder> reminders = new LinkedList<>();
        while(isSettingReminder){
            System.out.println("Do you to set reminder? (y/n)");
            if (scanner.next().equals("y")){
                scanner.nextLine();
                System.out.print("Enter end time to remind (dd/MM/yyyy HH:mm): ");
                String input = scanner.nextLine();
                LocalDateTime timeToRemind  = LocalDateTime.parse(input, formatter);
                reminders.add(new Reminder(timeToRemind));
            }
            else isSettingReminder = false;
        }
        return new Event(start, duration, end, note, periodical,reminders);
    }

    private static void notification(){
        List<Reminder> reminders = calendar.getEvents().stream().flatMap(event -> event.getReminders().stream()).toList();
        LocalDateTime now = LocalDateTime.now();
        reminders.stream()
                .filter(reminder -> {
                    LocalDateTime remindTime = reminder.getTimeToRemind();
                    return !remindTime.isAfter(now) && remindTime.isAfter(now.minusMinutes(1));
                })
                .forEach(reminder -> System.out.println("Event Notification: " + reminder.getEvent()));

    }

    private static void calendarMenu(){
        System.out.println("Calendar App");
        for (Selection selection : Selection.values()){
            System.out.println(selection.getValue() + ": " + selection.getDescription());
        }
        System.out.print("Selection: ");
    }
    private static void periodicalMenu(){
        System.out.println("Choose Periodical");
        for (Periodical periodical : Periodical.values()){
            System.out.println(periodical.getValue() + ": " + periodical.getDescription());
        }
        System.out.print("Selection: ");
    }
    private static YearMonth inputYearMonth(){
        System.out.print("Enter year and month (yyyy-MM): ");
        String input = scanner.next();
        return YearMonth.parse(input);
    }
}