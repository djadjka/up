


import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import java.io.File;
import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.util.*;

/**
 * Created by djadjka_by on 09.02.2016.
 */
public class Main {
    public static void main(String[] args) {
        Messages m = new Messages();
        m.readMessages(new File("C:\\Users\\djadjka_by\\Downloads\\log.txt"));
        m.delMessage("46f408b2-72cb-4307-b6e6-95a8515eb7c0");
        m.printMessages();

    }


}
