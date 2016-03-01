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

    public Messages() {
        this.messages = new ArrayList<>();
        this.gson = new GsonBuilder().create();
    }


    public void readMessages(File fin) {
        try (Scanner sc = new Scanner(fin)) {
            int counter = 0;
            final String REGEX = "[{][^{]+[}]";
            StringBuilder sb = new StringBuilder();
            while (sc.hasNextLine()) {
                sb.append(sc.nextLine());
            }
            Pattern p = Pattern.compile(REGEX);
            Matcher m = p.matcher(sb);
            while (m.find()) {
                try {
                    messages.add(gson.fromJson(sb.substring(m.start(), m.end()), Message.class));
                    counter++;
                } catch (JsonSyntaxException e) {
                    Log.getInstance().addException(e.toString());
                }
            }
            Log.getInstance().addInformation(counter + " read Messages");
        } catch (FileNotFoundException e) {
            System.out.println(e.toString());
            Log.getInstance().addException(e.toString());
        }
    }

    public void writeMessages(File fout) {
        try (PrintStream ps = new PrintStream(fout)) {
            ps.print("[");
            int counter = 0;
            for (Message message : messages) {
                ps.print(gson.toJson(message));
                counter++;
                if (counter < messages.size()) {
                    ps.println(",");
                }
            }
            ps.print("]");
            Log.getInstance().addInformation(messages.size() + " write Messages");
        } catch (FileNotFoundException e) {
            System.out.println(e.toString());
            Log.getInstance().addException(e.toString());
        }
    }


    public void delMessage(String id) {
        for (Message message : messages) {
            if (message.getId().compareTo(id) == 0) {
                messages.remove(message);
                break;
            }
        }
        Log.getInstance().addInformation(id + " del  Message by id");
    }

    public void addMessage(String author, String message) {
        final int RECOMENDE_SIZE_MESSAGE = 140;
        messages.add(new Message(author, message));
        if (message.length() > RECOMENDE_SIZE_MESSAGE) {
            Log.getInstance().addWarning("message text is too long");
        }
        Log.getInstance().addInformation(" add Message");
    }

    private void oneMessagePrint(Message message) {
        System.out.println(message.getMessage());
    }

    public List<Message> getMessageHistory() {
        List<Message> copy = new ArrayList<>();
        copy.addAll(messages);
        Collections.sort(copy);
        return copy;
    }

    public List<Message> getMessageByAuthor(String author) {
        List<Message> temp = new ArrayList<>();
        for (Message message : messages) {
            if (message.getAuthor().trim().compareTo(author.trim()) == 0) {
                temp.add(message);
            }
        }
        Log.getInstance().addInformation(temp.size() + " Found posts by author: " + author);
        return temp;
    }

    public List<Message> getMessageByKeyWord(String keyWord) {
        List<Message> temp = new ArrayList<>();
        for (Message message : messages) {
            if (message.getMessage().contains(keyWord.trim())) {
                temp.add(message);
            }
        }
        Log.getInstance().addInformation(temp.size() + " Found posts by keyWord : " + keyWord);
        return temp;
    }

    public List<Message> getMessageByRegExKeyWord(String regExKeyWord) {
        Pattern p = Pattern.compile(regExKeyWord.trim());
        List<Message> temp = new ArrayList<>();
        for (Message message : messages) {
            Matcher m = p.matcher(message.getMessage());
            if (m.find()) {
                temp.add(message);
            }
        }
        Log.getInstance().addInformation(temp.size() + " Found posts by RegExKeyWord : " + regExKeyWord);
        return temp;
    }

    public List<Message> getMessageByPeriod(String start, String end) {
        List<Message> temp = new ArrayList<>();
        try {
            long startCalendarDay = CalendarDay.getTimestamp(start);
            long endCalendarDay = CalendarDay.getTimestamp(end);
            long mesTimestamp;
            for (Message message : messages) {
                mesTimestamp = message.getTimestamp();
                if ((mesTimestamp > startCalendarDay) && (mesTimestamp < endCalendarDay)) {
                    temp.add(message);
                }
            }
        } catch (NumberFormatException | ArrayIndexOutOfBoundsException e) {
            System.out.println("bad data Format");
            Log.getInstance().addException(e.toString() + "bad data Format");
        }
        Log.getInstance().addInformation(temp.size() + " Found posts by time : " + start + " - " + end);
        return temp;
    }

    public void printMessages(List<Message> m) {
        for (Message message : m) {
            oneMessagePrint(message);
        }
    }
}
