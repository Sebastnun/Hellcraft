package com.hellcraft.Util.Manager;

import com.hellcraft.Main;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;

import java.io.File;
import java.io.IOException;

public class FirstRegister {
    private File f = new File(Main.ins.getDataFolder(), "FirstRegister.yml");

    private FileConfiguration cfg;

    private Main main = Main.getInstance();

    public FirstRegister() {
        file();
    }

    public void file() {
        cfg = (FileConfiguration)YamlConfiguration.loadConfiguration(this.f);
        try {
            if (!f.exists()){
                this.f.createNewFile();
            }
        } catch (IOException i) {
            Bukkit.getConsoleSender().sendMessage(ChatColor.RED + "No se cargo el FristRegister.yml");
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


    public boolean isStoneAviable(String uuid) {
        if (this.cfg.contains(uuid))
            return true;
        return false;
    }
}
