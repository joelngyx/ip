package Commands;

import Data.Storage;
import Data.TaskList;
import Tasks.Deadline;
import Tasks.Event;
import Tasks.Todo;
import Ui.Messages;


import java.io.IOException;
import java.time.LocalDate;


public class Commands {

    /**
     * Adds new Todo Task to a TaskList
     *
     * @param description String containing the Task description
     * @param list The TaskList being used by the User
     * @throws IOException
     */
    public static void addTodoTask(String description, TaskList list) throws IOException {
        list.addToList(new Todo(description));
        list.addToFile(new Todo(description));
        Messages.printAddedTask(list.get(list.size() - 1), list.size());
    }

    /**
     * Adds new Event or Deadline Task to a TaskList
     * @param description String containing the Task description
     * @param type String containing the Type of Task
     * @param list The askList being used by the User
     * @param date LocalDate containing the Date included in the Task
     * @throws IOException
     */
    public static void addEventOrDeadlineTask (String description,
            String type, TaskList list, LocalDate date) throws IOException {
        switch(type) {
        case ("event"):
            list.addToList(new Event(description, date));
            list.addToFile(new Event(description, date));
            break;
        case ("deadline"):
            list.addToList(new Deadline(description, date));
            list.addToFile(new Deadline(description, date));
            break;
        }
        Messages.printAddedTask(list.get(list.size() - 1), list.size());
    }

    /**
     * Updates the Task Status when the User evokes the DONE Command
     *
     * @param input String containing the User's input
     * @param sizeOfList An integer containing how large the TaskList is at that instance
     * @param list The TaskList being used by the User
     * @throws IOException
     */
    public static void updateTaskStatus (String input, int sizeOfList, TaskList list) throws IOException {
        String providedIndexInStr = input.replaceAll("[^0-9]", "");
        int providedIndexInInt = Integer.parseInt(providedIndexInStr);
        boolean isIndexValid = ((providedIndexInInt < sizeOfList) || (providedIndexInInt > 0));

        if (isIndexValid) {
            list.get(providedIndexInInt - 1).markDone();
            Storage.overWriteFile(list);
            Messages.printTaskMarkedDone(list, providedIndexInInt);
        }
    }

    /**
     * Deletes Task indicated by the User
     *
     * @param list The TaskList being used by the User
     * @param input String containing the User's input
     * @param index Integer index of the Task that is to be deleted
     * @throws IOException
     */
    public static void deleteTask (TaskList list, String input, int index) throws IOException {
        String checkedStrIndex = input.replaceAll("[^0-9]", "");
        int checkedIntIndex = Integer.parseInt(checkedStrIndex);
        if (checkedIntIndex <= index + 1 && checkedIntIndex > 0) {
            Messages.printRemovedTask(list, checkedIntIndex);
            list.remove(checkedIntIndex - 1);
        }
        Storage.overWriteFile(list);
    }

    /**
     * Prints out Tasks that contain the description provided by the User
     *
     * @param s String containing the User's input
     * @param list The TaskList being used by the User
     */
    public static void findTask(String s, TaskList list) {
        s = s.replace("find", "");
        int count = 1;

        System.out.print(Messages.MARGINS);

        for(int i = 0; i < list.size(); i ++) {
            boolean isTaskFound = list.get(i).getDescription().contains(s);
            if(isTaskFound) {
                System.out.println(count + "." +
                        Messages.printTask(list.get(i))
                );
                count ++;
            }
        }

        if(count == 1) {
            Messages.printNoTasksFound();
        }

        System.out.print(Messages.MARGINS);
    }
}
