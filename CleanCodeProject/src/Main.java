import java.io.FileNotFoundException;

public class Main {

    public static void main(String[] args) {
        try {
            ConsoleUI consoleUI = new ConsoleUI();
            consoleUI.chooseCommand();
        } catch (FileNotFoundException e) {
            System.out.println(e.toString());
            Log.getInstance().addException(e.toString());
        }

    }


}
