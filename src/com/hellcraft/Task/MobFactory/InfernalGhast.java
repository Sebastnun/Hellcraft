package com.hellcraft.Task.MobFactory;

import net.minecraft.server.v1_16_R2.*;
import org.bukkit.Location;
import org.bukkit.craftbukkit.v1_16_R2.CraftWorld;


public class InfernalGhast extends EntityGhast {


    private com.hellcraft.Main main = com.hellcraft.Main.getInstance();

    public InfernalGhast(Location loc){
        super(EntityTypes.GHAST, ((CraftWorld)loc.getWorld()).getHandle());

        this.setPosition(loc.getX(),loc.getY(),loc.getZ());
        this.setAggressive(true);
        IChatBaseComponent name = new ChatComponentText(main.format("&6&lInfernal Ghast"));
        this.setCustomName(name);
        this.setHealth(200.0f);
        this.setInvisible(true);
    }


    public void initPathFinders(){
        super.initPathfinder();
        this.goalSelector.a(1,new PathfinderGoalNearestAttackableTarget<EntityHuman>(this,EntityHuman.class, true));
    }



}
