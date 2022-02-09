package com.hellcraft.Util.Manager;

import com.hellcraft.Item.NebulaArmor;
import com.hellcraft.Item.RealNetherite;
import com.hellcraft.Item.SpecialItems;
import com.hellcraft.Main;
import com.sk89q.worldedit.EditSession;
import com.sk89q.worldedit.WorldEdit;
import com.sk89q.worldedit.WorldEditException;
import com.sk89q.worldedit.bukkit.BukkitWorld;
import com.sk89q.worldedit.extent.Extent;
import com.sk89q.worldedit.extent.clipboard.Clipboard;
import com.sk89q.worldedit.extent.clipboard.io.ClipboardFormat;
import com.sk89q.worldedit.extent.clipboard.io.ClipboardFormats;
import com.sk89q.worldedit.extent.clipboard.io.ClipboardReader;
import com.sk89q.worldedit.function.operation.Operation;
import com.sk89q.worldedit.function.operation.Operations;
import com.sk89q.worldedit.math.BlockVector3;
import com.sk89q.worldedit.session.ClipboardHolder;
import net.minecraft.server.v1_16_R2.ExplosionDamageCalculatorEntity;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.World;
import org.bukkit.attribute.Attribute;
import org.bukkit.block.Block;
import org.bukkit.block.Chest;
import org.bukkit.block.data.type.Fire;
import org.bukkit.entity.*;
import org.bukkit.event.EventException;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.event.block.BlockDispenseEvent;
import org.bukkit.event.entity.*;
import org.bukkit.event.inventory.InventoryOpenEvent;
import org.bukkit.event.player.PlayerBucketEmptyEvent;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.event.world.ChunkLoadEvent;
import org.bukkit.generator.ChunkGenerator;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.plugin.Plugin;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;
import org.bukkit.util.noise.SimplexOctaveGenerator;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.*;

public class    DimencionManager implements Listener {

    private Main main = Main.getInstance();
    private final SplittableRandom random;

    public DimencionManager() {
        this.random = new SplittableRandom();
    }

    private List<Integer> randomLoc = new ArrayList<>();

    private List<String> probs;

    private List<Material> alreadyRolled;

    @EventHandler(priority = EventPriority.HIGHEST)
    public void onLoadChunk(ChunkLoadEvent e){
        if (e.getWorld().getName().equalsIgnoreCase("data")){
            if (e.isNewChunk()){
                final SimplexOctaveGenerator lowGenerator = new SimplexOctaveGenerator(new Random(e.getWorld().getSeed()), 8);
                final int chunkX = e.getChunk().getX();
                final int chunkZ = e.getChunk().getZ();
                for (int X = 0; X < 16; ++X) {
                    for (int Z = 0; Z < 16; ++Z) {
                        final int noise = (int)(lowGenerator.noise((double)(chunkX * 16 + X), (double)(chunkZ * 16 + Z), 0.5, 0.5) * 15.0);

                        if (noise <= 0) {
                            if (this.random.nextInt(10000) == 0) {
                                final int finalX = X;
                                final int finalZ = Z;

                                Bukkit.getScheduler().runTaskLater((Plugin) Main.getInstance(), () -> this.generateIsland(e.getWorld(), chunkX * 16 + finalX, chunkZ * 16 + finalZ, this.random), 20L);
                            }
                        }else {

                            if (this.random.nextInt(80000) == 0) {
                                final int finalX2 = X;
                                final int finalZ2 = Z;
                                Bukkit.getScheduler().runTaskLater((Plugin)Main.getInstance(), () -> this.generateBig(e.getWorld(), chunkX * 16 + finalX2, chunkZ * 16 + finalZ2), 20L);
                            }
                        }
                    }
                }
            }
        }
    }

