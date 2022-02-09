package com.hellcraft.Events.Final.Util;

import com.hellcraft.Main;
import org.bukkit.Color;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.Particle;
import org.bukkit.entity.*;
import org.bukkit.plugin.Plugin;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;
import org.bukkit.scheduler.BukkitRunnable;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.SplittableRandom;

public class EnderTicks extends BukkitRunnable {

    private Map<Location, Integer> regenTime = new HashMap<>();

    private Location teleportLocation;

    private DemonCurrentAttack currentAttack = DemonCurrentAttack.NONE;

    private DemonPhase currentDemonPhase = DemonPhase.NORMAL;

    private MovesTask currentMovesTask = null;

    private EnderDragon enderDragon;

    private Main main;

    private int timeForTnT = 30;

    private int nextDragonAttack = 20;

    private int lightingDuration = 5;

    private int nightVisionDuration = 5;

    private int timeForEnd360 = 20;

    private boolean nightVision = false;

    private boolean isDied;

    private boolean attack360 = false;

    private boolean lightingRain = false;

    private boolean canMakeAnAttack = true;

    private boolean decided = false;

    private Location eggLocation;

    private SplittableRandom random = new SplittableRandom();

    public EnderTicks(EnderDragon enderDragon, Main main){
        this.main = main;
        this.enderDragon = enderDragon;
        int y = this.main.endWorld.getMaxHeight() - 1;
        while (y > 0 && this.main.endWorld.getBlockAt(0, y, 0).getType() != Material.BEDROCK)
            y--;
        this.eggLocation = this.main.endWorld.getHighestBlockAt(new Location(this.main.endWorld, 0.0D, y, 0.0D)).getLocation();
        this.teleportLocation = this.eggLocation.clone().add(0.0D, 2.0D, 0.0D);
        this.teleportLocation.setPitch(enderDragon.getLocation().getPitch());
    }


    @Override
    public void run(){
        if (this.isDied || this.enderDragon.isDead()) {
            this.main.setTask(null);
            cancel();
            return;
        }
        tickTnTAttack();
        tickLightingRain();
        tickNightVision();
        tick360Attack();
        tickDemonPhase();
        tickRandomLighting();
        tickEnderCrystals();
        tickDragonAttacks();
    }

    private void tickEnderCrystals() {
        if (!this.regenTime.isEmpty())
            for (Location loc : this.regenTime.keySet()) {
                int time = ((Integer)this.regenTime.get(loc)).intValue();
                if (time >= 1) {
                    this.regenTime.replace(loc, Integer.valueOf(time), Integer.valueOf(time - 1));
                    continue;
                }
                loc.getWorld().spawnEntity(loc, EntityType.ENDER_CRYSTAL);
                this.regenTime.remove(loc);
                if (loc.getWorld().getBlockAt(loc) != null) {
                    if (loc.getWorld().getBlockAt(loc).getType() == Material.BEDROCK || loc.getWorld().getBlockAt(loc).getType() == Material.AIR)
                        return;
                    loc.getWorld().getBlockAt(loc).setType(Material.AIR);
                }
            }
    }

    private void tickRandomLighting() {
        int x = (this.random.nextBoolean() ? 1 : -1) * this.random.nextInt(21);
        int z = (this.random.nextBoolean() ? 1 : -1) * this.random.nextInt(21);
        int y = this.main.endWorld.getHighestBlockYAt(x, z);
        if (y < 0)
            return;
        this.main.endWorld.strikeLightning(new Location(this.main.endWorld, x, y, z));
    }




    private void tickDemonPhase() {
        if (this.currentDemonPhase == DemonPhase.ENRAGED) {
            EnderDragon dragon = this.enderDragon;
            dragon.addPotionEffect(new PotionEffect(PotionEffectType.INCREASE_DAMAGE, 2147483647, 7));
            dragon.setCustomName(Main.format("&4&lHellDemon"));
        } else {
            EnderDragon dragon = this.enderDragon;
            dragon.addPotionEffect(new PotionEffect(PotionEffectType.INCREASE_DAMAGE, 2147483647, 5));
        }
    }

