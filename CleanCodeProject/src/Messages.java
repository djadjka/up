import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.PrintStream;
import java.util.*;
import java.util.logging.Logger;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Created by djadjka_by on 09.02.2016.
 */
public class Messages {
    private List<Message> messages;

    private Gson gson;

    public Messages() {
        this.messages = new ArrayList<>();

        this.gson = new GsonBuilder().create();
    }


    public void readMessages(File fin) {
        try {
            Scanner sc = new Scanner(fin);
            StringBuilder sb = new StringBuilder();
            while (sc.hasNextLine()) {
                sb.append(sc.nextLine());
            }
            Pattern p = Pattern.compile("[{][^{]+[}]");
            Matcher m = p.matcher(sb);
            while (m.find()) {
                messages.add(gson.fromJson(sb.substring(m.start(), m.end()), Message.class));
            }
        } catch (FileNotFoundException e) {
            System.out.println("FIle not found");

        }
    }

    public void writeMessages(File fout) {
        try {
            PrintStream ps = new PrintStream(fout);
            ps.print("[");
            int flag = 0;
            for (Message message : messages) {
                ps.print(gson.toJson(message));
                flag++;
                if (flag < messages.size())
                    ps.print(",");

            }
            ps.print("]");
        } catch (FileNotFoundException e) {
            System.out.println("FIle not found");
        }
    }


    public void delMessage(String id) {
        for (Message message : messages) {
            if (message.getId().compareTo(id) == 0) {
                messages.remove(message);
                break;
            }
        }
    }

    public void addMessage(String author, String message) {
        messages.add(new Message(author, message));
    }

    private void oneMessagePrint(Message message) {
        System.out.println(message.getMessage());
    }

    public void printMessages() {
        for (Message message : messages) {
            oneMessagePrint(message);
        }
    }

    public void printMessageHistory() {
        List<Message> copy = new ArrayList<>();
        copy.addAll(messages);
        Collections.sort(copy, new Comparator<Message>() {
            @Override
            public int compare(Message o1, Message o2) {
                return (int) (o1.getTimestamp() - o2.getTimestamp());
            }
        });
        for (Message message : copy) {
            oneMessagePrint(message);
        }
    }

    public void printMessageByAuthor(String author) {
        for (Message message : messages) {
            if (message.getAuthor().compareTo(author) == 0)
                oneMessagePrint(message);
        }
    }

    public void printMessageByKeyWord(String KeyWord) {
        for (Message message : messages) {
            if (message.getMessage().indexOf(KeyWord) != -1)
                oneMessagePrint(message);
        }
    }

    public void printMessageByRegExKeyWord(String RegExKeyWord) {
        Pattern p = Pattern.compile(RegExKeyWord);
        for (Message message : messages) {
            Matcher m = p.matcher(message.getMessage());
            if (m.find())
                oneMessagePrint(message);
        }
    }

    public void printMessageByPeriod(String start, String end) {
        try {
            String[] parsedStart = start.split("[.]");
            String[] parsedEnd = end.split("[.]");
            int DAY = 0;
            int Month = 1;
            int YEAR = 2;
            int startDay = Integer.parseInt(parsedStart[DAY]);
            int startMonth = Integer.parseInt(parsedStart[Month]);
            int startYear = Integer.parseInt(parsedStart[YEAR]);
            int endDay = Integer.parseInt(parsedEnd[DAY]);
            int endMonth = Integer.parseInt(parsedEnd[Month]);
            int endYear = Integer.parseInt(parsedEnd[YEAR]);
            long startTimestamp = new GregorianCalendar(startYear, startMonth, startDay).getTimeInMillis();
            long endTimestamp = new GregorianCalendar(startYear, startMonth, startDay).getTimeInMillis();
            if (startTimestamp > endTimestamp) {
                long temp = startTimestamp;
                startTimestamp = endTimestamp;
                endTimestamp = temp;
            }
            for (Message message : messages) {
                if (message.getTimestamp() > startTimestamp && message.getTimestamp() < endTimestamp)
                    oneMessagePrint(message);
            }
        } catch (NumberFormatException e) {
            System.out.println("bad data Format");
        } catch (ArrayIndexOutOfBoundsException e) {
            System.out.println("bad data Format");
        }
    }
}
