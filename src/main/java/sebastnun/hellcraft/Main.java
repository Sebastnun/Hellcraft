package sebastnun.hellcraft;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;
import org.bukkit.event.Listener;
import org.bukkit.plugin.java.JavaPlugin;
import sebastnun.hellcraft.Util.Data.PlayerDataManager;
import sebastnun.hellcraft.Util.Manager.TablistManager;

import java.util.ArrayList;

public final class Main extends JavaPlugin implements Listener {

    private static Main instance;

    public ArrayList<Integer> H = new ArrayList<>();

    public ArrayList<Integer> M = new ArrayList<>();

    public ArrayList<Player> names = new ArrayList<>();

    private TablistManager tab;

    @Override
    public void onEnable() {
        instance=this;
        Bukkit.getConsoleSender().sendMessage(format("&6&lHellcraft"));
        registerEvents();
        registerCommands();
        setupTabList();
    }

    private void registerCommands(){
        this.getCommand("helltime").setExecutor(new PlayerDataManager());
    }

    private void registerEvents(){
        Bukkit.getServer().getPluginManager().registerEvents(this,this);
        Bukkit.getServer().getPluginManager().registerEvents(new PlayerDataManager(),this);
    }
    private void setupTabList(){
        this.tab = new TablistManager();
        tab.addHeaders("&c&lHellcraft");
        tab.addHeaders("&c&l&oHellcraft");

        tab.showTab();
    }


    @Override
    public void onDisable() {
        Bukkit.getConsoleSender().sendMessage(format("&6&lHellcraft out"));
    }


    public static String format(String texto){
        return ChatColor.translateAlternateColorCodes('&',texto);
    }





    public static Main getInstance(){return instance;}
}
