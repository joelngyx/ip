package Parser;

import Commands.Commands;
import Data.TaskList;
import Ui.Messages;

import java.io.IOException;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Objects;

public class Parser {

    // The Parser Class contains methods that interprets
    // the User's inputs and prepares the corresponding commands

    public static String REGEX = "\\d+";


    public static int getIndex(String input, String type) {

        // Returns the index provided in a DELETE or DONE command

        String temp = input.replace(type, "").replace(" ", "");
        if(temp.replaceAll(REGEX, "").isEmpty()) {
            return Integer.parseInt(temp);
        } else {
            return (-1);
        }
    }

    public static void parseCommand(String input, TaskList list) throws IOException {

        // Figures out what type of command the input is to evoke

        int index;
        LocalDate date;
        int numberOfTasks = list.size();
        input = input.toLowerCase();

        if(input.equals("list")){
            System.out.println(list.contentsOfTaskList());
        }
        else if(input.contains("done")){
            index = getIndex(input,"done");
            if(index < 1 || index > (numberOfTasks + 1)){
                Messages.printGeneralError();
            } else {
                Commands.updateIfTaskIsDone(input, index, list);
            }
        }
        else if(input.contains("delete")){
            index = getIndex(input,"delete");
            if(index < 1 || index > (numberOfTasks)){
                Messages.printGeneralError();
            } else {
                Commands.removeTask(list, input, index);
            }
        }
        else if (input.contains("todo")) {
            if(!getTaskType(input).equals("Error")) {
                System.out.println(getDescription(getTaskType(input), input));
                Commands.addTodoTask(getDescription(getTaskType(input), input), list);
            }
        }
        else if (input.contains("event") || input.contains("deadline")){
            if(!getTaskType(input).equals("Error")) {
                date = getDate(input);
                if(date == null){
                    Messages.printDateInputError();
                }
                else {
                    System.out.print(date.format(DateTimeFormatter.ofPattern("dd/MM/yyyy")));
                    Commands.addEventOrDeadlineTask(getDescription(getTaskType(input), input),
                            getTaskType(input), list, date);
                }
            }
        }
        else {
            Messages.printGeneralError();
        }
    }

    public static String getDescription(String type, String input) {

        String ch;
        int lastIndex = input.lastIndexOf(type);
        if(type.equals("deadline") || type.equals("event")){
            int positionOfFirstSlash = input.indexOf("[/,(]");
            int positionOfSecondSlash = input.indexOf("/", positionOfFirstSlash + 1);
            if(positionOfSecondSlash > 2){
                ch = String.valueOf(input.charAt(positionOfSecondSlash - 2));
            }
            else {
                return "SOMETHING WRONG";
            }
            if(ch.matches(REGEX)){
                return (input.substring(lastIndex, positionOfSecondSlash - 2).replace("/", "(")
                        + getDate(input).format(DateTimeFormatter.ofPattern("dd/MM/yyyy"))
                        + ")");
            }
            else{
                return (input.substring(lastIndex, positionOfSecondSlash - 1).replace("/", "(")
                        + getDate(input).format(DateTimeFormatter.ofPattern("dd/MM/yyyy"))
                        + ")");
            }
        }
        return input.substring(lastIndex);
    }

    public static String getTaskType(String s){

        boolean isTodo = checkForGeneralErrors(s, "todo");
        boolean isDeadline = checkForGeneralErrors(s, "deadline");
        boolean isEvent = checkForGeneralErrors(s, "event");
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

    public static boolean checkForGeneralErrors(String s, String type) {

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

    public static LocalDate getDate(String s) {

        int day;
        int month;
        int year;

        LocalDate date;

        // Expects input in the form TYPE + description + /by dd/mm/yyyy + time

        s = s.replace(" ", "");
        s += " ";
        char[] ch = s.toCharArray();
        System.out.println(s);

        // Get the positions of the '/' characters

        int count = 0;
        int[] indexArray = new int[4];

        for(int i = 0; i < ch.length; i ++){
            if(ch[i] == '/' || ch[i] == '('){
                indexArray[count] = i;
                count ++;
                System.out.println("count:" + count);
            }
            if(count > 3){
                System.out.println("TOO MANY '/'");
                break;
            }
        }

        if(count < 2){
            System.out.println("INVALID");
            return null;
        }

        for(int i = 0; i < 3; i ++){
            System.out.println(indexArray[i]);
        }

        // To handle Null Exception Error

        if(!(((indexArray[1] - indexArray[0]) >= 1) && ((indexArray[2] - indexArray[1]) > 1)
                && ((ch.length - indexArray[2]) >= 4))){
            Messages.printDateInputError();
            return null;
        }

        day = getPartOfDate(indexArray[1] -2, indexArray[1], s);
        if(checkForDateError(day, 31)){
            Messages.printDateComponentError("day");
            return null;
        }

        month = getPartOfDate(indexArray[2] -2, indexArray[2], s);
        if(checkForDateError(month, 12)){
            System.out.println(month);
            Messages.printDateComponentError("month");
            return null;
        }

        year = getPartOfDate(indexArray[2] + 1, indexArray[2] + 5, s);
        if(checkForDateError(year, 9999)){
            Messages.printDateComponentError("year");
            return null;
        }

        date = LocalDate.of(year, month, day);
        return date;
    }

    public static int getPartOfDate (int start, int end, String input) {

        System.out.println("GETTING PART OF DATE");
        System.out.println("DEBUG " + input.substring(start, end));
        if(input.substring(start, end).matches(REGEX)){
            return Integer.parseInt(input.substring(start, end));
        }
        else if (input.substring(start + 1, end).matches(REGEX)){
            return Integer.parseInt(input.substring(start + 1, end));
        }
        else {
            return -1;
        }
    }

    public static boolean checkForDateError (int dateComponent, int i) {
        if(dateComponent > i) {
            return true;
        }
        else if (dateComponent == 0) {
            return true;
        }
        else if (dateComponent == -1) {
            return true;
        }
        else {
            return false;
        }
    }
}
