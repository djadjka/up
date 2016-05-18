import by.bsu.chat.Log;
import org.apache.commons.codec.digest.DigestUtils;

import java.io.*;
import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;


public class UserData {
    private static UserData instance;
    public static final String FILE_NAME = "d:\\UserData.txt";
    private Map<String, String> userDateMap = new HashMap<String, String>();
    private FileWriter writer;

    private UserData() {
        try {
            File f = new File(FILE_NAME);
            Scanner sc = new Scanner(f);
            while (sc.hasNext()) {
                userDateMap.put(sc.next(), sc.next());
            }
        } catch (FileNotFoundException e) {
            Log.getInstance().addException("tho path with user data not found");
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
            String HexPass = userDateMap.get(login.trim());
            if (HexPass.equals(DigestUtils.md5Hex(pass.trim()))) {
                return true;
            }
        } catch (NullPointerException e) {
            return false;
        }
        return false;
    }

    public void addUserData(String login, String pass) {
        try {
            String hexPass = DigestUtils.md5Hex(pass.trim());
            writer.write("\r\n");
            writer.write(login.trim() + "  " + hexPass);
            writer.flush();
            userDateMap.put(login.trim(), hexPass);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public boolean checkLogin(String login) {
        for (String item : userDateMap.keySet()) {
            if (login.equals(item)) {
                return true;
            }
        }
        return false;
    }
}
