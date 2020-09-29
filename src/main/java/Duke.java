import java.io.FileNotFoundException;
import java.util.Scanner;
import java.util.ArrayList;
import java.io.File;
import java.io.IOException;
import java.io.FileWriter;

public class Duke {

    public static void main(String[] args) throws IOException {
        File duke = new File("duke.txt");
        duke.createNewFile();
        System.out.println("full path: " + duke.getAbsolutePath());
        System.out.println("file exists?: " + duke.exists());
        System.out.println("is Directory?: " + duke.isDirectory());
        System.out.println("Hello from\n" + Messages.WELCOME_MESSAGE);
        addingTasksToList(duke);
    }

    //Processes
    public static void addingTasksToList(File duke) throws IOException {
        Scanner in = new Scanner(System.in);
        ArrayList<Task> list = new ArrayList<>();
        int index = load("duke.txt", list);
        String input = in.nextLine();

        while((!input.equals("bye")) && (!input.equals("Bye")) && (index < 100)){
            if(input.equals("list") || input.equals("List")){
                Messages.printTaskList(list);
            }
            else if(input.contains("done") || input.contains("Done")){
                updateIfTaskIsDone(input, index, list);
            }
            else if(input.contains("delete") || input.contains("Delete")){
                removeTask(list, input, index);
                index --;
                overWriteFile("duke.txt", list);
            }
            else{
                addATask(list, index, input);
                if(!getTaskType(input).equals("Error")) {
                    index++;
                }
            }
            input = in.nextLine();
        }
        //overWriteFile("duke.txt", list);
        System.out.println(Messages.BYE_MESSAGE);
    }

    public static void addATask (ArrayList<Task> list, int index, String input) throws IOException {
        String type = getTaskType(input);
        if(type != "Error") {
            switch (type) {
            case ("Todo"):
                list.add(new Todo(input.replace("todo", "")));
                break;
            case ("Deadline"):
                list.add(new Deadline(input.replace("deadline", "").
                        replace("/", "(") + ")"));
                break;
            case ("Event"):
                list.add(new Event(input.replace("event", "").
                            replace("/", "(") + ")"));
                break;
            }
            appendToFile("duke.txt", list.get(index), true);
            Messages.printAddedTask(list.get(index), index);
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


    public static void updateIfTaskIsDone(String input, int index, ArrayList<Task> list) throws IOException {
        String checkedStrIndex = input.replaceAll("[^0-9]", "");
        int checkedIntIndex = Integer.parseInt(checkedStrIndex);
        if(checkedIntIndex <= index + 1 && checkedIntIndex > 0){
            list.get(checkedIntIndex - 1).updateIsDone();
        }
        Messages.printTaskMarkedDone(list, checkedIntIndex);
        overWriteFile("duke.txt", list);
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

    public static void removeTask(ArrayList<Task> list, String input, int index) throws IOException {
        String checkedStrIndex = input.replaceAll("[^0-9]", "");
        int checkedIntIndex = Integer.parseInt(checkedStrIndex);
        if(checkedIntIndex <= index + 1 && checkedIntIndex > 0){
            Messages.printRemovedTask(list, checkedIntIndex);
            list.remove(checkedIntIndex - 1);
        }
    }

    //FILE I/O
    public static void appendToFile(String filePath, Task input, boolean mode) throws IOException {
        FileWriter fw = new FileWriter(filePath, mode);
        fw.write(Messages.printToFile(input));
        fw.close();
    }

    public static void overWriteFile(String filePath, ArrayList <Task> list) throws IOException {
        int i = 0;
        FileWriter fw = new FileWriter(filePath, false);
        while (i < list.size()){
           fw.write(Messages.printToFile(list.get(i)));
           i++;
        }
        fw.close();
    }

    public static int load (String filePath, ArrayList<Task> list) throws FileNotFoundException {
        //This function populates the list with saved data on duke.txt
        Scanner sc = new Scanner(new File(filePath));
        int i = 0;
        while (sc.hasNextLine()) {
            String task = sc.nextLine();
            char type = task.charAt(1);
            //System.out.println(type);
            char status = task.charAt(4);
            //System.out.println(status);
            String description = task.substring(6);
            switch (type) {
                case ('T'):
                    list.add(new Todo(description));
                    break;
                case ('E'):
                    list.add(new Event(description));
                    break;
                case ('D'):
                    list.add(new Deadline(description));
                    break;
            }
            switch (status) {
                case ('\u2713'):
                    list.get(i).isDone = true;
                    break;
                case ('\u2718'):
                    list.get(i).isDone = false;
                    break;
            }
            i++;
        }
        return i;
    }
}

// Git test