package com.hellcraft.Item;


import de.tr7zw.nbtapi.NBTContainer;
import de.tr7zw.nbtapi.NBTItem;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.attribute.Attribute;
import org.bukkit.attribute.AttributeModifier;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.inventory.EquipmentSlot;
import org.bukkit.inventory.ItemFlag;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import java.util.Arrays;
import java.util.UUID;


public class NebulaArmor {

    private NBTItem nbti;


    private ItemStack setNBT(ItemStack itemStack){
        nbti = new NBTItem(itemStack);
        nbti.mergeCompound(new NBTContainer("{CanPlaceOn:[granite]}"));

        return nbti.getItem();
    }

    private ItemStack nbtCustom(ItemStack itemStack){
        nbti = new NBTItem(itemStack);
        nbti.mergeCompound(new NBTContainer("{CustomModelData:7654321,nebula:1b}"));

        return nbti.getItem();
    }

    //Nebula = Granite
    //Vortex = gravel


    public ItemStack NebulaHelmet(){
        ItemStack itemStack = new ItemStack(Material.NETHERITE_HELMET);
        itemStack = setNBT(itemStack);
        itemStack = nbtCustom(itemStack);
        ItemMeta meta = itemStack.getItemMeta() ;
        meta.setUnbreakable(true);
        meta.setDisplayName(ChatColor.translateAlternateColorCodes('&', "&a&l&oNebulous Helmet"));
        meta.setLore(Arrays.asList("having the 3 components","unlocks the magical abilities"));
        meta.setCustomModelData(7654321);
        itemStack.setItemMeta(meta);
        return itemStack;
    }


    public ItemStack NebulaChestplate(){
        ItemStack itemStack = new ItemStack(Material.NETHERITE_CHESTPLATE);

        itemStack = setNBT(itemStack);
        itemStack = nbtCustom(itemStack);
        ItemMeta meta = itemStack.getItemMeta() ;
        meta.setUnbreakable(true);
        meta.setDisplayName(ChatColor.translateAlternateColorCodes('&', "&a&l&oNebulous Chestplate"));
        meta.setLore(Arrays.asList("having the 3 components","unlocks the magical abilities"));
        meta.setCustomModelData(7654321);
        itemStack.setItemMeta(meta);
        return itemStack;
    }

    public ItemStack NebulaLeggins(){
        ItemStack itemStack = new ItemStack(Material.NETHERITE_LEGGINGS);

        itemStack = setNBT(itemStack);
        itemStack = nbtCustom(itemStack);
        ItemMeta meta = itemStack.getItemMeta() ;
        meta.setUnbreakable(true);
        meta.setDisplayName(ChatColor.translateAlternateColorCodes('&', "&a&l&oNebulous Leggins"));
        meta.setLore(Arrays.asList("having the 3 components","unlocks the magical abilities"));
        meta.setCustomModelData(7654321);
        itemStack.setItemMeta(meta);
        return itemStack;
    }

    public ItemStack NebulaBoots(){
        ItemStack itemStack = new ItemStack(Material.NETHERITE_BOOTS);

        itemStack = setNBT(itemStack);
        itemStack = nbtCustom(itemStack);
        ItemMeta meta = itemStack.getItemMeta() ;
        meta.setUnbreakable(true);
        meta.setDisplayName(ChatColor.translateAlternateColorCodes('&', "&a&l&oNebulous Boots"));
        meta.setLore(Arrays.asList("having the 3 components","unlocks the magical abilities"));
        meta.setCustomModelData(7654321);
        itemStack.setItemMeta(meta);
        return itemStack;
    }


    public ItemStack DeaethGem(){
        ItemStack itemStack = new ItemStack(Material.DIAMOND);

        itemStack = setNBT(itemStack);
        itemStack = nbtCustom(itemStack);
        ItemMeta meta = itemStack.getItemMeta() ;
        meta.addEnchant(Enchantment.MENDING,1,true);
        meta.addItemFlags(ItemFlag.HIDE_ENCHANTS);
        meta.setDisplayName(ChatColor.translateAlternateColorCodes('&', "&a&l&oGema de la Muerte"));
        meta.setLore(Arrays.asList("This is using for crafting","the Nebulous Armor"));
        meta.setCustomModelData(7654321);
        itemStack.setItemMeta(meta);
        return itemStack;
    }

    public ItemStack NebuluosIngot(){
        ItemStack itemStack = new ItemStack(Material.NETHERITE_INGOT);

        itemStack = setNBT(itemStack);
        itemStack = nbtCustom(itemStack);
        ItemMeta meta = itemStack.getItemMeta() ;
        meta.addEnchant(Enchantment.MENDING,1,true);
        meta.addItemFlags(ItemFlag.HIDE_ENCHANTS);
        meta.setDisplayName(ChatColor.translateAlternateColorCodes('&', "&a&l&oNebulous Ingot"));
        meta.setLore(Arrays.asList("This is using for crafting","the Nebulous Armor"));
        meta.setCustomModelData(7654321);
        itemStack.setItemMeta(meta);
        return itemStack;
    }

    public ItemStack NebulaElytras(){
        ItemStack itemStack = new ItemStack(Material.ELYTRA);

        itemStack = nbtCustom(itemStack);
        itemStack = setNBT(itemStack);
        ItemMeta meta = itemStack.getItemMeta() ;
        meta.addAttributeModifier(Attribute.GENERIC_ARMOR,  new AttributeModifier(UUID.randomUUID(),
                "GENERIC_ARMOR", 8, AttributeModifier.Operation.ADD_NUMBER, EquipmentSlot.CHEST));

        meta.setUnbreakable(true);

        meta.addAttributeModifier(Attribute.GENERIC_ARMOR_TOUGHNESS, new AttributeModifier(UUID.randomUUID(),
                "GENERIC_ARMOR_TOUGHNESS",4,AttributeModifier.Operation.ADD_NUMBER, EquipmentSlot.CHEST));
        meta.setDisplayName(ChatColor.translateAlternateColorCodes('&', "&a&l&oNebulous Elytras"));
        meta.setLore(Arrays.asList("having the 3 components","unlocks the magical abilities"));
        meta.setCustomModelData(7654321);

        itemStack.setItemMeta(meta);
        return itemStack;
    }







}
