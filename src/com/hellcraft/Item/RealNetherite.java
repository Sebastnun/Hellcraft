package com.hellcraft.Item;

import com.hellcraft.Main;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.attribute.Attribute;
import org.bukkit.attribute.AttributeModifier;
import org.bukkit.inventory.EquipmentSlot;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import java.util.UUID;

public class RealNetherite {

    private Main main = Main.getInstance();

    public ItemStack NetheriteSword(){
        ItemStack sword = new ItemStack(Material.NETHERITE_SWORD);
        ItemMeta meta = sword.getItemMeta();
        meta.setDisplayName(main.format("&4&l&oReal Netherite Sword"));
        meta.setUnbreakable(true);
        meta.addAttributeModifier(Attribute.GENERIC_ATTACK_DAMAGE,  new AttributeModifier(UUID.randomUUID(),
                "ATTACK_DAMAGE", 9, AttributeModifier.Operation.ADD_NUMBER, EquipmentSlot.HAND));
        meta.addAttributeModifier(Attribute.GENERIC_ATTACK_SPEED,  new AttributeModifier(UUID.randomUUID(),
                "ATTACK_SPEED", -2, AttributeModifier.Operation.ADD_NUMBER, EquipmentSlot.HAND));
        meta.setCustomModelData(1234567);
        sword.setItemMeta(meta);
        return sword;
    }

    public ItemStack NetheritePickaxe(){
        ItemStack sword = new ItemStack(Material.NETHERITE_PICKAXE);
        ItemMeta meta = sword.getItemMeta();
        meta.setDisplayName(main.format("&4&l&oReal Netherite Pickaxe"));
        meta.setUnbreakable(true);
        meta.setCustomModelData(1234567);
        sword.setItemMeta(meta);
        return sword;
    }

    public ItemStack NetheriteHoe(){
        ItemStack sword = new ItemStack(Material.NETHERITE_HOE);
        ItemMeta meta = sword.getItemMeta();
        meta.setDisplayName(main.format("&4&l&oReal Netherite Hoe"));
        meta.setUnbreakable(true);
        meta.setCustomModelData(1234567);
        sword.setItemMeta(meta);
        return sword;
    }

    public ItemStack NetheriteShovel(){
        ItemStack sword = new ItemStack(Material.NETHERITE_SHOVEL);
        ItemMeta meta = sword.getItemMeta();
        meta.setDisplayName(main.format("&4&l&oReal Netherite Shovel"));
        meta.setUnbreakable(true);
        meta.setCustomModelData(1234567);
        sword.setItemMeta(meta);
        return sword;
    }


    public ItemStack NetheriteAxe(){
        ItemStack sword = new ItemStack(Material.NETHERITE_AXE);
        ItemMeta meta = sword.getItemMeta();
        meta.setDisplayName(main.format("&4&l&oReal Netherite Axe"));
        meta.addAttributeModifier(Attribute.GENERIC_ATTACK_DAMAGE,  new AttributeModifier(UUID.randomUUID(),
                "ATTACK_DAMAGE", 11, AttributeModifier.Operation.ADD_NUMBER, EquipmentSlot.HAND));
        meta.addAttributeModifier(Attribute.GENERIC_ATTACK_SPEED,  new AttributeModifier(UUID.randomUUID(),
                "ATTACK_SPEED", -2.6, AttributeModifier.Operation.ADD_NUMBER, EquipmentSlot.HAND));
        meta.setUnbreakable(true);
        meta.setCustomModelData(1234567);
        sword.setItemMeta(meta);
        return sword;
    }

    public ItemStack NetheriteIngot(){
        ItemStack sword = new ItemStack(Material.NETHERITE_INGOT);
        ItemMeta meta = sword.getItemMeta();
        meta.setDisplayName(main.format("&4&l&oReal Netherite Ingot"));
        meta.setUnbreakable(true);
        meta.setCustomModelData(1234567);
        sword.setItemMeta(meta);
        return sword;
    }

    public ItemStack NetheriteStick(){
        ItemStack sword = new ItemStack(Material.STICK);
        ItemMeta meta = sword.getItemMeta();
        meta.setDisplayName(main.format("&4&l&oReal Netherite Stick"));
        meta.setUnbreakable(true);
        meta.setCustomModelData(1234567);
        sword.setItemMeta(meta);
        return sword;
    }


