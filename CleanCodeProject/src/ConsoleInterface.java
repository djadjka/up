import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;


public class ConsoleInterface {
    private Messages messages;
    private Scanner consoleSc;
    private Scanner interfaceSc;
    private String interfaceText;

    public ConsoleInterface() throws FileNotFoundException {
        messages = new Messages();
        consoleSc = new Scanner(System.in);
        interfaceSc = new Scanner(new File("interfaceText.txt"));
        interfaceText = readInterfaceText();
    }

    private String readInterfaceText() {
        StringBuilder sb = new StringBuilder();
        final int NUMBER_OF_PINTS = 10;
        for (int i = 0; i < NUMBER_OF_PINTS; i++) {
            sb.append(interfaceSc.nextLine());
            sb.append("\r\n");
        }
        return sb.toString();
    }

    public void printInterface() {

        System.out.println(interfaceText);
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
            }
        }
    }

    private void readMessages() {
        System.out.println("enter the path to the file");
        consoleSc.skip("[\r\n]+");
        messages.readMessages(new File(consoleSc.nextLine()));
    }

    private void writeMessages() {
        System.out.println("enter the path to the file");
        consoleSc.skip("[\r\n]+");
        messages.writeMessages(new File(consoleSc.nextLine()));
    }

    private void delMessages() {
        System.out.println("enter ID");
        messages.delMessage(consoleSc.next());
    }

    private void addMessage() {
        System.out.println("enter your user name");
        String userName = consoleSc.next();
        System.out.println("enter your user message");
        consoleSc.skip("[\r\n]+");
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
        System.out.println("enter start period (dd.mm.yy)");
        String start = consoleSc.next();
        System.out.println("enter end period (dd.mm.yy)");
        String end = consoleSc.next();
        messages.printMessages(messages.getMessageByPeriod(start, end));
    }

    public void printMessageByRegExKeyWord() {
        System.out.println("enter regex");
        consoleSc.skip("[\r\n]+");
        messages.printMessages(messages.getMessageByRegExKeyWord(consoleSc.nextLine()));
    }

    public void printMessageByKeyWord() {
        System.out.println("enter key word");
        consoleSc.skip("[\r\n]+");
        messages.printMessages(messages.getMessageByKeyWord(consoleSc.nextLine()));
    }

}
