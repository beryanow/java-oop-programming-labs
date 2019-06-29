package ru.nsu.g.beryanov.messenger.server.logger;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

public class Logger {
    private static FileWriter fileWriter;
    private static final DateFormat dateFormat = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss ");

    public static void log(String info) {
        Date date = new Date();
        System.out.println(dateFormat.format(date) + info);
        try {
            fileWriter.write(dateFormat.format(date) + info + "\n");
            fileWriter.flush();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static void setLogFile(String fileName) {
        File logFile = new File(fileName);
        try {
            logFile.createNewFile();
            fileWriter = new FileWriter(fileName);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}