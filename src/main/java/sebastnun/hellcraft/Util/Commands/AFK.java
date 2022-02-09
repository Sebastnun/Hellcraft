package sebastnun.hellcraft.Util.Commands;

import org.bukkit.*;
import org.bukkit.block.Block;
import org.bukkit.block.BlockFace;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.*;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.entity.CreatureSpawnEvent;
import org.bukkit.event.entity.EntityExplodeEvent;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.event.player.PlayerQuitEvent;
import org.bukkit.metadata.FixedMetadataValue;
import org.bukkit.metadata.MetadataValue;
import org.bukkit.persistence.PersistentDataType;
import org.bukkit.plugin.Plugin;
import org.bukkit.scheduler.BukkitRunnable;
import org.bukkit.util.Vector;
import sebastnun.hellcraft.Main;
import sebastnun.hellcraft.Util.Data.PlayerDataManager;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Random;

public class AFK implements CommandExecutor, Listener {

    private static final Main main = Main.getInstance();

    public static HashMap<Player,Integer> activeAFK = new HashMap<>();


    @Override
    public boolean onCommand(CommandSender sender, Command command, String s, String[] strings) {
        if (!(sender instanceof Player)){
            Bukkit.getConsoleSender().sendMessage(Main.format("&cTu no eres Jugador"));
            return true;
        }else {
            Player p = (Player) sender;
            if (!p.getPersistentDataContainer().has(new NamespacedKey(main,"afk"), PersistentDataType.BYTE)&&!activeAFK.containsKey(p)){
                p.sendMessage(Main.format("&6Ahora puedes quedarte a AFK"));
                p.getPersistentDataContainer().set(new NamespacedKey(main,"afk"),PersistentDataType.BYTE,Byte.valueOf((byte) 1));
                addAFK(p,p.getStatistic(Statistic.PLAY_ONE_MINUTE));
            }else {
                p.sendMessage(Main.format("&6Ya no puedes quedarte a AFK"));
                p.getPersistentDataContainer().remove(new NamespacedKey(main,"afk"));
                removeAFK(p);
            }
            return true;
        }
    }


    /*@EventHandler(priority = EventPriority.HIGHEST)
    public void onExplode(EntityExplodeEvent e) {
        if (!e.getEntity().getCustomName().equalsIgnoreCase("tnt"))
            return;
        if (e.getEntity() instanceof TNTPrimed) {
            if (!e.blockList().isEmpty()) {
                List<FallingBlock> fallingBlocks = new ArrayList<>();
                final List<Block> blockList = new ArrayList<>(e.blockList());
                for (Block b : blockList) {
                    float x = (float)(-0.2D + (float)(Math.random() * 0.6000000000000001D));
                    float y = -1.0F + (float)(Math.random() * 3.0D);
                    float z = (float)(-0.2D + (float)(Math.random() * 0.6000000000000001D));
                    if (b.getType() == Material.GRASS_BLOCK || b.getType() == Material.END_STONE_BRICKS) {
                        FallingBlock fb = b.getWorld().spawnFallingBlock(b.getLocation(), b.getState().getData());
                        b.getState().setData(b.getState().getData());
                        fb.setVelocity(new Vector(x, y, z));
                        fb.setDropItem(false);
                        fb.setMetadata("Exploded", (MetadataValue)new FixedMetadataValue((Plugin)this.main, Integer.valueOf(0)));
                        fallingBlocks.add(fb);
                        (new BukkitRunnable() {
                            public void run() {
                                for (Block b : blockList) {
                                    b.getState().update();
                                    cancel();
                                }
                            }
                        }).runTaskLater((Plugin)this.main, 2L);
                        e.blockList().clear();
                    }
                }
            }
        }
    }*/




    @EventHandler
    private void onDisconected(PlayerQuitEvent e){
        if (e.getPlayer().getPersistentDataContainer().has(new NamespacedKey(main,"afk"), PersistentDataType.BYTE)){
            e.getPlayer().getPersistentDataContainer().remove(new NamespacedKey(main,"afk"));
        }
        if (activeAFK.containsKey(e.getPlayer())){
            activeAFK.remove(e.getPlayer());
        }
    }

    public static void addAFK(Player p,int i){
        if (!p.getPersistentDataContainer().has(new NamespacedKey(main,"afk"), PersistentDataType.BYTE)&&!activeAFK.containsKey(p))
            p.getPersistentDataContainer().set(new NamespacedKey(main,"afk"),PersistentDataType.BYTE,Byte.valueOf((byte) 1));

        activeAFK.put(p,i);
    }

    public static void removeAFK(Player p){
        if (p.getPersistentDataContainer().has(new NamespacedKey(main,"afk"), PersistentDataType.BYTE)&&!activeAFK.containsKey(p))
            p.getPersistentDataContainer().remove(new NamespacedKey(main,"afk"));

        activeAFK.remove(p);
    }

    public static int getAFK(Player p){
        return activeAFK.get(p);
    }

    public HashMap getActiveAFK(){
        return activeAFK;
    }

    public static boolean contains(Player key){
        return activeAFK.containsKey(key);
    }

}
