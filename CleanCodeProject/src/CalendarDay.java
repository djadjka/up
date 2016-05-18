import java.util.GregorianCalendar;

public class CalendarDay {


    public static long getTimestamp(String s) throws NumberFormatException, ArrayIndexOutOfBoundsException {
        String[] parsed = s.split("[.]");
        int day = Integer.parseInt(parsed[0]);
        int month = Integer.parseInt(parsed[1]);
        int year = Integer.parseInt(parsed[2]);
        return new GregorianCalendar(year, month, day).getTimeInMillis();
    }
}
