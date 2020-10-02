package Parser;

import Commands.Commands;
import Data.TaskList;
import Ui.Messages;
import java.io.IOException;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

/**
 * The Parser consists of multiple methods that aid in the selection
 * of commands to execute based on the user's inputs
 */
public class Parser {

    public Parser(){
    }

    public static String REGEX = "\\d+";
    public static String DONE = "done";
    public static String EVENT = "event";
    public static String DEADLINE = "deadline";
    public static String TODO = "todo";
    public static String ERROR = "Error";

    /**
     * Parses User input into a command
     *
     * @param input String containing the User's inputs
     * @param list TaskList being used by the User
     * @throws IOException
     */
    public static void parseCommand(String input, TaskList list) throws IOException {
        input = input.toLowerCase();

        if(input.equals("list")){
            showList(list);
        }
        else if(input.contains(DONE)){
            prepareDoneCommand(list, input);
        }
        else if(input.contains("delete")){
            prepareDeleteCommand(input, list);
        }
        else if (input.contains(TODO)) {
            prepareToAddTodo(input, list);
        }
        else if (input.contains(EVENT) || input.contains(DEADLINE)){
            prepareToAddDeadlineOrEvent(input, list);
        }
        else if (input.contains("find")) {
            Commands.findTask(input, list);
        }
        else if (input.contains("bye")) {
            Messages.printPreparingToExit();
        }
        else {
            Messages.printErrorInvalidInput();
        }
    }

    /**
     * Extracts the index from the User's input. Evoked when
     * the command specifically targets a particular task, such
     * the DELETE and DONE commands
     *
     * @param input String containing the User's input
     * @param type String containing the Task type
     * @return Integer representing the Task's index
     */
    public static int getIndex(String input, String type) {
        String description = input.replace(type, "").replace(" ", "");
        boolean hasDigits = description.replaceAll(REGEX, "").isEmpty();

        if(hasDigits) {
            return Integer.parseInt(description);
        } else {
            return (-1);
        }
    }

    /**
     * Shows all the Tasks in the User's TaskList
     *
     * @param list The TaskList being used by the User
     */
    public static void showList (TaskList list) {
        System.out.println(list.contentsOfTaskList());
    }

    /**
     * Checks for erros before proceeding with the DONE command
     *
     * @param list The TaskList being used by the User
     * @param input String containing the User's input
     * @throws IOException
     */
    public static void prepareDoneCommand (TaskList list, String input)
            throws IOException {
        int index = getIndex(input, DONE);
        boolean isIndexValid = (index > 0 && index < list.size() + 1);

        if(isIndexValid) {
            Commands.updateTaskStatus(input, index, list);
        }
        else {
            Messages.printErrorInvalidInput();
        }
    }

    /**
     * Checks for error before proceeding with the DONE command
     *
     * @param list The TaskList being used by the User
     * @param input String containing the User's input
     * @throws IOException
     */
    public static void prepareDeleteCommand(String input, TaskList list) throws IOException {
        int index = getIndex(input, "delete");
        boolean isIndexValid = (index > 0 && index < list.size() + 1);

        if(isIndexValid) {
            Commands.deleteTask(list, input, index);
        }
        else {
            Messages.printErrorInvalidInput();
        }
    }

    /**
     * Checks for errors before evoking the addTodoTask method in the Commands class
     *
     * @param input String containing the User's input
     * @param list The TaskList being used by the User
     * @throws IOException
     */
    public static void prepareToAddTodo(String input, TaskList list) throws IOException {
        String taskType = getTaskType(input);
        boolean hasNoErrors = (!taskType.equals(ERROR));

        if(hasNoErrors) {
            String description = getDescription(taskType, input);
            Commands.addTodoTask(description, list);
        }
    }

