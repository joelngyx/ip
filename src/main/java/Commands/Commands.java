package Commands;

import Data.Storage;
import Data.TaskList;
import Tasks.Task;
import Tasks.Deadline;
import Tasks.Event;
import Tasks.Todo;
import Ui.Messages;

import java.io.File;
import java.io.IOException;
import java.util.Scanner;

public class Commands {

    public static void addTask(String description, String type, TaskList list) throws IOException {
        switch (type){
            case ("todo"):
                list.addToList(new Todo(description));
                list.addToFile(new Todo(description));
                break;
            case ("event") :
                list.addToList(new Event(description));
                list.addToFile(new Event(description));
                break;
            case ("deadline") :
                list.addToList(new Deadline(description));
                list.addToFile(new Deadline(description));
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

    public static void findTask(String s, TaskList list) {
        s = s.replace("find", "");
        int count = 1;
        System.out.print(Messages.MARGINS);
        for(int i = 0; i < list.size(); i ++) {
            if(list.get(i).getDescription().contains(s)) {
                System.out.println(
                    count + "." + Messages.printTask(list.get(i))
                );
                count ++;
            }
        }
        if(count == 1) {
            System.out.print("Sorry, no tasks found!\n");
        }
        System.out.print(Messages.MARGINS);
    }
}
