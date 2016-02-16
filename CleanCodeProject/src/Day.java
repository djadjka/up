import java.util.GregorianCalendar;

public class Day {
    private int day;
    private int month;
    private int year;

    public Day(String s) throws NumberFormatException, ArrayIndexOutOfBoundsException {
        String[] parsed = s.split("[.]");
        day = Integer.parseInt(parsed[0]);
        month = Integer.parseInt(parsed[1]);
        year = Integer.parseInt(parsed[2]);
    }

    public long getTimestamp() {
        return new GregorianCalendar(year, month, day).getTimeInMillis();
    }

}
