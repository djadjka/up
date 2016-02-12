

import java.io.File;
import java.util.InputMismatchException;
import java.util.Scanner;

/**
 * Created by djadjka_by on 12.02.2016.
 */
public class ConsoleInterface {
    private Messages messages;
    private Scanner sc;

    public ConsoleInterface() {
        messages = new Messages();
        sc = new Scanner(System.in);
    }

    public void printInterface() {
        System.out.println("1)read from a file");
        System.out.println("2)write from a file");
        System.out.println("3)add message");
        System.out.println("4)view the history of messages in chronological order");
        System.out.println("5)delete the message by ID");
        System.out.println("6)search in the history of posts by author");
        System.out.println("7)search in the history of posts by keyword");
        System.out.println("8)search in the history of posts by a regular expression");
        System.out.println("9)view message history for a certain period");
        System.out.println("0)exit");
    }

    public void chooseComand() {


        while (true) {
            printInterface();
            try {
                switch (Integer.parseInt(sc.next())) {
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
            }catch (NumberFormatException e){
                System.out.println("error");
            }
        }
    }
    private void readMessages() {
        System.out.println("enter the path to the file");
        sc.skip("[\r\n]+");
        messages.readMessages(new File(sc.nextLine()));
    }

    private void writeMessages() {
        System.out.println("enter the path to the file");
        sc.skip("[\r\n]+");
        messages.writeMessages(new File(sc.nextLine()));
    }

    private void delMessages() {
        System.out.println("enter ID");
        messages.delMessage(sc.next());
    }

    private void addMessage() {
        System.out.println("enter your user name");
        String userName = sc.next();
        System.out.println("enter your user message");
        sc.skip("[\r\n]+");
        String message = sc.nextLine();
        messages.addMessage(userName, message);
    }

    public void printMessageHistory() {
        messages.printMessageHistory();
    }

    public void printMessageByAuthor() {
        System.out.println("enter user name");
        messages.printMessageByAuthor(sc.next());
    }

    public void printMessageByPeriod() {
        System.out.println("enter start period (dd.mm.yy)");
        String start = sc.next();
        System.out.println("enter end period (dd.mm.yy)");
        String end = sc.next();
        messages.printMessageByPeriod(start, end);
    }

    public void printMessageByRegExKeyWord() {
        System.out.println("enter regex");
        sc.skip("[\r\n]+");
        messages.printMessageByRegExKeyWord(sc.nextLine());
    }

    public void printMessageByKeyWord() {
        System.out.println("enter key word");
        sc.skip("[\r\n]+");
        messages.printMessageByKeyWord(sc.nextLine());
    }

}
