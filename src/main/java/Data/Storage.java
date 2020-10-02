package Data;

import Tasks.Deadline;
import Tasks.Event;
import Tasks.Task;
import Tasks.Todo;
import Ui.Messages;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Scanner;

/**
 * The Storage class is responsible for reading from and writing
 * onto the file duke.txt
 */
public class Storage {

    protected String filePath;

    public Storage(String filePath){
        this.filePath = filePath;
    }

    /**
     * Creates a new file or opens an existing duke.txt
     *
     * @throws IOException
     */
    public void getFile() throws IOException {
        File duke = new File("duke.txt");
        duke.createNewFile();
    }

    /**
     * Appends Tasks from the User's inputs to the existing
     * text in duke.txt
     *
     * @param input String containing Task information to be added
     *              into the file
     * @throws IOException
     */
    public static void appendToFile(Task input) throws IOException{
        FileWriter fw = new FileWriter("duke.txt" ,true);
        fw.write(Messages.printTo(input));
        fw.close();
    }

    /**
     * Updates the contents of duke.txt by overwriting the entire
     * text file
     *
     * @param list The TaskList being used by the User
     * @throws IOException
     */
    public static void overWriteFile(TaskList list) throws IOException {
        int i = 0;
        FileWriter fw = new FileWriter("duke.txt", false);

        while (i < list.size()){
            fw.write(Messages.printTo(list.get(i)));
            i++;
        }

        fw.close();
    }

    /**
     * Loads the contents of duke.txt into a TaskList
     *
     * @param list The TaskList to be used by the User
     * @throws IOException
     */
    public void load (TaskList list) throws IOException {

        //This function populates the list with saved data on duke.txt
        Scanner sc = new Scanner(new File(filePath));
        int index = 0;

        while (sc.hasNextLine()) {

            String task = sc.nextLine();
            char type = task.charAt(1);
            char status = task.charAt(4);
            String description = task.substring(6);

            scanInEachTask(list, type, description);
            updateTaskStatus(list, status, index);

            index ++;
        }
    }

    /**
     * A void method that aids in reading duke.txt into
     * a TaskList
     *
     * @param list The TaskList to be used by the User
     * @param type A Char that represents each Task type
     * @param description String containing Task description
     * @throws IOException
     */
    public void scanInEachTask (TaskList list, char type, String description) throws IOException {
        switch (type) {
        case ('T'):
            list.addToList(new Todo(description));
            break;
        case ('E'):
            list.addToList(new Event(Parser.Parser.getDescription("event", description),
                    Parser.Parser.getDate(description)));
            break;
        case ('D'):
            list.addToList(new Deadline(Parser.Parser.getDescription("deadline", description),
                    Parser.Parser.getDate(description)));
            break;
        }
    }

    /**
     * Updates Task status based on data in duke.txt
     *
     * @param list TaskList to be used by the User
     * @param status String representing the status of each Task
     * @param index Index of the Task to be updated
     */
    public void updateTaskStatus (TaskList list, char status, int index) {
        switch (status) {
        case ('\u2713'):
            list.get(index).markDone();
            break;
        case ('\u2718'):
            list.get(index).markNotDone();
            break;
        }
    }
}
