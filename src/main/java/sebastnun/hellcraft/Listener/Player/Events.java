package sebastnun.hellcraft.Listener.Player;

import com.comphenix.protocol.ProtocolLibrary;
import com.comphenix.protocol.ProtocolManager;
import com.comphenix.protocol.events.PacketContainer;
import net.minecraft.network.protocol.game.PacketPlayInClientCommand;
import org.bukkit.*;
import org.bukkit.block.Block;
import org.bukkit.block.Chest;
import org.bukkit.command.CommandSender;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.craftbukkit.v1_17_R1.entity.CraftPlayer;
import org.bukkit.entity.Arrow;
import org.bukkit.entity.Player;
import org.bukkit.entity.TNTPrimed;
import org.bukkit.event.EventException;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.block.BlockPlaceEvent;
import org.bukkit.event.entity.PlayerDeathEvent;
import org.bukkit.event.entity.ProjectileHitEvent;
import org.bukkit.event.inventory.InventoryOpenEvent;
import org.bukkit.event.player.AsyncPlayerChatEvent;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.persistence.PersistentDataType;
import sebastnun.hellcraft.Entities.Boss;
import sebastnun.hellcraft.Main;
import sebastnun.hellcraft.PrincipalCommand;
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
        main.reloadLives(p);
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
        main.reloadLives(p);
    }




    private int InicializateLives(Player p){
        int lives=1;
        if (p.isOp()){
            return 10000;
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

//PacketPlayOutEntityStatus status = new PacketPlayOutEntityStatus(((CraftPlayer)player).getHandle(), (byte) 35);
//        ((CraftPlayer)player).getHandle().playerConnection.sendPacket(status);

    @EventHandler
    private void blockPlace(BlockPlaceEvent e){
        if(e.getBlock().getType() == Material.ICE){
            e.setCancelled(true);
        }
        if (e.getBlock().getType()==Material.TNT){
            e.getBlock().setType(Material.AIR);
            TNTPrimed tnt =e.getBlock().getLocation().getWorld().spawn(e.getBlock().getLocation().add(0.5,0,0.5), TNTPrimed.class);
            tnt.setCustomName("tnt");

        }
    }

    @EventHandler
    private void ArrowEvent(ProjectileHitEvent e){
        if (!(e.getEntity() instanceof Arrow))
            return;
        if (!(e.getEntity().getShooter() instanceof Player))
            return;
        Arrow arrow = (Arrow) e.getEntity();
        if (!(arrow.getFireTicks()>0))
            return;
        if(e.getHitBlock().getType() == Material.BARREL){
            e.getHitBlock().setType(Material.AIR);
            e.getHitBlock().getLocation().getWorld().createExplosion(e.getHitBlock().getLocation(),3,true);
            e.getEntity().remove();
        }
    }

/*
    @EventHandler
    private void Invetory(PlayerInteractEvent e){
        if (e.hasBlock()){
            if(e.getClickedBlock().getType().equals(Material.CHEST)){
                if (((Chest)e.getClickedBlock()).getPersistentDataContainer().has(PrincipalCommand.getKey(),PersistentDataType.BYTE)){
                    if (e.getPlayer().getInventory().getItemInMainHand().hasItemMeta()){
                        if (e.getPlayer().getInventory().getItemInMainHand().getItemMeta().getCustomModelData() != 123456){
                            e.setCancelled(true);
                        }
                    }
                }else {
                    return;
                }

            }
        }
    }
*/


    @EventHandler
    private void Chat(AsyncPlayerChatEvent e){
        if (Main.isEvent()){
            e.setCancelled(true);
            for (Player p : Bukkit.getOnlinePlayers()){
                p.sendMessage(Main.format("&7"+e.getPlayer().getDisplayName()+": ".concat(e.getMessage())));
            }
        }
    }

}