    private void generateBig(final World world, final int x, final int z) {
        final File file = new File(main.getDataFolder()+ File.separator + "/schematics/big.schem");
        final ClipboardFormat format = ClipboardFormats.findByFile(file);
        assert format != null;
        Clipboard clipboard;
        try (final ClipboardReader reader = format.getReader((InputStream)new FileInputStream(file))) {
            clipboard = reader.read();
        }
        catch (IOException e) {
            e.printStackTrace();
            return;
        }
        try (final EditSession editSession = WorldEdit.getInstance().getEditSessionFactory().getEditSession((com.sk89q.worldedit.world.World)new BukkitWorld(world), -1)) {
            final ClipboardHolder clipboardHolder = new ClipboardHolder(clipboard);
            final Operation operation = clipboardHolder.createPaste((Extent)editSession).to(BlockVector3.at(x, 134, z)).ignoreAirBlocks(true).copyEntities(true).build();
            Operations.complete(operation);

        }
        catch (WorldEditException e2) {
            e2.printStackTrace();
        }


    }
    // \



    private void generateIsland(final World world, final int x, final int z, final SplittableRandom random) {
        File file = null;
        switch (random.nextInt(6)) {
            case 0: {
                file = new File(main.getDataFolder() + File.separator +"/schematics/island1.schem");
                break;
            }
            case 1: {
                file = new File(main.getDataFolder() + File.separator + "/schematics/island2.schem");
                break;
            }
            case 2: {
                file = new File(main.getDataFolder() + File.separator +"/schematics/island3.schem");
                break;
            }
            case 3: {
                file = new File(main.getDataFolder()+ File.separator +"/schematics/island4.schem");
                break;
            }
            default: {
                file = new File(main.getDataFolder()+ File.separator + "/schematics/island5.schem");
                break;
            }
        }
        final ClipboardFormat format = ClipboardFormats.findByFile(file);
        assert format != null;
        Clipboard clipboard;
        try (final ClipboardReader reader = format.getReader((InputStream)new FileInputStream(file))) {
            clipboard = reader.read();
        }
        catch (IOException e) {
            e.printStackTrace();
            return;
        }
        try (final EditSession editSession = WorldEdit.getInstance().getEditSessionFactory().getEditSession((com.sk89q.worldedit.world.World)new BukkitWorld(world), -1)) {
            final Operation operation = new ClipboardHolder(clipboard).createPaste((Extent)editSession).to(BlockVector3.at(x, 200, z)).ignoreAirBlocks(true).build();
            Operations.complete(operation);
        }
        catch (WorldEditException e2) {
            e2.printStackTrace();
        }
    }


    /*
    @EventHandler
    public void onSpawn(EntitySpawnEvent e){
        if (e.getLocation().getWorld().getName().equalsIgnoreCase("data")){
            if (e.getEntity() instanceof Creeper){
                e.setCancelled(true);
                Creeper c = e.getEntity().getLocation().getWorld().spawn(e.getLocation().add(0.5,0,0.5),Creeper.class);
                c.setPowered(true);
                c.setInvisible(true);
                c.setExplosionRadius(4);
                c.setCustomName(main.format("&4&lCreeper Infernal"));
                c.setCustomNameVisible(true);
            }else if (e.getEntity() instanceof Zombie){
                e.setCancelled(true);
                NebulaArmor n = new NebulaArmor();
                WitherSkeleton skeleton = e.getEntity().getLocation().getWorld().spawn(e.getLocation().add(0.5,0,0.5),WitherSkeleton.class);
                skeleton.setMaxHealth(100.0);
                skeleton.setHealth(100.0);
                skeleton.addPotionEffect(new PotionEffect(PotionEffectType.SPEED,99999,1,false,false));
                skeleton.setCustomName(main.format("&4&lSegidor de la muerte"));
                skeleton.setCustomNameVisible(true);
                skeleton.getEquipment().setItemInMainHand(new SpecialItems().EspadaDelBoos());
                skeleton.getEquipment().setHelmet(n.NebulaHelmet());
                skeleton.getEquipment().setChestplate(n.NebulaChestplate());
                skeleton.getEquipment().setLeggings(n.NebulaLeggins());
                skeleton.getEquipment().setBoots(n.NebulaBoots());

                //Drop Chances
                skeleton.getEquipment().setHelmetDropChance(0);
                skeleton.getEquipment().setChestplateDropChance(0);
                skeleton.getEquipment().setLeggingsDropChance(0);
                skeleton.getEquipment().setBootsDropChance(0);
                skeleton.getEquipment().setItemInMainHandDropChance(0);
                skeleton.getEquipment().setItemInOffHandDropChance(0);
            }else if (e.getEntity() instanceof Spider){
                e.setCancelled(true);
                Ghast g = e.getEntity().getLocation().getWorld().spawn(e.getLocation().add(0.5,0,0.5),Ghast.class);
                g.setMaxHealth(200.0);
                g.setHealth(200.0);
                g.setCustomName(main.format("&6&lGhast Infernal"));
                g.setCustomNameVisible(true);
            }
        }
    }*/


