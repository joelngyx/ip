import java.util.Scanner;

public class Duke {

    public static final String BYE_MESSAGE = ""
            + "____________________________________________________________\n"
            + "Bye. Hope to see you again soon!\n"
            + "____________________________________________________________\n";
    public static final String WELCOME_MESSAGE = ""
            + "|  _ \\ _   _| | _____ \n"
            + "| | | | | | | |/ / _ \\\n"
            + "| |_| | |_| |   <  __/\n"
            + "|____/ \\__,_|_|\\_\\___|\n"
            + "____________________________________________________________\n"
            + "Hello! I'm Duke\n"
            + "What can I do for you?\n"
            + "____________________________________________________________\n";

    public static void main(String[] args) {
        System.out.println("Hello from\n" + WELCOME_MESSAGE);
        addToList();
    }

    public static void printMarkedDone (Task[] list, int checkedIntIndex){
        System.out.println(""
                + "____________________________________________________________\n"
                + "Nice! I've marked this task as done:\n"
                + "  " + list[checkedIntIndex - 1].getTaskIcon() + "["
                + list[checkedIntIndex - 1].getStatusIcon() + "]"
                + list[checkedIntIndex - 1].description
                + "\n____________________________________________________________"
        );
    }

    public static void printAddingError(){
        System.out.println(""
                + "____________________________________________________________\n"
                + "Please enter a valid input"
                + "\n____________________________________________________________"
        );
    }

    public static void printAddedTask(Task input, int index){
        String task = "tasks";
        if(index == 0){
            task = "task";
        }
        System.out.println(""
                + "____________________________________________________________\n"
                + "Got it. I've added this task: \n"
                + "  " + input.getTaskIcon() + "["
                + input.getStatusIcon() + "]" + input.description
                + "\nNow you have " + (index + 1) + " " + task + " in the list."
                + "\n____________________________________________________________\n"
        );
    }

    public static void printList(Task[] list){
        int count = 0;
        System.out.println("____________________________________________________________");
        while(list[count] != null){
            System.out.println(""
                    + (count + 1) + "." + list[count].getTaskIcon() + '['
                    + list[count].getStatusIcon() + "]" + " "
                    + list[count].description
            );
            count ++;
        }
        System.out.println("____________________________________________________________");
    }

    public static void addToList(){
        Scanner in = new Scanner(System.in);
        Task[] list = new Task[100];
        int index = 0;
        String input = in.nextLine();

        while((!input.equals("bye")) && (!input.equals("Bye")) && (index < 100)){
            if(input.equals("list") || input.equals("List")){
                printList(list);
            }
            else if(input.contains("done") || input.contains("Done")){
                updateIfDone(input, index, list);
            }
            else{
                addTask(list, index, input);
                if(getTaskType(input) != "Error") {
                    index++;
                }
            }
            input = in.nextLine();
        }
        System.out.println(BYE_MESSAGE);
    }

    public static void addTask (Task[] list, int index, String input){
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
            printAddingError();
        }
    }

    public static void updateIfDone(String input, int index, Task[] list){
        String checkedStrIndex = input.replaceAll("[^0-9]", "");
        int checkedIntIndex = Integer.parseInt(checkedStrIndex);
        if(checkedIntIndex <= index + 1 && checkedIntIndex > 0){
            list[checkedIntIndex - 1].updateIsDone();
        }
        printMarkedDone(list, checkedIntIndex);
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

    public static boolean checkTaskType(String s, String type){
        if((s.contains("deadline")) || (s.contains("event"))){
            if(!s.contains("/")){
                return false;
            }
        }
        return s.contains(type);
    }
}

