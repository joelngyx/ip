import java.util.ArrayList;

public class Messages {
    public static final String MARGINS = "____________________________________________________________\n";

    public static final String BYE_MESSAGE = ""
            + MARGINS + "Bye. Hope to see you again soon!\n" + MARGINS;

    public static final String WELCOME_MESSAGE = ""
            + "|  _ \\ _   _| | _____ \n"
            + "| | | | | | | |/ / _ \\\n"
            + "| |_| | |_| |   <  __/\n"
            + "|____/ \\__,_|_|\\_\\___|\n"
            + MARGINS
            + "Hello! I'm Duke\n"
            + "What can I do for you?\n"
            + MARGINS;

    public static void printRemovedTask (ArrayList<Task> list, int checkedIntIndex){
        System.out.println(""
                + MARGINS
                + "Noted. I've removed this task:\n"
                + "  " + list.get(checkedIntIndex - 1).getTaskIcon() + "["
                + list.get(checkedIntIndex - 1).getStatusIcon() + "]"
                + list.get(checkedIntIndex - 1).description + "\n"
                + "Now you have " + (list.size() - 1) + " tasks in the list.\n"
                + MARGINS
        );
    }

    public static void printTaskMarkedDone (ArrayList<Task> list, int checkedIntIndex){
        System.out.println(""
                + MARGINS
                + "Nice! I've marked this task as done:\n"
                + "  " + list.get(checkedIntIndex - 1).getTaskIcon() + "["
                + list.get(checkedIntIndex - 1).getStatusIcon() + "]"
                + list.get(checkedIntIndex - 1).description + "\n"
                + MARGINS
        );
    }

    public static void printAddingTaskError(){
        System.out.println(MARGINS + "Please enter a valid input\n" + MARGINS);
    }

    public static void printAddedTask(Task input, int index){
        String task = "tasks";
        if(index == 0){
            task = "task";
        }
        System.out.println(""
                + MARGINS
                + "Got it. I've added this task: \n" + "  " + input.getTaskIcon() + "["
                + input.getStatusIcon() + "]" + input.description
                + "\nNow you have " + (index + 1) + " " + task + " in the list.\n"
                + MARGINS
        );
    }

    public static void printTaskList(ArrayList<Task> list){
        int count = 0;
        System.out.println("____________________________________________________________");
        while(count < list.size()){
            System.out.println(""
                    + (count + 1) + "." + list.get(count).getTaskIcon() + '['
                    + list.get(count).getStatusIcon() + "]"
                    + list.get(count).description
            );
            count ++;
        }
        System.out.println("____________________________________________________________");
    }

    public static void printError_EmptyInput(String s){
        System.out.println(MARGINS + "Tasks of type " + s + " cannot be empty\n" + MARGINS);
    }

    public static void printError_LackSlash(String s){
        System.out.println(MARGINS + "Please indicate the");
        if (s.equals("deadline")) {
            System.out.println("deadline");
        } else {
            System.out.println("duration");
        }
        System.out.println("of the task with a '/'\n" + MARGINS);
    }
}
