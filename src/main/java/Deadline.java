public class Deadline extends Task {

    public Deadline(String description) {
        super(description);
    }

    public String getTaskIcon(){
        return "[D]";
    }
}