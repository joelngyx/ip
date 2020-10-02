package Ui;

import java.io.InputStream;
import java.io.PrintStream;
import java.util.Scanner;

/**
 * The Text UI of Duke
 */
public class TextUi {

    private final Scanner in;
    private final PrintStream out;

    public TextUi() {
        this(System.in, System.out);
    }

    public TextUi(InputStream in, PrintStream out) {
        this.in = new Scanner(in);
        this.out = out;
    }

    public String getInput() {
        out.print("Enter an input!\n");
        return in.nextLine();
    }

    public void showToUser(String... message) {
        for (String m : message) {
            out.println(Messages.MARGINS + message + Messages.MARGINS);
        }
    }

    public void showWelcomeMessage() {
        out.print(Messages.WELCOME_MESSAGE);
    }

    public void showGoodbyeMessage() {
        out.print(Messages.BYE_MESSAGE);
    }

}