    @EventHandler
    public void onBucket(PlayerBucketEmptyEvent e) {
        if (e.getPlayer().getWorld().getName().equalsIgnoreCase("data"))
            e.setCancelled(true);
    }

    @EventHandler
    public void onWater(BlockDispenseEvent e) {
        if (e.getItem() != null && (
                e.getItem().getType() == Material.BUCKET || e.getItem().getType() == Material.WATER_BUCKET) &&
                e.getBlock().getWorld().getName().equalsIgnoreCase("data"))
            e.setCancelled(true);
    }






    @EventHandler
    public void onInventoryOpenEvent(InventoryOpenEvent e){
        if (e.getPlayer().getWorld().getName().equalsIgnoreCase("data")){
            if (e.getInventory().getHolder() instanceof Chest){
                if (e.getInventory().contains(Material.STRUCTURE_VOID)){
                    e.getInventory().clear();
                    addItem(e.getInventory());

                }
            }
        }
    }



    @EventHandler
    public void onDamage(EntityDamageByEntityEvent e){
        if (e.getEntity() instanceof Player){
            if (e.getCause() == EntityDamageEvent.DamageCause.PROJECTILE){
                Projectile projectile = (Projectile) e.getDamager();
                if (projectile.getShooter() instanceof Ghast){
                    if (e.getDamager().getLocation().getWorld().getName().equalsIgnoreCase("data")||e.getDamager().getLocation().getWorld().getName().equalsIgnoreCase("end")){
                        e.setDamage(100.0);
                    }
                }
            }
        }
        if (e.getEntity() instanceof Player){
            if (e.getCause() == EntityDamageEvent.DamageCause.ENTITY_EXPLOSION){
                if (e.getDamager() instanceof Fireball){
                    if (e.getDamager().getLocation().getWorld().getName().equalsIgnoreCase("data")||e.getDamager().getLocation().getWorld().getName().equalsIgnoreCase("end")){
                        e.setDamage(100.0);
                    }
                }
            }
        }
        if (e.getEntity() instanceof Ghast){
            if (e.getCause() == EntityDamageEvent.DamageCause.ENTITY_EXPLOSION){
                if (e.getDamager() instanceof Fireball){
                    if (e.getDamager().getLocation().getWorld().getName().equalsIgnoreCase("data")||e.getDamager().getLocation().getWorld().getName().equalsIgnoreCase("end")){
                        e.setCancelled(true);
                    }
                }
            }
        }
    }

    @EventHandler
    public void onExplotion(ExplosionPrimeEvent e){
        if (e.getEntity() instanceof Fireball){
            if (e.getEntity().getLocation().getWorld().getName().equalsIgnoreCase("data")){
                e.setRadius(5);
            }else if(e.getEntity().getLocation().getWorld().getName().equalsIgnoreCase("end")){
                e.setRadius(2);
            }
        }
    }



