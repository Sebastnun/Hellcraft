package sebastnun.hellcraft.Items;

import com.comphenix.protocol.PacketType;
import com.comphenix.protocol.ProtocolLibrary;
import com.comphenix.protocol.ProtocolManager;
import com.comphenix.protocol.events.PacketContainer;
import net.minecraft.network.protocol.game.PacketPlayOutEntityStatus;
import org.bukkit.*;
import org.bukkit.craftbukkit.v1_17_R1.entity.CraftPlayer;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Player;
import org.bukkit.entity.Zombie;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.persistence.PersistentDataType;
import org.bukkit.scheduler.BukkitRunnable;
import sebastnun.hellcraft.Main;

import java.lang.reflect.InvocationTargetException;

public class Chains implements Listener {

    private final NamespacedKey key = new NamespacedKey(Main.getInstance(),"anima_sola");
    private int timeM = 1;
    private int timeS = 5;

    public ItemStack Chain(){
        ItemStack item = new ItemStack(Material.WOODEN_SWORD);
        ItemMeta meta = item.getItemMeta();
        meta.setCustomModelData(123456);
        meta.setDisplayName(Main.format("&4&lAnima Sola"));
        meta.getPersistentDataContainer().set(key, PersistentDataType.BYTE,(byte)1);
        meta.setUnbreakable(true);

        item.setItemMeta(meta);
        return item;
    }

    @EventHandler
    public void onInteractByAnima(PlayerInteractEvent e){
        ItemStack item = e.getPlayer().getInventory().getItemInMainHand();
        if (item==null)return;
        if (e.getAction()==Action.LEFT_CLICK_AIR||e.getAction()==Action.LEFT_CLICK_BLOCK)return;
        if (e.getPlayer().getPersistentDataContainer().has(key,PersistentDataType.BYTE)) return;
        if (!item.hasItemMeta())return;
        if (!item.getItemMeta().getPersistentDataContainer().has(key,PersistentDataType.BYTE))return;

        float distance = 30;
        Zombie zombie= null;

        for (World w : Bukkit.getWorlds()){
            for (Entity i : w.getEntities()){
                if (i instanceof Zombie && i.hasMetadata("EvilBlas")){
                    if (e.getPlayer().getLocation().distance(i.getLocation())<= distance){
                        zombie = (Zombie) i;
                    }
                }
            }
        }
        Zombie finalZombie = zombie;

        if (finalZombie == null){
            e.getPlayer().sendMessage(Main.format("&cNo se encotro a Evil Blas"));
        }else {
            finalZombie.getPersistentDataContainer().set(key,PersistentDataType.BYTE,(byte)1);
            e.getPlayer().getPersistentDataContainer().set(key,PersistentDataType.BYTE,(byte)1);
            e.getPlayer().sendMessage(Main.format("&6Se a aplicado las cadenas a blas"));
            onMoveEvent(zombie);
        }

        if (zombie==null)return;
        Bukkit.getScheduler().scheduleSyncDelayedTask(Main.getInstance(), new Runnable() {
            @Override
            public void run() {

                finalZombie.getPersistentDataContainer().remove(key);
                e.getPlayer().getPersistentDataContainer().remove(key);
            }
        },((20)*timeS)*timeM);
        Location loc = zombie.getLocation();

    }

    private void summonParticles(Location loc, Zombie z){
        World w = loc.getWorld();
        double dist=0.5;
        double distY=0.5;
        Particle particle = Particle.TOTEM;
        double dist3 = (dist+dist+dist);
        //---------------------------------------------------
        w.spawnParticle(particle, loc.add(dist,distY,dist),5);
        loc.subtract(0,distY,0);
        w.spawnParticle(particle, loc.add(dist,0.5,dist),5);
        loc.subtract(0,0.5,0);
        w.spawnParticle(particle, loc.add(dist,0,dist),5);
        loc.subtract(dist3,0,dist3);

        //----------------------------------------------------
        w.spawnParticle(particle, loc.add(dist,distY,-dist),5);
        loc.subtract(0,distY,0);
        w.spawnParticle(particle, loc.add(dist,0.5,-dist),5);
        loc.subtract(0,0.5,0);
        w.spawnParticle(particle, loc.add(dist,0,-dist),5);
        loc.subtract(dist3,0,-dist3);

        //----------------------------------------------------
        w.spawnParticle(particle, loc.add(-dist,distY,dist),5);
        loc.subtract(0,distY,0);
        w.spawnParticle(particle, loc.add(-dist,0.5,dist),5);
        loc.subtract(0,0.5,0);
        w.spawnParticle(particle, loc.add(-dist,0,dist),5);
        loc.subtract(-dist3,0,dist3);

        //-----------------------------------------------------
        w.spawnParticle(particle, loc.add(-dist,distY,-dist),5);
        loc.subtract(0,distY,0);
        w.spawnParticle(particle, loc.add(-dist,0.5,-dist),5);
        loc.subtract(0,0.5,0);
        w.spawnParticle(particle, loc.add(-dist,0,-dist),5);
        loc.subtract(-dist3,0,-dist3);


        new BukkitRunnable() {
            public void run() {
                //---------------------------------------------------
                w.spawnParticle(particle, loc.add(dist,distY,dist),5);
                w.spawnParticle(particle, loc.add(dist,0,dist),5);
                w.spawnParticle(particle, loc.add(dist,0,dist),5);
                loc.subtract(dist3,distY,dist3);

                //----------------------------------------------------
                w.spawnParticle(particle, loc.add(dist,distY,-dist),5);
                w.spawnParticle(particle, loc.add(dist,0,-dist),5);
                w.spawnParticle(particle, loc.add(dist,0,-dist),5);
                loc.subtract(dist3,distY,-dist3);

                //----------------------------------------------------
                w.spawnParticle(particle, loc.add(-dist,distY,dist),5);
                w.spawnParticle(particle, loc.add(-dist,0,dist),5);
                w.spawnParticle(particle, loc.add(-dist,0,dist),5);
                loc.subtract(-dist3,distY,dist3);

                //-----------------------------------------------------
                w.spawnParticle(particle, loc.add(-dist,distY,-dist),5);
                w.spawnParticle(particle, loc.add(-dist,0,-dist),5);
                w.spawnParticle(particle, loc.add(-dist,0,-dist),5);
                loc.subtract(-dist3,distY,-dist3);

                if (!z.getPersistentDataContainer().has(key,PersistentDataType.BYTE)){
                    cancel();
                }
            }
        }.runTaskTimer(Main.getInstance(),0,4);

    }

    public void onMoveEvent(Zombie e){
        if (e==null)
            return;

        if (!e.getPersistentDataContainer().has(key,PersistentDataType.BYTE))
            return;

        new BukkitRunnable() {
            final Location loc = e.getLocation();
            @Override
            public void run() {
                e.teleport(loc);
                if (!e.getPersistentDataContainer().has(key,PersistentDataType.BYTE))
                    cancel();
            }
        }.runTaskTimer(Main.getInstance(),0,3);
        this.summonParticles(e.getLocation(),e);

    }


    public int getTimeS(){return this.timeS;}

    public int getTimeM(){return this.timeM;}

    public void setTimeS(int Seconds){
        if (Seconds<=0)Seconds=1;
        this.timeS=Seconds;
    }

    public void setTimeM(int Minutes){
        if (Minutes <= 0)Minutes = 1;
        this.timeM=Minutes;
    }

    public NamespacedKey getKey(){return this.key;}


}
