package by.bsu.up.chat.logging;


import java.io.FileNotFoundException;
import java.io.PrintStream;
import java.util.Calendar;
import java.util.GregorianCalendar;

public class LogStorage {
    private Calendar calendar;
    private PrintStream ps;
    private static volatile LogStorage instance = new LogStorage();
    private final String DESIGN_OUTPUT = "%-30s %-10s %-140s%n";
    private final  String FILE_NAME = "logfileStorage.txt";

    private LogStorage() {
        calendar = new GregorianCalendar();
        try {
            ps = new PrintStream(FILE_NAME);
        } catch (FileNotFoundException e) {
            System.out.println("the path for the log was not found");
        }
    }


    public static LogStorage getInstance() {
        return instance;
    }

    public void addException(String information) {
        ps.printf(DESIGN_OUTPUT, calendar.getTime().toString(), "Exception", information);
    }

    public void addInformation(String information) {
        ps.printf(DESIGN_OUTPUT, calendar.getTime().toString(), "Information", information);
    }

    public void addWarning(String information) {
        ps.printf(DESIGN_OUTPUT, calendar.getTime().toString(), "Warning", information);
    }


}
