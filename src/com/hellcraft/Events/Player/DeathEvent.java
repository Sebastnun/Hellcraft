package com.hellcraft.Events.Player;

import com.hellcraft.Discord.DiscordManager;
import com.hellcraft.Main;
import org.bukkit.*;
import org.bukkit.block.Block;
import org.bukkit.block.BlockFace;
import org.bukkit.block.BlockState;
import org.bukkit.block.Skull;
import org.bukkit.block.data.BlockData;
import org.bukkit.block.data.Directional;
import org.bukkit.block.data.Rotatable;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Blaze;
import org.bukkit.entity.Item;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDamageEvent;
import org.bukkit.event.entity.EntityDeathEvent;
import org.bukkit.event.entity.PlayerDeathEvent;
import org.bukkit.event.player.PlayerRespawnEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.material.Stairs;
import org.bukkit.plugin.Plugin;
import org.bukkit.scheduler.BukkitRunnable;
import org.bukkit.scheduler.BukkitScheduler;

import java.util.Objects;

public class DeathEvent implements Listener {

    private Main main = Main.getInstance();

    long stormTicks;


    public DeathEvent(){
        loadTicks();

    }

    public void loadTicks(){
        this.stormTicks = main.world.getWeatherDuration();
    }

    @EventHandler(priority = EventPriority.HIGH)
    public void onPlayerDeath(PlayerDeathEvent e){
        Player player = e.getEntity();
        String victim = player.getName();
        int livesInit = livesCountInitial(player);

        loadTicks();

        if (!this.main.getF().getConfig().contains("players."+player.getUniqueId().toString()+".lives")){
            this.main.getF().getConfig().set("players."+player.getUniqueId().toString()+".lives",livesInit);
            main.getF().saveConfig();
        }

        int lives = 0;

        if (this.main.getF().getConfig().contains("players."+player.getUniqueId().toString()+".lives")){
            lives = this.main.getF().getConfig().getInt("players."+player.getUniqueId().toString()+".lives");
        }
        this.main.getF().getConfig().set("players."+player.getUniqueId().toString()+".lives",(lives-1));
        main.getF().saveConfig();
        if (this.main.getF().getConfig().contains("players."+player.getUniqueId().toString()+".lives")){
            lives = this.main.getF().getConfig().getInt("players."+player.getUniqueId().toString()+".lives");
        }


        if (lives >= 2){
            for (Player p : Bukkit.getOnlinePlayers()){
                String msg = main.format("&c&lEste es el comienzo del sufrimiento eterno de&4&l "+victim+ "&c&l ¡HA PERDIDO UNA VIDA! ¡LE QUEDAN "+lives+"!");
                p.sendMessage(msg);
                String ServerMessageTitle = main.format("&c¡Hellcraft!");
                String ServerMessageSubtitle = main.format("!%player% ha muerto!");
                p.sendTitle(ServerMessageTitle, ServerMessageSubtitle.replace("%player%", victim), 20, 100, 20);
                p.playSound(p.getLocation(), Sound.ENTITY_SKELETON_HORSE_DEATH, 10.0F, 1.0F);
            }
        }

        if (lives == 1){
            for (Player p : Bukkit.getOnlinePlayers()){
                String msg = main.format("&c&lEste es el comienzo del sufrimiento eterno de&4&l "+victim+ "&c&l ¡HA PERDIDO UNA VIDA! ¡ESTA USANDO SU ULTIMA VIDA!");
                p.sendMessage(msg);
                String ServerMessageTitle = main.format("&c&l¡Hellcraft!");
                String ServerMessageSubtitle = main.format("!%player% ha muerto!");
                p.sendTitle(ServerMessageTitle, ServerMessageSubtitle.replace("%player%", victim), 20, 100, 20);
                p.playSound(p.getLocation(), Sound.ENTITY_SKELETON_HORSE_DEATH, 10.0F, 1.0F);
            }
        }

        if (lives<=0){
            for (Player p : Bukkit.getOnlinePlayers()) {
                if (main.getRank(e.getEntity(),"netherite")){
                    String msg = main.format("&c&lEste es el comienzo del sufrimiento eterno de&4&l "+victim+ "&c&l. ¡YA NO LE QUEDA MAS VIDAS! ¡Ahora es ovni!");
                    p.sendMessage(msg);
                    String ServerMessageTitle = main.format("&c¡Hellcraft!");
                    String ServerMessageSubtitle = main.format("!%player% ha muerto!");
                    p.sendTitle(ServerMessageTitle, ServerMessageSubtitle.replace("%player%", victim), 20, 100, 20);
                    p.playSound(p.getLocation(), Sound.ENTITY_SKELETON_HORSE_DEATH, 10.0F, 1.0F);
                }else {
                    String msg = main.format("&c&lEste es el comienzo del sufrimiento eterno de&4&l "+victim+ "&c&l. ¡YA NO LE QUEDA MAS VIDAS! ¡Ha SIDO PERMABANEADO!");
                    p.sendMessage(msg);
                    String ServerMessageTitle = main.format("&c¡Hellcraft!");
                    String ServerMessageSubtitle = main.format("!%player% ha muerto!");
                    p.sendTitle(ServerMessageTitle, ServerMessageSubtitle.replace("%player%", victim), 20, 100, 20);
                    p.playSound(p.getLocation(), Sound.ENTITY_SKELETON_HORSE_DEATH, 10.0F, 1.0F);
                }

            }
        }

        if (main.world.hasStorm()){
            if (main.world.getWeatherDuration()>=(((20*60)*60)*6)){
                for (Player p : Bukkit.getOnlinePlayers()){
                    String msg = main.format("&c&lEl Jugador&6&l {p} &c&lya no puede dar mas horas de tormenta").replace("{p}",victim);
                    p.sendMessage(msg);
                }
            }else {
                main.world.setWeatherDuration(main.world.getWeatherDuration()+((20*60)*60));
            }
        }else {
            Bukkit.dispatchCommand((CommandSender)Bukkit.getConsoleSender(), "weather thunder");
            main.world.setWeatherDuration((20*60)*60);
        }
        main.world.setGameRule(GameRule.DO_WEATHER_CYCLE,true);


    }


