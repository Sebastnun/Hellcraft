package com.hellcraft.Events.Final;

import com.hellcraft.Events.Final.Util.DemonPhase;
import com.hellcraft.Events.Final.Util.EnderTicks;
import com.hellcraft.Main;


import com.hellcraft.Mobs.CustomCreeper;
import com.hellcraft.Mobs.CustomEnderman;
import com.hellcraft.Mobs.CustomGhast;
import com.hellcraft.Task.EndTask;
import com.sun.istack.internal.Nullable;
import net.minecraft.server.v1_16_R2.*;
import org.bukkit.*;
import org.bukkit.Material;
import org.bukkit.attribute.Attribute;
import org.bukkit.block.Block;
import org.bukkit.block.BlockFace;
import org.bukkit.craftbukkit.v1_16_R2.CraftWorld;
import org.bukkit.entity.*;
import org.bukkit.entity.Entity;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.*;
import org.bukkit.event.player.PlayerMoveEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.FireworkMeta;
import org.bukkit.metadata.FixedMetadataValue;
import org.bukkit.metadata.MetadataValue;
import org.bukkit.persistence.PersistentDataType;
import org.bukkit.plugin.Plugin;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;
import org.bukkit.scheduler.BukkitRunnable;
import org.bukkit.util.Vector;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.SplittableRandom;


public class FinalDragon implements Listener {

    private Main main = Main.getInstance();

    private List<Entity> enderGhasts;

    private ArrayList<Location> alreadyExploded = new ArrayList<>();

    private SplittableRandom random = new SplittableRandom();

    @EventHandler
    public void onCreatureSpawn(CreatureSpawnEvent e) {
        if (e.isCancelled())
            return;
        LivingEntity entity = e.getEntity();
        if (isInEnd(entity.getLocation())) {
            if (this.main.getTask() == null) {
                for (Entity n : e.getLocation().getWorld().getEntitiesByClass(EnderDragon.class)) {
                    if (n.isValid() && !n.isDead()) {
                        n.setCustomName(Main.format("&4&lHellDragon"));
                        ((LivingEntity) n).getAttribute(Attribute.GENERIC_MAX_HEALTH).setBaseValue(2000);
                        ((LivingEntity) n).setHealth(2000);
                        this.main.setTask(new EnderTicks((EnderDragon) n, this.main));
                        this.main.getTask().runTaskTimer((Plugin) this.main, 0L, 20L);
                    }
                }
            } else if (!this.main.getTask().isDied()) {
                Entity n = this.main.getTask().getEnderDragon();
                if (n.getType() == EntityType.ENDER_DRAGON && n.isValid() && !n.isDead()) {
                    EnderDragon dragon = (EnderDragon) n;
                    int enragedHealth = 1000;
                    if (enragedHealth > 2000 || enragedHealth < 10)
                        enragedHealth = 2000 / 2;
                    if (dragon.getHealth() <= enragedHealth)
                        this.main.getTask().setCurrentDemonPhase(DemonPhase.ENRAGED);
                }
            }
        }
    }

    public Creeper spawnQuantumCreeper(Location l, @Nullable Creeper c) {
        if (c == null)
            c = (Creeper)l.getWorld().spawn(l, Creeper.class);
        c.setCustomName(main.format("&6Quantum Creeper"));
        addPersistentData((Entity)c, "quantum_creeper");
        c.setPowered(true);
        c.setInvisible(true);
        c.setExplosionRadius(8);
        return c;
    }

    public void addPersistentData(Entity entity, String id) {
        entity.getPersistentDataContainer().set(new NamespacedKey((Plugin)this.main, id), PersistentDataType.BYTE, Byte.valueOf((byte)1));
    }



    @EventHandler
    public void onBlock(PlayerMoveEvent e){
        if (e.getPlayer().getLocation().getWorld()!=main.endWorld)
            return;
        if (e.getPlayer().getLocation().getBlock().getRelative(BlockFace.DOWN).getType() != Material.BEDROCK)
            return;
        e.getPlayer().setVelocity(new Vector(0,2,0));
    }

