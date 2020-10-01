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

    public static void addTodoTask(String description, TaskList list) throws IOException {
        list.addToList(new Todo(description));
        list.addToFile(new Todo(description));
        Messages.printAddedTask(list.get(list.size() - 1), list.size());
    }

    public static void addEventOrDeadlineTask (String description, String type, TaskList list, LocalDate date) throws IOException {
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

    public static void updateIfTaskIsDone(String input, int index, TaskList list) throws IOException {
        String checkedStrIndex = input.replaceAll("[^0-9]", "");
        int checkedIntIndex = Integer.parseInt(checkedStrIndex);
        if (checkedIntIndex <= index + 1 && checkedIntIndex > 0) {
            list.get(checkedIntIndex - 1).updateIsDone();
        }
        Storage.overWriteFile(list);
        Messages.printTaskMarkedDone(list, checkedIntIndex);
    }

    public static void removeTask(TaskList list, String input, int index) throws IOException {
        String checkedStrIndex = input.replaceAll("[^0-9]", "");
        int checkedIntIndex = Integer.parseInt(checkedStrIndex);
        if (checkedIntIndex <= index + 1 && checkedIntIndex > 0) {
            Messages.printRemovedTask(list, checkedIntIndex);
            list.remove(checkedIntIndex - 1);
        }
        Storage.overWriteFile(list);
    }
}
