package Tasks;

import java.time.LocalDate;

/**
 * The Deadline class, which is a type of Task
 */
public class Deadline extends Task {

    public Deadline(String description, LocalDate date) {
        super(description, date);
    }

    public String getTaskIcon(){
        return "[D]";
    }
}
