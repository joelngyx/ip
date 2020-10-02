package Ui;

import Data.TaskList;
import Tasks.Task;

/**
 * The class Messages comprises of methods that produce
 * appropriate hints and notifications to the user
 */
public class Messages {

    public static final String MARGINS =
            "____________________________________________________________\n";


    public static final String BYE_MESSAGE = ""
            + MARGINS + "Bye. Hope to see you again soon!\n" + MARGINS;

    public static final String WELCOME_MESSAGE = ""
            + "Hello there!\n"
            + MARGINS
            + "|  _ \\ _   _| | _____ \n"
            + "| | | | | | | |/ / _ \\\n"
            + "| |_| | |_| |   <  __/\n"
            + "|____/ \\__,_|_|\\_\\___|\n"
            + MARGINS
            + "Welcome to Duke!\n"
            + "Otherwise known as Joel's CS2113T iP\n"
            + MARGINS;

    public static void printRemovedTask (TaskList list, int taskIndex){
        System.out.println("" + MARGINS
                + "Noted. I've removed this task:\n"
                + "  " + list.get(taskIndex - 1).getTaskIcon() + "["
                + list.get(taskIndex - 1).getStatusIcon() + "]"
                + list.get(taskIndex - 1).getDescription() + "\n"
                + "Now you have " + (list.size() - 1) + " tasks in the list.\n"
                + MARGINS
        );
    }

    public static void printTaskMarkedDone (TaskList list, int checkedIntIndex){
        System.out.println(""
                + MARGINS
                + "Nice! I've marked this task as done:\n"
                + "  " + list.get(checkedIntIndex - 1).getTaskIcon() + "["
                + list.get(checkedIntIndex - 1).getStatusIcon() + "]"
                + list.get(checkedIntIndex - 1).getDescription() + "\n"
                + MARGINS
        );
    }

    public static void printAddedTask(Tasks.Task input, int index){
        String task = "tasks";
        if(index == 0){
            task = "task";
        }
        System.out.println(""
                + MARGINS
                + "Got it. I've added this task: \n" + "  " + input.getTaskIcon() + "["
                + input.getStatusIcon() + "]" + input.getDescription()
                + "\nNow you have " + (index) + " " + task + " in the list.\n"
                + MARGINS
        );
    }

    public static String printTo(Task input){
        return ( input.getTaskIcon() + "["
                + input.getStatusIcon() + "]" + input.getDescription() + "\n");
    }

    public static String printTask(Task input){
        return ( input.getTaskIcon() + "["
                + input.getStatusIcon() + "]" + input.getDescription());
    }

    public static void printErrorEmptyInput(String s){
        System.out.println(MARGINS + "Tasks of type " + s
                + " cannot be empty\n" + MARGINS);
    }

    public static void printErrorInvalidInput() {
        System.out.println(MARGINS + "Please enter a valid input\n" + MARGINS);
    }

    public static void printDateInputError() {
        System.out.println(MARGINS + "Please enter dates in the form 'dd/MM/yyyy'\n"
                +  "e.g. deadline return book /by 2/12/2019\n" + MARGINS);
    }

    public static void printDateComponentError(String s) {
        System.out.println("Please enter a valid " + s + "\n");
    }

    public static void printNoTasksFound() {
        System.out.println("Sorry, no tasks found!");
    }

    public static void printPreparingToExit() {
        System.out.println("Preparing to leave...");
    }
}
