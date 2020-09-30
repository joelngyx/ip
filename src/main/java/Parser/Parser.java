package Parser;

import Commands.Commands;
import Data.TaskList;
import Ui.Messages;

import java.io.IOException;

public class Parser {

    // The Parser.Parser Class contains methods that interprets
    // the User's inputs and prepares the corresponding
    // commands

    public static int getIndex(String input, String type) {

        String temp = input.replace(type, "").replace(" ", "");
        if(temp.replaceAll("[0-9]", "").isEmpty()) {
            return Integer.parseInt(temp);
        } else {
            return (-1);
        }
    }

    public static void parseCommand(String input, TaskList list) throws IOException {

        // This method figures out what type of command the
        // input is to evoke

        int index;
        System.out.print(list.size() + "\n");
        int numberOfTasks = list.size();
        String temp = input.toLowerCase();

        if(temp.equals("list")){
            System.out.println(list.contentsOfTaskList());
        } else if(temp.contains("done")){
            index = getIndex(input,"done");
            if(index < 1 || index > (numberOfTasks + 1)){
                Messages.printGeneralError();
            } else {
                Commands.updateIfTaskIsDone(input, index, list);
            }
        } else if(temp.contains("delete")){
            index = getIndex(input,"delete");
            if(index < 1 || index > (numberOfTasks)){
                Messages.printGeneralError();
            } else {
                Commands.removeTask(list, input, index);
            }
        } else if (temp.contains("todo") || temp.contains("event") || temp.contains("deadline")) {
            if(!getTaskType(input).equals("Error")) {
                System.out.println(getDescription(getTaskType(input), input));
                Commands.addTask(getDescription(getTaskType(temp), input), getTaskType(input), list);
            }
        } else {
            Messages.printGeneralError();
        }
    }

    public static String getDescription(String type, String input) {
        String temp = input.toLowerCase();
        int lastIndex = temp.lastIndexOf(type);
        return input.substring(lastIndex);
    }

    public static String getTaskType(String s){

        boolean isTodo = checkForErrors(s, "todo");
        boolean isDeadline = checkForErrors(s, "deadline");
        boolean isEvent = checkForErrors(s, "event");
        if(isTodo){
            return "todo";
        } else if (isDeadline){
            return "deadline";
        } else if (isEvent){
            return "event";
        } else {
            return "Error";
        }
    }

    public static boolean checkForErrors(String s, String type) {
        String temp = s.replace(type, "").replace(" ","");
        temp = temp.replace("/", "");
        if (s.contains("todo")) {
            if(!temp.equals("")) {
                return s.contains(type);
            } else {
                Messages.printErrorEmptyInput(s);
            }
        }
        if(s.contains("event") || s.contains("deadline")){
            if(s.contains("/")) {
                if(!temp.equals("")){
                    return s.contains(type);
                } else {
                    Messages.printErrorEmptyInput(s);
                }
            } else {
                Messages.printErrorWrongSlash(s);
            }
        }
        return false;
    }
}
