package com.hellcraft.Events.Final;

import com.hellcraft.Item.NebulaArmor;
import com.hellcraft.Item.RealNetherite;
import com.hellcraft.Item.SpecialItems;
import com.hellcraft.Main;
import de.tr7zw.nbtapi.NBTCompound;
import de.tr7zw.nbtapi.NBTItem;
import org.bukkit.*;
import org.bukkit.boss.BarColor;
import org.bukkit.boss.BarFlag;
import org.bukkit.boss.BarStyle;
import org.bukkit.boss.BossBar;
import org.bukkit.entity.*;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.event.entity.EntityDamageEvent;
import org.bukkit.event.entity.EntityDeathEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.inventory.meta.SkullMeta;
import org.bukkit.metadata.FixedMetadataValue;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;
import org.bukkit.projectiles.ProjectileSource;
import org.bukkit.scheduler.BukkitRunnable;
import org.bukkit.util.Vector;

import java.util.Arrays;
import java.util.Random;
import java.util.concurrent.ThreadLocalRandom;

public class Mobs implements Listener {

    private Main main = Main.getInstance();

    private BossBar bossSkeleton;

    private BossBar boosBlas;

    public boolean phase2;
    public boolean isPhase1;
    public boolean phase1;



    public void spawnEvilBlas(Location loc){
        ItemStack head = new ItemStack(Material.PLAYER_HEAD, 1); // Creating the ItemStack, your input may vary.
        NBTItem nbti = new NBTItem(head); // Creating the wrapper.

        NBTCompound skull = nbti.addCompound("SkullOwner"); // Getting the compound, that way we can set the skin information
        skull.setString("Name", "blasphemy_boi");
        //head = nbti.getItem();
        ItemStack cabeza = new ItemStack(Material.PLAYER_HEAD, 1);
        SkullMeta meta = (SkullMeta) cabeza.getItemMeta();
        meta.setOwner("blasphemy_boi");
        cabeza.setItemMeta((ItemMeta) meta);

        Zombie z = loc.getWorld().spawn(loc,Zombie.class);
        z.setSilent(true);
        z.setAdult();
        z.setCustomName(main.format("&4&l&oEvil Blas"));
        z.getEquipment().setHelmet(cabeza);
        z.getEquipment().setItemInOffHand(new ItemStack(Material.SHIELD));
        z.getEquipment().setChestplate(new NebulaArmor().NebulaChestplate());
        z.getEquipment().setLeggings(new NebulaArmor().NebulaLeggins());
        z.getEquipment().setBoots(new NebulaArmor().NebulaBoots());
        z.getEquipment().setItemInMainHand(new SpecialItems().EspadaDelBoos());
        z.getEquipment().setItemInMainHandDropChance(0);
        z.getEquipment().setHelmetDropChance(0);
        z.getEquipment().setChestplateDropChance(0);
        z.getEquipment().setLeggingsDropChance(0);
        z.getEquipment().setBootsDropChance(0);
        z.getEquipment().setItemInMainHandDropChance(0);
        z.setMaxHealth(1500);
        z.setHealth(1500);
        z.setMetadata("EvilBlas",new FixedMetadataValue(main, "evilblas"));
        z.setCustomNameVisible(true);
        for (Player p : Bukkit.getOnlinePlayers()){
            p.sendMessage(main.format("&4&lEvil Blas&f: Hola me Extrañaron"));
            p.sendMessage(main.format("&4&lEvil Blas&f: Bueno si El &6Rey&f no los vencio, yo me encargare de ustedes"));
        }
        creatorBossBar();

    }

