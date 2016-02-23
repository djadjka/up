import java.io.FileNotFoundException;

public class Main {
    static Log log = new Log("logfile.txt");

    public static void main(String[] args) {

        try {
            ConsoleUI consoleUI = new ConsoleUI();
            consoleUI.chooseCommand();
        } catch (FileNotFoundException e) {
            System.out.println(e.toString());
            log.addException(e.toString());
        }

    }


}
