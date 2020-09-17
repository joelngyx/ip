import java.util.Scanner;

public class Duke {

    public static void main(String[] args) {
        System.out.println("Hello from\n" + WELCOME_MESSAGE);
        addingTasksToList();
    }

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

    public static void printTaskMarkedDone (Task[] list, int checkedIntIndex){
        System.out.println(""
                + MARGINS
                + "Nice! I've marked this task as done:\n"
                + "  " + list[checkedIntIndex - 1].getTaskIcon() + "["
                + list[checkedIntIndex - 1].getStatusIcon() + "]"
                + list[checkedIntIndex - 1].description + "\n"
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

    public static void printTaskList(Task[] list){
        int count = 0;
        System.out.println("____________________________________________________________");
        while(list[count] != null){
            System.out.println(""
                    + (count + 1) + "." + list[count].getTaskIcon() + '['
                    + list[count].getStatusIcon() + "]"
                    + list[count].description
            );
            count ++;
        }
        System.out.println("____________________________________________________________");
    }

    public static void printError_EmptyInput(String s){
        System.out.println(MARGINS + "Tasks of type" + s + "cannot be empty\n" + MARGINS);
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

    //Processes
    public static void addingTasksToList(){
        Scanner in = new Scanner(System.in);
        Task[] list = new Task[100];
        int index = 0;
        String input = in.nextLine();

        while((!input.equals("bye")) && (!input.equals("Bye")) && (index < 100)){
            if(input.equals("list") || input.equals("List")){
                printTaskList(list);
            }
            else if(input.contains("done") || input.contains("Done")){
                updateIfTaskDone(input, index, list);
            }
            else{
                addATask(list, index, input);
                if(getTaskType(input) != "Error") {
                    index++;
                }
            }
            input = in.nextLine();
        }
        System.out.println(BYE_MESSAGE);
    }

    public static void addATask (Task[] list, int index, String input){
        String type = getTaskType(input);
        if(type != "Error") {
            switch (type) {
            case ("Todo"):
                list[index] = new Todo(input.replace("todo", ""));
                break;
            case ("Deadline"):
                list[index] = new Deadline(input.replace("deadline", "").
                        replace("/", "(") + ")");
                break;
            case ("Event"):
                list[index] = new Event(input.replace("event", "").
                            replace("/", "(") + ")");
                break;
            }
            printAddedTask(list[index], index);
        } else {
            String errorType = checkErrorType(input);
            switch(errorType){
            case ("Todo_Empty_Input"):
                printError_EmptyInput("todo");
                break;
            case ("Event_Empty_Input"):
                printError_EmptyInput("event");
                break;
            case ("Deadline_Empty_Input"):
                printError_EmptyInput("deadline");
                break;
            case ("Deadline_Lack_Slash"):
                printError_LackSlash("deadline");
                break;
            case ("Event_Lack_Slash"):
                printError_LackSlash("event");
                break;
            default:
                printAddingTaskError();
            }

        }
    }

    public static void updateIfTaskDone(String input, int index, Task[] list){
        String checkedStrIndex = input.replaceAll("[^0-9]", "");
        int checkedIntIndex = Integer.parseInt(checkedStrIndex);
        if(checkedIntIndex <= index + 1 && checkedIntIndex > 0){
            list[checkedIntIndex - 1].updateIsDone();
        }
        printTaskMarkedDone(list, checkedIntIndex);
    }

    public static String getTaskType(String s){
        //Types can be Todo, Deadline or Event
        boolean isTodo = checkTaskType(s, "todo");
        boolean isDeadline = checkTaskType(s, "deadline");
        boolean isEvent = checkTaskType(s, "event");
        if(isTodo){
            return "Todo";
        } else if (isDeadline){
            return "Deadline";
        } else if (isEvent){
            return "Event";
        } else {
            return "Error";
        }
    }

    public static boolean checkTaskType(String s, String type) {
        String temp = s.replace(type, "");
        temp = temp.replace("/", "");
        if (s.contains("todo")) {
            if(!temp.equals("")) {
                return s.contains(type);
            }
        }
        if(s.contains("event") || s.contains("deadline")){
            if(s.contains("/")) {
                if(!temp.equals("")){
                    return s.contains(type);
                }
            }
        }
        return false;
    }
}

