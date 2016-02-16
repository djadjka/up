import java.io.FileNotFoundException;
import java.io.PrintStream;
import java.util.Calendar;
import java.util.GregorianCalendar;

public class Log {
    private Calendar calendar;
    private PrintStream ps;

    public Log(String path) {
        calendar = new GregorianCalendar();
        try {
            ps = new PrintStream(path);
        } catch (FileNotFoundException e) {
            System.out.println("the path for the log was not found");
        }
    }

    public void add(String view, String information) {
        ps.println(calendar.getTime() + "  " + view + "  " + information);
    }


}
