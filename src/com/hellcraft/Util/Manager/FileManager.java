package com.hellcraft.Util.Manager;

import com.hellcraft.Main;
import com.hellcraft.Util.Library.FileAPI;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.entity.Player;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.logging.Level;

public class FileManager {
    private Main main;
    private FileConfiguration dataConfig = null;
    private File configFile = null;


    public FileManager(Main main){
        this.main = main;
        saveDefaultConfig();
    }

    public void reloadConfig(){
        if (this.configFile == null)
            this.configFile = new File(this.main.getDataFolder(),"Lives.yml");

        this.dataConfig = YamlConfiguration.loadConfiguration(this.configFile);

        InputStream defaultStream = this.main.getResource("Lives.yml");
        if(defaultStream != null){
            YamlConfiguration defaultConfig = YamlConfiguration.loadConfiguration(new InputStreamReader(defaultStream));
            this.dataConfig.setDefaults(defaultConfig);
        }
    }

    public FileConfiguration getConfig(){
        if(this.dataConfig==null)
            reloadConfig();
        return this.dataConfig;
    }

    public void saveConfig(){
        if(this.dataConfig==null || this.configFile==null)
            return;

        try {
            this.getConfig().save(this.configFile);
        }catch (IOException i){
            main.getLogger().log(Level.SEVERE, "No se pudo guarda el archivo de configuracion Lives.yml",i);
        }
    }

    public void saveDefaultConfig(){
        if (this.configFile==null)
            this.configFile = new File(this.main.getDataFolder(), "Lives.yml");

        if (!this.configFile.exists())
            this.main.saveResource("Lives.yml",false);
    }






}
