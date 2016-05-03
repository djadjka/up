import org.apache.commons.codec.digest.DigestUtils;

import java.io.*;
import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;


public class UserData {
    private static UserData instance;
    public static final String FILE_NAME = "UserData.txt";
    private Map<String, String> userDateMap = new HashMap<String, String>();
    private FileWriter writer;

    private UserData() {
        try {
            Scanner sc = new Scanner(new File(FILE_NAME));
            while (sc.hasNext()) {
                userDateMap.put(sc.next(), sc.next());
            }
        } catch (FileNotFoundException e) {
            System.out.println("the path for the log was not found");
        }
        try {
            writer = new FileWriter(new File(FILE_NAME), true);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static UserData getInstance() {
        if (instance == null) {
            instance = new UserData();
        }
        return instance;
    }

    public boolean compareLoginPass(String login, String pass) {
        try {
            String HexPass = userDateMap.get(login);
            if (HexPass.equals(DigestUtils.md5Hex(pass))) {
                return true;
            }
        } catch (NullPointerException e) {
            return false;
        }
        return false;
    }

    public void addUserData(String login, String pass) {
        try {
            String HexPass = DigestUtils.md5Hex(pass);
            writer.write("\r\n");
            writer.write(login + "  " + HexPass);
            writer.flush();
            userDateMap.put(login, HexPass);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