    @EventHandler
    public void FinalDeath(PlayerDeathEvent e){
        final Player p = e.getEntity();
        final Player off = p;
        OfflinePlayer player1 = Bukkit.getOfflinePlayer(off.getName());
        Player player = e.getEntity();
        int lives = this.main.getF().getConfig().getInt("players."+player.getUniqueId().toString()+".lives");;

        if (!(lives<=0))
            return;

        Location l = player.getEyeLocation().clone();
        if (l.getY() < 3.0D)
            l.setY(3.0D);
        int x = (int)l.getX();
        int y = (int)l.getY();
        int z = (int)l.getZ();
        Block skullBlock = l.getBlock();
        skullBlock.setType(Material.PLAYER_HEAD);
        Skull skullState = (Skull)skullBlock.getState();
        skullState.setOwningPlayer((OfflinePlayer)player);
        skullState.update();
        Rotatable rotatable = (Rotatable)skullBlock.getBlockData();
        rotatable.setRotation(BlockFace.WEST);
        skullBlock.setBlockData((BlockData)rotatable);
        skullBlock.getRelative(BlockFace.DOWN).setType(Material.NETHER_BRICK_FENCE);
        skullBlock.getRelative(BlockFace.DOWN).getRelative(BlockFace.DOWN).setType(Material.SEA_LANTERN);

    }



    @EventHandler
    public void onKeepInvetory(PlayerDeathEvent e){
        Player player = e.getEntity();
        int lives = this.main.getF().getConfig().getInt("players."+player.getUniqueId().toString()+".lives");;



        if (e.getEntity().hasPermission("hellcraft.keepinventory")){
            if (lives<=0){
                e.setKeepLevel(false);
                e.setKeepInventory(false);
            }else{
                e.setKeepLevel(true);
                e.setKeepInventory(true);
                e.getDrops().clear();
                e.setDroppedExp(0);
            }
        }
    }


    @EventHandler
    public void Ban(PlayerDeathEvent e){
        Player player = e.getEntity();
        int livesInit = livesCountInitial(player);
        if (!this.main.getF().getConfig().contains("players."+player.getUniqueId().toString()+".lives")){
            this.main.getF().getConfig().set("players."+player.getUniqueId().toString()+".lives",livesInit);
            main.getF().saveConfig();
        }
        new BukkitRunnable() {
            private Main main=Main.getInstance();

            @Override
            public void run() {
                int lives=this.main.getF().getConfig().getInt("players."+player.getUniqueId().toString()+".lives");
                if (lives<=0){
                    if(player.hasPermission("hellcraft.ovni")){
                        player.setGameMode(GameMode.SPECTATOR);
                        cancel();
                    }else {
                        player.kickPlayer(main.format("&c&lHas sido ¡Permabaneado!"));
                        Bukkit.getBanList(BanList.Type.IP).addBan(player.getAddress().getHostName(),ChatColor.RED+"Has sido Permabaneado",null,player.getName());
                        cancel();
                    }
                }else {
                    cancel();
                }
            }
        }.runTaskTimer(main, 0L, 40L);

    }


    public long getTick(){//get de stormTicks
        return stormTicks;
    }

    public void setTick(long i){
        stormTicks = i;
    }


    @EventHandler
    public void onPlayerDiscord(PlayerDeathEvent e){
        Player player = e.getEntity();
        int livesInit = livesCountInitial(player);
        if (!this.main.getF().getConfig().contains("players."+player.getUniqueId().toString()+".lives")){
            this.main.getF().getConfig().set("players."+player.getUniqueId().toString()+".lives",livesInit);
            main.getF().saveConfig();
        }
        int lives = this.main.getF().getConfig().getInt("players."+player.getUniqueId().toString()+".lives");
        if (lives<=0){
            DiscordManager.getInstance().banPlayer((OfflinePlayer) player, e.getDeathMessage());
        }
    }

    public int livesCountInitial(Player p) {
        if(main.getRank(p, "admin")||main.getRank(p,"netherite")){
            return 4;
        }else if (main.getRank(p, "oro") || main.getRank(p, "diamante") || main.getRank(p, "twitch")) {
            return 3;
        } else if (main.getRank(p, "hierro")) {
            return 2;
        } else {
            return -1;
        }

    }

    @EventHandler
    public void onRespawn(PlayerRespawnEvent e){
        Player player = e.getPlayer();
        final Player p = e.getPlayer();
        final Player off = p;
        OfflinePlayer player1 = Bukkit.getOfflinePlayer(off.getName());
        int finalLives = 0;
        if (this.main.getF().getConfig().contains("players."+player.getUniqueId().toString()+".lives"))
            finalLives = this.main.getF().getConfig().getInt("players."+player.getUniqueId().toString()+".lives");
        int finalLives1 = finalLives;
        new BukkitRunnable() {
            public void run() {
                if (finalLives1 <=0){
                    cancel();
                }else {
                    player.setGameMode(GameMode.SURVIVAL);
                    cancel();
                }
            }
        }.runTaskTimer((Plugin) main, 0, 200);


    }



}
