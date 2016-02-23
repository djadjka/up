import java.io.FileNotFoundException;
import java.io.PrintStream;
import java.util.Calendar;
import java.util.GregorianCalendar;

public class Log {
    private Calendar calendar;
    private PrintStream ps;
    private final String DESIGN_OUTPUT = "%-30s %-10s %-140s%n";

    public Log(String path) {
        calendar = new GregorianCalendar();
        try {
            ps = new PrintStream(path);
        } catch (FileNotFoundException e) {
            System.out.println("the path for the log was not found");
        }
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
