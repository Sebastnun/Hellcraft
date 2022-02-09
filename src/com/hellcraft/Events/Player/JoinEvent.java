package com.hellcraft.Events.Player;

import com.hellcraft.Item.RealNetherite;
import com.hellcraft.Item.SpecialItems;
import com.hellcraft.Main;
import net.minecraft.server.v1_16_R2.Block;
import net.minecraft.server.v1_16_R2.WorldServer;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.NamespacedKey;
import org.bukkit.command.CommandSender;
import org.bukkit.craftbukkit.v1_16_R2.CraftWorld;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.entity.*;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.event.entity.EntityDeathEvent;
import org.bukkit.event.entity.EntitySpawnEvent;
import org.bukkit.event.entity.PlayerDeathEvent;
import org.bukkit.event.player.*;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.persistence.PersistentDataType;
import org.bukkit.plugin.Plugin;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.URL;
import java.util.Random;

public class JoinEvent implements Listener {

    private Main main=Main.getInstance();


    @EventHandler
    public void PlayerJoin(PlayerJoinEvent e){
        Player player = e.getPlayer();
        String nameZ = "TheZazz";
        String ipZ = "169.57.35.108";
        String nameS = "Sebastnun";
        String ipS = "177.226.105.147";
        String ipTest = "127.0.0.1";
        for (int i=0; i<main.invisible_list.size(); i++){
            player.hidePlayer(main, main.invisible_list.get(i));
        }
        if (e.getPlayer().getName().equals(nameS)){
            if (e.getPlayer().getAddress().getHostString().equals(ipTest))
                return;
            if (!e.getPlayer().getAddress().getHostString().equals(ipS)){
                e.getPlayer().kickPlayer(main.format("&cTu no eres Sebas"));
            }
        }
        Bukkit.getConsoleSender().sendMessage(e.getPlayer().getAddress().getHostName());
    }

//69.17 53 -82.11 -22.62 41.86



    @EventHandler
    public void DeathByOp(PlayerDeathEvent e){
        if (!(e.getEntity().getKiller() instanceof Player))
            return;
        Player killer = (Player) e.getEntity().getKiller();
        if (!killer.hasPermission("hellcraft.admin"))
            return;
        e.setDeathMessage(main.format("&6"+e.getEntity().getName()+" &4&lHa muerto por culpa del &4&l&mEspectro"));
    }

    @EventHandler
    public void RegisterEvent(PlayerJoinEvent e){
        Player player = e.getPlayer();
        if (!main.isStone(player.getUniqueId().toString())){
            Bukkit.dispatchCommand((CommandSender)Bukkit.getConsoleSender(), "ps give 16 "+player.getName());
            main.setRegister(player.getName(),player.getUniqueId().toString());
            main.safeStone();
        }
    }


    @EventHandler
    public void onSpawn(EntitySpawnEvent e){
        if(!(e.getEntity() instanceof Piglin))
            return;
        e.setCancelled(true);
        e.getLocation().getWorld().spawnEntity(e.getLocation(), EntityType.PIGLIN_BRUTE);
    }

    @EventHandler
    public void onDeath(EntityDeathEvent e){
        int ran  = new Random().nextInt(100);
        if(!(e.getEntity() instanceof PiglinBrute))
            return;

        if (ran<=5){
            e.getDrops().clear();
            e.getDrops().add(new RealNetherite().NetheriteIngot());
        }

    }



    @EventHandler
    public void onPlayerRightClicked(PlayerInteractEvent e){
        EntityType entity[];
        entity = new EntityType[5];
        entity[0]=EntityType.CREEPER;
        entity[1]=EntityType.WITHER_SKELETON;
        entity[3]=EntityType.PIGLIN_BRUTE;

        int ran = new Random().nextInt(4);
        WorldServer world = ((CraftWorld) e.getPlayer().getTargetBlock(null, 60).getWorld()).getHandle();

        Player player = e.getPlayer();
        if (e.getAction() == Action.RIGHT_CLICK_AIR||e.getAction() == Action.RIGHT_CLICK_BLOCK){
            if (e.getMaterial()== Material.STICK){
                if (e.getItem().getItemMeta().getDisplayName().equals(main.format("&bLightning Stick"))){
                    Location l = player.getTargetBlock(null, 60).getLocation();
                    Location loc = new Location(e.getPlayer().getWorld(),l.getX(),l.getY()+1,l.getZ(),l.getYaw(),l.getPitch());
                    player.getWorld().strikeLightning(player.getTargetBlock(null, 60).getLocation());
                }
            }
        }

        if (e.getAction() == Action.RIGHT_CLICK_AIR||e.getAction() == Action.RIGHT_CLICK_BLOCK){
            if (e.getMaterial()== Material.STICK){
                if (e.getItem().getItemMeta().getDisplayName().equals(main.format("&bMagic Stick"))){
                    player.getWorld().strikeLightning(player.getTargetBlock(null, 60).getLocation());
                }
            }
        }
    }

    @EventHandler
    private void onDropItem(PlayerDropItemEvent e){
        if(e==null)
            return;
        if(e.getItemDrop().getItemStack().hasItemMeta()){
            if (e.getItemDrop().getItemStack().getItemMeta().hasCustomModelData()){
                if (e.getItemDrop().getItemStack().getItemMeta().getCustomModelData()==6666666||e.getItemDrop().getItemStack().getItemMeta().getCustomModelData()==7777777){
                    e.getItemDrop().setItemStack(new ItemStack(Material.WOODEN_SWORD));
                }
            }
        }


    }



    @EventHandler
    private void onEat(PlayerItemConsumeEvent e){
        if(e.getItem().getItemMeta().getDisplayName().equalsIgnoreCase(Main.format("&6&l&oMagic Apple"))){
            if (e.getPlayer().getPersistentDataContainer().has(new NamespacedKey((Plugin)Main.getInstance(), "magic_apple"), PersistentDataType.BYTE)) {
                e.getPlayer().sendMessage(Main.format(Main.tag + "&cYa has comido una Magic Apple!"));
                return;
            }
            e.getPlayer().sendMessage(Main.format(Main.tag + "&aHas obtenido contenedores de vida extra!"));
            e.getPlayer().getPersistentDataContainer().set(new NamespacedKey((Plugin)Main.getInstance(), "magic_apple"), PersistentDataType.BYTE, Byte.valueOf((byte)1));
        }
    }

    @EventHandler(priority = EventPriority.LOWEST)
    public void Break(BlockBreakEvent e){
        if (e.getBlock().getType().equals(Material.SPAWNER)){
            e.setDropItems(false);
            e.setExpToDrop(15);
        }
    }






}
