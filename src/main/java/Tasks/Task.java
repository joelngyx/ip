package Tasks;

/**
 * Constructs a generic Task object, which has the attributes
 * date (applies only for Event and Deadline type Tasks), Task
 * description, and the Task status
 */
import java.time.LocalDate;

public class Task {

    protected LocalDate date;
    protected String description;
    protected boolean isDone;

    public Task(String description) {
        this.description = description;
        this.isDone = false;
    }

    public Task(String description, LocalDate date) {
        this.description = description;
        this.isDone = false;
        this.date = date;
    }

    public String getStatusIcon() {
        return (isDone ? "\u2713" : "\u2718");
    }

    public void markDone(){
        this.isDone = true;
    }

    public void markNotDone() {
        this.isDone = false;
    }

    public String getTaskIcon(){
        return "Error";
    }

    public String getDescription() {
        return this.description;
    }

    public LocalDate getDate() {
        return this.date;
    }
}
