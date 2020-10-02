package Tasks;

import java.time.LocalDate;

/**
 * The Event class, which is a type of Task
 */
public class Event extends Task{

    public Event(String description, LocalDate date) {
        super(description, date);
    }

    public String getTaskIcon(){
        return "[E]";
    }
}
