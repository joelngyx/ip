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

public class Storage {

    protected String filePath;

    public Storage(String filePath){
        this.filePath = filePath;
    }

    public void getFile() throws IOException {
        File duke = new File("duke.txt");
        duke.createNewFile();
    }

    public static void appendToFile(Task input) throws IOException{
        FileWriter fw = new FileWriter("duke.txt" ,true);
        fw.write(Messages.printTo(input));
        fw.close();
    }

    public static void overWriteFile(TaskList list) throws IOException {
        int i = 0;
        FileWriter fw = new FileWriter("duke.txt", false);
        while (i < list.size()){
            fw.write(Messages.printTo(list.get(i)));
            i++;
        }
        fw.close();
    }

    public void load (TaskList list) throws IOException {
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
                    list.addToList(new Todo(description));
                    break;
                case ('E'):
                    list.addToList(new Event(description));
                    break;
                case ('D'):
                    list.addToList(new Deadline(description));
                    break;
            }
            switch (status) {
                case ('\u2713'):
                    list.get(i).updateIsDone();
                    break;
                case ('\u2718'):
                    list.get(i).markNotDone();
                    break;
            }
            i++;
        }
    }
}