    public void spawnSkeletoKing(Location loc){
        Skeleton s = loc.getWorld().spawn(loc,Skeleton.class);

        RealNetherite real = new RealNetherite();

        for (Player p :Bukkit.getOnlinePlayers()){
            p.sendMessage(main.format("&6&lSkeleton King&f: Hola de nuevo, me extrañaron?"));
        }

        s.setMaxHealth(1200);
        s.setHealth(1200);
        s.setGlowing(true);
        s.setCustomName(main.format("&6&l&oSkeleton King"));
        s.setMetadata("SkeletonKing", new FixedMetadataValue(main,"skeletonking"));
        s.getEquipment().setHelmet(real.HelmetNetherite());
        s.getEquipment().setChestplate(real.ChestplateNetherite());
        s.getEquipment().setLeggings(real.LeggingsNetherite());
        s.getEquipment().setBoots(real.BootsNetherite());
        s.getEquipment().setItemInMainHand(new SpecialItems().EspadaDelBoos());
        s.getEquipment().setItemInMainHandDropChance(0);
        s.getEquipment().setHelmetDropChance(0);
        s.getEquipment().setChestplateDropChance(0);
        s.getEquipment().setLeggingsDropChance(0);
        s.getEquipment().setBootsDropChance(0);
        creatorBossBar();

    }

    public void spawnHellWither(Location loc){
        Wither w = loc.getWorld().spawn(loc,Wither.class);

        for (Player p : Bukkit.getOnlinePlayers()){
            p.sendMessage(main.format("&4&lHellWither&f: Hola de nuevo"));
            p.sendMessage(main.format("&4&lHellWither&f: Hace cuantas semanas que no nos vemos"));
            p.sendMessage(main.format("&4&lHellWither&f: Como unas 4"));
        }

        w.setCustomName(main.format("&c&lHellWither"));
        w.setMaxHealth(1000);
        w.setHealth(1000);
        w.setCustomNameVisible(true);
        w.setFallDistance(100);
        w.setMetadata("HellWither",new FixedMetadataValue(main,"hellwither"));
        w.setVelocity(new Vector(2.5,0,2.5));
    }

    //Names Tag
    public ItemStack WitherTag(){
        ItemStack i = new ItemStack(Material.NAME_TAG);
        ItemMeta meta = i.getItemMeta();
        meta.setDisplayName(main.format("&c&lHellWither"));
        i.setItemMeta(meta);
        return i;
    }

    public ItemStack EviLBlasTag(){
        ItemStack i = new ItemStack(Material.NAME_TAG);
        ItemMeta meta = i.getItemMeta();
        meta.setDisplayName(main.format("&4&l&oEvil Blas"));
        i.setItemMeta(meta);
        return i;
    }

    public ItemStack SkeletonKingTag(){
        ItemStack i = new ItemStack(Material.NAME_TAG);
        ItemMeta meta = i.getItemMeta();
        meta.setDisplayName(main.format("&6&l&oSkeleton King"));
        i.setItemMeta(meta);
        return i;
    }
    public ItemStack SkeletonDemonTag(){
        ItemStack i = new ItemStack(Material.NAME_TAG);
        ItemMeta meta = i.getItemMeta();
        meta.setDisplayName(main.format("&4&l&oSkeleton Demon"));
        i.setItemMeta(meta);
        return i;
    }


    //Creator Boss Bar
    private void creatorBossBar(){
        if (bossSkeleton==null && main.SkeletonKing){
            bossSkeleton = Bukkit.createBossBar(main.format("&6&l&oSkeleton King"), BarColor.YELLOW, BarStyle.SOLID, BarFlag.PLAY_BOSS_MUSIC);
            for (Player p : Bukkit.getOnlinePlayers()){
                if (p.getWorld() == main.BossDimencion){
                    bossSkeleton.addPlayer(p);
                }
            }
            bossSkeleton.setVisible(true);
        }else if(boosBlas == null && main.EvilBlas){
            boosBlas = Bukkit.createBossBar(main.format("&4&l&oEvil Blas"),BarColor.RED,BarStyle.SOLID,BarFlag.PLAY_BOSS_MUSIC);
            for (Player p : Bukkit.getOnlinePlayers()){
                if (p.getWorld() == main.BossDimencion){
                    boosBlas.addPlayer(p);
                }
            }
            boosBlas.setVisible(true);

        }
        UpdateBossBar();
    }

