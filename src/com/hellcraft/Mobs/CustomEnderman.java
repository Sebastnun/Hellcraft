package com.hellcraft.Mobs;

import net.minecraft.server.v1_16_R2.EntityEnderman;
import net.minecraft.server.v1_16_R2.EntityTypes;
import net.minecraft.server.v1_16_R2.World;

public class CustomEnderman extends EntityEnderman {

    public CustomEnderman(EntityTypes<? extends EntityEnderman> type, World world) {
        super(type, world);
    }
}
