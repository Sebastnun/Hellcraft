package sebastnun.hellcraft.Util;

import org.bukkit.*;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.entity.Player;
import sebastnun.hellcraft.Main;
import sebastnun.hellcraft.Util.Data.PlayerDataManager;

import java.io.File;
import java.io.IOException;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class EscobaMagica{

    private final Main main = Main.getInstance();

    private final PlayerDataManager pd = new PlayerDataManager();

    public LocalDate fechaActual;

    public List<OfflinePlayer> checkedPlayer = new ArrayList<>();


    public EscobaMagica(){
        this.fechaActual=LocalDate.now();
        Schedule();
    }


    public void Schedule(){
        Bukkit.getScheduler().scheduleSyncRepeatingTask(main, new Runnable() {
            @Override
            public void run() {

                for (Player p : Bukkit.getOnlinePlayers()){
                    CheckTimeOnline(p);
                }

                for (OfflinePlayer p : Bukkit.getOfflinePlayers()){
                    CheckTimeOffline(p);
                }

            }
        },0,60);

        Bukkit.getScheduler().scheduleSyncRepeatingTask(main, new Runnable() {
            @Override
            public void run() {

                for (Player p : Bukkit.getOnlinePlayers()){
                    Bukkit.getScheduler().scheduleSyncDelayedTask(main, new Runnable() {
                        @Override
                        public void run() {
                            CheckLives(p);
                        }
                    },((20)*60));
                }

                for (OfflinePlayer p : Bukkit.getOfflinePlayers()){
                    Bukkit.getScheduler().scheduleSyncDelayedTask(main, new Runnable() {
                        @Override
                        public void run() {
                            CheckLives(p);
                        }
                    },((20)*60));
                }
            }
        },0,((20)*60)*3);
    }

    public void CheckLives(OfflinePlayer p){
        FileConfiguration config;
        String uuid = p.getUniqueId().toString();
        File folder = new File(main.getDataFolder() + File.separator + "players");
        File file = new File(folder + File.separator + uuid+".yml");
        config = (FileConfiguration) YamlConfiguration.loadConfiguration(file);
        if (!file.exists())
            return;

        if (p.isOnline()){
            if (!config.getBoolean("info.hasRank")){
                config.set("info.lives",InicializateLives(p.getPlayer()));
                config.set("info.hasRank",true);
            }
        }
        main.reloadLives(p);
        if (config.getInt("info.lives")<=0){
            config.set("info.isBanned",true);
        }
        if (config.getBoolean("info.isBanned")){
            if(!config.contains("info.IP"))
                return;
            Bukkit.getServer().getBanList(BanList.Type.IP).addBan(config.getString("info.IP"),Main.format("&c&lHas sido baneado"),null,p.getName());
            if (p.isOnline()){
                p.getPlayer().kickPlayer(Main.format("&c&lHas sido baneado"));
            }
        }
        try {
            config.save(file);
        } catch (IOException er) {
            Bukkit.getConsoleSender().sendMessage(main.format("&c[ERROR] No se cargo el archivo de configuracion del jugador " + p.getName()));
        }
        main.reloadLives(p);
    }


    private int InicializateLives(Player p){
        int lives=1;
        if (p.isOp()){
            return 99;
        }
        if (Main.isPlayerInGroup(p,"netherite")){
            return 4;
        }
        if (Main.isPlayerInGroup(p,"twitch")||Main.isPlayerInGroup(p,"diamante")||Main.isPlayerInGroup(p,"oro")){
            return 3;
        }
        if (Main.isPlayerInGroup(p,"hierro")){
            return 2;
        }
        return lives;
    }





    private void CheckTimeOnline(Player p){
        FileConfiguration config;
        String uuid = p.getUniqueId().toString();
        File folder = new File(main.getDataFolder() + File.separator + "players");
        File file = new File(folder + File.separator + uuid+".yml");
        config = (FileConfiguration) YamlConfiguration.loadConfiguration(file);
        java.util.Date fecha = new Date();
        if (fecha.getDay()!=0){
            if (checkedPlayer.contains(p))
                checkedPlayer.remove(p);
            return;
        }
        if (p.hasPermission("hellcraft.admin"))
            return;
        if (!(pd.getOfflineHour(p)>main.getHoraMinima())&&checkedPlayer.contains(p)){
            if (!p.isOnline()){
                main.configuration(p).set("info.lives",(main.configuration(p).getInt("info.lives")-1));
                main.reloadLives(p);
            }else if(!checkedPlayer.contains(p)){
                p.sendMessage(main.format("Haz perdido una vida"));
                main.configuration(p).set("info.lives",(main.configuration(p).getInt("info.lives")-1));
                main.reloadLives(p);
            }
        }
        if (checkedPlayer.contains(p))
            return;
        checkedPlayer.add(p);
        reloadPlay_One_Minute();
        try {
            config.save(file);
        } catch (IOException er) {
            Bukkit.getConsoleSender().sendMessage(main.format("&c[ERROR] No se cargo el archivo de configuracion del jugador " + p.getName()));
        }
    }


    private void CheckTimeOffline(OfflinePlayer p){
        FileConfiguration config;
        String uuid = p.getUniqueId().toString();
        File folder = new File(main.getDataFolder() + File.separator + "players");
        File file = new File(folder + File.separator + uuid+".yml");
        config = (FileConfiguration) YamlConfiguration.loadConfiguration(file);
        java.util.Date fecha = new Date();
        if (fecha.getDay()!=0){
            if (checkedPlayer.contains(p))
                checkedPlayer.remove(p);
            return;
        }
        if (!(pd.getOfflineHour(p)>main.getHoraMinima())&&!checkedPlayer.contains(p)&&file.exists()){
            main.configuration(p).set("info.lives",(main.configuration(p).getInt("info.lives")-1));
            try {
                config.save(file);
            } catch (IOException er) {
                Bukkit.getConsoleSender().sendMessage(main.format("&c[ERROR] No se cargo el archivo de configuracion del jugador " + p.getName()));
            }
            main.reloadLives(p);
        }
        if (checkedPlayer.contains(p))
            return;
        checkedPlayer.add(p);
        reloadPlay_One_Minute();
        try {
            config.save(file);
        } catch (IOException er) {
            Bukkit.getConsoleSender().sendMessage(main.format("&c[ERROR] No se cargo el archivo de configuracion del jugador " + p.getName()));
        }
    }

    public void reloadPlay_One_Minute() {
        LocalDate now = LocalDate.now();
        java.util.Date fecha = new Date();

        if (!Main.getInstance().getConfig().getBoolean("TimeBan"))return;

        if (fecha.getDay()!=0) return;

        if (this.fechaActual.isBefore(now)) {
            this.fechaActual = now;
            for (Player pl : Bukkit.getOnlinePlayers())
                pl.getPlayer().setStatistic(Statistic.PLAY_ONE_MINUTE,0);
            for (OfflinePlayer pl : Bukkit.getOfflinePlayers())
                pl.setStatistic(Statistic.PLAY_ONE_MINUTE,0);

            Bukkit.getConsoleSender().sendMessage(Main.format("&6Se ha reiniciado el tiempo a todos los jugadores"));
        }
    }






}
