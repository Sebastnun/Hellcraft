package com.hellcraft.Item;

import com.hellcraft.Main;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.attribute.Attribute;
import org.bukkit.attribute.AttributeModifier;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.entity.Skeleton;
import org.bukkit.event.Listener;
import org.bukkit.inventory.EquipmentSlot;
import org.bukkit.inventory.ItemFlag;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.metadata.FixedMetadataValue;

import java.util.Arrays;
import java.util.UUID;

public class SpecialItems implements Listener {

    private Main main = Main.getInstance();


    public ItemStack LighningStick(){
        ItemStack stick = new ItemStack(Material.STICK);
        ItemMeta meta = stick.getItemMeta();
        meta.setUnbreakable(true);
        meta.setDisplayName(main.format("&bLightning Stick"));
        meta.addEnchant(Enchantment.MENDING,1, true);
        stick.setItemMeta(meta);
        return stick;
    }
    public ItemStack MagicStick(){
        ItemStack stick = new ItemStack(Material.STICK);
        ItemMeta meta = stick.getItemMeta();
        meta.setUnbreakable(true);
        meta.setDisplayName(main.format("&bMagic Stick"));
        meta.addEnchant(Enchantment.MENDING,1, true);
        stick.setItemMeta(meta);
        return stick;
    }


    public ItemStack MagicApple(){
        ItemStack stack = new ItemStack(Material.GOLDEN_APPLE);
        ItemMeta meta = stack.getItemMeta();
        meta.setDisplayName(main.format("&6&l&oMagic Apple"));
        meta.setUnbreakable(true);
        meta.addEnchant(Enchantment.ARROW_INFINITE, 1,false);
        meta.addItemFlags(ItemFlag.HIDE_ENCHANTS);
        meta.addItemFlags(ItemFlag.HIDE_UNBREAKABLE);
        stack.setItemMeta(meta);
        return stack;
    }


    //Armamento del Boss
    public ItemStack EspadaDelBoos(){
        ItemStack stack = new ItemStack(Material.NETHERITE_SWORD);
        ItemMeta meta = stack.getItemMeta();
        meta.addEnchant(Enchantment.DAMAGE_ALL,10,true);
        meta.setDisplayName(main.format("&6&l&oInfernal Sword"));
        meta.setUnbreakable(true);
        meta.setLore(Arrays.asList(main.format("&bLa espada mas poderosa")));
        meta.addAttributeModifier(Attribute.GENERIC_ATTACK_DAMAGE,  new AttributeModifier(UUID.randomUUID(),
                "ATTACK_DAMAGE", 100.0, AttributeModifier.Operation.ADD_NUMBER, EquipmentSlot.HAND));
        meta.addAttributeModifier(Attribute.GENERIC_ATTACK_SPEED,  new AttributeModifier(UUID.randomUUID(),
                "ATTACK_SPEED", 30.0, AttributeModifier.Operation.ADD_NUMBER, EquipmentSlot.HAND));
        meta.setCustomModelData(6666666);
        stack.setItemMeta(meta);
        return stack;
    }


    public ItemStack ArcoDelBoos(){
        ItemStack stack = new ItemStack(Material.BOW);
        ItemMeta meta = stack.getItemMeta();
        meta.addEnchant(Enchantment.ARROW_DAMAGE,50,true);
        meta.addEnchant(Enchantment.ARROW_FIRE,1,true);
        meta.addEnchant(Enchantment.ARROW_INFINITE,1,true);
        meta.setDisplayName(main.format("&6&l&oInfernal Bow"));
        meta.setUnbreakable(true);
        meta.setLore(Arrays.asList(main.format("&bEl arco mas poderosa")));
        meta.setCustomModelData(6666666);
        stack.setItemMeta(meta);
        return stack;
    }

    public ItemStack EscombroDeVida(){
        ItemStack stack = new ItemStack(Material.IRON_INGOT);
        ItemMeta meta = stack.getItemMeta();
        meta.addEnchant(Enchantment.ARROW_DAMAGE,1,true);
        meta.addItemFlags(ItemFlag.HIDE_ENCHANTS);
        meta.addItemFlags(ItemFlag.HIDE_UNBREAKABLE);
        meta.setDisplayName(main.format("&6&l&oPoderes de Dios"));
        meta.setUnbreakable(true);
        meta.setLore(Arrays.asList(main.format("&bObten los Poderes de un Dios")));
        meta.setCustomModelData(7777777);
        stack.setItemMeta(meta);
        return stack;
    }

    public ItemStack FlechaDelBoos(){
        ItemStack stack = new ItemStack(Material.ARROW);
        ItemMeta meta = stack.getItemMeta();
        meta.addEnchant(Enchantment.KNOCKBACK,30,true);
        meta.setDisplayName(main.format("&6&l&oBoss Arrow"));
        meta.setUnbreakable(true);
        meta.setLore(Arrays.asList(main.format("&bLa flecha mas poderosa")));
        meta.setCustomModelData(6666666);
        stack.setItemMeta(meta);
        return stack;
    }



