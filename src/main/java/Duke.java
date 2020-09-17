import java.util.Scanner;

public class Duke {

    public static void main(String[] args) {
        System.out.println("Hello from\n" + Messages.WELCOME_MESSAGE);
        addingTasksToList();
    }

    //Processes
    public static void addingTasksToList(){
        Scanner in = new Scanner(System.in);
        Task[] list = new Task[100];
        int index = 0;
        String input = in.nextLine();

        while((!input.equals("bye")) && (!input.equals("Bye")) && (index < 100)){
            if(input.equals("list") || input.equals("List")){
                Messages.printTaskList(list);
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
        System.out.println(Messages.BYE_MESSAGE);
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
            Messages.printAddedTask(list[index], index);
        } else {
            String errorType = DukeExceptions.checkErrorType(input);
            switch(errorType){
            case ("Todo_Empty_Input"):
                Messages.printError_EmptyInput("todo");
                break;
            case ("Event_Empty_Input"):
                Messages.printError_EmptyInput("event");
                break;
            case ("Deadline_Empty_Input"):
                Messages.printError_EmptyInput("deadline");
                break;
            case ("Deadline_Lack_Slash"):
                Messages.printError_LackSlash("deadline");
                break;
            case ("Event_Lack_Slash"):
                Messages.printError_LackSlash("event");
                break;
            default:
                Messages.printAddingTaskError();
            }
        }
    }

    public static void updateIfTaskDone(String input, int index, Task[] list){
        String checkedStrIndex = input.replaceAll("[^0-9]", "");
        int checkedIntIndex = Integer.parseInt(checkedStrIndex);
        if(checkedIntIndex <= index + 1 && checkedIntIndex > 0){
            list[checkedIntIndex - 1].updateIsDone();
        }
        Messages.printTaskMarkedDone(list, checkedIntIndex);
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

    public static String checkErrorType(String s){
        String temp = s.replace("todo", "");
        temp = temp.replace("deadline", "");
        temp = temp.replace("event", "");
        temp = temp.replace("/", "");
        temp = temp.replace(" ", "");
        if(s.contains("todo")){
            if(temp.equals("")){
                return "Todo_Empty_Input";
            }
        }
        if(s.contains("event")){
            if(!s.contains("/")){
                return "Event_Lacks_Slash";
            } else if(temp.equals("")){
                return "Event_Empty_Input";
            }
        }
        if(s.contains("deadline")) {
            if (!s.contains("/")) {
                return "Deadline_Lacks_Slash";
            } else if (temp.equals("")) {
                return "Deadline_Empty_Input";
            }
        }
        return "Error";
    }
}

