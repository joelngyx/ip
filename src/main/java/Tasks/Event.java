package Tasks;

public class Event extends Task{

    public Event(String description) {
        super(description);
    }

    public String getTaskIcon(){
        return "[E]";
    }
}