    /**
     * Checks for errors before evoking the adEventOrDeadlineTask method in the
     * Commands class
     *
     * @param input String containing the User's input
     * @param list The TaskList being used by the User
     * @throws IOException
     */
    public static void prepareToAddDeadlineOrEvent(String input, TaskList list) throws IOException {
        String taskType = getTaskType(input);
        LocalDate date;
        boolean hasNoErrors = (!taskType.equals(ERROR));

        if(hasNoErrors) {
            date = getDate(input);
            boolean isAValidDate = (date != null);

            if(isAValidDate) {
                String description = getDescription(getTaskType(input), input);
                Commands.addEventOrDeadlineTask(description, taskType, list, date);
            }
            else {
                Messages.printDateInputError();
            }
        }
    }

    /**
     * Extracts the Task description
     *
     * @param type String containing the type of Task
     * @param input String containing the User's input
     * @return String containing the Task Description
     */
    public static String getDescription(String type, String input) {
        String inputWithoutType = input.replace(type, "");

        if(type.equals(DEADLINE) || type.equals(EVENT)){
            int positionOfFirstSlash = getFirstPos(inputWithoutType);
            int positionOfSecondSlash = inputWithoutType.indexOf("/",
                    positionOfFirstSlash + 1);
            int positionOfThirdSlash = inputWithoutType.indexOf("/",
                    positionOfSecondSlash + 1);

            String ch1 = String.valueOf(inputWithoutType.charAt(positionOfSecondSlash - 2));
            String ch2 = String.valueOf(inputWithoutType.charAt(positionOfSecondSlash - 1));
            if(ch1.matches(REGEX) && ch2.matches(REGEX)){
                return getDescriptionWithTime(inputWithoutType, positionOfSecondSlash,
                    positionOfThirdSlash, 2);
            }
            else if(ch2.matches(REGEX)){
                return getDescriptionWithTime(inputWithoutType, positionOfSecondSlash,
                    positionOfThirdSlash, 1);
            }
        }

        int index = input.lastIndexOf(type);
        return input.substring(index).replace(type, "");
    }

    /**
     * Extracts the position of the first instance of either '/' or '('. Used
     * for reading User inputs and data from duke.txt
     *
     * @param inputWithoutType String containing the User's input, but with the
     *                         Task type removed
     * @return An integer representing the first instance of the characters
     * '/' and '('
     */
    public static int getFirstPos(String inputWithoutType) {
        if(inputWithoutType.contains("(")){
            return inputWithoutType.indexOf("(");
        }
        else{
            return inputWithoutType.indexOf("/");
        }
    }

    /**
     * Extracts Task Descriptions with their dates included
     *
     * @param inputWithoutType String of User's input with the Task typre
     *                         removed
     * @param positionOfSecondSlash Integer representing the position of the
     *                              second instance of '/'
     * @param positionOfThirdSlash Integer representing the position of the
     *                             third instance of '/'
     * @param x Integer that helps with extracting the day component of the
     *          date
     * @return A string that contains both the description and the date of
     * the Task
     */
    public static String getDescriptionWithTime(String inputWithoutType,
            int positionOfSecondSlash, int positionOfThirdSlash, int x) {
        LocalDate date = getDate(inputWithoutType);
        String dateToString = date.format(DateTimeFormatter.ofPattern("dd/MM/yyyy"));

        String description_a = inputWithoutType.substring(0, positionOfSecondSlash - x)
                .replace("/", "(");
        String description_b = inputWithoutType.substring(positionOfThirdSlash + 5);

        if(inputWithoutType.contains(")")) {
            return (description_a + dateToString + " " + description_b);
        }
        else {
            if(description_b.replace(" ", "").isEmpty()) {
                return (description_a + dateToString + ")");
            }
            else {
                return (description_a + dateToString + " " + description_b + ")");
            }
        }
    }

