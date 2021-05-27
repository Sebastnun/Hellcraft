package sebastnun.hellcraft.Util.Data;

import net.md_5.bungee.api.ChatColor;
import net.md_5.bungee.api.chat.ClickEvent;
import net.minecraft.server.v1_16_R3.IChatBaseComponent;
import net.minecraft.server.v1_16_R3.PacketPlayOutChat;
import org.bukkit.*;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.craftbukkit.v1_16_R3.entity.CraftPlayer;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerQuitEvent;
import sebastnun.hellcraft.Main;
import sebastnun.hellcraft.Util.PremiumUUID;
import net.md_5.bungee.api.chat.TextComponent;


import java.io.File;
import java.io.IOException;

public class PlayerDataManager implements Listener, CommandExecutor {

    private Main main = Main.getInstance();


//Comando /hellTime
    @Override
    public boolean onCommand(CommandSender sender,  Command command, String s, String[] strings) {
        if (!(sender instanceof Player)){
            Bukkit.getConsoleSender().sendMessage(main.format("&cNo eres jugador"));
            return true;
        }else {
            Player p = (((Player) sender).getPlayer());
            p.sendMessage(main.format("&6Tiempo acumulado: "+getTotalTime(p)));
            TextComponent msg = new TextComponent("[Discord]");
            msg.setClickEvent(new ClickEvent(ClickEvent.Action.OPEN_URL,"https://discord.gg/eEGYVBvW"));
            msg.setColor(ChatColor.BLUE);
            p.spigot().sendMessage(msg);
            return true;

        }

    }








    public String getTotalTime(Player player){
        Player p  = player.getPlayer();

        long lS = (player.getStatistic(Statistic.valueOf("PLAY_ONE_MINUTE"))/20L);
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
        String time;
        if (lH<=3){
            time = main.format("&c"+lH+":"+lM+":"+lS);
        }else {
            time = main.format("&a"+lH+":"+lM+":"+lS);
        }
        return time;

    }





    //Creacion del archivo del jugador
    @EventHandler(priority = EventPriority.HIGHEST)
    private void onJoinFile(PlayerJoinEvent e){
        if (new PremiumUUID().isUsernamePremium(e.getPlayer().getName())){
            FileConfiguration config;
            String uuid = PremiumUUID.getUUID(e.getPlayer().getName());
            File file = new File(main.getDataFolder(), uuid+".yml");
            config = (FileConfiguration) YamlConfiguration.loadConfiguration(file);
            if (file.exists())
                return;

            if (!config.contains("info.name"))
                config.set("info.name",e.getPlayer().getName());
            if (!config.contains("info.lives"))
                config.set("info.lives",Integer.valueOf(0));
            if (!config.contains("info.timePlayed"))
                config.set("info.timePlayed",Integer.valueOf(0));
            if (!config.contains("info.isCracked"))
                config.set("info.isCracked",Boolean.valueOf("false"));
            if (!config.contains("info.isBanned"))
                config.set("info.isBanned",Boolean.valueOf("false"));
            if (!config.contains("info.lastLocation")){
                config.set("info.lastLocation.x",0);
                config.set("info.lastLocation.y",0);
                config.set("info.lastLocation.z",0);
                config.set("info.lastLocation.yaw",0);
                config.set("info.lastLocation.pitch",0);
            }


            if (!file.exists()){
                try {
                    config.save(file);
                }catch (IOException er){
                    Bukkit.getConsoleSender().sendMessage(main.format("&c[ERROR] No se cargo el archivo de configuracion del jugador "+e.getPlayer().getName()));
                }
            }

            if (!file.exists());{
                try {
                    file.createNewFile();
                }catch (IOException er){
                    Bukkit.getConsoleSender().sendMessage(main.format("&c[ERROR] No se cargo el archivo de configuracion del jugador "+e.getPlayer().getName()));
                }
            }
        }else {
            FileConfiguration config;
            String uuid = e.getPlayer().getUniqueId().toString();
            File file = new File(main.getDataFolder(), uuid + ".yml");
            config = (FileConfiguration) YamlConfiguration.loadConfiguration(file);

            if (file.exists())
                return;

            if (!config.contains("info.name"))
                config.set("info.name", e.getPlayer().getName());
            if (!config.contains("info.lives"))
                config.set("info.lives", Integer.valueOf(1));
            if (!config.contains("info.timePlayed"))
                config.set("info.timePlayed", Integer.valueOf(0));
                config.set("info.timePlayed.minutos", Integer.valueOf(0));
            if (!config.contains("info.isCracked"))
                config.set("info.isCracked", Boolean.valueOf("true"));
            if (!config.contains("info.isBanned"))
                config.set("info.isBanned",Boolean.valueOf("false"));
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
        }

    }

    @EventHandler(priority = EventPriority.HIGHEST)
    public void PlayerExit(PlayerQuitEvent e){
        if (new PremiumUUID().isUsernamePremium(e.getPlayer().getName())){
            FileConfiguration config;
            String uuid = PremiumUUID.getUUID(e.getPlayer().getName());
            File file = new File(main.getDataFolder(), uuid+".yml");
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


        }else {
            FileConfiguration config;
            String uuid = e.getPlayer().getUniqueId().toString();
            File file = new File(main.getDataFolder(), uuid + ".yml");
            config = (FileConfiguration) YamlConfiguration.loadConfiguration(file);

            if (!file.exists())
                return;

            config.set("info.timePlayed.hora",ticktoHours(e.getPlayer().getStatistic(Statistic.PLAY_ONE_MINUTE)));
            config.set("info.timePlayed.minutos",ticktoMinutes(e.getPlayer().getStatistic(Statistic.PLAY_ONE_MINUTE)));
            Location l = e.getPlayer().getLocation();

            config.set("info.lastLocation.x",l.getX());
            config.set("info.lastLocation.y",l.getY());
            config.set("info.lastLocation.z",l.getZ());
            config.set("info.lastLocation.yaw",l.getYaw());
            config.set("info.lastLocation.pitch",l.getPitch());
        }
    }

    private int ticktoMinutes(int tick){
        return ((tick/20)/60);
    }

    private int ticktoHours(int tick){
        return (((tick/20)/60)/60);
    }

}