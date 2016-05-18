import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;


public class ConsoleUI {
    private Messages messages;
    private Scanner consoleSc;
    private String textUI;
    private final String ENTER_PATH = "enter the path to the file";
    final String SKIP_R_N = "[\r\n]+";

    public ConsoleUI() throws FileNotFoundException {
        messages = new Messages();
        consoleSc = new Scanner(System.in);
        textUI = readUI();
    }

    private String readUI() throws FileNotFoundException {
        Scanner uiSc = new Scanner(new File("UI.txt"));
        StringBuilder sb = new StringBuilder();
        final int NUMBER_OF_PINTS = 10;
        for (int i = 0; i < NUMBER_OF_PINTS; i++) {
            sb.append(uiSc.nextLine());
            sb.append("\r\n");
        }
        uiSc.close();
        return sb.toString();
    }

    public void printInterface() {
        System.out.println();
        System.out.println(textUI);
    }

    private void printAndMoveRow(String text) {
        System.out.println(text);
        consoleSc.skip(SKIP_R_N);
    }

    public void chooseCommand() {
        while (true) {
            printInterface();
            try {
                switch (Integer.parseInt(consoleSc.next())) {
                    case 1:
                        readMessages();
                        break;
                    case 2:
                        writeMessages();
                        break;
                    case 3:
                        addMessage();
                        break;
                    case 4:
                        printMessageHistory();
                        break;
                    case 5:
                        delMessages();
                        break;
                    case 6:
                        printMessageByAuthor();
                        break;
                    case 7:
                        printMessageByKeyWord();
                        break;
                    case 8:
                        printMessageByRegExKeyWord();
                        break;
                    case 9:
                        printMessageByPeriod();
                        break;
                    case 0:
                        return;
                    default:
                        break;
                }
            } catch (NumberFormatException e) {
                System.out.println(e.toString());
                Log.getInstance().addException(e.toString());
            }
        }
    }

    private void readMessages() {
        printAndMoveRow(ENTER_PATH);
        messages.readMessages(new File(consoleSc.nextLine()));
    }

    private void writeMessages() {

        printAndMoveRow(ENTER_PATH);
        messages.writeMessages(new File(consoleSc.nextLine()));
    }

    private void delMessages() {
        System.out.println("enter ID");
        messages.delMessage(consoleSc.next());
    }

    private void addMessage() {
        System.out.println("enter your user name");
        String userName = consoleSc.next();
        printAndMoveRow("enter your user message");
        String message = consoleSc.nextLine();
        messages.addMessage(userName, message);
    }

    public void printMessageHistory() {
        messages.printMessages(messages.getMessageHistory());
    }

    public void printMessageByAuthor() {
        System.out.println("enter user name");
        messages.printMessages(messages.getMessageByAuthor(consoleSc.next()));
    }

    public void printMessageByPeriod() {
        System.out.println("enter start period (dd.mm.yyyy)");
        String start = consoleSc.next();
        System.out.println("enter end period (dd.mm.yyyy)");
        String end = consoleSc.next();
        messages.printMessages(messages.getMessageByPeriod(start, end));
    }

    public void printMessageByRegExKeyWord() {
        printAndMoveRow("enter regex");
        messages.printMessages(messages.getMessageByRegExKeyWord(consoleSc.nextLine()));
    }

    public void printMessageByKeyWord() {
        printAndMoveRow("enter key word");
        messages.printMessages(messages.getMessageByKeyWord(consoleSc.nextLine()));
    }

}
