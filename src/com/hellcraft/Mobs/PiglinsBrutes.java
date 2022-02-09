package com.hellcraft.Mobs;

import net.minecraft.server.v1_16_R2.*;
import org.bukkit.Location;
import org.bukkit.craftbukkit.v1_16_R2.CraftWorld;
import com.hellcraft.Main;

public class PiglinsBrutes extends EntityPiglin{
    private Main main = Main.getInstance();
    public PiglinsBrutes(Location loc) {
        super(EntityTypes.PIGLIN_BRUTE, ((CraftWorld)loc.getWorld()).getHandle());
            this.setPosition(loc.getX(), loc.getY(),loc.getZ());
            this.setCustomName(new ChatComponentText(main.format("&cEsqueleto Rey")));
            this.setCustomNameVisible(true);
            this.setAggressive(true);

    }

}
