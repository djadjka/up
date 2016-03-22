package by.bsu.up.chat.logging.impl;

import by.bsu.up.chat.logging.Logger;

import java.io.FileNotFoundException;
import java.io.PrintStream;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Objects;

public class Log implements Logger {

    private static final String TEMPLATE = "[%s] %s";

    private String tag;
    private PrintStream ps;
    private Log(Class<?> cls, String fileName)  {
        tag = String.format(TEMPLATE, cls.getName(), "%s");
        if (!Objects.equals(fileName, ""))
            try {

                this.ps = new PrintStream(fileName);
            }catch (FileNotFoundException e){
                System.out.println("File not found");
            }
        else
            this.ps = new PrintStream(System.out);
    }

    @Override
    public void info(String message) {
        Date d = new Date();
        SimpleDateFormat format1 = new SimpleDateFormat("dd.MM.yyyy hh:mm:ss");
        ps.println(format1.format(d)+String.format(tag, message));
    }

    @Override
    public void error(String message, Throwable e) {
        System.err.println(String.format(tag, message));
        e.printStackTrace(System.err);
    }


    public static Log create(Class<?> cls, String fileName){
        return new Log(cls, fileName);
    }

    public static Log create(Class<?> cls){
        return new Log(cls, "");
    }
}