    public ItemStack HelmetNetherite(){
        ItemStack itemStack = new ItemStack(Material.NETHERITE_HELMET);

        ItemMeta meta = itemStack.getItemMeta() ;
        meta.setDisplayName(ChatColor.translateAlternateColorCodes('§', "§4§l§oReal Netherite Helmet"));

        meta.addAttributeModifier(Attribute.GENERIC_ARMOR,  new AttributeModifier(UUID.randomUUID(),
                "GENERIC_ARMOR", 3, AttributeModifier.Operation.ADD_NUMBER, EquipmentSlot.HEAD));

        meta.setUnbreakable(true);

        meta.addAttributeModifier(Attribute.GENERIC_ARMOR_TOUGHNESS, new AttributeModifier(UUID.randomUUID(),
                "GENERIC_ARMOR_TOUGHNESS",4,AttributeModifier.Operation.ADD_NUMBER, EquipmentSlot.HEAD));

        meta.addAttributeModifier(Attribute.GENERIC_KNOCKBACK_RESISTANCE, new AttributeModifier(UUID.randomUUID(),
                "GENERIC_KNOCKBACK_RESISTANCE",0.2,AttributeModifier.Operation.ADD_NUMBER, EquipmentSlot.HEAD));
        meta.setCustomModelData(1234567);
        itemStack.setItemMeta(meta);
        return itemStack;
    }

    public ItemStack ChestplateNetherite(){
        ItemStack itemStack = new ItemStack(Material.NETHERITE_CHESTPLATE);

        ItemMeta meta = itemStack.getItemMeta() ;
        meta.setDisplayName(ChatColor.translateAlternateColorCodes('§', "§4§l§oReal Netherite Chestplate"));

        meta.addAttributeModifier(Attribute.GENERIC_ARMOR,  new AttributeModifier(UUID.randomUUID(),
                "GENERIC_ARMOR", 8, AttributeModifier.Operation.ADD_NUMBER, EquipmentSlot.CHEST));

        meta.setUnbreakable(true);

        meta.addAttributeModifier(Attribute.GENERIC_ARMOR_TOUGHNESS, new AttributeModifier(UUID.randomUUID(),
                "GENERIC_ARMOR_TOUGHNESS",4,AttributeModifier.Operation.ADD_NUMBER, EquipmentSlot.CHEST));
        meta.setCustomModelData(1234567);
        meta.addAttributeModifier(Attribute.GENERIC_KNOCKBACK_RESISTANCE, new AttributeModifier(UUID.randomUUID(),
                "GENERIC_KNOCKBACK_RESISTANCE",0.2,AttributeModifier.Operation.ADD_NUMBER, EquipmentSlot.CHEST));
        itemStack.setItemMeta(meta);
        return itemStack;
    }

    public ItemStack LeggingsNetherite(){
        ItemStack itemStack = new ItemStack(Material.NETHERITE_LEGGINGS);

        ItemMeta meta = itemStack.getItemMeta() ;
        meta.setDisplayName(ChatColor.translateAlternateColorCodes('§', "§4§l§oReal Netherite Leggings"));

        meta.addAttributeModifier(Attribute.GENERIC_ARMOR,  new AttributeModifier(UUID.randomUUID(),
                "GENERIC_ARMOR", 6, AttributeModifier.Operation.ADD_NUMBER, EquipmentSlot.LEGS));
        meta.setCustomModelData(1234567);
        meta.setUnbreakable(true);

        meta.addAttributeModifier(Attribute.GENERIC_ARMOR_TOUGHNESS, new AttributeModifier(UUID.randomUUID(),
                "GENERIC_ARMOR_TOUGHNESS",1,AttributeModifier.Operation.ADD_NUMBER, EquipmentSlot.LEGS));

        meta.addAttributeModifier(Attribute.GENERIC_KNOCKBACK_RESISTANCE, new AttributeModifier(UUID.randomUUID(),
                "GENERIC_KNOCKBACK_RESISTANCE",0.2,AttributeModifier.Operation.ADD_NUMBER, EquipmentSlot.LEGS));
        itemStack.setItemMeta(meta);
        return itemStack;
    }




    public ItemStack BootsNetherite(){
        ItemStack itemStack = new ItemStack(Material.NETHERITE_BOOTS);//

        ItemMeta meta = itemStack.getItemMeta() ;
        meta.setDisplayName(ChatColor.translateAlternateColorCodes('§', "§4§l§oReal Netherite Boots"));

        meta.addAttributeModifier(Attribute.GENERIC_ARMOR,  new AttributeModifier(UUID.randomUUID(),
                "GENERIC_ARMOR", 3, AttributeModifier.Operation.ADD_NUMBER, EquipmentSlot.FEET));
        meta.setCustomModelData(1234567);
        meta.setUnbreakable(true);

        meta.addAttributeModifier(Attribute.GENERIC_ARMOR_TOUGHNESS, new AttributeModifier(UUID.randomUUID(),
                "GENERIC_ARMOR_TOUGHNESS",3,AttributeModifier.Operation.ADD_NUMBER, EquipmentSlot.FEET));

        meta.addAttributeModifier(Attribute.GENERIC_KNOCKBACK_RESISTANCE, new AttributeModifier(UUID.randomUUID(),
                "GENERIC_KNOCKBACK_RESISTANCE",0.2,AttributeModifier.Operation.ADD_NUMBER, EquipmentSlot.FEET));
        itemStack.setItemMeta(meta);
        return itemStack;
    }


}
