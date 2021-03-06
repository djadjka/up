package by.bsu.chat;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonSyntaxException;

import java.io.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;
import java.util.regex.Matcher;
import java.util.regex.Pattern;


public class InMemoryMessageStorage implements MessageStorage {

    private List<Message> messages;
    private Gson gson;


    public InMemoryMessageStorage() {
        this.messages = new ArrayList<>();
        this.gson = new GsonBuilder().create();
        readMessages(new File(Constants.FILE_NAME));
    }


    public void readMessages(File fin) {
        try (Scanner sc = new Scanner(fin)) {
            int counter = 0;
            StringBuilder sb = new StringBuilder();
            while (sc.hasNextLine()) {
                sb.append(sc.nextLine());
            }
            Pattern p = Pattern.compile(Constants.REGEX);
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


    private void oneMessagePrint(Message message) {
        System.out.println(message.getText());
    }


    public List<Message> getMessageByKeyWord(String keyWord) {
        List<Message> temp = new ArrayList<>();
        for (Message message : messages) {
            if (message.getText().contains(keyWord.trim())) {
                temp.add(message);
            }
        }
        Log.getInstance().addInformation(temp.size() + " Found posts by keyWord : " + keyWord);
        return temp;
    }

    public List<Message> getMessageByRegExKeyWord(String regExKeyWord) {
        Pattern p = Pattern.compile(regExKeyWord.trim());
        List<Message> temp = new ArrayList<Message>();
        for (Message message : messages) {
            Matcher m = p.matcher(message.getText());
            if (m.find()) {
                temp.add(message);
            }
        }
        Log.getInstance().addInformation(temp.size() + " Found posts by RegExKeyWord : " + regExKeyWord);
        return temp;
    }

    public void printMessages(List<Message> m) {
        for (Message message : m) {
            oneMessagePrint(message);
        }
    }

    @Override
    public List<Message> getPortion(Portion portion) {
        int from = portion.getFromIndex();
        if (from < 0) {
            throw new IllegalArgumentException(String.format("Portion from index %d can not be less then 0", from));
        }
        int to = portion.getToIndex();
        if (to != -1 && to < portion.getFromIndex()) {
            throw new IllegalArgumentException(String.format("Porting last index %d can not be less then start index %d", to, from));
        }
        to = Math.max(to, messages.size());
        return messages.subList(from, to);
    }

    @Override
    public void addMessage(Message message) {
        messages.add(message);
        writeMessages(new File(Constants.FILE_NAME));
    }

    @Override
    public boolean updateMessage(Message message) {
        return false;
    }

    @Override
    public boolean removeMessage(String messageId) {
        return false;
    }


    @Override
    public int size() {
        return messages.size();
    }
}
