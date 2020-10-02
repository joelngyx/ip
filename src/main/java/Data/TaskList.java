package Data;

import Tasks.Task;

import java.io.IOException;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;

/**
 * A modified ArrayList which stores the User's Tasks
 */
public class TaskList {

    // Used to instantiates an ArrayList of Tasks
    // Contains methods to be called by commands
    // Namely,
    // 1) Adding tasks
    // 2) Listing tasks in the Data.TaskList
    // 3) Updating isDone for tasks
    // 4) Removing tasks

    private final ArrayList<Task> taskList;

    public TaskList() {
        taskList = new ArrayList<>();
    }

    public void addToList(Task task) {
        taskList.add(task);
    }

    public void addToFile(Task task) throws IOException {
        Storage.appendToFile(task);
    }

    public Task get(int i) {
        return taskList.get(i);
    }

    public int size(){
        return taskList.size();
    }

    public String contentsOfTaskList() {

        int count = 0;
        StringBuilder sb = new StringBuilder();
        sb.append("____________________________________________________________");
        while(count < taskList.size()){
            sb.append("\n").append(count + 1).append(".").
                    append(taskList.get(count).getTaskIcon()).append('[').
                    append(taskList.get(count).getStatusIcon()).
                    append("]");
            if(taskList.get(count).getDate() != null) {
                sb.append(taskList.get(count).getDescription().replace(
                        taskList.get(count).getDate().format(DateTimeFormatter
                        .ofPattern("dd/MM/yyyy")), taskList.get(count).
                        getDate().format(DateTimeFormatter.ofPattern("yyyy/dd/MM"))
                    )
                );
            }
            else {
                sb.append(taskList.get(count).getDescription());
            }
            count++;
        }
        sb.append("\n");
        sb.append("____________________________________________________________");

        return sb.toString();
    }

    public void remove(int i) {
        taskList.remove(i);
    }
}
