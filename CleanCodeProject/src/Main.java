import java.io.FileNotFoundException;

public class Main {
    public static void main(String[] args) {

        try {
            ConsoleInterface consoleInterface = new ConsoleInterface();
            consoleInterface.chooseCommand();
        } catch (FileNotFoundException e) {
            System.out.println(e.toString());
        }

    }


}
