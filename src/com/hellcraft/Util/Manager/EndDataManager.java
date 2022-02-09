package com.hellcraft.Util.Manager;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;

import com.hellcraft.Main;
import org.bukkit.configuration.InvalidConfigurationException;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;

public class EndDataManager {
    private File endFile;

    private FileConfiguration config;

    private Main instance;

    private ArrayList<Integer> timeList;

    public EndDataManager(Main instance) {
        this.instance = instance;
        this.endFile = new File(instance.getDataFolder(), "endConfig.yml");
        this.config = (FileConfiguration)YamlConfiguration.loadConfiguration(this.endFile);
        if (!this.endFile.exists())
            try {
                this.endFile.createNewFile();
            } catch (IOException e) {
                System.out.println("[ERROR] Ha ocurrido un error al crear el archivo 'endConfig.yml'");
            }
        if (!this.config.contains("EnderCrystalRegenTime")) {
            this.config.set("EnderCrystalRegenTimeINFO", "La siguiente es una lista de nen segundos del tiempo que toma regenerar un End Crystal.");
            this.config.set("EnderCrystalRegenTime", Arrays.asList(new Integer[] { Integer.valueOf(60), Integer.valueOf(90), Integer.valueOf(120), Integer.valueOf(30), Integer.valueOf(100), Integer.valueOf(150) }));
        }
        if (!this.config.contains("PlacedObsidian"))
            this.config.set("PlacedObsidian", new ArrayList());
        if (!this.config.contains("ReplacedObsidian"))
            this.config.set("ReplacedObsidian", Boolean.valueOf(true));
        if (!this.config.contains("CreatedRegenZone"))
            this.config.set("CreatedRegenZone", Boolean.valueOf(false));
        if (!this.config.contains("DecoratedEndSpawn"))
            this.config.set("DecoratedEndSpawn", Boolean.valueOf(true));
        saveFile();
        reloadFile();
        loadSettings();
    }

    public File getEndFile() {
        return this.endFile;
    }

    public FileConfiguration getConfig() {
        return this.config;
    }

    public void loadSettings() {
        this.timeList = (ArrayList<Integer>)this.config.getIntegerList("EnderCrystalRegenTime");
    }

    public ArrayList<Integer> getTimeList() {
        return this.timeList;
    }

    public void saveFile() {
        try {
            this.config.save(this.endFile);
        } catch (IOException e) {
            System.out.println("[ERROR] Ha ocurrido un error al guardar el archivo 'endConfig.yml'");
        }
    }

    public void reloadFile() {
        try {
            this.config.load(this.endFile);
        } catch (IOException e) {
            System.out.println("[ERROR] Ha ocurrido un error al guardar el archivo 'endConfig.yml'");
        } catch (InvalidConfigurationException e) {
            System.out.println("[ERROR] Ha ocurrido un error al guardar el archivo 'endConfig.yml'");
        }
        loadSettings();
    }
}