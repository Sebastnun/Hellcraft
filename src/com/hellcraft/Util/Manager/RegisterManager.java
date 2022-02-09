package com.hellcraft.Util.Manager;

import com.hellcraft.Main;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;

import java.io.File;
import java.io.IOException;

public class RegisterManager {
    private File f = new File(Main.ins.getDataFolder(), "Register.yml");

    private FileConfiguration cfg = (FileConfiguration)YamlConfiguration.loadConfiguration(this.f);

    private Main main;

    public RegisterManager(Main main) {
        this.main = main;
        file();
    }

    public void file() {
        try {
            if (!f.exists()){
                this.f.createNewFile();
            }
        } catch (IOException i) {
            Bukkit.getConsoleSender().sendMessage(ChatColor.RED + "No se cargo el Register.yml");
        }
    }


    public FileConfiguration getCfg(){
        file();
        return cfg;
    }

    public void safeCfg() {
        try {
            this.cfg.save(this.f);
        } catch (IOException iOException) {}
    }


    public boolean isKitsAviable(String uuid) {
        if (this.cfg.contains(uuid))
            return true;
        return false;
    }
}