    /**
     * Extracts the type of Task- it could be TODO, EVENT, DEADLINE
     *
     * @param s String containing the User's input
     * @return A string containing this potential Task's type
     */
    public static String getTaskType(String s){

        boolean hasTodo = s.contains(TODO);
        boolean hasEvent = s.contains(EVENT);
        boolean hasDeadline = s.contains(DEADLINE);
        boolean hasSlash = s.contains("/");

        if(hasTodo) {
            if(s.replace(TODO, "").equals("")) {
                Messages.printErrorEmptyInput(TODO);
                return ERROR;
            }
            else {
                return TODO;
            }
        }
        else if(hasDeadline) {
            if(!hasSlash) {
                Messages.printDateInputError();
                return ERROR;
            }
            else if(s.replace(DEADLINE, "").equals("")) {
                Messages.printErrorEmptyInput(DEADLINE);
                return ERROR;
            }
            else if(s.replace(DEADLINE, "").replace(
                    " ", "").replace(
                            "/", "").equals("")) {
                Messages.printErrorEmptyInput(DEADLINE);
                return ERROR;
            }
            else {
                return DEADLINE;
            }
        }
        else if(hasEvent) {
            if(!hasSlash) {
                Messages.printDateInputError();
                return ERROR;
            }
            else if(s.replace(EVENT, "").equals("")) {
                Messages.printErrorEmptyInput(EVENT);
                return ERROR;
            }
            else if(s.replace(EVENT, "").replace(
                    " ", "").replace(
                    "/", "").equals("")) {
                Messages.printErrorEmptyInput(EVENT);
                return ERROR;
            }
            else {
                return EVENT;
            }
        }

        return ERROR;
    }

    /**
     * Extracts the date mentioned in the User's inputs when the Task
     * types DEADLINE and EVENT are intended to be added to the TaskList
     *
     * @param s String containing the User's input
     * @return A LocalDate, containing the date mentioned in the User's
     * input
     */
    public static LocalDate getDate(String s) {

        int day;
        int month;
        int year;

        LocalDate date;

        int[] indexArray;
        indexArray = indexOfDividers(s);

        if(indexOfDividers(s) == null){
            return null;
        }

        if(!(((indexArray[1] - indexArray[0]) >= 1) && ((indexArray[2] - indexArray[1]) > 1)
                && ((s.length() - indexArray[2]) >= 4))){
            return null;
        }

        day = getPartOfDate(indexArray[1] - 2, indexArray[1], s);
        if(hasDateError(day, 31)){
            Messages.printDateComponentError("day");
            return null;
        }

        month = getPartOfDate(indexArray[2] -2, indexArray[2], s);
        if(hasDateError(month, 12)){
            Messages.printDateComponentError("month");
            return null;
        }

        year = getPartOfDate(indexArray[2] + 1, indexArray[2] + 5, s);
        if(hasDateError(year, 9999)){
            Messages.printDateComponentError("year");
            return null;
        }

        date = LocalDate.of(year, month, day);
        return date;
    }

    /**
     * Helps with extracting the date in the User's input by extracting
     * out the day, month and year components of the date
     *
     * @param start Integer containing the start index
     * @param end Integer containing the end index
     * @param input String containing the User's input
     * @return An integer containing either the day, month or year of the
     * date in the User's input
     */
    public static int getPartOfDate(int start, int end, String input) {

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

    /**
     * Checks if there is an error in how the User inputted the date
     *
     * @param dateComponent Integer which could contain the day, month
     *                      or year of the date
     * @param i Integer containing the value that we use to determine
     *          if the day, month or year are valid
     * @return A boolean value which indicates if there are errors or
     * not
     */
    public static boolean hasDateError(int dateComponent, int i) {
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

    /**
     * Helps with the extracting of dates in User inputs by
     * recording the positions of the '/' character. Also used
     * to see if there are sufficient '/' characters in the input
     *
     * @param s String containing the User's input
     * @return An integer array of positions of '/' characters
     */
    public static int[] indexOfDividers(String s) {
        String temp = s.replace(" ", "");
        temp += " ";
        char[] ch = s.toCharArray();

        int count = 0;
        int[] indexArray = new int[3];

        for(int i = 0; i < s.length(); i ++){
            if(ch[i] == '/' || ch[i] == '('){
                indexArray[count] = i;
                count ++;
            }
            if(count > 3){
                break;
            }
        }
        if(count < 2){
            return null;
        }

        return indexArray;
    }
}
