
import Data.Storage;
import Data.TaskList;
import Parser.Parser;
import Ui.Messages;
import Ui.TextUi;
import java.util.Scanner;
import java.io.File;
import java.io.IOException;

public class Duke {

    private Storage storage;
    private TextUi ui;
    private TaskList list;

    public Duke(String filePath) {
        ui = new TextUi();
        storage = new Storage(filePath);
        list = new TaskList();
        try {
            storage.load(list);
        } catch (IOException e) {
            ui.showToUser(e.toString());
        }
    }

    private void start() {
        this.ui = new TextUi();
        ui.showWelcomeMessage();
    }

    private void exit() {
        ui.showGoodbyeMessage();
        System.exit(0);
    }

    public void run() throws IOException {
        start();
        storage.getFile();
        String input;

        do{
            input = ui.getInput();
            Parser.parseCommand(input, list);
        } while (!input.contains("bye"));
        exit();
    }

    public static void main (String[] args) throws IOException {
        new Duke("duke.txt").run();
    }
}