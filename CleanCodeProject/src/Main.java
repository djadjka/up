import java.io.FileNotFoundException;

public class Main {
    public static void main(String[] args) {

        try {
            ConsoleInterface ci = null;
            ci = new ConsoleInterface();
            ci.chooseComand();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }

    }


}