    //Update Boss Bar
    private void UpdateBossBar(){
        if (main.SkeletonKing){
            for (Entity en : main.BossDimencion.getEntities()){
                if (en instanceof Skeleton){
                    if (en.hasMetadata("SkeletonKing")){
                        Skeleton e = (Skeleton) en;
                        new BukkitRunnable() {
                            @Override
                            public void run() {
                                if (!Bukkit.getOnlinePlayers().isEmpty()){
                                    for (Player p : Bukkit.getOnlinePlayers()){
                                        if (p.getWorld()==main.BossDimencion){
                                            bossSkeleton.addPlayer(p);
                                        }
                                    }
                                }
                                bossSkeleton.setProgress(e.getHealth()/1200);
                                if (e.getHealth()<=600&&!isPhase1){
                                    e.setHealth(900);
                                    for (Player p :Bukkit.getOnlinePlayers()){
                                        p.sendMessage(main.format("&6&lSkeleton King&f: Vamos &4&lHellDragon &fdame el poder que necesito"));
                                    }
                                    isPhase1=true;
                                    bossSkeleton.setTitle(main.format("&4&l&oSkeleton Demon"));
                                }
                                if (bossSkeleton.getProgress()<=0.0){
                                    bossSkeleton.removeAll();
                                    bossSkeleton = null;
                                    main.SkeletonKing = false;
                                }
                                if (!main.SkeletonKing){
                                    cancel();
                                }
                            }
                        }.runTaskTimer(main,0, 5);
                    }
                }
            }
        }else if (main.EvilBlas){
            for (Entity en : main.BossDimencion.getEntities()){
                if (en instanceof Zombie){
                    if (en.hasMetadata("EvilBlas")){
                        Zombie e = (Zombie)en;
                        new BukkitRunnable() {
                            @Override
                            public void run() {
                                if (!Bukkit.getOnlinePlayers().isEmpty()){
                                    for (Player p : Bukkit.getOnlinePlayers()){
                                        if (p.getWorld()==main.BossDimencion){
                                            boosBlas.addPlayer(p);
                                        }
                                    }
                                }
                                boosBlas.setProgress(e.getHealth()/1500);
                                if (e.getHealth()<=1000&&!phase1){
                                    for (Player p : Bukkit.getOnlinePlayers()){
                                        p.sendMessage(main.format("&4&lEvil Blas&f: De verdad creen vencerme"));
                                        p.sendMessage(main.format("&4&lEvil Blas&f: Por favor"));
                                        p.sendMessage(main.format("&4&lEvil Blas&f: Si la ultima vez tuvieron que usar /kill"));
                                        p.sendMessage(main.format("&4&lEvil Blas&f: Para poderme hacer algo de daño"));
                                    }
                                    phase1=true;
                                }
                                if (e.getHealth()<=500&&!phase2){
                                    e.setHealth(700);
                                    for (Player p : Bukkit.getOnlinePlayers()){
                                        p.sendMessage(main.format("&4&lEvil Blas&f: Wow han mejorado"));
                                        p.sendMessage(main.format("&4&lEvil Blas&f: Desde la ultima ves que los venci"));
                                    }
                                    phase2=true;
                                }
                                if (boosBlas.getProgress()<=0.0){
                                    boosBlas.removeAll();
                                    boosBlas = null;
                                    main.EvilBlas=false;
                                }
                                if (!main.EvilBlas){
                                    cancel();
                                }
                            }
                        }.runTaskTimer(main,0, 5);
                    }
                }
            }
        }
    }

    @EventHandler
    public void DamageEvilBlas(EntityDamageByEntityEvent e){
        if (!main.EvilBlas)
            return;
        Random random = new Random();
        if (e.getEntity() instanceof Zombie && e.getDamager() instanceof Player){
            if (e.getEntity().hasMetadata("EvilBlas")){
                Player player = (Player) e.getDamager();
                int ran = random.nextInt(15);
                if (ran<=10){
                    e.setCancelled(true);
                    player.sendMessage(main.format("&6JAJA tu ataque ha sido bloqueado"));
                    player.playSound(player.getLocation(), Sound.ITEM_SHIELD_BLOCK,5,5);
                }
            }
        }
    }

