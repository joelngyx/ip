package Ui;

import Data.TaskList;
import Tasks.Task;

public class Messages {
    public static final String MARGINS = "____________________________________________________________\n";

    public static final String BYE_MESSAGE = ""
            + MARGINS + "Bye. Hope to see you again soon!\n" + MARGINS;

    public static final String WELCOME_MESSAGE = ""
            + MARGINS
            + "|  _ \\ _   _| | _____ \n"
            + "| | | | | | | |/ / _ \\\n"
            + "| |_| | |_| |   <  __/\n"
            + "|____/ \\__,_|_|\\_\\___|\n"
            + MARGINS
            + "Welcome to Duke!\n"
            + "Otherwise known as Joel's CS2113T iP\n"
            + MARGINS;

    public static void printRemovedTask (TaskList list, int checkedIntIndex){
        System.out.println(""
                + MARGINS
                + "Noted. I've removed this task:\n"
                + "  " + list.get(checkedIntIndex - 1).getTaskIcon() + "["
                + list.get(checkedIntIndex - 1).getStatusIcon() + "]"
                + list.get(checkedIntIndex - 1).getDescription() + "\n"
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

    public static String printToFile(Task input){
        return ( input.getTaskIcon() + "["
                + input.getStatusIcon() + "]" + input.getDescription() + "\n");
    }

    public static void printErrorEmptyInput(String s){
        System.out.println(MARGINS + "Tasks of type " + s + " cannot be empty\n" + MARGINS);
    }

    public static void printErrorWrongSlash(String s){
        System.out.println(MARGINS + "Hint" + ": " + "Please indicate the");
        if (s.equals("deadline")) {
            System.out.println("deadline");
        } else {
            System.out.println("duration");
        }
        System.out.println("of the task with 3 '/' characters\n"
                + "e.g. deadline return book /by 2/12/2019\n" + MARGINS);
    }

    public static void printGeneralError() {
        System.out.println(MARGINS + "Please enter a valid input\n" + MARGINS);
    }

    public static void printDateInputError() {
        System.out.println(MARGINS + "Please enter dates in the form 'dd/MM/yyyy'\n"
                +  "e.g. deadline return book /by 2/12/2019\n" + MARGINS);
    }

    public static void printDateComponentError (String s) {
        System.out.println("Enter a valid " + s + "\n");
    }
}
