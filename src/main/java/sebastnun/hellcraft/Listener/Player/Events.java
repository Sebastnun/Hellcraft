package sebastnun.hellcraft.Listener.Player;

import net.minecraft.server.v1_16_R3.PacketPlayInClientCommand;
import org.bukkit.*;
import org.bukkit.command.CommandSender;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.craftbukkit.v1_16_R3.entity.CraftPlayer;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.PlayerDeathEvent;
import sebastnun.hellcraft.Main;
import sebastnun.hellcraft.Util.EscobaMagica;

import java.io.File;
import java.io.IOException;

public class Events implements Listener {

    private final Main main = Main.getInstance();


    @EventHandler(priority = EventPriority.HIGHEST)
    public void onDeath(PlayerDeathEvent e){

        new EscobaMagica().CheckLives(e.getEntity().getPlayer());
        String victim = e.getEntity().getPlayer().getName();
        Player p = e.getEntity();
        this.setLives(p);

        int live = Main.Lives.get(e.getEntity());
        if (live >= 2){
            for (Player pl : Bukkit.getOnlinePlayers()){
                String msg = main.format("&c&lEste es el comienzo del sufrimiento eterno de&4&l "+victim+ "&c&l ¡HA PERDIDO UNA VIDA! ¡LE QUEDAN "+live+"!");
                pl.sendMessage(msg);
                String ServerMessageTitle = main.format("&c&l¡Hellcraft!");
                String ServerMessageSubtitle = main.format("!%player% ha muerto!");
                pl.sendTitle(ServerMessageTitle, ServerMessageSubtitle.replace("%player%", victim), 20, 100, 20);
                pl.playSound(pl.getLocation(), Sound.ENTITY_SKELETON_HORSE_DEATH, 10.0F, 1.0F);
            }
        }

        if (live == 1){
            for (Player pl : Bukkit.getOnlinePlayers()){
                String msg = main.format("&c&lEste es el comienzo del sufrimiento eterno de&4&l "+victim+ "&c&l ¡HA PERDIDO UNA VIDA! ¡ESTA USANDO SU ULTIMA VIDA!");
                pl.sendMessage(msg);
                String ServerMessageTitle = main.format("&c&l¡Hellcraft!");
                String ServerMessageSubtitle = main.format("!%player% ha muerto!");
                pl.sendTitle(ServerMessageTitle, ServerMessageSubtitle.replace("%player%", victim), 20, 100, 20);
                pl.playSound(pl.getLocation(), Sound.ENTITY_SKELETON_HORSE_DEATH, 10.0F, 1.0F);
            }
        }

        if (live<=0){
            for (Player pl : Bukkit.getOnlinePlayers()) {
                String msg = main.format("&c&lEste es el comienzo del sufrimiento eterno de&4&l "+victim+ "&c&l. ¡YA NO LE QUEDA MAS VIDAS! ¡Ha SIDO PERMABANEADO!");
                pl.sendMessage(msg);
                String ServerMessageTitle = main.format("&c¡Hellcraft!");
                String ServerMessageSubtitle = main.format("!%player% ha muerto!");
                pl.sendTitle(ServerMessageTitle, ServerMessageSubtitle.replace("%player%", victim), 20, 100, 20);
                pl.playSound(pl.getLocation(), Sound.ENTITY_SKELETON_HORSE_DEATH, 10.0F, 1.0F);
            }
        }
        if (main.world.hasStorm()){
            if (main.world.getWeatherDuration()>=(((20*60)*60)*8)){
                for (Player pl : Bukkit.getOnlinePlayers()){
                    String msg = main.format("&c&lEl Jugador&6&l {p} &c&lya no puede dar mas horas de tormenta").replace("{p}",victim);
                    pl.sendMessage(msg);
                }
            }else {
                main.world.setWeatherDuration(main.world.getWeatherDuration()+((20*60)*60));
            }
        }else {
            Bukkit.dispatchCommand((CommandSender)Bukkit.getConsoleSender(), "weather thunder");
            main.world.setWeatherDuration((20*60)*60);
        }
        main.world.setGameRule(GameRule.DO_WEATHER_CYCLE,true);
        new EscobaMagica().CheckLives(e.getEntity().getPlayer());
        main.reloadLives();
        Bukkit.getScheduler().scheduleSyncDelayedTask(main, new Runnable() {
            @Override
            public void run() {
                Player player = e.getEntity();

                PacketPlayInClientCommand packet = new PacketPlayInClientCommand(PacketPlayInClientCommand.EnumClientCommand.PERFORM_RESPAWN);
                ((CraftPlayer) player).getHandle().playerConnection.a(packet);
            }
        },20);
        Bukkit.getScheduler().scheduleSyncDelayedTask(main, new Runnable() {
            @Override
            public void run() {
                Player player = e.getEntity();
                new EscobaMagica().CheckLives(player);
            }
        },20);

    }




    private void setLives(Player p){
        FileConfiguration config;
        String uuid = p.getUniqueId().toString();
        File folder = new File(main.getDataFolder() + File.separator + "players");
        File file = new File(folder + File.separator + uuid+".yml");
        config = (FileConfiguration) YamlConfiguration.loadConfiguration(file);

        if (!file.exists())
            return;


        if (!config.getBoolean("info.hasRank")){
            config.set("info.lives",InicializateLives(p));
            config.set("info.hasRank",true);
        }

        int locaLives = config.getInt("info.lives");
        config.set("info.lives",(locaLives-1));

        try {
            config.save(file);
        } catch (IOException er) {
            Bukkit.getConsoleSender().sendMessage(main.format("&c[ERROR] No se cargo el archivo de configuracion del jugador " + p.getName()));
        }
        main.reloadLives();
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

}
