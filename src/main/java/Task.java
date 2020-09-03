public class Task {
    protected String description;
    protected boolean isDone;
    public static int numberOfTasks = 0;

    public Task(String description) {
        this.description = description;
        this.isDone = false;
    }

    public String getStatusIcon() {
        return (isDone ? "\u2713" : "\u2718"); //return tick or X symbols
    }

    public void updateIsDone(){
        this.isDone = true;
    }

    public String getTaskIcon(){
        return "Error";
    }
}
