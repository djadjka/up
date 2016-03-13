import java.io.FileNotFoundException;
import java.io.PrintStream;
import java.util.Calendar;
import java.util.GregorianCalendar;

public class Log {
    private Calendar calendar;
    private PrintStream ps;
    private static volatile Log instance;
    private final String DESIGN_OUTPUT = "%-30s %-10s %-140s%n";
    final private String FILE_NAME = "logfile.txt";

    private Log() {
        calendar = new GregorianCalendar();
        try {
            ps = new PrintStream(FILE_NAME);
        } catch (FileNotFoundException e) {
            System.out.println("the path for the log was not found");
        }
    }


    public static Log getInstance() {
        Log localInstance = instance;
        if (localInstance == null) {
            synchronized (Log.class) {
                localInstance = instance;
                if (localInstance == null) {
                    instance = localInstance = new Log();
                }
            }
        }
        return localInstance;
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
