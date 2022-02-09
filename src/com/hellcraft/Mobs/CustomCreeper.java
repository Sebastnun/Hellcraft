package com.hellcraft.Mobs;

import net.minecraft.server.v1_16_R2.*;

import java.lang.reflect.Field;
import java.util.EnumMap;
import java.util.EnumSet;
import java.util.LinkedHashSet;

public class CustomCreeper extends EntityCreeper {
    private boolean isEnderCreeper;

    public CustomCreeper(EntityTypes<? extends EntityCreeper> type, World world, boolean isE) {
        super(type, world);
        PathfinderGoalSelector goalSelector = this.goalSelector;
        PathfinderGoalSelector targetSelector = this.targetSelector;
        try {
            Field dField = PathfinderGoalSelector.class.getDeclaredField("d");
            dField.setAccessible(true);
            dField.set(goalSelector, new LinkedHashSet());
            Field cField = PathfinderGoalSelector.class.getDeclaredField("c");
            cField.setAccessible(true);
            cField.set(goalSelector, new EnumMap<>(PathfinderGoal.Type.class));
            Field fField = PathfinderGoalSelector.class.getDeclaredField("f");
            fField.setAccessible(true);
            fField.set(goalSelector, EnumSet.noneOf(PathfinderGoal.Type.class));
        } catch (NoSuchFieldException|SecurityException|IllegalArgumentException|IllegalAccessException e) {
            e.printStackTrace();
        }
        try {
            Field dField = PathfinderGoalSelector.class.getDeclaredField("d");
            dField.setAccessible(true);
            dField.set(targetSelector, new LinkedHashSet());
            Field cField = PathfinderGoalSelector.class.getDeclaredField("c");
            cField.setAccessible(true);
            cField.set(targetSelector, new EnumMap<>(PathfinderGoal.Type.class));
            Field fField = PathfinderGoalSelector.class.getDeclaredField("f");
            fField.setAccessible(true);
            fField.set(targetSelector, EnumSet.noneOf(PathfinderGoal.Type.class));
        } catch (NoSuchFieldException|SecurityException|IllegalArgumentException|IllegalAccessException e) {
            e.printStackTrace();
        }
        setNoAI(false);
        this.goalSelector.a(1, (PathfinderGoal)new PathfinderGoalFloat((EntityInsentient)this));
        this.goalSelector.a(2, (PathfinderGoal)new PathfinderGoalSwell(this));
        this.goalSelector.a(3, (PathfinderGoal)new PathfinderGoalMeleeAttack((EntityCreature)this, 1.0D, false));
        this.goalSelector.a(4, (PathfinderGoal)new PathfinderGoalRandomStrollLand((EntityCreature)this, 0.8D));
        this.goalSelector.a(5, (PathfinderGoal)new PathfinderGoalLookAtPlayer((EntityInsentient)this, EntityHuman.class, 8.0F));
        this.goalSelector.a(5, (PathfinderGoal)new PathfinderGoalRandomLookaround((EntityInsentient)this));
        this.targetSelector.a(1, (PathfinderGoal)new PathfinderGoalNearestAttackableTarget((EntityInsentient)this, EntityHuman.class, true));
        this.targetSelector.a(2, (PathfinderGoal)new PathfinderGoalHurtByTarget((EntityCreature)this, new Class[0]));
        this.isEnderCreeper = isE;
    }

    public boolean damageEntity(DamageSource damagesource, float f) {
        if (isInvulnerable(damagesource))
            return false;
        if (!(damagesource instanceof net.minecraft.server.v1_16_R2.EntityDamageSourceIndirect)) {
            boolean flag = super.damageEntity(damagesource, f);
            if (!this.world.s_() && this.random.nextInt(10) != 0)
                if (this.isEnderCreeper)
                    eM();
            return flag;
        }
        for (int i = 0; i < 64; i++) {
            if (eM())
                return true;
        }
        return false;
    }

    protected boolean eM() {
        if (!this.world.s_() && isAlive()) {
            double d0 = locX() + (this.random.nextDouble() - 0.5D) * 64.0D;
            double d1 = locY() + (this.random.nextInt(64) - 32);
            double d2 = locZ() + (this.random.nextDouble() - 0.5D) * 64.0D;
            return o(d0, d1, d2);
        }
        return false;
    }

    private boolean o(double d0, double d1, double d2) {
        BlockPosition.MutableBlockPosition blockposition_mutableblockposition = new BlockPosition.MutableBlockPosition(d0, d1, d2);
        while (blockposition_mutableblockposition.getY() > 0 && !this.world.getType((BlockPosition)blockposition_mutableblockposition).getMaterial().isSolid())
            blockposition_mutableblockposition.c(EnumDirection.DOWN);
        IBlockData iblockdata = this.world.getType((BlockPosition)blockposition_mutableblockposition);
        boolean flag = iblockdata.getMaterial().isSolid();
        if (flag)
            return a(d0, d1, d2, true);
        return false;
    }
}