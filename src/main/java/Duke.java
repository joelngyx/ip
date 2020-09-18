import java.io.*;
import java.nio.Buffer;
import java.util.Scanner;

public class Duke {

    public static void main(String[] args) throws IOException {
        File duke = new File("data/duke.txt");
        System.out.println("full path: " + duke.getAbsolutePath());
        System.out.println("file exists?: " + duke.exists());
        System.out.println("is Directory?: " + duke.isDirectory());
        System.out.println("Hello from\n" + Messages.WELCOME_MESSAGE);
        addingTasksToList(duke);
    }

    //Processes
    public static void addingTasksToList(File duke) throws IOException {
        Scanner in = new Scanner(System.in);
        Task[] list = new Task[100];
        int index = load("duke.txt", list);
        String input = in.nextLine();

        while((!input.equals("bye")) && (!input.equals("Bye")) && (index < 100)){
            if(input.equals("list") || input.equals("List")){
                Messages.printTaskList(list);
            }
            else if(input.contains("done") || input.contains("Done")){
                updateIfTaskDone(input, index, list);
            }
            else if(input.contains("load")){
                printFile("duke.txt", list);
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

    public static void addATask (Task[] list, int index, String input) throws IOException {
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
            writeToFile("duke.txt", list[index], true);
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

    public static void updateIfTaskDone(String input, int index, Task[] list) throws IOException {
        String checkedStrIndex = input.replaceAll("[^0-9]", "");
        int checkedIntIndex = Integer.parseInt(checkedStrIndex);
        if(checkedIntIndex <= index + 1 && checkedIntIndex > 0){
            list[checkedIntIndex - 1].updateIsDone();
        }
        Messages.printTaskMarkedDone(list, checkedIntIndex);
        editFile("duke.txt", list);
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

    //FILE I/O
    public static void writeToFile(String filePath, Task input, boolean mode) throws IOException {
        FileWriter fw = new FileWriter(filePath, mode);
        fw.write(Messages.printToFile(input) + "\n");
        fw.close();
    }

    public static void printFile(String filePath, Task[] list) throws FileNotFoundException {
        File f = new File(filePath);
        Scanner s = new Scanner(f);
        s.useDelimiter("[,],\n");
        int count = 0;
        while(s.hasNext()){
            System.out.println(s.nextLine());
        }
    }

    public static void editFile(String filePath, Task[] list) throws IOException {
        File duke = new File(filePath);
        int i = 0;
        if(list[i] != null){
            writeToFile("duke.txt", list[i], false);
            i++;
        }
        while(list[i] != null){
            writeToFile("duke.txt", list[i], true);
            i++;
        }
    }

    public static int load (String filePath, Task[] list) throws FileNotFoundException {
        //This function populates the list with saved data on duke.txt
        Scanner sc = new Scanner(new File(filePath));
        int i = 0;
        while(sc.hasNextLine()){
            String task = sc.nextLine();
            char type = task.charAt(1);
            //System.out.println(type);
            char status = task.charAt(4);
            //System.out.println(status);
            String description = task.substring(6);
            switch(type){
            case('T'):
                list[i] = new Todo(description);
                break;
            case('E'):
                list[i] = new Event(description);
                break;
            case('D'):
                list[i] = new Deadline(description);
                break;
            }
            switch(status){
            case('\u2713'):
                list[i].isDone = true;
                break;
            case('\u2718'):
                list[i].isDone = false;
                break;
            }
            i ++;
        }
        return i;
    }
}