    @EventHandler
    public void onSpawn(CreatureSpawnEvent e) {
        if (e.isCancelled())
            return;
        LivingEntity entity = e.getEntity();
        if (isInEnd(entity.getLocation())) {
            if (this.main.getTask() == null) {
                for (Entity n : e.getLocation().getWorld().getEntitiesByClass(EnderDragon.class)) {
                    if (n.isValid() && !n.isDead()) {
                        n.setCustomName(Main.format("&4&lHellDragon"));
                        ((LivingEntity)n).getAttribute(Attribute.GENERIC_MAX_HEALTH).setBaseValue(2000);
                        ((LivingEntity)n).setHealth(2000);
                        this.main.setTask(new EnderTicks((EnderDragon)n,this.main));
                        this.main.getTask().runTaskTimer((Plugin)this.main, 0L, 20L);
                    }
                }
            } else if (!this.main.getTask().isDied()) {
                Entity n = this.main.getTask().getEnderDragon();
                if (n.getType() == EntityType.ENDER_DRAGON && n.isValid() && !n.isDead()) {
                    EnderDragon dragon = (EnderDragon)n;
                    int enragedHealth = 1000;
                    if (enragedHealth > 2000 || enragedHealth < 10)
                        enragedHealth = 2000 / 2;
                    if (dragon.getHealth() <= enragedHealth)
                        this.main.getTask().setCurrentDemonPhase(DemonPhase.ENRAGED);
                }
            }
            if (!(entity instanceof Enderman))
                return;
            int cCP = 30;
            if (cCP < 1 || cCP > 1000)
                cCP = 10;
            int cGP = 70;
            if (cGP < 1 || cGP > 1000)
                cGP = 170;
            int creeperProb = this.random.nextInt(cCP) + 1;
            int ghastProb = this.random.nextInt(cGP) + 1;
            if (creeperProb == 1) {
                spawnQuantumCreeper(e.getLocation(),null);
                e.setCancelled(true);
            }
            if (ghastProb == 1) {
                this.spawnCustomGhast(e.getLocation(), CreatureSpawnEvent.SpawnReason.CUSTOM, true);
                e.setCancelled(true);
            }
            int removeProb = this.random.nextInt(100) + 1;
            if (removeProb <= 10)
                e.setCancelled(true);
        }
    }

    @EventHandler
    public void onDamage(EntityDamageByEntityEvent e){
        if (!(e.getEntity() instanceof Player))
            return;
        if (e.getEntity().getLocation().getWorld()!=main.endWorld)
            return;
        if (!(e.getDamager() instanceof Enderman))
            return;
        e.setDamage(20);
    }

    @EventHandler
    public void onDead(EntityDeathEvent e) {
        if (e.getEntity().getType() == EntityType.ENDER_DRAGON &&
                this.main.getTask() != null) {
            this.main.getTask().setDied(true);
            for (Player all : this.main.endWorld.getPlayers()){
                spawnFireworks(all.getLocation().add(0.0D, 1.0D, 0.0D), 1);
            }
            main.Adios=false;
        }
        LivingEntity livingEntity = e.getEntity();
        if (e.getEntity() instanceof Ghast && e.getEntity().getWorld()==main.endWorld) {
            e.getDrops().clear();
            e.setDroppedExp(0);
        }

    }


    public Entity spawnCustomGhast(Location location, CreatureSpawnEvent.SpawnReason reason, boolean isEnder) {
        WorldServer worldServer = ((CraftWorld)location.getWorld()).getHandle();
        CustomGhast nmsEntity = new CustomGhast(EntityTypes.GHAST, (net.minecraft.server.v1_16_R2.World) worldServer);
        nmsEntity.setPosition(location.getX(), location.getY(), location.getZ());
        worldServer.addEntity((net.minecraft.server.v1_16_R2.Entity) nmsEntity, reason);
        if (isEnder) {
            nmsEntity.getAttributeInstance(GenericAttributes.MAX_HEALTH).setValue(200.0D);
            LivingEntity e2 = (LivingEntity)nmsEntity.getBukkitEntity();
            e2.setHealth(200.0D);
            e2.setCustomName(main.format("&1Ender Ghast"));
            e2.getPersistentDataContainer().set(new NamespacedKey((Plugin)Main.getInstance(), "ender_ghast"), PersistentDataType.BYTE, Byte.valueOf((byte)1));
            e2.setCustomNameVisible(false);
        }
        return (nmsEntity == null) ? null : (Entity)nmsEntity.getBukkitEntity();
    }


