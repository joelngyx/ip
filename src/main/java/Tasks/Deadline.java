package Tasks;

import java.time.LocalDate;

public class Deadline extends Task {

    public Deadline(String description, LocalDate date) {
        super(description, date);
    }

    public String getTaskIcon(){
        return "[D]";
    }
}
