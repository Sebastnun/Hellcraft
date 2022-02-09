package com.hellcraft.Util.Manager;

import java.io.File;
import java.io.IOException;
import java.time.LocalDate;
import java.time.format.DateTimeParseException;
import java.time.temporal.ChronoUnit;

import com.hellcraft.Discord.DiscordManager;
import com.hellcraft.Main;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.command.CommandSender;
import org.bukkit.configuration.InvalidConfigurationException;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;

public class DateManager {
    private Main instance;

    private static DateManager dai;

    private File f;

    private FileConfiguration c;

    public String fecha;

    public LocalDate fechaInicio;

    public LocalDate fechaActual;

    public DateManager(Main main) {
        this.instance = main;
        this.fechaActual = LocalDate.now();
        prepareFile();
        this.fecha = this.c.getString("Fecha");
        try {
            this.fechaInicio = LocalDate.parse(this.fecha);
        } catch (DateTimeParseException ex) {
            Bukkit.getConsoleSender().sendMessage(Main.format(Main.tag + "&4&lERROR: &eLa fecha en config.yml estaba mal configurada &7(" + this.c.getString("Fecha") + ")&e."));
            Bukkit.getConsoleSender().sendMessage(Main.format(Main.tag + "&eSe ha establecido el d&b1"));
            this.fechaInicio = LocalDate.parse(getDateForDayOne());
            this.c.set("Fecha", getDateForDayOne());
            saveFile();
            reloadFile();
        }
    }

    public void tick() {
        LocalDate now = LocalDate.now();
        if (this.fechaActual.isBefore(now)) {
            this.fechaActual = now;
            DiscordManager.getInstance().onDayChange();
        }
    }

    public void reloadDate() {
        this.fecha = this.c.getString("Fecha");
        this.fechaInicio = LocalDate.parse(this.fecha);
        this.fechaActual = LocalDate.now();
    }

    public void setDay(CommandSender sender, String args1) {
        int nD;
        try {
            int d = Integer.parseInt(args1);
            if (d > 120 || d < 0) {
                nD = 0;
            } else {
                nD = d;
            }
        } catch (NumberFormatException ex) {
            sender.sendMessage(Main.format("&cNecesitas ingresar un numero Valido"));
            return;
        }
        if (nD == 0) {
            sender.sendMessage(Main.format("&cHas ingresado un nuemero no valido"));
            return;
        }
        LocalDate add = this.fechaActual.minusDays(nD);
        getInstance(instance).setNewDate(String.format(add.getYear() + "-%02d-%02d", new Object[] { Integer.valueOf(add.getMonthValue()), Integer.valueOf(add.getDayOfMonth()) }));
        sender.sendMessage(Main.format("&eSe han actualizado los da: &7" + nD));
        sender.sendMessage(Main.format("&c&lNota importante: &7Algunos cambios pueden requerir un reinicio y la fecha puede no ser exacta."));
        Bukkit.dispatchCommand((CommandSender)Bukkit.getConsoleSender(), "hc reload");
    }

    public long getDays() {
        return this.fechaInicio.until(this.fechaActual, ChronoUnit.DAYS);
    }

    public void setNewDate(String value) {
        this.c.set("Fecha", value);
        saveFile();
        reloadFile();
    }

    public String getDateForDayOne() {
        LocalDate w = this.fechaActual.minusDays(1L);
        return String.format(w.getYear() + "-%02d-%02d", new Object[] { Integer.valueOf(w.getMonthValue()), Integer.valueOf(w.getDayOfMonth()) });
    }

    private void prepareFile() {
        this.f = new File(this.instance.getDataFolder(), "fecha.yml");
        this.c = (FileConfiguration)YamlConfiguration.loadConfiguration(this.f);
        if (!this.f.exists()) {
            this.instance.saveResource("fecha.yml", false);
            this.c.set("Fecha", getDateForDayOne());
            saveFile();
            reloadFile();
        }
        if (this.c.getString("Fecha").isEmpty()) {
            this.c.set("Fecha", getDateForDayOne());
            saveFile();
            reloadFile();
        }
    }

    private void saveFile() {
        try {
            this.c.save(this.f);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void reloadFile() {
        try {
            this.c.load(this.f);
        } catch (IOException e) {
            e.printStackTrace();
        } catch (InvalidConfigurationException e) {
            e.printStackTrace();
        }
    }

    public static DateManager getInstance(Main main) {
        if (dai == null)
            dai = new DateManager(main);
        return dai;
    }
}
