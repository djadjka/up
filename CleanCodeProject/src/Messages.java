import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonSyntaxException;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.PrintStream;
import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;


public class Messages {
    private List<Message> messages;
    private Gson gson;
    private Log log;

    public Messages() {
        this.messages = new ArrayList<>();
        this.gson = new GsonBuilder().create();
        log = new Log("logfile.txt");
    }


    public void readMessages(File fin) {
        try {
            int counter = 0;
            Scanner sc = new Scanner(fin);
            StringBuilder sb = new StringBuilder();
            while (sc.hasNextLine()) {
                sb.append(sc.nextLine());
            }
            Pattern p = Pattern.compile("[{][^{]+[}]");
            Matcher m = p.matcher(sb);
            while (m.find()) {
                try {
                    messages.add(gson.fromJson(sb.substring(m.start(), m.end()), Message.class));
                    counter++;
                } catch (JsonSyntaxException e) {
                    log.add("Exception ", e.toString());
                }
            }
            log.add("Information", counter + " read Messages");
            sc.close();
        } catch (FileNotFoundException e) {
            System.out.println("FIle not found");
            log.add("Exception ", e.toString());
        }
    }

    public void writeMessages(File fout) {
        try {
            PrintStream ps = new PrintStream(fout);
            ps.print("[");
            int counter = 0;
            for (Message message : messages) {
                ps.print(gson.toJson(message));
                counter++;
                if (counter < messages.size())
                    ps.print(",");

            }
            ps.print("]");
            ps.close();
            log.add("Information ", messages.size() + " write Messages");
        } catch (FileNotFoundException e) {
            System.out.println("FIle not found");
            log.add("Exception", e.toString());
        }
    }


    public void delMessage(String id) {
        for (Message message : messages) {
            if (message.getId().compareTo(id) == 0) {
                messages.remove(message);
                break;
            }
        }
        log.add("Information ", id + " del  Message by id");
    }

    public void addMessage(String author, String message) {
        messages.add(new Message(author, message));
        if (message.length() > 140)
            log.add("Warning", "message text is too long");
        log.add("Information ", " add Message");
    }

    private void oneMessagePrint(Message message) {
        System.out.println(message.getMessage());
    }

    public List<Message> getMessageHistory() {
        List<Message> copy = new ArrayList<>();
        copy.addAll(messages);
        Collections.sort(copy, new Comparator<Message>() {
            @Override
            public int compare(Message o1, Message o2) {
                return (int) (o1.getTimestamp() - o2.getTimestamp());
            }
        });
        return copy;
    }

    public List<Message> getMessageByAuthor(String author) {
        List<Message> temp = new ArrayList<>();
        for (Message message : messages) {
            if (message.getAuthor().compareTo(author) == 0) {
                temp.add(message);

            }
        }
        log.add("Information ", temp.size() + " Found posts by author: " + author);
        return temp;
    }

    public List<Message> getMessageByKeyWord(String keyWord) {
        List<Message> temp = new ArrayList<>();
        for (Message message : messages) {
            if (message.getMessage().contains(keyWord))
                temp.add(message);
        }
        log.add("Information ", temp.size() + " Found posts by keyWord : " + keyWord);
        return temp;
    }

    public List<Message> getMessageByRegExKeyWord(String regExKeyWord) {
        Pattern p = Pattern.compile(regExKeyWord);
        List<Message> temp = new ArrayList<>();
        for (Message message : messages) {
            Matcher m = p.matcher(message.getMessage());
            if (m.find())
                temp.add(message);
        }
        log.add("Information ", temp.size() + " Found posts by RegExKeyWord : " + regExKeyWord);
        return temp;
    }

    public List<Message> getMessageByPeriod(String start, String end) {
        List<Message> temp = new ArrayList<>();
        try {
            CalendarDay startCalendarDay = new CalendarDay(start);
            CalendarDay endCalendarDay = new CalendarDay(end);
            long mesTimestamp;
            for (Message message : messages) {
                mesTimestamp = message.getTimestamp();
                if ((mesTimestamp > startCalendarDay.getTimestamp()) && (mesTimestamp < endCalendarDay.getTimestamp()))
                    temp.add(message);
            }
        } catch (NumberFormatException | ArrayIndexOutOfBoundsException e) {
            System.out.println("bad data Format");
            log.add("Exception", "bad data Format");
        }
        log.add("Information ", temp.size() + " Found posts by time : " + start + " - " + end);
        return temp;
    }

    public void printMessages(List<Message> m) {
        for (Message message : m) {
            oneMessagePrint(message);
        }
    }
}
