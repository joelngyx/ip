package Tasks;

import java.time.LocalDate;

public class Event extends Task{

    public Event(String description, LocalDate date) {
        super(description, date);
    }

    public String getTaskIcon(){
        return "[E]";
    }
}