    private void tickTnTAttack() {
        this.timeForTnT--;
        if (this.timeForTnT == 0) {
            if (this.enderDragon.getPhase() != EnderDragon.Phase.DYING && !this.attack360 && this.enderDragon.getLocation().distance(this.eggLocation) >= 15.0D) {
                TNTPrimed tnt1 = (TNTPrimed)this.enderDragon.getWorld().spawnEntity(this.enderDragon.getLocation().add(3.0D, 0.0D, -3.0D), EntityType.PRIMED_TNT);
                tnt1.setFuseTicks(60);
                tnt1.setYield(tnt1.getYield() * 2.0F);
                tnt1.setCustomName("dragontnt");
                tnt1.setCustomNameVisible(false);
                TNTPrimed tnt2 = (TNTPrimed)this.enderDragon.getWorld().spawnEntity(this.enderDragon.getLocation().add(3.0D, 0.0D, 3.0D), EntityType.PRIMED_TNT);
                tnt2.setFuseTicks(60);
                tnt2.setYield(tnt2.getYield() * 2.0F);
                tnt2.setCustomName("dragontnt");
                tnt2.setCustomNameVisible(false);
                TNTPrimed tnt3 = (TNTPrimed)this.enderDragon.getWorld().spawnEntity(this.enderDragon.getLocation().add(3.0D, 0.0D, 0.0D), EntityType.PRIMED_TNT);
                tnt3.setFuseTicks(60);
                tnt3.setYield(tnt3.getYield() * 2.0F);
                tnt3.setCustomName("dragontnt");
                tnt3.setCustomNameVisible(false);
                TNTPrimed tnt4 = (TNTPrimed)this.enderDragon.getWorld().spawnEntity(this.enderDragon.getLocation().add(-3.0D, 0.0D, 3.0D), EntityType.PRIMED_TNT);
                tnt4.setFuseTicks(60);
                tnt4.setYield(tnt4.getYield() * 2.0F);
                tnt4.setCustomName("dragontnt");
                tnt4.setCustomNameVisible(false);
                TNTPrimed tnt5 = (TNTPrimed)this.enderDragon.getWorld().spawnEntity(this.enderDragon.getLocation().add(-3.0D, 0.0D, -3.0D), EntityType.PRIMED_TNT);
                tnt5.setFuseTicks(60);
                tnt5.setYield(tnt5.getYield() * 2.0F);
                tnt5.setCustomName("dragontnt");
                tnt5.setCustomNameVisible(false);
                TNTPrimed tnt6 = (TNTPrimed)this.enderDragon.getWorld().spawnEntity(this.enderDragon.getLocation().add(-3.0D, 0.0D, 0.0D), EntityType.PRIMED_TNT);
                tnt6.setFuseTicks(60);
                tnt6.setYield(tnt6.getYield() * 2.0F);
                tnt6.setCustomName("dragontnt");
                tnt6.setCustomNameVisible(false);
            }
            this.timeForTnT = 30 + this.random.nextInt(61);
        }
    }

    private void tick360Attack() {
        if (this.enderDragon.getLocation().distance(this.eggLocation) >= 10.0D && this.decided)
            this.decided = false;
        if (this.enderDragon.getLocation().distance(this.eggLocation) <= 3.0D && !this.decided) {
            this.decided = true;
            this.enderDragon.setRotation(this.enderDragon.getLocation().getPitch(), 0.0F);
            if (this.random.nextInt(10) <= 7)
                start360attack();
        }
        if (this.attack360) {
            this.canMakeAnAttack = false;
            if (this.timeForEnd360 >= 1)
                this.timeForEnd360--;
            if (this.timeForEnd360 >= 16) {
                EnderDragon dragon = this.enderDragon;
                if (dragon.getPhase() != EnderDragon.Phase.LAND_ON_PORTAL)
                    dragon.setPhase(EnderDragon.Phase.LAND_ON_PORTAL);
                dragon.teleport(this.teleportLocation);
            }
            if (this.timeForEnd360 == 15) {
                this.currentMovesTask = new MovesTask(this.main, this.enderDragon, this.teleportLocation);
                this.currentMovesTask.runTaskTimer((Plugin)this.main, 5L, 5L);
            }
            if (this.timeForEnd360 == 0) {
                if (this.currentMovesTask != null) {
                    this.currentMovesTask.cancel();
                    this.currentMovesTask = null;
                }
                this.canMakeAnAttack = true;
                this.timeForEnd360 = 20;
                this.attack360 = false;
                this.enderDragon.setPhase(EnderDragon.Phase.LEAVE_PORTAL);
            }
        }
    }

