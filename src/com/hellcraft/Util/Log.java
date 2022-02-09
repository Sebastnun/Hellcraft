package com.hellcraft.Util;

import com.hellcraft.Main;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.time.LocalDate;
import java.time.LocalDateTime;

public class Log {
    private static Log logs;

    private Main instance;

    private File file = new File(this.instance.getDataFolder(), "logs.txt");

    public Log() {
        if (!this.file.exists())
            try {
                this.file.createNewFile();
            } catch (IOException e) {
                e.printStackTrace();
            }
    }

    public void disable(String reason) {
        log("El plugin ha sido apagado: " + reason);
    }

    public void log(String log) {
        LocalDate date = LocalDate.now();
        LocalDateTime time = LocalDateTime.now();
        String msg = String.format("[%02d/%02d/%02d] ", new Object[] { Integer.valueOf(date.getDayOfMonth()), Integer.valueOf(date.getMonthValue()), Integer.valueOf(date.getYear()) }) + String.format("%02d:%02d:%02d ", new Object[] { Integer.valueOf(time.getHour()), Integer.valueOf(time.getMinute()), Integer.valueOf(time.getSecond()) }) + log;
        add(msg);
    }

    private void add(String msg) {
        try {
            BufferedWriter bw = new BufferedWriter(new FileWriter(this.file, true));
            bw.append(msg);
            bw.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static Log getInstance() {
        if (logs == null)
            logs = new Log();
        return logs;
    }
}