   @EventHandler
    public void onBreak(BlockBreakEvent e){
        Player p = e.getPlayer();
        if (p.getWorld().getName().equalsIgnoreCase("data") && e.getBlock().getState() instanceof Chest) {
            Chest chest = (Chest)e.getBlock().getState();
            if (chest.getInventory().contains(Material.STRUCTURE_VOID)){
                chest.getInventory().clear();
                ItemStack itemStack;

                Random random = new Random();
                boolean ingot = true;
                for (int i=0; i<=27;i++){
                    switch (random.nextInt(60)){
                        case 0:{
                            itemStack = new ItemStack(Material.GOLDEN_APPLE,3);

                            break;
                        }
                        case 1:{
                            itemStack = new ItemStack(Material.GOLD_INGOT,2);
                            break;
                        }
                        case 2:{
                            itemStack = new ItemStack(Material.FIREWORK_ROCKET  ,5);
                            break;
                        }
                        case 3:{
                            if (ingot){
                                itemStack = new NebulaArmor().DeaethGem();
                                ingot = false;
                            }else {
                                itemStack = new ItemStack(Material.VOID_AIR);
                            }
                            break;
                        }
                        case 4:{
                            if (ingot){
                                itemStack = new NebulaArmor().NebuluosIngot();
                                ingot = false;
                            }else {
                                itemStack = new ItemStack(Material.VOID_AIR);
                            }
                            break;
                        }
                        case 5:{
                            itemStack = new ItemStack(Material.FIREWORK_ROCKET  ,8);
                            break;
                        }
                        case 6:{
                            itemStack = new ItemStack(Material.GOLD_INGOT,6);
                            break;
                        }
                        case 7:{
                            itemStack = new ItemStack(Material.GOLDEN_APPLE,5);

                            break;
                        }
                        case 8:{
                            itemStack = new ItemStack(Material.FIREWORK_ROCKET  ,6);
                            break;
                        }
                        case 9:{
                            itemStack = new ItemStack(Material.GOLD_INGOT,3);
                            break;
                        }
                        case 10:{
                            itemStack = new ItemStack(Material.GOLDEN_APPLE,2);

                            break;
                        }
                        default:{
                            itemStack = new ItemStack(Material.VOID_AIR);
                            break;
                        }
                    }
                    chest.getInventory().addItem(itemStack);

                }
                chest.getInventory().addItem(new ItemStack(Material.CHEST));

            }

        }
    }

    @EventHandler
    public void onDeath(EntityDeathEvent e){
        if (!(e.getEntity().getWorld().getName().equalsIgnoreCase(main.dimension.getName())))
            return;
        if (!(e.getEntity() instanceof WitherSkeleton))
            return;
        e.getDrops().clear();
    }



    private void addItem(Inventory inventory){
        ItemStack itemStack;

        boolean ingot = true;
        Random random = new Random();
        for (int i=0; i<=26;i++){
            switch (random.nextInt(37)){
                case 0:{
                    itemStack = new ItemStack(Material.GOLDEN_APPLE,3);

                    break;
                }
                case 1:{
                    itemStack = new ItemStack(Material.GOLD_INGOT,2);
                    break;
                }
                case 2:{
                    itemStack = new ItemStack(Material.FIREWORK_ROCKET  ,15);
                    break;
                }
                case 3:{
                    if (ingot){
                        itemStack = new NebulaArmor().DeaethGem();
                        ingot = false;
                    }else {
                        itemStack = new ItemStack(Material.VOID_AIR);
                    }
                    break;
                }
                case 4:{
                    if (ingot){
                        itemStack = new NebulaArmor().NebuluosIngot();
                        ingot = false;
                    }else {
                        itemStack = new ItemStack(Material.VOID_AIR);
                    }
                    break;
                }
                case 5:{
                    itemStack = new ItemStack(Material.FIREWORK_ROCKET  ,8);
                    break;
                }
                case 6:{
                    itemStack = new ItemStack(Material.GOLD_INGOT,6);
                    break;
                }
                case 7:{
                    itemStack = new ItemStack(Material.GOLDEN_APPLE,5);

                    break;
                }
                case 8:{
                    itemStack = new ItemStack(Material.FIREWORK_ROCKET  ,6);
                    break;
                }
                case 9:{
                    itemStack = new ItemStack(Material.GOLD_INGOT,3);
                    break;
                }
                case 10:{
                    itemStack = new ItemStack(Material.GOLDEN_APPLE,2);

                    break;
                }
                default:{
                    itemStack = new ItemStack(Material.VOID_AIR);
                    break;
                }
            }
            inventory.setItem(i,itemStack);
        }

    }


}