    @EventHandler(priority = EventPriority.HIGHEST)
    public void onExplode(EntityExplodeEvent e) {
        Entity t = e.getEntity();
        if (isInEnd(t.getLocation()))
            if (e.getEntity().getType() == EntityType.ENDER_CRYSTAL && this.main.getTask() != null) {
                if (this.alreadyExploded.contains(e.getLocation()))
                    return;
                EnderCrystal c = (EnderCrystal)e.getEntity();
                if (c.getLocation().getBlock().getRelative(BlockFace.DOWN).getType() == Material.BEDROCK) {
                    int random = (new Random()).nextInt(this.main.getEndData().getTimeList().size());
                    this.main.getTask().getRegenTime().put(c.getLocation(), this.main.getEndData().getTimeList().get(random));
                    Location nL = e.getLocation().clone().add(0.0D, 10.0D, 0.0D);
                    Entity g = spawnCustomGhast(nL, CreatureSpawnEvent.SpawnReason.CUSTOM, true);
                    final Location loc = e.getLocation();
                    this.enderGhasts.add(g);
                    this.alreadyExploded.add(loc);
                    Bukkit.getScheduler().runTaskLater((Plugin)main, new Runnable() {
                        public void run() {
                            if (FinalDragon.this.alreadyExploded.contains(loc))
                                FinalDragon.this.alreadyExploded.remove(loc);
                        }
                    },  100L);
                    for (Player all : this.main.endWorld.getPlayers())
                        all.playSound(nL, Sound.ENTITY_WITHER_SPAWN, 100.0F, 100.0F);
                }
            }
        if (e.getEntity() instanceof TNTPrimed) {
            if (e.getEntity().getCustomName() == null)
                return;
            if (!e.getEntity().getCustomName().equalsIgnoreCase("dragontnt"))
                return;
            if (!e.blockList().isEmpty()) {
                Location egg = new Location(this.main.endWorld, 0.0D, 0.0D, 0.0D);
                Location withY = this.main.endWorld.getHighestBlockAt(egg).getLocation();
                if (e.getLocation().distance(withY) <= 10 ) {
                    e.blockList().clear();
                    e.setYield(0.0F);
                    return;
                }
                List<FallingBlock> fallingBlocks = new ArrayList<>();
                final List<Block> blockList = new ArrayList<>(e.blockList());
                for (Block b : blockList) {
                    float x = (float)(-0.2D + (float)(Math.random() * 0.6000000000000001D));
                    float y = -1.0F + (float)(Math.random() * 3.0D);
                    float z = (float)(-0.2D + (float)(Math.random() * 0.6000000000000001D));
                    if (b.getType() == Material.END_STONE || b.getType() == Material.END_STONE_BRICKS) {
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
    }




    public static void spawnFireworks(Location location, int amount) {
        Location loc = location;
        Firework fw = (Firework)loc.getWorld().spawnEntity(loc, EntityType.FIREWORK);
        FireworkMeta fwm = fw.getFireworkMeta();
        fwm.setPower(2);
        fwm.addEffect(FireworkEffect.builder().withColor(Color.LIME).flicker(true).build());
        fw.setFireworkMeta(fwm);
        fw.detonate();
        for (int i = 0; i < amount; i++) {
            Firework fw2 = (Firework)loc.getWorld().spawnEntity(loc, EntityType.FIREWORK);
            fw2.setFireworkMeta(fwm);
        }
    }




    private boolean isInDimencion(Location loc){
        return loc.getWorld()==main.endWorld;
    }
    private boolean isInEnd(Location loc){
        return loc.getWorld()==main.endWorld;
    }


}
