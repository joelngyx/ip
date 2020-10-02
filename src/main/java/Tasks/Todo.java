package Tasks;

/**
 * The Todo class, which is a type of Task
 */
public class Todo extends Task {

    public Todo(String description) {
        super(description);
    }

    public String getTaskIcon(){
        return "[T]";
    }
}
