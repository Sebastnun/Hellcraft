package com.hellcraft.Task.MobFactory;

import com.hellcraft.Item.NebulaArmor;
import com.hellcraft.Item.SpecialItems;
import net.minecraft.server.v1_16_R2.*;
import org.bukkit.Location;
import org.bukkit.craftbukkit.v1_16_R2.CraftWorld;
import org.bukkit.craftbukkit.v1_16_R2.inventory.CraftItemStack;
import org.bukkit.entity.Item;
import org.bukkit.entity.Spider;
import org.bukkit.event.Listener;

import java.util.Arrays;
import java.util.List;
import java.util.Random;

public class InfernalSkeleton extends EntitySkeletonWither {

    private com.hellcraft.Main main = com.hellcraft.Main.getInstance();

    public InfernalSkeleton(Location loc, String s, org.bukkit.inventory.ItemStack i){
        super(EntityTypes.WITHER_SKELETON, ((CraftWorld)loc.getWorld()).getHandle());

        ItemStack nmsItem = CraftItemStack.asNMSCopy(i);
        ItemStack helmet = CraftItemStack.asNMSCopy(new NebulaArmor().NebulaHelmet());
        ItemStack chest = CraftItemStack.asNMSCopy(new NebulaArmor().NebulaChestplate());
        ItemStack leg = CraftItemStack.asNMSCopy(new NebulaArmor().NebulaLeggins());
        ItemStack boots = CraftItemStack.asNMSCopy(new NebulaArmor().NebulaBoots());

        this.setPosition(loc.getX(),loc.getY(),loc.getZ());
        this.setAggressive(true);
        IChatBaseComponent name = new ChatComponentText(main.format(s));
        this.setCustomName(name);
        this.setHealth(70.0f);
        this.setSlot(EnumItemSlot.MAINHAND, nmsItem);
        this.setSlot(EnumItemSlot.HEAD, helmet);
        this.setSlot(EnumItemSlot.CHEST, chest);
        this.setSlot(EnumItemSlot.LEGS, leg);
        this.setSlot(EnumItemSlot.FEET, boots);

    }

    public void initPathFinders(){
        super.initPathfinder();
        this.goalSelector.a(1,new PathfinderGoalNearestAttackableTarget<EntityHuman>(this,EntityHuman.class, true));
    }


}