    @EventHandler
    public void onDeath(EntityDeathEvent e){
        if (e.getEntity() instanceof Zombie){
            if (e.getEntity().hasMetadata("EvilBlas")){
                for (Player p : Bukkit.getOnlinePlayers()){
                    p.sendMessage(main.format("&4&lEvil Blas&f: No como me han podido vencer"));
                    p.sendMessage(main.format("&4&lEvil Blas&f: Juro que no podran vencer al &4&LHellDragon"));
                }
                e.getDrops().clear();
                e.getDrops().add(FinalEye(4));
            }
        }
        if (e.getEntity() instanceof Skeleton){
            if (e.getEntity().hasMetadata("SkeletonKing")){
                for (Player p : Bukkit.getOnlinePlayers()){
                    p.sendMessage(main.format("&6&lSkeleton King&f: No como me han podido vencer"));
                    p.sendMessage(main.format("&6&lSkeleton King&f: &cEvil Blas &fayudame"));
                }
                e.getDrops().clear();
                e.getDrops().add(FinalEye(4));
            }
        }
        if (e.getEntity() instanceof Wither){
            if (e.getEntity().hasMetadata("HellWither")){
                for (Player p : Bukkit.getOnlinePlayers()){
                    p.sendMessage(main.format("&4&lHellWither&f: &6Skeleton King &fencargate tu"));
                }
                e.getDrops().clear();
                e.getDrops().add(FinalEye(4));
            }
        }

    }

    public ItemStack FinalEye(int amount){
        ItemStack i = new ItemStack(Material.ENDER_EYE,amount);
        ItemMeta meta = i.getItemMeta();
        meta.setDisplayName(main.format("&4&lOjo del Dragon"));
        meta.setLore(Arrays.asList(main.format("&6Usa este ojo"),main.format("&6Para abrir el portal")));
        i.setItemMeta(meta);

        return i;
    }

    @EventHandler
    public void onArrow(EntityDamageByEntityEvent e){
        if (!main.EvilBlas)
            return;
        if (!(e.getEntity() instanceof Zombie))
            return;
        if (!e.getEntity().hasMetadata("EvilBlas"))
            return;
        if (e.getCause() != EntityDamageEvent.DamageCause.PROJECTILE)
            return;
        ProjectileSource shooter = ((Projectile) e.getDamager()).getShooter();
        if(shooter instanceof Player) {
            e.setCancelled(true);
            ((Player) shooter).playSound(((Player) shooter).getLocation(),Sound.ITEM_SHIELD_BLOCK,5,5);
        }
    }

    @EventHandler
    public void onDamage(EntityDamageByEntityEvent e){
        if (!main.SkeletonKing)
            return;
        if(e.getEntity() instanceof Skeleton && e.getDamager() instanceof Player){
            if(e.getEntity().hasMetadata("SkeletonKing")){
                int random = ThreadLocalRandom.current().nextInt(10);
                if(random<5){ //0 1 2 3 4
                    e.setCancelled(true);
                    Player player = (Player)e.getDamager();
                    player.playSound(player.getLocation(), Sound.ITEM_SHIELD_BLOCK,10,10);
                    player.sendMessage("§c§lTU ATAQUE HA SIDO BLOQUEADO");
                }
            }
        }
        if(e.getDamager() instanceof Skeleton && e.getEntity() instanceof Player){
            if(e.getDamager().hasMetadata("SkeletonKing")){
                int random = ThreadLocalRandom.current().nextInt(10);
                if(random<5){
                    e.setCancelled(true);
                    Player player = (Player)e.getEntity();
                    player.setVelocity(new Vector(0,2,0));
                    player.addPotionEffect(new PotionEffect(PotionEffectType.BLINDNESS,60,2));
                    player.sendMessage("§4§lVETE!");
                }
            }
        }
    }







}
