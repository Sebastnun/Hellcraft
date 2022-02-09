package com.hellcraft.Task.MobFactory;

import net.minecraft.server.v1_16_R2.*;
import org.bukkit.Location;
import org.bukkit.craftbukkit.v1_16_R2.CraftWorld;
import org.bukkit.entity.EntityType;




public class Creeper extends EntityCreeper {

    private com.hellcraft.Main main = com.hellcraft.Main.getInstance();

    public Creeper(Location loc){
        super(EntityTypes.CREEPER,((CraftWorld)loc.getWorld()).getHandle());

        this.setAggressive(true);
        IChatBaseComponent name = new ChatComponentText(main.format("&6&lInfernal Creeper"));
        this.setCustomName(name);
        this.setHealth(50.0f);
        this.setPowered(true);
        this.setInvisible(true);
        this.setSilent(true);
    }

    public void initPathFinders(){
        super.initPathfinder();
        this.goalSelector.a(1,new PathfinderGoalNearestAttackableTarget<EntityHuman>(this,EntityHuman.class, true));
    }
}