    public ItemStack CascoDelBoss(){
        ItemStack stack = new ItemStack(Material.NETHERITE_HELMET);
        ItemMeta meta = stack.getItemMeta();
        meta.setDisplayName(main.format("&6&l&oCasco del Boss"));

        meta.addAttributeModifier(Attribute.GENERIC_ARMOR,  new AttributeModifier(UUID.randomUUID(),
                "GENERIC_ARMOR", 10, AttributeModifier.Operation.ADD_NUMBER, EquipmentSlot.HEAD));

        meta.setUnbreakable(true);

        meta.addAttributeModifier(Attribute.GENERIC_ARMOR_TOUGHNESS, new AttributeModifier(UUID.randomUUID(),
                "GENERIC_ARMOR_TOUGHNESS",10,AttributeModifier.Operation.ADD_NUMBER, EquipmentSlot.HEAD));

        meta.addAttributeModifier(Attribute.GENERIC_KNOCKBACK_RESISTANCE, new AttributeModifier(UUID.randomUUID(),
                "GENERIC_KNOCKBACK_RESISTANCE",1,AttributeModifier.Operation.ADD_NUMBER, EquipmentSlot.HEAD));
        meta.setCustomModelData(6666666);
        stack.setItemMeta(meta);
        return stack;
    }

    public ItemStack ChestplateDelBoss(){
        ItemStack stack = new ItemStack(Material.NETHERITE_CHESTPLATE);
        ItemMeta meta = stack.getItemMeta();
        meta.setDisplayName(main.format("&6&l&oPerchera del Boss"));

        meta.addAttributeModifier(Attribute.GENERIC_ARMOR,  new AttributeModifier(UUID.randomUUID(),
                "GENERIC_ARMOR", 10, AttributeModifier.Operation.ADD_NUMBER, EquipmentSlot.CHEST));

        meta.setUnbreakable(true);

        meta.addAttributeModifier(Attribute.GENERIC_ARMOR_TOUGHNESS, new AttributeModifier(UUID.randomUUID(),
                "GENERIC_ARMOR_TOUGHNESS",10,AttributeModifier.Operation.ADD_NUMBER, EquipmentSlot.CHEST));

        meta.addAttributeModifier(Attribute.GENERIC_KNOCKBACK_RESISTANCE, new AttributeModifier(UUID.randomUUID(),
                "GENERIC_KNOCKBACK_RESISTANCE",1,AttributeModifier.Operation.ADD_NUMBER, EquipmentSlot.CHEST));
        meta.setCustomModelData(6666666);
        stack.setItemMeta(meta);
        return stack;
    }

    public ItemStack LegginsDelBoss(){
        ItemStack stack = new ItemStack(Material.NETHERITE_LEGGINGS);
        ItemMeta meta = stack.getItemMeta();
        meta.setDisplayName(main.format("&6&l&oPantalones del Boss"));

        meta.addAttributeModifier(Attribute.GENERIC_ARMOR,  new AttributeModifier(UUID.randomUUID(),
                "GENERIC_ARMOR", 10, AttributeModifier.Operation.ADD_NUMBER, EquipmentSlot.LEGS));

        meta.setUnbreakable(true);

        meta.addAttributeModifier(Attribute.GENERIC_ARMOR_TOUGHNESS, new AttributeModifier(UUID.randomUUID(),
                "GENERIC_ARMOR_TOUGHNESS",10,AttributeModifier.Operation.ADD_NUMBER, EquipmentSlot.LEGS));

        meta.addAttributeModifier(Attribute.GENERIC_KNOCKBACK_RESISTANCE, new AttributeModifier(UUID.randomUUID(),
                "GENERIC_KNOCKBACK_RESISTANCE",1,AttributeModifier.Operation.ADD_NUMBER, EquipmentSlot.LEGS));

        meta.setCustomModelData(6666666);
        stack.setItemMeta(meta);
        return stack;
    }

    public ItemStack BootsDelBoss(){
        ItemStack stack = new ItemStack(Material.NETHERITE_BOOTS);
        ItemMeta meta = stack.getItemMeta();
        meta.setDisplayName(main.format("&6&l&oBotas del Boss"));

        meta.addAttributeModifier(Attribute.GENERIC_ARMOR,  new AttributeModifier(UUID.randomUUID(),
                "GENERIC_ARMOR", 10, AttributeModifier.Operation.ADD_NUMBER, EquipmentSlot.FEET));

        meta.setUnbreakable(true);

        meta.addAttributeModifier(Attribute.GENERIC_ARMOR_TOUGHNESS, new AttributeModifier(UUID.randomUUID(),
                "GENERIC_ARMOR_TOUGHNESS",10,AttributeModifier.Operation.ADD_NUMBER, EquipmentSlot.FEET));

        meta.addAttributeModifier(Attribute.GENERIC_KNOCKBACK_RESISTANCE, new AttributeModifier(UUID.randomUUID(),
                "GENERIC_KNOCKBACK_RESISTANCE",1,AttributeModifier.Operation.ADD_NUMBER, EquipmentSlot.FEET));

        meta.setCustomModelData(6666666);

        stack.setItemMeta(meta);
        return stack;
    }


    public void Boss(Location e){
        RealNetherite real = new RealNetherite();
        Skeleton skeleton = e.getBlock().getWorld().spawn(e.getBlock().getLocation().add(0.5,0,0.5),Skeleton.class);
        skeleton.setCustomName("§8§lSkeleton Boss");
        skeleton.setCustomNameVisible(true);
        skeleton.setMaxHealth(50.0);
        skeleton.setHealth(50.0);
        skeleton.getEquipment().setItemInHand(real.NetheriteSword());
        skeleton.getEquipment().setHelmet(real.HelmetNetherite());
        skeleton.getEquipment().setChestplate(real.ChestplateNetherite());
        skeleton.getEquipment().setLeggings(real.LeggingsNetherite());
        skeleton.getEquipment().setBoots(real.BootsNetherite());
        skeleton.setMetadata("SkeletonKing",new FixedMetadataValue(main,"skeletonking"));

    }




}