    private void tickDragonAttacks() {
        if (this.nextDragonAttack >= 1) {
            this.nextDragonAttack--;
        } else if (this.nextDragonAttack == 0) {
            if (getCurrentDemonPhase() == DemonPhase.NORMAL) {
                this.nextDragonAttack = 60;
            } else {
                this.nextDragonAttack = 40;
            }
            if (this.canMakeAnAttack) {
                chooseAnAttack();
            } else {
                this.currentAttack = DemonCurrentAttack.NONE;
            }
            if (this.currentAttack == DemonCurrentAttack.NONE)
                return;
            if (this.currentAttack == DemonCurrentAttack.ENDERMAN_BUFF) {
                int endermanschoosed = 0;
                ArrayList<Enderman> endermen = new ArrayList<>();
                for (Enderman man : this.main.endWorld.getEntitiesByClass(Enderman.class)) {
                    Location backUp = man.getLocation();
                    backUp.setY(0.0D);
                    if (this.eggLocation.distance(backUp) <= 35.0D &&
                            endermanschoosed < 4) {
                        endermanschoosed++;
                        endermen.add(man);
                    }
                }
                if (!endermen.isEmpty())
                    for (Enderman mans : endermen) {
                        AreaEffectCloud a = (AreaEffectCloud)this.main.endWorld.spawnEntity(this.main.endWorld.getHighestBlockAt(mans.getLocation()).getLocation().add(0.0D, 1.0D, 0.0D), EntityType.AREA_EFFECT_CLOUD);
                        a.setRadius(10.0F);
                        a.setParticle(Particle.VILLAGER_HAPPY);
                        a.setColor(Color.GREEN);
                        a.addCustomEffect(new PotionEffect(PotionEffectType.REGENERATION, 999999, 0), false);
                        mans.setInvulnerable(true);
                    }
            } else if (this.currentAttack == DemonCurrentAttack.LIGHTING_RAIN) {
                this.lightingRain = true;
                this.lightingDuration = 5;
            } else if (this.currentAttack == DemonCurrentAttack.NIGHT_VISION) {
                this.nightVision = true;
                this.nightVisionDuration = 5;
                for (Player all : this.main.endWorld.getPlayers())
                    all.addPotionEffect(new PotionEffect(PotionEffectType.NIGHT_VISION, 140, 0));
            }
        }
    }


    private void tickLightingRain() {
        if (this.lightingRain)
            if (this.lightingDuration >= 1) {
                this.canMakeAnAttack = false;
                this.lightingDuration--;
                for (Player all : this.main.endWorld.getPlayers()) {
                    this.main.endWorld.strikeLightning(all.getLocation());
                    if (this.currentDemonPhase == DemonPhase.ENRAGED)
                        all.damage(1.0D);
                }
            } else {
                this.lightingRain = false;
                this.lightingDuration = 5;
                this.canMakeAnAttack = true;
            }
    }

    private void tickNightVision() {
        if (this.nightVision)
            if (this.nightVisionDuration >= 1) {
                this.nightVisionDuration--;
            } else {
                for (Player all : this.main.endWorld.getPlayers()) {
                    if (this.currentDemonPhase == DemonPhase.NORMAL) {
                        Location location = this.main.endWorld.getHighestBlockAt(all.getLocation()).getLocation().add(0.0D, 1.0D, 0.0D);
                        AreaEffectCloud areaEffectCloud = (AreaEffectCloud)this.main.endWorld.spawnEntity(location, EntityType.AREA_EFFECT_CLOUD);
                        areaEffectCloud.setParticle(Particle.DAMAGE_INDICATOR);
                        areaEffectCloud.addCustomEffect(new PotionEffect(PotionEffectType.HARM, 100, 1), false);
                        areaEffectCloud.setRadius(3.0F);
                        continue;
                    }
                    Location highest = this.main.endWorld.getHighestBlockAt(all.getLocation()).getLocation();
                    AreaEffectCloud eff = (AreaEffectCloud)this.main.endWorld.spawnEntity(highest, EntityType.AREA_EFFECT_CLOUD);
                    eff.setParticle(Particle.DAMAGE_INDICATOR);
                    eff.addCustomEffect(new PotionEffect(PotionEffectType.HARM, 100, 1), false);
                    eff.setRadius(3.0F);
                }
                this.nightVision = false;
                this.canMakeAnAttack = true;
            }
    }

    public void chooseAnAttack() {
        int ran = this.random.nextInt(25);
        if (ran <= 3) {
            this.currentAttack = DemonCurrentAttack.LIGHTING_RAIN;
        } else if (ran >= 4 && ran <= 15) {
            this.currentAttack = DemonCurrentAttack.ENDERMAN_BUFF;
        } else if (ran >= 15 && ran <= 25) {
            this.currentAttack = DemonCurrentAttack.NIGHT_VISION;
        }
    }


    public Map<Location, Integer> getRegenTime() {
        return this.regenTime;
    }

    public void setDied(boolean died) {
        this.isDied = died;
    }

    public Entity getEnderDragon() {
        return (Entity)this.enderDragon;
    }

    public boolean isDied() {
        return this.isDied;
    }

    public Main getMain() {
        return this.main;
    }

    public void start360attack() {
        this.attack360 = true;
    }

    public DemonPhase getCurrentDemonPhase() {
        return this.currentDemonPhase;
    }

    public void setCurrentDemonPhase(DemonPhase currentDemonPhase) {
        this.currentDemonPhase = currentDemonPhase;
    }
}
