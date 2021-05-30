package sebastnun.hellcraft.Util.Data;

import net.md_5.bungee.api.ChatColor;
import net.md_5.bungee.api.chat.ClickEvent;
import org.bukkit.*;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerQuitEvent;
import sebastnun.hellcraft.Main;
import sebastnun.hellcraft.Util.Commands.AFK;
import net.md_5.bungee.api.chat.TextComponent;


import java.io.File;
import java.io.IOException;

public class PlayerDataManager implements Listener, CommandExecutor {

    private final Main main = Main.getInstance();


//Comando /hellTime
    @Override
    public boolean onCommand(CommandSender sender,  Command command, String s, String[] strings) {
        if (!(sender instanceof Player)){
            Bukkit.getConsoleSender().sendMessage(main.format("&cNo eres jugador"));
            return true;
        }else {
            Player p = (((Player) sender).getPlayer());
            assert p != null;
            p.sendMessage(Main.format("&6Tiempo acumulado: "+getTotalTime(p)));
            TextComponent msg = new TextComponent("[Discord]");
            msg.setClickEvent(new ClickEvent(ClickEvent.Action.OPEN_URL,"https://discord.gg/eEGYVBvW"));
            msg.setColor(ChatColor.BLUE);
            p.spigot().sendMessage(msg);
            return true;

        }

    }


    public String getTotalTime(Player player){
        Player p  = player.getPlayer();
        AFK afk = new AFK();
        int lS;
        if (afk.contains(p)){
            player.setStatistic(Statistic.PLAY_ONE_MINUTE,afk.getAFK(p));
            lS= (afk.getAFK(p)/20);
        }else {
            lS = (player.getStatistic(Statistic.PLAY_ONE_MINUTE)/20);
        }

        int lH=0;
        int lM=0;

        while (lS>=60){
            lM++;
            lS=lS-60;
            if (lM>=60){
                lH++;
                lM=lM-60;
            }
        }

        String msg;
        if (!(lH>main.getHoraMinima())){
            if (afk.contains(p)){
                msg="&7&l";
            }else {
                msg="&c&l";
            }
        }else {
            if (afk.contains(p)){
                msg="&7";
            }else {
                msg="&a";
            }
        }

        String time;
        if (lS<10){
            if (lM<10){
                time = Main.format(msg+lH+":"+"0"+lM+":"+"0"+lS);
            }else{
                time = Main.format(msg+lH+":"+lM+":"+"0"+lS);
            }
        }else{
            if (lM<10){
                time = Main.format(msg+lH+":"+"0"+lM+":"+lS);
            }else{
                time = Main.format(msg+lH+":"+lM+":"+lS);
            }
        }
        return time;

    }



    public int getHour(Player p){

        if (new AFK().activeAFK.containsKey(p)){
            p.setStatistic(Statistic.PLAY_ONE_MINUTE,new AFK().activeAFK.get(p));
        }

        int lS = (p.getStatistic(Statistic.PLAY_ONE_MINUTE)/20);
        int lH=0;
        int lM=0;

        while (lS>=60){
            if (lS>=60){
                lM++;
                lS=lS-60;
            }
            if (lM>=60){
                lH++;
                lM=lM-60;
            }
        }
        return lH;
    }


    public int getOfflineHour(OfflinePlayer p){
        long lS = (p.getStatistic(Statistic.PLAY_ONE_MINUTE)/20L);
        int lH=0;
        int lM=0;

        while (lS>=60){
            if (lS>=60){
                lM++;
                lS=lS-60;
            }
            if (lM>=60){
                lH++;
                lM=lM-60;
            }
        }
        return lH;
    }




    //Creacion del archivo del jugador
    @EventHandler(priority = EventPriority.HIGH)
    private void onJoinFile(PlayerJoinEvent e){
        FileConfiguration config;
        String uuid = e.getPlayer().getUniqueId().toString();
        File folder = new File(main.getDataFolder() + File.separator + "players");
        File file = new File(folder + File.separator + uuid+".yml");
        config = (FileConfiguration) YamlConfiguration.loadConfiguration(file);

        if (file.exists())
            return;

        if (!config.contains("info.name"))
            config.set("info.name", e.getPlayer().getName());
        if (!config.contains("info.lives"))
            config.set("info.lives", Integer.valueOf(1));
        if (!config.contains("info.timePlayed"))
            config.set("info.timePlayed", Integer.valueOf(0));
        if (!config.contains("info.isBanned"))
            config.set("info.isBanned",Boolean.valueOf("false"));
        if (!config.contains("info.IP"))
            config.set("info.IP",e.getPlayer().getAddress().getHostName());
        if (!config.contains("info.hasRank"))
            config.set("info.hasRank",Boolean.valueOf("false"));
        if (!config.contains("info.lastLocation")){
            config.set("info.lastLocation.x",0.0);
            config.set("info.lastLocation.y",0.0);
            config.set("info.lastLocation.z",0.0);
            config.set("info.lastLocation.yaw",0.0);
            config.set("info.lastLocation.pitch",0.0);
        }

        if (!file.exists()) {
            try {
                config.save(file);
            } catch (IOException er) {
                Bukkit.getConsoleSender().sendMessage(main.format("&c[ERROR] No se cargo el archivo de configuracion del jugador " + e.getPlayer().getName()));
            }
            try {
                file.createNewFile();
            } catch (IOException er) {
                Bukkit.getConsoleSender().sendMessage(main.format("&c[ERROR] No se cargo el archivo de configuracion del jugador " + e.getPlayer().getName()));
            }
        }
        main.reloadLives();

    }


    public void Medalla(){

    }





    @EventHandler(priority = EventPriority.HIGH)
    public void PlayerExit(PlayerQuitEvent e){
        FileConfiguration config;
        String uuid = e.getPlayer().getUniqueId().toString();
        File folder = new File(main.getDataFolder() + File.separator + "players");
        File file = new File(folder + File.separator + uuid+".yml");
        config = (FileConfiguration) YamlConfiguration.loadConfiguration(file);
        if (!file.exists())
            return;

        config.set("info.timePlayed",ticktoHours(e.getPlayer().getStatistic(Statistic.PLAY_ONE_MINUTE)));
        Location l = e.getPlayer().getLocation();

        config.set("info.lastLocation.x",l.getX());
        config.set("info.lastLocation.y",l.getY());
        config.set("info.lastLocation.z",l.getZ());
        config.set("info.lastLocation.yaw",l.getYaw());
        config.set("info.lastLocation.pitch",l.getPitch());

        if (config.contains("info.lastLocation")){
            config.set("info.lastLocation.x",l.getX());
            config.set("info.lastLocation.y",l.getY());
            config.set("info.lastLocation.z",l.getZ());
            config.set("info.lastLocation.yaw",l.getYaw());
            config.set("info.lastLocation.pitch",l.getPitch());
        }
        try {
            config.save(file);
        } catch (IOException er) {
            Bukkit.getConsoleSender().sendMessage(main.format("&c[ERROR] No se cargo el archivo de configuracion del jugador " + e.getPlayer().getName()));
        }
        main.reloadLives();
    }

    private int ticktoMinutes(int tick){
        return ((tick/20)/60);
    }

    private int ticktoHours(int tick){
        return (((tick/20)/60)/60);
    }

}