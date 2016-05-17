package by.bsu.chat;
import java.io.FileNotFoundException;
import java.io.PrintStream;
import java.util.Calendar;
import java.util.GregorianCalendar;

public class Log {
    private Calendar calendar;
    private PrintStream ps;
    private static volatile Log instance = new Log();
    private final String DESIGN_OUTPUT = "%-30s %-10s %-140s%n";
    private final  String FILE_NAME = "logfileStorage.txt";

    private Log() {
        calendar = new GregorianCalendar();
        try {
            ps = new PrintStream(FILE_NAME);
        } catch (FileNotFoundException e) {
            System.out.println("the path for the log was not found");
        }
    }


    public static Log getInstance() {
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
