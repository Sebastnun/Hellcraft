package com.hellcraft.Task.MobFactory;

import net.minecraft.server.v1_16_R2.*;
import org.bukkit.Location;
import org.bukkit.craftbukkit.v1_16_R2.CraftWorld;

public class Test extends EntitySkeletonWither {

    public Test(Location loc){
        super(EntityTypes.WITHER_SKELETON,((CraftWorld)loc.getWorld()).getHandle());

        this.setAggressive(true);
        this.setCustomName(new ChatComponentText("Test"));
    }


}
