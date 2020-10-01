package Tasks;

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
        return (isDone ? "\u2713" : "\u2718"); //return tick or X symbols
    }

    public void updateIsDone(){
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

    public void addDate(LocalDate x) {
        this.date = x;
    }
}
